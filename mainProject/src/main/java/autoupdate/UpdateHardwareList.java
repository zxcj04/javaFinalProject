package autoupdate;

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
        cpuList      = UpdateCpu.getCpuList();
        mbList       = UpdateMb.getMbList();
        coolerList   = UpdateCooler.getCoolerList();
        ramList      = UpdateRam.getRamList();
        vgaList      = UpdateVga.getVgaList();
        diskList     = UpdateDisk.getDiskList();
        psuList      = UpdatePsu.getPsuList();
        crateList    = UpdateCrate.getCrateList();
    }
}