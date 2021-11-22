package mainlogic;

import org.bson.Document;

public class Psu extends Hardware
{
    private int length;
    private int watts;
    private String size;

    public Psu(String name, int length, int watts, String size)
    {
        super(name);

        this.length = length;
        this.watts = watts;
        this.size = size;
    }

    public int getLength()
    {
        return this.length;
    }

    public int getWatts()
    {
        return this.watts;
    }

    public String getSize()
    {
        return this.size;
    }

    public static Psu toObject(Document doc)
    {
        Psu object = new Psu((String)doc.get("name"), (int)doc.get("length"), (int)doc.get("watts"), (String)doc.get("size"));

        return object;
    }
}