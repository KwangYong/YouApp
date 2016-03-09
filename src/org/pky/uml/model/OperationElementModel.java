package org.pky.uml.model;

public class OperationElementModel  extends ElementModel{

	private OperationItem item = null;

	public OperationElementModel(OperationItem item){
		this.item = item;
		this.setName(item.toString());
	}
	
	public void setName(String name) {
//		item.setString(name);
//		super.setName(item.name);	
	}

	
	public String toString() {

		if(item!=null)
			return item.toString();
		else
			return super.toString();
	}

}
