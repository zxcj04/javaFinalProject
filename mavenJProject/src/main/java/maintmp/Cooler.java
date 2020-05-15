package maintmp;

import org.bson.Document;

public class Cooler extends Hardware
{
    private String height;

    public Cooler(String name, String height)
    {
        super(name);
        
        this.height = height;
    }
    
    public String getHeight()
    {
        return this.height;
    }
 
    public static Cooler toObject(Document doc)
    {
        Cooler object = new Cooler((String)doc.get("name"), (String)doc.get("height"));

        return object;
    }
}