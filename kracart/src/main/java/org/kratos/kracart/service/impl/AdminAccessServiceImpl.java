package org.kratos.kracart.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.kratos.kracart.core.bean.AdminAccessLevel;
import org.kratos.kracart.core.modules.BaseAccess;
import org.kratos.kracart.core.modules.ModuleSubGroup;
import org.kratos.kracart.entity.Administrator;
import org.kratos.kracart.entity.AdministratorAccess;
import org.kratos.kracart.entity.Language;
import org.kratos.kracart.model.AdministratorModel;
import org.kratos.kracart.model.LanguageModel;
import org.kratos.kracart.service.AdminAccessService;
import org.kratos.kracart.utility.CommonUtils;
import org.kratos.kracart.utility.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("adminAccessService")
public class AdminAccessServiceImpl implements AdminAccessService {
	
	private static final String MODULE_DIR = "org.kratos.kracart.core.modules.access";
	
	@Autowired
	private AdministratorModel administratorModel;
	@Autowired
	private LanguageModel languageModel;
	
	private Administrator admin;
	private List<String> modules;
	private List<AdminAccessLevel> levels;
	private List<Language> langs;
	
	public List<String> getModules() {
		return modules;
	}

	public void initialize(String userName) {
		admin = administratorModel.getAdminLevels(userName);
		langs = languageModel.getLanguages();
		levels = getLevels();
		modules = getUserLevels();
	}
	
	private Set<String> getAllModules() {
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
		Set<String> allModules = getAllModules();
		List<AdminAccessLevel> levels = new ArrayList<AdminAccessLevel>();
		for (String module : allModules) {
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
	
	private List<String> getUserLevels() {
		List<String> modules = new ArrayList<String>();
		for (AdminAccessLevel access : levels) {
			modules.add(access.getGroup());
			List<BaseAccess> links = access.getModules();
			if(links != null && links.size() > 0) {
				for (BaseAccess link : links) {
					String module = link.getModule();
					modules.add(module);
					List<ModuleSubGroup> subGroup = link.getSubGroup();
					if(subGroup != null && subGroup.size() > 0) {
						modules.add(module);
					}
				}
			}
		}
		return modules;
	}

	@Override
	public String getModuleObjects() {
		String menu = "[]";
		ArrayList<String> modules = new ArrayList<String>();
		for (AdminAccessLevel access : levels) {
			modules.add("new Toc.desktop." + CommonUtils.ucFirst(access.getGroup()) + "GroupWindow()");
			List<BaseAccess> links = access.getModules();
			if(links != null && links.size() > 0) {
				for (BaseAccess link : links) {
					String module = CommonUtils.ucWord(link.getModule());
					List<ModuleSubGroup> subGroupList = link.getSubGroup();
					if(subGroupList != null && subGroupList.size() > 0) {
						modules.add("new Toc.desktop." + module + "SubGroupWindow()");
						for (ModuleSubGroup subGroup : subGroupList) {
							modules.add("new Toc.desktop." + module + 
								"Window({id: '" + subGroup.getIdentifier() + "', " +
								"title: '" + subGroup.getTitle() + "', " +
								"iconCls: '" + subGroup.getIconCls() + "', " + 
								"shortcutIconCls: '" + subGroup.getShortcutIconCls() + "', " +
								"params: '" + JsonUtils.convertToJsonString(subGroup.getParams(), "null") + "'})");
							
						}
					} else {
						modules.add("new Toc.desktop." + module + "Window()");
					}
				}
			}
		}
		modules.add("new Toc.desktop.LanguagesGroupWindow()");
		for (Language language : langs) {
			modules.add("new Toc.desktop." + CommonUtils.ucWord(language.getCode()) + "Window()");
		}
		if(modules.size() > 0) {
			menu = "[" + CommonUtils.listToString(modules, ",") + "]";
		}
		return menu;
	}

	@Override
	public String getOutputModule() {
		StringBuilder output = new StringBuilder();
		for (AdminAccessLevel access : levels) {
			String group = access.getGroup();
			StringBuilder groupClass = new StringBuilder();
			ArrayList<String> modules = new ArrayList<String>();
			List<BaseAccess> links = access.getModules();
			if(links != null && links.size() > 0) {
				for (BaseAccess link : links) {
					String module = link.getModule();
					List<ModuleSubGroup> subGroupList = link.getSubGroup();
					if(subGroupList != null && subGroupList.size() > 0) {
						modules.add("'" + module + "-subgroup'");
					} else {
						modules.add("'" + module + "-win'");
					}
				}
			}
			groupClass.append("Toc.desktop." + CommonUtils.ucFirst(group) + "GroupWindow = Ext.extend(Toc.desktop.Module, {\n");
			groupClass.append("appType : 'group',\n");
			groupClass.append("id : '" + group + "-grp',\n");
		}
		return output.toString();
	}

}
