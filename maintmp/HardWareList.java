package maintmp;

import java.util.ArrayList;

public class HardWareList
{
    public ArrayList<String> cpu;
    public ArrayList<String> mb;
    public ArrayList<String> cooler;
    public ArrayList<String> ram;
    public ArrayList<String> vga;
    public ArrayList<String> disk;
    public ArrayList<String> psu;
    public ArrayList<String> crate;

    public HardWareList()
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

    public HardWareList(ArrayList<String> cpu, ArrayList<String> mb, ArrayList<String> cooler, ArrayList<String> ram, ArrayList<String> vga, ArrayList<String> disk, ArrayList<String> psu, ArrayList<String> crate)
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

    @Override
    public String toString()
    {
        return String.format("cpu: %d, mb: %d, cooler: %d, ram: %d,%nvga: %d. disk: %d, psu: %d, crate: %d"
                                , cpu.size(), mb.size(), cooler.size(), ram.size()
                                , vga.size(), disk.size(), psu.size(), crate.size());
    }
}