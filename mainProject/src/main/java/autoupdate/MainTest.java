package autoupdate;

public class MainTest 
{
    public static void main(String[] args)
    {
        double init = System.currentTimeMillis();
        
        UpdateHardwareList updateHardwareList = new UpdateHardwareList();

        UpdateMongo updateMongo = new UpdateMongo();

        updateHardwareList.update();

        updateMongo.updateMongo(updateHardwareList);

        System.out.println(System.currentTimeMillis() - init);
    }
}