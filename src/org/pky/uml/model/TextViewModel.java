package org.pky.uml.model;

import org.eclipse.swt.graphics.RGB;
import org.pky.uml.browser.common.propertybrowser.Property;
import org.pky.uml.commons.managers.LayoutManager;
import org.pky.uml.commons.managers.ProjectManager;
import org.pky.uml.model.common.UMLModel;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class TextViewModel extends UMLModel{

	public TextViewModel(){
		super(100,50);
//		addChild(new ElementModel());
	}

	@Override
	public Element writeLayoutAndroid(Document doc) {
		// TODO Auto-generated method stub
		Element element = super.writeLayoutAndroid(doc);

		return element;
	}
	@Override
	public void writeLayoutIOS() {
		LayoutManager.getInstance().setSourceModel(this);
		super.writeLayoutIOS();
		
		OperationItem operationItem = ProjectManager.getInstance().getNullCreateOperation(this, Property.OPERATION_VOID_LOADVIEW);

		StringBuffer str = new StringBuffer();

		String text = (String)getPropertyValue(Property.ID_TEXT);
//		text = text.replaceAll("\n", LayoutManager.ENTER_TOKEN_TEMP);
		str.append(getName()+".text = @"+LayoutManager.QUO_WORLD+text+LayoutManager.QUO_WORLD+";"+LayoutManager.ENTER_TOKEN);
		str.append(getName()+".font = [UIFont systemFontOfSize:"+getPropertyValue(Property.ID_TEXT_SIZE)+"];"+LayoutManager.ENTER_TOKEN);
		str.append("["+getName()+" setNumberOfLines:0];"+LayoutManager.ENTER_TOKEN);
		RGB rgb = (RGB)getPropertyValue(Property.ID_COLOR);
		
		str.append(getName()+".textColor = [UIColor colorWithRed:"+rgb.red+"/255.0"+LayoutManager.SPACE_TOKEN+"green:"+rgb.green+"/255.0"+LayoutManager.SPACE_TOKEN+"blue:"+rgb.blue+"/255.0"+LayoutManager.SPACE_TOKEN+"alpha:1.0"+"];"+LayoutManager.ENTER_TOKEN);
		
		operationItem.getActionDetailList().add(ProjectManager.getInstance().addSourceLine(str.toString()));
		LayoutManager.getInstance().addOperationMap(this, operationItem);
		

	}

	public String writeSourceAndroid() {
		StringBuffer str = new StringBuffer();
		str.append(super.writeSourceAndroid());
		str.append(getName()+LayoutManager.DOT_WORLD+"setTextSize"+LayoutManager.FUNCTION_START_TOKEN+"TypedValue.COMPLEX_UNIT_SP"+LayoutManager.COMMAMA_WORLD+getPropertyValue(Property.ID_TEXT_SIZE)+LayoutManager.FUNCTION_END_TOKEN+LayoutManager.END_MARK_TOKEN+LayoutManager.ENTER_TOKEN);
		RGB rgb = (RGB)getPropertyValue(Property.ID_COLOR);
		str.append(getName()+LayoutManager.DOT_WORLD+"setTextColor"+LayoutManager.FUNCTION_START_TOKEN+"Color.rgb"+LayoutManager.FUNCTION_START_TOKEN+rgb.red+LayoutManager.COMMAMA_WORLD+rgb.green+LayoutManager.COMMAMA_WORLD+rgb.blue+LayoutManager.FUNCTION_END_TOKEN+LayoutManager.FUNCTION_END_TOKEN+LayoutManager.END_MARK_TOKEN+LayoutManager.ENTER_TOKEN);
		return str.toString();
	}

}
