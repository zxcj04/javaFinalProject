//ok
package may11;

import java.io.IOException;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class GPU extends Crawler{
	public GPU() throws IOException {
		super("https://www.techpowerup.com/gpu-specs/");
		Elements table = doc.select(".table-wrapper").select("tbody");
		Elements tr = table.select("tr");
		for(Element currenttr : tr) {
			tmp.clear();
			Elements td= currenttr.select("td");
			for(Element currenttd : td )
				tmp.add(currenttd.text());
			obj.add(tmp);
			System.out.println(tmp);
		}
		//System.out.println(obj);
		//Product Name	GPU Chip	Released	Bus	Memory	GPU clock	Memory clock	Shaders / TMUs / ROPs

	}
}
