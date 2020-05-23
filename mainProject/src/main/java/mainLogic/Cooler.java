package mainlogic;

import org.bson.Document;

public class Cooler extends Hardware
{
    private int height;

    public Cooler(String name, int height)
    {
        super(name);
        
        this.height = height;
    }
    
    public int getHeight()
    {
        return this.height;
    }
 
    public static Cooler toObject(Document doc)
    {
        Cooler object = new Cooler((String)doc.get("name"), (int)doc.get("height"));

        return object;
    }
}