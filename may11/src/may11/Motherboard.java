//ok
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
				 Elements div = currentspan.select("div");
				 Elements name = currentspan.select(".t");
				 Elements x = currentspan.select(".x");
				 tmp.add(0,name.text());//cpu
				 for(Element currentdiv : div) {
					 
					if(currentdiv.text().split("CPU：").length >1) {
						 tmp.add(1,currentdiv.text().split("CPU：")[1]);//cpu
					}
					//else if(tmp.size() > 2) {
						if(currentdiv.text().split("尺寸：").length >1) {
							canAdd( tmp.size(),2,currentdiv.text().split("尺寸：")[1]);//大小
						}
						if(currentdiv.text().split("網路：").length >1) {
							canAdd( tmp.size(),3,currentdiv.text().split("網路：")[1]);//網路孔
						 }
						if(currentdiv.text().split("內建：").length >1) {
							canAdd( tmp.size(),4,currentdiv.text().split("內建：")[1]);//usb
						 }
						if(currentdiv.text().split("顯示：").length >1) {//輸出
							canAdd( tmp.size(),5,currentdiv.text().split("顯示：")[1]);//usb
						 }
						if(currentdiv.text().split("儲存：").length >1) {//輸出
							canAdd( tmp.size(),6,currentdiv.text().split("儲存：")[1]);//usb
						}
						else if(currentdiv.text().split("儲存空間：").length >1) {//輸出
							canAdd( tmp.size(),6,currentdiv.text().split("儲存空間：")[1]);//usb
						}
					//}	 
				 }	
				 if(tmp.size() > 3) {
					 String[] money=x.text().split("NT")[1].split("◆開箱討論");
					 tmp.add(money[0]);
					 System.out.println(tmp);
					 obj.add(tmp);
				 }
			}
		 }

	}
}
