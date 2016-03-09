package org.pky.uml.model;

import java.util.ArrayList;

import org.pky.uml.commons.managers.LayoutManager;
import org.pky.uml.model.common.ArrayItem;
import org.pky.uml.model.common.UMLModel;

public class InterfaceItem extends ArrayItem {

	ArrayList<OperationItem> item = new ArrayList();

	public String id;
	public String android_operation;
	public String ios_operation;
	public String interfaceName;

	public InterfaceItem(String id,String android_operation,String ios_operation,String interfaceName,UMLModel model){
		this.id = id;
		this.android_operation = android_operation;
		this.ios_operation = ios_operation;
		this.interfaceName = interfaceName;
		this.item = item;

		if(model!=null)
			model.addAction(this);
	}

	public ArrayList<OperationItem> getItem() {
		return item;
	}

	public String getId() {
		return id;
	}

	public String getAndroid_operation() {
		return android_operation;
	}

	public String getIos_operation() {
		return ios_operation;
	}

	public String getInterfaceName() {
		return interfaceName;
	}

	public void setItem(ArrayList<OperationItem> item) {
		this.item = item;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setAndroid_operation(String android_operation) {
		this.android_operation = android_operation;
	}

	public void setIos_operation(String ios_operation) {
		this.ios_operation = ios_operation;
	}

	public void setInterfaceName(String interfaceName) {
		this.interfaceName = interfaceName;
	}

	public String getLabel(){
		return "";
	}
	public void addItem(OperationItem oper){
		this.item.add(oper);
	}
	public String writeSourceAndroid(){
		LayoutManager.getInstance().setType(1);
		return LayoutManager.getInstance().writeListener(this);
	}
	



}
