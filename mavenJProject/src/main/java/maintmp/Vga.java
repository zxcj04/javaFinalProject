package maintmp;

import org.bson.Document;

public class Vga extends Hardware
{
    private int length;
    private int TDP;

    public Vga(String name, int length, int TDP)
    {
        super(name);

        this.length = length;
        this.TDP = TDP;
    }

    public int getLength()
    {
        return this.length;
    }

    public int getTDP()
    {
        return this.TDP;
    }

    public static Vga toObject(Document doc)
    {
        Vga object = new Vga((String)doc.get("name"), (int)doc.get("length"), (int)doc.get("TDP"));

        return object;
    }
}