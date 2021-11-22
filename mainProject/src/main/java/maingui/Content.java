package maingui;

import java.util.ArrayList;

import javax.swing.JSpinner;

import mainlogic.HardwareNameList;
import mainlogic.MainGee;

public class Content
{
	public static final int CPU  = 0; public static final int COOL = 1;
	public static final int MB   = 2; public static final int MEM  = 3;
	public static final int DISK = 4; public static final int VGA  = 5;
	public static final int PSU  = 6; public static final int CASE = 7;
	
	private MainGee source;
	private HardwareNameList content;
	private HardwareNameList currentInputs;

	private ArrayList<ArrayList<String>> lists; // content of comboBoxes
	private ArrayList<ArrayList<String>> inputs;// chosen of comboBoxes
	private ArrayList<String> suggestions;
	public Content() {}
	
	public void setSource(MainGee source) {
		this.source = source;
	}
	public MainGee getSource() {
		return source;
	}
	public void setContent(HardwareNameList content) {
		this.content = content;
	}
	public HardwareNameList getContent() {
		return content;
	}
	public void setLists() {
		HardwareNameList newLists = source.getList(currentInputs);

		lists = new ArrayList<ArrayList<String>>();
		lists.add(newLists.cpu);	lists.add(newLists.cooler);
		lists.add(newLists.mb);		lists.add(newLists.ram);
		lists.add(newLists.disk);	lists.add(newLists.vga);
		lists.add(newLists.psu);	lists.add(newLists.crate);
	}
	public void initLists() {
		lists = new ArrayList<ArrayList<String>>();
		
		lists.add(new ArrayList<String>(content.cpu)); 		lists.add(new ArrayList<String>(content.cooler));
		lists.add(new ArrayList<String>(content.mb)); 		lists.add(new ArrayList<String>(content.ram));
		lists.add(new ArrayList<String>(content.disk)); 	lists.add(new ArrayList<String>(content.vga));
		lists.add(new ArrayList<String>(content.psu)); 		lists.add(new ArrayList<String>(content.crate));
		
		for(ArrayList<String> list : lists) {
			list.add(0, "請選擇");
		}
	}
	public ArrayList<ArrayList<String>> getLists(){
		return lists;
	}
	public void setInputs(ArrayList<ArrayList<FilterComboBox>> comboBoxes, ArrayList<JSpinner> spinners) {
		inputs = new ArrayList<ArrayList<String>>();
		
		for(int i = 0; i < comboBoxes.size(); i++) {
			inputs.add(new ArrayList<String>());
			
			for(int j = 0; j < comboBoxes.get(i).size(); j++) {
				String chosen = comboBoxes.get(i).get(j).getTextField().getText();
				
				if((lists.get(i).indexOf(chosen) >= 0 && !chosen.equals("請選擇")) || customMatched(chosen, i)){
					if(i != MEM) {
						inputs.get(i).add(chosen);
					}
					
					else {
						for(int k = 0
							; k < (Integer)(spinners.get(j).getValue())
							; k++) {
							
							inputs.get(i).add(chosen);
						}
					}
				}
			}
		}
		
		currentInputs = new HardwareNameList(inputs.get(CPU) , inputs.get(COOL),
											 inputs.get(MB)  , inputs.get(MEM) ,
											 inputs.get(DISK), inputs.get(VGA) ,
											 inputs.get(PSU) , inputs.get(CASE));
	}
	

	public ArrayList<ArrayList<String>> getInputs(){
		return inputs;
	}
	public void setSuggestions() {
		suggestions = new ArrayList<String>(source.getSuggestion(currentInputs));
	}
	
	public ArrayList<String> getSuggestions(){
		return suggestions;
	}
	
	
	public boolean customMatched(String str, int cur) {
		boolean matched = false;
		switch(cur) {
			case COOL:
				if(str.matches("custom [0-9]{1,3}cm")) {
					matched = true;
				}
				break;
			case MEM:
				if(str.matches("custom ddr[1-4] (1|2|4|8|16|32)G")) {
					matched = true;			
				}
				break;
			case DISK:
				if(str.matches("custom ((m.2 (pcie|sata))|((ssd|hdd) (2.5\"|3.5\"))) [0-9]{1,3}(G|T)")) {
					matched = true;
				}
				break;
			case VGA:
				if(str.matches("custom [0-9]{1,3}cm [0-9]{1,3}W")) {
					matched = true;
				}
				break;
			case PSU:
				if(str.matches("custom [0-9]{1,3}cm [0-9]{1,3}W (ATX|SFX)")) {
					matched = true;
				}
				break;
			case CASE:
				if(str.matches("custom (ATX|MATX|EATX|ITX|MITX) [0-9]{1,3}cm (ATX|SFX) [0-9]{1,3}cm [0-9]{1,3}cm [0-9]{1,3}個")) {
					matched = true;
				}
				break;
		}
		return matched;
	}
}