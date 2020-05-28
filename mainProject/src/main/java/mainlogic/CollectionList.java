package mainlogic;

import com.mongodb.client.MongoCollection;

import org.bson.Document;

public class CollectionList 
{
    private MongoCollection<Document> cpuCollection;
    private MongoCollection<Document> mbCollection;
    private MongoCollection<Document> coolerCollection;
    private MongoCollection<Document> ramCollection;
    private MongoCollection<Document> vgaCollection;
    private MongoCollection<Document> diskCollection;
    private MongoCollection<Document> psuCollection;
    private MongoCollection<Document> crateCollection;

    public HardwareList toHardwareList()
    {
        HardwareList list = new HardwareList();

        for(Document cur : cpuCollection.find())
        {
            list.cpuList.add(Cpu.toObject(cur));
        }

        for(Document cur : mbCollection.find())
        {
            list.mbList.add(Mb.toObject(cur));
        }

        for(Document cur : coolerCollection.find())
        {
            list.coolerList.add(Cooler.toObject(cur));
        }

        for(Document cur : ramCollection.find())
        {
            list.ramList.add(Ram.toObject(cur));
        }

        for(Document cur : vgaCollection.find())
        {
            list.vgaList.add(Vga.toObject(cur));
        }

        for(Document cur : diskCollection.find())
        {
            list.diskList.add(Disk.toObject(cur));
        }

        for(Document cur : psuCollection.find())
        {
            list.psuList.add(Psu.toObject(cur));
        }

        for(Document cur : crateCollection.find())
        {
            list.crateList.add(Crate.toObject(cur));
        }

        return list;
    }

    public void setCpuCollection(MongoCollection<Document> cpuCollection)
    {
        this.cpuCollection = cpuCollection;
    }
    
    public void setMbCollection(MongoCollection<Document> mbCollection)
    {
        this.mbCollection = mbCollection;
    }
    
    public void setCoolerCollection(MongoCollection<Document> coolerCollection)
    {
        this.coolerCollection = coolerCollection;
    }
    
    public void setRamCollection(MongoCollection<Document> ramCollection)
    {
        this.ramCollection = ramCollection;
    }
    
    public void setVgaCollection(MongoCollection<Document> vgaCollection)
    {
        this.vgaCollection = vgaCollection;
    }
    
    public void setDiskCollection(MongoCollection<Document> diskCollection)
    {
        this.diskCollection = diskCollection;
    }
    
    public void setPsuCollection(MongoCollection<Document> psuCollection)
    {
        this.psuCollection = psuCollection;
    }
    
    public void setCrateCollection(MongoCollection<Document> crateCollection)
    {
        this.crateCollection = crateCollection;
    }

    public MongoCollection<Document> getCpuCollection()
    {
        return cpuCollection;
    }

    public MongoCollection<Document> getMbCollection()
    {
        return mbCollection;
    }

    public MongoCollection<Document> getCoolerCollection()
    {
        return coolerCollection;
    }

    public MongoCollection<Document> getRamCollection()
    {
        return ramCollection;
    }

    public MongoCollection<Document> getVgaCollection()
    {
        return vgaCollection;
    }

    public MongoCollection<Document> getDiskCollection()
    {
        return diskCollection;
    }

    public MongoCollection<Document> getPsuCollection()
    {
        return psuCollection;
    }

    public MongoCollection<Document> getCrateCollection()
    {
        return crateCollection;
    }

}