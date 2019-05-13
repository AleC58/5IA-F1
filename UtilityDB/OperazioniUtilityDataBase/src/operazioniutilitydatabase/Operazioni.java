/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package OperazioniUtilityDataBase;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
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

	public static boolean popolaDB() throws SQLException {
		String csvFile = "C:\\Users\\raoul.giurin\\Desktop\\LavoroF1\\PopulationDb\\Dati\\circuits.csv";
		BufferedReader br = null;
		String line = "";
		String cvsSplitBy = ",";

		try {

			br = new BufferedReader(new FileReader(csvFile));

			while ((line = br.readLine()) != null) {

				// use comma as separator
				String[] circuito = line.split(cvsSplitBy);
				insert("Circuito", circuito);
				/*
				System.out.println("Circuito [Code= " + circuito[0] + ", Ref= " + circuito[1] + 
						", Name= " + circuito[2] + ", Location= " + circuito[3] + ", Country= " + circuito[4] + 
						", Lat= " + circuito[5] + ", Lng= " + circuito[6] + ", Alt= " + circuito[7] + 
						", Url= " + circuito[8] + "]");
				 */
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return true;
	}

	public static boolean insert(String nomeTabella, String[] value) throws SQLException {
		Connection conn = ConnessioneDB.getConnection();
		Statement stm = null;
		ResultSet rst = null;
		Exception esito = null;
		boolean riuscita = true;
		ArrayList elencoValori = new ArrayList();
		String strQuery;
		String save = elencoDomanda(value);
		String question = "(";
		if (save.length() > 0) {
			int numeroElementi = 1;
			for (int i = 0; i < save.length(); i++) {
				if (save.charAt(i) == ',') {
					numeroElementi++;
				}
			}
			for (int i = 0; i < (numeroElementi - 1); i++) {
				question += "?,";
			}
			question+="?);";
			strQuery = "INSERT INTO ? VALUES"+question;
			PreparedStatement prep = conn.prepareStatement(strQuery);
			prep.setString(1, nomeTabella);
			String [] elenco=save.split(",");
			for (int i = 2; i < elenco.length+2; i++) {
				prep.setString(i, elenco[i-2]);
			}
			prep.executeUpdate();
			stm = conn.createStatement();
			rst.close();
			stm.close();
			riuscita=true;
		}
		else{
			riuscita=false;
		}
		return riuscita;
	}

	private static String elencoValue(String[] value) {
		String[] valori = value;
		String ris = "";
		for (int i = 0; i < valori.length; i++) {
			ris += "," + valori[i];
		}
		return ris.substring(1); //per saltare la prima virgola inserita
	}

	public static boolean delete(String nomeTabella, String condition) throws SQLException {
		Connection conn = null;
		Statement stm = null;
		ResultSet rst = null;
		ArrayList elencoValori = null;
		String strQuery;
		ResultSetMetaData rsmd;

		strQuery = "DELETE FROM " + nomeTabella + " WHERE " + condition;

		conn = ConnessioneDB.getConnection();
		stm = conn.createStatement();
		rst = stm.executeQuery(strQuery);

		rst.close();
		stm.close();
		return true; ///!!!!!!!!!!!!
	}

	private static String elencoDomanda(String[] value) {
		String[] valori = value;
		String ris = "";
		for (int i = 0; i < valori.length; i++) {
			ris += "," + "?";
		}
		return ris.substring(1);
	}
}
