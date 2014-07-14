package org.kratos.kracart.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.kratos.kracart.entity.Language;
import org.kratos.kracart.model.LanguageModel;
import org.kratos.kracart.service.LanguageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.druid.util.StringUtils;

@Service("languageService")
public class LanguageServiceImpl implements LanguageService {
	
	@Autowired
	private LanguageModel languageModel;
	
	private Map<String, Language> languages;
	private String code;
	
	public Map<String, Language> getLanguages() {
		return languages;
	}
	
	public String getCode() {
		return code;
	}
	
	public void setCode(String code) {
		this.code = code;
	}
	
	public LanguageServiceImpl() {
		
	}

	@Override
	public void initialize() {
		if(languages == null) {
			languages = new HashMap<String, Language>();
			List<Language> langs = languageModel.getLanguages();
			for (Language language : langs) {
				language.setCountryISO(language.getCode().substring(3));
				languages.put(language.getCode(), language);
			}
		}
	}
	
	public Map<String, Language> getAll() {
		return languages;
	}
	
	public Integer getId() {
		if(!StringUtils.isEmpty(code)) {
			return languages.get(code).getId();
		}
		return null;
	}
	
	public String getName() {
		if(!StringUtils.isEmpty(code)) {
			return languages.get(code).getName();
		}
		return null;
	}
	
	public String getLocale() {
		if(!StringUtils.isEmpty(code)) {
			return languages.get(code).getLocale();
		}
		return null;
	}

	@Override
	public Language getLanguage(String code) {
		if(!StringUtils.isEmpty(code)) {
			this.setCode(code);
			return languages.get(code);
		}
		return null;
	}

	@Override
	public List<Language> getAllLanguage() {
		List<Language> langs = new ArrayList<Language>();
		for (Language language : languages.values()) {
			langs.add(language);
		}
		return langs;
	}

}
