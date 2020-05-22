//ok

package may11;

import java.io.IOException;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Battery extends Crawler{
	public Battery() throws IOException {
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
				 for(Element currentdiv : div) { 
					if(currentdiv.text().split("電源長度：").length >1) {
						 tmp.add(1,currentdiv.text().split("電源長度：")[1]);//cpu
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
					 tmp.add(1,watt+"0W");
				 
				 String[] money=x.text().split("NT")[1].split("◆開箱討論");
				 tmp.add(money[0]);
				 System.out.println(tmp);
				 obj.add(tmp);
			}
		 }
	}
}
