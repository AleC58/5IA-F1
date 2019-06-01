package orm.dao;

import orm.entity.*;

import java.sql.Date;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * ****************************************************************************************
 *
 * @author Chiara Fanello,Riccardo Forese,Riccardo Potente
 * @version 8.2 *
 * ****************************************************************************************
 * @(#)F1_DAO_Implements.java Implementazione DAO(Data Access Object) e query	*
 * superiori
 * @date 29/04/2019	*
 */
public class F1_DAO_Implements implements F1_DAO_Interface {

	/**
	 * ***********************************************************************************
	 * crea la classifica piloti per un determinato anno decrescente
	 *
	 * @param anno:Integer che identifica la classifica dell'anno
	 * @return ArrayList<Paio < Integer, Integer>> dove sono inseriti i piloti e
	 * il loro punteggio
	 * ************************************************************************************
	 * @throws può sollevare SQLException
	 */
	@Override
	public ArrayList<Paio<Integer, Integer>> classificaPilotiS(int anno) {
		String str = "SELECT Drivers.driverId,SUM(points) AS punteggio"
				+ " FROM Drivers INNER JOIN results ON Drivers.driverId = results.driverId"
				+ " INNER JOIN Races ON results.raceId = Races.raceId"
				+ " WHERE year = " + anno
				+ " GROUP BY surname,Drivers.driverId"
				+ " ORDER BY punteggio DESC";
		ArrayList<Paio<Integer, Integer>> ris = new ArrayList<>();
		ArrayList app = new ArrayList<>();
		try {
			app = OperazioniDB.getResult(str);
		} catch (SQLException ex) {
			System.err.println("Errore: " + ex.getMessage());
			System.exit(0);
		}

		for (int i = 0; i < app.size(); i++) {
			int punti = ((Double) ((ArrayList) app.get(i)).get(1)).intValue();
			ris.add(new Paio((Integer) ((ArrayList) app.get(i)).get(0), punti));
		}
		return ris;
	}

	/**
	 * ***********************************************************************************
	 * restituisce i piloti di un dato anno
	 *
	 * @param anno:Integer che identifica l'anno
	 * @return ArrayList<Integer> dove sono inseriti i piloti
	 * ************************************************************************************
	 * @throws puo sollevare SQLException
	 */
	@Override
	public ArrayList<Integer> pilotiAnno(int anno) {
		String str = "SELECT Drivers.driverId"
				+ " FROM Drivers INNER JOIN results ON Drivers.driverId = results.driverId"
				+ " INNER JOIN Races ON results.raceId = Races.raceId"
				+ " WHERE year = " + anno;
		ArrayList<Integer> ris = new ArrayList<>();
		ArrayList<Integer> app = new ArrayList<>();
		try {
			app = OperazioniDB.getResult(str);
		} catch (SQLException ex) {
			System.err.println("Errore: " + ex.getMessage());
			System.exit(0);
		}

		for (int i = 0; i < app.size(); i++) {
			ris.add((Integer) app.get(i));
		}
		return ris;
	}

	/**
	 * ***********************************************************************************
	 * crea la classifica piloti per un determinato anno
	 *
	 * @param anno:Integer che identifica la classifica dell'anno
	 * @return ArrayList<Paio < Integer, Integer>> dove sono inseriti i
	 * costruttori e il loro punteggio
	 * ************************************************************************************
	 * @throws può sollevare SQLException
	 */
	@Override
	public ArrayList<Paio<Integer, Integer>> classificaCostruttoriS(int anno) {
		String str = "SELECT DISTINCT Constructors.constructorId, SUM(points) as Punteggi"
				+ " FROM Constructors INNER JOIN results on Constructors.constructorid = results.constructorid"
				+ " INNER JOIN Drivers ON Drivers.driverId = results.driverId"
				+ " INNER JOIN Races ON results.raceId = Races.raceId"
				+ " WHERE year = " + anno
				+ " GROUP BY Constructors.constructorId"
				+ " ORDER BY Punteggi DESC";
		ArrayList app = new ArrayList<>();

		try {
			app = OperazioniDB.getResult(str);
		} catch (SQLException ex) {
			System.err.println("Errore: " + ex.getMessage());
			System.exit(0);
		}

		ArrayList<Paio<Integer, Integer>> ris = new ArrayList<>();

		for (int i = 0; i < app.size(); i++) {
			ArrayList elem = (ArrayList) app.get(i);
			ris.add(new Paio((((ArrayList) app.get(i)).get(0)), elem.get(1)));
		}

		return ris;
	}

	/**
	 * ***********************************************************************************
	 * crea la classifica piloti in una data gara
	 *
	 * @param anno:Integer che identifica la classifica dell'anno
	 * @param nome:String il nome del circuito
	 * @return ArrayList<Paio < Integer, Integer>> dove sono inseriti i piloti e
	 * il loro punteggio
	 * ************************************************************************************
	 * @throws può sollevare SQLException
	 */
	@Override
	public ArrayList<Paio<Integer, Integer>> risultatoGara(String nome, int anno) {
		String str = "SELECT Drivers.driverId, Results.points"
				+ " FROM Drivers INNER JOIN results ON Drivers.driverId = results.driverId"
				+ " INNER JOIN Races ON results.raceId = Races.raceId INNER JOIN Circuits ON Races.circuitId = Circuits.circuitId"
				+ " WHERE year = " + anno + " AND Circuits.name LIKE \"" + nome
				+ "\" ORDER BY Results.points DESC";
		ArrayList app = new ArrayList<>();

		try {
			app = OperazioniDB.getResult(str);
		} catch (SQLException ex) {
			System.err.println("Errore: " + ex.getMessage());
			System.exit(0);
		}

		ArrayList<Paio<Integer, Integer>> ris = new ArrayList<>();

		for (int i = 0; i < app.size(); i++) {
			ArrayList elem = (ArrayList) app.get(i);
			ris.add(new Paio(elem.get(0), elem.get(1)));
		}

		return ris;
	}

	/**
	 * ***********************************************************************************
	 * restituisce le date ancora da disputare (Serve un controllo per trovare i
	 * giorni rimanenti prima di una certa gara)
	 *
	 * @return ArrayList<Race> la raccolta delle gare da disputare
	 * ************************************************************************************
	 * @throws può sollevare SQLException
	 */
	@Override
	public ArrayList<Race> gareDaDisputare() {

		GregorianCalendar data = new GregorianCalendar();
		java.util.Date dataOdierna = (java.util.Date) data.getTime();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String s = sdf.format(dataOdierna);

		String str = "SELECT Races.raceId, Races.Date"
				+ " FROM Races";

		ArrayList app = new ArrayList<>();

		try {
			app = OperazioniDB.getResult(str);
		} catch (SQLException ex) {
			System.err.println("Errore: " + ex.getMessage());
			System.exit(0);
		}

		ArrayList<Race> ris = new ArrayList<>();

		for (int i = 0; i < app.size(); i++) {
			ArrayList elem = (ArrayList) app.get(i);
			Race a = new Race();
			String d = (String) ((ArrayList) app.get(i)).get(1);
			System.out.println("Data: " + i + " ::: " + d);
			a.setRaceId((Integer) ((ArrayList) app.get(i)).get(0));
			a.setDate(Date.valueOf((String) ((ArrayList) app.get(i)).get(1)));

			if (d.compareTo(s) > 0) {
				ris.add(a);
			}

			//MANCA DA AGGIUNGERE I GIORNI CHE MANCANO ALLE VARIE GARE DA DISPUTARE
		}

		return ris;
	}

	/**
	 * **********************************************************************************
	 * restituisce i piloti di un dato costruttore
	 *
	 * @param anno:Integer che identifica l'anno
	 * @param costruttore:Constructor identifica il costruttore
	 * @return ArrayList<Driver> dove sono inseriti i piloti del costruttore
	 * ************************************************************************************
	 * @throws può sollevare SQLException
	 */
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
			System.err.println("Errore: " + ex.getMessage());
			System.exit(0);
		}

		for (int i = 0; i < app.size(); i++) {
			Driver a = new Driver();
			a.setDriverId((Integer) ((ArrayList) app.get(i)).get(0));
			a.setForename((String) ((ArrayList) app.get(i)).get(1));
			ris.add(a);
		}
		return ris;
	}

	/**
	 * *************************************************************************************
	 * restituisce il costruttore di un pilota in un dato anno
	 *
	 * @param pilota:Driver che identifica il pilota
	 * @param anno:int l'anno a cui riferirsi
	 * @return Constructor il costruttore del pilota
	 * ***************************************************************************************
	 * @throws può sollevare SQLException
	 */
	@Override
	public Constructor costruttorePilota(Driver pilota, int anno) {
		String str = "SELECT DISTINCT Constructors.ConstructorId "
				+ "FROM Constructors INNER JOIN results ON Constructors.constructorId = results.constructorId INNER JOIN Races ON Results.RaceId = Races.RaceId "
				+ "WHERE Results.driverId = " + pilota.getDriverId() + " AND year = " + anno;
		ArrayList app = new ArrayList<>();
		try {
			app = (ArrayList) OperazioniDB.getResult(str).toArray()[0];
		} catch (SQLException ex) {
			System.err.println("Errore: " + ex.getMessage());
			System.exit(0);
		}
		return infoCostruttore((Integer) app.get(0));
	}

	/**
	 * *************************************************************************************
	 * dato un indice, trova le info del pilota
	 *
	 * @param indice:int che identifica il pilota
	 * @return Driver le info del pilota
	 * ***************************************************************************************
	 * @throws può sollevare SQLException
	 */
	@Override
	public Driver infoPilota(int indice) {
		String str = "SELECT * FROM Drivers WHERE Drivers.DriverId = " + indice;

		Driver ris = new Driver();
		ArrayList app = new ArrayList<>();
		try {
			app = (ArrayList) OperazioniDB.getResult(str).toArray()[0];
		} catch (SQLException ex) {
			System.err.println("Errore: " + ex.getMessage());
			System.exit(0);
		}
		ris.setByDB(app);
		return ris;
	}

	/**
	 * *************************************************************************************
	 * dato un indice, trova le info del costruttore
	 *
	 * @param indice:int che identifica il costruttore
	 * @return Constructor le info del costruttore
	 * ***************************************************************************************
	 * @throws può sollevare SQLException
	 */
	@Override
	public Constructor infoCostruttore(int indice) {
		String str = "SELECT * FROM Constructors WHERE Constructors.ConstructorId = " + indice;

		Constructor ris = new Constructor();
		ArrayList app = new ArrayList<>();
		try {
			app = (ArrayList) OperazioniDB.getResult(str).toArray()[0];
		} catch (SQLException ex) {
			System.err.println("Errore: " + ex.getMessage());
			System.exit(0);
		}
		ris.setByDB(app);
		return ris;
	}

	/**
	 * *************************************************************************************
	 * dato un indice, trova le info della corsa
	 *
	 * @param indice:int che identifica la corsa
	 * @return Race le info della corsa
	 * ***************************************************************************************
	 * @throws può sollevare SQLException
	 */
	@Override
	public Race infoCorsa(int indice) {
		String str = "SELECT * FROM Races WHERE Races.RaceId = " + indice;

		Race ris = new Race();
		ArrayList app = new ArrayList<>();
		try {
			app = (ArrayList) OperazioniDB.getResult(str).toArray()[0];
		} catch (SQLException ex) {
			System.err.println("Errore: " + ex.getMessage());
			System.exit(0);
		}
		ris.setByDB(app);
		return ris;
	}

	/**
	 * *************************************************************************************
	 * dato un indice, trova le info del risultato
	 *
	 * @param indice:int che identifica il risultato
	 * @return Result le info del risultato
	 * ***************************************************************************************
	 * @throws può sollevare SQLException
	 */
	@Override
	public Result infoRisultato(int indice) {
		String str = "SELECT * FROM Results WHERE Results.ResultId = " + indice;

		Result ris = new Result();
		ArrayList app = new ArrayList<>();
		try {
			app = (ArrayList) OperazioniDB.getResult(str).toArray()[0];
		} catch (SQLException ex) {
			System.err.println("Errore: " + ex.getMessage());
			System.exit(0);
		}
		ris.setByDB(app);
		return ris;
	}

	/**
	 * *************************************************************************************
	 * dato indice corsa e pilota, trova le info del risultato
	 *
	 * @param indiceR:int che identifica la corsa
	 * @param indiceD:int che identifica il pilota
	 * @return Result le info del risultato
	 * ***************************************************************************************
	 * @throws può sollevare SQLException
	 */
	@Override
	public Result infoRisultato(int indiceR, int indiceD) {
		String str = "SELECT * FROM Results "
				+ "WHERE Results.RaceId = " + indiceR + " AND Results.DriverId = " + indiceD;

		Result ris = new Result();
		ArrayList app = new ArrayList<>();
		try {
			app = (ArrayList) OperazioniDB.getResult(str).toArray()[0];
		} catch (SQLException ex) {
			System.err.println("Errore: " + ex.getMessage());
			System.exit(0);
		}
		ris.setByDB(app);
		return ris;
	}

	/**
	 * *************************************************************************************
	 * dato un indice, trova le info del circuito
	 *
	 * @param indice:int che identifica il circuito
	 * @return Circuit le info del circuito
	 * ***************************************************************************************
	 * @throws può sollevare SQLException
	 */
	@Override
	public Circuit infoCircuito(int indice) {
		String str = "SELECT * FROM Circuits WHERE Circuits.CircuitId = " + indice;

		Circuit ris = new Circuit();
		ArrayList app = new ArrayList<>();
		try {
			app = (ArrayList) OperazioniDB.getResult(str).toArray()[0];
		} catch (SQLException ex) {
			System.err.println("Errore: " + ex.getMessage());
			System.exit(0);
		}
		ris.setByDB(app);
		return ris;
	}

	/**
	 * ***************************************************************************
	 * Dato il nome di una tabella ne restituisce gli id in un arraylist di
	 * interi
	 *
	 * @param tab:String indica il nome della tabella da cui prendere gli indici
	 * @return ArrayList<Integer> tutti gli indici dell tabella
	 * ****************************************************************************
	 */
	@Override
	public ArrayList<Integer> indiciTabella(String tab) {
		String tmp = tab.substring(0, tab.length() - 1) + "Id";
		String str = "SELECT " + tmp + " FROM " + tab + " ORDER BY " + tmp;

		ArrayList<Integer> ris = new ArrayList<>();
		ArrayList app = new ArrayList<>();
		try {
			app = OperazioniDB.getResult(str);
		} catch (SQLException ex) {
			System.err.println("Errore: " + ex.getMessage());
			System.exit(0);
		}
		for (int i = 0; i < app.size(); i++) {
			int id = (Integer) ((ArrayList) app.get(i)).get(0);
			ris.add(id);
		}
		return ris;
	}

	/**
	 * *************************************************************************************
	 * restituisce i piloti in ordine di arrivo di una corsa definita
	 *
	 * @param corsa:Race che identifica la corsa
	 * @return ArrayList<Driver> dove sono inseriti i piloti in ordine di arrivo
	 * della corsa
	 * ***************************************************************************************
	 * @throws può sollevare SQLException
	 */
	@Override
	public ArrayList<Driver> classificaGara(Race corsa) {
		String str = "SELECT Drivers.driverId, surname, results.Position "
				+ "FROM Drivers INNER JOIN results ON Drivers.driverId = results.driverId "
				+ "WHERE results.RaceId = " + corsa.getRaceId()
				+ " ORDER BY results.Position";

		ArrayList<Driver> ris = new ArrayList<>();
		ArrayList app = new ArrayList<>();
		try {
			app = OperazioniDB.getResult(str);
		} catch (SQLException ex) {
			System.err.println("Errore: " + ex.getMessage());
			System.exit(0);
		}

		for (int i = 0; i < app.size(); i++) {
			ArrayList elem = (ArrayList) app.get(i);
			Driver a = new Driver();
			a.setDriverId((Integer) ((ArrayList) app.get(i)).get(0));
			a.setForename((String) ((ArrayList) app.get(i)).get(1));
			ris.add(a);
		}
		return ris;
	}

	/**
	 * *************************************************************************************
	 * restituisce i punteggi di un pilota per una data stagione
	 *
	 * @param pilota:Driver che identifica il pilota
	 * @param anno:Integer indica l'anno di riferimento
	 * @return HashMap<Race, Integer> dove sono inserite le corse e i relativi
	 * punteggi
	 * ***************************************************************************************
	 * @throws può sollevare SQLException
	 */
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
			System.err.println("Errore: " + ex.getMessage());
			System.exit(0);
		}

		for (int i = 0; i < app.size(); i++) {
			ArrayList elem = (ArrayList) app.get(i);
			Race a = new Race();
			a.setName((String) ((ArrayList) app.get(i)).get(0));
			a.setDate(Date.valueOf((String) ((ArrayList) app.get(i)).get(1)));
			int punti = (Integer) ((ArrayList) app.get(i)).get(2);
			ris.put(a, punti);
		}
		return ris;
	}

	/**
	 * *************************************************************************************
	 * restituisce le posizioni del pilota e le gare a cui il pilota ha
	 * partecipato
	 *
	 * @param pilota:Driver che identifica il pilota
	 * @return HashMap<Race, Integer> dove sono inserite le corse e la relativa
	 * posizione
	 * ***************************************************************************************
	 * @throws può sollevare SQLException
	 */
	@Override
	public HashMap<Race, Integer> garePerPilota(Driver pilota) {
		String str = "SELECT Races.name, Races.date, Results.position, Races.raceid"
				+ " FROM Drivers INNER JOIN results ON Drivers.driverId = results.driverId"
				+ " INNER JOIN Races ON Races.raceId = results.raceId"
				+ " WHERE Drivers.driverId = " + pilota.getDriverId()
				+ " ORDER BY races.year DESC";

		HashMap<Race, Integer> ris = new HashMap<>();
		ArrayList app = new ArrayList<>();
		try {
			app = OperazioniDB.getResult(str);
		} catch (SQLException ex) {
			System.err.println("Errore: " + ex.getMessage());
			System.exit(0);
		}

		for (int i = 0; i < app.size(); i++) {
			Race a = new Race();
			a.setName((String) ((ArrayList) app.get(i)).get(0));
			a.setDate(Date.valueOf((String) ((ArrayList) app.get(i)).get(1)));
			a.setRaceId((Integer) ((ArrayList) app.get(i)).get(3));
			int posizione;
			try {
				posizione = (Integer) ((ArrayList) app.get(i)).get(2);
			} catch (NullPointerException e) {
				posizione = -1;
			}
			ris.put(a, posizione);
		}
		return ris;
	}
	
	
	
