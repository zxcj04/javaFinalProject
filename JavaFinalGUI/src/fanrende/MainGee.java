package fanrende;

//import com.mongodb.client.MongoClients;
//import com.mongodb.client.MongoClient;
//import com.mongodb.client.MongoDatabase;

import java.util.ArrayList;

public class MainGee 
{
//    HardwareList originList;
//
//    HardwareNameList nameList;
//
//    MongoClient mongoClient;
//    MongoDatabase javaTestDB;
//
//    CollectionList collectionList;
//
//    public MainGee()
//    {
//        originList = new HardwareList();
//
//        collectionList = new CollectionList();
//
//        init();
//
//        nameList = new HardwareNameList();
//    }
//
//    public void init()
//    {
//        originList.clear();
//
//        // fetch things from mongodb for several list
//
//        mongoClient = MongoClients.create("mongodb://localhost:27017");
//
//        javaTestDB = mongoClient.getDatabase("javaTest");
//
//        collectionList.setCpuCollection(javaTestDB.getCollection("cpu"));
//        collectionList.setMbCollection(javaTestDB.getCollection("mb"));
//        collectionList.setCoolerCollection(javaTestDB.getCollection("cooler"));
//        collectionList.setRamCollection(javaTestDB.getCollection("ram"));
//        collectionList.setVgaCollection(javaTestDB.getCollection("vga"));
//        collectionList.setDiskCollection(javaTestDB.getCollection("disk"));
//        collectionList.setPsuCollection(javaTestDB.getCollection("psu"));
//        collectionList.setCrateCollection(javaTestDB.getCollection("crate"));
//
//        originList = collectionList.toHardwareList();
//
//    }

    public HardwareNameList getList()
    {
//        if(!nameList.isEmpty())
//        {
//            return nameList;
//        }
//
//        nameList.clear();
//
//        nameList.addAll(originList);
    	

        return new HardwareNameList();
    }

    public HardwareNameList getList(HardwareNameList selectedList, boolean smart)
    {
//        HardwareList nowList = new HardwareList();
//
//        nowList.setHardware(selectedList, nameList, originList);

    	ArrayList<String> cpu     = new ArrayList<String>();
    	ArrayList<String> mb      = new ArrayList<String>();
    	ArrayList<String> cooler  = new ArrayList<String>();
    	ArrayList<String> ram     = new ArrayList<String>();
    	ArrayList<String> vga     = new ArrayList<String>();
    	ArrayList<String> disk    = new ArrayList<String>();
    	ArrayList<String> psu     = new ArrayList<String>();
    	ArrayList<String> crate   = new ArrayList<String>();
		
		cpu.add("1");
		cooler.add("2");
		mb.add("3");
		ram.add("4");
		disk.add("5");
		vga.add("6");
		psu.add("7");
		crate.add("8");
        
		cpu.add("A");
		cooler.add("B");
		mb.add("Ccccccccc");
		ram.add("D");
		disk.add("E");
		vga.add("F");
		psu.add("G");
		crate.add("H");

        return new HardwareNameList(cpu, cooler, mb, ram, disk, vga, psu, crate);
    }
//
//    public HardwareList getHList(HardwareNameList selectedList)
//    {
//        HardwareList nowList = new HardwareList();
//
//        nowList.setHardware(selectedList, nameList, originList);
//
//        return nowList;
//    }
}