package org.pky.uml.model;

import org.pky.uml.commons.managers.LayoutManager;
import org.pky.uml.model.common.ArrayItem;

public class AndroidSDKItem {

	public String lebel;
	public String filePath;
	public int id;
	

	public AndroidSDKItem(String lebel,String path,int id){
		this.lebel = lebel; 
		this.filePath = path;
		this.id = id;
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "API"+id+ ":" + LayoutManager.SPACE_TOKEN+lebel;
	}
}
