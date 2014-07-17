package org.kratos.kracart.core.bean;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="wallpapers")
public class DesktopWallPapers {
	
	private List<DesktopWallPaper> wallpaper = new ArrayList<DesktopWallPaper>();

	@XmlElement
	public List<DesktopWallPaper> getWallpaper() {
		return wallpaper;
	}

	public void setWallpaper(List<DesktopWallPaper> wallpaper) {
		this.wallpaper = wallpaper;
	}
}
