package org.pky.uml.dialog;

import java.io.File;
import java.util.ArrayList;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.ICellModifier;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TableLayout;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Text;
import org.pky.uml.commons.managers.EmulatorManager;
import org.pky.uml.commons.managers.ProjectManager;
import org.pky.uml.model.AndroidDeviceModel;



public class AndroidDevicesDialog extends TitleAreaDialog implements SelectionListener{

	private static final String AVD_NAME_PROPERTY = "AVD";
	private static final String ANDROID_VER_PROPERTY = "Android Ver";

	Composite comp = null;
	TableViewer emulatorViewer = null;
	Table emulatorTable;

	Combo androidDevicesCombo;
	Button avdManagerButton;
	Button avdStartButton;
	Button deviceButton;
	Button emulatorButton;
	Button dirButton;


	Text apkPathText;

	public AndroidDevicesDialog(Shell parentShell) {
		super(parentShell);

	}


	protected Control createDialogArea(Composite parent) {

		setTitle("안드로이드 앱 실행");
		setMessage("여러분이 만드신 앱을 이제 구동해봅시다!!! 야호!");

		comp = new Composite((Composite)super.createDialogArea(parent),SWT.NONE);
		comp.setLayout(new GridLayout(3, false));
		comp.setLayoutData(new GridData(GridData.FILL_BOTH));

		GridData data = new GridData(GridData.FILL_HORIZONTAL);
		data.horizontalSpan = 1;

		new Label(comp, SWT.NONE).setText("APK 파일위치");
		apkPathText = new Text(comp, SWT.BORDER);
		apkPathText.setLayoutData(data);
		dirButton = new Button(comp, SWT.PUSH);
		dirButton.addSelectionListener(this);

		data = new GridData(GridData.FILL);
		data.horizontalSpan = 1;

		deviceButton = new Button(comp, SWT.RADIO);
		deviceButton.setText("안드로이드 모바일 장치로 실행 ");
		deviceButton.setLayoutData(data);
		deviceButton.addSelectionListener(this);

		data = new GridData(GridData.FILL_HORIZONTAL);
		data.horizontalSpan = 2;

		androidDevicesCombo = new Combo(comp, SWT.NONE);
		androidDevicesCombo.setLayoutData(data);

		ArrayList<AndroidDeviceModel> list = EmulatorManager.getInstance().getAndroidDevices();
		for(int i = 0 ; i< list.size(); i ++){
			androidDevicesCombo.add(list.get(i).getAvdName());
		}
		if(list.size()>0)
			androidDevicesCombo.select(0);

		emulatorButton = new Button(comp, SWT.RADIO);
		emulatorButton.setText("에뮬레이터(가상)에서 실행");
		emulatorButton.setLayoutData(data);
		emulatorButton.addSelectionListener(this);
		

		emulatorTable = new Table(comp, SWT.BORDER);
		emulatorViewer = buildAndLayoutTable(emulatorTable);
		attachContentProvider(emulatorViewer);
		attachLabelProvider(emulatorViewer);
		attachCellEditors(emulatorViewer, emulatorTable);

		emulatorViewer.setInput(EmulatorManager.getInstance().getAndroidEmulators());

		data = new GridData(GridData.FILL_HORIZONTAL);
		data.horizontalSpan = 3;
		data.heightHint = 60;
		emulatorTable.setLayoutData(data);
		
		emulatorViewer.addSelectionChangedListener(new ISelectionChangedListener() {
			
			@Override
			public void selectionChanged(SelectionChangedEvent event) {
				emulatorButton.setSelection(true);
				deviceButton.setSelection(false);
				
			}
		});

		data = new GridData(GridData.FILL_HORIZONTAL);
		data.horizontalSpan = 3;

		Composite buttonComp = new Composite(comp, SWT.NONE);

		buttonComp.setLayout(new GridLayout(3, false));
		buttonComp.setLayoutData(data);

		avdStartButton = new Button(buttonComp, SWT.PUSH);
		avdStartButton.setText("AVD 실행");
		avdStartButton.addSelectionListener(this);

		avdManagerButton = new Button(buttonComp, SWT.PUSH);
		avdManagerButton.setText("AVD 설정");
		data = new GridData(GridData.HORIZONTAL_ALIGN_END);

		avdManagerButton.addSelectionListener(this);

		//기본설정 
		if(list.size()>0)
			deviceButton.setSelection(true);
		else
			emulatorButton.setSelection(true);
		apkPathText.setText(ProjectManager.getInstance().getAndroidAPK());
		return super.createDialogArea(parent);
	}
	protected Button createButton(Composite parent, int id, String label,
			boolean defaultButton) {
		if(label.equals("OK")){
			label = "실행";
		}

		return super.createButton(parent, id, label, defaultButton);


	}
	@Override
	public boolean close() {
		// TODO Auto-generated method stub
		ProjectManager.getInstance().getTempData().remove("AndroidDevicesDialog_apkPathText");
		ProjectManager.getInstance().getTempData().remove("AndroidDevicesDialog_avdName");
	
		return super.close();
	}
	@Override
	public void closeTray() throws IllegalStateException {
		ProjectManager.getInstance().getTempData().remove("AndroidDevicesDialog_apkPathText");
		ProjectManager.getInstance().getTempData().remove("AndroidDevicesDialog_avdName");
	
		super.closeTray();
	}
	@Override
	protected void cancelPressed() {
		ProjectManager.getInstance().getTempData().remove("AndroidDevicesDialog_apkPathText");
		ProjectManager.getInstance().getTempData().remove("AndroidDevicesDialog_avdName");
		
		super.cancelPressed();
	}
	@Override
	protected void okPressed() {
		
		
		
		File apkFile = new File(apkPathText.getText());
		if(!apkFile.exists()){
			ProjectManager.getInstance().showMessage(MessageDialog.WARNING,"안내" ,"APK 파일이 존재하지 않습니다." );
			return;
		}
			
		
		if(emulatorButton.getSelection()){
			
			if(getSelection()==null){
				ProjectManager.getInstance().showMessage(MessageDialog.WARNING,"안내" ,"선택된 AVD 가 없습니다. 선택 후 진행해주세요." );
				return;
			}
			
			
			ProjectManager.getInstance().putTempData("AndroidDevicesDialog_apkPathText", apkPathText.getText());
			ProjectManager.getInstance().putTempData("AndroidDevicesDialog_avdName", getSelection().getAvdName());
			EmulatorManager.getInstance().openAndroidEmulator(getSelection().getAvdName(),apkPathText.getText());
			
			
			
		}else{
			EmulatorManager.getInstance().androidAppInstall(androidDevicesCombo.getText(), apkPathText.getText());
			
			
		}



		super.okPressed();
	}
	private void attachLabelProvider(TableViewer viewer) {
		viewer.setLabelProvider(
				new ITableLabelProvider() {
					public Image getColumnImage(Object element, int columnIndex) {
						return null;
					}
					public String getColumnText(Object element, int columnIndex) {
						AndroidDeviceModel model = (AndroidDeviceModel)element;
						switch (columnIndex) {
						case 0:

							return model.getAvdName();
						case 1:
							return model.getSdkVer();

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
	private void attachCellEditors(final TableViewer viewer, Composite parent) {
		viewer.setCellModifier(
				new ICellModifier() {
					public boolean canModify(Object element, String property) {
						return false;
					}
					public Object getValue(Object element, String property) {
						AndroidDeviceModel deviceModel = (AndroidDeviceModel)element;


						if (AVD_NAME_PROPERTY.equals(property))
							return deviceModel.getAvdName();
						else if (AVD_NAME_PROPERTY.equals(property))
							return deviceModel.getSdkVer();
						else
							return "";



					}
					public void modify(Object element, String property, Object value) {



					}
				});

		viewer.setCellEditors(
				new CellEditor[] {
						new TextCellEditor(parent),new TextCellEditor(parent)

				});
		viewer.setColumnProperties(
				new String[] {
						AVD_NAME_PROPERTY,ANDROID_VER_PROPERTY
				});
	}
	private void attachContentProvider(TableViewer viewer) {
		viewer.setContentProvider(
				new IStructuredContentProvider() {
					public Object[] getElements(Object inputElement) {
						return (Object[]) ((ArrayList)inputElement).toArray();
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
		layout.addColumnData(new ColumnWeightData(50, 75, true));

		TableColumn avdNameColumn = new TableColumn(table, SWT.CENTER);
		avdNameColumn.setText(AVD_NAME_PROPERTY);

		TableColumn sdkVerColumn = new TableColumn(table, SWT.CENTER);
		sdkVerColumn.setText(ANDROID_VER_PROPERTY);


		table.setLayout(layout);
		table.setHeaderVisible(true);
		return tableViewer;
	}
	public AndroidDeviceModel getSelection(){
		StructuredSelection selection = (StructuredSelection)emulatorViewer.getSelection();
		if(selection.getFirstElement()!=null){
			AndroidDeviceModel actionItem = (AndroidDeviceModel)selection.getFirstElement();
			return actionItem;
		}else 
			return null;
	}

	@Override
	public void widgetSelected(SelectionEvent e) {
		if(e.widget == avdManagerButton){
			EmulatorManager.getInstance().openAVDManager();
		}else if(e.widget == avdStartButton){
			if(getSelection()!=null){
				EmulatorManager.getInstance().openAndroidEmulator(getSelection().getAvdName(),null);
			}
		}else if(e.widget == dirButton){
			FileDialog dialog = new FileDialog(comp.getShell(),SWT.OPEN);

			dialog.setFilterExtensions(new String[]{"*.apk"});

			apkPathText.setText(dialog.open());
		}else if(e.widget == deviceButton){
			emulatorTable.deselectAll();
		}

	}


	@Override
	public void widgetDefaultSelected(SelectionEvent e) {
		// TODO Auto-generated method stub

	}

}
