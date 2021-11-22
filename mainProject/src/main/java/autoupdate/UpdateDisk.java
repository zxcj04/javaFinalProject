package autoupdate;

import org.bson.Document;

import org.jsoup.Jsoup;
//import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import mainlogic.Disk;

import java.util.ArrayList;
import java.util.regex.*;

public class UpdateDisk {
	private String ssdUrl;
	private String hddUrl;
    private ArrayList<Document> info;
    private Document nowDisk;
    
    public UpdateDisk() {
    	ssdUrl = "https://www.coolpc.com.tw/eachview.php?IGrp=7";
    	hddUrl = "https://www.coolpc.com.tw/eachview.php?IGrp=8";
    	info = new ArrayList<Document>();
    	nowDisk = new Document();
    	
    	
    	try {
    		org.jsoup.nodes.Document doc = Jsoup.connect(ssdUrl).get();
    		
    		Elements titles = doc.select("div.main span div.t");
    		
    		for(Element title : titles) {
//    			System.out.println(title.text());
    			
    			if(matchCapacity(title.text())) {
    				matchSsdName(title.text());
	    			matchDiskType(title.text());
	    			matchSsdSize(title.text());
	    			
					info.add(nowDisk);
					
					nowDisk = new Document();
    			}
    			
//    			System.out.println("-----------------------------------------");
    		}
    		
    		doc = Jsoup.connect(hddUrl).get();
    		
    		titles = doc.select("div.main span div.t");
    		
    		for(Element title : titles) {
//    			System.out.println(title.text());

    			if(matchCapacity(title.text())) {
	    			matchHddName(title.text());
	    			matchDiskType(title.text());
	    			matchHddSize(title.text());

					info.add(nowDisk);
					
					nowDisk = new Document();
    			}
    			
//    			System.out.println("-----------------------------------------");
    		}
    	}
    	catch(Exception e) {
    		e.printStackTrace();
    	}
    }

    public void matchSsdName(String inp) {
    	Matcher name = findstr(inp, "([^【]+)[/]");
    	
    	if(name.find()){
    		nowDisk.append("name", name.group(1));
        }
		
		else {
			nowDisk.append("name", inp);
		}
    	
//    	System.out.print("name: ");
//    	System.out.println(nowDisk.getString("name"));
    }

    public void matchHddName(String inp) {
    	Matcher name = findstr(inp, "[^(]+");
    	
    	if(name.find()){
    		nowDisk.append("name", name.group(0));
        }
		
		else {
			nowDisk.append("name", inp);
		}
    	
//    	System.out.print("name: ");
//    	System.out.println(nowDisk.getString("name"));
    }
    
    public void matchDiskType(String inp) {
    	Matcher diskType = findstr(inp, "PCIe");
    	
    	if(diskType.find()){
    		nowDisk.append("diskType", diskType.group(0).toLowerCase());
        }
		
		else {
			nowDisk.append("diskType", "sata");
		}
    	
//    	System.out.print("diskType: ");
//    	System.out.println(nowDisk.getString("diskType"));
    }
    
    public boolean matchCapacity(String inp) {
    	Matcher capacity = findstr(inp, "([0-9]+)(T|G)B?");
    	
    	if(capacity.find()){
    		int number = Integer.parseInt(capacity.group(1));
    		
    		if(capacity.group(2).equals("T")) {
    			number *= 1000;
    		}
    		
    		nowDisk.append("capacity", number);
        	
//        	System.out.print("capacity: ");
//        	System.out.println(nowDisk.getInteger("capacity"));
    		
    		return true;
        }
		
		else {
			return false;
		}
    }
    
    public void matchSsdSize(String inp) {
    	Matcher size = findstr(inp, "(M[.]2)|(2[.]5)|(3[.]5)");
    	
    	if(size.find()){
    		nowDisk.append("size", size.group(0).toLowerCase());
        }
		
		else {
			nowDisk.append("size", "2.5");
		}
    	
//    	System.out.print("size: ");
//    	System.out.println(nowDisk.getString("size"));
    }
    
    public void matchHddSize(String inp) {
    	Matcher size = findstr(inp, "(2[.]5)|(3[.]5)");
    	
    	if(size.find()){
    		nowDisk.append("size", size.group(0));
        }
		
		else {
			nowDisk.append("size", "3.5");
		}
    	
//    	System.out.print("size: ");
//    	System.out.println(nowDisk.getString("size"));
    }
    	
    public Matcher findstr(String inp, String pattern) {
    	Pattern p = Pattern.compile(pattern);
    	Matcher m = p.matcher(inp);
    	return m;
    }
    
    public ArrayList<Document> getInfo(){
		ArrayList<Document> info = new ArrayList<Document>();

		for(Document ele : this.info)
		{
			try
			{
				Disk.toObject(ele);
				
				ele.remove("_id");
				info.add(ele);
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}

    	return info;
    }
    
    public static void main(String[] args) {
    	UpdateDisk a = new UpdateDisk();
    }
}
