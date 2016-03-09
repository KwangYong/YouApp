package org.pky.uml.model.action;

import org.pky.uml.browser.common.propertybrowser.Property;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class ActionMobileServiceCallItem implements IActionItem{
	
	private String id = "";
	private String value = "";
	
	public ActionMobileServiceCallItem(String id,String value){
		this.id = id;
		this.value = value;
	}
	
	public String getId() {
		return id;
	}
	public String getValue() {
		return value;
	}
	public void setId(String id) {
		this.id = id;
	}
	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public Element writeModel(Document doc) {
		// TODO Auto-generated method stub
		Element element = doc.createElement(Property.ACTION_MOBILE_SERVICE_CALL);
		element.setAttribute("ID", id);
		element.setAttribute("VALUE", value);
		return element;
	}


}