//===========================POSSIBILI=METODI=PER=GESTIRE=GLI=UTENTI====================================
	/**
	 * *****************************************************************************
	 * restituisce tutti gli utenti registrati nel database
	 *
	 * @return ArrayList<User>: array che contiene tutti gli utenti registrati
	 * *****************************************************************************
	 * @throws può sollevare SQLException
	 *//*
	@Override
	public ArrayList<User> utente() {
		String str = "SELECT *"
				+ " FROM Users";

		ArrayList<User> ris = new ArrayList<>();
		ArrayList app = new ArrayList<>();

		try {
			app = OperazioniDB.getResult(str);
		} catch (SQLException ex) {
			System.err.println("Errore: " + ex.getMessage());
			System.exit(0);
		}

		for (int i = 0; i < app.size(); i++) {
			ArrayList elem = (ArrayList) app.get(i);
			User a = new User();
			a.setByDB(elem);
			ris.add(a);
		}
		return ris;
	}
*/
	
	
	/**
	 * *********************************************************************************
	 * metodo che aggiunge un nuovo utente al database
	 * 
	 * @param nome:String indica il nome vero dell'utente
	 * @param cognome:String indica il cognome vero dell'utente
	 * @param username:String indica il nome utente da registrare
	 * @param password:String indica la password !!!!DA CRIPTARE!!!!
	 * @return boolean: vero o falso a seconda se ha effettuato i comandi con successo
	 * *********************************************************************************
	 * @throws può sollevare SQLException
	 *//*
	@Override
	public boolean utente(String nome, String cognome, String username, String password) {
		boolean ris = true;
		String nomeTabella = "Users";
		String[] nomeCampi = {"nome", "cognome", "username", "password"};
		Object[] valoriCampi = {nome, cognome, username, password};
		try {
			OperazioniDB.insRec(nomeTabella, nomeCampi, valoriCampi);
		} catch (SQLException ex) {
			System.err.println("Errore: " + ex.getMessage());
			return ris = false;
		}
		return ris;
	}

	
	//==================================================================================================
	
	
	
	
	
	
	/**
	 * **********************************************************************************
	 * crea la classifica piloti per un determinato anno
	 *
	 * @param anno:Integer che identifica la classifica dell'anno
	 * @return HashMap<Constructor, Integer> dove sono inseriti i piloti e il
	 * loro punteggio
	 * ************************************************************************************
	 * @throws può sollevare SQLException
	 *//*
    @Override
    public HashMap<Constructor, Integer> classificaCostruttoriS(int anno) {
        HashMap<Driver, Integer> pil = classificaPilotiS(anno);
        String str = "SELECT DISTINCT constructorId, name"
                + " FROM Constructors";
        ArrayList app = new ArrayList<>();

        try {
            app = OperazioniDB.getResult(str);
        } catch (SQLException ex) {
            System.err.println("Errore: " + ex.getMessage());
            System.exit(0);
        }

        HashMap<Constructor, Integer> ris = new HashMap<>();

        for (int i = 0; i < app.size(); i++) {
            ArrayList elem = (ArrayList) app.get(i);
            Constructor a = new Constructor();
            a.setConstructorId((Integer) ((ArrayList) app.get(i)).get(0));
            a.setName((String) ((ArrayList) app.get(i)).get(1));
            ArrayList<Driver> pc = pilotiCostruttore(a, anno);
            int punti = 0;
            punti = pil.get(pc.get(0)) + pil.get(pc.get(1));
            ris.put(a, punti);
        }
        return ris;
    }


   
	 */

}
