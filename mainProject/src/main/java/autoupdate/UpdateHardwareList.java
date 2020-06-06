package autoupdate;

import java.io.IOException;

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
            // cpuList      = UpdateCpu.getCpuList();
            mbList       = UpdateMb.getMbList();
            // coolerList   = UpdateCooler.getCoolerList();
            // ramList      = UpdateRam.getRamList();
            // vgaList      = UpdateVga.getVgaList();
            // diskList     = UpdateDisk.getDiskList();
            // psuList      = UpdatePsu.getPsuList();
            // crateList    = UpdateCrate.getCrateList();

            // crateList = new UpdateCase().getInfo();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        System.out.println(System.currentTimeMillis() - init);
    }
}