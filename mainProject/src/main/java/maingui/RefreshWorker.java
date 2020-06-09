package maingui;

import javax.swing.JOptionPane;
import javax.swing.SwingWorker;

public class RefreshWorker extends SwingWorker<Void, Void>{

	private MainFrame parent;
	public RefreshWorker(MainFrame parent) {
		this.parent = parent;
	}
	
	@Override
	protected Void doInBackground() throws Exception {
		parent.content.setSuggestions();
		
		if(parent.getSmartModeBtnPanel().getToRefresh()) {
			parent.getMainPanel().getOptionPanel().updateSpinners(parent.content.getSuggestions());
			
			checkCustom();
			parent.content.setLists();
		}
		else {
			parent.content.initLists();
		}

		parent.setSuggestion();
		parent.getMainPanel().getOptionPanel().updateComboBoxes(parent.getSmartModeBtnPanel().getToRefresh());
		return null;
	}
	@Override
    protected void done() {
//      parent.switchCards("MAIN");
		parent.enableSwitch(true);
    }
	
	private void checkCustom() {
		String customFailed = parent.content.getSuggestions().get(parent.content.getSuggestions().size() - 3);
		
		int i = parent.getMainPanel().getOptionPanel().getNowGeari();
		int j = parent.getMainPanel().getOptionPanel().getNowGearj();
		parent.getMainPanel().getOptionPanel().initNowGeari();
		parent.getMainPanel().getOptionPanel().initNowGearj();
		
		if((customFailed.contains("cooler") && i == Content.COOL) ||
		   (customFailed.contains("ram")    && i == Content.MEM ) ||
		   (customFailed.contains("disk")   && i == Content.DISK) ||
		   (customFailed.contains("vga")    && i == Content.VGA ) ||
		   (customFailed.contains("psu")    && i == Content.PSU ) ||
		   (customFailed.contains("crate")  && i == Content.CASE)) {
		
			parent.getMainPanel().getOptionPanel().getComboBoxes().get(i).get(j).getTextField().setText("請選擇");
			
			JOptionPane.showMessageDialog(parent, "cannot customize");
		}
		
		parent.content.setInputs(parent.getMainPanel().getOptionPanel().getComboBoxes(),
								 parent.getMainPanel().getOptionPanel().getSpinners());
	}
}