package org.kratos.kracart.service.impl;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import org.kratos.kracart.core.bean.DesktopLauncher;
import org.kratos.kracart.core.bean.DesktopSetting;
import org.kratos.kracart.core.bean.DesktopStyle;
import org.kratos.kracart.core.bean.DesktopWallPaper;
import org.kratos.kracart.core.config.DesktopConstant;
import org.kratos.kracart.entity.Language;
import org.kratos.kracart.model.AdministratorModel;
import org.kratos.kracart.service.DesktopService;
import org.kratos.kracart.utility.CommonUtils;
import org.kratos.kracart.utility.JsonUtils;
import org.kratos.kracart.utility.PHPSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("desktopService")
public class DesktopServiceImpl implements DesktopService {

	@Autowired
	private AdministratorModel administratorModel;
	
	private String userName;
	public String getUserName() {
		return userName;
	}
	
	private DesktopSetting settings;

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
	
	@SuppressWarnings({"rawtypes"})
	public void initialize(String userName) {
		this.userName = userName;
		String userSetting = administratorModel.getAdminSettingByName(userName);
		try {
			Map settings = (Map) PHPSerializer.unserialize(userSetting.getBytes(), Map.class);
			if(settings == null || settings.size() == 0 || !settings.containsKey("desktop")) {
				this.settings = getDefaultSettings();
				saveDesktopSetting();
			} else {
				this.settings = getDesktopSetting(settings.get("desktop"));
			}
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}
	
	private DesktopSetting getDefaultSettings() {
		DesktopSetting defaultSetting = new DesktopSetting();
		
		DesktopStyle style = new DesktopStyle();
		style.setTheme("vistablue");
		style.setTransparency("100");
		style.setBackgroundcolor("3A6EA5");
		style.setFontcolor("FFFFFF");
		style.setDeskWallPaper(new DesktopWallPaper("blank"));
		style.setWallpaperposition("tile");
		defaultSetting.setStyle(style);

		DesktopLauncher launcher = new DesktopLauncher();
		launcher.setAutorun("[\"dashboard-win\"]");
		launcher.setContextmenu("[]");
		launcher.setQuickstart("[\"articles_categories-win\",\"articles-win\",\"faqs-win\",\"slide_images-win\",\"products-win\",\"customers-win\",\"orders-win\", \"invoices-win\", \"coupons-win\",\"gift_certificates-win\",\"dashboard-win\"]");
		launcher.setShortcut("[\"articles_categories-win\",\"articles-win\",\"faqs-win\",\"slide_images-win\",\"products-win\",\"customers-win\",\"orders-win\", \"invoices-win\", \"coupons-win\",\"gift_certificates-win\",\"dashboard-win\"]");
		defaultSetting.setLauncher(launcher);
		
		defaultSetting.setWizard_complete(false);
		defaultSetting.setDashboards("overview:0,new_orders:1,new_customers:2,new_reviews:0,orders_statistics:1,last_visits:2");
		defaultSetting.setLivefeed(0);
		
		return defaultSetting;
	}
	
	private void saveDesktopSetting() {
		Map<String, Object> setting = new HashMap<String, Object>();
		setting = CommonUtils.beanToMap(setting, settings);
		Map<String, Object> desktop = new HashMap<String, Object>();
		desktop.put("desktop", setting);
		String s = new String(PHPSerializer.serialize(desktop));
		Map<String, String> param = new HashMap<String, String>();
		param.put("name", userName);
		param.put("setting", s);
		administratorModel.saveAdminSetting(param);
	}
	
	@SuppressWarnings("unchecked")
	private DesktopSetting getDesktopSetting(Object desktop) {
		Map<String, Object> map = (Map<String, Object>) desktop;
		DesktopSetting setting = new DesktopSetting();
		if(map != null) {
			DesktopStyle style = new DesktopStyle();
			CommonUtils.mapToBean(map, style);
			setting.setStyle(style);
			
			DesktopLauncher launcher = new DesktopLauncher();
			CommonUtils.mapToBean(map, launcher);
			setting.setLauncher(launcher);
			
			CommonUtils.mapToBean(map, setting);
		}
		return setting;
	}

	@Override
	public String getLaunchers() {
		DesktopLauncher launcher = settings.getLauncher();
		if(launcher == null) {
			launcher = new DesktopLauncher();
		}
		return JsonUtils.convertToJsonString(launcher);
	}

	@Override
	public String getStyles() {
		DesktopStyle styles = settings.getStyle();
		if(styles == null) {
			styles = new DesktopStyle();
		}
		styles.setDeskWallPaper(getWallPaper());
		return JsonUtils.convertToJsonString(styles);
	}
	
	private DesktopWallPaper getWallPaper() {
		DesktopWallPaper wallpaper = new DesktopWallPaper();
		
		return wallpaper;
	}

}
