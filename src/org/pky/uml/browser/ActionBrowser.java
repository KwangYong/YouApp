package org.pky.uml.browser;

import java.util.ArrayList;

import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.ComboBoxCellEditor;
import org.eclipse.jface.viewers.ICellModifier;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TableLayout;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.part.ViewPart;
import org.pky.uml.browser.common.propertybrowser.Property;
import org.pky.uml.commons.managers.ProjectManager;
import org.pky.uml.model.ViewModel;
import org.pky.uml.model.action.ActionAlertMessageItem;
import org.pky.uml.model.action.ActionItem;
import org.pky.uml.model.action.ActionMobileServiceCallItem;
import org.pky.uml.model.action.ActionMoveItem;
import org.pky.uml.model.common.UMLModel;

public class ActionBrowser extends ViewPart implements SelectionListener,ModifyListener{

	public static String ID = "org.pky.uml.browser.ActionBrowser";

	private TableViewer tableViewer;

	private static final String TPYE_PROPERTY = "액션 타입";


	Button upButton,downButton,addButton,delButton;
	Table actionTable;
	UMLModel umlModel;
	Combo moveCombo,moveEffectCombo;
	ArrayList<UMLModel> moveComboRef;
	ArrayList<String> moveEffectComboRef;

	//모바일 서비스 호출
	Combo mobileServiceCallCombo;
	ArrayList<String> mobileServiceCallComboRef;

	Button moveLayoutButton;
	Button alertMessageButton;
	Button mobileServiceCallButton;
	Text alertTitleText; //Alert메세지 박스
	Text alertMessageText; //Alert메세지 박스

	Text mobileServiceText; //내부 서비스 호출내용 


	public void createPartControl(Composite parent) {


		GridLayout gridLayout = new GridLayout(4, false);
		GridData gridData = new GridData(GridData.HORIZONTAL_ALIGN_FILL);

		gridLayout.marginBottom = 0;
		gridLayout.marginHeight = 0;
		gridLayout.marginLeft = 0;
		gridLayout.marginTop = 0;

		parent.setLayout(gridLayout);
		parent.setLayoutData(gridData);

		GridData data1 = new GridData(GridData.FILL_HORIZONTAL);
		data1.horizontalSpan = 4;
		//		data1.heightHint = 200;
		//		data1.heightHint = 30;


		Group buttonGroup = new Group(parent, SWT.NONE);
		buttonGroup.setLayout(gridLayout);
		buttonGroup.setLayoutData(data1);
		addButton = new Button(buttonGroup, SWT.PUSH);

		addButton.setText("+");

		addButton.addSelectionListener(this);


		delButton = new Button(buttonGroup, SWT.PUSH);
		delButton.setText("-");

		delButton.addSelectionListener(this);

		upButton = new Button(buttonGroup, SWT.PUSH);
		upButton.setText("↑");

		downButton = new Button(buttonGroup, SWT.PUSH);
		downButton.setText("↓");

		data1 = new GridData(GridData.FILL_HORIZONTAL);
		data1.horizontalSpan = 4;
		data1.heightHint = 110;

		actionTable = new Table(parent, SWT.BORDER);

		actionTable.setLayoutData(data1);

		tableViewer = buildAndLayoutTable(actionTable);

		attachContentProvider(tableViewer);
		attachLabelProvider(tableViewer);
		attachCellEditors(tableViewer, actionTable);

		tableViewer.addSelectionChangedListener(new ISelectionChangedListener() {

			@Override
			public void selectionChanged(SelectionChangedEvent event) {
				StructuredSelection selection = (StructuredSelection)event.getSelection();

				setSubProperty((ActionItem)selection.getFirstElement());

			}
		});
		data1 = new GridData(GridData.FILL_HORIZONTAL);
		data1.horizontalSpan = 4;

		moveLayoutButton = new Button(parent, SWT.CHECK);
		moveLayoutButton.setText("화면이동");
		moveLayoutButton.setLayoutData(data1);

		moveLayoutButton.addSelectionListener(this);
		moveCombo = new Combo(parent, SWT.BORDER);
		moveCombo.setLayoutData(data1);

		moveCombo.addModifyListener(this);

		//		moveEffectCombo = new Combo(parent, SWT.BORDER);
		//		moveEffectCombo.setLayoutData(data1);
		//
		//		moveEffectCombo.addModifyListener(this);

		alertMessageButton = new Button(parent, SWT.CHECK);
		alertMessageButton.setText("메세지창 띄우기");
		alertMessageButton.setLayoutData(data1);
		alertMessageButton.addSelectionListener(this);

		alertTitleText = new Text(parent,SWT.BORDER);
		alertTitleText.setLayoutData(data1);
		alertTitleText.addModifyListener(this);

		data1 = new GridData(GridData.FILL_HORIZONTAL);
		data1.horizontalSpan = 4;
		data1.heightHint = 30;
		alertMessageText = new Text(parent, SWT.BORDER|SWT.MULTI|SWT.SCROLL_PAGE);
		alertMessageText.setLayoutData(data1);
		alertMessageText.addModifyListener(this);

		data1 = new GridData(GridData.FILL_HORIZONTAL);
		data1.horizontalSpan = 4;

		mobileServiceCallButton = new Button(parent, SWT.CHECK);
		mobileServiceCallButton.setText("내부 서비스 연동");
		mobileServiceCallButton.setLayoutData(data1);
		mobileServiceCallButton.addSelectionListener(this);

		data1 = new GridData(GridData.FILL_HORIZONTAL);
		data1.horizontalSpan = 4;

		mobileServiceCallCombo = new Combo(parent, SWT.BORDER);
		mobileServiceCallCombo.setLayoutData(data1);

		data1 = new GridData(GridData.FILL_HORIZONTAL);
		data1.horizontalSpan = 4;
		data1.heightHint = 30;

		mobileServiceText= new Text(parent,SWT.BORDER);
		mobileServiceText.setLayoutData(data1);
		mobileServiceText.addModifyListener(this);

		mobileServiceCallComboRef = new ArrayList<String>();
		mobileServiceCallComboRef.add("");
		mobileServiceCallComboRef.add(Property.ACTION_MOBILE_SERVICE_CALL_TEL);
		mobileServiceCallComboRef.add(Property.ACTION_MOBILE_SERVICE_CALL_APP);
//		mobileServiceCallComboRef.add(Property.ACTION_MOBILE_SERVICE_CALL_PHOTO);
		mobileServiceCallComboRef.add(Property.ACTION_MOBILE_SERVICE_CALL_MESSAGE);
		mobileServiceCallComboRef.add(Property.ACTION_MOBILE_SERVICE_CALL_KAKAO);
		mobileServiceCallCombo.add("");
		mobileServiceCallCombo.add("전화걸기");
		mobileServiceCallCombo.add("프로그램 열기");
//		mobileServiceCallCombo.add("사진첩 열기");
		mobileServiceCallCombo.add("문자메세지 전송");
		mobileServiceCallCombo.add("카카오톡 메세지 전송");


	}

