package maintmp;

import com.mongodb.client.MongoCollection;

import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Updates.*;

import org.bson.BsonArray;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.*;

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
            // else
            // {
            //     this.cpuList.addAll(originList.cpuList);
            // }

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
                    this.coolerList.add(originList.coolerList.get(nameList.cooler.indexOf(i)));
                }
            }

            if(!selectedList.ram.isEmpty())
            {
                for(String i : selectedList.ram)
                {
                    this.ramList.add(originList.ramList.get(nameList.ram.indexOf(i)));
                }
            }

            if(!selectedList.vga.isEmpty())
            {
                for(String i : selectedList.vga)
                {
                    this.vgaList.add(originList.vgaList.get(nameList.vga.indexOf(i)));
                }
            }

            if(!selectedList.disk.isEmpty())
            {
                for(String i : selectedList.disk)
                {
                    this.diskList.add(originList.diskList.get(nameList.disk.indexOf(i)));
                }
            }

            if(!selectedList.psu.isEmpty())
            {
                for(String i : selectedList.psu)
                {
                    this.psuList.add(originList.psuList.get(nameList.psu.indexOf(i)));
                }
            }

            if(!selectedList.crate.isEmpty())
            {
                for(String i : selectedList.crate)
                {
                    this.crateList.add(originList.crateList.get(nameList.crate.indexOf(i)));
                }
            }
        }
        catch(Exception e)
        {
            System.out.println("ERROR: No such hardware!");

            this.clear();
        }
    }

    public void filterCpu(HardwareList selected, CollectionList db, HardwareList origin)
    {
        if(!selected.cpuList.isEmpty())
        {
            this.cpuList.addAll(origin.cpuList);

            return;
        }

        MongoCollection<Document> collection = db.getCpuCollection();

        // System.out.println(this.cpuList.get(0).getPin());

        if(!selected.mbList.isEmpty())
        {
            for(Document d: collection.find(eq("pin", selected.mbList.get(0).getPin())))
            {
                this.cpuList.add(Cpu.toObject(d));
            }
        }
    }

    public void filterMb(HardwareList selected, CollectionList db, HardwareList origin)
    {
        if(!selected.mbList.isEmpty())
        {
            this.mbList.addAll(origin.mbList);

            return;
        }
        
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
                if(d.getDiskType() == "sata")
                {
                    sataDisk += 1;
                }

                if(d.getDiskType() == "m.2")
                {
                    m2Disk += 1;

                    if(m2Type == "default")
                    {
                        m2Type = d.getSize();
                    }
                    else if(m2Type != d.getSize())
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

                default:

                    break;
            }

            filters.add(or(crateFilters));
        }

        for(Document d : collection.find(and(filters)))
        {
            mbList.add(Mb.toObject(d));
        }
    }

    public void filterCooler(HardwareList selected, CollectionList db, HardwareList origin)
    {
        if(!selected.coolerList.isEmpty())
        {
            this.coolerList.addAll(origin.coolerList);

            return;
        }
        
        MongoCollection<Document> collection = db.getCoolerCollection();

        ArrayList<Bson> filters = new ArrayList<Bson>();
        
        if(!selected.crateList.isEmpty())
        {
            filters.add(lte("height", selected.crateList.get(0).getCoolerHeight()));        
        }

        for(Document d : collection.find(and(filters)))
        {
            coolerList.add(Cooler.toObject(d));
        }
    }

    public void filterRam(HardwareList selected, CollectionList db, HardwareList origin)
    {
        if(!selected.ramList.isEmpty())
        {
            this.ramList.addAll(origin.ramList);

            return;
        }
        
        MongoCollection<Document> collection = db.getRamCollection();

        ArrayList<Bson> filters = new ArrayList<Bson>();
        
        // if(!selected.cpuList.isEmpty())
        // {
        //     filters.add(eq("pin", selected.cpuList.get(0).getPin()));        
        // }

        // for(Document d : collection.find(and(filters)))
        // {
        //     mbList.add(Mb.toObject(d));
        // }
    }

    public void filterVga(HardwareList selected, CollectionList db, HardwareList origin)
    {
        if(!selected.vgaList.isEmpty())
        {
            this.vgaList.addAll(origin.vgaList);

            return;
        }
        
        MongoCollection<Document> collection = db.getVgaCollection();

        // ArrayList<Bson> filters = new ArrayList<Bson>();
        
        // if(!selected.cpuList.isEmpty())
        // {
        //     filters.add(eq("pin", selected.cpuList.get(0).getPin()));        
        // }

        // for(Document d : collection.find(and(filters)))
        // {
        //     mbList.add(Mb.toObject(d));
        // }
    }

    public void filterDisk(HardwareList selected, CollectionList db, HardwareList origin)
    {
        if(!selected.diskList.isEmpty())
        {
            this.diskList.addAll(origin.diskList);

            return;
        }
        
        MongoCollection<Document> collection = db.getDiskCollection();

        // ArrayList<Bson> filters = new ArrayList<Bson>();
        
        // if(!selected.cpuList.isEmpty())
        // {
        //     filters.add(eq("pin", selected.cpuList.get(0).getPin()));        
        // }

        // for(Document d : collection.find(and(filters)))
        // {
        //     mbList.add(Mb.toObject(d));
        // }
    }
    
    public void filterPsu(HardwareList selected, CollectionList db, HardwareList origin)
    {
        if(!selected.psuList.isEmpty())
        {
            this.psuList.addAll(origin.psuList);

            return;
        }
        
        MongoCollection<Document> collection = db.getPsuCollection();

        // ArrayList<Bson> filters = new ArrayList<Bson>();
        
        // if(!selected.cpuList.isEmpty())
        // {
        //     filters.add(eq("pin", selected.cpuList.get(0).getPin()));        
        // }

        // for(Document d : collection.find(and(filters)))
        // {
        //     mbList.add(Mb.toObject(d));
        // }
    }

    public void filterCrate(HardwareList selected, CollectionList db, HardwareList origin)
    {
        if(!selected.crateList.isEmpty())
        {
            this.crateList.addAll(origin.crateList);

            return;
        }
        
        MongoCollection<Document> collection = db.getCrateCollection();

        // ArrayList<Bson> filters = new ArrayList<Bson>();
        
        // if(!selected.cpuList.isEmpty())
        // {
        //     filters.add(eq("pin", selected.cpuList.get(0).getPin()));        
        // }

        // for(Document d : collection.find(and(filters)))
        // {
        //     mbList.add(Mb.toObject(d));
        // }
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