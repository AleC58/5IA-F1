package orm;
import java.util.ArrayList;

public class Lap_Time implements DBEntity {

	static final int LAP = 0;
	static final int RACE_ID = 1;
	static final int DRIVER_ID = 2;
	static final int POSITION = 3;
	static final int TIME = 4;
	static final int MILLISECONDS = 5;

	private Integer raceId;
	private Integer driverId;
	private Integer lap;
	private Integer position;
	private String time;
	private Integer milliseconds;

	Lap_Time() {
	}

	public Integer getRaceId() {
		return raceId;
	}

	public Integer getDriverId() {
		return driverId;
	}

	public Integer getLap() {
		return lap;
	}

	public Integer getPosition() {
		return position;
	}

	public String getTime() {
		return time;
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

	public void setLap(Integer lap) {
		this.lap = lap;
	}

	public void setPosition(Integer position) {
		this.position = position;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public void setMilliseconds(Integer milliseconds) {
		this.milliseconds = milliseconds;
	}

	@Override
	public boolean setByDB(ArrayList a) {
		this.driverId = (Integer) a.get(DRIVER_ID);
		this.lap = (Integer) a.get(LAP);
		this.milliseconds = (Integer) a.get(MILLISECONDS);
		this.position = (Integer) a.get(POSITION);
		this.raceId = (Integer) a.get(RACE_ID);
		this.time = (String) a.get(TIME);
		return true;

	}

}
