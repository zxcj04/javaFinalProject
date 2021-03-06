package autoupdate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import mainlogic.Mb;

public class UpdateMb
{
    ArrayList<org.bson.Document> result;

    ExecutorService executor;

    private static final ArrayList<String> lg2 = new ArrayList<String>(
        Arrays.asList("z370", 
                      "h310", 
                      "b360", 
                      "b365", 
                      "h370", 
                      "q370", 
                      "z390"
        ));

    public UpdateMb() throws IOException
    {
        result = new ArrayList<org.bson.Document>();
        
        executor = Executors.newCachedThreadPool();

        getMbList();
    }

    public void getMbList() throws IOException
    {
        Document doc = Jsoup.connect("https://www.motherboarddb.com/motherboards/ajax/table/").get();

        Elements pageLinks = doc.select("a.page-link");

        int pages = Integer.parseInt(pageLinks.get(pageLinks.size() - 2).text());
        // int pages = 1;

        for(int i = 0 ; i < pages ; i++)
        {
            // executor.execute(new UpdateMbListWorker(this, i));

            getPageList(i);
        }

        executor.shutdown();

        while(!executor.isTerminated()); 
    }

    public void getPageList(int page) throws IOException
    {        
        Document doc = Jsoup.connect("https://www.motherboarddb.com/motherboards/ajax/table/?page=" + String.valueOf(page)).get();

        Elements links = doc.select("div.mb-1 a");

        for(Element link : links)
        {
            // System.out.println(link.absUrl("href"));

            // result.add(getInnerMessages(link.absUrl("href")));

            executor.execute(new UpdateMbWorker(link.absUrl("href"), this.result));
        }

        // result.add(getInnerMessages(links.get(0).absUrl("href")));
    }

