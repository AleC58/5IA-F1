package orm;

import java.util.ArrayList;

public class Circuits {
	public ArrayList<Circuit> tab;
	
	public Circuits(){
		tab = new ArrayList<>();
	}

	public ArrayList<Circuit> getTab() {
		return tab;
	}

	public void setTab(ArrayList<Circuit> tab) {
		this.tab = tab;
	}
}
