package autoupdate;

import java.util.ArrayList;

public class UpdateCpuWorker implements Runnable 
{
    private String url;
    private ArrayList<org.bson.Document> result;
    private String generation;

    public UpdateCpuWorker(String url, ArrayList<org.bson.Document> result, String generation)
    {
        this.url = url;
        this.result = result;
        this.generation = generation;
    }

    @Override
    public void run() 
    {
        try
        {
            result.add(UpdateCpu.getInnerMessages(url, generation));
        }
        catch(Exception e)
        {
            e.printStackTrace();
            
            System.out.println("error working");
        }
    }    
}