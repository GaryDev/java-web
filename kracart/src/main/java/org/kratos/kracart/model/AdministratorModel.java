package org.kratos.kracart.model;

import java.util.List;
import java.util.Map;

import org.kratos.kracart.entity.Administrator;
import org.kratos.kracart.entity.AdministratorAccess;

public interface AdministratorModel {
	
	public String getAdminSettingByName(String userName);
	public Administrator getAdminLevels(String userName);
	public Administrator getAdministrator(Administrator admin);
	public List<Administrator> getAdministrators(Map<String, Object> criteria);
	public AdministratorAccess getAdminAccessModule(AdministratorAccess access);
	public void updateAdminSetting(Map<String, String> param);
	public void updatePassword(Administrator admin);
	public void insertAdministrator(Administrator admin);
	public void updateAdministrator(Administrator admin);
	public void insertAdministratorAccess(AdministratorAccess access);
	public void deleteAdministratorAccess(Map<String, Object> params);

}
