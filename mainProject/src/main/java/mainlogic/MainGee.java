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
    private HardwareList originList;

    private HardwareNameList nameList;

    private MongoClient mongoClient;
    private MongoDatabase javaTestDB;

    private CollectionList collectionList;

    private String ramSelected;

    private String ramExceed;

    private String conflict;

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

        boolean localTest = false;
        
        try
        {
            if(localTest)
            {
                mongoClient = MongoClients.create("mongodb://localhost:27017");

                javaTestDB = mongoClient.getDatabase("javaTest2");
            }
            else
            {
                mongoClient = MongoClients.create("mongodb://javaUser:noHackPlz@ec2-54-150-211-20.ap-northeast-1.compute.amazonaws.com/?authSource=javaTest2");
    
                javaTestDB = mongoClient.getDatabase("javaTest2");

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

        ramSelected = "";
        conflict = "";
        ramExceed = "0";

        selectList.setHardware(selectedList, nameList, originList);

        suggestion.addAll(suggestCpu(selectList));
        suggestion.addAll(suggestMb(selectList));
        suggestion.addAll(suggestCooler(selectList));
        suggestion.addAll(suggestRam(selectList));
        suggestion.addAll(suggestVga(selectList));
        suggestion.addAll(suggestDisk(selectList));
        suggestion.addAll(suggestPsu(selectList));
        suggestion.addAll(suggestCrate(selectList));

        suggestion.add(conflict);
        suggestion.add(ramExceed);  // "1" "0"
        suggestion.add(ramSelected);

        // System.out.println("\t" + ramExceed);

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
            addConflict("mb");
            addConflict("cpu");

            suggest.add(String.format("CPU與主機板不符合歐\ncpu: '%s', mb: '%s'\n"
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
                addConflict("cpu");
                addConflict("ram");

                suggest.add(String.format("記憶體容量超出CPU最大支援的大小囉\nram capacity: %sG, cpu ramCapacity: %sG\n"
                                            , ramCapacity, selected.cpuList.get(0).getRamMaximumSupport()));
            }
            
            if(!selected.cpuList.get(0).getRamGenerationSupport().equals(selected.ramList.get(0).getRamType()))
            {
                addConflict("mb");
                addConflict("cpu");

                suggest.add(String.format("CPU支援的代數與記憶體不符合歐\ncpu generation: %s, ram generation: %s\n"
                                            , selected.cpuList.get(0).getRamGenerationSupport(), selected.ramList.get(0).getRamType()));
            }
        }

        ramSelected = selected.cpuList.get(0).getRamGenerationSupport();

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
                addConflict("mb");
                addConflict("ram");

                suggest.add(String.format("主機板支援的代數與記憶體不符合歐\nmb generation: %s, ram generation: %s\n"
                                            , selected.mbList.get(0).getRamType(), selected.ramList.get(0).getRamType()));
            }

            if(selected.mbList.get(0).getRamMaximum() < ramCapacity)
            {
                addConflict("mb");
                addConflict("ram");

                suggest.add(String.format("記憶體容量超出主機板最大支援的大小囉\nram capacity: %sG, mb ramCapacity: %sG\n"
                                , ramCapacity, selected.mbList.get(0).getRamMaximum()));
            }

            if(selected.mbList.get(0).getRamQuantity() < selected.ramList.size())
            {
                this.setRamExceed(true);
                
                addConflict("mb");
                addConflict("ram");

                suggest.add(String.format("主機板的記憶體插槽不夠插歐\nmb quantity: %s, ram quantity: %s\n"
                                            , selected.mbList.get(0).getRamQuantity(), selected.ramList.size()));
            }
            else if(selected.mbList.get(0).getRamQuantity() == selected.ramList.size())
            {
                this.setRamExceed(true);
            }
        }

        if(!selected.vgaList.isEmpty())
        {
            if(selected.mbList.get(0).getPcieQuantity() < selected.vgaList.size())
            {
                addConflict("mb");
                addConflict("vga");

                suggest.add(String.format("主機板的PCIE插槽不夠插顯卡囉\nmb pcie: %s, graphic cards: %s\n"
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

            if(m2Type != selected.mbList.get(0).getM2Type() && !selected.mbList.get(0).getM2Type().equals("pcie/sata") && !m2Type.equals("default"))
            {
                addConflict("mb");
                addConflict("disk");

                suggest.add(String.format("主機板的m.2插槽不能插這個m.2硬碟歐\nmb m.2 type: %s, disk type: %s\n"
                                            , selected.mbList.get(0).getM2Type(), m2Type));
            }

            if(selected.mbList.get(0).getSata3Quantity() < sataDisk)
            {
                addConflict("mb");
                addConflict("disk");

                suggest.add(String.format("主機板的sata接口不夠囉\nmb sata: %s, sata disk: %s\n"
                                            , selected.mbList.get(0).getSata3Quantity(), sataDisk));
            }
            
            if(selected.mbList.get(0).getM2Quantity() < m2Disk)
            {
                addConflict("mb");
                addConflict("disk");

                suggest.add(String.format("主機板的m.2接口不夠囉\nmb m.2: %s, m.2 disk: %s\n"
                                            , selected.mbList.get(0).getM2Quantity(), m2Disk));
            }
        }
        
        if(!selected.crateList.isEmpty())
        {
            ArrayList<String> mbSize = new ArrayList<String>(
                Arrays.asList("eatx", "atx", "matx", "itx"));

            if(mbSize.indexOf(selected.crateList.get(0).getMbSize()) > mbSize.indexOf(selected.mbList.get(0).getSize()))
            {
                addConflict("mb");
                addConflict("crate");

                suggest.add(String.format("機殼裝不下主機板歐\nmb size: %s, case size: %s\n"
                                            , selected.mbList.get(0).getSize(), selected.crateList.get(0).getMbSize()));
            }
        }

        ramSelected = selected.mbList.get(0).getRamType();

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
            addConflict("crate");
            addConflict("cooler");

            suggest.add(String.format("機殼裝不下CPU散熱器耶\ncase height: %s, cooler height: %s\n"
                                , selected.crateList.get(0).getCoolerHeight(), selected.coolerList.get(0).getHeight()));
        }

        return suggest;
    }

    public ArrayList<String> suggestRam(HardwareList selected)
    {
        ArrayList<String> suggest = new ArrayList<String>();

        if(selected.ramList.isEmpty())
        {
            return suggest;
        }

        if(selected.cpuList.isEmpty() && selected.mbList.isEmpty())
            ramSelected = selected.ramList.get(0).getRamType();

        return suggest;
    }

    public ArrayList<String> suggestVga(HardwareList selected)
    {
        ArrayList<String> suggest = new ArrayList<String>();

        if(selected.vgaList.isEmpty())
        {
            return suggest;
        }

        if(!selected.crateList.isEmpty())
        {
            int maxLength = selected.vgaList.get(0).getLength();

            for(Vga v : selected.vgaList)
            {
                if(v.getLength() > maxLength)
                    maxLength = v.getLength();
            }

            if(selected.crateList.get(0).getVgaLength() < maxLength)
            {
                addConflict("vga");
                addConflict("crate");

                suggest.add(String.format("有顯卡塞不進機殼唷\ncase length: %s, graphic card length: %d\n"
                        , selected.crateList.get(0).getVgaLength(), maxLength));
            }
        }

        return suggest;
    }

    public ArrayList<String> suggestDisk(HardwareList selected)
    {
        ArrayList<String> suggest = new ArrayList<String>();

        if(selected.diskList.isEmpty())
        {
            return suggest;
        }

        if(!selected.crateList.isEmpty())
        {
            int disk35 = 0;

            for(Disk d : selected.diskList)
            {
                if(d.getSize() == "3.5")
                {
                    disk35 += 1;
                }
            }

            if(selected.crateList.get(0).getDiskQuantity() < disk35)
            {
                addConflict("crate");
                addConflict("disk");

                suggest.add(String.format("機殼的3.5\"硬碟架不夠捏\ncase disk quantity: %s, 3.5\" disk quantity: %s\n"
                                , selected.crateList.get(0).getDiskQuantity(), disk35));
            }
        }

        return suggest;
    }

    public ArrayList<String> suggestPsu(HardwareList selected)
    {
        ArrayList<String> suggest = new ArrayList<String>();

        if(selected.psuList.isEmpty())
        {
            return suggest;
        }

        if(!selected.crateList.isEmpty())
        {
            if(!selected.psuList.get(0).getSize().equals(selected.crateList.get(0).getPsuSize()))
            {
                addConflict("crate");
                addConflict("psu");

                suggest.add(String.format("機殼跟電供的size不合><\ncase psu size: %s, psu size: %s\n"
                    , selected.crateList.get(0).getPsuSize(), selected.psuList.get(0).getSize()));
            }

            if(selected.psuList.get(0).getLength() > selected.crateList.get(0).getPsuLength())
            {
                addConflict("crate");
                addConflict("psu");

                suggest.add(String.format("機殼裝不下電供啦\ncase psu length: %s, psu length: %s\n"
                    , selected.crateList.get(0).getPsuLength(), selected.psuList.get(0).getLength()));
            }
        }

        return suggest;
    }

    public ArrayList<String> suggestCrate(HardwareList selected)
    {
        ArrayList<String> suggest = new ArrayList<String>();

        if(selected.crateList.isEmpty())
        {
            return suggest;
        }

        return suggest;
    }

    public void setRamExceed(Boolean value)
    {
        this.ramExceed = (value == true)? "1": "0";
    }

    public void addConflict(String value)
    {
        if(this.conflict.equals(""))
        {
            this.conflict = value;
        }
        else
        {
            this.conflict +=  " " + value;
        }
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
