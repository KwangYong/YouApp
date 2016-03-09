package org.pky.uml.model;

import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.pky.uml.browser.common.propertybrowser.Property;
import org.pky.uml.commons.managers.LayoutManager;
import org.pky.uml.commons.managers.ProjectManager;
import org.pky.uml.model.common.UMLModel;

public class ButtonModel extends UMLModel{


	public ButtonModel(){
		super(130,45);
	}
	public ButtonModel(int width,int height){
		super(width,height);
	}
	@Override
	public void writeLayoutIOS() {
		// TODO Auto-generated method stub
//		super.writeLayoutIOS();
		
		LayoutManager.getInstance().setSourceModel(this);
		OperationItem operationItem = ProjectManager.getInstance().getNullCreateOperation(this, Property.OPERATION_VOID_LOADVIEW);
		StringBuffer str = new StringBuffer();
		str.append(getName()+" = [UIButton buttonWithType:UIButtonTypeRoundedRect];"+LayoutManager.ENTER_TOKEN);										
		str.append("["+getName()+" setTitle:@"+LayoutManager.QUO_WORLD+getPropertyValue(Property.ID_TEXT)+LayoutManager.QUO_WORLD+" forState:UIControlStateNormal];"+LayoutManager.ENTER_TOKEN);
		str.append(getName()+".frame = CGRectMake("+ProjectManager.getInstance().getLayoutIOSLocation(this)+");"+LayoutManager.ENTER_TOKEN);
		str.append(LayoutManager.SQUARE_BRACKET_START_KEY_TOKEN+"scrollView"+LayoutManager.SPACE_TOKEN+"addSubview"+LayoutManager.COLON_KEY_TOKEN+LayoutManager.SPACE_TOKEN+getName()+LayoutManager.SQUARE_BRACKET_END_KEY_TOKEN+LayoutManager.END_MARK_TOKEN+LayoutManager.ENTER_TOKEN);
		
		operationItem.getActionDetailList().add(ProjectManager.getInstance().addSourceLine(str.toString()));
		LayoutManager.getInstance().addOperationMap(this, operationItem);
		
		
	}

	public String writeSourceIOSM() {
		StringBuffer str = new StringBuffer();

		return str.toString();
	}
	public String writeSourceAndroid() {
		StringBuffer str = new StringBuffer();
		str.append(super.writeSourceAndroid());
		return str.toString();
	}
}
