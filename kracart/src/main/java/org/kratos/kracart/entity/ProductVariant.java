package org.kratos.kracart.entity;

import java.io.Serializable;

public class ProductVariant implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private int id;
	private int languageId;
	private String name;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getLanguageId() {
		return languageId;
	}
	public void setLanguageId(int languageId) {
		this.languageId = languageId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

}
