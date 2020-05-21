package maintmp;

import com.mongodb.client.MongoClients;
import com.mongodb.MongoSocketReadException;
import com.mongodb.MongoTimeoutException;
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

        // init();

        nameList = new HardwareNameList();
    }

    public void init()
    {
        originList.clear();

        // fetch things from mongodb for several list

        boolean localTest = true;
        
        try
        {
            if(localTest)
            {
                mongoClient = MongoClients.create("mongodb://localhost:27017");

                javaTestDB = mongoClient.getDatabase("javaTest2");
            }
            else
            {
                mongoClient = MongoClients.create("mongodb+srv://testJUser:test@testjavafinalproject-tlluw.gcp.mongodb.net/test?retryWrites=true&w=majority");
    
                javaTestDB = mongoClient.getDatabase("testJavaFinalProject");
            }

            collectionList.setCpuCollection(javaTestDB.getCollection("cpu"));
            collectionList.setMbCollection(javaTestDB.getCollection("mb"));
            collectionList.setCoolerCollection(javaTestDB.getCollection("cooler"));
            collectionList.setRamCollection(javaTestDB.getCollection("ram"));
            collectionList.setVgaCollection(javaTestDB.getCollection("vga"));
            collectionList.setDiskCollection(javaTestDB.getCollection("disk"));
            collectionList.setPsuCollection(javaTestDB.getCollection("psu"));
            collectionList.setCrateCollection(javaTestDB.getCollection("crate"));
        }
        catch(MongoTimeoutException | MongoSocketReadException e)
        {
            System.out.println("Timeout Connecting Remote Mongo");

            try 
            {
                mongoClient = MongoClients.create("mongodb://localhost:27017");
            } 
            catch (Exception eIn)
            {
                throw new RuntimeException("ERROR Connecting Local Mongo");
            }            
        }
        catch (Throwable throwable) 
        {
            System.out.println("ERROR during fetching data: " + throwable.getMessage());

            return;
        }
        // catch(Exception e)
        // {
        //     System.out.println("ERROR during fetching data: " + e.getMessage());

        //     return;
        // }

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

        filteredList.filterCpu(selectList, collectionList, originList);
        filteredList.filterMb(selectList, collectionList, originList);
        filteredList.filterCooler(selectList, collectionList, originList);
        filteredList.filterRam(selectList, collectionList, originList);
        filteredList.filterVga(selectList, collectionList, originList);
        filteredList.filterDisk(selectList, collectionList, originList);
        filteredList.filterPsu(selectList, collectionList, originList);
        filteredList.filterCrate(selectList, collectionList, originList);

        return HardwareNameList.toNameList(filteredList);
    }

    public HardwareList getHList(HardwareNameList selectedList)
    {
        HardwareList nowList = new HardwareList();

        nowList.setHardware(selectedList, nameList, originList);

        return nowList;
    }
}
