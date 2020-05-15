package javafinal.gui;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
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
		
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		mainFrame.setSize(1000, 700);
		mainFrame.setResizable(false);
		mainFrame.setLocationRelativeTo(null);
		mainFrame.setVisible(true);
	}
}