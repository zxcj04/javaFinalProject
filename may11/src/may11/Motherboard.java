package may11;

import java.io.IOException;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Motherboard extends Crawler{
	public Motherboard() throws IOException {
		super("https://www.coolpc.com.tw/eachview.php?IGrp=5");
		Elements tbody = doc.select(".main");
		for(Element currenttbody : tbody) {	
			Elements span= currenttbody.select("span");
			for(Element currentspan : span) {	
				 tmp.clear();
				 Elements t = currentspan.select(".t");
				 Elements x = currentspan.select(".x");
				 String inf=t.text();
				
				 String[] money=x.text().split("NT")[1].split("¡»¶}½c°Q½×");
				 
				 
				System.out.println(money[0]);
			}
			 tmp.add(currenttbody.html());
		 }

	}
}
