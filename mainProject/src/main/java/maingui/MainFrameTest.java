package maingui;

import javax.swing.JFrame;

import mainlogic.MainGee;

public class MainFrameTest
{
	public static void main(String[] args)
	{
		MainGee code = new MainGee();
		code.init();
		MainFrame mainFrame = new MainFrame(code, code.getList());
		
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setSize(1000, 700);
		mainFrame.setResizable(false);
		mainFrame.setLocationRelativeTo(null);
		
		LoadingFrame loading = new LoadingFrame();
		loading.setLocationRelativeTo(mainFrame);
		loading.setVisible(true);
		
		mainFrame.init();
		
		loading.dispose();
		mainFrame.setVisible(true);
//		mainFrame.test();
	}
}