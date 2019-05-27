package DB;

import static DB.OperazioniDB.fileName;
import static DB.OperazioniDB.tableName;
import Utilities.Utility;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @(#)ConnessioneDB.java
 *
 */
public class ConnessioneDB {

	private static Connection connection = null;
	private static String strConnDB
			= "jdbc:sqlite:F1.db";
	private static String strDriver = "org.sqlite.JDBC";

	public static Connection getConnection() {
		try {
			openConnection();
		} catch (SQLException ex) {
			Logger.getLogger(ConnessioneDB.class.getName()).log(Level.SEVERE, null, ex);
		} catch (ClassNotFoundException ex) {
			Logger.getLogger(ConnessioneDB.class.getName()).log(Level.SEVERE, null, ex);
		} finally {
			return connection;
		}

	}

	public static void setStringConnection(String strC, String strD) {
		if (strC != null) {
			strConnDB = strC;
		}
		if (strD != null) {
			strDriver = strD;
		}
	}

	private static void deleteTable(String nameTable) throws SQLException {
		Statement stm = connection.createStatement();
		stm.execute("DELETE FROM " + nameTable);
		stm.close();
	}

	private static void deleteTables() throws SQLException {

		deleteTable("Circuits");
		deleteTable("Constructors");
		deleteTable("Drivers");
		deleteTable("Races");
		deleteTable("Results");
	}

	private static void insertIntoTables() throws ClassNotFoundException, SQLException {

		for (int i = 0; i < tableName.length; i++) {
			
			int righe = Utility.contaRighe(fileName[i]);

			for (int j = 1; j <= righe; j++) {	//Per ogni riga del file
				String[] res = Utility.readLineFileCSV(fileName[i], j);
				Utility.fromCSVToTable(res, tableName[i]);
			}
		}

	}

	public static void openConnectionXAgg() throws ClassNotFoundException,
			SQLException,
			Exception {

		Class.forName(strDriver);
		connection = DriverManager.getConnection(strConnDB, "", "");

	}

	private static void openConnection() throws ClassNotFoundException,
			SQLException,
			Exception {
		if (connection == null) {
			synchronized (ConnessioneDB.class) {
				if (Utility.getChangesDate()) {
					Utility.downloadZIP();
					Utility.unzip();
					openConnectionXAgg();
					deleteTables();
					//ConnessioneDB.closeConnection();
					if (connection == null) {
						Class.forName(strDriver);
						connection = DriverManager.getConnection(strConnDB, "", "");
					}
					insertIntoTables();
					//ConnessioneDB.closeConnection();
				} else if (connection == null) {
					Class.forName(strDriver);
					connection = DriverManager.getConnection(strConnDB, "", "");
				}
			}
		}
	}

	public static void closeConnection() throws SQLException {
		if (connection != null) {
			connection.close();
		}
	}
}
