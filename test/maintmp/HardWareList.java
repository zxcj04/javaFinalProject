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

    public void setHardware(HardwareNameList selectedList,HardwareNameList nameList , HardwareList originList)
    {
        try
        {
            if(!selectedList.cpu.isEmpty())
            {
                for(String i : selectedList.cpu)
                {
                    this.cpuList.add(originList.cpuList.get(nameList.cpu.indexOf(i)));
                }
            }
            // else
            // {
            //     this.cpuList.addAll(originList.cpuList);
            // }

            if(!selectedList.mb.isEmpty())
            {
                for(String i : selectedList.mb)
                {
                    this.mbList.add(originList.mbList.get(nameList.mb.indexOf(i)));
                }
            }

            if(!selectedList.cooler.isEmpty())
            {
                for(String i : selectedList.cooler)
                {
                    this.coolerList.add(originList.coolerList.get(nameList.cooler.indexOf(i)));
                }
            }

            if(!selectedList.ram.isEmpty())
            {
                for(String i : selectedList.ram)
                {
                    this.ramList.add(originList.ramList.get(nameList.ram.indexOf(i)));
                }
            }

            if(!selectedList.vga.isEmpty())
            {
                for(String i : selectedList.vga)
                {
                    this.vgaList.add(originList.vgaList.get(nameList.vga.indexOf(i)));
                }
            }

            if(!selectedList.disk.isEmpty())
            {
                for(String i : selectedList.disk)
                {
                    this.diskList.add(originList.diskList.get(nameList.disk.indexOf(i)));
                }
            }

            if(!selectedList.psu.isEmpty())
            {
                for(String i : selectedList.psu)
                {
                    this.psuList.add(originList.psuList.get(nameList.psu.indexOf(i)));
                }
            }

            if(!selectedList.crate.isEmpty())
            {
                for(String i : selectedList.crate)
                {
                    this.crateList.add(originList.crateList.get(nameList.crate.indexOf(i)));
                }
            }
        }
        catch(Exception e)
        {
            System.out.println("ERROR: No such hardware!");

            this.clear();
        }
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