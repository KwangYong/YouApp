package org.pky.uml.model;

import org.pky.uml.model.common.UMLModel;

public class ElementModel extends UMLModel{
	public static String ATTR = "ATTR";
	public static String OPER = "OPER";
	public static String PARA = "PARA";
	public static String NAME = "NAME";
	public static String STERE = "STERE";
	
	private String type = ""; 
	public ElementModel(){
		setSize(100, 10);
	}
	public ElementModel(String name){
		this();
		setName(name);
		this.setType(NAME);
	}
	public ElementModel(String name,String type){
		this();
		setName(name);
	}
	public String toString(){
		return super.toString();
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
}
