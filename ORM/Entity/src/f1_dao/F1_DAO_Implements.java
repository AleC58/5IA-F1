package f1_dao;
import java.util.ArrayList;
import java.util.HashMap;
import entity.*;
import java.sql.SQLException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/******************************************************************************************
* @(#)F1_DAO_Implements.java
* Implementazione DAO(Data Access Object) e query superiori
* @author Chiara Fanello,Riccardo Forese,Riccardo Potente
* @date 29/04/2019
* @version 8.2
******************************************************************************************/

public class F1_DAO_Implements implements F1_DAO_Interface {

	/** ***********************************************************************************
	* crea la classifica piloti per un determinato anno
	* @exception può sollevare SQLException
	* @param anno:Integer che identifica la classifica dell'anno
	* @return HashMap<Driver, Integer> dove sono inseriti i piloti e il loro punteggio 
	************************************************************************************* */
	
	@Override
	public HashMap<Driver, Integer> classificaPilotiS(int anno) {
		String str = "SELECT Drivers.driverId,surname,SUM(points) AS punteggio"
				+ " FROM Drivers INNER JOIN results ON Drivers.driverId = results.driverId"
				+ " INNER JOIN Races ON results.raceId = Races.raceId"
				+ " WHERE year = " + anno
				+ " GROUP BY surname,Drivers.driverId"
				+ " ORDER BY punteggio";
		HashMap<Driver, Integer> ris = new HashMap<>();
		ArrayList app = new ArrayList<>();
		try {
			app = OperazioniDB.getResult(str);
		} catch (SQLException ex) {
			Logger.getLogger(F1_DAO_Implements.class.getName()).log(Level.SEVERE, null, ex);
		}

		for (int i = 0; i < app.size(); i++) {
			ArrayList elem = (ArrayList) app.get(i);
			Driver a = new Driver();
			//a.setByDB(elem);
			a.setDriverId((Integer) ((ArrayList) app.get(i)).get(0));
			a.setForename((String) ((ArrayList) app.get(i)).get(1));
			int punti = (Integer) ((ArrayList) app.get(i)).get(2);
			ris.put(a, punti);
		}
		return ris;
	}

	/** ***********************************************************************************
	* crea la classifica piloti per un determinato anno
	* @exception può sollevare SQLException
	* @param anno:Integer che identifica la classifica dell'anno
	* @return HashMap<Constructor, Integer> dove sono inseriti i piloti e il loro punteggio 
	************************************************************************************* */
	@Override
	public HashMap<Constructor, Integer> classificaCostruttoriS(int anno) {
		HashMap<Driver, Integer> pil = classificaPilotiS(anno);
		String str = "SELECT DISTINCT constructorId, name"
				+ " FROM Constructors";
		ArrayList app = new ArrayList<>();
		
		try {
			app = OperazioniDB.getResult(str);
		} catch (SQLException ex) {
			Logger.getLogger(F1_DAO_Implements.class.getName()).log(Level.SEVERE, null, ex);
		}
		
		HashMap<Constructor, Integer> ris = new HashMap<>();
		
		for (int i = 0; i < app.size(); i++) {
			ArrayList elem = (ArrayList) app.get(i);
			Constructor a = new Constructor();
			//a.setByDB(elem);
			a.setConstructorId((Integer) ((ArrayList) app.get(i)).get(0));
			a.setName((String) ((ArrayList) app.get(i)).get(1));
			ArrayList<Driver> pc = pilotiCostruttore(a, anno);
			int punti = 0;
			punti = pil.get(pc.get(0)) + pil.get(pc.get(1));
			ris.put(a, punti);
		}
		return ris;
	}

	
	/** ***********************************************************************************
	* restituisce i piloti di un dato costruttore
	* @exception può sollevare SQLException
	* @param anno:Integer che identifica l'anno
	* @param costruttore:Constructor identifica il costruttore
	* @return ArrayList<Driver> dove sono inseriti i piloti del costruttore 
	************************************************************************************* */
	
	@Override
	public ArrayList<Driver> pilotiCostruttore(Constructor costruttore, int anno) {
		String str = "SELECT DISTINCT Drivers.driverId, surname"
				+ " FROM Constructors INNER JOIN results ON Constructors.constructorId = results.constructorId"
				+ " INNER JOIN Drivers ON results.driverId = Drivers.driverId"
				+ " INNER JOIN Races ON results.raceId = Races.raceId"
				+ " WHERE Constructors.constructorId = " + costruttore.getConstructorId()
				+ " AND year = " + anno;
		
		
		ArrayList<Driver> ris = new ArrayList<>();
		ArrayList app = new ArrayList<>();
		try {
			app = OperazioniDB.getResult(str);
		} catch (SQLException ex) {
			Logger.getLogger(F1_DAO_Implements.class.getName()).log(Level.SEVERE, null, ex);
		}

		for (int i = 0; i < app.size(); i++) {
			ArrayList elem = (ArrayList) app.get(i);
			Driver a = new Driver();
			//a.setByDB(elem);
			a.setDriverId((Integer) ((ArrayList) app.get(i)).get(0));
			a.setForename((String) ((ArrayList) app.get(i)).get(1));
			ris.add(a);
		}
		return ris;
	}

	
	/** **************************************************************************************
	* restituisce i piloti in ordine di arrivo di una corsa definita
	* @exception può sollevare SQLException
	* @param corsa:Race che identifica la corsa
	* @return ArrayList<Driver> dove sono inseriti i piloti in ordine di arrivo della corsa 
	**************************************************************************************** */
	
