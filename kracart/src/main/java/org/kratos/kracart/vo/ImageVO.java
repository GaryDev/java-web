package org.kratos.kracart.vo;

public class ImageVO {

	private String module;
	private String run;
	
	public ImageVO() {
		
	}
	
	public ImageVO(String module, String run) {
		this.module = module;
		this.run = run;
	}

	public String getModule() {
		return module;
	}

	public void setModule(String module) {
		this.module = module;
	}

	public String getRun() {
		return run;
	}

	public void setRun(String run) {
		this.run = run;
	}

}
