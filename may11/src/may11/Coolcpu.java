package may11;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
 
import java.io.IOException;
import java.util.ArrayList;


public class Coolcpu extends Crawler {

	public Coolcpu() throws IOException {
		super("https://www.coolpc.com.tw/AMD.htm");
		 Elements table = doc.select("table");
		 for(Element currenttable : table) {
			 Elements MsoNormalTable = currenttable.select(".MsoNormalTable");
			 for(Element currentMsoNormalTable : MsoNormalTable) { 
				 Elements tr = currentMsoNormalTable.select("tr");
				 for(Element currenttr : tr) {	 
					 tmp.clear();
					 //System.out.println(currenttr.text());
					 Elements td = currenttr.select("td");
					 for(Element currenttd : td) {	
						 //System.out.println(currenttd.text());
						 tmp.add(currenttd.text());
					 }
				     if(tmp.size()>2){	
				    	 obj.add(tmp);
				     //System.out.println(tmp);
				     }
				}
			}
			 System.out.println("------------------------------");
		// System.out.println(obj);
		 }
	}
}
