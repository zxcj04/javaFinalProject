package may11;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.util.ArrayList;

public abstract class Crawler  {
	public  ArrayList<ArrayList<String>> obj= new ArrayList<ArrayList<String>>();
	public ArrayList<String> tmp= new ArrayList<String>();
	public Document doc;
	public  Crawler(String url) throws IOException {
			 doc = Jsoup.connect(url).get();
			String title = doc.title();
			System.out.println("title is: " + title);
	}
}
