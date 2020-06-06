package autoupdate;

import org.bson.Document;

import org.jsoup.Jsoup;
//import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import mainlogic.Ram;

import java.io.IOException;
import java.util.Iterator;

import java.util.ArrayList;
import java.util.regex.*;

public class UpdateRam {
	private String url;
    private ArrayList<Document> info;
    private Document nowRam;
    
    public UpdateRam() {
    	url = "https://www.coolpc.com.tw/eachview.php?IGrp=6";
    	info = new ArrayList<Document>();
    	nowRam = new Document();
    	
    	
    	try {
    		org.jsoup.nodes.Document doc = Jsoup.connect(url).get();
    		
    		Elements titles = doc.select("div.main span div.t");
    		
    		for(Element title : titles) {
//    			System.out.println(title.text());
    			
    			if(matchCapacity(title.text())) {
    				matchRamType(title.text());
    				matchName(title.text());
    				
    				info.add(nowRam);
    			}
    			
//    			System.out.println("-----------------");
    		}
    	}
    	catch(Exception e) {
    		e.printStackTrace();
    	}
    }
    
    public boolean matchCapacity(String inp) {
    	Matcher capacity = findstr(inp, "GB?[*]");
    	
    	if(capacity.find()){
            return false;
        }
		
		else {
			capacity = findstr(inp, "([0-9]+)GB?");
			
			if(capacity.find()) {
				nowRam.append("capacity", Integer.parseInt(capacity.group(1)));
	
//	        	System.out.println(nowRam.getInteger("capacity"));
	            return true;
			}
			else {
				return false;
			}
		}
    	
    }
    
    public void matchRamType(String inp) {
    	Matcher ramType = findstr(inp, "(DDR[1-4])|(D([3-4]))");
    	
    	if(ramType.find()){
    		if(ramType.group(1) != null)
    			nowRam.append("ramType", ramType.group(1).toLowerCase());
    		else
    			nowRam.append("ramType", "ddr" + ramType.group(3));
        }
		
		else {
			nowRam.append("ramType", "n/a");
		}
    	
//    	System.out.println(nowRam.getString("ramType"));
    }
    
    public void matchName(String inp) {
    	Matcher name = findstr(inp, "^((.*?)(DDR[0-9]|D[0-9])([^(]+))");
    	
    	if(name.find()){
            nowRam.append("name", name.group(1));
        }
		
		else {
			nowRam.append("name", inp);
		}
    	
//    	System.out.println(nowRam.getString("name"));
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
				Ram.toObject(ele);

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
    	UpdateRam a = new UpdateRam();
    }
}
