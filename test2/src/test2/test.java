package test2;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
 
import java.io.IOException;
import java.util.Iterator;
import java.util.ArrayList;

public class test {
	public static  ArrayList<ArrayList<String>> techpowerup= new ArrayList<ArrayList<String>>();
	public static  ArrayList<ArrayList<String>> techpowerup2= new ArrayList<ArrayList<String>>();
	//public static  ArrayList Motherboards= new ArrayList();
	 public static void main(String[] args) throws IOException {

		 Document doc1=load("https://www.techpowerup.com/cpu-specs/");
		 //loadtechpowerup(doc1);
		 Document doc2=load("https://www.motherboarddb.com/");
		 //loadmotherboard(doc2);
		 Document doc3=load("https://www.techpowerup.com/gpu-specs/");
		 //loadmotechpowerup(doc3);
	    }
	 private static void loadmotherboard(Document doc) {
			Elements col_md_6 = doc.select(".col-md-6");
			Elements row =col_md_6.select(".row");
			Elements table =row.select(".col-md-6");
			for (Element currenttable : table) {
				System.out.println(currenttable.text());
			}
		}
	private static void loadmotechpowerup(Document doc) {
		Elements list = doc.select("#list");
		Elements table = list.select("tr");
		for (Element currenttable : table) {
			Elements td = currenttable.select("td:lt(3)");
			ArrayList<String> tmp= new ArrayList<String>();
			for (Element currenttd : td) {
	        	tmp.add(currenttd.text());
	        	}
			System.out.println(tmp);
			techpowerup2.add(tmp);
		}
	}

	private static Document load(String url) throws IOException {
		Document doc = Jsoup.connect(url).get();
		 String title = doc.title();
		 System.out.println("title is: " + title);
		 return doc;
		 
	}

	private static void loadtechpowerup(Document doc) throws IOException {
	        Elements tablete = doc.select("table.processors");
	        Elements contents = tablete.select("tr");
	        
	        for (Element currentcontents : contents) {
	        	Elements td = currentcontents.select("tr td");
	        	ArrayList<String> tmp= new ArrayList<String>();
	        	for (Element currenttr : td) {
	        	tmp.add(currenttr.text());
	        	}
	        	techpowerup.add(tmp);
	        	System.out.println(tmp);
	        }
	        System.out.println();

	 }
}
