//ok
package autoupdate.old;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
 
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Pattern;


public class Memory extends Crawler{
	public Memory() throws IOException {
		super("https://www.coolpc.com.tw/eachview.php?IGrp=6");
		Elements span =  doc.select("span");
		 for (Element currentspan : span ) {
			 tmp.clear();
			 Elements t = currentspan.select(".t");
			 Elements x = currentspan.select(".x");
			 String inf=t.text();
			
			 int i=1,gb=0;
			 tmp.add(0,inf);
			 pattern = Pattern.compile("([0-9]+)G(B)?(\\*\\d)?");
			 m = pattern.matcher(inf);
			 if (m.find( )) {
				 canAdd( tmp.size(),2,m.group().split("G")[0]);
			 }
			int ddrit=inf.lastIndexOf("DDR"); 
			 if(ddrit!=-1 && (inf.charAt(ddrit+3)-'0' >= 0 && inf.charAt(ddrit+3)-'0' <= 9))
				 canAdd( tmp.size(),1,"ddr"+inf.charAt(ddrit+3));
			 else 
				 canAdd( tmp.size(),1,"ddr4");
			 System.out.println(tmp);
			}
		}	 
}
