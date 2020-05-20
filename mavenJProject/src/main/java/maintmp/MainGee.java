package maintmp;

import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;

public class MainGee 
{
    HardwareList originList;

    HardwareNameList nameList;

    MongoClient mongoClient;
    MongoDatabase javaTestDB;

    CollectionList collectionList;

    public MainGee()
    {
        originList = new HardwareList();

        collectionList = new CollectionList();

        init();

        nameList = new HardwareNameList();
    }

    public void init()
    {
        originList.clear();

        // fetch things from mongodb for several list

        // mongoClient = MongoClients.create("mongodb://localhost:27017");
        mongoClient = MongoClients.create("mongodb+srv://testJUser:test@testjavafinalproject-tlluw.gcp.mongodb.net/test?retryWrites=true&w=majority");

        // javaTestDB = mongoClient.getDatabase("javaTest");
        javaTestDB = mongoClient.getDatabase("testJavaFinalProject");

        collectionList.setCpuCollection(javaTestDB.getCollection("cpu"));
        collectionList.setMbCollection(javaTestDB.getCollection("mb"));
        collectionList.setCoolerCollection(javaTestDB.getCollection("cooler"));
        collectionList.setRamCollection(javaTestDB.getCollection("ram"));
        collectionList.setVgaCollection(javaTestDB.getCollection("vga"));
        collectionList.setDiskCollection(javaTestDB.getCollection("disk"));
        collectionList.setPsuCollection(javaTestDB.getCollection("psu"));
        collectionList.setCrateCollection(javaTestDB.getCollection("crate"));

        originList = collectionList.toHardwareList();

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
        HardwareList selectList = new HardwareList();
        HardwareList filteredList = new HardwareList();

        selectList.setHardware(selectedList, nameList, originList);

        filteredList.filterCpu(selectList, collectionList);
        filteredList.filterMb(selectList, collectionList);
        filteredList.filterCooler(selectList, collectionList);
        filteredList.filterRam(selectList, collectionList);
        filteredList.filterVga(selectList, collectionList);
        filteredList.filterDisk(selectList, collectionList);
        filteredList.filterPsu(selectList, collectionList);
        filteredList.filterCrate(selectList, collectionList);

        return HardwareNameList.toNameList(filteredList);
    }

    public HardwareList getHList(HardwareNameList selectedList)
    {
        HardwareList nowList = new HardwareList();

        nowList.setHardware(selectedList, nameList, originList);

        return nowList;
    }
}
