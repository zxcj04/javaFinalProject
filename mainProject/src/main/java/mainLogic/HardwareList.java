package mainlogic;

import com.mongodb.client.MongoCollection;

import static com.mongodb.client.model.Filters.*;

import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.ArrayList;

public class HardwareList 
{
    public ArrayList<Cpu>      cpuList;
    public ArrayList<Mb>       mbList;
    public ArrayList<Cooler>   coolerList;
    public ArrayList<Ram>      ramList;
    public ArrayList<Vga>      vgaList;
    public ArrayList<Disk>     diskList;
    public ArrayList<Psu>      psuList;
    public ArrayList<Crate>    crateList;

    public HardwareList()
    {
        cpuList     = new ArrayList<Cpu>();
        mbList      = new ArrayList<Mb>();
        coolerList  = new ArrayList<Cooler>();
        ramList     = new ArrayList<Ram>();
        vgaList     = new ArrayList<Vga>();
        diskList    = new ArrayList<Disk>();
        psuList     = new ArrayList<Psu>();
        crateList   = new ArrayList<Crate>();
    }

    public HardwareList(HardwareList that)
    {
        cpuList     = new ArrayList<Cpu>(that.cpuList);
        mbList      = new ArrayList<Mb>(that.mbList);
        coolerList  = new ArrayList<Cooler>(that.coolerList);
        ramList     = new ArrayList<Ram>(that.ramList);
        vgaList     = new ArrayList<Vga>(that.vgaList);
        diskList    = new ArrayList<Disk>(that.diskList);
        psuList     = new ArrayList<Psu>(that.psuList);
        crateList   = new ArrayList<Crate>(that.crateList);
    }

    public void clear()
    {
        cpuList.clear();
        mbList.clear();
        coolerList.clear();
        ramList.clear();
        vgaList.clear();
        diskList.clear();
        psuList.clear();
        crateList.clear();
    }

    /**
     * From NameList to HardwareList
     */
    public void setHardware(HardwareNameList selectedList,HardwareNameList nameList , HardwareList originList)
    {
        try
        {
            if(!selectedList.cpu.isEmpty())
            {
                for(String i : selectedList.cpu)
                {
                    this.cpuList.add(originList.cpuList.get(nameList.cpu.indexOf(i)));
                }
            }

            if(!selectedList.mb.isEmpty())
            {
                for(String i : selectedList.mb)
                {
                    this.mbList.add(originList.mbList.get(nameList.mb.indexOf(i)));
                }
            }

            if(!selectedList.cooler.isEmpty())
            {
                for(String i : selectedList.cooler)
                {
                    if(i.indexOf("custom") == 0)
                    {
                        // custom 14cm

                        String name[] = i.toLowerCase().split(" ");

                        this.coolerList.add(new Cooler(i, Integer.parseInt(name[1].substring(0, name[1].length() - 2))));

                        continue;
                    }

                    this.coolerList.add(originList.coolerList.get(nameList.cooler.indexOf(i)));
                }
            }

            if(!selectedList.ram.isEmpty())
            {
                for(String i : selectedList.ram)
                {
                    if(i.indexOf("custom") == 0)
                    {
                    //     // custom ddr3 8g

                        String name[] = i.toLowerCase().split(" ");

                        this.ramList.add(new Ram(i, name[1]
                                , Integer.parseInt(name[2].substring(0, name[2].length() - 1))));

                        continue;
                    }
                    
                    this.ramList.add(originList.ramList.get(nameList.ram.indexOf(i)));
                }
            }

            if(!selectedList.vga.isEmpty())
            {
                for(String i : selectedList.vga)
                {
                    if(i.indexOf("custom") == 0)
                    {
                    //     // custom 1cm 1W

                        String name[] = i.toLowerCase().split(" ");

                        this.vgaList.add(new Vga(i, Integer.parseInt(name[1].substring(0, name[1].length() - 2))
                                    , Integer.parseInt(name[2].substring(0, name[2].length() - 1))));

                        continue;
                    }
                    
                    this.vgaList.add(originList.vgaList.get(nameList.vga.indexOf(i)));
                }
            }

            if(!selectedList.disk.isEmpty())
            {
                for(String i : selectedList.disk)
                {
                    if(i.indexOf("custom") == 0)
                    {
                        // custom m.2 pcie 1G

                        String name[] = i.toLowerCase().split(" ");

                        int capacity = Integer.parseInt(name[3].substring(0, name[3].length() -1));

                        capacity = (name[3].indexOf("t") == -1)? capacity: 1000 * capacity;

                        this.diskList.add(new Disk(i, name[1], capacity, name[2]));
                        //         Integer.parseInt(name[2].substring(0, name[2].length() - 1))));

                        continue;
                    }
                    
                    this.diskList.add(originList.diskList.get(nameList.disk.indexOf(i)));
                }
            }

            if(!selectedList.psu.isEmpty())
            {
                for(String i : selectedList.psu)
                {
                    if(i.indexOf("custom") == 0)
                    {
                        // custom 1cm 1W ATX

                        String name[] = i.toLowerCase().split(" ");

                        this.psuList.add(new Psu(i, Integer.parseInt(name[1].substring(0, name[1].length() - 2)), Integer.parseInt(name[2].substring(0, name[2].length() - 1)), name[3]));

                        continue;
                    }
                    
                    this.psuList.add(originList.psuList.get(nameList.psu.indexOf(i)));
                }
            }

            if(!selectedList.crate.isEmpty())
            {
                for(String i : selectedList.crate)
                {
                    if(i.indexOf("custom") == 0)
                    {
                        // custom ATX 1cm ATX 1cm 1cm 1個

                        String name[] = i.toLowerCase().split(" ");

                        this.crateList.add(new Crate(i, name[1]
                                        , Integer.parseInt(name[2].substring(0, name[2].length() - 2))
                                        , name[3]
                                        , Integer.parseInt(name[5].substring(0, name[5].length() - 2))
                                        , Integer.parseInt(name[6].substring(0, name[6].length() - 1))
                                        , Integer.parseInt(name[4].substring(0, name[4].length() - 2))));

                        continue;
                    }
                    
                    this.crateList.add(originList.crateList.get(nameList.crate.indexOf(i)));
                }
            }
        }
        catch(Exception e)
        {
            System.out.println("ERROR: ");

            for(StackTraceElement i : e.getStackTrace())
            {
                System.out.println(i);
            }

            System.out.println(e.getMessage());

            this.clear();
        }
    }

