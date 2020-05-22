//ok
package may11;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
 
import java.io.IOException;
import java.util.ArrayList;


public class Memory extends Crawler{
	public Memory() throws IOException {
		super("https://www.coolpc.com.tw/eachview.php?IGrp=6");
		Elements span =  doc.select("span");
		 for (Element currentspan : span ) {
			 tmp.clear();
			 Elements t = currentspan.select(".t");
			 Elements x = currentspan.select(".x");
			 String inf=t.text();
			
			 String[] money=x.text().split("NT")[1].split("¡»¶}½c°Q½×");
			 int i=1,gb=0;
			 tmp.add(0,inf);
			 int gbiteam=inf.lastIndexOf("GB");
			 while(i<gbiteam-1) {
				 if(inf.charAt(gbiteam-i)-'0' >= 0 && inf.charAt(gbiteam-i)-'0' <= 9)
					 gb+= (inf.charAt(gbiteam-i)-'0')*Math.pow(10, i-1);
				 else
					 break;
				 i++;
			 }
				 tmp.add(1,gb+"GB");
			int ddrit=inf.lastIndexOf("DDR"); 
			 if(ddrit!=-1 && (inf.charAt(ddrit+3)-'0' >= 0 && inf.charAt(ddrit+3)-'0' <= 9))
				 tmp.add(2,"DDR"+inf.charAt(ddrit+3));
			 else 
				 tmp.add(2,"DDR4");
			 tmp.add(3,money[0]);
			 System.out.println(tmp);
			}
		}	 
}
