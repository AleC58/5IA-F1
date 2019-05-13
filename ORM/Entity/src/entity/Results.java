package entity;

import java.util.ArrayList;

public class Results {
	public ArrayList<Result> tab;

	public Results() {
		tab = new ArrayList<>();
	}

	public ArrayList<Result> getTab() {
		return tab;
	}

	public void setTab(ArrayList<Result> tab) {
		this.tab = tab;
	}

	public int getPunti(int idPilota, int year) {
		ArrayList<Result> selez = new ArrayList<>();
		Races gare = new Races();
		
		for (int i = 0; i < selez.size(); i++) {
			if (tab.get(i).getDriverId() == idPilota) {
				selez.add(tab.get(i));
			}
		}
		return 0;

	}
}
