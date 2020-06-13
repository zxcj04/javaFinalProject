package autoupdate;

import java.util.ArrayList;

public class UpdateMbWorker implements Runnable
{
    private String url;
    private ArrayList<org.bson.Document> result;
    
    public UpdateMbWorker(String url,ArrayList<org.bson.Document> result)
    {
        this.url = url;
        this.result = result;
    }

    @Override
    public void run() 
    {
        try
        {
            org.bson.Document tmp = UpdateMb.getInnerMessages(url);

            if(tmp != null)
            {
                result.add(UpdateMb.getInnerMessages(url));
            }
        }
        catch(Exception e)
        {
            System.out.println("error working");
        }
    }    
}