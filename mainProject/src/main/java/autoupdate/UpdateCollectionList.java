package autoupdate;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.StreamSupport;

import com.google.common.collect.Lists;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.ReplaceOptions;

import static com.mongodb.client.model.Filters.*;

import org.bson.Document;

import mainlogic.CollectionList;

public class UpdateCollectionList extends CollectionList {
    public UpdateCollectionList() {
        super();
    }

    public void setCollection(MongoDatabase db) {
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
        ExecutorService executor = Executors.newCachedThreadPool();
        
        // executor.execute(new UpdateInsertWorker(list.cpuDocumentList      , db, "cpu"   ));
        // executor.execute(new UpdateInsertWorker(list.coolerDocumentList   , db, "cooler"));
        // executor.execute(new UpdateInsertWorker(list.mbDocumentList       , db, "mb"    ));
        // executor.execute(new UpdateInsertWorker(list.ramDocumentList      , db, "ram"   ));
        // executor.execute(new UpdateInsertWorker(list.diskDocumentList     , db, "disk"  ));
        // executor.execute(new UpdateInsertWorker(list.vgaDocumentList      , db, "vga"   ));
        // executor.execute(new UpdateInsertWorker(list.psuDocumentList      , db, "psu"   ));
        // executor.execute(new UpdateInsertWorker(list.crateDocumentList    , db, "crate" ));

        executor.shutdown();

        // while(!executor.isTerminated());

        insertOneDocument(list.cpuDocumentList      , db, "cpu"   );
        insertOneDocument(list.coolerDocumentList   , db, "cooler");
        insertOneDocument(list.mbDocumentList       , db, "mb"    );
        insertOneDocument(list.ramDocumentList      , db, "ram"   );
        insertOneDocument(list.diskDocumentList     , db, "disk"  );
        insertOneDocument(list.vgaDocumentList      , db, "vga"   );
        insertOneDocument(list.psuDocumentList      , db, "psu"   );
        insertOneDocument(list.crateDocumentList    , db, "crate" );

        // MongoCollection<Document> collection = db.getCollection("cpu");

        // FindIterable<Document> exist = collection.find(eq("name", "AMD Ryzen 3 1300X"));

        // if(exist.first() == null)
        // {
        //     System.out.println(exist.first());
        // }
        // else
        // {
        //     System.out.println("already exists : " + exist.first());
        // }
    }

    private class UpdateInsertWorker implements Runnable 
    {
        ArrayList<Document> documentList;
        MongoDatabase db;
        String collectionName;

        public UpdateInsertWorker(ArrayList<Document> documentList, MongoDatabase db, String collectionName)
        {
            this.documentList = documentList;
            this.db = db;
            this.collectionName = collectionName;
        }

        @Override
        public void run()
        {
            insertOneDocument(documentList, db, collectionName);
        }
    }

    private void insertOneDocument(ArrayList<Document> documentList, MongoDatabase db, String collectionName)
    {
        MongoCollection<Document> collection = db.getCollection(collectionName);

        // FindIterable<Document> exist;
        
        // for(Document d : documentList)
        // {
        //     exist = collection.find(eq("name", d.getString("name")));

        //     if(exist.first() == null)
        //     {
        //         collection.insertOne(d);
        //     }
        //     else
        //     {
        //         System.out.println("already exists : " + d.getString("name"));
        //     }
        // }

        for(Document d : documentList)
        {
            collection.replaceOne(eq("name", d.getString("name")), d, new ReplaceOptions().upsert(true));
        }
    }

    public void insertDocuments_old(UpdateHardwareList list, MongoDatabase db)
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