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
					 
					if(currentdiv.text().split("CPU�G").length >1) {
						 tmp.add(1,currentdiv.text().split("CPU�G")[1]);//cpu
					}
					//else if(tmp.size() > 2) {
						if(currentdiv.text().split("�ؤo�G").length >1) {
							canAdd( tmp.size(),2,currentdiv.text().split("�ؤo�G")[1]);//�j�p
						}
						if(currentdiv.text().split("�����G").length >1) {
							canAdd( tmp.size(),3,currentdiv.text().split("�����G")[1]);//������
						 }
						if(currentdiv.text().split("���ءG").length >1) {
							canAdd( tmp.size(),4,currentdiv.text().split("���ءG")[1]);//usb
						 }
						if(currentdiv.text().split("��ܡG").length >1) {//��X
							canAdd( tmp.size(),5,currentdiv.text().split("��ܡG")[1]);//usb
						 }
						if(currentdiv.text().split("�x�s�G").length >1) {//��X
							canAdd( tmp.size(),6,currentdiv.text().split("�x�s�G")[1]);//usb
						}
						else if(currentdiv.text().split("�x�s�Ŷ��G").length >1) {//��X
							canAdd( tmp.size(),6,currentdiv.text().split("�x�s�Ŷ��G")[1]);//usb
						}
					//}	 
				 }	
				 if(tmp.size() > 3) {
					 String[] money=x.text().split("NT")[1].split("���}�c�Q��");
					 tmp.add(money[0]);
					 System.out.println(tmp);
					 obj.add(tmp);
				 }
			}
		 }

	}
}
