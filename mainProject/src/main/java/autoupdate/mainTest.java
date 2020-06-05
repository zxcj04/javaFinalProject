package autoupdate;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class mainTest 
{
    public static void main(String[] args)
    {
        UpdateHardwareList updateHardwareList = new UpdateHardwareList();

        updateHardwareList.update();
    }
}