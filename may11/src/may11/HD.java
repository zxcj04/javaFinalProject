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

				 int i=1,dot=0;
				 double Inches=0;
				 int InchesIt=name.text().lastIndexOf("¦T") ;
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
					 
				 int mmIt=name.text().lastIndexOf("mm");
				 if(mmIt!=-1 && (name.text().charAt(mmIt-1)-'0' >= 0 && name.text().charAt(mmIt-1)-'0' <= 9))
					 canAdd( tmp.size(),3,Inches+"¦T"+name.text().charAt(mmIt-1)+"mm");
				 else 
					 canAdd( tmp.size(),3,Inches+"¦T"+"7mm");
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
				 
				 String[] money=x.text().split("NT")[1].split("¡»¶}½c°Q½×");
				 canAdd( tmp.size(),4,money[0]);
				 System.out.println(tmp);
				 obj.add(tmp);
			}
		 }
	}
}
