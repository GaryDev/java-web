package org.kratos.kracart.model;

import java.util.List;
import java.util.Map;

import org.kratos.kracart.entity.Administrator;

public interface AdministratorModel {
	
	public void updatePassword(Administrator admin);
	public Administrator getAdministratorByMail(String email);
	public String getAdminSettingByName(String userName);
	public void saveAdminSetting(Map<String, String> param);
	public Administrator getAdminLevels(String userName);
	public Administrator getAdministratorById(int id);
	public List<Administrator> getAdministrators(Map<String, Object> criteria);

}
