package org.pky.uml.dialog;

import java.util.ArrayList;

import org.eclipse.gef.dnd.TemplateTransfer;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.pky.uml.browser.common.propertybrowser.Property;
import org.pky.uml.commons.managers.ProjectManager;
import org.pky.uml.dialog.common.TreeViewContentProvider;
import org.pky.uml.dialog.common.TreeViewLabelProvider;
import org.pky.uml.dialog.common.TreeViewerDragListener;
import org.pky.uml.dialog.common.TreeViewerDropAdapter;
import org.pky.uml.model.action.ActionAlertMessageItem;
import org.pky.uml.model.action.ActionMoveItem;
import org.pky.uml.model.action.ListViewerItem;
import org.pky.uml.model.common.UMLModel;

public class ListViewItemDialog extends Dialog implements SelectionListener{
	private TreeViewer viewer = null;
	ListViewerItem root;
	ListViewerItem lists;
	Action addAction;
	Action delAction;

	Combo actionMoveCombo;
	Text alertTitleText;
	Text alertMsgText;

	ArrayList<UMLModel> actionMoveComboRef;
	Button alertMsgButton,moveLayoutButton;
	
	boolean isChgSelection = false;

	public ListViewItemDialog(Shell parent) {
		super(parent);
		// TODO Auto-generated constructor stub
	}
	protected Control createDialogArea(Composite parent) {
		final Composite composite = (Composite)super.createDialogArea(parent);

		composite.setLayout(new GridLayout(3, true));
		composite.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		//		Label label = new Label(composite,SWT.NONE);
		//		label.setText("아이템 리스트");		
		//		label.setLayoutData(new GridData(SWT.LEFT,SWT.CENTER,true,true,3,1));


		viewer = new TreeViewer(composite, SWT.FILL|SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER| SWT.MULTI);
		Transfer[] types = new Transfer[] {
				TemplateTransfer.getInstance()
		};
		viewer.addDragSupport(DND.DROP_COPY | DND.DROP_MOVE, types,	new TreeViewerDragListener(viewer));
		viewer.addDropSupport(DND.DROP_COPY | DND.DROP_MOVE, types,	new TreeViewerDropAdapter(viewer));
		GridData data = new GridData(GridData.FILL_BOTH);
		data.verticalSpan = 1;
		data.horizontalSpan = 1;
		viewer.getControl().setLayoutData(data);
		viewer.setContentProvider(new TreeViewContentProvider());       
		viewer.setLabelProvider(new TreeViewLabelProvider());           

		viewer.setInput(initialize());

		viewer.addDoubleClickListener(new IDoubleClickListener() {

			@Override
			public void doubleClick(DoubleClickEvent event) {

				openInputDialog();
			}
		});

		viewer.addSelectionChangedListener(new ISelectionChangedListener() {

			@Override
			public void selectionChanged(SelectionChangedEvent event) {
				isChgSelection = true;
				setItemAction();
				isChgSelection = false;

			}
		});



		MenuManager menuMgr = new MenuManager("#PopupMenu");
		menuMgr.setRemoveAllWhenShown(true);
		menuMgr.addMenuListener(new IMenuListener() {
			public void menuAboutToShow(IMenuManager manager) {
				setMenuList(manager);
			}
		});
		Menu menu = menuMgr.createContextMenu(viewer.getControl());
		viewer.getControl().setMenu(menu);

		viewer.expandAll();

		data = new GridData(GridData.FILL_BOTH);
		data.horizontalSpan = 2;
		data.minimumHeight = 300;
		Group group = new Group(composite, SWT.NONE);
		group.setLayoutData(data);
		group.setLayout(new GridLayout(3, true));
		group.setText("액션");



		GridData data1 = new GridData(GridData.FILL_HORIZONTAL);
		data1.horizontalSpan = 3;
		data1.verticalSpan = 2;

		moveLayoutButton = new Button(group, SWT.RADIO);
		moveLayoutButton.setText("화면이동");
		moveLayoutButton.addSelectionListener(this);
		actionMoveCombo = new Combo(group, SWT.BORDER);
		actionMoveCombo.setLayoutData(data1);
		actionMoveCombo.addSelectionListener(this);

		actionMoveComboRef = ProjectManager.getInstance().getModels(2);

		for(int i = 0; i < actionMoveComboRef.size(); i ++){
			UMLModel model = actionMoveComboRef.get(i);
			String name = actionMoveComboRef.get(i).getName();
			String text =(String)model.getPropertyValue(Property.ID_TEXT);
			
			if(text!=null && !text.equals("")){
				name = name + "[" + text + "]";
			}
			
			actionMoveCombo.add(name);		
		}

		alertMsgButton = new Button(group, SWT.RADIO);
		alertMsgButton.setText("메세지창 띄우기");
		alertMsgButton.setLayoutData(data1);
		alertMsgButton.addSelectionListener(this);

		alertTitleText = new Text(group,SWT.BORDER);
		alertTitleText.setLayoutData(data1);

		alertMsgText = new Text(group,SWT.BORDER|SWT.MULTI);
		alertMsgText.setLayoutData(data1);


		alertMsgText.addModifyListener(new ModifyListener() {

			@Override
			public void modifyText(ModifyEvent e) {
				// TODO Auto-generated method stub
				if(!alertMsgText.getText().equals("")){

					saveAction();
				}

			}
		});
		actionMoveCombo.setEnabled(false);
		alertMsgText.setEnabled(false);
		alertTitleText.setEnabled(false);

		return composite;
	}
// 아이템 선택했을시 내용 로드 
	public void setItemAction(){

		//초기화 
		moveLayoutButton.setSelection(false);
		alertMsgButton.setSelection(false);
		actionMoveCombo.setEnabled(false);
		alertMsgText.setEnabled(false);
		alertTitleText.setEnabled(false);
		alertMsgText.setText("");
		alertTitleText.setText("");
		actionMoveCombo.select(-1);

		if(getSelection()!=null){
			ListViewerItem item = getSelection();
			java.util.Iterator iterator =  item.getActionMap().keySet().iterator();

			while(iterator.hasNext()){
				String key = (String)iterator.next();
				if(key.equals(Property.ACTION_MOVE_LAYOUT)){
					ActionMoveItem actionMoveItem = (ActionMoveItem)item.getActionMap().get(Property.ACTION_MOVE_LAYOUT);
					
					actionMoveCombo.select(getActionMoveComboRefIndex(actionMoveItem.getModelID()));

					alertMsgButton.setSelection(false);
					moveLayoutButton.setSelection(true);
					actionMoveCombo.setEnabled(true);
				}else if(key.equals(Property.ACTION_ALERT)){
					ActionAlertMessageItem text = (ActionAlertMessageItem)item.getActionMap().get(Property.ACTION_ALERT);
					alertTitleText.setText(text.getTitle());
					alertMsgText.setText(text.getMessage());

					moveLayoutButton.setSelection(false);
					alertMsgButton.setSelection(true);
					alertMsgText.setEnabled(true);
					alertTitleText.setEnabled(true);
				}
			}
		}
	}