    public void filterCpu(HardwareList selected, CollectionList db)
    {
        // if(!selected.cpuList.isEmpty())
        // {
        //     this.cpuList.addAll(origin.cpuList);

        //     return;
        // }

        MongoCollection<Document> collection = db.getCpuCollection();

        ArrayList<Bson> filters = new ArrayList<Bson>();

        if(!selected.mbList.isEmpty())
        {
            filters.add(eq("pin", selected.mbList.get(0).getPin()));
        }

        if(!selected.ramList.isEmpty())
        {
            int ramCapacity = 0;

            for(Ram r : selected.ramList)
            {
                ramCapacity += r.getCapacity();
            }

            filters.add(gte("ramMaximumSupport", ramCapacity));
            filters.add(eq("ramGenerationSupport", selected.ramList.get(0).getRamType()));
        }
        
        if(filters.isEmpty())
        {
            filters.add(or(eq("", ""), ne("", "")));
        }

        for(Document d: collection.find(and(filters)))
        {
            this.cpuList.add(Cpu.toObject(d));
        }
    }

    public void filterMb(HardwareList selected, CollectionList db)
    {
        // if(!selected.mbList.isEmpty())
        // {
        //     this.mbList.addAll(origin.mbList);

        //     return;
        // }
        
        MongoCollection<Document> collection = db.getMbCollection();

        ArrayList<Bson> filters = new ArrayList<Bson>();
        
        if(!selected.cpuList.isEmpty())
        {
            filters.add(eq("pin", selected.cpuList.get(0).getPin()));        
        }

        if(!selected.ramList.isEmpty())
        {
            int ramCapacity = 0;

            for(Ram r : selected.ramList)
            {
                ramCapacity += r.getCapacity();
            }

            filters.add(eq("ramType", selected.ramList.get(0).getRamType()));    
            filters.add(gte("ramMaximum", ramCapacity));
            filters.add(gte("ramQuantity", selected.ramList.size()));
        }

        if(!selected.vgaList.isEmpty())
        {
            filters.add(gte("pcieQuantity", selected.vgaList.size()));
        }

        if(!selected.diskList.isEmpty())
        {
            int sataDisk = 0;
            int m2Disk = 0;
            String m2Type = "default";

            for(Disk d : selected.diskList)
            {
                if(d.getDiskType() == "sata" && d.getSize() != "m.2")
                {
                    sataDisk += 1;
                }

                if(d.getSize() == "m.2")
                {
                    m2Disk += 1;

                    if(m2Type == "default")
                    {
                        m2Type = d.getDiskType();
                    }
                    else if(m2Type != d.getDiskType())
                    {
                        m2Type = "pcie/sata";
                    }
                }
            }

            if(m2Type == "pcie")
            {
                filters.add(or(eq("m2Type", "pcie"), eq("m2Type", "pcie/sata")));
            }
            else if(m2Type == "sata")
            {
                filters.add(or(eq("m2Type", "sata"), eq("m2Type", "pcie/sata")));
            }
            
            filters.add(gte("sata3Quantity", sataDisk));
            filters.add(gte("m2Quantity", m2Disk));
        }
        
        if(!selected.crateList.isEmpty())
        {
            ArrayList<Bson> crateFilters = new ArrayList<Bson>();

            switch(selected.crateList.get(0).getMbSize())
            {
                case "eatx":
                    crateFilters.add(eq("size", "eatx"));

                case "atx":
                    crateFilters.add(eq("size", "atx"));

                case "matx":
                    crateFilters.add(eq("size", "matx"));

                case "itx":
                    crateFilters.add(eq("size", "itx"));

                    break;

                default:

                    break;
            }

            if(crateFilters.isEmpty())
            {
                crateFilters.add(or(eq("", ""), ne("", "")));
            }
            
            filters.add(or(crateFilters));
        }
        
        if(filters.isEmpty())
        {
            filters.add(or(eq("", ""), ne("", "")));
        }

        for(Document d : collection.find(and(filters)))
        {
            mbList.add(Mb.toObject(d));
        }
    }

