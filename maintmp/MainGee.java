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
        originList.coolerList.add(new Cooler("testCooler", "12cm"));
        originList.mbList.add(new Mb("testMb", "matx", "am4", "ryzen", "1", "dvi/hdmi", "4", "2", "ddr4", "4", "64g", "0", "6", "pcie", "1"));
        originList.ramList.add(new Ram("testRam", "ddr4", "16g"));
        originList.vgaList.add(new Vga("testVga", "17cm"));
        originList.diskList.add(new Disk("testDisk", "ssd", "512g", "2.5"));
        originList.psuList.add(new Psu("testPsu", "16cm", "650w", "atx"));
        originList.crateList.add(new Crate("testCase", "matx", "20cm", "17cm", "13cm", "3"));
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

    public HardwareNameList getList(HardwareNameList list)
    {
        HardwareList nowList = new HardwareList();

        nowList.setHardware(list, originList);

        return null;
    }
}