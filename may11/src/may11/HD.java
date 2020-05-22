package may11;


import java.io.IOException;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class HD extends Crawler{
	public HD() throws IOException {
		super("https://www.coolpc.com.tw/eachview.php?IGrp=7");
		Elements tbody = doc.select(".main");
		for(Element currenttbody : tbody) {	
			Elements span= currenttbody.select("span");
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
					 tmp.add(1,Inches+"¦T");
					 
				 int mmIt=name.text().lastIndexOf("mm");
				 if(mmIt!=-1 && (name.text().charAt(mmIt-1)-'0' >= 0 && name.text().charAt(mmIt-1)-'0' <= 9))
					 tmp.add(2,name.text().charAt(mmIt-1)+"mm");
				 else 
					 tmp.add(2,"7mm");
				 

				 int gbIt=(name.text().lastIndexOf("G") > 0 ? name.text().lastIndexOf("G"):name.text().lastIndexOf("GB"));
				 int gb=0;
				 i=1;
				 if(gbIt > 0 ) {
					 while(i<gbIt-1) {
						 if(name.text().charAt(gbIt-i)-'0' >= 0 && name.text().charAt(gbIt-i)-'0' <= 9)
							 gb+= (name.text().charAt(gbIt-i)-'0')*Math.pow(10, i-1);
						 else
							 break;
						 i++;
					 }
						 tmp.add(3,gb+"G");
				 }
				 else
				 {
					 int TIt=(name.text().lastIndexOf("T")> 0 ? name.text().lastIndexOf("T"):name.text().lastIndexOf("TB")); 
					 int T=0;
					 i=1;
					 while(i<TIt-1) {
						 if(name.text().charAt(TIt-i)-'0' >= 0 && name.text().charAt(TIt-i)-'0' <= 9)
							 T+= (name.text().charAt(TIt-i)-'0')*Math.pow(10, i-1);
						 else
							 break;
						 i++;
					 }
						 tmp.add(3,T+"T");
				 }
				 String[] money=x.text().split("NT")[1].split("¡»¶}½c°Q½×");
				 tmp.add(money[0]);
				 System.out.println(tmp);
				 obj.add(tmp);
			}
		 }
	}
}
