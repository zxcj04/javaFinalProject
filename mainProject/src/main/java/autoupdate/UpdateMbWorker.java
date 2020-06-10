package autoupdate;

import java.util.ArrayList;

public class UpdateMbWorker implements Runnable
{
    String url;
    ArrayList<org.bson.Document> result;
    
    public UpdateMbWorker(String url,ArrayList<org.bson.Document> result)
    {
        this.url = url;
        this.result = result;
    }

    @Override
    public void run() 
    {
        result.add(UpdateMb.getInnerMessages(url));
    }    
}