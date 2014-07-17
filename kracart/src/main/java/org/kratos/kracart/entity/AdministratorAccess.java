package org.kratos.kracart.entity;

import java.io.Serializable;

public class AdministratorAccess implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private int id;
	private String module;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getModule() {
		return module;
	}
	public void setModule(String module) {
		this.module = module;
	}

}