	public ListViewerItem getViewerSelection() {
		TreeSelection iSelection = (TreeSelection)viewer.getSelection();
		ListViewerItem treeObject = (ListViewerItem)iSelection.getFirstElement();
		return treeObject;
	}
	//저장
	public void saveAction(){
		if(getViewerSelection()!=null && !isChgSelection){
			ListViewerItem item = getViewerSelection();
			item.getActionMap().clear();
			if(moveLayoutButton.getSelection()){
				if(actionMoveCombo.getSelectionIndex()>=0)
					item.addAction(Property.ACTION_MOVE_LAYOUT, new ActionMoveItem(actionMoveComboRef.get(actionMoveCombo.getSelectionIndex()).getID()));
			}else if(alertMsgButton.getSelection()){
				if(!alertMsgText.getText().equals("") || !alertTitleText.getText().equals("")){
					item.addAction(Property.ACTION_ALERT,new ActionAlertMessageItem(alertTitleText.getText(), alertMsgText.getText()) );
				}
			}
		}
	}
	private ListViewerItem initialize() {

		try{
			Object obj = ProjectManager.getInstance().getSelections().get(0).getUMLModel();
			if(ProjectManager.getInstance().getSelections().size()==1 && 
					ProjectManager.getInstance().getSelections().get(0).getUMLModel().getPropertyValue(Property.ID_LISTVIEW_ITEM)!=null){
				ListViewerItem item  = (ListViewerItem)ProjectManager.getInstance().getSelections().get(0).getUMLModel().getPropertyValue(Property.ID_LISTVIEW_ITEM);
				lists = item;
			}else{
				lists = new ListViewerItem("List");
			}
			root = new ListViewerItem("Model");

			root.addChild(lists);

		}catch(Exception e){
			e.printStackTrace();
		}
		return root;
	}
	@Override
	protected void buttonPressed(int buttonId) {
		// TODO Auto-generated method stub
		super.buttonPressed(buttonId);
		if(buttonId==Dialog.OK){
			for(int i = 0 ; i < ProjectManager.getInstance().getSelections().size(); i ++){
				ProjectManager.getInstance().getSelections().get(i).getUMLModel().setPropertyValue(Property.ID_LISTVIEW_ITEM, lists);
			}
		}
	}
	public void openInputDialog(){
		InputDialog inputDialog = new InputDialog(getShell(), "이름 변경", "아이템 이름을 입력하여 주세요.",getSelection().getName(), null);
		if(inputDialog.open()==0){
			getSelection().setName(inputDialog.getValue());
			viewer.refresh();
		}
	}

