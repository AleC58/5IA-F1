package orm;

import java.util.ArrayList;

public class Lap_Times {
	public ArrayList<Lap_Time> tab;
	
	public Lap_Times(){
		tab = new ArrayList<>();
	}

	public ArrayList<Lap_Time> getTab() {
		return tab;
	}

	public void setTab(ArrayList<Lap_Time> tab) {
		this.tab = tab;
	}
}
