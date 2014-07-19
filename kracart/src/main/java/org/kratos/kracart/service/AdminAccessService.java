package org.kratos.kracart.service;

import java.util.List;
import java.util.ResourceBundle;

import org.kratos.kracart.core.bean.AdminModuleParent;


public interface AdminAccessService {
	
	public void setResouceBundle(ResourceBundle bundle);
	public void setUserName(String userName);
	public void setContextPath(String path);
	public void initialize();
	public String getModuleObjects();
	public String getOutputModule();
	public List<AdminModuleParent> getAdminModules(String name);
	public List<AdminModuleParent> getModules(boolean isGlobal);

}
