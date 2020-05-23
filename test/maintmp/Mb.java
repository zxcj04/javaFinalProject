package maintmp;

public class Mb extends Hardware
{
    private String size;
    private String pin;
    private String supportCpu;
    private String RJ45;
    private String graphicOutput;
    private String usbQuantity;
    private String pcieQuantity;
    private String ramType;
    private String ramQuantity;
    private String ramMaximum;
    private String wifi;
    private String sata3Quantity;
    private String m2Type;
    private String m2Quantity;

    public Mb(String name, String size, String pin, String supportCpu, String RJ45, String graphicOutput, String usbQuantity, String pcieQuantity, String ramType, String ramQuantity, String ramMaximum, String wifi, String sata3Quantity, String m2Type, String m2Quantity)
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

    public String getUsbQuantity()
    {
        return this.usbQuantity;
    }

    public String getPcieQuantity()
    {
        return this.pcieQuantity;
    }

    public String getRamType()
    {
        return this.ramType;
    }

    public String getRamQuantity()
    {
        return this.ramQuantity;
    }

    public String getRamMaximum()
    {
        return this.ramMaximum;
    }

    public String getWifi()
    {
        return this.wifi;
    }

    public String getSata3Quantity()
    {
        return this.sata3Quantity;
    }

    public String getM2Type()
    {
        return this.m2Type;
    }

    public String getM2Quantity()
    {
        return this.m2Quantity;
    }
}