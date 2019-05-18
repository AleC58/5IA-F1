package entity;

import java.util.ArrayList;
import javafx.util.Pair;

public interface DriverDAO {
	
	public int getGareFatte(int idPilota);
	public int getPunti(int idPilota, int anno);
	public Constructor getCostruttoreAnno();
	
	
}
