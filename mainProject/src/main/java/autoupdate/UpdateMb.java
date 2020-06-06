package autoupdate;

import java.io.IOException;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import mainlogic.Mb;

public class UpdateMb
{
    public static ArrayList<Mb> getMbList() throws IOException
    {
        Document doc = Jsoup.connect("https://www.motherboarddb.com/motherboards/ajax/table/").get();

        ArrayList<Mb> result = new ArrayList<Mb>();

        Elements pageLinks = doc.select("a.page-link");

        // int pages = Integer.parseInt(pageLinks.get(pageLinks.size() - 2).text());
        int pages = 10;

        for(int i = 0 ; i < pages ; i++)
        {
            result.addAll(getPageList(i));
        }
        
        return result;
    }

    private static ArrayList<Mb> getPageList(int page) throws IOException
    {
        ArrayList<Mb> result = new ArrayList<Mb>();
        
        Document doc = Jsoup.connect("https://www.motherboarddb.com/motherboards/ajax/table/?page=" + String.valueOf(page)).get();

        Elements links = doc.select("div.mb-1 a");

        for(Element link : links)
        {
            System.out.println(link.absUrl("href"));

            result.add(getInnerMessages(link.absUrl("href")));
        }

        return result;
    }

    private static Mb getInnerMessages(String url) throws IOException
    {
        Mb mb;

        org.bson.Document nowMb = new org.bson.Document();

        Document doc = Jsoup.connect(url).get();

        // mb = Mb.toObject(nowMb);

        // return mb;

        return null;
    }
}