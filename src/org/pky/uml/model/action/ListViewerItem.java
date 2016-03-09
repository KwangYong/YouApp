package org.pky.uml.model.action;

import java.util.HashMap;
import java.util.Iterator;

import org.pky.uml.browser.common.propertybrowser.Property;
import org.pky.uml.commons.managers.LayoutManager;
import org.pky.uml.commons.managers.ModelDataManager;
import org.pky.uml.model.command.TreeParent;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class ListViewerItem extends TreeParent implements IActionItem{

	private HashMap<String,Object> actionMap = new HashMap();
	public ListViewerItem(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}


	public void addAction(String key,Object value){
		actionMap.put(key, value);
	}
	
	public HashMap<String,Object> getActionMap(){
		return actionMap;
	}


	@Override
	public Element writeModel(Document doc) {
		Element element = doc.createElement(Property.ID_LISTVIEW_ITEM);
		element.setAttribute(ModelDataManager.ID_PARENT, this.getParent().getId());
		element.setAttribute(ModelDataManager.ID, this.getId());
		element.setAttribute(Property.ID_NAME, this.getName());
		Iterator<String> iterator = actionMap.keySet().iterator();
	
		while(iterator.hasNext()){
			IActionItem actionItem = (IActionItem)actionMap.get(iterator.next());
			
			Element action = actionItem.writeModel(doc);
			action.setAttribute(ModelDataManager.TYPE, Property.ID_LISTVIEW_ITEM);
			action.setAttribute(ModelDataManager.ID_PARENT, this.getId());
			
			element.appendChild(action);
			
		}
		return element;
	}
	
	
	
	
	



}
