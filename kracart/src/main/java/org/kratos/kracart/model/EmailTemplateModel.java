package org.kratos.kracart.model;

import java.util.Map;

import org.kratos.kracart.entity.EmailTemplateDescription;

public interface EmailTemplateModel {
	
	public EmailTemplateDescription getEmailTemplateByName(Map<String, String> criteria);

}