    public void filterCooler(HardwareList selected, CollectionList db)
    {
        // if(!selected.coolerList.isEmpty())
        // {
        //     this.coolerList.addAll(origin.coolerList);

        //     return;
        // }
        
        MongoCollection<Document> collection = db.getCoolerCollection();

        ArrayList<Bson> filters = new ArrayList<Bson>();
        
        if(!selected.crateList.isEmpty())
        {
            filters.add(lte("height", selected.crateList.get(0).getCoolerHeight()));        
        }
        
        if(filters.isEmpty())
        {
            filters.add(or(eq("", ""), ne("", "")));
        }

        for(Document d : collection.find(and(filters)))
        {
            coolerList.add(Cooler.toObject(d));
        }
    }

    public void filterRam(HardwareList selected, CollectionList db)
    {
        // if(!selected.ramList.isEmpty())
        // {
        //     this.ramList.addAll(origin.ramList);

        //     return;
        // }
        
        MongoCollection<Document> collection = db.getRamCollection();

        ArrayList<Bson> filters = new ArrayList<Bson>();
        
        if(!selected.cpuList.isEmpty())
        {
            filters.add(eq("ramType", selected.cpuList.get(0).getRamGenerationSupport()));        
        }

        if(!selected.mbList.isEmpty())
        {
            filters.add(eq("ramType", selected.mbList.get(0).getRamType()));
        }

        if(selected.ramList.size() > 1)
        {
            filters.add(eq("ramType", selected.ramList.get(0).getRamType()));
        }

        if(filters.isEmpty())
        {
            filters.add(or(eq("", ""), ne("", "")));
        }

        for(Document d : collection.find(and(filters)))
        {
            ramList.add(Ram.toObject(d));
        }
    }

    public void filterVga(HardwareList selected, CollectionList db)
    {
        // if(!selected.vgaList.isEmpty())
        // {
        //     this.vgaList.addAll(origin.vgaList);

        //     return;
        // }
        
        MongoCollection<Document> collection = db.getVgaCollection();

        ArrayList<Bson> filters = new ArrayList<Bson>();
        
        if(!selected.mbList.isEmpty())
        {
            if(selected.mbList.get(0).getPcieQuantity() < 1)
                return;
        }

        if(!selected.crateList.isEmpty())
        {
            filters.add(lte("length", selected.crateList.get(0).getVgaLength()));
        }

        if(filters.isEmpty())
        {
            filters.add(or(eq("", ""), ne("", "")));
        }
        
        for(Document d : collection.find(and(filters)))
        {
            vgaList.add(Vga.toObject(d));
        }
    }

