package org.pky.uml.model;

import java.util.ArrayList;

import org.pky.uml.browser.common.propertybrowser.Property;
import org.pky.uml.commons.managers.LayoutManager;
import org.pky.uml.commons.managers.ProjectManager;
import org.pky.uml.model.action.ActionItem;
import org.pky.uml.model.common.UMLModel;

public class WebViewModel extends UMLModel{
	public WebViewModel() {
		super(280,280);
	}
	
	public WebViewModel(int width,int height){
		super(width,height);
	}
	
	@Override
	public void writeLayoutIOS() {
		LayoutManager.getInstance().setSourceModel(this);
		// TODO Auto-generated method stub
		super.writeLayoutIOS();
		StringBuffer str = new StringBuffer();
		
		str.append("["+getName()+"  loadRequest:[NSURLRequest requestWithURL:[NSURL URLWithString:@"+LayoutManager.QUO_WORLD+getPropertyValue(Property.ID_WEBVIEW_URL)+LayoutManager.QUO_WORLD+"]]];"+LayoutManager.ENTER_TOKEN);		
		str.append("[scrollView addSubview: "+getName()+"];"+LayoutManager.ENTER_TOKEN);	
		
		OperationItem operationItem = ProjectManager.getInstance().getNullCreateOperation(this, Property.OPERATION_VOID_LOADVIEW);
		operationItem.getActionDetailList().add(ProjectManager.getInstance().addSourceLine(str.toString()));
		LayoutManager.getInstance().addOperationMap(this, operationItem);
	}
//	public ArrayList<OperationItem> writeLayoutIOS(ArrayList<OperationItem> operations) {
//
//
//		OperationItem operationItem = null;
//		
//		operationItem = ProjectManager.getInstance().getOperation(operations, Property.OPERATION_VOID_LOADVIEW);
//
//		
//		if(operationItem==null){
//			operationItem = ProjectManager.getInstance().createIosOperation(Property.OPERATION_VOID_LOADVIEW);
//			operations.add(operationItem);
//		}
//		StringBuffer str = new StringBuffer();
//		str.append(getName()+"= [[UIWebView alloc]initWithFrame:CGRectMake("+ProjectManager.getInstance().getLayoutIOSLocation(this)+")];"+LayoutManager.ENTER_TOKEN);										
//		str.append("["+getName()+"  loadRequest:[NSURLRequest requestWithURL:[NSURL URLWithString:@"+LayoutManager.QUO_WORLD+getPropertyValue(Property.ID_WEBVIEW_URL)+LayoutManager.QUO_WORLD+"]]];"+LayoutManager.ENTER_TOKEN);		
//		str.append("[scrollView addSubview: "+getName()+"];"+LayoutManager.ENTER_TOKEN);	
//		
//		operationItem.getActionDetailList().add(ProjectManager.getInstance().addSourceLine(str.toString()));
//		LayoutManager.getInstance().addOperationMap(this, operationItem);
//		
//		return operations;
//	
//		
//	}
	@Override
	public String writeSourceAndroid() {
		
		ArrayList actionList = (ArrayList)this.getPropertyValue(Property.ID_ACTION_ITEM);
		
		StringBuffer str = new StringBuffer();
		
		str.append(this.getName()+LayoutManager.DOT_WORLD+"getSettings"+LayoutManager.FUNCTION_START_TOKEN+LayoutManager.FUNCTION_END_TOKEN + LayoutManager.DOT_WORLD+"setJavaScriptEnabled"+LayoutManager.FUNCTION_START_TOKEN+"true"+LayoutManager.FUNCTION_END_TOKEN + LayoutManager.END_MARK_TOKEN+LayoutManager.ENTER_TOKEN );
		str.append(this.getName()+LayoutManager.DOT_WORLD+"loadUrl"+LayoutManager.FUNCTION_START_TOKEN+LayoutManager.QUO_WORLD+getPropertyValue(Property.ID_WEBVIEW_URL)+LayoutManager.QUO_WORLD+LayoutManager.FUNCTION_END_TOKEN+LayoutManager.END_MARK_TOKEN+LayoutManager.ENTER_TOKEN);
		
		ActionItem onclickItem = new  ActionItem();
		onclickItem.setOperation(this.getViewModel().getAction(Property.OPERATION_SHOULD_OVERRIDE_URL_LOADING));
		onclickItem.put(Property.ACTION_WEBVIEW_LOADURL, this.getPropertyValue(Property.ID_WEBVIEW_URL));


		ArrayList tempActionList = new ArrayList();
		if(actionList!=null)
			for(int i = 0 ; i < actionList.size(); i ++){
				tempActionList.add(actionList.get(i));
			}

		tempActionList.add(onclickItem);
		this.setPropertyValue(Property.ID_ACTION_ITEM, tempActionList);
		str.append(super.writeSourceAndroid());
		this.setPropertyValue(Property.ID_ACTION_ITEM, actionList);
		return str.toString();
	}
}
