package autoupdate;

import java.util.ArrayList;

import org.bson.Document;

import mainlogic.Cooler;

public class UpdateCooler
{
    public static ArrayList<Document> getCoolerList()
    {
        ArrayList<Document> list = new ArrayList<Document>();

        list.add(new Document("name", "Cooler Master 13cm").append("height", 13));
        list.add(new Document("name", "Cooler Master 15cm").append("height", 15));
        list.add(new Document("name", "Cooler Master 17cm").append("height", 17));
        list.add(new Document("name", "Cooler Master 19cm").append("height", 19));

		ArrayList<Document> info = new ArrayList<Document>();
        
		for(Document ele : list)
		{
			try
			{
				Cooler.toObject(ele);

				ele.remove("_id");
				info.add(ele);
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
        
        return info;
    }
}