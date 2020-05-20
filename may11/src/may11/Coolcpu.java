package may11;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
 
import java.io.IOException;
import java.util.ArrayList;


public class Coolcpu extends Crawler {

	public Coolcpu() throws IOException {
		super("https://www.coolpc.com.tw/home/cpu/coolcpu.html");
		 Elements tbody = doc.select("#sortable2");

			ArrayList<String> tmp= new ArrayList<String>();
			/*String[] name= {"�W��","�}��","�֤߼�","�u�{��","��¦�W�v","boost�W�v","�̤j�O����j�p","�O��������(DDR)","TDP"};
			for(int i=0;i<name.length;i++) {
				tmp.add(name[i]);
			}
			obj.add(tmp);
			System.out.println(obj);*/
		 for (Element currenttbody : tbody) {
				Elements li = currenttbody.select("li");
				for (Element currentli : li) {
					tmp.clear();
					String []tokens= currentli.text().split("\\|");
					tmp.add(0, tokens[0]);//
					tmp.add(1, tokens[tokens.length-1]);//
					String[] tok=tokens[2].split("C");
					tmp.add(2, tok[0]);//�֤߼�(C )
					tmp.add(3, tok[1]);//�u�{��(T)
					tmp.add(4, tokens[1]);//��¦�W�v
					tmp.add(5, "0");//boost�W�v
					tmp.add(6, "0");//�̤j�O����j�p
					tmp.add(7, "0");//�O��������(DDR)
					tmp.add(8, "0");//TDP*/
					//System.out.println(currentli.text());
					//System.out.println(tmp);
					//obj.add(tmp);
					System.out.println("------------------------------");
	        	}
				
			}
		 System.out.println(obj);
	}
	
}
