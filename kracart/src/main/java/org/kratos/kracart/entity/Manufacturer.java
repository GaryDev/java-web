package org.kratos.kracart.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

public class Manufacturer implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private int id;
	private String name;
	private String image;
	private Timestamp dateAdded;
	private Timestamp dateUpdated;
	private List<ManufacturerInfo> infos;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public Timestamp getDateAdded() {
		return dateAdded;
	}
	public void setDateAdded(Timestamp dateAdded) {
		this.dateAdded = dateAdded;
	}
	public Timestamp getDateUpdated() {
		return dateUpdated;
	}
	public void setDateUpdated(Timestamp dateUpdated) {
		this.dateUpdated = dateUpdated;
	}
	public List<ManufacturerInfo> getInfos() {
		return infos;
	}
	public void setInfos(List<ManufacturerInfo> infos) {
		this.infos = infos;
	}

}
