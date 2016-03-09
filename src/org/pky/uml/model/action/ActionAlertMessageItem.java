package org.pky.uml.model.action;

import org.pky.uml.browser.common.propertybrowser.Property;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

//Alert ¸Þ¼¼Áö 
public class ActionAlertMessageItem implements IActionItem {
	private String title = "";
	private String message = "";
	
	public ActionAlertMessageItem(String title,String message){
		this.title = title;
		this.message = message;
	}

	public String getTitle() {
		return title;
	}

	public String getMessage() {
		return message;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return title + message;
	}

	@Override
	public Element writeModel(Document doc) {
		
		Element element = doc.createElement(Property.ACTION_ALERT);
		element.setAttribute("TITLE", title);
		element.setAttribute("MESSAGE", message);
		return element;
	}

	

}
