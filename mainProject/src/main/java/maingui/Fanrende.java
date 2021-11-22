package maingui;

import java.util.ArrayList;

public class Fanrende
{
	ArrayList<String> cpuList, 
					  cpuCoolerList,
					  motherboardList, 
					  memoryList,
					  hardwareList,
					  vgaList,
					  psuList,
					  caseList;
	public Fanrende()
	{
		cpuList = new ArrayList<String>();
		cpuCoolerList = new ArrayList<String>();
		motherboardList = new ArrayList<String>();
		memoryList = new ArrayList<String>();
		hardwareList = new ArrayList<String>();
		vgaList = new ArrayList<String>();
		psuList = new ArrayList<String>();
		caseList = new ArrayList<String>();
		
		cpuList.add("請選擇");
		cpuCoolerList.add("請選擇");
		motherboardList.add("請選擇");
		memoryList.add("請選擇");
		hardwareList.add("請選擇");
		vgaList.add("請選擇");
		psuList.add("請選擇");
		caseList.add("請選擇");
		
		cpuList.add("A");
		cpuCoolerList.add("B");
		motherboardList.add("C");
		memoryList.add("D");
		hardwareList.add("E");
		vgaList.add("F");
		psuList.add("G");
		caseList.add("H");
	}
	
	public void setCpuList(){}
	public void setCpuCoolerList(){}
	public void setMotherboardList(){} 
	public void setMemoryList(){}
	public void setHardwareList(){} 
	public void setVgaList(){}
	public void setPsuList(){}
	public void setCaseList() {}
	
	public ArrayList<String> getCpuList(){return cpuList;}
	public ArrayList<String> getCpuCoolerList(){return cpuCoolerList;}
	public ArrayList<String> getMotherboardList(){return motherboardList;} 
	public ArrayList<String> getMemoryList(){return memoryList;}
	public ArrayList<String> getHardwareList(){return hardwareList;} 
	public ArrayList<String> getVgaList(){return vgaList;}
	public ArrayList<String> getPsuList(){return psuList;}
	public ArrayList<String> getCaseList(){return caseList;}
}