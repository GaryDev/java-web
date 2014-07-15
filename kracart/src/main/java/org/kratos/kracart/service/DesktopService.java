package org.kratos.kracart.service;

import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import org.kratos.kracart.entity.Language;

public interface DesktopService {

	public void loadDesktopConstants(Map<String, Object> data);
	public void loadLanguages(Map<String, Object> data, List<Language> langs);
	public void loadLanguageDefinition(Map<String, Object> data, ResourceBundle desktopBundle);
	
}
