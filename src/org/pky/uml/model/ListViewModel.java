package org.pky.uml.model;

import java.util.ArrayList;

import org.pky.uml.browser.common.propertybrowser.Property;
import org.pky.uml.commons.managers.LayoutManager;
import org.pky.uml.commons.managers.ProjectManager;
import org.pky.uml.model.action.ActionItem;
import org.pky.uml.model.action.ListViewerItem;
import org.pky.uml.model.common.UMLModel;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class ListViewModel extends UMLModel{
	public ListViewModel(){
		super(LayoutModel.DISPLAY_WIDTH,LayoutModel.DISPLAY_HEIGHT);
	}
	public ListViewModel(int width,int height){
		super(width,height);
	}

	@Override
	public void writeLayoutIOS() {
		// TODO Auto-generated method stub
		super.writeLayoutIOS();
		
		OperationItem operationItem = null;

		operationItem = ProjectManager.getInstance().getNullCreateOperation(this, Property.OPERATION_VOID_LOADVIEW);
		
		StringBuffer str = new StringBuffer();

		str.append(getName()+".delegate = self;"+LayoutManager.ENTER_TOKEN);
		str.append(getName()+".dataSource = self;"+LayoutManager.ENTER_TOKEN);
		operationItem.getActionDetailList().add(ProjectManager.getInstance().addSourceLine(str.toString()));
		LayoutManager.getInstance().addOperationMap(this, operationItem);
		
		if(this.getPropertyValue(Property.ID_LISTVIEW_ITEM)!=null){
			ListViewerItem item = (ListViewerItem)this.getPropertyValue(Property.ID_LISTVIEW_ITEM);
			//OPERATION_INT_NUMBEROFROWSINSECTION
			operationItem = ProjectManager.getInstance().getNullCreateOperation(this, Property.OPERATION_INT_TABLEVIEW_NUMBEROFROWSINSECTION);
			

			str = new StringBuffer();
			str.append("return"+LayoutManager.SPACE_TOKEN+item.getChildren().length+LayoutManager.END_MARK_TOKEN+LayoutManager.ENTER_TOKEN);
			operationItem.getActionDetailList().add(ProjectManager.getInstance().addSourceLine(str.toString()));
			LayoutManager.getInstance().addOperationMap(this, operationItem);
			//OPERATION_UITABLEVIEWCELL_CELLFORROWATINDEXPATH
			str = new StringBuffer();
			operationItem = ProjectManager.getInstance().getNullCreateOperation(this, Property.OPERATION_UITABLEVIEWCELL_CELLFORROWATINDEXPATH);
			
			str.append("UITableViewCell *cell = [tableView dequeueReusableCellWithIdentifier:@"+LayoutManager.QUO_WORLD+LayoutManager.QUO_WORLD+"]"+LayoutManager.END_MARK_TOKEN+LayoutManager.ENTER_TOKEN);
			str.append("if (cell == nil)"+LayoutManager.BLOCK_START_TOKEN+LayoutManager.ENTER_TOKEN);
			str.append("cell = [[UITableViewCell alloc] initWithFrame:CGRectZero]"+LayoutManager.END_MARK_TOKEN+LayoutManager.ENTER_TOKEN);
			str.append(LayoutManager.BLOCK_END_TOKEN+LayoutManager.ENTER_TOKEN);

			for(int i = 0; i < item.getChildren().length; i ++){
				str.append("if"+LayoutManager.FUNCTION_START_TOKEN+LayoutManager.SQUARE_BRACKET_START_KEY_TOKEN+"indexPath row"+LayoutManager.SQUARE_BRACKET_END_KEY_TOKEN+"=="+i+LayoutManager.FUNCTION_END_TOKEN+LayoutManager.BLOCK_START_TOKEN+LayoutManager.ENTER_TOKEN);
				str.append("cell.textLabel.text = @"+LayoutManager.QUO_WORLD+item.getChildren()[i].getName()+LayoutManager.QUO_WORLD+LayoutManager.END_MARK_TOKEN+LayoutManager.ENTER_TOKEN);
				str.append(LayoutManager.BLOCK_END_TOKEN+LayoutManager.ENTER_TOKEN);
			}
			str.append("return cell"+LayoutManager.END_MARK_TOKEN+LayoutManager.ENTER_TOKEN);
			operationItem.getActionDetailList().add(ProjectManager.getInstance().addSourceLine(str.toString()));
			LayoutManager.getInstance().addOperationMap(this, operationItem);
			
			str = new StringBuffer();
			operationItem = ProjectManager.getInstance().getNullCreateOperation(this, Property.OPERATION_VOID_TABLEVIEW_DIDSELECTROWATINDEXPATH);
//			for(int i = 0; i < item.getChildren().length; i ++){
//				str.append("if"+LayoutManager.FUNCTION_START_TOKEN+LayoutManager.SQUARE_BRACKET_START_KEY_TOKEN+"indexPath row"+LayoutManager.SQUARE_BRACKET_END_KEY_TOKEN+"=="+i+LayoutManager.FUNCTION_END_TOKEN+LayoutManager.BLOCK_START_TOKEN+LayoutManager.ENTER_TOKEN);
				str.append(LayoutManager.getInstance().writeActionListViewItemClick((ListViewerItem)item));
//				str.append(LayoutManager.BLOCK_END_TOKEN+LayoutManager.ENTER_TOKEN);
//			}
			
			operationItem.getActionDetailList().add(ProjectManager.getInstance().addSourceLine(str.toString()));
			LayoutManager.getInstance().addOperationMap(this, operationItem);
		}else{
			
			operationItem = ProjectManager.getInstance().getNullCreateOperation(this, Property.OPERATION_INT_TABLEVIEW_NUMBEROFROWSINSECTION);
			
			operationItem.getActionDetailList().add(ProjectManager.getInstance().addSourceLine("return 0"+LayoutManager.END_MARK_TOKEN+LayoutManager.ENTER_TOKEN));
			LayoutManager.getInstance().addOperationMap(this, operationItem);
			
			operationItem = ProjectManager.getInstance().getNullCreateOperation(this, Property.OPERATION_UITABLEVIEWCELL_CELLFORROWATINDEXPATH);
			
			str = new StringBuffer();
			str.append("UITableViewCell *cell = [tableView dequeueReusableCellWithIdentifier:@"+LayoutManager.QUO_WORLD+LayoutManager.QUO_WORLD+"]"+LayoutManager.END_MARK_TOKEN+LayoutManager.ENTER_TOKEN);
			str.append("if (cell == nil)"+LayoutManager.BLOCK_START_TOKEN+LayoutManager.END_MARK_TOKEN+LayoutManager.ENTER_TOKEN);
			str.append("cell = [[UITableViewCell alloc] initWithFrame:CGRectZero]"+LayoutManager.END_MARK_TOKEN+LayoutManager.ENTER_TOKEN);
			str.append(LayoutManager.BLOCK_END_TOKEN+LayoutManager.ENTER_TOKEN);
			
			str.append("return cell"+LayoutManager.END_MARK_TOKEN+LayoutManager.ENTER_TOKEN);
			
			operationItem.getActionDetailList().add(ProjectManager.getInstance().addSourceLine(str.toString()));
			LayoutManager.getInstance().addOperationMap(this, operationItem);
		}
		
		
	}
	
	@Override
	public Element writeLayoutAndroid(Document doc) {
		// TODO Auto-generated method stub
		return super.writeLayoutAndroid(doc);
	}

	public String writeSourceAndroid() {
		// TODO Auto-generated method stub
		ArrayList actionList = (ArrayList)this.getPropertyValue(Property.ID_ACTION_ITEM);

		String items ="";
		StringBuffer str = new StringBuffer();
		if(this.getPropertyValue(Property.ID_LISTVIEW_ITEM)!=null){
			ListViewerItem item = (ListViewerItem)this.getPropertyValue(Property.ID_LISTVIEW_ITEM);

			for(int i = 0; i < item.getChildren().length; i ++){
				if(items.equals("")){
					items = LayoutManager.QUO_WORLD + item.getChildren()[i].getName() + LayoutManager.QUO_WORLD;
				}else{
					items = items + LayoutManager.COMMAMA_WORLD + LayoutManager.QUO_WORLD + item.getChildren()[i].getName() + LayoutManager.QUO_WORLD;
				}
			}



			str.append(this.getName()+LayoutManager.DOT_WORLD+"setAdapter");
			str.append(LayoutManager.FUNCTION_START_TOKEN);
			str.append(LayoutManager.NEW_WORLD + LayoutManager.SPACE_TOKEN + "ArrayAdapter<String>"+ LayoutManager.FUNCTION_START_TOKEN);

			str.append("this"+LayoutManager.COMMAMA_WORLD+"android.R.layout.simple_list_item_1"+LayoutManager.COMMAMA_WORLD+LayoutManager.NEW_WORLD+ LayoutManager.SPACE_TOKEN+ "String[]"+LayoutManager.BLOCK_START_TOKEN+items+LayoutManager.BLOCK_END_TOKEN+LayoutManager.FUNCTION_END_TOKEN+LayoutManager.FUNCTION_END_TOKEN+LayoutManager.END_MARK_TOKEN);
			str.append(LayoutManager.ENTER_TOKEN);


			ActionItem onclickItem = new  ActionItem();
			onclickItem.setOperation(this.getViewModel().getAction(Property.OPERATION_ON_ITEM_CLICK));
			onclickItem.put(Property.ACTION_LISTVIEW_ITEM_ONCLICK, this.getPropertyValue(Property.ID_LISTVIEW_ITEM));


			ArrayList tempActionList = new ArrayList();
			if(actionList!=null)
				for(int i = 0 ; i < actionList.size(); i ++){
					tempActionList.add(actionList.get(i));
				}

			tempActionList.add(onclickItem);

			this.setPropertyValue(Property.ID_ACTION_ITEM, tempActionList);





		}


		str.append(super.writeSourceAndroid());
		this.setPropertyValue(Property.ID_ACTION_ITEM, actionList);
		return str.toString();
	}


}
