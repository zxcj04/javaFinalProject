package maintmp;

public class MainGee 
{
    HardwareList originList;

    HardwareNameList nameList;

    public MainGee()
    {
        originList = new HardwareList();

        init();

        nameList = new HardwareNameList();
    }

    public void init()
    {
        originList.clear();

        // fetch things from mongodb for several list


        // tmp
        originList.cpuList.add(new Cpu("testCpu", "6", "12", "65w", "3.0ghz", "3.6ghz", "am4", "64g", "ddr4", "n/a"));
            originList.cpuList.add(new Cpu("test1Cpu", "6", "12", "65w", "3.0ghz", "3.6ghz", "am4", "64g", "ddr4", "n/a"));
            originList.cpuList.add(new Cpu("test2Cpu", "6", "12", "65w", "3.0ghz", "3.6ghz", "am4", "64g", "ddr4", "n/a"));
            originList.cpuList.add(new Cpu("test3Cpu", "6", "12", "65w", "3.0ghz", "3.6ghz", "am4", "64g", "ddr4", "n/a"));
            originList.cpuList.add(new Cpu("test4Cpu", "6", "12", "65w", "3.0ghz", "3.6ghz", "am4", "64g", "ddr4", "n/a"));
            originList.cpuList.add(new Cpu("test5Cpu", "6", "12", "65w", "3.0ghz", "3.6ghz", "am4", "64g", "ddr4", "n/a"));
            originList.cpuList.add(new Cpu("test6Cpu", "6", "12", "65w", "3.0ghz", "3.6ghz", "am4", "64g", "ddr4", "n/a"));
            originList.cpuList.add(new Cpu("test7Cpu", "6", "12", "65w", "3.0ghz", "3.6ghz", "am4", "64g", "ddr4", "n/a"));
            originList.cpuList.add(new Cpu("test8Cpu", "6", "12", "65w", "3.0ghz", "3.6ghz", "am4", "64g", "ddr4", "n/a"));
            originList.cpuList.add(new Cpu("test9Cpu", "6", "12", "65w", "3.0ghz", "3.6ghz", "am4", "64g", "ddr4", "n/a"));
            originList.cpuList.add(new Cpu("test10Cpu", "6", "12", "65w", "3.0ghz", "3.6ghz", "am4", "64g", "ddr4", "n/a"));
            originList.cpuList.add(new Cpu("test11Cpu", "6", "12", "65w", "3.0ghz", "3.6ghz", "am4", "64g", "ddr4", "n/a"));
            originList.cpuList.add(new Cpu("test12Cpu", "6", "12", "65w", "3.0ghz", "3.6ghz", "am4", "64g", "ddr4", "n/a"));
            originList.cpuList.add(new Cpu("test13Cpu", "6", "12", "65w", "3.0ghz", "3.6ghz", "am4", "64g", "ddr4", "n/a"));
            originList.cpuList.add(new Cpu("test14Cpu", "6", "12", "65w", "3.0ghz", "3.6ghz", "am4", "64g", "ddr4", "n/a"));
            originList.cpuList.add(new Cpu("test15Cpu", "6", "12", "65w", "3.0ghz", "3.6ghz", "am4", "64g", "ddr4", "n/a"));
            originList.cpuList.add(new Cpu("test16Cpu", "6", "12", "65w", "3.0ghz", "3.6ghz", "am4", "64g", "ddr4", "n/a"));
        originList.coolerList.add(new Cooler("testCooler", "12cm"));
        originList.mbList.add(new Mb("testMb", "matx", "am4", "ryzen", "1", "dvi/hdmi", "4", "2", "ddr4", "4", "64g", "0", "6", "pcie", "1"));
        originList.ramList.add(new Ram("testRam", "ddr4", "16g"));
            originList.ramList.add(new Ram("test1Ram", "ddr4", "16g"));
            originList.ramList.add(new Ram("test2Ram", "ddr4", "16g"));
            originList.ramList.add(new Ram("test3Ram", "ddr4", "16g"));
            originList.ramList.add(new Ram("test4Ram", "ddr4", "16g"));
            originList.ramList.add(new Ram("test5Ram", "ddr4", "16g"));
            originList.ramList.add(new Ram("test6Ram", "ddr4", "16g"));
            originList.ramList.add(new Ram("test7Ram", "ddr4", "16g"));
            originList.ramList.add(new Ram("test8Ram", "ddr4", "16g"));
            originList.ramList.add(new Ram("test9Ram", "ddr4", "16g"));
            originList.ramList.add(new Ram("test10Ram", "ddr4", "16g"));
            originList.ramList.add(new Ram("test11Ram", "ddr4", "16g"));
            originList.ramList.add(new Ram("test12Ram", "ddr4", "16g"));
        originList.vgaList.add(new Vga("testVga", "17cm"));
        originList.diskList.add(new Disk("testDisk", "ssd", "512g", "2.5"));
        originList.psuList.add(new Psu("testPsu", "16cm", "650w", "atx"));
        originList.crateList.add(new Crate("testCrate", "matx", "20cm", "17cm", "13cm", "3"));
    }

    public HardwareNameList getList()
    {
        if(!nameList.isEmpty())
        {
            return nameList;
        }

        nameList.clear();

        nameList.addAll(originList);

        return nameList;
    }

    public HardwareNameList getList(HardwareNameList selectedList)
    {
        HardwareList nowList = new HardwareList();

        nowList.setHardware(selectedList, nameList, originList);

        

        return null;
    }

    public HardwareList getHList(HardwareNameList selectedList)
    {
        HardwareList nowList = new HardwareList();

        nowList.setHardware(selectedList, nameList, originList);

        return nowList;
    }
}
