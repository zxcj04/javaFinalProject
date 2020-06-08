package autoupdate;

import java.util.ArrayList;
import java.util.stream.StreamSupport;

import com.google.common.collect.Lists;
import com.mongodb.client.MongoDatabase;

import org.bson.Document;

import mainlogic.CollectionList;

public class UpdateCollectionList extends CollectionList
{
    public UpdateCollectionList()
    {
        super();
    }

    public void setCollection(MongoDatabase db)
    {
        setCpuCollection(db.getCollection("cpu"));
        setMbCollection(db.getCollection("mb"));
        setCoolerCollection(db.getCollection("cooler"));
        setRamCollection(db.getCollection("ram"));
        setVgaCollection(db.getCollection("vga"));
        setDiskCollection(db.getCollection("disk"));
        setPsuCollection(db.getCollection("psu"));
        setCrateCollection(db.getCollection("crate"));
    }

    public void insertDocuments(UpdateHardwareList list, MongoDatabase db)
    {
        ArrayList<String> names = Lists.newArrayList(db.listCollectionNames());
        
        if(names.contains("cpu"))
            getCpuCollection().drop();
        getCpuCollection().insertMany(list.cpuDocumentList);

        if(names.contains("mb"))
            getMbCollection().drop();
        getMbCollection().insertMany(list.mbDocumentList);

        if(names.contains("cooler"))
            getCoolerCollection().drop();
        getCoolerCollection().insertMany(list.coolerDocumentList);

        if(names.contains("ram"))
            getRamCollection().drop();

        getRamCollection().insertMany(list.ramDocumentList);

        if(names.contains("vga"))
            getVgaCollection().drop();
        getVgaCollection().insertMany(list.vgaDocumentList);

        if(names.contains("disk"))
            getDiskCollection().drop();
        getDiskCollection().insertMany(list.diskDocumentList);

        if(names.contains("psu"))
            getPsuCollection().drop();
        getPsuCollection().insertMany(list.psuDocumentList);

        if(names.contains("crate"))
            getCrateCollection().drop();
        getCrateCollection().insertMany(list.crateDocumentList);
    }
}