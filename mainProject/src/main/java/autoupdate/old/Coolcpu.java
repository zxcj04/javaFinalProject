package autoupdate.old;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
 
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Pattern;


public class Coolcpu extends Crawler {

	public Coolcpu() throws IOException {
		super("https://www.coolpc.com.tw/AMD.htm");
		 Elements table = doc.select("table");
		 for(Element currenttable : table) {
			 Elements MsoNormalTable = currenttable.select(".MsoNormalTable");
			 for(Element currentMsoNormalTable : MsoNormalTable) { 
				 Elements tr = currentMsoNormalTable.select("tr");
				 for(Element currenttr : tr) {	 
					 tmp.clear();
					 //System.out.println(currenttr.text());
					 Elements td = currenttr.select("td");
					 canAdd( tmp.size(),0,td.get(0).text()); 
					 for (int i=4;i<=5 && td.size()>5;i++) {
						 if(td.get(i).text().contains("2066"))
							 findstr("2066",td.get(i).text(),1);
						 else if(td.get(i).text().contains("1151"))
							 findstr("1151",td.get(i).text(),1);
						 else if(td.get(i).text().contains("2011-V3"))
							 findstr("2011-V3",td.get(i).text(),1);
						 else if(td.get(i).text().contains("2011"))
							 findstr("2011",td.get(i).text(),1);
						 else if(td.get(i).text().contains("1150"))
							 findstr("1150",td.get(i).text(),1);
						 else if(td.get(i).text().contains("1155"))
							 findstr("1155",td.get(i).text(),1);
						 else if(td.get(i).text().contains("1156"))
							 findstr("1156",td.get(i).text(),1);
						 else if(td.get(i).text().contains("775"))
							 findstr("775",td.get(i).text(),1);
						 else if(td.get(i).text().contains("771"))
							 findstr("771",td.get(i).text(),1);
						 else if(td.get(i).text().contains("TR4"))
							 findstr("TR4",td.get(i).text(),1);
						 else if(td.get(i).text().contains("AM4"))
							 findstr("AM4",td.get(i).text(),1);
						 else if(td.get(i).text().contains("FM2+"))
							 findstr("(FM2\\+)",td.get(i).text(),1);
						 else if(td.get(i).text().contains("AM3+"))
							 findstr("(AM3\\+)",td.get(i).text(),1);
						 else if(td.get(i).text().contains("FM1"))
							 findstr("FM1",td.get(i).text(),1);
						 else if(td.get(i).text().contains("TF1"))
							 findstr("TF1",td.get(i).text(),1);
						 else if(td.get(i).text().contains("AM3"))
							 findstr("AM3",td.get(i).text(),1);
						 else if(td.get(i).text().contains("AM2+"))
							 findstr("(AM2\\+)",td.get(i).text(),1);
						 else if(td.get(i).text().contains("AM2"))
							 findstr("AM2",td.get(i).text(),1);
						 else if(td.get(i).text().contains("939"))
							 findstr("939",td.get(i).text(),1);
						 else if(td.get(i).text().contains("754"))
							 findstr("754",td.get(i).text(),1);
					 }
					 findstr("\\d+C",td.text(),2);
					 findstr("\\d+T",td.text(),3);
					 
					 if(td.size() > 2 && td.get(2).text().contains("G")) 
					  findstr("\\d+(\\.\\d+)?G",td.get(2).text(),4);
					 
					 if(td.size() > 2 && td.get(2).text().contains("G)*")) 
						 findstr("\\d+(\\.\\d+)?G",td.get(2).text().split("G")[1],5);
					 else if(td.size() > 3 && td.get(3).text().contains("G"))
						 findstr("\\d+(\\.\\d+)?G",td.get(3).text(),5);
					 
					 if(td.size() > 14 && td.get(14).text().contains("DDR") )
						 canAdd( tmp.size(),7,td.get(14).text());
					 else if(td.size() > 13 && td.get(13).text().contains("DDR") )
						 canAdd( tmp.size(),7,td.get(13).text());
					 else if(td.size() > 12 && td.get(12).text().contains("DDR") )
						 canAdd( tmp.size(),7,td.get(12).text());
					 
					 for (int i=3;i<=4 && td.size()>4;i++) {
						 if( td.get(i).text().contains("HD") )//HD Graphics (350/1150MHz)
							 findstr("[A-Z]*HD( )?[A-Za-z]*(\\d)*?( )?\\(\\d+\\/\\d+MHz\\)|HD Graphics",td.get(i).text(),8);	 
						 else if( td.get(i).text().contains("NO") )
							 canAdd( tmp.size(),8,"NO"); 
						 else if( td.get(i).text().contains("No") )
							 canAdd( tmp.size(),8,"NO");
						 else if( td.get(i).text().contains("no") )
							 canAdd( tmp.size(),8,"NO");
					 }					 
					 if( td.size() > 12 && td.get(12).text().contains("W") )
						 findstr("\\d+",td.get(12).text(),9);
					 else if( td.size() > 14 && td.get(14).text().contains("W") )
						 findstr("\\d+",td.get(14).text(),9);
					 else if( td.size() > 13 && td.get(13).text().contains("W") ) {
						 if( td.get(13).text().contains("\\") )
							 findstr("\\d+",td.get(13).text().split("\\")[1],9);
						 else
							 findstr("\\d+",td.get(13).text(),9);
					 }

				     if(tmp.size()>2 && !tmp.get(1).equals("NA")){	
				    	 obj.add(tmp);
				     System.out.println(tmp);
				    }
				}
			}
			 System.out.println("------------------------------");
		// System.out.println(obj);
		 }
	}
	private void findstr(String tocut,String cut,int it) {
		pattern = Pattern.compile(tocut);
		m = pattern.matcher(cut);
		if(m.find()) {
			//System.out.println(m.group());
			canAdd( tmp.size(),it,m.group());
		}
		
	}
}
