package orm.entity;
import orm.db.DBEntity;

import java.util.ArrayList;

public class Qualifying implements DBEntity {

	static final int QUALIFY_ID = 0;
	static final int RACE_ID = 1;
	static final int DRIVER_ID = 2;
	static final int CONSTRUCTOR_ID = 3;
	static final int NUMBER = 4;
	static final int POSITION = 5;
	static final int Q1 = 6;
	static final int Q2 = 7;
	static final int Q3 = 8;

	private Integer qualifyId;
	private Integer raceId;
	private Integer driverId;
	private Integer constructorId;
	private Integer number;
	private Integer position;
	private String q1;
	private String q2;
	private String q3;

	public Qualifying() {
	}

	public Integer getQualifyId() {
		return qualifyId;
	}

	public Integer getRaceId() {
		return raceId;
	}

	public Integer getDriverId() {
		return driverId;
	}

	public Integer getConstructorId() {
		return constructorId;
	}

	public Integer getNumber() {
		return number;
	}

	public Integer getPosition() {
		return position;
	}

	public String getQ1() {
		return q1;
	}

	public String getQ2() {
		return q2;
	}

	public String getQ3() {
		return q3;
	}

	public void setQualifyId(Integer qualifyId) {
		this.qualifyId = qualifyId;
	}

	public void setRaceId(Integer raceId) {
		this.raceId = raceId;
	}

	public void setDriverId(Integer driverId) {
		this.driverId = driverId;
	}

	public void setConstructorId(Integer constructorId) {
		this.constructorId = constructorId;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	public void setPosition(Integer position) {
		this.position = position;
	}

	public void setQ1(String q1) {
		this.q1 = q1;
	}

	public void setQ2(String q2) {
		this.q2 = q2;
	}

	public void setQ3(String q3) {
		this.q3 = q3;
	}

	@Override
	public boolean setByDB(ArrayList a) {
		this.qualifyId = (Integer) a.get(QUALIFY_ID);
		this.raceId = (Integer) a.get(RACE_ID);
		this.driverId = (Integer) a.get(DRIVER_ID);
		this.constructorId = (Integer) a.get(CONSTRUCTOR_ID);	
		this.number = (Integer) a.get(NUMBER);
		this.position = (Integer) a.get(POSITION);
		this.q1 = (String) a.get(Q1);
		this.q2 = (String) a.get(Q2);
		this.q3 = (String) a.get(Q3);
		return true;

	}

}
