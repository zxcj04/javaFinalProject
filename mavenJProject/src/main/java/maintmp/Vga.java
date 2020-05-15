package maintmp;

import org.bson.Document;

public class Vga extends Hardware
{
    private String length;

    public Vga(String name, String length)
    {
        super(name);

        this.length = length;
    }

    public String getLength()
    {
        return this.length;
    }

    public static Vga toObject(Document doc)
    {
        Vga object = new Vga((String)doc.get("name"), (String)doc.get("length"));

        return object;
    }
}