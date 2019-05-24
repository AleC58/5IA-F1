package orm;

import java.util.ArrayList;

public class Drivers {
	public ArrayList<Driver> tab;
	
	public Drivers(){
		tab = new ArrayList<>();
	}

	public ArrayList<Driver> getTab() {
		return tab;
	}

	public void setTab(ArrayList<Driver> tab) {
		this.tab = tab;
	}
}
