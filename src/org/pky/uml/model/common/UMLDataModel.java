package org.pky.uml.model.common;

import java.util.Date;
import java.util.HashMap;

import org.pky.uml.commons.managers.ProjectManager;

public class UMLDataModel {

	private String id;
	private HashMap<String,Object> dataMap = null;
	
	public UMLDataModel(Object obj){
		
		
		
		id = ProjectManager.getInstance().getNewID(obj);
		
		
		
		dataMap = new HashMap<String, Object>();
	}

	/**
	 * @return the dataMap
	 */
	public HashMap getDataMap() {
		return dataMap;
	}

	/**
	 * @param dataMap the dataMap to set
	 */
	public void setDataMap(HashMap dataMap) {
		this.dataMap = dataMap;
	}

	public String getID() {
		return id;
	}
	
	public void setID(String id){
		this.id = id;
		
	}

	
	
	
}
