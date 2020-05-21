package maintmp;

import java.util.logging.Logger;
import java.util.logging.Level;

public class MainTest 
{
    public static void main(String[] args) 
    {
        Logger mongoLogger = Logger.getLogger( "org.mongodb.driver" );
        mongoLogger.setLevel(Level.SEVERE); // e.g. or Log.WARNING, etc.

        double init = System.currentTimeMillis();

        MainGee gee;

        try 
        {        
            gee = new MainGee();    

            gee.init();
        }
        catch (Exception e) 
        {
            System.out.println("exiting...");

            return;
        }

        double a = System.currentTimeMillis();

        HardwareNameList geeList = gee.getList();

        double b = System.currentTimeMillis();

        geeList = gee.getList();

        double c = System.currentTimeMillis();

        // System.out.printf("%s%n%s%n%s%n%s%n%s%n%s%n%s%n%s%n"
        //                 , geeList.cpu, geeList.mb, geeList.cooler, geeList.ram
        //                 , geeList.vga, geeList.disk, geeList.psu, geeList.crate);
        
        System.out.printf("%f%n", a - init);
        System.out.printf("%f %f%n", b - a, c - b);
        System.out.println(geeList);

        HardwareNameList select = new HardwareNameList();

        select.cpu      .add("test3Cpu");
        // select.mb       .add("testMb");
        // select.cooler   .add("testCooler");
        select.ram      .add("test6Ram");
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

        HardwareNameList selcName = gee.getList(select);

        System.out.print(selcName.fullDisplay());

        // System.out.println(selcName.cpu.get(0));
        // System.out.println(selcName.mb.get(0));
        // System.out.println(selcName.cooler.get(0));
        // System.out.println(selcName.ram.get(0));
        // System.out.println(selcName.vga.get(0));
        // System.out.println(selcName.disk.get(0));
        // System.out.println(selcName.psu.get(0));
        // System.out.println(selcName.crate.get(0));
    }
}