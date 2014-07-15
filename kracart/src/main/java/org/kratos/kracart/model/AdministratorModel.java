package org.kratos.kracart.model;

import org.kratos.kracart.entity.Administrator;

public interface AdministratorModel {
	
	public void updatePassword(Administrator admin);
	public Administrator getAdministratorByMail(String email);
	public String getAdminSettingByName(String userName);

}
