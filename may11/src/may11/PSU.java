//ok
package may11;
import java.io.IOException;
	import java.util.ArrayList;

	import org.jsoup.Jsoup;
	import org.jsoup.nodes.Document;
	import org.jsoup.nodes.Element;
	import org.jsoup.select.Elements;
public class PSU extends Crawler{
	public PSU() throws IOException {
		super("https://www.coolpc.com.tw/eachview.php?IGrp=15");
		Elements span = doc.select("span");
		for(Element currentspan : span) {
			 tmp.clear();
			 Elements tex=currentspan.select(".t");
			 String inf=tex.text();
			 Elements price=currentspan.select(".x");
			 String[] money=price.text().split("NT")[1].split("◆開箱討論");
			 int i=1,watt=0,awg=0;
			 tmp.add(0,inf);
			 int wattIt=inf.lastIndexOf("0W");
			 while(i<wattIt-1) {
				 if(inf.charAt(wattIt-i)-'0' >= 0 && inf.charAt(wattIt-i)-'0' <= 9)
					 watt+= (inf.charAt(wattIt-i)-'0')*Math.pow(10, i-1);
				 else
					 break;
				 i++;
			 }
				 tmp.add(1,watt+"0W");
			int awgIt=inf.lastIndexOf("AWG"); 
			i=1;
			while(i<awgIt-1) {
				 if(inf.charAt(awgIt-i)-'0' >= 0 && inf.charAt(awgIt-i)-'0' <= 9)
					 awg+= (inf.charAt(awgIt-i)-'0')*Math.pow(10, i-1);
				 else
					 break;
				 i++;
			 }
			tmp.add(2,awg+"AWG");
			Elements div=currentspan.select("div");
			String[] eletricLon =div.get(1).text().split("電源長度：");
			if(!(eletricLon.length > 1 ))
				eletricLon=div.get(2).text().split("電源長度：");
			if(eletricLon.length > 1)
				tmp.add(3,eletricLon[1]); 
			else
				tmp.add(3,"NA");
			
			tmp.add(4,money[0]); 
			obj.add(tmp);
			System.out.println(tmp);
			//Name WATT AWG 電源長度  Money
		}
	}
}

