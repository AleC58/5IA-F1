/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package OperazioniUtilityDataBase;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;

public class Operazioni {

    public static int getTipoAttributo(String nomeTabella, String nomeAttributo)
            throws ClassNotFoundException, SQLException {
        Connection conn;
        DatabaseMetaData mtd;
        ResultSet rstCol = null;
        int ris;

        conn = ConnessioneDB.getConnection();
        mtd = conn.getMetaData();
        rstCol = mtd.getColumns(null, null, nomeTabella, nomeAttributo);

        if (rstCol.next()) {
            ris = rstCol.getInt("DATA_TYPE");
        } else {
            ris = 0;
        }

        rstCol.close();
        return ris;
    }

    public static boolean isNumerico(String nomeTabella, String nomeAttributo)
            throws ClassNotFoundException, SQLException {
        int tipo = getTipoAttributo(nomeTabella, nomeAttributo);

        return ((tipo == Types.INTEGER) || (tipo == Types.DECIMAL)
                || (tipo == Types.FLOAT) || (tipo == Types.DOUBLE)
                || (tipo == Types.BINARY) || (tipo == Types.BIGINT)
                || (tipo == Types.NUMERIC) || (tipo == Types.REAL)
                || (tipo == Types.SMALLINT) || (tipo == Types.TINYINT));
    }

    /**
     * Recupera i valori di una riga
     *
     * @param nomeTabella il nome della tabella o query
     * @param nomeKey il nome del campo chiave
     * @param valKey il valore della chiave
     * @param elencoNomiCampi, ArrayList contenente i nomi dei campi da estrarre
     * @return ArrayList i cui elementi sono ArrayList delle tplue dei campi
     * specificati
     * @throws java.lang.ClassNotFoundException
     * @throws java.sql.SQLException
     */
    public static ArrayList getRow(String nomeTabella, String nomeKey,
            Object valKey, String[] elencoNomiCampi)
            throws ClassNotFoundException, SQLException {
        Connection conn = null;
        Statement stm = null;
        ResultSet rst = null;
        ArrayList elencoTuple = new ArrayList();
        ArrayList elencoValori = null;
        String strQuery;
        boolean estraiTutti = true;
        ResultSetMetaData rsmd;
        int numberOfColumns;

        if ((elencoNomiCampi == null) || (elencoNomiCampi.length == 0)) {
            strQuery = "Select * ";
        } else {
            estraiTutti = false;
            strQuery = "Select ";
            for (Object x : elencoNomiCampi) {
                strQuery += x + ",";
            }
            strQuery = strQuery.substring(0, strQuery.length() - 1);
        }
        strQuery += " FROM " + nomeTabella;
        if ((nomeKey != null) && (!nomeKey.equals("")) && (valKey != null)) {
            strQuery += " WHERE " + nomeKey + "=";
            if (isNumerico(nomeTabella, nomeKey)) {
                strQuery += valKey;
            } else {
                strQuery += "'" + valKey + "'";
            }
        }

        conn = ConnessioneDB.getConnection();
        stm = conn.createStatement();
        rst = stm.executeQuery(strQuery);
        if (estraiTutti) {
            rsmd = rst.getMetaData();
            numberOfColumns = rsmd.getColumnCount();
        } else {
            numberOfColumns = elencoNomiCampi.length;
        }
        while (rst.next()) {
            elencoValori = new ArrayList(numberOfColumns);
            if (estraiTutti) {
                for (int i = 1; i <= numberOfColumns; i++) {
                    elencoValori.add(rst.getObject(i));
                }
            } else {
                for (int i = 0; i < numberOfColumns; i++) {
                    elencoValori.add(rst.getObject((String) elencoNomiCampi[i]));
                }
            }
            elencoTuple.add(elencoValori);
        }

        rst.close();
        stm.close();
        return elencoTuple;
    }

