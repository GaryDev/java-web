package org.kratos.kracart.service;

import java.util.List;
import java.util.Locale;

import org.kratos.kracart.entity.Administrator;
import org.kratos.kracart.vo.AdministratorVO;

public interface AdminService {
	
	public boolean IsLogon();
	public boolean validateEmail(String email);
	public boolean resetPassword(String email, String ip, Locale locale);
	public Administrator getAdministratorById(int id);
	public List<Administrator> getAdministrators(String start, String limit);
	public int getTotal();
	public int saveAdministrator(AdministratorVO voAdmin);

}
