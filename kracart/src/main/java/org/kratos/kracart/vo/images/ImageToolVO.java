package org.kratos.kracart.vo.images;

public class ImageToolVO {

	private String module;
	private String run;
	
	public ImageToolVO() {
		
	}
	
	public ImageToolVO(String module, String run) {
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
