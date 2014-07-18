package org.kratos.kracart.service;

import java.util.ResourceBundle;


public interface AdminAccessService {
	
	public void setResouceBundle(ResourceBundle bundle);
	public void setUserName(String userName);
	public void setContextPath(String path);
	public void initialize();
	public String getModuleObjects();
	public String getOutputModule();

}
