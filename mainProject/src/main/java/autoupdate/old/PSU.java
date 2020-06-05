//ok
package autoupdate.old;

import java.io.IOException;
	import java.util.ArrayList;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
	import org.jsoup.nodes.Document;
	import org.jsoup.nodes.Element;
	import org.jsoup.select.Elements;
public class PSU extends Crawler{
	public PSU() throws IOException {
		super("https://www.coolpc.com.tw/eachview.php?IGrp=15");
		Elements tbody = doc.select(".main");
		for(Element currenttbody : tbody) {	
			Elements span= currenttbody.select("span");
			for(Element currentspan : span) {	
				 tmp.clear();
				 

				 Elements div = currentspan.select("div");
				 Elements name = currentspan.select(".t");
				 Elements x = currentspan.select(".x");
				 tmp.add(0,name.text());//cpu
				 
				if(name.text().contains("SFX"))
					canAdd( tmp.size(),3,"sfx");
				else 
					canAdd( tmp.size(),3,"atx");
				
				 for(Element currentdiv : div) { 
					if(currentdiv.text().split("電源長度：").length >1) {
						canAdd( tmp.size(),1,currentdiv.text().split("電源長度：")[1]);//cpu
					}
				 }	
				 
				 int i=1,watt=0;
				 int wattIt=name.text().lastIndexOf("0W");
				 while(i<wattIt-1) {
					 if(name.text().charAt(wattIt-i)-'0' >= 0 && name.text().charAt(wattIt-i)-'0' <= 9)
						 watt+= (name.text().charAt(wattIt-i)-'0')*Math.pow(10, i-1);
					 else
						 break;
					 i++;
				 }
				 canAdd( tmp.size(),2,watt+"0W");
				 
				 System.out.println(tmp);
				 obj.add(tmp);
			//Name WATT AWG �q������  Money
		}
		}
	}
}

