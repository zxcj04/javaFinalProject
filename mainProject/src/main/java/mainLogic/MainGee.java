package mainlogic;

import com.mongodb.client.MongoClients;

import static com.mongodb.client.model.Filters.*;

import java.util.ArrayList;
import java.util.Arrays;

import com.mongodb.MongoSocketReadException;
import com.mongodb.MongoTimeoutException;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;

import org.bson.Document;

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
                // mongoClient = MongoClients.create("mongodb://rende:rende0304@ds017636.mlab.com:17636/heroku_g27s6wk1");
                mongoClient = MongoClients.create("ec2-54-248-132-195.ap-northeast-1.compute.amazonaws.com");
    
                javaTestDB = mongoClient.getDatabase("heroku_g27s6wk1");

                System.out.println("Remote Okay!");
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

    public ArrayList<String> getSuggestion(HardwareNameList selectedList)
    {
        HardwareList selectList = new HardwareList();
        
        ArrayList<String> suggestion = new ArrayList<String>();

        selectList.setHardware(selectedList, nameList, originList);

        suggestion.addAll(suggestCpu(selectList));
        suggestion.addAll(suggestMb(selectList));
        suggestion.addAll(suggestCooler(selectList));
        // suggestion.addAll(suggestRam(selectList));
        // suggestion.addAll(suggestVga(selectList));
        // suggestion.addAll(suggestDisk(selectList));
        // suggestion.addAll(suggestPsu(selectList));
        // suggestion.addAll(suggestCrate(selectList));

        return suggestion;
    }

    public HardwareList getHList(HardwareNameList selectedList)
    {
        HardwareList nowList = new HardwareList();

        nowList.setHardware(selectedList, nameList, originList);

        return nowList;
    }

    public ArrayList<String> suggestCpu(HardwareList selected)
    {        
        ArrayList<String> suggest = new ArrayList<String>();

        if(selected.cpuList.isEmpty())
            return suggest;

        if(!selected.mbList.isEmpty() && !selected.cpuList.get(0).getPin().equals(selected.mbList.get(0).getPin()))
        {
            suggest.add(String.format("CPU與主機板不符合歐 cpu: '%s', mb: '%s'"
                                      , selected.cpuList.get(0).getPin(), selected.mbList.get(0).getPin()));
        }

        if(!selected.ramList.isEmpty())
        {
            int ramCapacity = 0;

            for(Ram r : selected.ramList)
            {
                ramCapacity += r.getCapacity();
            }

            if(ramCapacity > selected.cpuList.get(0).getRamMaximumSupport())
            {
                suggest.add(String.format("記憶體容量超出CPU最大支援的大小囉 ram capacity: %sG, cpu ramCapacity: %sG"
                                            , ramCapacity, selected.cpuList.get(0).getRamMaximumSupport()));
            }
            
            if(!selected.cpuList.get(0).getRamGenerationSupport().equals(selected.ramList.get(0).getRamType()))
            {
                suggest.add(String.format("CPU支援的代數與記憶體不符合歐 cpu generation: %s, ram generation: %s"
                                            , selected.cpuList.get(0).getRamGenerationSupport(), selected.ramList.get(0).getRamType()));
            }
        }

        return suggest;
    }

    public ArrayList<String> suggestMb(HardwareList selected)
    {        
        ArrayList<String> suggest = new ArrayList<String>();

        if(selected.mbList.isEmpty())
            return suggest;

        if(!selected.ramList.isEmpty())
        {
            int ramCapacity = 0;

            for(Ram r : selected.ramList)
            {
                ramCapacity += r.getCapacity();
            }

            if(!selected.mbList.get(0).getRamType().equals(selected.ramList.get(0).getRamType()))
            {
                suggest.add(String.format("主機板支援的代數與記憶體不符合歐 mb generation: %s, ram generation: %s"
                                            , selected.mbList.get(0).getRamType(), selected.ramList.get(0).getRamType()));
            }

            if(selected.mbList.get(0).getRamMaximum() < ramCapacity)
            {
                suggest.add(String.format("記憶體容量超出主機板最大支援的大小囉 ram capacity: %sG, mb ramCapacity: %sG"
                                , ramCapacity, selected.mbList.get(0).getRamMaximum()));
            }

            if(selected.mbList.get(0).getRamQuantity() < selected.ramList.size())
            {
                suggest.add(String.format("主機板的記憶體插槽不夠插歐 mb quantity: %s, ram quantity: %s"
                                            , selected.mbList.get(0).getRamQuantity(), selected.ramList.size()));
            }
        }

        if(!selected.vgaList.isEmpty())
        {
            if(selected.mbList.get(0).getPcieQuantity() < selected.vgaList.size())
            {
                suggest.add(String.format("主機板的PCIE插槽不夠插顯卡囉 mb pcie: %s, graphic cards: %s"
                                            , selected.mbList.get(0).getPcieQuantity(), selected.vgaList.size()));
            }
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

            if(m2Type != selected.mbList.get(0).getM2Type() && !selected.mbList.get(0).getM2Type().equals("pcie/sata"))
            {
                suggest.add(String.format("主機板的m.2插槽不能插這個m.2硬碟歐 mb m.2 type: %s, disk type: %s"
                                            , selected.mbList.get(0).getM2Type(), m2Type));
            }

            if(selected.mbList.get(0).getSata3Quantity() < sataDisk)
            {
                suggest.add(String.format("主機板的sata接口不夠囉 mb sata: %s, sata disk: %s"
                                            , selected.mbList.get(0).getSata3Quantity(), sataDisk));
            }
            
            if(selected.mbList.get(0).getM2Quantity() < m2Disk)
            {
                suggest.add(String.format("主機板的m.2接口不夠囉 mb m.2: %s, m.2 disk: %s"
                                            , selected.mbList.get(0).getM2Quantity(), m2Disk));
            }
        }
        
        if(!selected.crateList.isEmpty())
        {
            ArrayList<String> mbSize = new ArrayList<String>(
                Arrays.asList("eatx", "atx", "matx", "itx"));

            if(mbSize.indexOf(selected.crateList.get(0).getMbSize()) > mbSize.indexOf(selected.mbList.get(0).getSize()))
            {
                suggest.add(String.format("機殼裝不下主機板歐 mb size: %s, case size: %s"
                                            , selected.mbList.get(0).getSize(), selected.crateList.get(0).getMbSize()));
            }
        }

        return suggest;
    }

    public ArrayList<String> suggestCooler(HardwareList selected)
    {
        ArrayList<String> suggest = new ArrayList<String>();

        if(selected.coolerList.isEmpty())
        {
            return suggest;
        }

        if(!selected.crateList.isEmpty() && selected.crateList.get(0).getCoolerHeight() < selected.coolerList.get(0).getHeight())
        {
            suggest.add(String.format("機殼裝不下CPU散熱器耶 case height: %s, cooler height: %s"
                                , selected.crateList.get(0).getCoolerHeight(), selected.coolerList.get(0).getHeight()));
        }

        return suggest;
    }



    public void test()
    {
        Document d = new Document("name", "test2Mb")
                        .append("size", "atx")
                        .append("pin", "am4")
                        .append("supportCpu", "ryzen")
                        .append("RJ45", "1")
                        .append("graphicOutput", "dvi/hdmi")
                        .append("usbQuantity", 4)
                        .append("pcieQuantity", 2)
                        .append("ramType", "ddr4")
                        .append("ramQuantity", 4)
                        .append("ramMaximum", 64)
                        .append("wifi", "0")
                        .append("sata3Quantity", 6)
                        .append("m2Type", "pcie/sata")
                        .append("m2Quantity", 1);
        
        collectionList.getMbCollection().insertOne(d);
    }

    public void testRemove()
    {
        collectionList.getMbCollection().deleteMany(eq("name", "test2Mb"));
    }
}
