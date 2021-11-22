//ok
package autoupdate.old;

import java.io.IOException;
import java.util.regex.Pattern;

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
				 if(name.text().contains("*D4")) {
					 canAdd( tmp.size(),9,"ddr4");
					 canAdd( tmp.size(),10,"8");
				 }
				 for(Element currentdiv : div) {
					if(currentdiv.text().contains("E-ATX"))
						canAdd( tmp.size(),1,"eatx");
					else if(currentdiv.text().contains("M-ATX"))
						canAdd( tmp.size(),1,"matx");
					else if(currentdiv.text().contains("ATX"))
						canAdd( tmp.size(),1,"atx");
					else if(currentdiv.text().contains("XL-ATX"))
						canAdd( tmp.size(),1,"xlatx");
					else if(currentdiv.text().contains("mini-ITX"))
						canAdd( tmp.size(),1,"miniitx");
					else if(currentdiv.text().contains("ITX"))
						canAdd( tmp.size(),1,"itx");
					
					
					if(currentdiv.text().contains("1151"))
						findstr(currentdiv.text(),"1151 \\s",2);
					else if(currentdiv.text().contains("1150"))
						findstr(currentdiv.text(),"1150 \\s",2);
					else if(currentdiv.text().contains("1200"))
						findstr(currentdiv.text(),"1200 \\s",2);
					else if(currentdiv.text().contains("AM4"))
						canAdd( tmp.size(),2,"am4");
					else if(currentdiv.text().contains("2066"))
						canAdd( tmp.size(),2,"2066");
					else if(currentdiv.text().split("CPU：").length >1) 
						canAdd( tmp.size(),2,currentdiv.text().split("CPU：")[1]);//cpu
					if(currentdiv.text().split("CPU：").length >1) 
						canAdd( tmp.size(),3,currentdiv.text().split("CPU：")[1]);
					if(currentdiv.text().split("網路：").length >1) {
						if(currentdiv.text().split("網路：")[1].contains("n"))
							canAdd(tmp.size(),12,"n");
						else if(currentdiv.text().split("網路：")[1].contains("ac"))
							canAdd(tmp.size(),12,"ac");
						else if(currentdiv.text().split("網路：")[1].contains("b"))
							canAdd(tmp.size(),12,"b");
						else if(currentdiv.text().split("網路：")[1].contains("g"))
							canAdd(tmp.size(),12,"g");
						else if(currentdiv.text().split("網路：")[1].contains("a"))
							canAdd(tmp.size(),12,"a");
						
					canAdd( tmp.size(),4,currentdiv.text().split("網路：")[1]);//網路孔
					}
					
					if(currentdiv.text().split("顯示：").length >1) {//輸出
						canAdd( tmp.size(),5,currentdiv.text().split("顯示：")[1]);//hdmi
					 }
					if(currentdiv.text().contains("USB")) {
						pattern = Pattern.compile("\\d+\\*USB");
						m = pattern.matcher(currentdiv.text());
						int count=0;
						while(m.find()) {
							count+=m.group().charAt(0)-'0';
							}
						if(!m.find()){
							pattern = Pattern.compile("USB");
							m = pattern.matcher(currentdiv.text());
							while(m.find()) 
									count++;
						}
						canAdd( tmp.size(),7, Integer.toString(count));
					 }
					
					if(currentdiv.text().split("儲存：").length >1) {//輸出
						canAdd( tmp.size(),8,currentdiv.text().split("儲存：")[1]);//usb
						pattern = Pattern.compile("\\d?\\+?\\d\\*SATA3");
						m = pattern.matcher(currentdiv.text().split("儲存：")[1]);
						if(m.find()) {
							int sataCount=0;
							sataCount=m.group().charAt(0)-'0';
							
							if(m.group().contains("+"))
								sataCount+=m.group().charAt(2)-'0';
							canAdd( tmp.size(),13,Integer.toString(sataCount));
						}
						pattern = Pattern.compile("\\d\\*M\\.2");
						m = pattern.matcher(currentdiv.text().split("儲存：")[1]);
						if(m.find()) {
							int m2=0;
							m2=m.group().charAt(0)-'0';
							canAdd( tmp.size(),15,Integer.toString(m2));
						}
					}
					else if(currentdiv.text().split("儲存空間：").length >1) {//輸出
						canAdd( tmp.size(),8,currentdiv.text().split("儲存空間：")[1]);//usb
					}
					if(currentdiv.text().split("記憶體：").length >1) {//輸出
						if(currentdiv.text().split("記憶體：")[1].contains("DDR4"))//usb
							canAdd( tmp.size(),9,"ddr4");
						else if(currentdiv.text().split("記憶體：")[1].contains("DDR3"))//usb
							canAdd( tmp.size(),9,"ddr3");
						if(currentdiv.text().split("記憶體：")[1].contains("雙通道"))
							canAdd( tmp.size(),10,"2");
						else
							canAdd( tmp.size(),10,"2");
						if(tmp.get(9).equals("NA"))
							canAdd( tmp.size(),9,"ddr4");
						findstr("\\d+GB",currentdiv.text().split("記憶體：")[1],11);
					}
				
					//}	 
				 }	
				 
				 if(tmp.size()> 3 && tmp.get(2)!="NA") {
					 System.out.println(tmp);
					 obj.add(tmp);
				 }
			}
		 }

	}
	private void findstr(String tocut,String cut,int it) {
		pattern = Pattern.compile(tocut);
		m = pattern.matcher(cut);
		if(m.find()) {
			//System.out.println(m.group());
			canAdd( tmp.size(),it,m.group());
		}
		
	}
}
