package autoupdate.old;

import java.io.IOException;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Fan extends Crawler{
	public Fan() throws IOException {
		super("https://www.coolpc.com.tw/eachview.php?IGrp=16");
		Elements tbody = doc.select(".main");
		for(Element currenttbody : tbody) {	
			Elements span= currenttbody.select("span");
			for(Element currentspan : span) {	
				 tmp.clear();
				 Elements name = currentspan.select(".t");
				 Elements x = currentspan.select(".x");
				 tmp.add(0,name.text());

				 int i=1,centimeter=0;
				 int centimeterIt=(name.text().lastIndexOf("cm") > 0 ? name.text().lastIndexOf("cm"):name.text().lastIndexOf("CM"));
				 while(i<centimeterIt-1) {
					 if(name.text().charAt(centimeterIt-i)-'0' >= 0 && name.text().charAt(centimeterIt-i)-'0' <= 9)
						 centimeter+= (name.text().charAt(centimeterIt-i)-'0')*Math.pow(10, i-1);
					 else
						 break;
					 i++;
				 }
					 tmp.add(1,centimeter+"cm");
				 
				 String[] money=x.text().split("NT")[1].split("���}�c�Q��");
				 tmp.add(money[0]);
				 System.out.println(tmp);
				 obj.add(tmp);
			}
		 }
	}
}
