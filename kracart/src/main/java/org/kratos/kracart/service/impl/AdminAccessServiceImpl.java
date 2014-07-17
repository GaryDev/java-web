package org.kratos.kracart.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.kratos.kracart.core.bean.AdminAccessLevel;
import org.kratos.kracart.core.modules.BaseAccess;
import org.kratos.kracart.entity.Administrator;
import org.kratos.kracart.entity.AdministratorAccess;
import org.kratos.kracart.model.AdministratorModel;
import org.kratos.kracart.service.AdminAccessService;
import org.kratos.kracart.utility.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("adminAccessService")
public class AdminAccessServiceImpl implements AdminAccessService {
	
	private static final String MODULE_DIR = "org.kratos.kracart.core.modules.access";
	
	@Autowired
	private AdministratorModel administratorModel;
	
	private Administrator admin;
	
	private Set<String> modules;
	
	List<AdminAccessLevel> levels;
	
	public void initialize(String userName) {
		admin = administratorModel.getAdminLevels(userName);
		modules = getUserLevels();
		levels = getLevels();
	}
	
	private Set<String> getUserLevels() {
		Set<String> modules = new HashSet<String>();
		List<AdministratorAccess> levels = admin.getAccess();
		for (AdministratorAccess access : levels) {
			modules.add(access.getModule());
		}
		if(modules.contains("*")) {
			modules.clear();
			modules.addAll(CommonUtils.getClassName(MODULE_DIR, getClass().getClassLoader().getResource("")));
		}
		return modules;
	}
	
	private List<AdminAccessLevel> getLevels() {
		List<AdminAccessLevel> levels = new ArrayList<AdminAccessLevel>();
		for (String module : modules) {
			try {
				BaseAccess access = (BaseAccess) Class.forName(module).newInstance();
				String group = access.getGroup();
				AdminAccessLevel level = new AdminAccessLevel();
				if(levels.size() > 0) {
					for (AdminAccessLevel l : levels) {
						if(group.equals(l.getGroup())) {
							level = l;
							break;
						}
					}
				}
				List<BaseAccess> modules = new ArrayList<BaseAccess>();
				if(group.equals(level.getGroup())) {
					if(level.getModules() == null || level.getModules().size() == 0) {
						level.setModules(modules);
					}
					level.getModules().add(access);
				} else {
					modules.add(access);
					level.setModules(modules);
					level.setGroup(group);
				}
				levels.add(level);
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
		return levels;
	}

	@Override
	public String getModules() {
		return null;
	}

	@Override
	public String getOutputModule() {
		return null;
	}

}