	@Override
	public void widgetSelected(SelectionEvent e) {
		// TODO Auto-generated method stub
		if(e.widget == delButton){ //삭제 버튼 

			StructuredSelection iSelection = (StructuredSelection)tableViewer.getSelection();
			Object obj = iSelection.getFirstElement();
			int selectIndex= tableViewer.getTable().getSelectionIndex();
			if (obj != null) {
				tableViewer.remove(obj);

				if(tableViewer.getTable().getItemCount()>=selectIndex+1){
					tableViewer.getTable().select(selectIndex);
				}else{
					tableViewer.getTable().select(tableViewer.getTable().getItemCount()-1);
				}

			}


			refresh();


		}else if(e.widget == moveLayoutButton){ //레이아웃 이동 버튼 

			if(moveLayoutButton.getSelection()){
				moveCombo.select(moveCombo.getSelectionIndex());
			}else{
				getSelectionActionItem().remove(Property.ACTION_MOVE_LAYOUT);
			}		
		}else if(e.widget == addButton){ // 추가 버튼 

			ActionItem actionItem = new ActionItem();
			tableViewer.add(actionItem);
			refresh();


		}else if(e.widget == alertMessageButton){
			if(!alertMessageButton.getSelection()){
				getSelectionActionItem().remove(Property.ACTION_ALERT);
			}
			refresh();
		}else if(e.widget == mobileServiceCallButton){
			if(!mobileServiceCallButton.getSelection()){
				getSelectionActionItem().remove(Property.ACTION_MOBILE_SERVICE_CALL);
			}
			refresh();
		}
	}

