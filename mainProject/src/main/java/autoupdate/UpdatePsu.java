package autoupdate;

import org.bson.Document;

import org.jsoup.Jsoup;
//import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
 
import java.io.IOException;
import java.util.Iterator;

import java.util.ArrayList;
import java.util.regex.*;

public class UpdatePsu {
	private String url;
    private ArrayList<Document> info;
    private Document nowPsu;
    
    public UpdatePsu() {
    	url = "https://www.coolpc.com.tw/eachview.php?IGrp=15";
    	info = new ArrayList<Document>();
    	nowPsu = new Document();
    	
    	
    	try {
    		org.jsoup.nodes.Document doc = Jsoup.connect(url).get();
    		
    		doc.select("div.main span div.x").remove();
    		
    		Elements blocks = doc.select("div.main span");
    		
    		for(Element block : blocks) {
    			Elements eles = block.select("div");
    			
    			if(eles.size() == 2) {
    				Iterator<Element> iter = eles.iterator();
    				Element title = iter.next();
    				Element psuLength = iter.next();
    				
    				matchName(title.text());
    				matchLength(psuLength.text());
    				matchWatts(title.text());
    				matchSize(title.text());
    				
    				info.add(nowPsu);
    			}
    		}
    	}
    	catch(Exception e) {
    		e.printStackTrace();
    	}
    }
    
    public void matchName(String inp) {
    	Matcher name = findstr(inp, "(.*?)/([^/]+)$");
    	
    	if(name.find()){
            nowPsu.append("name", name.group(1));
        }
		
		else {
			nowPsu.append("name", inp);
		}
    	
//    	System.out.println(nowPsu.getString("name"));
    }
    
    public void matchLength(String inp) {
    	Matcher length = findstr(inp, "([0-9]+([.][0-9]+)?)[+]?([0-9]+)?");
    	
    	if(length.find()){
    		double number = Double.parseDouble(length.group(1));
    		number = Math.ceil(number);
    		
    		if(length.group(3) != null) {
    			int second = Integer.parseInt(length.group(3));
    			number += second;
    		}
    		
            nowPsu.append("length", ((Double)number).intValue());
        }
    	
//    	System.out.println(nowPsu.getInteger("length"));
    }
    
    public void matchWatts(String inp) {
    	Matcher watts = findstr(inp, "([0-9]+)W");
    	
    	if(watts.find()){
            nowPsu.append("watts", Integer.parseInt(watts.group(1)));
        }
    	
//    	System.out.println(nowPsu.getInteger("watts"));
    }
    
    public void matchSize(String inp) {
    	Matcher size = findstr(inp, "SFX");
    	
    	if(size.find()){
            nowPsu.append("size", size.group(0).toLowerCase());
        }
		
		else {
			nowPsu.append("size", "atx");
		}
    	
//    	System.out.println(nowPsu.getString("size"));
    }
    
    public Matcher findstr(String inp, String pattern) {
    	Pattern p = Pattern.compile(pattern);
    	Matcher m = p.matcher(inp);
    	return m;
    }
    
    public ArrayList<Document> getInfo(){
    	return info;
    }
    
    public static void main(String[] args) {
    	UpdatePsu a = new UpdatePsu();
    }
}
