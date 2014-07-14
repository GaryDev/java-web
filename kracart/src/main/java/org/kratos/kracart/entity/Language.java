package org.kratos.kracart.entity;

import java.io.Serializable;

public class Language implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private int id;
	private String code;
	private String countryISO;
	private String name;
	private String locale;
	private String charset;
	private String dateFormatShort;
	private String dateFormatLong;
	private String timeFormat;
	private String textDirection;
	private String numericSepratorDecimal;
	private String numericSepratorThousands;
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getCountryISO() {
		return countryISO;
	}
	public void setCountryISO(String countryISO) {
		this.countryISO = countryISO;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLocale() {
		return locale;
	}
	public void setLocale(String locale) {
		this.locale = locale;
	}
	public String getCharset() {
		return charset;
	}
	public void setCharset(String charset) {
		this.charset = charset;
	}
	public String getDateFormatShort() {
		return dateFormatShort;
	}
	public void setDateFormatShort(String dateFormatShort) {
		this.dateFormatShort = dateFormatShort;
	}
	public String getDateFormatLong() {
		return dateFormatLong;
	}
	public void setDateFormatLong(String dateFormatLong) {
		this.dateFormatLong = dateFormatLong;
	}
	public String getTimeFormat() {
		return timeFormat;
	}
	public void setTimeFormat(String timeFormat) {
		this.timeFormat = timeFormat;
	}
	public String getTextDirection() {
		return textDirection;
	}
	public void setTextDirection(String textDirection) {
		this.textDirection = textDirection;
	}
	public String getNumericSepratorDecimal() {
		return numericSepratorDecimal;
	}
	public void setNumericSepratorDecimal(String numericSepratorDecimal) {
		this.numericSepratorDecimal = numericSepratorDecimal;
	}
	public String getNumericSepratorThousands() {
		return numericSepratorThousands;
	}
	public void setNumericSepratorThousands(String numericSepratorThousands) {
		this.numericSepratorThousands = numericSepratorThousands;
	}
	
}
