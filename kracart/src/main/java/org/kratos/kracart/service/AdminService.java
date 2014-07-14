package org.kratos.kracart.service;

import java.util.Locale;

public interface AdminService {
	
	public boolean IsLogon();
	public boolean validateEmail(String email);
	public boolean resetPassword(String email, String ip, Locale locale);
	

}
