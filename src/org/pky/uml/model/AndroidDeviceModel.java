package org.pky.uml.model;

public class AndroidDeviceModel {
	
	private String avdName = "";
	private String sdkVer = "";
	
	public AndroidDeviceModel(){
		
	}
	public AndroidDeviceModel(String avdName, String sdkVer){
		this.avdName = avdName;
		this.sdkVer = sdkVer;
	}

	public String getAvdName() {
		return avdName;
	}

	public String getSdkVer() {
		return sdkVer;
	}

	public void setAvdName(String avdName) {
		this.avdName = avdName;
	}

	public void setSdkVer(String sdkVer) {
		this.sdkVer = sdkVer;
	}
	
	

}
