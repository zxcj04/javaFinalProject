package maintmp;

import org.bson.Document;

public class Crate extends Hardware
{
    private String mbSize;
    private int vgaLength;
    private int psuSize;
    private int coolerHeight;
    private int diskQuantity;

    public Crate(String name, String mbSize, int vgaLength, int psuSize, int coolerHeight, int diskQuantity)
    {
        super(name);

        this.mbSize = mbSize;
        this.vgaLength = vgaLength;
        this.psuSize = psuSize;
        this.coolerHeight = coolerHeight;
        this.diskQuantity = diskQuantity;
    }

    public static Crate toObject(Document doc)
    {
        Crate object = new Crate((String)doc.get("name"), (String)doc.get("mbSize"), (int)doc.get("vgaLength"), (int)doc.get("psuSize"), (int)doc.get("coolerHeight"), (int)doc.get("diskQuantity"));

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

    public int getPsuSize()
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

}