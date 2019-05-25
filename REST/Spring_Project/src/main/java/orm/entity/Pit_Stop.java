package orm.entity;
import orm.db.DBEntity;

import java.time.LocalTime;
import java.util.ArrayList;

public class Pit_Stop implements DBEntity {

	static final int STOP = 0;
	static final int RACE_ID = 1;
	static final int DRIVER_ID = 2;
	static final int LAP = 3;
	static final int TIME = 4;
	static final int DURATION = 5;
	static final int MILLISECONDS= 6;
	
	private Integer raceId;
	private Integer driverId;
	private Integer stop;
	private Integer lap;
	private LocalTime time; //Tempo a cui è arrivato al pitstop(forse)
	private String duration;
	private Integer milliseconds;

	Pit_Stop() {
	}

	public Integer getRaceId() {
		return raceId;
	}

	public Integer getDriverId() {
		return driverId;
	}

	public Integer getStop() {
		return stop;
	}

	public Integer getLap() {
		return lap;
	}

	public LocalTime getTime() {
		return time;
	}

	public String getDuration() {
		return duration;
	}

	public Integer getMilliseconds() {
		return milliseconds;
	}

	public void setRaceId(Integer raceId) {
		this.raceId = raceId;
	}

	public void setDriverId(Integer driverId) {
		this.driverId = driverId;
	}

	public void setStop(Integer stop) {
		this.stop = stop;
	}

	public void setLap(Integer lap) {
		this.lap = lap;
	}

	public void setTime(LocalTime time) {
		this.time = time;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	public void setMilliseconds(Integer milliseconds) {
		this.milliseconds = milliseconds;
	}

	@Override
	public boolean setByDB(ArrayList a) {
	this.driverId=(Integer) a.get(DRIVER_ID);
	this.duration=(String) a.get(DURATION);
	this.lap=(Integer) a.get(LAP);
	this.milliseconds=(Integer) a.get(MILLISECONDS);
	this.raceId=(Integer) a.get(RACE_ID);
	this.stop=(Integer) a.get(STOP);
	this.time=(LocalTime) a.get(TIME);
	return true;
	}

}
