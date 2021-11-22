package autoupdate.old;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class IntelCPU extends Crawler {
	public Document doc1;
	public IntelCPU() throws IOException {
		super("https://ark.intel.com/content/www/tw/zh/ark/products/series/186673/9th-generation-intel-core-i9-processors.html");
		 Elements tr = doc.select(".blank-table-row");
		 Elements td = tr.select(".ark-product-name");
		 Elements links=doc.getElementsByTag("a");
		 
		 for(Element link:links){
			 //System.out.println(links.indexOf(link));
				String linkhref=link.attr("href");
				if(linkhref.startsWith("/content/www/tw/zh/ark/products/")){
					doc1 = Jsoup.connect("https://ark.intel.com/"+"linkhref").get();
					String data=doc1.html();
					String[] product=data.split("◆開箱討論");
					String[] it = null;
					if((product.length >=2 )){	
						//it=product[1].split("�N��");
						System.out.println(product[1]);
					}
						//System.out.println(linkhref);
						break;
				}
				
		}
		 /*System.out.println(td.html());
		 for (Element currenttd : td) {}
		*/	
	}
}
