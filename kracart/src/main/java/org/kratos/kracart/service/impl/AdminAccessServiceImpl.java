package org.kratos.kracart.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import org.kratos.kracart.core.bean.AdminAccessLevel;
import org.kratos.kracart.core.bean.AdminModuleNode;
import org.kratos.kracart.core.bean.AdminModuleParent;
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
import org.springframework.util.StringUtils;

@Service("adminAccessService")
public class AdminAccessServiceImpl implements AdminAccessService {
	
	private static final String MODULE_DIR = "org.kratos.kracart.core.modules.access";
	
	@Autowired
	private AdministratorModel administratorModel;
	@Autowired
	private LanguageModel languageModel;
	
	private Administrator admin;
	private List<AdminAccessLevel> levels;
	private List<Language> langs;
	private ResourceBundle bundle;
	private String userName;
	private String contextPath;
	
	@Override
	public void setResouceBundle(ResourceBundle bundle) {
		this.bundle = bundle;
	}

	@Override
	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Override
	public void setContextPath(String path) {
		this.contextPath = path;
	}

	public void initialize() {
		if(StringUtils.hasLength(userName)) {
			admin = administratorModel.getAdminLevels(userName);
		}
		langs = languageModel.getLanguages();
		levels = getLevels(getAdminModules());
	}
	
	private Set<String> getAdminModules() {
		Set<String> modules = new HashSet<String>();
		List<AdministratorAccess> levels = admin.getAccess();
		for (AdministratorAccess access : levels) {
			modules.add(access.getModule());
		}
		return modules;
	}
	
	private Set<String> getAllModules(Set<String> adminModules) {
		if(adminModules == null || adminModules.size() == 0 || adminModules.contains("*")) {
			Set<String> modules = new HashSet<String>();
			modules.addAll(CommonUtils.getClassName(MODULE_DIR, getClass().getClassLoader().getResource("")));
			return modules;
		}
		return adminModules;
	}
	
