package maingui;

import javax.swing.JFrame;

import mainlogic.MainGee;

public class MainFrameTest
{
	public static void main(String[] args)
	{
		MainGee code = new MainGee();
		MainFrame mainFrame = new MainFrame(code, code.getList());
		
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setSize(1000, 665);
		mainFrame.setResizable(false);
		mainFrame.setLocationRelativeTo(null);
		
		LoadingFrame loading = new LoadingFrame();
		loading.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		loading.setSize(500, 500);
		loading.setLocationRelativeTo(mainFrame);
		loading.setVisible(true);
		
		mainFrame.init();
		
		loading.dispose();
		mainFrame.setVisible(true);
	}
}