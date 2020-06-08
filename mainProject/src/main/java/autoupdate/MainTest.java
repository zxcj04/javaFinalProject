package autoupdate;

public class MainTest 
{
    public static void main(String[] args)
    {
        UpdateHardwareList updateHardwareList = new UpdateHardwareList();

        UpdateMongo updateMongo = new UpdateMongo();

        updateHardwareList.update();

        updateMongo.updateMongo(updateHardwareList);
    }
}