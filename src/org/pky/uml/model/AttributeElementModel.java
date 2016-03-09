package org.pky.uml.model;

public class AttributeElementModel extends ElementModel{

	private AttributeItem item = null;

	public AttributeElementModel(AttributeItem item){

		//		super(item.toString(),ATTR);
		this.item = item;
		this.setName(item.toString());


	}
	@Override
	public void setName(String name) {
		item.setString(name);
		super.setName(item.name);
		
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		if(item!=null)
			return item.toString();
		else
			return super.toString();
	}

}
