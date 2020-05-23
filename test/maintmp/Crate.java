package maintmp;

public class Crate extends Hardware
{
    private String mbSize;
    private String vgaLength;
    private String psuSize;
    private String coolerHeight;
    private String diskQuantity;

    public Crate(String name, String mbSize, String vgaLength, String psuSize, String coolerHeight, String diskQuantity)
    {
        super(name);

        this.mbSize = mbSize;
        this.vgaLength = vgaLength;
        this.psuSize = psuSize;
        this.coolerHeight = coolerHeight;
        this.diskQuantity = diskQuantity;
    }

    public String getMbSize()
    {
        return this.mbSize;
    }

    public String getVgaLength()
    {
        return this.vgaLength;
    }

    public String getPsuSize()
    {
        return this.psuSize;
    }

    public String getCoolerHeight()
    {
        return this.coolerHeight;
    }

    public String getDiskQuantity()
    {
        return this.diskQuantity;
    }

}