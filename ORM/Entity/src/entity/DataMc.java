package entity;

public class DataMc {

	static DataMc dataMc = null;
	
	private DataMc(){	
	}
	public static synchronized DataMc getDataMc() {

		if (dataMc == null) {
			dataMc = new DataMc();
		}
		return dataMc;
	}
	
}
