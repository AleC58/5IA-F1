package orm.dao;

import orm.entity.*;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * ****************************************************************************************
 *
 * @author Chiara Fanello,Riccardo Forese,Riccardo Potente									*
 * @version 8.2                                                                                *
 * ****************************************************************************************
 * @(#)F1_DAO_Implements.java Implementazione DAO(Data Access Object) e query				*
 * superiori																				*
 * @date 29/04/2019																			*
 */
public class F1_DAO_Implements implements F1_DAO_Interface {

    /**
     * **********************************************************************************
     * crea la classifica piloti per un determinato anno
     *
     * @param anno:Integer che identifica la classifica dell'anno
     * @return HashMap<Driver, Integer> dove sono inseriti i piloti e il loro
     * punteggio
     * ************************************************************************************
     * @throws può sollevare SQLException
     */
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
            System.err.println("Errore: " + ex.getMessage());
            System.exit(0);
        }

        for (int i = 0; i < app.size(); i++) {
            ArrayList elem = (ArrayList) app.get(i);
            Driver a = new Driver();
            a.setDriverId((Integer) ((ArrayList) app.get(i)).get(0));
            a.setForename((String) ((ArrayList) app.get(i)).get(1));
            int punti = (Integer) ((ArrayList) app.get(i)).get(2);
            ris.put(a, punti);
        }
        return ris;
    }

    /**
     * **********************************************************************************
     * crea la classifica piloti per un determinato anno
     *
     * @param anno:Integer che identifica la classifica dell'anno
     * @return HashMap<Constructor, Integer> dove sono inseriti i piloti e il
     * loro punteggio
     * ************************************************************************************
     * @throws può sollevare SQLException
     */
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

    /**
     * **********************************************************************************
     * restituisce i piloti di un dato costruttore
     *
     * @param anno:Integer            che identifica l'anno
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
     * @param anno:Integer  indica l'anno di riferimento
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
            a.setDate(Date.valueOf((ArrayList) (app.get(i)).get(1)));
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
        String str = "SELECT Races.name, Races.date, Results.position"
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
            ArrayList elem = (ArrayList) app.get(i);
            Race a = new Race();
            a.setName((String) ((ArrayList) app.get(i)).get(0));
            a.setDate(Date.valueOf((ArrayList) (app.get(i)).get(1)));
            int posizione = (Integer) ((ArrayList) app.get(i)).get(2);
            ris.put(a, posizione);
        }
        return ris;
    }

    /**
     * *************************************************************************************
     * restituisce il costruttore di un pilota in un dato anno
     *
     * @param pilota:Driver che identifica il pilota
     * @param anno:int      l'anno a cui riferirsi
     * @return Constructor il costruttore del pilota
     * ***************************************************************************************
     * @throws può sollevare SQLException
     */

    @Override
    public Constructor costruttorePilota(Driver pilota, int anno) {
        String str = "SELECT DISTINCT Constructors.ConstructorId "
                + "FROM Constructors INNER JOIN results ON Constructors.constructorId = results.constructorId INNER JOIN Races ON Results.RaceId = Races.RaceId "
                + "WHERE Results.driverId = " + pilota.getDriverId() + " AND year = " + anno;

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
     * Dato il nome di una tabella ne restituisce gli id in un arraylist di interi
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


}
