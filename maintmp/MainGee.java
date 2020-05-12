package maintmp;

import java.util.ArrayList;

public class MainGee 
{
    ArrayList<Cpu>      cpuList;
    ArrayList<Mb>       mbList;
    ArrayList<Cooler>   coolerList;
    ArrayList<Ram>      ramList;
    ArrayList<Vga>      vgaList;
    ArrayList<Disk>     diskList;
    ArrayList<Psu>      psuList;
    ArrayList<Case>     caseList;

    public MainGee()
    {
        cpuList     = new ArrayList<Cpu>();
        mbList      = new ArrayList<Mb>();
        coolerList  = new ArrayList<Cooler>();
        ramList     = new ArrayList<Ram>();
        vgaList     = new ArrayList<Vga>();
        psuList     = new ArrayList<Psu>();
        caseList    = new ArrayList<Case>();

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
        caseList.clear();

        // fetch things from mongodb for several list


        // tmp

        
    }




}