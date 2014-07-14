package org.kratos.kracart.core.email;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.mail.internet.MimeMessage;

import org.kratos.kracart.core.config.ConfigConstant;
import org.kratos.kracart.entity.EmailTemplateDescription;
import org.kratos.kracart.service.ConfigurationService;
import org.kratos.kracart.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

@Component
public abstract class KRAEmailTemplate {
	
	protected String title;
	protected String content;
	protected String mailTo;
	protected String mailFrom;
	
	@Autowired
	private EmailService emailService;
	@Autowired
	protected ConfigurationService configurationService;
	@Autowired
	private JavaMailSender javaMailSender;
	
	public boolean sendEmail() {
		buildMessage();
		boolean result = false;
		try {
			MimeMessage mimeMessage = javaMailSender.createMimeMessage();
			MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, false, "UTF-8");
			messageHelper.setTo(mailTo);
			messageHelper.setFrom(mailFrom);
			messageHelper.setSubject(title);
			messageHelper.setText(content, true);
			javaMailSender.send(mimeMessage);
			result = true;
		} catch (Exception e) {
			e.printStackTrace();
			result = false;
		}
		return result;
	}
	
	public void createTemplate(String templateName, Locale locale) {
		Map<String, String> criteria = new HashMap<String, String>();
		criteria.put("name", templateName);
		criteria.put("code", locale.toString());
		EmailTemplateDescription tpl = emailService.getEmailTemplateDetail(criteria);
		if(tpl != null) {
			title = tpl.getTitle();
			content = tpl.getContent();
		}
		mailFrom = configurationService.getConfigurationValue(ConfigConstant.KEY_EMAIL_FROM);
	}
	
	public abstract void setData(Map<String, Object> data);
	protected abstract void buildMessage();

}
