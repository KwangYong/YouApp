package org.pky.uml.model;

import java.util.ArrayList;

import org.pky.uml.browser.common.propertybrowser.Property;
import org.pky.uml.commons.managers.LayoutManager;
import org.pky.uml.commons.managers.ProjectManager;
import org.pky.uml.model.action.ActionItem;
import org.pky.uml.model.common.UMLModel;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class YoutubeModel extends UMLModel{

	public YoutubeModel() {
		super(150,150);
	}

	@Override
	public Element writeLayoutAndroid(Document doc) {
		// TODO Auto-generated method stub
		return super.writeLayoutAndroid(doc);
	}

	@Override
	public String writeSourceAndroid() {
		// TODO Auto-generated method stub
		ArrayList actionList = (ArrayList)this.getPropertyValue(Property.ID_ACTION_ITEM);

		StringBuffer str = new StringBuffer();



		str.append(this.getName()+LayoutManager.DOT_WORLD+"getSettings"+LayoutManager.FUNCTION_START_TOKEN+LayoutManager.FUNCTION_END_TOKEN + LayoutManager.DOT_WORLD+"setJavaScriptEnabled"+LayoutManager.FUNCTION_START_TOKEN+"true"+LayoutManager.FUNCTION_END_TOKEN + LayoutManager.END_MARK_TOKEN+LayoutManager.ENTER_TOKEN );
		str.append(this.getName()+LayoutManager.DOT_WORLD+"getSettings"+LayoutManager.FUNCTION_START_TOKEN+LayoutManager.FUNCTION_END_TOKEN + LayoutManager.DOT_WORLD+"setPluginsEnabled"+LayoutManager.FUNCTION_START_TOKEN+"true"+LayoutManager.FUNCTION_END_TOKEN + LayoutManager.END_MARK_TOKEN+LayoutManager.ENTER_TOKEN );
//		 String html = "<iframe class=\\\"youtube-player\\\" style=\\\"border: 0"+LayoutManager.STKEND_TEMP+" width: 100%"+LayoutManager.STKEND_TEMP+" height: 95%"+LayoutManager.STKEND_TEMP+" padding:0px"+LayoutManager.STKEND_TEMP+" margin:0px\\\" id=\\\"ytplayer\\\" type=\\\"text/html\\\" src=\\\""+((String)getPropertyValue(Property.ID_WEBVIEW_URL))+"\\\" frameborder=\\\"0\\\">\n</iframe>\n";
		String html = "<iframe width=\\\"100%\\\" height=\\\"100%\\\" src=\\\""+((String)getPropertyValue(Property.ID_WEBVIEW_URL))+"\\\" frameborder=\\\"0\\\" allowfullscreen></iframe>\n";
		str.append(this.getName()+LayoutManager.DOT_WORLD+"loadDataWithBaseURL"+LayoutManager.FUNCTION_START_TOKEN+LayoutManager.QUO_WORLD+LayoutManager.QUO_WORLD+LayoutManager.COMMAMA_WORLD+LayoutManager.QUO_WORLD+html+LayoutManager.QUO_WORLD+LayoutManager.COMMAMA_WORLD+LayoutManager.QUO_WORLD+"text/html"+LayoutManager.QUO_WORLD+LayoutManager.COMMAMA_WORLD+LayoutManager.QUO_WORLD+"UTF-8"+LayoutManager.QUO_WORLD+LayoutManager.COMMAMA_WORLD+LayoutManager.QUO_WORLD+LayoutManager.QUO_WORLD+LayoutManager.FUNCTION_END_TOKEN+LayoutManager.END_MARK_TOKEN+LayoutManager.ENTER_TOKEN);


		return str.toString();
	}
	public void writeLayoutIOS() {
		
		LayoutManager.getInstance().setSourceModel(this);

		// TODO Auto-generated method stub
		super.writeLayoutIOS();
		StringBuffer str = new StringBuffer();
		
//		String html = "<html><head><style type=\\\"text/css\\\"><body>"+LayoutManager.BLOCK_START_TEMP+"background-color: transparent"+LayoutManager.STKEND_TEMP+"color: white"+LayoutManager.STKEND_TEMP+LayoutManager.BLOCK_START_TEMP+"</style></head><body style=\\\"margin:0\\\"><iframe class=\\\"youtube-player\\\" width=\\\"100%\\\" height=\\\"100%\\\" src=\\\""+((String)getPropertyValue(Property.ID_WEBVIEW_URL))+"\\\" frameborder=\\\"0\\\" allowfullscreen=\\\"true\\\"></iframe></body></html>";
		String html = "<html><head><style type=\\\"text/css\\\">body"+LayoutManager.BLOCK_START_TEMP+"background-color: transparent"+LayoutManager.STKEND_TEMP+"color: white"+LayoutManager.STKEND_TEMP+LayoutManager.BLOCK_START_TEMP+"</style></head><body style=\\\"margin:0\\\"><iframe class=\\\"youtube-player\\\" width=\\\"100%\\\" height=\\\"100%\\\" src=\\\""+((String)getPropertyValue(Property.ID_WEBVIEW_URL))+"\\\" frameborder=\\\"0\\\" allowfullscreen=\\\"true\\\"></iframe></body></html>";
		str.append("["+getName()+"  loadHTMLString:@"+LayoutManager.QUO_WORLD+html+LayoutManager.QUO_WORLD+" baseURL:nil"+"];"+LayoutManager.ENTER_TOKEN);		
		str.append("[scrollView addSubview: "+getName()+"];"+LayoutManager.ENTER_TOKEN);	
		
		OperationItem operationItem = ProjectManager.getInstance().getNullCreateOperation(this, Property.OPERATION_VOID_LOADVIEW);
		operationItem.getActionDetailList().add(ProjectManager.getInstance().addSourceLine(str.toString()));
		LayoutManager.getInstance().addOperationMap(this, operationItem);
	
	}




}
