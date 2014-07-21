package org.kratos.kracart.entity;

import java.io.Serializable;

public class ProductImage implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private int id;
	private String image;
	private short defaultFlag;
	private int sortOrder;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public short getDefaultFlag() {
		return defaultFlag;
	}
	public void setDefaultFlag(short defaultFlag) {
		this.defaultFlag = defaultFlag;
	}
	public int getSortOrder() {
		return sortOrder;
	}
	public void setSortOrder(int sortOrder) {
		this.sortOrder = sortOrder;
	}
}
