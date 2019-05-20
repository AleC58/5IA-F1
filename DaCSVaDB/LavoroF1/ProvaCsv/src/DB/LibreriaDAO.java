package DB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.util.Pair;

/**
 *
 * @author carlosalvagno
 */
public class LibreriaDAO {
//
//    public static ArrayList<Libreria> loadLibrary() {
//        try {
//            String query = "SELECT  * from tblLibreria";
//            ArrayList<Libreria> lib = new ArrayList<>();
//            ArrayList arr;
//            arr = OperazioniDB.getRow("tblLibreria", null, null, null); //OperationManager.getIstance().getResults(query, 0);
//            for (Object o : arr) {
//                Libreria d = new Libreria();
//                d.setByDB((ArrayList) o);
//                lib.add(d);
//            }
//            return lib;
//
//        } catch (Exception ex) {
//            Logger.getLogger(LibreriaDAO.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        return null;
//    }
//
//    public static ArrayList<Autore> loadAutoriLibro(int idLibro) {
//        ArrayList app = new ArrayList();
//        try { 
//              app = OperazioniDB.getResult("Select tblAutore.* FROM tblAutore inner join tbrLibroAutore on "+
//                      " tblAutore.CodiceAutore = tbrLibroAutore.CodiceAutore WHERE tbrLibroAutore.CodiceLibro = "+idLibro);
//      
//        } catch (SQLException e) {
//            Logger.getLogger(LibreriaDAO.class.getName()).log(Level.SEVERE, null, e);
//        }
//        ArrayList<Autore> authors = new ArrayList<Autore>();
//        for (int i = 0; i < app.size(); i++) {
//            ArrayList elem = (ArrayList) app.get(i);
//            Autore a = new Autore();
//            a.setByDB(elem);
//            authors.add(a);
//
//        }
//        return authors;
//    }
//
//    public static ArrayList<Pair<Libro, Integer>> loadLibriDisponibili(int idLibreria) {
//        Connection conn;
//        PreparedStatement pstm;
//        Libro l;
//        int copieDisponibili;
//        String strCommand = "Select tblLibro.*, Copie " +
//                            "From tblLibro inner join tblScorta on tblLibro.CodiceLibro = tblScorta.CodiceLibro "+
//                            "Where CodiceLibreria = ?";
//
//        ArrayList<Pair<Libro, Integer>> app = new ArrayList<Pair<Libro, Integer>>();
//        try { 
//           conn = ConnessioneDB.getConnection();
//           pstm = conn.prepareStatement(strCommand);
//           pstm.setInt(1, idLibreria);
//           ResultSet rs = pstm.executeQuery();
//           while (rs.next()) {
//               copieDisponibili = rs.getInt("Copie");
//               if (copieDisponibili > 0) {
//                    l = new Libro(rs.getString("Titolo"), rs.getInt("CodiceGenere"),
//                       rs.getString("Editore"), rs.getDouble("Prezzo"), rs.getInt("CodiceLibro"));
//                    app.add(new Pair(l, copieDisponibili));
//               }
//           }
//           pstm.close();
//        } catch (SQLException e) {
//            Logger.getLogger(LibreriaDAO.class.getName()).log(Level.SEVERE, null, e);
//        }
//
//        return app;
//    }    
// 
//    public static String loadDescrizioneGenere(int idGenere) {
//        Connection conn;
//        PreparedStatement pstm;
//        String strCommand = "Select  Descrizione From tblGenere " +
//                            "Where CodiceGenere = ?";
//
//        String descrizione = "";
//        try { 
//           conn = ConnessioneDB.getConnection();
//           pstm = conn.prepareStatement(strCommand);
//           pstm.setInt(1, idGenere);
//           ResultSet rs = pstm.executeQuery();
//           while (rs.next()) {
//              descrizione = rs.getString("Descrizione");
//           }
//           pstm.close();
//        } catch (SQLException e) {
//            Logger.getLogger(LibreriaDAO.class.getName()).log(Level.SEVERE, null, e);
//        }
//
//        return descrizione;
//    }    
//     
    
    
}
