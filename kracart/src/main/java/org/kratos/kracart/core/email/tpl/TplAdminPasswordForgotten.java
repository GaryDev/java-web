package org.kratos.kracart.core.email.tpl;

import java.util.HashMap;
import java.util.Map;

import org.kratos.kracart.core.config.ConfigConstant;
import org.kratos.kracart.core.email.KRAEmailTemplate;
import org.springframework.stereotype.Component;

@Component
public class TplAdminPasswordForgotten extends KRAEmailTemplate {
	
	public static final String TPL_NAME = "admin_password_forgotten";
	private static final String[] KEYWORDS = new String[] { 
				"%%admin_ip_address%%", 
				"%%admin_password%%", 
				"%%store_name%%", 
				"%%store_owner_email_address%%"
	};
	
	private Map<String, String> replaces;
	
	public void setData(Map<String, Object> data) {
		replaces = new HashMap<String, String>();
		replaces.put(KEYWORDS[0], data.get("ip").toString());
		replaces.put(KEYWORDS[1], data.get("password").toString());
		replaces.put(KEYWORDS[2], configurationService.getConfigurationValue(ConfigConstant.KEY_STORE_NAME));
		replaces.put(KEYWORDS[3], configurationService.getConfigurationValue(ConfigConstant.KEY_STORE_OWNER_EMAIL_ADDRESS));
		mailTo = data.get("mail").toString();
	}
	
	protected void buildMessage() {
		for (String keyword : KEYWORDS) {
			if(replaces.containsKey(keyword)) {
				this.title = this.title.replaceAll(keyword, replaces.get(keyword));
				this.content = this.content.replaceAll(keyword, replaces.get(keyword));
			}
		}
	}
	
}
