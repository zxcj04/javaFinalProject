package maintmp;

import java.util.ArrayList;

public class MainGee 
{
    ArrayList<Cpu>  cpuList;
    ArrayList<Mb>   mbList;
    ArrayList<Ram>  ramList;
    ArrayList<Vga>  vgaList;
    ArrayList<Psu>  psuList;
    ArrayList<Case> caseList;

    public MainGee()
    {
        cpuList     = new ArrayList<Cpu>();
        mbList      = new ArrayList<Mb>();
        ramList     = new ArrayList<Ram>();
        vgaList     = new ArrayList<Vga>();
        psuList     = new ArrayList<Psu>();
        caseList    = new ArrayList<Case>();

        init();
    }

    public void init()
    {
        // fetch things from mongodb for several list


        // tmp

        cpuList.add(new Cpu());
    }




}