    public static org.bson.Document getInnerMessages(String url)
    {
        org.bson.Document nowMb = initMbDocument();

        try
        {
        Document doc = Jsoup.connect(url).get();

        nowMb.append("name", doc.select("h1").get(0).text());

        // System.out.println(doc.select(".main-content").get(0).text());

        // for(Element e : doc.select("div.row :not(hr,img,script,div,ul)"))

        Elements eles = doc.select("div.row th,td");

        Matcher m;
        
        String tmp;
        int tmpi, tmpi2;

        m = findstr(doc.text(), "This board has ([0-9]+) slots with the following specifications:");

        if(m.find())
        {
            nowMb.append("ramQuantity", Integer.parseInt(m.group(1)));
        }
        else
        {
            nowMb.append("ramQuantity", 999);
        }

        upperData:
        for(int i = 0 ; i < eles.size() ; i++)
        {
            switch(eles.get(i).text())
            {
                case "Form Factor" :
                    i++;

                    switch(eles.get(i).select("a").text())
                    {
                        case "ATX" :
                            nowMb.append("size", "atx");

                            break;
                        
                        case "E-ATX" :
                            nowMb.append("size", "eatx");

                            break;
                        
                        case "Micro-ATX" :
                            nowMb.append("size", "matx");

                            break;

                        case "Mini-ITX" :
                            nowMb.append("size", "itx");

                            break;

                        default :
                            nowMb.append("size", "n/a");
                    }

                    // System.out.print("\t");

                    break;

                case "Socket(s)" :
                    i++;

                    if(eles.get(i).select("a").text().contains(" "))
                    {
                        m = findstr(eles.get(i).select("a").text(), " (.*?)$");

                        if(m.find())
                        {
                            nowMb.append("pin", m.group(1));
                        }
                        else
                        {
                            nowMb.append("pin", "n/a");
                        }
                    }
                    else
                    {
                        nowMb.append("pin", eles.get(i).select("a").text().toLowerCase());
                    }

                    if(m.group(1).equals("1151"))
                    {
                        m = findstr(nowMb.getString("name"), "(.*?) ([A-Z]([0-9]+))");

                        if(m.find() && lg2.indexOf(m.group(2).toLowerCase()) >= 0)
                        {
                            nowMb.append("pin", nowMb.getString("pin") + "-2");
                        }
                    }

                    // System.out.print("\t");

                    break;

                case "Slot Protocol" :
                    i++;

                    nowMb.append("ramType", eles.get(i).text().toLowerCase());

                    // System.out.print("\t");

                    break;

                case "Maximum Capacity" :
                    i++;

                    m = findstr(eles.get(i).text(), "([0-9]+) GB");

                    if(m.find())
                    {
                        nowMb.append("ramMaximum", Integer.parseInt(m.group(1)));
                    }
                    else
                    {
                        nowMb.append("ramMaximum", 999);
                    }

                    break;
            }

            // System.out.println(eles.get(i) + "\n");
        }

        eles = doc.select(".card-body h6,ul:not(.navbar-nav)");

        innerData:
        for(int i = 0 ; i < eles.size() ; i++)
        {
            switch(eles.get(i).text())
            {
                case "Wired" :
                    nowMb.append("RJ45", "1");

                    break;

                case "Wireless" :
                    nowMb.append("wifi", "1");

                    break;

                case "Video" :
                    i++;

                    tmp = "";

                    if(eles.get(i).select("ul").text().contains("DisplayPort"))
                    {
                        tmp += "DisplayPort/";
                    }
                    if(eles.get(i).select("ul").text().contains("HDMI"))
                    {
                        tmp += "HDMI/";
                    }
                    if(eles.get(i).select("ul").text().contains("VGA"))
                    {
                        tmp += "VGA/";
                    }
                    if(eles.get(i).select("ul").text().contains("DVI-D"))
                    {
                        tmp += "DVI-D/";
                    }

                    // if(!eles.get(i).select("ul").text().isEmpty())
                    if(!tmp.equals(""))
                    {
                        tmp = tmp.substring(0, tmp.length() - 1);
                    }

                    nowMb.append("graphicOutput", tmp);

                    break;

                case "USB" :
                    i++;

                    tmpi = 0;

                    // for(int j = 0; i < eles.get(i).select("li").size(); i++)
                    for(Element ele : eles.get(i).select("li"))
                    {
                        m = findstr(ele.text(), "([0-9]+)x USB");

                        if(m.find())
                        {
                            tmpi += Integer.parseInt(m.group(1));
                        }
                    }

                    nowMb.append("usbQuantity", tmpi);

                    break;

                case "Slots" :
                    i++;

                    tmpi = 0;

                    for(Element ele : eles.get(i).select("li"))
                    {
                        m = findstr(ele.text(), "([0-9]+)x (.*?) x16");

                        if(m.find())
                        {
                            tmpi += Integer.parseInt(m.group(1));
                        }
                    }

                    nowMb.append("pcieQuantity", tmpi);

                    break;

                case "Storage" :
                    i++;

                    tmpi = 0;

                    for(Element ele : eles.get(i).select("li"))
                    {
                        m = findstr(ele.text(), "([0-9]+)x SATA3");

                        if(m.find())
                        {
                            tmpi += Integer.parseInt(m.group(1));
                        }
                    }

                    nowMb.append("sata3Quantity", tmpi);

                    break;

                case "M.2 Connections" :
                    tmp = "";

                    tmpi = 0;
                    tmpi2 = 0;
                    
                    for(Element ele : eles.get(i).parent().select("td"))
                    {
                        if(tmpi2 != 3)
                        {
                            if(findstr(ele.text(),"(PCI-E|SATA3)(.*?)(SATA3)").find())
                            {
                                tmp = "pcie/sata";

                                tmpi++;
                                tmpi2 = 3;
                            }
                            else if(findstr(ele.text(),"(PCI-E)").find())
                            {
                                if(tmpi2 == 2)
                                {
                                    tmp = "pcie/sata";

                                    tmpi2 = 3;
                                }
                                else
                                {
                                    tmp = "pcie";

                                    tmpi2 = 1;
                                }

                                tmpi++;
                            }
                            else if(findstr(ele.text(),"(SATA3)").find())
                            {
                                if(tmpi2 == 1)
                                {
                                    tmp = "pcie/sata";

                                    tmpi2 = 3;
                                }
                                else
                                {
                                    tmp = "sata";

                                    tmpi2 = 2;
                                }

                                tmpi++;
                            }
                        }
                        else if(findstr(ele.text(),"(PCI-E|SATA3)").find())
                        {
                            tmpi++;
                        }
                    }

                    nowMb.append("m2Type", tmp);
                    nowMb.append("m2Quantity", tmpi);

                    break;
                    
            }

            // System.out.println(eles.get(i) + "\n");
        }

        System.out.println(nowMb);

        }
        catch(Exception e)
        {
            System.out.println(":error documenting...");
            e.printStackTrace();

            return null;
        }

        try
        {
            Mb.toObject(nowMb);
        }
        catch(Exception e)
        {
            System.out.println(":error toObjecting...");

            return null;
        }
        
        nowMb.remove("_id");

        return nowMb;
    }

    private static org.bson.Document initMbDocument()
    {
        org.bson.Document now = new org.bson.Document();

        now.append("name", "n/a").append("size", "n/a")
        .append("pin", "n/a").append("supportCpu", "n/a").append("RJ45", "n/a")
        .append("graphicOutput", "n/a").append("usbQuantity", 0)
        .append("pcieQuantity", 0).append("ramType", "n/a").append("ramQuantity", 0)
        .append("ramMaximum", 0).append("wifi", "0").append("sata3Quantity", 0)
        .append("m2Type", "n/a").append("m2Quantity", 0);

        return now;
    }

    private static Matcher findstr(String inp, String pattern) 
    {
		Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(inp);
        
        return m;
	}

    public ArrayList<org.bson.Document> getResult() 
    {
		return this.result;
	}
}