package org.pky.uml.model.action;

import org.pky.uml.browser.common.propertybrowser.Property;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class ActionMoveItem implements IActionItem{
	
	private String modelID = "";
	private String moveEffect = "";
	
	public ActionMoveItem(String modelID){
		this.modelID = modelID;
	}

	public String getMoveEffect() {
		return moveEffect;
	}

	public void setMoveEffect(String moveEffect) {
		this.moveEffect = moveEffect;
	}

	public String getModelID() {
		return modelID;
	}

	public void setModelID(String modelID) {
		this.modelID = modelID;
	}
	
	@Override
	public Element writeModel(Document doc) {
		Element element = doc.createElement(Property.ACTION_MOVE_LAYOUT);
		element.setAttribute("ID", modelID);
		return element;
	}
	
	
}
