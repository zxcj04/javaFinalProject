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

import mainlogic.Cpu;

public class UpdateCpu 
{
    ArrayList<org.bson.Document> result;

    ExecutorService executor;
    
    public UpdateCpu() throws IOException
    {
        result = new ArrayList<org.bson.Document>();
        
        executor = Executors.newCachedThreadPool();

        getCpuList();
    }

    public void getCpuList() throws IOException 
    {
        ArrayList<String> sites = new ArrayList<String>(Arrays.asList(
                        "https://www.cpu-monkey.com/en/cpu_family-intel_core_i3-6", 
                        "https://www.cpu-monkey.com/en/cpu_family-intel_core_i5-5", 
                        "https://www.cpu-monkey.com/en/cpu_family-intel_core_i7-7", 
                        "https://www.cpu-monkey.com/en/cpu_family-intel_core_i9-37", 
                        "https://www.cpu-monkey.com/en/cpu_family-intel_pentium-4", 
                        "https://www.cpu-monkey.com/en/cpu_family-amd_ryzen-32"
                    ));

        for(String site : sites) 
        {
            getPageList(site);
        }

        executor.shutdown();

        // getPageList(sites.get(0));
    }

    public void getPageList(String site) throws IOException
    {
        Document doc = Jsoup.connect(site)
                            .userAgent("Mozilla/5.0 (Windows; U; WindowsNT 5.1; en-US; rv1.8.1.6) Gecko/20070725 Firefox/2.0.0.6")
                            .referrer("http://www.google.com")
                            .get();

        Elements pages = doc.select(".data");

        for(Element page : pages.get(0).select("a"))
        {
            org.bson.Document tmp = getInnerMessages(page.absUrl("href"), page.parent().parent().children().get(1).text());
            
            if(tmp == null)
                continue;

            result.add(tmp);
            
            // executor.execute(new UpdateCpuWorker(page.absUrl("href"), this.result, page.parent().parent().children().get(1).text()));
        }

        // for(int i = 0; i < 5 ; i++)
        //     result.add(getInnerMessages(pages.get(0).select("a").get(i).absUrl("href"), pages.get(0).select("a").get(i).parent().parent().children().get(1).text()));
    }

    public static org.bson.Document getInnerMessages(String url, String generationText)
    {        
        org.bson.Document nowCpu = initCpuDocument();

        Matcher m;

        try
        {
            Document doc = Jsoup.connect(url)
                                .get();
    
            nowCpu.append("name", doc.select("h1 a").get(0).text());

            if(nowCpu.getString("name").toLowerCase().contains("intel")) 
            {                           
                m = findstr(nowCpu.getString("name").toLowerCase(), "-[0-9]+(.*?)$");

                if(m.find())
                {                    
                    if( m.group(1).toLowerCase().contains("g") ||
                        m.group(1).toLowerCase().contains("e") ||
                        m.group(1).toLowerCase().contains("u") ||
                        m.group(1).toLowerCase().contains("h") ||
                        m.group(1).toLowerCase().contains("y") ||
                        m.group(1).toLowerCase().contains("q") ||
                        m.group(1).toLowerCase().contains("m") ||
                        m.group(1).toLowerCase().contains("h") ||
                        m.group(1).toLowerCase().contains("p") ||
                        m.group(1).toLowerCase().contains("c") ||
                        m.group(1).toLowerCase().contains("b")                                                           
                        )
                    {
                        return null;
                    }
                }
            }

            String s = "-1";

            if(!generationText.isEmpty())
            {
                s = generationText.substring(0, generationText.length() - 1);
            }

            nowCpu.append("generation", Integer.valueOf(s));

            Elements tds = doc.select("td");

            // for(Element td : doc.select("td"))
            for(int i = 0 ; i < tds.size() ; i++)
            {
                switch(tds.get(i).text())
                {
                    case "Socket:" :
                        i++;

                        m = findstr(tds.get(i).text(), " (.*?)$");

                        if(m.find())
                        {
                            nowCpu.append("pin", m.group(1));
                        }
                        else
                        {
                            nowCpu.append("pin", tds.get(i).text().toLowerCase());
                        }

                        break;

                    case "CPU Cores:":
                        i++;

                        nowCpu.append("cores", tds.get(i).text());

                        break;

                    case "CPU Threads:" :
                        i++;

                        nowCpu.append("threads", tds.get(i).text());

                        break;

                    case "Frequency:" :
                        i++;

                        m = findstr(tds.get(i).text(), "([0-9]+\\.?[0-9]+) GHz");

                        if(m.find())
                            nowCpu.append("frequency", m.group(1));

                        break;

                    case "Turbo (1 Core):" :
                        i++;

                        m = findstr(tds.get(i).text(), "([0-9]+\\.?[0-9]+) GHz");

                        if(m.find())
                            nowCpu.append("turboBoost", m.group(1));

                        break;

                    case "Max. Memory:" :
                        if(tds.get(i).attr("style").equals("height: 50px;"))
                        {
                            break;
                        }

                        i++;

                        m = findstr(tds.get(i).text(), "([0-9]+) GB");

                        if(m.find())
                            nowCpu.append("ramMaximumSupport", Integer.valueOf(m.group(1)));

                        break;

                    case "Memory type:" :
                        i++;

                        m = findstr(tds.get(i).text(), "(DDR[0-9])");

                        while(m.find())
                        {
                            nowCpu.append("ramGenerationSupport", m.group(1).toLowerCase());
                        }

                        break;

                    case "GPU name:" :
                        i++;
                        
                        if(!tds.get(i).text().equals(""))
                            nowCpu.append("internalGraphic", tds.get(i).text());

                        break;

                    case "TDP:" :
                        i++;

                        m = findstr(tds.get(i).text(), "([0-9]+) W");

                        if(m.find())
                            nowCpu.append("TDP", Integer.valueOf(m.group(1)));

                        break;
                }
            }

            System.out.println(nowCpu);
        }
        catch(Exception e)
        {
            e.printStackTrace();

            System.out.println("Error documenting...");
        }

        try
        {
            Cpu.toObject(nowCpu);
        }
        catch(Exception e)
        {
            System.out.println(":error toObjecting...");

            return null;
        }
        
        return nowCpu;
    }

    private static org.bson.Document initCpuDocument()
    {
        org.bson.Document now = new org.bson.Document();

        now .append("name", "n/a").append("pin", "n/a").append("cores", "n/a")
            .append("threads", "n/a").append("frequency", "n/a")
            .append("turboBoost", "n/a").append("ramMaximumSupport", 256)
            .append("ramGenerationSupport", "n/a").append("internalGraphic", "n/a")
            .append("TDP", 0).append("generation", 0);

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