    public void filterDisk(HardwareList selected, CollectionList db)
    {
        // if(!selected.diskList.isEmpty())
        // {
        //     this.diskList.addAll(origin.diskList);

        //     return;
        // }
        
        MongoCollection<Document> collection = db.getDiskCollection();

        ArrayList<Bson> filters = new ArrayList<Bson>();
        
        if(filters.isEmpty())
        {
            filters.add(or(eq("", ""), ne("", "")));
        }

        for(Document d : collection.find(and(filters)))
        {
            diskList.add(Disk.toObject(d));
        }
    }
    
    public void filterPsu(HardwareList selected, CollectionList db)
    {
        // if(!selected.psuList.isEmpty())
        // {
        //     this.psuList.addAll(origin.psuList);

        //     return;
        // }
        
        MongoCollection<Document> collection = db.getPsuCollection();

        ArrayList<Bson> filters = new ArrayList<Bson>();
        
        if(!selected.cpuList.isEmpty() && !selected.vgaList.isEmpty())
        {
            int costTDP = 0;

            costTDP += selected.cpuList.get(0).getTDP();

            for(Vga v : selected.vgaList)
            {
                costTDP += v.getTDP();
            }

            filters.add(eq("watts", costTDP));        
        }

        if(!selected.crateList.isEmpty())
        {
            filters.add(eq("size", selected.crateList.get(0).getPsuSize()));
            filters.add(lte("length", selected.crateList.get(0).getPsuLength()));
        }
        
        if(filters.isEmpty())
        {
            filters.add(or(eq("", ""), ne("", "")));
        }

        for(Document d : collection.find(and(filters)))
        {
            psuList.add(Psu.toObject(d));
        }
    }

    public void filterCrate(HardwareList selected, CollectionList db)
    {
        // if(!selected.crateList.isEmpty())
        // {
        //     this.crateList.addAll(origin.crateList);

        //     return;
        // }
        
        MongoCollection<Document> collection = db.getCrateCollection();

        ArrayList<Bson> filters = new ArrayList<Bson>();
        
        if(!selected.mbList.isEmpty())
        {
            ArrayList<Bson> mbFilters = new ArrayList<Bson>();

            switch(selected.mbList.get(0).getSize())
            {
                case "itx":
                    mbFilters.add(eq("mbSize", "itx"));

                case "matx":
                    mbFilters.add(eq("mbSize", "matx"));

                case "atx":
                    mbFilters.add(eq("mbSize", "atx"));

                case "eatx":
                    mbFilters.add(eq("mbSize", "eatx"));

                    break;

                default:

                    break;
            }
            
            if(mbFilters.isEmpty())
            {
                mbFilters.add(or(eq("", ""), ne("", "")));
            }

            filters.add(or(mbFilters));        
        }

        if(!selected.coolerList.isEmpty())
        {
            filters.add(gte("coolerHeight", selected.coolerList.get(0).getHeight()));
        }

        if(!selected.vgaList.isEmpty())
        {
            int maxLength = selected.vgaList.get(0).getLength();

            for(Vga v : selected.vgaList)
            {
                if(v.getLength() > maxLength)
                    maxLength = v.getLength();
            }

            filters.add(gte("vgaLength", maxLength));
        }

        if(!selected.diskList.isEmpty())
        {
            int disk35 = 0;

            for(Disk d : selected.diskList)
            {
                if(d.getSize() == "3.5")
                {
                    disk35 += 1;
                }
            }

            filters.add(gte("diskQuantity", disk35));
        }

        if(!selected.psuList.isEmpty())
        {
            filters.add(gte("psuLength", selected.psuList.get(0).getLength()));
            filters.add(eq("psuSize", selected.psuList.get(0).getSize()));
        }
        
        if(filters.isEmpty())
        {
            filters.add(or(eq("", ""), ne("", "")));
        }

        for(Document d : collection.find(and(filters)))
        {
            crateList.add(Crate.toObject(d));
        }
    }

    @Override
    public String toString()
    {
        return String.format("cpu: %d, mb: %d, cooler: %d, ram: %d,%nvga: %d. disk: %d, psu: %d, crate: %d", 
                                cpuList.size(), 
                                mbList.size(), 
                                coolerList.size(), 
                                ramList.size(), 
                                vgaList.size(), 
                                diskList.size(), 
                                psuList.size(), 
                                crateList.size());
    }
}