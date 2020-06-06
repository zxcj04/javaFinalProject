package autoupdate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element; 
import org.jsoup.select.Elements;

import mainlogic.Cpu;

public class UpdateCpu
{
    public static ArrayList<org.bson.Document> getCpuList() throws IOException 
    {
        Document doc = Jsoup.connect("https://www.coolpc.com.tw/AMD.htm").get();

        Elements tables = doc.select("table tbody");

        ArrayList<org.bson.Document> result = new ArrayList<org.bson.Document>();

        result.addAll(getIntelCpuList(tables.get(2)));
        result.addAll(getAmdCpuList(tables.get(6)));        
        
        return result;
    }

    // {{0, 2, 3, 4, 9, 12, 13}, {0, 2, 3, 4, 10, 13, 14}}

    private static ArrayList<org.bson.Document> getAmdCpuList(Element table) 
    {
        ArrayList<org.bson.Document> result = new ArrayList<org.bson.Document>();

        Elements trs = table.select("tr");

        // ArrayList<Integer> which = new ArrayList<Integer>(Arrays.asList(0, 2, 3, 4, 10, 13, 14));

        for(int j = 2 ; j < trs.size() ; j++)
        {
            try
            {
                org.bson.Document nowCpu = new org.bson.Document();

                Elements tds = trs.get(j).select("td");
                
                Matcher m;

                for(int i = 0 ; i < tds.size() ; i++)
                {
                    switch(i)
                    {
                        case 0 :
                            nowCpu.append("name", tds.get(i).text());
                            break;

                        case 2 :
                            m = findstr(tds.get(i).text(), "([0-9]+\\.[0-9]+)GHz(\\(([0-9]+\\.[0-9]+)GHz\\))?");

                            if(m.find())
                            {
                                nowCpu.append("frequency", m.group(1));

                                try
                                {
                                    if(m.group(3) == null)
                                    {
                                        nowCpu.append("turboBoost", "0");
                                    }
                                    else
                                    {
                                        nowCpu.append("turboBoost", m.group(3));
                                    }
                                }
                                catch(Exception e)
                                {
                                    nowCpu.append("turboBoost", "0");
                                }
                            }
                            else
                            {
                                nowCpu.append("frequency", "0");
                                nowCpu.append("turboBoost", "0");
                            }
                        
                        case 3 :
                            m = findstr(tds.get(i).text(), "([0-9]+)C\\/([0-9]+)T");

                            if(m.find())
                            {
                                nowCpu.append("cores", m.group(1));
                                nowCpu.append("threads", m.group(2));
                            }
                            else
                            {
                                nowCpu.append("cores", "0");
                                nowCpu.append("threads", "0");
                            }

                            break;

                        case 4 :
                            nowCpu.append("pin", tds.get(i).text().toLowerCase());

                            break;

                        case 10 :
                            if(tds.get(i).text().contains("NO"))
                            {
                                nowCpu.append("internalGraphic", "n/a");
                            }
                            else
                            {
                                nowCpu.append("internalGraphic", tds.get(i).text());
                            }

                            break;
                            
                        case 13 :
                            m = findstr(tds.get(i).text(), "DDR([0-9]{0,1})");

                            if(m.find())
                            {
                                nowCpu.append("ramGenerationSupport", m.group(0).toLowerCase());                               
                            }
                            else
                            {
                                nowCpu.append("ramGenerationSupport", "n/a");
                            }
                        
                            break;

                        case 14 :
                            m = findstr(tds.get(i).text(), "([0-9]+)W");

                            if(m.find())
                            {
                                nowCpu.append("TDP", Integer.valueOf(m.group(1)));
                            }
                            else
                            {
                                nowCpu.append("TDP", 0);
                            }

                            break;
                    }

                }

                nowCpu.append("ramMaximumSupport", 256);

                if(nowCpu.getString("name").length() > 5)
                {
                    try
                    {
                        Cpu.toObject(nowCpu);

                        result.add(nowCpu);
                    }
                    catch(Exception e)
                    {
                        e.printStackTrace();
                    }
                    
                    System.out.println(nowCpu.toString());
                }
                else
                {
                    System.out.println("error: " + nowCpu.toString());
                }
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }

        return result;
    }

    private static ArrayList<org.bson.Document> getIntelCpuList(Element table) 
    {
        ArrayList<org.bson.Document> result = new ArrayList<org.bson.Document>();

        Elements trs = table.select("tr");

        // ArrayList<Integer> which = new ArrayList<Integer>(Arrays.asList(0, 2, 3, 4, 10, 13, 14));

        for(int j = 2 ; j < trs.size() ; j++)
        {
            try
            {
                org.bson.Document nowCpu = new org.bson.Document();

                Elements tds = trs.get(j).select("td");

                Matcher m;

                for(int i = 0 ; i < tds.size() ; i++)
                {
                    switch(i)
                    {
                        case 0 :
                            nowCpu.append("name", tds.get(i).text());
                            break;

                        case 2 :
                            m = findstr(tds.get(i).text(), "([0-9]+\\.[0-9]+)G(\\(↑?([0-9]+\\.[0-9]+)G\\))?");

                            if(m.find())
                            {
                                nowCpu.append("frequency", m.group(1));

                                try
                                {
                                    if(m.group(3) == null)
                                    {
                                        nowCpu.append("turboBoost", "0");
                                    }
                                    else
                                    {
                                        nowCpu.append("turboBoost", m.group(3));
                                    }
                                }
                                catch(Exception e)
                                {
                                    nowCpu.append("turboBoost", "0");
                                }
                            }
                            else
                            {
                                nowCpu.append("frequency", "0");
                                nowCpu.append("turboBoost", "0");
                            }
                        
                        case 9 :
                            m = findstr(tds.get(i).text(), "([0-9]+)C \\/ ([0-9]+)T");

                            if(m.find())
                            {
                                nowCpu.append("cores", m.group(1));
                                nowCpu.append("threads", m.group(2));
                            }
                            else
                            {
                                nowCpu.append("cores", "0");
                                nowCpu.append("threads", "0");
                            }

                            break;

                        case 4 :
                            nowCpu.append("pin", tds.get(i).text().toLowerCase());

                            break;

                        case 3 :
                            if(tds.get(i).text().contains("NO") || tds.get(i).text().contains("No"))
                            {
                                nowCpu.append("internalGraphic", "n/a");
                            }
                            else if(!tds.get(i).text().contains("("))
                            {
                                nowCpu.append("internalGraphic", tds.get(i).text());
                            }
                            else
                            {
                                m = findstr(tds.get(i).text(), "(.*?)(\\((.*?)\\))");

                                if(m.find())
                                    nowCpu.append("internalGraphic", m.group(1));
                                else
                                {
                                    nowCpu.append("internalGraphic", "n/a");
                                }
                            }

                            break;
                            
                        case 13 :

                            if(!tds.get(i).text().contains("DDR"))
                            {
                                m = findstr(tds.get(i).text(), "[0-9]+");

                                if(m.find())
                                {
                                    String tmp = m.group(0);

                                    while(m.find())
                                    {
                                        tmp = m.group(0);
                                    }

                                    int tmpi = Integer.valueOf(tmp);

                                    if(tmpi > 1600)
                                        nowCpu.append("ramGenerationSupport", "ddr4");
                                    else if(tmpi > 800)
                                        nowCpu.append("ramGenerationSupport", "ddr3");
                                    else if(tmpi > 400)
                                        nowCpu.append("ramGenerationSupport", "ddr2");
                                    else
                                        nowCpu.append("ramGenerationSupport", "ddr");             
                                }
                                else
                                {
                                    nowCpu.append("ramGenerationSupport", "n/a");
                                }       
                            }
                            else
                            {
                                m = findstr(tds.get(i).text(), "DDR([0-9]{0,1})");
    
                                if(m.find())
                                {
                                    nowCpu.append("ramGenerationSupport", m.group(0).toLowerCase());                               
                                }
                                else
                                {
                                    nowCpu.append("ramGenerationSupport", "n/a");
                                }                    
                            }

                            break;

                        case 12 :
                            m = findstr(tds.get(i).text(), "([0-9]+)W");

                            if(m.find())
                            {
                                nowCpu.append("TDP", Integer.valueOf(m.group(1)));
                            }
                            else
                            {
                                nowCpu.append("TDP", 0);
                            }

                            break;
                    }

                }
                
                nowCpu.append("ramMaximumSupport", 256);
                
                if(nowCpu.getString("name").length() > 5)
                {
                    try
                    {
                        Cpu.toObject(nowCpu);

                        result.add(nowCpu);
                    }
                    catch(Exception e)
                    {
                        e.printStackTrace();
                    }
                    
                    System.out.println(nowCpu.toString());
                }
                else
                {
                    System.out.println("error: " + nowCpu.toString());
                }
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }

        return result;
    }

    private static Matcher findstr(String inp, String pattern) 
    {
		Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(inp);
        
        return m;
	}
}