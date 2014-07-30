package org.kratos.kracart.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.kratos.kracart.core.email.KRAEmailTemplate;
import org.kratos.kracart.core.email.tpl.TplAdminPasswordForgotten;
import org.kratos.kracart.entity.Administrator;
import org.kratos.kracart.entity.AdministratorAccess;
import org.kratos.kracart.model.AdministratorModel;
import org.kratos.kracart.service.AdminService;
import org.kratos.kracart.utility.CommonUtils;
import org.kratos.kracart.utility.JsonUtils;
import org.kratos.kracart.utility.ValidatorUtils;
import org.kratos.kracart.vo.AdministratorVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service("adminService")
@Transactional
public class AdminServiceImpl implements AdminService {
	
	@Autowired
	private AdministratorModel administratorModel;
	@Autowired
	private KRAEmailTemplate template;

	@Override
	public boolean IsLogon() {
		return true;
	}

	@Override
	public boolean validateEmail(String email) {
		Administrator admin = getAdministratorByEmail(email);
		if(admin == null) {
			return false;
		}
		return true;
	}
	
	private Administrator getAdministratorByEmail(String email) {
		Administrator criteria = new Administrator();
		criteria.setEmail(email);
		Administrator admin = administratorModel.getAdministrator(criteria);
		return admin;
	}
	
	private Administrator getAdministratorByName(String name) {
		Administrator criteria = new Administrator();
		criteria.setName(name);
		Administrator admin = administratorModel.getAdministrator(criteria);
		return admin;
	}

	@Override
	public boolean resetPassword(String email, String ip, Locale locale) {
		String password = CommonUtils.createRandomString(8);
		String passwordEncrypt = CommonUtils.encryptString(password);
		updatePassword(email, passwordEncrypt);
		Map<String, Object> info = new HashMap<String, Object>();
		info.put("ip", ip);
		info.put("password", password);
		info.put("mail", email);
		template.createTemplate(TplAdminPasswordForgotten.TPL_NAME, locale);
		template.setData(info);
		return template.sendEmail();
	}
	
	private void updatePassword(String email, String newPassword) {
		Administrator admin = new Administrator();
		admin.setPassword(newPassword);
		admin.setEmail(email);
		administratorModel.updatePassword(admin);
	}

	@Override
	public List<Administrator> getAdministrators(String start, String limit) {
		Map<String, Object> criteria = new HashMap<String, Object>();
		criteria.put("start", start == null ? null : Integer.parseInt(start));
		criteria.put("limit", limit == null ? null : Integer.parseInt(limit));
		return administratorModel.getAdministrators(criteria);
	}

	@Override
	public int getTotal() {
		return getAdministrators(null, null).size();
	}

	@Override
	public Administrator getAdministratorById(int id) {
		Administrator criteria = new Administrator();
		criteria.setId(id);
		return administratorModel.getAdministrator(criteria);
	}

	@Override
	public int saveAdministrator(AdministratorVO voAdmin) {
		int result = validateAdministratorVO(voAdmin);
		if(result == 1) {
			result = saveAdminstratorInfo(voAdmin);
			if(result == 1) {
				List<String> modules = null;
				if("*".equals(voAdmin.getModules())) {
					modules = new ArrayList<String>();
					modules.add("*");
				} else {
					modules = JsonUtils.convertJsonStringToList(voAdmin.getModules());
				}
				if(modules != null && modules.size() > 0) {
					result = saveAdminstratorAccess(voAdmin.getaID(), modules);
				}
			}
		}
		return result;
	}
	
	private int validateAdministratorVO(AdministratorVO voAdmin) {
		int result = 1;
		if(ValidatorUtils.validateEmail(voAdmin.getEmail())) {
			Administrator admin = getAdministratorByEmail(voAdmin.getEmail());
			if(admin != null && !String.valueOf(admin.getId()).equals(voAdmin.getaID())) {
				return -4;
			} 
		} else {
			return -3;
		}
		Administrator admin = getAdministratorByName(voAdmin.getName());
		if(admin != null && !String.valueOf(admin.getId()).equals(voAdmin.getaID())) {
			return -2;
		} 
		return result;
	}
	
	private int saveAdminstratorInfo(AdministratorVO voAdmin) {
		Administrator data = new Administrator();
		data.setEmail(voAdmin.getEmail());
		data.setName(voAdmin.getName());
		String id = voAdmin.getaID();
		if(StringUtils.hasLength(id)) {
			data.setId(Integer.parseInt(id));
			String password = voAdmin.getPassword();
			if(StringUtils.hasLength(password)) {
				data.setPassword(CommonUtils.encryptString(password));
			}
			administratorModel.updateAdministrator(data);
		} else {
			data.setPassword(CommonUtils.encryptString(voAdmin.getPassword()));
			administratorModel.insertAdministrator(data);
		}
		voAdmin.setaID(String.valueOf(data.getId()));
		return 1;
	}
	
	private int saveAdminstratorAccess(String id, List<String> modules) {
		for (String module : modules) {
			AdministratorAccess data = new AdministratorAccess();
			data.setId(Integer.parseInt(id));
			data.setModule(module);
			if(administratorModel.getAdminAccessModule(data) == null) {
				administratorModel.insertAdministratorAccess(data);
			}
		}
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("id", id);
		param.put("modules", modules);
		administratorModel.deleteAdministratorAccess(param);
		return 1;
	}

	@Override
	public int deleteAdministrator(Object[] idArray) {
		if(idArray != null && idArray.length > 0) {
			for (Object element : idArray) {
				int id = Integer.parseInt(String.valueOf(element));
				administratorModel.deleteAdministratorAccessById(id);
				administratorModel.deleteAdministrator(id);
			}
			return 1;
		}
		return -1;
	}

}
