package mainlogic;

import org.bson.Document;

public class Disk extends Hardware
{
    private String diskType;
    private int capacity;
    private String size;
    
    public Disk(String name, String diskType, int capacity, String size)
    {
        super(name);

        this.diskType = diskType;
        this.capacity = capacity;
        this.size = size;
    }

    public String getDiskType()
    {
        return this.diskType;
    }
    public int getCapacity()
    {
        return this.capacity;
    }
    public String getSize()
    {
        return this.size;
    }

    public static Disk toObject(Document doc)
    {
        Disk object = new Disk((String)doc.get("name"), (String)doc.get("diskType"), (int)doc.get("capacity"), (String)doc.get("size"));

        return object;
    }
}