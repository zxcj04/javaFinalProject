package autoupdate;

import java.util.ArrayList;

import org.bson.Document;

import mainlogic.HardwareList;

public class UpdateHardwareList extends HardwareList
{
    public UpdateHardwareList()
    {
        super();
    }

    /**
     * get data from online webpages
     */
    public void update()
    {
        double init = System.currentTimeMillis();

        try
        {
            ArrayList<Document> cpuDocumentList, mbDocumentList, coolerDocumentList;
            ArrayList<Document> ramDocumentList, vgaDocumentList, diskDocumentList;
            ArrayList<Document> psuDocumentList, crateDocumentList;

            // cpuDocumentList      = UpdateCpu.getCpuList();
            // mbDocumentList       = UpdateMb.getMbList();
            // coolerDocumentList   = UpdateCooler.getCoolerList();
            // ramDocumentList      = UpdateRam.getRamList();
            // vgaDocumentList      = UpdateVga.getVgaList();
            // diskDocumentList     = UpdateDisk.getDiskList();
            // psuDocumentList      = UpdatePsu.getPsuList();
            // crateDocumentList    = UpdateCrate.getCrateList();

            ramDocumentList     = new UpdateRam().getInfo();
            vgaDocumentList     = new UpdateVga().getInfo();
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