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
        double init = System.currentTimeMillis();

        try
        {

            cpuDocumentList      = UpdateCpu.getCpuList();
            // mbDocumentList       = UpdateMb.getMbList();
            coolerDocumentList   = UpdateCooler.getCoolerList();

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

        System.out.println(System.currentTimeMillis() - init);
    }
}