package org.pky.uml.model.action;

import org.pky.uml.browser.common.propertybrowser.Property;
import org.pky.uml.commons.managers.ProjectManager;
import org.pky.uml.model.common.UMLModel;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class ActionAutoMoveActionItem implements IActionItem {


	int time = 0;
	String layoutID = "";

	public ActionAutoMoveActionItem(String layoutID,int time) {
		this.layoutID = layoutID;
		this.time = time;
	}
	public Element writeModel(Document doc) {
		// TODO Auto-generated method stub
		Element element = doc.createElement(Property.ID_AUTO_MOVE);
		element.setAttribute("LAYOUT_ID", layoutID);
		element.setAttribute("TIME", String.valueOf(time));
		return element;
	}
	public int getTime() {
		return time;
	}
	public String getLayoutID() {
		return layoutID;
	}
	public void setTime(int time) {
		this.time = time;
	}
	public void setLayoutID(String layoutID) {
		this.layoutID = layoutID;
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub

		if(!layoutID.equals("") ){
			UMLModel model = ProjectManager.getInstance().getSearchID(layoutID);
			if(model!=null){
				return model.getName() + "["+time+"√ ]";
			}else
				return "";
		}else
			return "";
	}

}
