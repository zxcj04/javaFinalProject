package autoupdate;

import java.util.ArrayList; 

import org.bson.Document;

public class UpdateHardwareList
{
    ArrayList<Document> cpuDocumentList, mbDocumentList, coolerDocumentList;
    ArrayList<Document> ramDocumentList, vgaDocumentList, diskDocumentList;
    ArrayList<Document> psuDocumentList, crateDocumentList;

    /**
     * get data from online webpages
     */
    public void update()
    {
        try
        {

            cpuDocumentList     = new UpdateCpu().getResult();
            
            coolerDocumentList  = UpdateCooler.getCoolerList();

            mbDocumentList      = new UpdateMb().getResult();
            // mbDocumentList      = new ArrayList<Document>();

            ramDocumentList     = new UpdateRam().getInfo();
            vgaDocumentList     = new UpdateVga().getInfo();
            diskDocumentList    = new UpdateDisk().getInfo();
            psuDocumentList     = new UpdatePsu().getInfo();
            crateDocumentList   = new UpdateCase().getInfo();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}