package mainlogic;

import org.bson.Document;

public class Ram extends Hardware
{
    private String ramType;  
    private int capacity;
    
    public Ram(String name, String ramType, int capacity)
    {
        super(name);
        
        this.ramType = ramType;
        this.capacity = capacity;
    }

    public String getRamType()
    {
        return this.ramType;
    }

    public int getCapacity()
    {
        return this.capacity;
    }

    public static Ram toObject(Document doc)
    {
        Ram object = new Ram((String)doc.get("name"), (String)doc.get("ramType"), (int)doc.get("capacity"));

        return object;
    }
}