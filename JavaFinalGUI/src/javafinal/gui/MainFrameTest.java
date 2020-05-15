package javafinal.gui;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.UIManager;

public class MainFrameTest
{
	public static void main(String[] args)
	{
		Fanrende code = new Fanrende();
		MainFrame mainFrame = new MainFrame(code.getCpuList(),
											code.getCpuCoolerList(),
											code.getMotherboardList(), 
											code.getMemoryList(),
											code.getHardwareList(), 
											code.getVgaList(),
											code.getPsuList(),
											code.getCaseList());
		mainFrame.addWindowListener(new WindowAdapter(){
			@Override
			public void windowClosing(WindowEvent e) {
		        System.exit(0);
		    }
		});
		
		mainFrame.setSize(1000, 700);
		mainFrame.setResizable(false);
		mainFrame.setVisible(true);
		mainFrame.setLocationRelativeTo(null);
	}
}