package org.pky.uml.model;

import java.util.ArrayList;
import java.util.HashMap;

import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.swt.SWT;
import org.pky.uml.browser.common.propertybrowser.Property;
import org.pky.uml.commons.managers.LayoutManager;
import org.pky.uml.commons.managers.ProjectManager;
import org.pky.uml.model.action.ActionItem;
import org.pky.uml.model.common.UMLModel;
import org.pky.uml.policy.PolicyCommand;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class EditTextModel extends UMLModel {

	public 	EditTextModel(){
		super(200,45);
		setName(ProjectManager.getInstance().getModelDefaultName(1,true));
	}
	
	@Override
	public void writeLayoutIOS() {
		// TODO Auto-generated method stub
		LayoutManager.getInstance().setSourceModel(this);
		super.writeLayoutIOS();
		
		OperationItem operationItem = ProjectManager.getInstance().getNullCreateOperation(this, Property.OPERATION_VOID_LOADVIEW);

		StringBuffer str = new StringBuffer();
		str.append("["+getName()+" setPlaceholder:@"+LayoutManager.QUO_WORLD+getPropertyValue(Property.ID_TEXT)+LayoutManager.QUO_WORLD+"];"+LayoutManager.ENTER_TOKEN);
		str.append(getName()+".delegate = self;"+LayoutManager.ENTER_TOKEN);
		str.append("["+getName()+" becomeFirstResponder];"+LayoutManager.ENTER_TOKEN);
		str.append("["+getName()+" setBorderStyle:UITextBorderStyleRoundedRect];"+LayoutManager.ENTER_TOKEN);
		operationItem.getActionDetailList().add(ProjectManager.getInstance().addSourceLine(str.toString()));
		LayoutManager.getInstance().addOperationMap(this, operationItem);
		

	
	}
	
	@Override
	public Element writeLayoutAndroid(Document doc) {
		// TODO Auto-generated method stub
		Element element = super.writeLayoutAndroid(doc);
		int type = (Integer)this.getPropertyValue(Property.ID_TYPE);
		if(this instanceof EditTextAutoCompleteTextViewModel){

		}else{
			if(type>0){
				element.setAttribute("android:inputType", editTextPropString[type]);

			}
		}
		return element;

	}


	@Override
	public String writeSourceAndroid() {

		return super.writeSourceAndroid();
	}


}