    /**
     * Recupera i valori di una colonna
     *
     * @param nomeTabella il nome della tabella o query
     * @param nomeAttributo il nome della colonna
     * @param nomeKey il nome del campo chiave
     * @param valKey valore della chiave
     * @return ArrayList contenente i valori della colonna richiesta
     * @throws java.lang.ClassNotFoundException
     * @throws java.sql.SQLException
     */
    public static ArrayList getCols(String nomeTabella, String nomeAttributo,
            String nomeKey, Object valKey)
            throws ClassNotFoundException, SQLException {
        Connection conn = null;
        Statement stm = null;
        ResultSet rst = null;
        Exception esito = null;
        ArrayList elencoValori = new ArrayList();
        String strQuery;

        strQuery = "Select " + nomeAttributo + " FROM " + nomeTabella;
        if ((nomeKey != null) && (!nomeKey.equals(""))) {
            strQuery += " WHERE " + nomeKey + "=";
            if (isNumerico(nomeTabella, nomeKey)) {
                strQuery += valKey;
            } else {
                strQuery += "'" + valKey + "'";
            }
        }
        conn = ConnessioneDB.getConnection();
        stm = conn.createStatement();
        rst = stm.executeQuery(strQuery);
        while (rst.next()) {
            elencoValori.add(rst.getObject(1));
        }
        rst.close();
        stm.close();

        return elencoValori;
    }

    /**
     * public static boolean popolaDB() throws SQLException { String csvFile =
     * "C:\\Users\\raoul.giurin\\Desktop\\LavoroF1\\PopulationDb\\Dati\\circuits.csv";
     * BufferedReader br = null; String line = ""; String cvsSplitBy = ","; try
     * { br = new BufferedReader(new FileReader(csvFile)); while ((line =
     * br.readLine()) != null) { String[] circuito = line.split(cvsSplitBy);
     * insert("Circuito", circuito); } } catch (FileNotFoundException e) {
     * e.printStackTrace(); } catch (IOException e) { e.printStackTrace(); }
     * finally { if (br != null) { try { br.close(); } catch (IOException e) {
     * e.printStackTrace(); } } } return true; }*
     */
    /**
     * INSERT
     *
     * @param nomeTabella nome tabella con aggiunta della colonna(se si esegue
     * non su tutta la tabella)
     * @param value array dei valori da assegnare
     */
    public static void insert(String nomeTabella, String[] value) {
        try {
            Connection conn = ConnessioneDB.getConnection();
            Statement stm = conn.createStatement();;
            String save = elencoDomanda(value);
            String question = "(";
            if (save.length() > 0) {
                int numeroElementi = 1;
                for (int i = 0; i < save.length(); i++) {
                    if (save.charAt(i) == ',') {
                        numeroElementi++;
                    }
                }
                for (int i = 1; i < (numeroElementi - 1); i++) {
                    question += "?,";
                }
                question += "?);";
                String strQuery = "INSERT INTO " + nomeTabella + " VALUES " + question;
                PreparedStatement prep = conn.prepareStatement(strQuery);
                //prep.setString(1, nomeTabella);
                for (int i = 0; i < value.length; i++) {
                    prep.setString((i + 2), value[i]);
                }
                prep.executeUpdate();
                stm.close();
            }
        } catch (SQLException e) {
            System.err.println("Errore nell'inserimento");
            System.err.println(e.getMessage());
        }
    }

    private static String elencoValue(String[] value) {
        String[] valori = value;
        String ris = "";
        for (int i = 0; i < valori.length; i++) {
            ris += "," + valori[i];
        }
        return ris.substring(1); //per saltare la prima virgola inserita
    }

    /**
     * DELETE
     *
     * @param nomeTabella nome tabella con aggiunta della colonna(se si esegue
     * non su tutta la tabella)
     * @param condition la condizione di cancellazione
     */
    public static void delete(String nomeTabella, String condition) {
        try {
            Connection conn = ConnessioneDB.getConnection();
            Statement stm = conn.createStatement();
            String strQuery = "DELETE FROM " + nomeTabella + " WHERE " + condition;
            PreparedStatement prep = conn.prepareStatement(strQuery);
            prep.executeUpdate();
            stm.close();

        } catch (SQLException e) {
            System.err.println("Errore nella cancellazione");
            System.err.println(e.getMessage());
        }
    }

