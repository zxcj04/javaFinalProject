package maintmp;

import org.bson.Document;

public class Ram extends Hardware
{
    private String ramType;  
    private String capacity;
    
    public Ram(String name, String ramType, String capacity)
    {
        super(name);
        
        this.ramType = ramType;
        this.capacity = capacity;
    }

    public String getRamType()
    {
        return this.ramType;
    }

    public String getCapacity()
    {
        return this.capacity;
    }

    public static Ram toObject(Document doc)
    {
        Ram object = new Ram((String)doc.get("name"), (String)doc.get("ramType"), (String)doc.get("capacity"));

        return object;
    }
}