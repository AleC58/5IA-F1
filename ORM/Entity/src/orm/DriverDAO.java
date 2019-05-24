package orm;

import java.util.ArrayList;
import javafx.util.Pair;

public interface DriverDAO {
	
	public int getGareFatteCarriera(Driver pilota);
	public int getPunti(Driver pilota, int anno);
	public Constructor getCostruttoreAnno(Driver pilota, int anno);
	public int getGareFatteCampionato(Driver pilota, int anno);
	
	
}
