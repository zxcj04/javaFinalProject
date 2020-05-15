package maintmp;

import java.util.ArrayList;
import static java.util.stream.Collectors.toList;

public class HardwareNameList
{
    public ArrayList<String> cpu;
    public ArrayList<String> mb;
    public ArrayList<String> cooler;
    public ArrayList<String> ram;
    public ArrayList<String> vga;
    public ArrayList<String> disk;
    public ArrayList<String> psu;
    public ArrayList<String> crate;

    public HardwareNameList()
    {
        cpu     = new ArrayList<String>();
        mb      = new ArrayList<String>();
        cooler  = new ArrayList<String>();
        ram     = new ArrayList<String>();
        vga     = new ArrayList<String>();
        disk    = new ArrayList<String>();
        psu     = new ArrayList<String>();
        crate   = new ArrayList<String>();
    }

    public HardwareNameList(ArrayList<String> cpu, ArrayList<String> mb, ArrayList<String> cooler,
            ArrayList<String> ram, ArrayList<String> vga, ArrayList<String> disk, ArrayList<String> psu,
            ArrayList<String> crate)
    {
        this.cpu = cpu;
        this.mb = mb;
        this.cooler = cooler;
        this.ram = ram;
        this.vga = vga;
        this.disk = disk;
        this.psu = psu;
        this.crate = crate;
    }

    public void clear()
    {
        cpu.clear();
        mb.clear();
        cooler.clear();
        ram.clear();
        vga.clear();
        disk.clear();
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
        
    @Override
    public String toString()
    {
        return String.format("cpu: %d, mb: %d, cooler: %d, ram: %d,%nvga: %d. disk: %d, psu: %d, crate: %d"
                                , cpu.size(), mb.size(), cooler.size(), ram.size()
                                , vga.size(), disk.size(), psu.size(), crate.size());
    }
}
