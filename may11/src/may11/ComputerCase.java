//ok
package may11;

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
			String[] inf=tbody.text().split("���}�c�Q��");
			
			
			for(int i=0;i<inf.length;i++) {	
				tmp.clear();
				
				
				findstr("��d��\\d+(\\.\\d+)?",inf[i],2);
				String[] price= inf[i].split("�t�|���G");
				canAdd( tmp.size(),5,price[1]);
				
				String[] computerName = price[0].split("�ؤo�G");
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
					String[] tex=computerName[1].split(" ");
					
					for(int j=0;j< tex.length;j++) {
						// computerName size �䴩���N������ �w�ЪŶ� ��������  �����䴩
						String[] tmp1= tex[j].split("�G");
						if(tmp1.length ==2 && tmp1[0].equals("�w�ЪŶ�")){
								findstr("3.5\\*(\\d)+",tmp1[1],3);	
						}
					}
					
					findstr("CPU��\\d+(\\.\\d+)?",inf[i],4) ;
					
					
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
