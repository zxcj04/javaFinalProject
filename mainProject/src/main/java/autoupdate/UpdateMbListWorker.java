package autoupdate;

public class UpdateMbListWorker implements Runnable
{
    private UpdateMb updateMb;
    private int pageNumber;
    
    public UpdateMbListWorker(UpdateMb updateMb, int pageNumber)
    {
        this.updateMb = updateMb;
        this.pageNumber = pageNumber;
    }

    @Override
    public void run() 
    {
        try
        {
            updateMb.getPageList(this.pageNumber);
        }
        catch(Exception e)
        {
            System.out.println("Error get mb data on page " + pageNumber);
        }
    }    
}