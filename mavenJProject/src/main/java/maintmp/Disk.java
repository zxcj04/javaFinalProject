package maintmp;

import org.bson.Document;

public class Disk extends Hardware
{
    private String diskType;
    private String capacity;
    private String size;
    
    public Disk(String name, String diskType, String capacity, String size)
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
    public String getCapacity()
    {
        return this.capacity;
    }
    public String getSize()
    {
        return this.size;
    }

    public static Disk toObject(Document doc)
    {
        Disk object = new Disk((String)doc.get("name"), (String)doc.get("diskType"), (String)doc.get("capacity"), (String)doc.get("size"));

        return object;
    }
}