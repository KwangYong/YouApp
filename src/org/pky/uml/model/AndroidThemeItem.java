package org.pky.uml.model;

public class AndroidThemeItem {
	
	public String id = "";	
	public String name = "";
	
	public AndroidThemeItem(String id, String name){
		this.id = id;
		this.name = name;
		
	}
	
	
	@Override
	public String toString() {
		return name;
		
	}

	

}
