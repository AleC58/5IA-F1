package entity;

import java.util.ArrayList;

public class Races {
	public ArrayList<Race> tab;
	
	public Races(){
		tab = new ArrayList<>();
	}

	public ArrayList<Race> getTab() {
		return tab;
	}

	public void setTab(ArrayList<Race> tab) {
		this.tab = tab;
	}
}
