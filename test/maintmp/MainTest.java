package maintmp;

public class MainTest 
{
    public static void main(String[] args) 
    {
        MainGee gee = new MainGee();

        double a = System.currentTimeMillis();

        HardwareNameList geeList = gee.getList();

        double b = System.currentTimeMillis();

        geeList = gee.getList();

        double c = System.currentTimeMillis();

        System.out.printf("%f %f%n", b - a, c - b);
        System.out.println(geeList);

        HardwareNameList select = new HardwareNameList();

        select.cpu      .add("test15Cpu");
        select.mb       .add("testMb");
        select.cooler   .add("testCooler");
        select.ram      .add("test10Ram");
        select.vga      .add("testVga");
        select.disk     .add("testDisk");
        select.psu      .add("testPsu");
        select.crate    .add("testCrate");

        a = System.currentTimeMillis();

        HardwareList selc = gee.getHList(select);

        b = System.currentTimeMillis();

        System.out.printf("%s%n%s%n%s%n%s%n%s%n%s%n%s%n%s%n"
                        , selc.cpuList, selc.mbList, selc.coolerList, selc.ramList
                        , selc.vgaList, selc.diskList, selc.psuList, selc.crateList);

        System.out.println(b - a);
        System.out.println(selc);
    }
}