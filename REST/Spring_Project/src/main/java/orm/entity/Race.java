package orm.entity;
import orm.db.DBEntity;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;

public class Race implements DBEntity {
		
	static final int RACE_ID = 0;	
	static final int YEAR = 1;
	static final int ROUND= 2;
	static final int CIRCUIT_ID = 3;
	static final int NAME = 4;
	static final int DATE = 5;
	static final int TIME = 6;
	static final int URL = 7;
	
	private Integer raceId;
	private Integer year;
	private Integer round;
	private Integer circuitId;
	private String name;
	private Date date;
	private LocalTime time;
	private String url;

	public Race() {
	}

	public Integer getRaceId() {
		return raceId;
	}

	public Integer getYear() {
		return year;
	}

	public Integer getRound() {
		return round;
	}

	public Integer getCircuitId() {
		return circuitId;
	}

	public String getName() {
		return name;
	}

	public Date getDate() {
		return date;
	}

	public LocalTime getTime() {
		return time;
	}

	public String getUrl() {
		return url;
	}

	public void setRaceId(Integer raceId) {
		this.raceId = raceId;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public void setRound(Integer round) {
		this.round = round;
	}

	public void setCircuitId(Integer circuitId) {
		this.circuitId = circuitId;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public void setTime(LocalTime time) {
		this.time = time;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Override
	public boolean setByDB(ArrayList a) {
		this.circuitId=(Integer) a.get(CIRCUIT_ID);
		this.date=(Date) a.get(DATE);
		this.name=(String) a.get(NAME);
		this.raceId=(Integer) a.get(RACE_ID);
		this.round=(Integer) a.get(ROUND);
		this.time=(LocalTime) a.get(TIME);
		this.url=(String) a.get(URL);
		this.year=(Integer) a.get(YEAR);
		return true; 
	}

}
