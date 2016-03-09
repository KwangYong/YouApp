package org.pky.uml.model.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.pky.uml.model.OperationItem;
import org.pky.uml.model.command.TreeObject;
import org.pky.uml.model.common.ArrayItem;

public class ActionItem {


	private int actionType = 0;
	private HashMap<String,Object> actionData = new HashMap();
	private OperationItem operation;

	public ActionItem(){

	}
	public ActionItem(int type){
		this.actionType = type;
	}
	public ActionItem(String type){
		this.actionType = Integer.parseInt(type);
	}
	public int getActionType() {
		return actionType;
	}

	public void setActionType(int actionType) {
		this.actionType = actionType;
	}

	public HashMap<String, Object> getActionData() {
		return actionData;
	}
	public void setActionData(HashMap<String, Object> actionData) {
		this.actionData = actionData;
	}
	public void remove(String id){
		actionData.remove(id);
	}
	public void put(String id,Object value){
		actionData.put(id, value);
	}
	public Iterator<String> getIterator(){
		return actionData.keySet().iterator();
	}


	public Object get(String id){
		if(actionData.get(id)!=null){
			return actionData.get(id);
		}else{
			return null;
		}
	}

	public String getLabel() {
		// TODO Auto-generated method stub
		if(operation==null)
			return null;
		else
			return operation.getLabel();
	}
	public OperationItem getOperation() {
		return operation;
	}
	public void setOperation(OperationItem operation) {
		this.operation = operation;
	}


}
