package org.kratos.kracart.service.impl;

import java.util.Map;

import org.kratos.kracart.entity.EmailTemplateDescription;
import org.kratos.kracart.model.EmailTemplateModel;
import org.kratos.kracart.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("emailService")
public class EmailServiceImpl implements EmailService {
	
	@Autowired
	private EmailTemplateModel emailTemplateModel;

	@Override
	public EmailTemplateDescription getEmailTemplateDetail(
			Map<String, String> params) {
		EmailTemplateDescription tpl = emailTemplateModel.getEmailTemplateByName(params);
		return tpl;
	}

}
