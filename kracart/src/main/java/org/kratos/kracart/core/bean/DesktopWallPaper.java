package org.kratos.kracart.core.bean;

public class DesktopWallPaper {

	private String name;
	private String code;
	private String path;
	private String thumbnail;
	
	public DesktopWallPaper() {
		
	}
	
	public DesktopWallPaper(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getThumbnail() {
		return thumbnail;
	}

	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}
	
}
