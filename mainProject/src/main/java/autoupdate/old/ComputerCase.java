//ok
package autoupdate.old;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
 
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ComputerCase extends Crawler {
	public ComputerCase() throws IOException {
		super("https://www.coolpc.com.tw/case.php");
			Element tbody = doc.select("tbody").first();
			String[] inf=tbody.text().split("◆開箱討論");
			
			
			for(int i=0;i<inf.length;i++) {	
				tmp.clear();
				//System.out.println(inf[i]);
				String[] computerName = inf[i].split("尺寸：");
				if(computerName[0].contains("E-ATX"))
					canAdd( tmp.size(),1,"eatx");
				else if(computerName[0].contains("M-ATX"))
					canAdd( tmp.size(),1,"matx");
				else if(computerName[0].contains("ATX"))
					canAdd( tmp.size(),1,"atx");
				else if(computerName[0].contains("ITX"))
					canAdd( tmp.size(),1,"itx");
				
				if(computerName.length>1) {
					canAdd( tmp.size(),0,computerName[0]); //name
					//String[] tex=computerName[1].split(" ");//42*21.3*44.5
					String[] gpu=computerName[0].split("顯卡長");
					if(gpu.length>1 && tmp.size()>1) 
						findstr("^\\d+(\\.\\d+)?",gpu[1],2);
						// computerName size 支援水冷散熱排 硬碟空間 內附風扇  風扇支援
					String[] hdSize=computerName[1].split("硬碟空間：");
					if(hdSize.length>1 && tmp.size()>2){
						String[] m3_5=hdSize[1].split("3.5");
						if(m3_5.length>1) {
						pattern = Pattern.compile("(\\d)+");
						m = pattern.matcher(m3_5[1]);
						if(m.find())
							canAdd( tmp.size(),5,m.group());
						}
					}
				
					String[] cpuHigh=computerName[0].split("CPU高");
					if(cpuHigh.length>1 && tmp.size()>3)
						findstr("^\\d+(\\.\\d+)?",cpuHigh[1],4) ;
					if(tmp.size()>3)
						obj.add(tmp);
				System.out.println(tmp);
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
