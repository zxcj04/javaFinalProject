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

        mongoClient = MongoClients.create("mongodb://localhost:27017");

        javaTestDB = mongoClient.getDatabase("javaTest");

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
        HardwareList nowList = new HardwareList();

        nowList.setHardware(selectedList, nameList, originList);

        

        return null;
    }

    public HardwareList getHList(HardwareNameList selectedList)
    {
        HardwareList nowList = new HardwareList();

        nowList.setHardware(selectedList, nameList, originList);

        return nowList;
    }
}
