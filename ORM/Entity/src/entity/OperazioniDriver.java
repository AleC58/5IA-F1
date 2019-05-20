package entity;

import f1_dao.F1_DAO_Implements;
import java.util.Collection;
import java.util.HashMap;

/**
 *
 * @author davide.dariol
 */
public class OperazioniDriver implements DriverDAO {

	@Override
	public int getPunti(Driver pilota, int anno) {
		F1_DAO_Implements dao = new F1_DAO_Implements();
		HashMap<Race, Integer> ris = dao.punteggiPerGara(pilota, anno);
		int somma = 0;
		Object punti[] = ris.values().toArray();
		for (int i = 0; i < punti.length; i++) {
			somma += (Integer) punti[i];
		}
		return somma;
	}

	@Override
	public int getGareFatteCarriera(Driver pilota) {
		F1_DAO_Implements dao = new F1_DAO_Implements();
		HashMap<Race, Integer> ris = dao.garePerPilota(pilota);
		int gareFatteInCarriera = ris.values().size();
		return gareFatteInCarriera;
	}

	@Override
	public int getGareFatteCampionato(Driver pilota, int anno) {
		F1_DAO_Implements dao = new F1_DAO_Implements();
		HashMap<Race, Integer> ris = dao.punteggiPerGara(pilota, anno);
		int gareFatteCampionato = ris.values().size();
		return gareFatteCampionato;
	}

	@Override
	public Constructor getCostruttoreAnno(Driver pilota, int anno) {
		Constructor c = new Constructor();
		return c;
	}

}


