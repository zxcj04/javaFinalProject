package may11;

import java.io.IOException;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class IntelCPU extends Crawler {
	public IntelCPU() throws IOException {
		super("https://ark.intel.com/content/www/us/en/ark/compare.html?productIds=192990,192987,191044,192943,190887,186605,191789,202329,201897,201837,196448,203906,199314,199325,199335,199318,203908,199316,196597,197120,196593,196449,196452");
		 Elements tb = doc.select("tb");
		 /*for (Element currenttb : tb) {
			 System.out.println(currenttb.text());
		 }*/
		
	}
}
