package entity;

import it.utility.DB.DBEntity;
import java.util.ArrayList;

public class Circuit implements DBEntity {

	static final int CIRCUIT_ID = 0;
	static final int CIRCUIT_REF = 1;
	static final int NAME = 2;
	static final int LOCATION = 3;
	static final int COUNTRY = 4;
	static final int LAT = 5;
	static final int LNG = 6;
	static final int ALT = 7;
	static final int URL = 8;

	private Integer circuitId;
	private Integer alt;
	private String circuitRef;
	private String name;
	private String location;
	private String country;
	private double lat;
	private double lng;
	private String url;
	

	public Circuit() {
	}

	public Integer getCircuitId() {
		return circuitId;
	}

	public Integer getAlt() {
		return alt;
	}

	public String getCircuitRef() {
		return circuitRef;
	}

	public String getName() {
		return name;
	}

	public String getCountry() {
		return country;
	}

	public String getLocation() {
		return location;
	}

	public double getLat() {
		return lat;
	}

	public double getLng() {
		return lng;
	}

	public String getUrl() {
		return url;
	}

	public void setCircuitId(Integer circuitId) {
		this.circuitId = circuitId;
	}

	public void setAlt(Integer alt) {
		this.alt = alt;
	}

	public void setCircuitRef(String circuitRef) {
		this.circuitRef = circuitRef;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}

	public void setLng(double lng) {
		this.lng = lng;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	@Override
	public boolean setByDB(ArrayList a) {
		this.alt = (Integer) a.get(ALT);
		this.circuitId = (Integer) a.get(CIRCUIT_ID);
		this.circuitRef = (String) a.get(CIRCUIT_REF);
		this.country = (String) a.get(COUNTRY);
		this.lat = (double) a.get(LAT);
		this.lng = (double) a.get(LNG);
		this.location = (String) a.get(LOCATION);
		this.name = (String) a.get(NAME);
		this.url = (String) a.get(URL);
		return true;
	}

}
