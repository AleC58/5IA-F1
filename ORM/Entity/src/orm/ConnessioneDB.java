package orm;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/************************************************************************
* @(#)ConnessioneDB.java
Connessione con il database per accedere ai dati
* @author Chiara Fanello,Riccardo Forese,Riccardo Potente
* @date 29/04/2019
* @version 8.2
*************************************************************************/

public class ConnessioneDB {

	private static Connection connection = null;
	private static String strConnDB
			= "jdbc:sqlite:F:\\scuola\\5°\\informatica\\F1_DAO\\F1.db";
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

	private static void openConnection() throws ClassNotFoundException,
			SQLException {
		if (connection == null) {
			synchronized (ConnessioneDB.class) {
				if (connection == null) {
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
