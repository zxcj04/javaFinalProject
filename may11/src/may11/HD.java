package may11;


import java.io.IOException;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HD extends Crawler{
	public HD() throws IOException {
		super("https://www.coolpc.com.tw/eachview.php?IGrp=7");
		Elements tbody = doc.select(".main");
		for(Element currenttbody : tbody) {	
			Elements span= currenttbody.select("span");
			//System.out.println(span.text());
			for(Element currentspan : span) {	
				 tmp.clear();
				 Elements name = currentspan.select(".t");
				 Elements x = currentspan.select(".x");
				 tmp.add(0,name.text());

				 if(name.text().contains("Vulcan")) {
					 canAdd( tmp.size(),1,"sata");
					 canAdd( tmp.size(),3,"2.5");
				 }
				 else if(name.text().contains("M.2")) {
					 canAdd( tmp.size(),1,"m.2");
					 canAdd( tmp.size(),3,"2.5");
				 }
				 else if(name.text().contains("SATA")) {
					 canAdd( tmp.size(),1,"sata");
					 canAdd( tmp.size(),3,"2.5");
				 }
				 else if(name.text().contains("RGB"))
					 canAdd( tmp.size(),1,"sata");
				 else if(name.text().contains("PCIe")) {
					 canAdd( tmp.size(),1,"pcie");
					 canAdd( tmp.size(),3,"2.5");
				 }
				 
				 int i=1,dot=0;
				 double Inches=0;
				 int InchesIt=name.text().lastIndexOf("吋") ;
				 while(i<InchesIt-1) {
					 if(name.text().charAt(InchesIt-i)-'0' >= 0 && name.text().charAt(InchesIt-i)-'0' <= 9)
						 Inches+= (name.text().charAt(InchesIt-i)-'0')*Math.pow(10, i-1-(dot > 0 ? 1 : 0));
					 else if(name.text().charAt(InchesIt-i)-'.'==0)
						 dot=i;
					 else 
						 break;
					 i++;
				 }
				 Inches/=Math.pow(10, dot-1);
				 if(tmp.size()<4 || tmp.get(3).equals("NA"))
				 canAdd( tmp.size(),3,Double.toString(Inches));
				 
				 if(Inches==2.5 && tmp.get(1).equals("NA"))
						canAdd( tmp.size(),1,"sata");
				 pattern = Pattern.compile("([0-9]+)G");
				 m = pattern.matcher(name.text());
				 if (m.find( )) {
					 canAdd( tmp.size(),2,m.group());
				 }
				 else
				 {
					 pattern = Pattern.compile("([0-9]+)T");
					 m = pattern.matcher(name.text());
					 if (m.find( )) {
						 canAdd( tmp.size(),2,m.group());
					 }
				 }
				 
				 System.out.println(tmp);
				 obj.add(tmp);
			}
		 }
	}
}