    /**
     * @param nomeTabella nome tabella con aggiunta della colonna(se si esegue
     * non su tutta la tabella)
     * @param cols array contenente le colonne da aggiornare
     * @param valori array contenente i valori da assegnare alle colonne
     * @param condition la condizione di update
     */
    public static void update(String nomeTabella, String[] cols, String[] valori, String condition) {
        try {
            Connection conn = ConnessioneDB.getConnection();
            Statement stm = conn.createStatement();
            for (int i = 0; i < cols.length; i++) {
                String strQuery = "UPDATE " + nomeTabella + " SET " + cols[i] + " =? WHERE " + condition;
                PreparedStatement prep = conn.prepareStatement(strQuery);
                prep.setString(1, valori[i]);
                prep.executeUpdate();
            }
            stm.close();
        } catch (SQLException e) {
            System.err.println("Errore nell'aggiornamento");
            System.err.println(e.getMessage());
        }
    }

    private static String getValori(String[] cols, String[] value) {
        String ris = "";
        for (int i = 0; i < cols.length; i++) {
            ris += cols[i] + " = " + value[i];
        }
        return ris;
    }

    private static String elencoDomanda(String[] value) {
        String[] valori = value;
        String ris = "";
        for (int i = 0; i < valori.length; i++) {
            ris += "," + "?";
        }
        return ris.substring(1);
    }

    public static ArrayList getResult(String query) throws SQLException {
        ArrayList row;
        ArrayList rows = new ArrayList();
        Statement stm = ConnessioneDB.getConnection().createStatement();
        ResultSet rs = stm.executeQuery(query);
        ResultSetMetaData rsmd = rs.getMetaData();
        int numberOfColumns = rsmd.getColumnCount();
        while (rs.next()) {
            row = new ArrayList(numberOfColumns);
            for (int i = 1; i <= numberOfColumns; i++) {
                row.add(rs.getObject(i));
            }

            rows.add(row);
        }
        stm.close();
        return rows;
    }

    //Ritorna tutti i piloti
    private static ArrayList getPilot() throws SQLException {
        Connection conn = null;
        Statement stm = null;
        ResultSet rst = null;
        Exception esito = null;
        ArrayList ris = new ArrayList();
        String strQuery;
        strQuery = "Select * From Drivers";
        conn = ConnessioneDB.getConnection();
        stm = conn.createStatement();
        rst = stm.executeQuery(strQuery);
        while (rst.next()) {
            ris.add(rst.getObject(1));
        }
        rst.close();
        stm.close();

        return ris;
    }

    //non concluso
    /* private static ArrayList getRows(String nomeTabella, String col, int pos) throws SQLException {
		Connection conn = ConnessioneDB.getConnection();
		Statement stm = conn.createStatement();
		String nomeTab = nomeTabella;
		
		String strQuery = "SELECT COUNT ? FROM ?";
		PreparedStatement prep = conn.prepareStatement(strQuery);
		prep.setString(1, col);
		prep.setString(2, nomeTabella);
		ResultSet rs = stm.executeQuery(strQuery);
		ris = rs.getInt(1); //Teoricamente è corretto 1 e non 0, da verificare
		stm.close();
		//return ris;
	}
     */
    //col deve essere il nome di una colonna qualsiasi della tabella che ci
    //viene passata in esame
    private static int numRows(String nomeTabella, String col) throws SQLException {
        int ris = 0;
        try {
            Connection conn = ConnessioneDB.getConnection();
            Statement stm = conn.createStatement();
            String nomeTab = nomeTabella;
            String strQuery = "SELECT COUNT " + col + " FROM " + nomeTab;
            PreparedStatement prep = conn.prepareStatement(strQuery);
            ResultSet rs = stm.executeQuery(strQuery);
            ris = rs.getInt(1);
            stm.close();
        } catch (SQLException e) {
            System.err.println("Errore nell'aggiornamento");
            System.err.println(e.getMessage());
        }
        return ris;
    }
    /*
	ResultSet rs=statement.executeQuery(query);

	int i = 0;
	while (rs.next())
		arr[i++]=rs.getString(1);
     */
}
