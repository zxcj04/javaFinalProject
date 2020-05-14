package maintmp;

import java.util.ArrayList;

public class HardwareList 
{
    public ArrayList<Cpu>      cpuList;
    public ArrayList<Mb>       mbList;
    public ArrayList<Cooler>   coolerList;
    public ArrayList<Ram>      ramList;
    public ArrayList<Vga>      vgaList;
    public ArrayList<Disk>     diskList;
    public ArrayList<Psu>      psuList;
    public ArrayList<Crate>    crateList;

    public HardwareList()
    {
        cpuList     = new ArrayList<Cpu>();
        mbList      = new ArrayList<Mb>();
        coolerList  = new ArrayList<Cooler>();
        ramList     = new ArrayList<Ram>();
        vgaList     = new ArrayList<Vga>();
        diskList    = new ArrayList<Disk>();
        psuList     = new ArrayList<Psu>();
        crateList   = new ArrayList<Crate>();
    }

    public HardwareList(HardwareList that)
    {
        cpuList     = new ArrayList<Cpu>(that.cpuList);
        mbList      = new ArrayList<Mb>(that.mbList);
        coolerList  = new ArrayList<Cooler>(that.coolerList);
        ramList     = new ArrayList<Ram>(that.ramList);
        vgaList     = new ArrayList<Vga>(that.vgaList);
        diskList    = new ArrayList<Disk>(that.diskList);
        psuList     = new ArrayList<Psu>(that.psuList);
        crateList   = new ArrayList<Crate>(that.crateList);
    }

    public void clear()
    {
        cpuList.clear();
        mbList.clear();
        coolerList.clear();
        ramList.clear();
        vgaList.clear();
        diskList.clear();
        psuList.clear();
        crateList.clear();
    }

    public void setHardware(HardwareNameList nameList, HardwareList originList)
    {
        cpuList.addAll(originList.cpuList);
    }

    @Override
    public String toString()
    {
        return String.format("cpu: %d, mb: %d, cooler: %d, ram: %d,%nvga: %d. disk: %d, psu: %d, crate: %d", 
                                cpuList.size(), 
                                mbList.size(), 
                                coolerList.size(), 
                                ramList.size(), 
                                vgaList.size(), 
                                diskList.size(), 
                                psuList.size(), 
                                crateList.size());
    }
}