package org.kratos.kracart.service;

import java.util.List;
import java.util.Locale;

import org.kratos.kracart.entity.Administrator;

public interface AdminService {
	
	public boolean IsLogon();
	public boolean validateEmail(String email);
	public boolean resetPassword(String email, String ip, Locale locale);
	public List<Administrator> getAdministartors(String start, String limit);
	public int getTotal();

}
