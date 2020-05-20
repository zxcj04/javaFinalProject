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
			String[] inf=tbody.text().split("���}�c�Q��");
			for(int i=0;i<inf.length;i++) {	
				tmp.clear();
				String[] price= inf[i].split("�t�|���G");
				String[] computerName = price[0].split("�ؤo�G");
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
						// computerName size �䴩���N������ �w�ЪŶ� ��������  �����䴩
						String[] tmp1= tex[j].split("�G");
						if(tmp1.length ==2){
							if(tmp1[0].equals("�䴩���N������")) 
								canAdd( tmp.size(),2,tmp1[1]);
							else if(tmp1[0].equals("�w�ЪŶ�") )
								canAdd( tmp.size(),3,tmp1[1]);
							else if(tmp1[0].equals("�����䴩")) 
								canAdd( tmp.size(),4,tmp1[1]);
							else if(tmp1[0].equals("��������") )
								canAdd( tmp.size(),5,tmp1[1]);
							else if(tmp1[0].equals("�eI/O") )
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
