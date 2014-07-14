package org.kratos.kracart.service;

import java.util.Map;

import org.kratos.kracart.entity.EmailTemplateDescription;

public interface EmailService {
	
	public EmailTemplateDescription getEmailTemplateDetail(Map<String, String> params);

}
