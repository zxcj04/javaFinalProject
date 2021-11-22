package mainlogic;

import org.bson.Document;

public class Mb extends Hardware
{
    private String size;
    private String pin;
    private String supportCpu;
    private String RJ45;
    private String graphicOutput;
    private int usbQuantity;
    private int pcieQuantity;
    private String ramType;
    private int ramQuantity;
    private int ramMaximum;
    private String wifi;
    private int sata3Quantity;
    private String m2Type;
    private int m2Quantity;

    public Mb(String name, String size, String pin, String supportCpu, String RJ45
            , String graphicOutput, int usbQuantity, int pcieQuantity, String ramType
            , int ramQuantity, int ramMaximum, String wifi, int sata3Quantity
            , String m2Type, int m2Quantity)
    {
        super(name);
        
        this.size = size;
        this.pin = pin;
        this.supportCpu = supportCpu;
        this.RJ45 = RJ45;
        this.graphicOutput = graphicOutput;
        this.usbQuantity = usbQuantity;
        this.pcieQuantity = pcieQuantity;
        this.ramType = ramType;
        this.ramQuantity = ramQuantity;
        this.ramMaximum = ramMaximum;
        this.wifi = wifi;
        this.sata3Quantity = sata3Quantity;
        this.m2Type = m2Type;
        this.m2Quantity = m2Quantity;
    }

    public static Mb toObject(Document doc)
    {
        Mb object = new Mb((String)doc.get("name"), (String)doc.get("size"), (String)doc.get("pin")
                         , (String)doc.get("supportCpu"), (String)doc.get("RJ45"), (String)doc.get("graphicOutput")
                         , (int)doc.get("usbQuantity"), (int)doc.get("pcieQuantity"), (String)doc.get("ramType")
                         , (int)doc.get("ramQuantity"), (int)doc.get("ramMaximum"), (String)doc.get("wifi")
                         , (int)doc.get("sata3Quantity"), (String)doc.get("m2Type"), (int)doc.get("m2Quantity"));

        return object;
    }

    public String getSize()
    {
        return this.size;
    }

    public String getPin()
    {
        return this.pin;
    }

    public String getSupportCpu()
    {
        return this.supportCpu;
    }

    public String getRJ45()
    {
        return this.RJ45;
    }

    public String getGraphicOutput()
    {
        return this.graphicOutput;
    }

    public int getUsbQuantity()
    {
        return this.usbQuantity;
    }

    public int getPcieQuantity()
    {
        return this.pcieQuantity;
    }

    public String getRamType()
    {
        return this.ramType;
    }

    public int getRamQuantity()
    {
        return this.ramQuantity;
    }

    public int getRamMaximum()
    {
        return this.ramMaximum;
    }

    public String getWifi()
    {
        return this.wifi;
    }

    public int getSata3Quantity()
    {
        return this.sata3Quantity;
    }

    public String getM2Type()
    {
        return this.m2Type;
    }

    public int getM2Quantity()
    {
        return this.m2Quantity;
    }

}