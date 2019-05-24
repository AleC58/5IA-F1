package entity;

import it.utility.DB.DBEntity;
import java.util.ArrayList;

public class Constructor implements DBEntity{
	
	static final int CONSTRUCTOR_ID = 0;
	static final int CONSTRUCTOR_REF = 1;
	static final int NAME = 2;
	static final int NATIONALITY =3;
	static final int URL = 4;

	private Integer constructorId;
	private String constructorRef;
	private String name;
	private String nationality;
	private String url;

	public Constructor() {
	}

	public Integer getConstructorId() {
		return constructorId;
	}

	public String getConstructorRef() {
		return constructorRef;
	}

	public String getName() {
		return name;
	}

	public String getNationality() {
		return nationality;
	}

	public String getUrl() {
		return url;
	}

	public void setConstructorId(Integer constructorId) {
		this.constructorId = constructorId;
	}

	public void setConstructorRef(String constructorRef) {
		this.constructorRef = constructorRef;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setNationality(String nationality) {
		this.nationality = nationality;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Override
	public boolean setByDB(ArrayList a) {
	this.constructorId=(Integer) a.get(CONSTRUCTOR_ID);
	this.constructorRef=(String) a.get(CONSTRUCTOR_REF);
	this.name=(String) a.get(NAME);
	this.nationality=(String) a.get(NATIONALITY);
	this.url=(String) a.get(URL);
	return true;
	}
	
}
