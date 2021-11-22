package mainlogic;

import org.bson.Document;

public class Crate extends Hardware
{
    private String mbSize;
    private int vgaLength;
    private String psuSize;
    private int coolerHeight;
    private int diskQuantity;
    private int psuLength;

    public Crate(String name, String mbSize, int vgaLength, String psuSize, int coolerHeight, int diskQuantity, int psuLength)
    {
        super(name);

        this.mbSize = mbSize;
        this.vgaLength = vgaLength;
        this.psuSize = psuSize;
        this.coolerHeight = coolerHeight;
        this.diskQuantity = diskQuantity;
        this.psuLength = psuLength;
    }

    public static Crate toObject(Document doc)
    {
        Crate object = new Crate((String)doc.get("name"), (String)doc.get("mbSize"), (int)doc.get("vgaLength"), (String)doc.get("psuSize"), (int)doc.get("coolerHeight"), (int)doc.get("diskQuantity"), (int)doc.get("psuLength"));

        return object;
    }

    public String getMbSize()
    {
        return this.mbSize;
    }

    public int getVgaLength()
    {
        return this.vgaLength;
    }

    public String getPsuSize()
    {
        return this.psuSize;
    }

    public int getCoolerHeight()
    {
        return this.coolerHeight;
    }

    public int getDiskQuantity()
    {
        return this.diskQuantity;
    }

    public int getPsuLength()
    {
        return this.psuLength;
    }
}