package maintmp;

import java.util.ArrayList;
import static java.util.stream.Collectors.toList;

public class MainGee 
{
    ArrayList<Cpu>      cpuList;
    ArrayList<Mb>       mbList;
    ArrayList<Cooler>   coolerList;
    ArrayList<Ram>      ramList;
    ArrayList<Vga>      vgaList;
    ArrayList<Disk>     diskList;
    ArrayList<Psu>      psuList;
    ArrayList<Crate>    crateList;

    public MainGee()
    {
        cpuList     = new ArrayList<Cpu>();
        mbList      = new ArrayList<Mb>();
        coolerList  = new ArrayList<Cooler>();
        ramList     = new ArrayList<Ram>();
        vgaList     = new ArrayList<Vga>();
        diskList    = new ArrayList<Disk>();
        psuList     = new ArrayList<Psu>();
        crateList   = new ArrayList<Crate>();

        init();
    }

    public void init()
    {
        cpuList.clear(); 
        mbList.clear(); 
        coolerList.clear();
        ramList.clear(); 
        vgaList.clear(); 
        psuList.clear(); 
        crateList.clear();

        // fetch things from mongodb for several list


        // tmp
        cpuList.add(new Cpu("testCpu", "6", "12", "65w", "3.0ghz", "3.6ghz", "am4", "64g", "ddr4", "n/a"));
        coolerList.add(new Cooler("testCooler", "12cm"));
        mbList.add(new Mb("testMb", "matx", "am4", "ryzen", "1", "dvi/hdmi", "4", "2", "ddr4", "4", "64g", "0", "6", "pcie", "1"));
        ramList.add(new Ram("testRam", "ddr4", "16g"));
        vgaList.add(new Vga("testVga", "17cm"));
        diskList.add(new Disk("testDisk", "ssd", "512g", "2.5"));
        psuList.add(new Psu("testPsu", "16cm", "650w", "atx"));
        crateList.add(new Crate("testCase", "matx", "20cm", "17cm", "13cm", "3"));
    }

    public HardWareList getList()
    {
        HardWareList list = new HardWareList();

        list.cpu.addAll(cpuList.stream().map(Hardware::getName).collect(toList()));
        list.mb.addAll(mbList.stream().map(Hardware::getName).collect(toList()));
        list.cooler.addAll(coolerList.stream().map(Hardware::getName).collect(toList()));
        list.ram.addAll(ramList.stream().map(Hardware::getName).collect(toList()));
        list.vga.addAll(vgaList.stream().map(Hardware::getName).collect(toList()));
        list.disk.addAll(diskList.stream().map(Hardware::getName).collect(toList()));
        list.psu.addAll(psuList.stream().map(Hardware::getName).collect(toList()));
        list.crate.addAll(crateList.stream().map(Hardware::getName).collect(toList()));

        return list;
    }

    public HardWareList getList(HardWareList list)
    {
        return null;
    }
}