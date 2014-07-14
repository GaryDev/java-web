package org.kratos.kracart.service;

import java.util.List;

import org.kratos.kracart.entity.Language;

public interface LanguageService {
	
	public void initialize();
	public Language getLanguage(String code);
	public List<Language> getAllLanguage();

}
