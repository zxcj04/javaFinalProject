package autoupdate;

import org.bson.Document;

import org.jsoup.Jsoup;
//import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import mainlogic.Vga;

import java.io.IOException;
import java.util.Iterator;

import java.util.ArrayList;
import java.util.regex.*;

public class UpdateVga {
	private String url;
	private ArrayList<Document> info;
	private Document nowVga;
	
	public UpdateVga() {
		url = "https://www.coolpc.com.tw/eachview.php?IGrp=12";
    	info = new ArrayList<Document>();
    	nowVga = new Document();
    	
    	try {
    		org.jsoup.nodes.Document doc = Jsoup.connect(url).get();
    		
    		doc.select("div.main span div.x").remove();
    		
    		Elements blocks = doc.select("div.main span");
    		
    		for(Element block : blocks) {
    			Elements eles = block.select("div");
    			
    			
    			if(eles.size() > 1) {
    				Iterator<Element> iter = eles.iterator();
    				
    				Element title = iter.next();
    				
    				while(iter.hasNext()) {
    					Element div = iter.next();
    					
    					if(matchVga(div.text())) {
    						matchName(title.text());
							info.add(nowVga);
							
							nowVga = new Document();
    						
    						break;
    					}
    				}
    			}
    		}
    	}
    	catch(Exception e) {
    		e.printStackTrace();
    	}
	}
	
	public boolean matchVga(String inp) {
    	Matcher length = findstr(inp, "顯卡長度(.*?)([0-9]+([.][0-9]+)?)");
    	
    	if(length.find()){
    		double len = Double.parseDouble(length.group(2));
    		len = Math.ceil(len);
    		
            nowVga.append("length", ((Double)len).intValue());
            
            return true;
        }
		
		else {
			nowVga.append("length", 0);

			return false;
		}
    	
    }
	
	public void matchName(String inp) {
    	Matcher name = findstr(inp, "^[^(]+");
    	
    	if(name.find()){
            nowVga.append("name", name.group(0));
        }
		
		else {
			nowVga.append("name", inp);
		}
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
				ele.append("TDP", 0);

				// System.out.println(ele);
				ele.remove("_id");
				Vga.toObject(ele);

				info.add(ele);
			}
			catch(Exception e)
			{
				// e.printStackTrace();
			}
		}

    	return info;
    }
    
    public static void main(String[] args) {
    	UpdateVga a = new UpdateVga();
    }
}
