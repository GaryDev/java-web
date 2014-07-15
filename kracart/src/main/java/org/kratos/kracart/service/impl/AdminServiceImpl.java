package org.kratos.kracart.service.impl;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.kratos.kracart.core.email.KRAEmailTemplate;
import org.kratos.kracart.core.email.tpl.TplAdminPasswordForgotten;
import org.kratos.kracart.entity.Administrator;
import org.kratos.kracart.model.AdministratorModel;
import org.kratos.kracart.model.EmailTemplateModel;
import org.kratos.kracart.service.AdminService;
import org.kratos.kracart.utility.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("adminService")
@Transactional
public class AdminServiceImpl implements AdminService {
	
	@Autowired
	private AdministratorModel administratorModel;
	
	@Autowired
	private EmailTemplateModel emailTemplateModel;
	
	@Autowired
	private KRAEmailTemplate template;

	@Override
	public boolean IsLogon() {
		return true;
	}

	@Override
	public boolean validateEmail(String email) {
		Administrator admin = administratorModel.getAdministratorByMail(email);
		if(admin == null) {
			return false;
		}
		return true;
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

}
