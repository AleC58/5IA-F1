package entity;

import it.utility.DB.DBEntity;
import java.util.ArrayList;

public class Result implements DBEntity {

	static final int RESULT_ID = 0;
	static final int RACE_ID = 1;
	static final int DRIVER_ID = 2;
	static final int CONSTRUCTOR_ID = 3;
	static final int NUMBER = 4;
	static final int GRID = 5;
	static final int POSITION = 6;
	static final int POSITION_TEXT = 7;
	static final int POSITION_ORDER = 8;
	static final int POINTS = 9;
	static final int LAPS = 10;
	static final int TIME = 11;
	static final int MILLISECONDS = 12;
	static final int FASTEST_LAP = 13;
	static final int RANK = 14;
	static final int FASTEST_LAP_TIME = 15;
	static final int FASTEST_LAP_SPEED = 16;
	static final int STATUS_ID = 17;

	private Integer resultId;
	private Integer raceId;
	private Integer driverId;
	private Integer constructorId;
	private Integer number;
	private Integer grid;
	private Integer position;
	private String positionText;
	private Integer positionOrder;
	private double points;
	private Integer laps;
	private String time;
	private Integer milliseconds;
	private Integer fastestLap;
	private Integer rank;
	private String fastestLapTime;
	private String fastestLapSpeed;
	private Integer statusId;

	public Result() {
	}

	public Integer getResultId() {
		return resultId;
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

	public Integer getGrid() {
		return grid;
	}

	public Integer getPosition() {
		return position;
	}

	public String getPositionText() {
		return positionText;
	}

	public Integer getPositionOrder() {
		return positionOrder;
	}

	public double getPoints() {
		return points;
	}

	public Integer getLaps() {
		return laps;
	}

	public String getTime() {
		return time;
	}

	public Integer getMilliseconds() {
		return milliseconds;
	}

	public Integer getFastestLap() {
		return fastestLap;
	}

	public Integer getRank() {
		return rank;
	}

	public String getFastestLapTime() {
		return fastestLapTime;
	}

	public String getFastestLapSpeed() {
		return fastestLapSpeed;
	}

	public Integer getStatusId() {
		return statusId;
	}

	public void setResultId(Integer resultId) {
		this.resultId = resultId;
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

	public void setGrid(Integer grid) {
		this.grid = grid;
	}

	public void setPosition(Integer position) {
		this.position = position;
	}

	public void setPositionText(String positionText) {
		this.positionText = positionText;
	}

	public void setPositionOrder(Integer positionOrder) {
		this.positionOrder = positionOrder;
	}

	public void setPoints(double points) {
		this.points = points;
	}

	public void setLaps(Integer laps) {
		this.laps = laps;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public void setMilliseconds(Integer milliseconds) {
		this.milliseconds = milliseconds;
	}

	public void setFastestLap(Integer fastestLap) {
		this.fastestLap = fastestLap;
	}

	public void setRank(Integer rank) {
		this.rank = rank;
	}

	public void setFastestLapTime(String fastestLapTime) {
		this.fastestLapTime = fastestLapTime;
	}

	public void setFastestLapSpeed(String fastestLapSpeed) {
		this.fastestLapSpeed = fastestLapSpeed;
	}

	public void setStatusId(Integer statusId) {
		this.statusId = statusId;
	}

	@Override
	public boolean setByDB(ArrayList a) {
		this.constructorId = (Integer) a.get(CONSTRUCTOR_ID);
		this.driverId = (Integer) a.get(DRIVER_ID);
		this.fastestLap = (Integer) a.get(FASTEST_LAP);
		this.fastestLapSpeed = (String) a.get(FASTEST_LAP_SPEED);
		this.fastestLapTime = (String) a.get(FASTEST_LAP_TIME);
		this.grid = (Integer) a.get(GRID);
		this.laps = (Integer) a.get(LAPS);
		this.milliseconds = (Integer) a.get(MILLISECONDS);
		this.number = (Integer) a.get(NUMBER);
		this.points = (double) a.get(POINTS);
		this.position = (Integer) a.get(POSITION);
		this.positionOrder = (Integer) a.get(POSITION_ORDER);
		this.positionText = (String) a.get(POSITION_TEXT);
		this.raceId = (Integer) a.get(RACE_ID);
		this.rank = (Integer) a.get(RANK);
		this.resultId = (Integer) a.get(RESULT_ID);
		this.statusId = (Integer) a.get(STATUS_ID);
		this.time = (String) a.get(TIME);
		return true;
	}

}
