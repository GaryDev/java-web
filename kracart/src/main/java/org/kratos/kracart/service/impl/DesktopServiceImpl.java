package org.kratos.kracart.service.impl;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import org.kratos.kracart.core.config.DesktopConstant;
import org.kratos.kracart.entity.Language;
import org.kratos.kracart.model.AdministratorModel;
import org.kratos.kracart.service.DesktopService;
import org.kratos.kracart.utility.JsonUtils;
import org.kratos.kracart.utility.PHPSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service("desktopService")
public class DesktopServiceImpl implements DesktopService {

	@Autowired
	private AdministratorModel administratorModel;
	
	private String userName;
	public String getUserName() {
		return userName;
	}
	
	private Map<String, String> settings;

	public void loadDesktopConstants(Map<String, Object> data) {
		data.put("steps", String.valueOf(DesktopConstant.EXT_GRID_STEPS));
		data.put("jsonReaderRoot", DesktopConstant.EXT_JSON_READER_ROOT);
		data.put("jsonReaderTotal", DesktopConstant.EXT_JSON_READER_TOTAL);
	}
	
	public void loadLanguages(Map<String, Object> data, List<Language> langs) {
		List<String> jsonLang = new ArrayList<String>();
		for (Language lang : langs) {
			JsonUtils.convertToJsonString(lang);
			jsonLang.add(JsonUtils.convertToJsonString(lang));
		}
		data.put("jsonLang", jsonLang);
	}
	
	public void loadLanguageDefinition(Map<String, Object> data, ResourceBundle desktopBundle) {
		Map<String, String> prop = new HashMap<String, String>();
		if(desktopBundle != null) {
			Enumeration<String> keys = desktopBundle.getKeys();
			while(keys.hasMoreElements()) {
				String key = keys.nextElement();
				prop.put(key, desktopBundle.getString(key));
			}
		}
		data.put("jsonLangDef", JsonUtils.convertToJsonString(prop));
	}
	
	@SuppressWarnings({"unchecked", "rawtypes"})
	public void initialize(String userName) {
		this.userName = userName;
		String userSetting = administratorModel.getAdminSettingByName(userName);
		try {
			Map settings = (Map) PHPSerializer.unserialize(userSetting.getBytes(), Map.class);
			if(settings == null || settings.size() == 0 || !settings.containsKey("desktop")) {
				this.settings = getDefaultSettings();
				// TODO: Save Setting
			} else {
				this.settings = (Map<String, String>) settings.get("desktop");
			}
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}
	
	private Map<String, String> getDefaultSettings() {
		Map<String, String> defaultSetting = new HashMap<String, String>();
		defaultSetting.put("theme", "vistablue");
		defaultSetting.put("transparency", "100");
		defaultSetting.put("backgroundcolor", "3A6EA5");
		defaultSetting.put("fontcolor", "FFFFFF");
		defaultSetting.put("wallpaper", "blank");
		defaultSetting.put("wallpaperposition", "tile");

		defaultSetting.put("autorun", "[\"dashboard-win\"]");
		defaultSetting.put("contextmenu", "[]");
		defaultSetting.put("quickstart", "[\"articles_categories-win\",\"articles-win\",\"faqs-win\",\"slide_images-win\",\"products-win\",\"customers-win\",\"orders-win\", \"invoices-win\", \"coupons-win\",\"gift_certificates-win\",\"dashboard-win\"]");
		defaultSetting.put("shortcut", "[\"articles_categories-win\",\"articles-win\",\"faqs-win\",\"slide_images-win\",\"products-win\",\"customers-win\",\"orders-win\", \"invoices-win\", \"coupons-win\",\"gift_certificates-win\",\"dashboard-win\"]");
		defaultSetting.put("wizard_complete", "FALSE");

		defaultSetting.put("dashboards", "overview:0,new_orders:1,new_customers:2,new_reviews:0,orders_statistics:1,last_visits:2");

		defaultSetting.put("livefeed", "0");
		return defaultSetting;
	}

	@Override
	public String getLaunchers() {
		String defaultValue = "[]";
		Map<String, String> launcher = new HashMap<String, String>();
		launcher.put("autorun", settings.containsKey("autorun")
						&& StringUtils.hasLength(settings.get("autorun")) ? settings.get("autorun") : defaultValue);
		launcher.put("contextmenu", settings.containsKey("contextmenu")
				&& StringUtils.hasLength(settings.get("contextmenu")) ? settings.get("contextmenu") : defaultValue);
		launcher.put("quickstart", settings.containsKey("quickstart")
				&& StringUtils.hasLength(settings.get("quickstart")) ? settings.get("quickstart") : defaultValue);
		launcher.put("shortcut", settings.containsKey("shortcut")
				&& StringUtils.hasLength(settings.get("shortcut")) ? settings.get("shortcut") : defaultValue);
		return JsonUtils.convertToJsonString(launcher);
	}

	@Override
	public String getStyles() {
		Map<String, Object> styles = new HashMap<String, Object>();
		styles.put("backgroundcolor", settings.containsKey("backgroundcolor")
				&& StringUtils.hasLength(settings.get("backgroundcolor")) ? settings.get("backgroundcolor") : "#3A6EA5");
		styles.put("fontcolor", settings.containsKey("fontcolor")
				&& StringUtils.hasLength(settings.get("fontcolor")) ? settings.get("fontcolor") : "FFFFFF");
		styles.put("transparency", settings.containsKey("transparency")
				&& StringUtils.hasLength(settings.get("transparency")) ? settings.get("transparency") : "100");
		styles.put("wallpaperposition", settings.containsKey("wallpaperposition")
				&& StringUtils.hasLength(settings.get("wallpaperposition")) ? settings.get("wallpaperposition") : "tile");
		styles.put("theme", "");
		styles.put("wallpaper", "");	// TODO: Get WallPapers
		return JsonUtils.convertToJsonString(styles);
	}

}
