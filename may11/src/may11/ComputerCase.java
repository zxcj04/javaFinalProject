//ok
package may11;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
 
import java.io.IOException;
import java.util.ArrayList;

public class ComputerCase extends Crawler {
	public ComputerCase() throws IOException {
		super("https://www.coolpc.com.tw/case.php");
			Element tbody = doc.select("tbody").first();
			String[] inf=tbody.text().split("◆開箱討論");
			for(int i=0;i<inf.length;i++) {	
				tmp.clear();
				String[] price= inf[i].split("含稅價：");
				String[] computerName = price[0].split("尺寸：");
				if(computerName.length>1) {
					tmp.add(0,computerName[0]);
					String[] tex=computerName[1].split(" ");
					tmp.add(1,tex[0]);
					for(int j=0;j< tex.length;j++){
						if(tex[j].equals("or") ) {
							if(tex[j-2].equals("or") )
								tex[j-3]=tex[j-3]+" or "+tex[j+1];
							else
								tex[j-1]=tex[j-1]+" or "+tex[j+1];
						}
					}
					for(int j=0;j< tex.length;j++) {
						// computerName size 支援水冷散熱排 硬碟空間 內附風扇  風扇支援
						String[] tmp1= tex[j].split("：");
						if(tmp1.length ==2){
							if(tmp1[0].equals("支援水冷散熱排")) 
								canAdd( tmp.size(),2,tmp1[1]);
							else if(tmp1[0].equals("硬碟空間") )
								canAdd( tmp.size(),3,tmp1[1]);
							else if(tmp1[0].equals("風扇支援")) 
								canAdd( tmp.size(),4,tmp1[1]);
							else if(tmp1[0].equals("內附風扇") )
								canAdd( tmp.size(),5,tmp1[1]);
							else if(tmp1[0].equals("前I/O") )
								canAdd( tmp.size(),6,tmp1[1]);
						}
					}	
				
				int size=tmp.size();
				while(tmp.size()<7)
				{
					tmp.add(size,"NA");
					size++;
				}
				System.out.println(tmp);
			}
		}
}

	private void canAdd(int size, int i,String data) {
		if(size <= i) {
			while(size < i) {
				tmp.add(size,"NA");
				size++;
			}
			tmp.add(i,data);
		}
		else {
			tmp.set(i,data);
		}
	}
}
