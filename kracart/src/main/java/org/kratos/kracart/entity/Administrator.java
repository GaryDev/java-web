package org.kratos.kracart.entity;

import java.io.Serializable;
import java.util.List;

public class Administrator implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private int id;
	private String name;
	private String password;
	private String settings;
	private String email;
	private List<AdministratorAccess> access;
	
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
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getSettings() {
		return settings;
	}
	public void setSettings(String settings) {
		this.settings = settings;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public List<AdministratorAccess> getAccess() {
		return access;
	}
	public void setAccess(List<AdministratorAccess> access) {
		this.access = access;
	}

}
