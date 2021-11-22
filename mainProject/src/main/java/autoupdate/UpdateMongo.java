package autoupdate;

import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;

public class UpdateMongo 
{

    private MongoClient mongoClient;
    private MongoDatabase javaTestDB;

    private UpdateCollectionList collectionList;

    public UpdateMongo()
    {
        collectionList = new UpdateCollectionList();
    }

    public void updateMongo(UpdateHardwareList list)
    {
        try
        {
            mongoClient = MongoClients.create("mongodb://javaUser:noHackPlz@ec2-54-150-211-20.ap-northeast-1.compute.amazonaws.com/?authSource=javaTest2");
    
            javaTestDB = mongoClient.getDatabase("javaTest2");

            collectionList.setCollection(javaTestDB);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        try
        {
            collectionList.insertDocuments(list, javaTestDB);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
}