package provacsv;

import DB.ConnessioneDB;
import DB.OperazioniDB;
import Utilities.Utility;
import java.sql.SQLException;

public class ProvaCsv {
    
    public static void main(String[] args) throws ClassNotFoundException, SQLException, Exception {
        	
//		if(Utility.getChangesDate()){
			Utility.downloadZIP(100);
//		}

		//OperazioniDB.main(args);
		
        /*
        int righe = Utility.contaRighe("results.csv");
        
        for (int i = 1; i <= righe; i++) {
            String[] res = Utility.readLineFileCSV("results.csv", i);
            Utility.fromCSVToTable(res, "Results");
        }
        
        ConnessioneDB.closeConnection();
        */
    }
}