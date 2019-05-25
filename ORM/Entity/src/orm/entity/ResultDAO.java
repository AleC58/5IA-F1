package orm.entity;
import java.util.HashMap;

public interface ResultDAO {
	
	public HashMap<Driver, Integer> getClassificaPiloti(int anno);
	public HashMap<Constructor, Integer> getclassificaCostruttori(int anno);
	public HashMap<Driver, Integer> getRisultatiGara(Race gara);
	
}
