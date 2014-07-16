package org.kratos.kracart.core.bean;

public class DesktopSetting {
	
	private DesktopStyle style;
	private DesktopLauncher launcher;
	private Boolean wizard_complete;
	private String dashboards;
	private Integer livefeed;
	
	public DesktopSetting() {
		
	}

	public DesktopStyle getStyle() {
		return style;
	}

	public void setStyle(DesktopStyle style) {
		this.style = style;
	}
	
	public DesktopLauncher getLauncher() {
		return launcher;
	}

	public void setLauncher(DesktopLauncher launcher) {
		this.launcher = launcher;
	}

	public Boolean getWizard_complete() {
		return wizard_complete;
	}

	public void setWizard_complete(Boolean wizard_complete) {
		this.wizard_complete = wizard_complete;
	}

	public String getDashboards() {
		return dashboards;
	}

	public void setDashboards(String dashboards) {
		this.dashboards = dashboards;
	}

	public Integer getLivefeed() {
		return livefeed;
	}

	public void setLivefeed(Integer livefeed) {
		this.livefeed = livefeed;
	}

}
