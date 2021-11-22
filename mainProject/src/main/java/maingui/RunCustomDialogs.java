package maingui;

import javax.swing.JDialog;
import javax.swing.JFrame;

public class RunCustomDialogs
{
	private MainFrame parent;
	private OptionPanel brother;
	public RunCustomDialogs(MainFrame parent, OptionPanel brother, int choice) {
		this.parent = parent;
		this.brother = brother;
		
		switch(choice) {
			case Content.COOL:
				runCoolerSubFrame();
				break;
			case Content.MEM:
				runMemorySubFrame();
				break;
			case Content.DISK:
				runDiskSubFrame();
				break;
			case Content.VGA:
				runVgaSubFrame();
				break;
			case Content.PSU:
				runPsuSubFrame();
				break;
			case Content.CASE:
				ruCaseSubFrame();
				break;
		}
	}
	
	private void runCoolerSubFrame() {
		
		JDialog coolerFrame = new CoolerSubFrame(parent, brother);
		
		coolerFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		coolerFrame.setSize(280, 120);
		coolerFrame.setResizable(false);
		coolerFrame.setLocationRelativeTo(parent);
		coolerFrame.setVisible(true);
	}
	
	private void runMemorySubFrame(){
		String fixedType;
		boolean memoryIsFixed;
		try {
			fixedType = new String(parent.content.getSuggestions().get(parent.content.getSuggestions().size() - 1));
			memoryIsFixed = !(fixedType.equals(""));
		}
		catch(NullPointerException e) {
			fixedType = "";
			memoryIsFixed = false;
		}
		
		JDialog memFrame = new MemorySubFrame(parent, brother, memoryIsFixed, fixedType, brother.getMemoryIsCustomized(),brother.getMemoryType());
		
		memFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		memFrame.setSize(250, 150);
		memFrame.setResizable(false);
		memFrame.setLocationRelativeTo(parent);
		memFrame.setVisible(true);
	}
	
	private void runDiskSubFrame() {
			
		JDialog diskFrame = new DiskSubFrame(parent, brother);
		
		diskFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		diskFrame.setSize(250, 200);
		diskFrame.setResizable(false);
		diskFrame.setLocationRelativeTo(parent);
		diskFrame.setVisible(true);
	}
	
	private void runVgaSubFrame() {
			
		JDialog vgaFrame = new VgaSubFrame(parent, brother);
		
		vgaFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		vgaFrame.setSize(280, 170);
		vgaFrame.setResizable(false);
		vgaFrame.setLocationRelativeTo(parent);
		vgaFrame.setVisible(true);
	}
	private void runPsuSubFrame() {
		
		JDialog psuFrame = new PsuSubFrame(parent, brother);
		
		psuFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		psuFrame.setSize(300, 200);
		psuFrame.setResizable(false);
		psuFrame.setLocationRelativeTo(parent);
		psuFrame.setVisible(true);
	}
	private void ruCaseSubFrame() {
		
		JDialog caseFrame = new CaseSubFrame(parent, brother);
		
		caseFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		caseFrame.setSize(380, 280);
		caseFrame.setResizable(false);
		caseFrame.setLocationRelativeTo(parent);
		caseFrame.setVisible(true);
	}
}