	private List<AdminAccessLevel> getLevels(Set<String> adminModules) {
		Set<String> allModules = getAllModules(adminModules);
		List<AdminAccessLevel> levels = new ArrayList<AdminAccessLevel>();
		for (String module : allModules) {
			try {
				if(module.indexOf(MODULE_DIR) == -1) {
					module = MODULE_DIR + "." + CommonUtils.ucFirst(module);
				}
				BaseAccess access = (BaseAccess) Class.forName(module).newInstance();
				if(!access.getEnabled()) {
					continue;
				}
				access.setTitle(bundle.getString("access_" + access.getModule() + "_title"));
				String group = access.getGroup();
				AdminAccessLevel item = new AdminAccessLevel();
				if(levels.size() > 0) {
					int index = levels.indexOf(new AdminAccessLevel(group));
					if(index != -1) {
						item = levels.get(index);
					}
				}
				List<BaseAccess> modules = new ArrayList<BaseAccess>();
				if(group.equals(item.getGroup())) {
					if(item.getModules() == null || item.getModules().size() == 0) {
						item.setModules(modules);
					}
					item.getModules().add(access);
				} else {
					modules.add(access);
					item.setModules(modules);
					item.setGroup(group);
					levels.add(item);
				}
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
		if(levels.size() > 0) {
			Collections.sort(levels, AdminAccessLevel.getAdminAccessLevel());
			for (AdminAccessLevel level : levels) {
				Collections.sort(level.getModules(), BaseAccess.getBaseAccessComparator());
			}
		}
		return levels;
	}
	
	public List<AdminModuleParent> getAdminModules(String name) {
		List<AdminModuleParent> parents = new ArrayList<AdminModuleParent>();
		this.userName = name;
		initialize();
		Set<String> adminModules = getAdminModules();
		if(adminModules.contains("*")) {
			parents = getModules(true);
		} else {
			parents = getModules(false, adminModules);
		}
		return parents;
	}
	
	public List<AdminModuleParent> getModules(boolean isGlobal) {
		return getModules(isGlobal, new HashSet<String>());
	}
	
	private List<AdminModuleParent> getModules(boolean isGlobal, Set<String> modules) {
		List<AdminAccessLevel> allLevels = getLevels(modules);
		Map<String, List<AdminModuleNode>> accessModules = new HashMap<String, List<AdminModuleNode>>();
		for (AdminAccessLevel level : allLevels) {
			List<BaseAccess> moduleList = level.getModules();
			if(moduleList != null && moduleList.size() > 0) {
				for (BaseAccess module : moduleList) {
					String title = bundle.getString("access_group_" + module.getGroup() + "_title");
					AdminModuleNode node = new AdminModuleNode();
					node.setId(module.getModule());
					node.setText(module.getTitle());
					node.setLeaf(true);
					node.setChecked(isGlobal || modules.contains(module.getModule()));
					if(accessModules.containsKey(title)) {
						accessModules.get(title).add(node);
					} else {
						List<AdminModuleNode> nodeList = new ArrayList<AdminModuleNode>();
						nodeList.add(node);
						accessModules.put(title, nodeList);
					}
				}
			}
		}
		List<AdminModuleParent> parents = new ArrayList<AdminModuleParent>();
		int count = 1;
		for (String	group : accessModules.keySet()) {
			AdminModuleParent parent = new AdminModuleParent();
			parent.setId(count++);
			parent.setText(group);
			parent.setChildren(accessModules.get(group));
			parents.add(parent);
		}
		return parents;
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
							subGroup.setTitle(bundle.getString("access_" + subGroup.getId() + "_title"));
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
			groupClass.append("appType: 'group',\n");
			groupClass.append("id: '" + group + "-grp',\n");
			groupClass.append("title : '" + bundle.getString("access_group_" + group + "_title") + "',\n");
			groupClass.append("menu : new Ext.menu.Menu(),\n");
			groupClass.append("items : [" + CommonUtils.listToString(modules, ",") + "],\n");
			groupClass.append("init : function(){\n");
            groupClass.append("this.launcher = {\n");
            groupClass.append("text: this.title,\n");
            groupClass.append("iconCls: 'icon-" + group + "-grp',\n");
            groupClass.append("menu: this.menu"+"\n");
            groupClass.append("}}});\n\n");
            
            output.append(groupClass);
            
            if(links != null && links.size() > 0) {
				for (BaseAccess link : links) {
					String module = CommonUtils.ucWord(link.getModule());
					List<ModuleSubGroup> subGroupList = link.getSubGroup();
					if(subGroupList != null && subGroupList.size() > 0) {
						modules.clear();
						for (ModuleSubGroup subGroup : subGroupList) {
							modules.add("'" + subGroup.getIdentifier() + "'");
						}
						groupClass.delete(0, groupClass.length());
						groupClass.append("Toc.desktop." + module + "SubGroupWindow = Ext.extend(Toc.desktop.Module, {\n");
						groupClass.append("appType : 'subgroup',\n");
						groupClass.append("id : '" + link.getModule() + "-subgroup',\n");
						groupClass.append("title : '" + link.getTitle() + "',\n");
						groupClass.append("menu : new Ext.menu.Menu(),\n");
						groupClass.append("items : [" + CommonUtils.listToString(modules, ",") + "],\n");
						groupClass.append("init : function(){\n");
			            groupClass.append("this.launcher = {\n");
			            groupClass.append("text: this.title,\n");
			            groupClass.append("iconCls: 'icon-" + link.getModule() + "-subgroup',\n");
	                    groupClass.append("menu: this.menu"+"\n");
	                    groupClass.append("}}});\n\n");
						
						output.append(groupClass);
						
						groupClass.delete(0, groupClass.length());
						groupClass.append("Toc.desktop." + module + "Window = Ext.extend(Toc.desktop.Module, {\n");
						groupClass.append("appType : 'win',\n");
						groupClass.append("id : '" + link.getModule() + "-win',\n");
						groupClass.append("title : '" + link.getTitle() + "',\n");
						groupClass.append("init : function(){\n");
			            groupClass.append("this.launcher = {\n");
			            groupClass.append("text: this.title,\n");
			            groupClass.append("iconCls: this.iconCls,\n");
			            groupClass.append("shortcutIconCls: this.shortcutIconCls,\n");
			            groupClass.append("scope: this\n");
	                    groupClass.append("}}});\n\n");
						
						output.append(groupClass);
					} else {
						groupClass.delete(0, groupClass.length());
						groupClass.append("Toc.desktop." + module + "Window = Ext.extend(Toc.desktop.Module, {\n");
						groupClass.append("appType : 'win',\n");
						groupClass.append("id : '" + link.getModule() + "-win',\n");
						groupClass.append("title : '" + link.getTitle() + "',\n");
						groupClass.append("init : function(){\n");
			            groupClass.append("this.launcher = {\n");
			            groupClass.append("text: this.title,\n");
			            groupClass.append("iconCls: 'icon-" + link.getModule() + "-win',\n");
			            groupClass.append("shortcutIconCls: 'icon-" + link.getModule() + "-shortcut',\n");
			            groupClass.append("scope: this\n");
	                    groupClass.append("}}});\n\n");
	                    
	                    output.append(groupClass);
					}
				}
            }
		}
		output.append(getLanguageModule());
		return output.toString();
	}
	
	private String getLanguageModule() {
		StringBuilder output = new StringBuilder();
		List<String> languages = new ArrayList<String>();
		for (Language l : langs) {
			languages.add("'lang-" + l.getCode().toLowerCase() + "-win'");
		}
		
		output.append("Toc.desktop.LanguagesGroupWindow = Ext.extend(Toc.desktop.Module, {\n");
		output.append("appType : 'group',\n");
		output.append("id : 'languages-grp',\n");
		output.append("title : '" + bundle.getString("access_group_languages_title") + "',\n");
		output.append("menu : new Ext.menu.Menu(),\n");
		output.append("items : [" + CommonUtils.listToString(languages, ",") + "],\n");
		output.append("init : function(){\n");
		output.append("this.launcher = {\n");
        output.append("text: '" + bundle.getString("header_title_languages") + "',\n");
        output.append("iconCls: 'icon-languages-grp',\n");
        output.append("menu: this.menu"+"\n");
        output.append("}}});\n\n");
		
        String url = contextPath + "/admin/index";
        for (Language l : langs) {
        	String code = CommonUtils.ucWord(l.getCode());
        	output.append("Toc.desktop." + code + "Window = Ext.extend(Toc.desktop.Module, {\n");
        	output.append("appType : 'win',\n");
        	output.append("id : 'lang-" + l.getCode().toLowerCase() + "-win',\n");
        	output.append("title: '" + l.getName() + "',\n");
        	output.append("init : function(){\n");
    		output.append("this.launcher = {\n");
    		output.append("text: '" + l.getName() + "',\n");
    		output.append("iconCls: 'icon-" + l.getCountryISO() + "-win',\n");
    		output.append("shortcutIconCls: 'icon-" + l.getCode() + "-shortcut',\n");
    		output.append("handler: function(){window.location = '" + url + "?lang=" + l.getCode() + "';},\n");
            output.append("scope: this\n");
            output.append("}}});\n\n");
        }
		
		return output.toString();
	}

}
