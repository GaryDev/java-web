package org.kratos.kracart.vo;

public class AdministratorVO {
	
	private String aID;
	private String name;
	private String password;
	private String email;
	private String accessGlobal;
	private String modules;
	
	public String getaID() {
		return aID;
	}
	public void setaID(String aID) {
		this.aID = aID;
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
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getAccessGlobal() {
		return accessGlobal;
	}
	public void setAccessGlobal(String accessGlobal) {
		this.accessGlobal = accessGlobal;
	}
	public String getModules() {
		return modules;
	}
	public void setModules(String modules) {
		if("on".equals(accessGlobal)) {
			modules = "*";
		}
		this.modules = modules;
	}
	
	

}
