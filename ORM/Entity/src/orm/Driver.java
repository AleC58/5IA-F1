package orm;
import java.sql.Date;
import java.util.ArrayList;

public class Driver implements DBEntity {

	static final int DRIVER_ID = 0;
	static final int DRIVER_REF = 1;
	static final int NUMBER = 2;
	static final int CODE = 3;
	static final int FORENAME = 4;
	static final int SURNAME = 5;
	static final int DOB = 6;
	static final int NATIONALITY = 7;
	static final int URL = 8;
	static final int CONSTRUCTOR_ID = 9;

	private Integer driverId;
	private String driverRef;
	private Integer number;
	private String code;
	private String forename;
	private String surname;
	private Date dob;
	private String nationality;
	private String url;
	private Integer constructorId;
	private Integer punti;

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public void setConstructorId(Integer constructorId) {
		this.constructorId = constructorId;
	}

	public String getSurname() {
		return surname;
	}

	public Integer getConstructorId() {
		return constructorId;
	}

	public Driver() {
	}

	public Integer getPoints(int year) {
		return punti;
	}

	public Integer getDriverId() {
		return driverId;
	}

	public String getDriverRef() {
		return driverRef;
	}

	public Integer getNumber() {
		return number;
	}

	public String getCode() {
		return code;
	}

	public String getForename() {
		return forename;
	}

	public Date getDob() {
		return dob;
	}

	public String getNationality() {
		return nationality;
	}

	public String getUrl() {
		return url;
	}

	public void setDriverId(Integer driverId) {
		this.driverId = driverId;
	}

	public void setDriverRef(String driverRef) {
		this.driverRef = driverRef;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public void setForename(String forename) {
		this.forename = forename;
	}

	public void setDob(Date dob) {
		this.dob = dob;
	}

	public void setNationality(String nationality) {
		this.nationality = nationality;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Override
	public boolean setByDB(ArrayList a) {
		this.code = (String) a.get(CODE);
		this.dob = (Date) a.get(DOB);
		this.driverId = (Integer) a.get(DRIVER_ID);
		this.driverRef = (String) a.get(DRIVER_REF);
		this.forename = (String) a.get(FORENAME);
		this.nationality = (String) a.get(NATIONALITY);
		this.number = (Integer) a.get(NUMBER);
		this.url = (String) a.get(URL);
		this.constructorId = (Integer) a.get(CONSTRUCTOR_ID);
		this.surname = (String) a.get(SURNAME);
		return true;
	}

}
