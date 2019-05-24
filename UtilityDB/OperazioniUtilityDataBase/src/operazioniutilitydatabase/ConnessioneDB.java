package OperazioniUtilityDataBase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @(#)ConnessioneDB.java
 *
 */
public class ConnessioneDB {

    private static Connection connection = null;
    private static String strConnDB
            = "jdbc:sqlite:prova.db";
    private static String strDriver = "org.sqlite.JDBC";

    public static Connection getConnection() {
        try {
            openConnection();

        } catch (SQLException ex) {
            Logger.getLogger(ConnessioneDB.class.getName()).log(Level.SEVERE, null, ex);
			System.out.println("Errore connessione");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ConnessioneDB.class.getName()).log(Level.SEVERE, null, ex);
			System.out.println("Errore connessione");
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
