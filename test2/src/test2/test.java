package test2;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
 
import java.io.IOException;
import java.util.Iterator;
import java.util.ArrayList;

public class test {
	public static  ArrayList<ArrayList<String>> cpu= new ArrayList<ArrayList<String>>();
	public static  ArrayList<ArrayList<String>> gpu= new ArrayList<ArrayList<String>>();
	public static  ArrayList<ArrayList<String>> cases= new ArrayList<ArrayList<String>>();
	//public static  ArrayList Motherboards= new ArrayList();
	 public static void main(String[] args) throws IOException {

		 Document doc1=load("https://www.techpowerup.com/cpu-specs/");
		 loadcpu(doc1);
		 Document doc2=load("https://www.motherboarddb.com/");
		 //loadmotherboard(doc2);
		 Document doc3=load("https://www.techpowerup.com/gpu-specs/");
		 //loadGPU(doc3);
		 Document doc4=load("https://www.coolpc.com.tw/case.php");
		 //loadcase(doc4);
		 
	    }
	 private static void loadcase(Document doc4) {
		 Elements tbody = doc4.select("tbody");
		 for (int i=0;i<2;i++) {
			 

		 Element inner = tbody.first();
		 System.out.println(inner.select("td").get(0).select("td").html());
		 /*for (Element currentcenter : inner.select("td")) {
				//Elements td = currentcenter.select("td:lt(1)");
				ArrayList<String> tmp= new ArrayList<String>();
				tmp.add(currentcenter.text().indent(1));
					/*for (Element currenttd : td) {
						Elements tt = currenttd.select("td:lt(1)");
						if(!tt.text().isEmpty())
		        			tmp.add(tt.text().indent(1));
					}
				cases.add(tmp);
				System.out.println(tmp);
			}*/
		 }
	}
	private static void loadmotherboard(Document doc) {
			Elements col_md_6 = doc.select(".col-md-6");
			Elements row =col_md_6.select(".row");
			Elements table =row.select(".col-md-6");
			for (Element currenttable : table) {
				System.out.println(currenttable.text());
			}
		}
	private static void loadGPU(Document doc) {
		Elements table = doc.select(".processors > tbody > tr");
		for (Element currenttable : table) {
			Elements td = currenttable.select("td");
			ArrayList<String> tmp= new ArrayList<String>();
			for (Element currenttr : td) {
        			tmp.add(currenttr.text());
        	}
			gpu.add(tmp);
			System.out.println(tmp);
		}
		
	}

	private static Document load(String url) throws IOException {
		Document doc = Jsoup.connect(url).get();
		 String title = doc.title();
		 System.out.println("title is: " + title);
		 return doc;
		 
	}

	private static void loadcpu(Document doc) throws IOException {
	        Elements tablete = doc.select("table.processors");
	        Elements contents = tablete.select("tbody tr");
	        
	        for (Element currentcontents : contents) {
	        	Elements td = currentcontents.select("tr td");
	        	ArrayList<String> tmp= new ArrayList<String>();
	        	for (Element currenttr : td) {
	        		if(currenttr.text() !="")
	        	tmp.add(currenttr.text());
	        	}
	        	cpu.add(tmp);
	        	System.out.println(tmp);
	        }
	        System.out.println();

	 }
}