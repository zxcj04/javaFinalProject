package maintmp;

import org.bson.Document;

public class Psu extends Hardware
{
    private String length;
    private String watts;
    private String size;

    public Psu(String name, String length, String watts, String size)
    {
        super(name);

        this.length = length;
        this.watts = watts;
        this.size = size;
    }

    public String getLength()
    {
        return this.length;
    }

    public String getWatts()
    {
        return this.watts;
    }

    public String getSize()
    {
        return this.size;
    }

    public static Psu toObject(Document doc)
    {
        Psu object = new Psu((String)doc.get("name"), (String)doc.get("length"), (String)doc.get("watts"), (String)doc.get("size"));

        return object;
    }
}