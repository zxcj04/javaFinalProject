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

        // try 
        // {        
            gee = new MainGee();    

            gee.init();
        // }
        // catch (Exception e) 
        // {
        //     System.out.println("exiting...");

        //     System.out.println(e.getLocalizedMessage());
        //     System.out.println(e.getCause());
        //     System.out.println(e.getStackTrace());            

        //     return;
        // }

        double a = System.currentTimeMillis();

        HardwareNameList geeList = gee.getList();

        double b = System.currentTimeMillis();

        geeList = gee.getList();

        double c = System.currentTimeMillis();

        System.out.printf("%s%n%s%n%s%n%s%n%s%n%s%n%s%n%s%n"
                        , geeList.cpu, geeList.mb, geeList.cooler, geeList.ram
                        , geeList.vga, geeList.disk, geeList.psu, geeList.crate);
        
        System.out.printf("%f%n", a - init);
        System.out.printf("%f %f%n", b - a, c - b);
        System.out.println(geeList);

        for(int i = 0 ; i < 1 ; i++)
        {

            HardwareNameList select = new HardwareNameList();

            // select.cpu      .add("test3Cpu"); 
            select.mb       .add("testMb");
            // select.cooler   .add("testCooler");
            select.cooler   .add("custom cooler 10cm");
            select.ram      .add("test6Ram");
            // select.vga      .add("testVga");
            select.disk     .add("testDisk");
            // select.psu      .add("test1Psu");
            // select.crate    .add("test1Crate");

            a = System.currentTimeMillis();

            HardwareList selc = gee.getHList(select);

            b = System.currentTimeMillis();

            System.out.printf("%s%n%s%n%s%n%s%n%s%n%s%n%s%n%s%n"
                            , selc.cpuList, selc.mbList, selc.coolerList, selc.ramList
                            , selc.vgaList, selc.diskList, selc.psuList, selc.crateList);

            System.out.println(b - a);
            System.out.println(selc);

            a = System.currentTimeMillis();

            HardwareNameList selcName = gee.getList(select);

            b = System.currentTimeMillis();

            System.out.println(b - a);

            System.out.print(selcName.fullDisplay());

            System.out.println(selcName);

        }   
    }
}