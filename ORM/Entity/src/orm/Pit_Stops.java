
package entity;

import java.util.ArrayList;

public class Pit_Stops {
	public ArrayList<Pit_Stop> tab;
	
	public Pit_Stops(){
		tab = new ArrayList<>();
	}

	public ArrayList<Pit_Stop> getTab() {
		return tab;
	}

	public void setTab(ArrayList<Pit_Stop> tab) {
		this.tab = tab;
	}
}
