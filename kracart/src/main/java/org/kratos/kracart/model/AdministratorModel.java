package org.kratos.kracart.model;

import java.util.Map;

import org.kratos.kracart.entity.Administrator;

public interface AdministratorModel {
	
	public void updatePassword(Administrator admin);
	public Administrator getAdministratorByMail(String email);
	public String getAdminSettingByName(String userName);
	public void saveAdminSetting(Map<String, String> param);

}
