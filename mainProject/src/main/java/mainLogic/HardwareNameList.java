package mainlogic;

import java.util.ArrayList;
import static java.util.stream.Collectors.toList;

public class HardwareNameList
{
    public ArrayList<String> cpu;
    public ArrayList<String> cooler;
    public ArrayList<String> mb;
    public ArrayList<String> ram;
    public ArrayList<String> disk;
    public ArrayList<String> vga;
    public ArrayList<String> psu;
    public ArrayList<String> crate;

    public HardwareNameList()
    {
        cpu     = new ArrayList<String>();
        cooler  = new ArrayList<String>();
        mb      = new ArrayList<String>();
        ram     = new ArrayList<String>();
        disk    = new ArrayList<String>();
        vga     = new ArrayList<String>();
        psu     = new ArrayList<String>();
        crate   = new ArrayList<String>();
    }

    public HardwareNameList(ArrayList<String> cpu, ArrayList<String> cooler, ArrayList<String> mb,
            ArrayList<String> ram, ArrayList<String> disk, ArrayList<String> vga, ArrayList<String> psu,
            ArrayList<String> crate)
    {
        this.cpu = cpu;
        this.cooler = cooler;
        this.mb = mb;
        this.ram = ram;
        this.disk = disk;
        this.vga = vga;
        this.psu = psu;
        this.crate = crate;
    }

    public void clear()
    {
        cpu.clear();
        cooler.clear();
        mb.clear();
        ram.clear();
        disk.clear();
        vga.clear();
        psu.clear();
        crate.clear();
    }

    public boolean isEmpty()
    {
        if(cpu.isEmpty() || mb.isEmpty() || cooler.isEmpty() || ram.isEmpty() || vga.isEmpty() || disk.isEmpty() || psu.isEmpty() || crate.isEmpty())
        {
            return true;
        }

        return false;
    }

    public void addAll(HardwareList originList)
    {
        this.cpu.addAll(originList.cpuList.stream().map(Hardware::getName).collect(toList()));
        this.mb.addAll(originList.mbList.stream().map(Hardware::getName).collect(toList()));
        this.cooler.addAll(originList.coolerList.stream().map(Hardware::getName).collect(toList()));
        this.ram.addAll(originList.ramList.stream().map(Hardware::getName).collect(toList()));
        this.vga.addAll(originList.vgaList.stream().map(Hardware::getName).collect(toList()));
        this.disk.addAll(originList.diskList.stream().map(Hardware::getName).collect(toList()));
        this.psu.addAll(originList.psuList.stream().map(Hardware::getName).collect(toList()));
        this.crate.addAll(originList.crateList.stream().map(Hardware::getName).collect(toList()));
    }

    public static HardwareNameList toNameList(HardwareList originList)
    {
        HardwareNameList tmp = new HardwareNameList();

        tmp.cpu.addAll(originList.cpuList.stream().map(Hardware::getName).collect(toList()));
        tmp.mb.addAll(originList.mbList.stream().map(Hardware::getName).collect(toList()));
        tmp.cooler.addAll(originList.coolerList.stream().map(Hardware::getName).collect(toList()));
        tmp.ram.addAll(originList.ramList.stream().map(Hardware::getName).collect(toList()));
        tmp.vga.addAll(originList.vgaList.stream().map(Hardware::getName).collect(toList()));
        tmp.disk.addAll(originList.diskList.stream().map(Hardware::getName).collect(toList()));
        tmp.psu.addAll(originList.psuList.stream().map(Hardware::getName).collect(toList()));
        tmp.crate.addAll(originList.crateList.stream().map(Hardware::getName).collect(toList()));

        return tmp;
    }
        
    @Override
    public String toString()
    {
        return String.format("cpu: %d, mb: %d, cooler: %d, ram: %d,%nvga: %d. disk: %d, psu: %d, crate: %d"
                                , cpu.size(), mb.size(), cooler.size(), ram.size()
                                , vga.size(), disk.size(), psu.size(), crate.size());
    }

    public String fullDisplay()
    {
        String result = "";

        result += "cpu: ";
        for(String s: cpu)
        {
            result += "[" + s + "]";
        }   result += "\n";

        result += "mb: ";
        for(String s:mb)
        {
            result += "[" + s + "]";
        }   result += "\n";
        
        result += "cooler: ";
        for(String s:cooler)
        {
            result += "[" + s + "]";
        }   result += "\n";
        
        result += "ram: ";
        for(String s:ram)
        {
            result += "[" + s + "]";
        }   result += "\n";
        
        result += "vga: ";
        for(String s:vga)
        {
            result += "[" + s + "]";
        }   result += "\n";
        
        result += "disk: ";
        for(String s:disk)
        {
            result += "[" + s + "]";
        }   result += "\n";
        
        result += "psu: ";
        for(String s:psu)
        {
            result += "[" + s + "]";
        }   result += "\n";
        
        result += "crate: ";
        for(String s:crate)
        {
            result += "[" + s + "]";
        }   result += "\n";
        

        return result;
    }
}
