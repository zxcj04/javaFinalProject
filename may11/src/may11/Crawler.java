package may11;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class Crawler  {
	public  ArrayList<ArrayList<String>> obj= new ArrayList<ArrayList<String>>();
	public ArrayList<String> tmp= new ArrayList<String>();
	public Document doc;
	public Pattern pattern;
	public Matcher m ;
	public  Crawler(String url) throws IOException {
			 doc = Jsoup.connect(url).get();
			String title = doc.title();
			System.out.println("title is: " + title);
	}
	protected void canAdd(int size, int i,String data) {
		if(size <= i) {
			while(size <= i) {
				tmp.add(size,"NA");
				size++;
			}
		}
		tmp.set(i,data);
	}
}