	@Override
	public void widgetDefaultSelected(SelectionEvent e) {
		// TODO Auto-generated method stub

	}
	//초기화 
	public void init(){

		moveLayoutButton.setSelection(false);
		moveCombo.select(-1);

		alertMessageText.setText("");
		alertTitleText.setText("");
		mobileServiceCallButton.setSelection(false);
		mobileServiceCallCombo.select(0);

		mobileServiceText.setText("");

		//		moveEffectComboRef.add("없음");
		//		moveEffectComboRef.add("없음");
	}

	public void refresh(){
		TableItem[] tableItem = tableViewer.getTable().getItems();
		ArrayList arrayList = new ArrayList();
		for (int i = 0; i < tableItem.length; i++) {
			arrayList.add(tableItem[i].getData());
		}
		this.umlModel.setPropertyValue(Property.ID_ACTION_ITEM, arrayList);
	}
	public void refresh(UMLModel umlModel){
		
		init();
		this.umlModel = umlModel;
		
		
		
//		Thread thread = new Thread(new Runnable() {
//			
//			@Override
//			public void run() {}
//		});
//		thread.run();
//		

		tableViewer.getTable().removeAll();
		
		if(umlModel.getPropertyValue(Property.ID_ACTION_ITEM)!=null)
			tableViewer.setInput(((ArrayList)umlModel.getPropertyValue(Property.ID_ACTION_ITEM)).toArray());// 모델에 해당 정보를 넣는다.
		else
			tableViewer.setInput(null);
		moveCombo.removeAll();


		moveComboRef = ProjectManager.getInstance().getModels(2);
//
		for(int i = 0; i < moveComboRef.size(); i ++){
			UMLModel model = moveComboRef.get(i);
			String name = moveComboRef.get(i).getName();
			String text =(String)model.getPropertyValue(Property.ID_TEXT);
			
			if(text!=null && !text.equals("")){
				name = name + "[" + text + "]";
			}
			
			moveCombo.add(name);		
		}
	
		
//
		attachLabelProvider(tableViewer);
		attachCellEditors(tableViewer,actionTable);






	}
	@Override
	public void setFocus() {

	}
	private void attachLabelProvider(TableViewer viewer) {
		viewer.setLabelProvider(
				new ITableLabelProvider() {
					public Image getColumnImage(Object element, int columnIndex) {
						return null;
					}
					public String getColumnText(Object element, int columnIndex) {

						switch (columnIndex) {
						case 0:
							Number index = ((ActionItem)element).getActionType();
							if(umlModel!=null && umlModel instanceof ViewModel){
								String str = umlModel.getActionBroserStringList()[index.intValue()];
								return str;
							}else{
								return "";
							}
						default:
							return "Invalid column: " + columnIndex;
						}
					}
					public void addListener(ILabelProviderListener listener) {
					}
					public void dispose() {
					}
					public boolean isLabelProperty(Object element, String property) {
						return false;
					}
					public void removeListener(ILabelProviderListener lpl) {
					}
				});
	}
	private void attachContentProvider(TableViewer viewer) {
		viewer.setContentProvider(
				new IStructuredContentProvider() {
					public Object[] getElements(Object inputElement) {
						return (Object[]) inputElement;
					}
					public void dispose() {
					}
					public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
					}
				});
	}
	private TableViewer buildAndLayoutTable(final Table table) {
		TableViewer tableViewer = new TableViewer(table);
		TableLayout layout = new TableLayout();
		layout.addColumnData(new ColumnWeightData(50, 75, true));

		TableColumn typeColumn = new TableColumn(table, SWT.CENTER);
		typeColumn.setText(TPYE_PROPERTY);


		table.setLayout(layout);
		table.setHeaderVisible(true);
		return tableViewer;
	}

	private void attachCellEditors(final TableViewer viewer, Composite parent) {
		viewer.setCellModifier(
				new ICellModifier() {
					public boolean canModify(Object element, String property) {
						return true;
					}
					public Object getValue(Object element, String property) {
						ActionItem ActionItem = (ActionItem)element;

						setSubProperty(ActionItem);
						if (TPYE_PROPERTY.equals(property))
							return ActionItem.getActionType();
						else
							return "";



					}
					public void modify(Object element, String property, Object value) {
						TableItem tableItem = (TableItem)element;
						ActionItem data = (ActionItem)tableItem.getData();

						if (TPYE_PROPERTY.equals(property)){
							data.setActionType((Integer)value);
							if(umlModel!=null && umlModel.getActionBroserList().size()>0){
								data.setOperation(umlModel.getActionBroserList().get((Integer)value));
							}
						}
						viewer.refresh(data);

						refresh();
					}
				});

		viewer.setCellEditors(
				new CellEditor[] {
						new ComboBoxCellEditor(parent,umlModel!=null&&(umlModel instanceof ViewModel)?umlModel.getActionBroserStringList():new String[0])

				});
		viewer.setColumnProperties(
				new String[] {
						TPYE_PROPERTY
				});
	}
	//테이블 선택하면 해당내용을 화면에 다시 뿌려주는 오퍼레이션 
	public void setSubProperty(ActionItem item){
		init();
		if(item==null)
			return;
		java.util.Iterator iterator =  item.getActionData().keySet().iterator();

		while(iterator.hasNext()){
			String key = (String)iterator.next();
			if(key.equals(Property.ACTION_MOVE_LAYOUT)){
				ActionMoveItem actionItem = (ActionMoveItem) item.getActionData().get(Property.ACTION_MOVE_LAYOUT);				
				moveCombo.select(getrefCombosIndex(moveComboRef,actionItem.getModelID()));
			}else if(key.equals(Property.ACTION_ALERT)){
				ActionAlertMessageItem messageModel = (ActionAlertMessageItem)item.getActionData().get(Property.ACTION_ALERT);
				alertTitleText.setText(messageModel.getTitle());
				alertMessageText.setText(messageModel.getMessage());
			}else if(key.equals(Property.ACTION_MOBILE_SERVICE_CALL)){
				ActionMobileServiceCallItem mobileServiceCallItem = (ActionMobileServiceCallItem)item.getActionData().get(Property.ACTION_MOBILE_SERVICE_CALL);
				mobileServiceCallCombo.select(getrefCombosIndex(mobileServiceCallComboRef,mobileServiceCallItem.getId()));
				mobileServiceText.setText(mobileServiceCallItem.getValue());
			}
		}
	}

	public ActionItem getSelectionActionItem(){
		StructuredSelection selection = (StructuredSelection)tableViewer.getSelection();
		if(selection.getFirstElement()!=null){
			ActionItem actionItem = (ActionItem)selection.getFirstElement();
			return actionItem;
		}else 
			return null;
	}
	public int getrefCombosIndex(ArrayList list,String id){
		if(list==moveComboRef){
			for(int i = 0 ; i < moveComboRef.size(); i ++){

				if(moveComboRef.get(i).getID().equals(id))
					return i;
			}
		}else if(list==mobileServiceCallComboRef){
			for(int i = 0 ; i < mobileServiceCallComboRef.size(); i ++){

				if(mobileServiceCallComboRef.get(i).equals(id))
					return i;
			}
			
		}
		return -1;
	}

	@Override
	public void modifyText(ModifyEvent e) {
		alertMessageButton.setSelection(false);
		moveLayoutButton.setSelection(false);

		if(getSelectionActionItem()!=null){
			//			getSelectionActionItem().getActionData().clear();

			//액션 이동 내용이 존재할 경우 저장 
			if(moveCombo.getSelectionIndex()>=0){
				if(getSelectionActionItem()!=null){
					getSelectionActionItem().put(Property.ACTION_MOVE_LAYOUT, new ActionMoveItem(moveComboRef.get(moveCombo.getSelectionIndex()).getID()));
					moveLayoutButton.setSelection(true);
				}
			}
			if(!alertTitleText.getText().equals("")&&!alertMessageText.getText().equals("")){
				ActionAlertMessageItem alertModel = new ActionAlertMessageItem(alertTitleText.getText(), alertMessageText.getText());
				System.out.println(alertModel);
				getSelectionActionItem().put(Property.ACTION_ALERT,alertModel);
				alertMessageButton.setSelection(true);
			}
			if(mobileServiceCallCombo.getSelectionIndex()>=1){
				if(getSelectionActionItem()!=null){
					ActionMobileServiceCallItem actionMobileServiceCallItem = new ActionMobileServiceCallItem(mobileServiceCallComboRef.get(mobileServiceCallCombo.getSelectionIndex()),mobileServiceText.getText());
					getSelectionActionItem().put(Property.ACTION_MOBILE_SERVICE_CALL, actionMobileServiceCallItem);
					mobileServiceCallButton.setSelection(true);
				}
			}

		}


	}


}