	private void setMenuList(IMenuManager manager) {
		addAction = new Action() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				ListViewerItem treeModel = new ListViewerItem("이름");
				getSelection().addChild(treeModel);
				viewer.refresh(getSelection());
				viewer.expandAll();

				ISelection sel = new StructuredSelection(treeModel);
				viewer.setSelection(sel);

				openInputDialog();
				super.run();
			}
		};
		addAction.setText("추가");

		delAction = new Action() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				ListViewerItem treeModel = (ListViewerItem) getSelection().getParent();
				ListViewerItem selectionTreeModel = getSelection();
				treeModel.removeChild(selectionTreeModel);
				viewer.remove(selectionTreeModel);
				viewer.expandAll();
				viewer.refresh();

				super.run();
			}
		};
		delAction.setText("삭제");

		manager.add(addAction);
		if(getSelection()!=lists){
			manager.add(delAction);
		}
	}
	public int getActionMoveComboRefIndex(String id){

		for(int i = 0 ; i < actionMoveComboRef.size(); i ++){

			if(actionMoveComboRef.get(i).getID().equals(id))
				return i;
		}
		return -1;
	}
	public ListViewerItem getSelection() {
		TreeSelection iSelection = (TreeSelection)viewer.getSelection();
		ListViewerItem treeObject = (ListViewerItem)iSelection.getFirstElement();
		if(treeObject!=null)
			return treeObject;
		else 
			return root;
	}
	@Override
	public void widgetSelected(SelectionEvent e) {

		Object obj = e.widget;
		alertTitleText.setEnabled(alertMsgButton.getSelection());
		alertMsgText.setEnabled(alertMsgButton.getSelection());
		actionMoveCombo.setEnabled(moveLayoutButton.getSelection());

		saveAction();



	}
	@Override
	public void widgetDefaultSelected(SelectionEvent e) {
		// TODO Auto-generated method stub

		System.out.println("");

	}
}
