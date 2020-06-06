package autoupdate;

import org.bson.Document;

import org.jsoup.Jsoup;
//import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import mainlogic.Crate;

import java.io.IOException;
import java.util.Iterator;

import java.util.ArrayList;
import java.util.regex.*;

public class UpdateCase {
    private String url;
	private ArrayList<Document> info;
	private Document nowCase;

    public UpdateCase(){
        info = new  ArrayList<Document>();

        url = "https://www.coolpc.com.tw/case.php";

        try {
        	org.jsoup.nodes.Document doc = Jsoup.connect(url).get();
        	
//        	org.jsoup.nodes.Document doc = Jsoup.parse("<table> <tr> <td> name <table class = \"Tbl\"> <td> first </td> <td> 硬碟空間:3.5*2 </td> </table> </td> </tr> </table>");
            // Document doc = Jsoup.parse("<table> <tr> <td> test <table class=\"Tbl\"><tr><td>QAQ</td></tr></table></td></tr></table>");
            // doc.select("table.Tbl").remove();
            // Elements tt = doc.select("table");
            // for(Element ele : tt)
            // {
            //     System.out.println(ele);
            //     System.out.println();
            // }
            Elements tds = doc.select("td");

            for(Element td : tds){
            	Elements tbls = td.select("table.Tbl");
            	for(Element tbl : tbls) {
            		Elements tblTds = tbl.select("td");
            		
            		if(tblTds.isEmpty()) {
            			break;
            		}
            		
            		
            		nowCase = new Document();
            		
            		matchDisk(tbl.text());
            		
            		td.select("table.Tbl").remove();
            		
            		String name = td.text();
            		int slash = name.indexOf("/");
					int nameEnd = (slash == -1)?name.length():slash;

            		int plus = name.indexOf("+");
					nameEnd = (plus == -1 || plus > nameEnd)?nameEnd:plus;
            			
            		String mbMatch = matchMbSize(td.text());
            		if(!mbMatch.equals("")) {
            			nameEnd = Math.min(nameEnd, name.indexOf(mbMatch));
            		}
            		
            		String vgaMatch= matchVgaLength(td.text());
            		if(!vgaMatch.equals("")) {
            			nameEnd = Math.min(nameEnd, name.indexOf(vgaMatch));
            		}
            		
            		String psuMatch = matchPsuSize(td.text());
            		if(!psuMatch.equals("")) {
            			nameEnd = Math.min(nameEnd, name.indexOf(psuMatch));
            		}
            		
            		String coolerMatch = matchCoolerHeight(td.text());
            		if(!coolerMatch.equals("")) {
            			nameEnd = Math.min(nameEnd, name.indexOf(coolerMatch));
					}
					
					nowCase.append("psuLength", 999);
            		
            		name = name.substring(0, nameEnd);
            		
					nowCase.append("name", name);

					// System.out.println(nowCase);
					
					try
					{
						Crate.toObject(nowCase);

						info.add(nowCase);
					}
					catch(Exception e)
					{
						e.printStackTrace();
					}
            	}
            }
        }
        catch(Exception e){
            e.printStackTrace();;
        }
    }
    
    public void matchDisk(String inp) {
    	Matcher disk = findstr(inp, "硬碟空間(.*?)3.5[*]([0-9])");
		if(disk.find()){
            nowCase.append("diskQuantity", Integer.parseInt(disk.group(2)));
        }
		
		else {
			nowCase.append("diskQuantity", 999);
		}
    }
    
    public String matchMbSize(String inp) {
    	Matcher mb = findstr(inp, "EATX|E-ATX|ATX|MATX|M-ATX|ITX|MITX|M-ITX");
    	
    	String wholeString;
    	
    	if(mb.find()) {
			if(mb.group(0).toLowerCase().equals("e-atx")) {
				nowCase.append("mbSize", "eatx");
			}
			else if(mb.group(0).toLowerCase().equals("m-atx")) {
				nowCase.append("mbSize", "matx");
			}
			else if(mb.group(0).toLowerCase().equals("m-itx")) {
				nowCase.append("mbSize", "mitx");
			}
			else {
				nowCase.append("mbSize", mb.group(0).toLowerCase());
			}
            
            wholeString = mb.group(0);
    	}
    	else {
    		nowCase.append("mbSize", "n/a");
    		
    		wholeString = "";
    	}
		
		return wholeString;
    	
    }
    
    public String matchVgaLength(String inp) {
    	Matcher vga = findstr(inp, "顯(.*?)([0-9]+)");
    	
    	String wholeString;
		
		if(vga.find()) {
			nowCase.append("vgaLength", Integer.parseInt(vga.group(2)));
			
			wholeString = vga.group(0);
		}
		
		else {
			nowCase.append("vgaLength", 999);
			
			wholeString =  "";
		}
		
		return wholeString;
		
    }
    
    public String matchPsuSize(String inp) {
    	Matcher psu = findstr(inp, "SFX");
    	
    	String wholeString;
    	
    	if(psu.find()) {
			nowCase.append("psuSize", psu.group(0).toLowerCase());
			
			wholeString = psu.group(0);
    	}
    	
    	else {
    		nowCase.append("psuSize", "atx");
			
			wholeString = "";
    	}
    	
    	return wholeString;
    }
    
    public String matchCoolerHeight(String inp) {
    	Matcher cooler = findstr(inp, "(CP)?U高([0-9]+([.][0-9])?)");
    	
    	String wholeString;
    	
    	if(cooler.find()) {
    		double coolerHeight = Double.parseDouble(cooler.group(2));
    		coolerHeight = Math.ceil(coolerHeight);
    		
			nowCase.append("coolerHeight", ((Double)coolerHeight).intValue());
			
			wholeString = cooler.group(0);
    	}
    	
    	else {
    		nowCase.append("coolerHeight", 999);
			
			wholeString = "";
    	}
    	
    	return wholeString;
    }

    public ArrayList<Document> getInfo(){
        return info;
    }
    
    public Matcher findstr(String inp, String pattern) {
    	Pattern p = Pattern.compile(pattern);
    	Matcher m = p.matcher(inp);
    	return m;
    }
}