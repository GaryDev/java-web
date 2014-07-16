package org.kratos.kracart.core.bean;

import org.springframework.util.StringUtils;

public class DesktopStyle {
	
	private String theme;
	private String transparency;
	private String backgroundcolor;
	private String fontcolor;
	private DesktopWallPaper deskWallPaper;
	private String wallpaper;
	private String wallpaperposition;
	
	public DesktopStyle() {
		
	}
	
	public String getTheme() {
		return theme;
	}
	public void setTheme(String theme) {
		this.theme = theme;
	}
	public String getTransparency() {
		return StringUtils.hasLength(transparency) ? transparency : "100";
	}
	public void setTransparency(String transparency) {
		this.transparency = transparency;
	}
	public String getBackgroundcolor() {
		return StringUtils.hasLength(backgroundcolor) ? backgroundcolor : "#3A6EA5";
	}
	public void setBackgroundcolor(String backgroundcolor) {
		this.backgroundcolor = backgroundcolor;
	}
	public String getFontcolor() {
		return StringUtils.hasLength(fontcolor) ? fontcolor : "FFFFFF";
	}
	public void setFontcolor(String fontcolor) {
		this.fontcolor = fontcolor;
	}
	public DesktopWallPaper getDeskWallPaper() {
		return deskWallPaper;
	}
	public void setDeskWallPaper(DesktopWallPaper deskWallPaper) {
		this.deskWallPaper = deskWallPaper;
		if(deskWallPaper != null) {
			wallpaper = deskWallPaper.getCode();
		}
	}
	public String getWallpaper() {
		if(deskWallPaper != null) {
			wallpaper = deskWallPaper.getCode();
		}
		return wallpaper;
	}
	public void setWallpaper(String wallpaper) {
		this.wallpaper = wallpaper;
		setDeskWallPaper(new DesktopWallPaper(wallpaper));
	}

	public String getWallpaperposition() {
		return StringUtils.hasLength(wallpaperposition) ? wallpaperposition : "tile";
	}
	public void setWallpaperposition(String wallpaperposition) {
		this.wallpaperposition = wallpaperposition;
	}

}
