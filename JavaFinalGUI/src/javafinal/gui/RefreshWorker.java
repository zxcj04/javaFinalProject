package javafinal.gui;

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
			parent.content.setLists();
		}
		else {
			parent.setSuggestion();
		}
		
		parent.getMainPanel().getOptionPanel().updateComboBoxes(parent.getSmartModeBtnPanel().getToRefresh());
		return null;
	}
	@Override
    protected void done() {
        parent.switchCards("MAIN");
    }
}