	@Override
	public ArrayList<Driver> classificaGara(Race corsa) {
		String str = "SELECT Drivers.driverId, surname, results.position"
				+ " FROM Drivers INNER JOIN results ON Drivers.driverId = results.driverId"
				+ " ORDER BY results.position"
				+ " WHERE " + corsa.getRaceId() + " = results.raceId";
		
		ArrayList<Driver> ris = new ArrayList<>();
		ArrayList app = new ArrayList<>();
		try {
			app = OperazioniDB.getResult(str);
		} catch (SQLException ex) {
			Logger.getLogger(F1_DAO_Implements.class.getName()).log(Level.SEVERE, null, ex);
		}

		for (int i = 0; i < app.size(); i++) {
			ArrayList elem = (ArrayList) app.get(i);
			Driver a = new Driver();
			//a.setByDB(elem);
			a.setDriverId((Integer) ((ArrayList) app.get(i)).get(0));
			a.setForename((String) ((ArrayList) app.get(i)).get(1));
			ris.add(a);
		}
		return ris;
	}
	
	
	/** **************************************************************************************
	* restituisce i punteggi di un pilota per una data stagione
	* @exception può sollevare SQLException
	* @param pilota:Driver che identifica il pilota
	* @param anno:Integer indica l'anno di riferimento
	* @return HashMap<Race, Integer> dove sono inserite le corse e i relativi punteggi 
	**************************************************************************************** */
	
	@Override
	public HashMap<Race, Integer> punteggiPerGara(Driver pilota, int anno) {
		String str = "SELECT Races.name, Races.date, results.points"
				+ " FROM Drivers INNER JOIN results ON Drivers.driverId = results.driverId"
				+ " INNER JOIN Races ON Races.raceId = results.raceId"
				+ " WHERE Races.year = " + anno + " AND Drivers.driverId = " + pilota.getDriverId();
		
		HashMap<Race, Integer> ris = new HashMap<>();
		ArrayList app = new ArrayList<>();
		try {
			app = OperazioniDB.getResult(str);
		} catch (SQLException ex) {
			Logger.getLogger(F1_DAO_Implements.class.getName()).log(Level.SEVERE, null, ex);
		}

		for (int i = 0; i < app.size(); i++) {
			ArrayList elem = (ArrayList) app.get(i);
			Race a = new Race();
			//a.setByDB(elem);
			a.setName((String) ((ArrayList) app.get(i)).get(0));
			a.setDate((Date) ((ArrayList) app.get(i)).get(1));
			int punti = (Integer) ((ArrayList) app.get(i)).get(2);
			ris.put(a, punti);
		}
		return ris;
	}
	
	
	/** **************************************************************************************
	* restituisce le posizioni del pilota e le gare a cui il pilota ha partecipato
	* @exception può sollevare SQLException
	* @param pilota:Driver che identifica il pilota
	* @return HashMap<Race, Integer> dove sono inserite le corse e la relativa posizione 
	**************************************************************************************** */
	
	@Override
	public HashMap<Race, Integer> garePerPilota(Driver pilota){
		String str = "SELECT Races.name, Races.date, Results.position"
				+ " FROM Drivers INNER JOIN results ON Drivers.driverId = results.driverId"
				+ " INNER JOIN Races ON Races.raceId = results.raceId"
				+ " WHERE Drivers.driverId = " + pilota.getDriverId();
		
		HashMap<Race, Integer> ris = new HashMap<>();
		ArrayList app = new ArrayList<>();
		try {
			app = OperazioniDB.getResult(str);
		} catch (SQLException ex) {
			Logger.getLogger(F1_DAO_Implements.class.getName()).log(Level.SEVERE, null, ex);
		}

		for (int i = 0; i < app.size(); i++) {
			ArrayList elem = (ArrayList) app.get(i);
			Race a = new Race();
			a.setName((String) ((ArrayList) app.get(i)).get(0));
			a.setDate((Date) ((ArrayList) app.get(i)).get(1));
			int posizione = (Integer) ((ArrayList) app.get(i)).get(2);
			ris.put(a, posizione);
		}
		return ris;
	}

}


