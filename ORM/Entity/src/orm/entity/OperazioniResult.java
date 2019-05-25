package orm.entity;

import orm.dao.F1_DAO_Implements;

import java.util.ArrayList;
import java.util.HashMap;

public class OperazioniResult implements ResultDAO {
	@Override
	public HashMap<Driver, Integer> getClassificaPiloti(int anno) {
		F1_DAO_Implements dao = new F1_DAO_Implements();
		HashMap<Driver, Integer> ris = dao.classificaPilotiS(anno);
		return ris;
	}

	@Override
	public HashMap<Constructor, Integer> getclassificaCostruttori(int anno) {
		F1_DAO_Implements dao = new F1_DAO_Implements();
		HashMap<Constructor, Integer> ris = dao.classificaCostruttoriS(anno);
		return ris;
	}

	@Override
	public HashMap<Driver, Integer> getRisultatiGara(Race gara) {
		F1_DAO_Implements dao = new F1_DAO_Implements();
		ArrayList<Driver> ris = dao.classificaGara(gara);
		HashMap<Driver, Integer> griglia = new HashMap<>();
		for (int i = 0; i < ris.size(); i++) {
			griglia.put(ris.get(i), i + 1);
		}
		return griglia;
	}

}
