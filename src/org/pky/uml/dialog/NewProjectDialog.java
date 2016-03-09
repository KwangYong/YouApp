package org.pky.uml.dialog;

import java.io.File;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StackLayout;
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
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.TreeItem;
import org.pky.uml.commons.managers.LayoutManager;
import org.pky.uml.commons.managers.ModelBrowserManager;
import org.pky.uml.commons.managers.ProjectManager;
import org.pky.uml.model.command.TreeObject;
import org.pky.uml.model.command.TreeParent;

public class NewProjectDialog extends TitleAreaDialog {

	StackLayout statckLayout;
	Control[] controls;
	final int BUTTON_MAX = 5;
	int[] index = new int[1];

	// Step 1
	Composite comp;

	Text projectNameText;
	Text projectPath;
	//Android
	Text androidPath;	
	Text androidAppName;
	Text androidPackageName;

	Button chkAndroid;
	Button androidDirectoryButton;	
	Combo androidMinimumSdk;
	Combo androidTargetSdk;
	Combo androidTheme;

	Image androidIconImg;

	//Ios
	Text iosPath;
	Text iosAppName;
	Button chkIos;
	Button iosDirectoryButton;




	TreeViewer androidDiagramTree;
	String androidLayoutPath = "/res/layout";


	public NewProjectDialog(Shell parentShell) {
		super(parentShell);


	}

	@Override
	protected Control createDialogArea(Composite parent) {
		comp = (Composite)super.createDialogArea(parent);

		statckLayout = new StackLayout();
		comp.setLayout(statckLayout);
		controls = new Control[BUTTON_MAX];

		setTitle("새 프로젝트 작성");
		setMessage("새 프로젝트의 이름과 경로를 작성하여 주시기 바랍니다.");

		controls = new Control[BUTTON_MAX];

		for(int i=0;i<controls.length;i++){
			if((i % 5) ==0){


				final Composite container1 = new Composite(comp,SWT.None);
				container1.setLayout(new GridLayout(3, false));
				container1.setLayoutData(new GridData(GridData.FILL_BOTH));


				Label projectNameLabel = new Label(container1, SWT.NONE);
				projectNameLabel.setText("프로젝트 이름");
				GridData gridData = new GridData(SWT.FILL,SWT.CENTER,false,false,1,1);
				projectNameLabel.setLayoutData(gridData);

				projectNameText = new Text(container1, SWT.BORDER);
				gridData = new GridData(SWT.FILL,SWT.CENTER,true,false,2,1);
				projectNameText.setLayoutData(gridData);

				Label projectPathLabel = new Label(container1, SWT.NONE);
				projectPathLabel.setText("저장 경로");
				gridData = new GridData(SWT.FILL,SWT.CENTER,false,false,1,1);
				projectPathLabel.setLayoutData(gridData);

				projectPath = new Text(container1, SWT.BORDER);
				gridData = new GridData(SWT.FILL,SWT.CENTER,true,false,1,1);
				projectPath.setLayoutData(gridData);

				Button directoryButton = new Button(container1,  SWT.NONE);
				directoryButton.setText("Brower");
				directoryButton.addSelectionListener(new SelectionListener() {
					public void widgetSelected(SelectionEvent e) {
						DirectoryDialog dialog = new DirectoryDialog(container1.getShell());
						projectPath.setText(dialog.open());
					}
					public void widgetDefaultSelected(SelectionEvent e) {
					}
				});

				controls[i] = container1;
			}else if((i % 5) ==1){



				final Composite container2 = new Composite(comp,SWT.None);

				container2.setLayout(new GridLayout(3, false));
				container2.setLayoutData(new GridData(GridData.FILL_BOTH));



				chkAndroid = new Button(container2, SWT.CHECK);
				GridData gridData = new GridData(SWT.FILL,SWT.LEFT,true,false,3,1);
				chkAndroid.setLayoutData(gridData);

				chkAndroid.setText("안드로이드(Android)");


				Label androidSavePathLeble = new Label(container2, SWT.NONE);
				androidSavePathLeble.setText("저장 경로");

				androidPath = new Text(container2, SWT.BORDER);
				gridData = new GridData(SWT.FILL,SWT.CENTER,true,false,2,1);
				androidPath.setLayoutData(gridData);
				androidPath.setEnabled(false);				

				Label androidProjectName = new Label(container2, SWT.NONE);
				androidProjectName.setText("앱 이름");
				androidAppName = new Text(container2, SWT.BORDER); 
				gridData = new GridData(SWT.FILL,SWT.CENTER,true,false,2,1);
				androidAppName.setLayoutData(gridData);

				Label androidPackageLabel = new Label(container2, SWT.NONE);
				androidPackageLabel.setText("패키지");
				androidPackageName = new Text(container2, SWT.BORDER); 
				gridData = new GridData(SWT.FILL,SWT.CENTER,true,false,2,1);
				androidPackageName.setLayoutData(gridData);

				Label androidMinVerLebel = new Label(container2, SWT.NONE);
				androidMinVerLebel.setText("최소 실행 버전");

				androidMinimumSdk = new Combo(container2, SWT.SELECTED);
				gridData = new GridData(SWT.FILL,SWT.CENTER,true,false,2,1);
				androidMinimumSdk.setLayoutData(gridData);
				androidMinimumSdk.setItems(ProjectManager.getInstance().getAndroidSDK());
				androidMinimumSdk.select(0);


				Label androidTargetVerLebel = new Label(container2, SWT.NONE);
				androidTargetVerLebel.setText("실행 버전");
				androidTargetSdk = new Combo(container2, SWT.SELECTED);
				gridData = new GridData(SWT.FILL,SWT.CENTER,true,false,2,1);
				androidTargetSdk.setLayoutData(gridData);
				androidTargetSdk.setItems(ProjectManager.getInstance().getAndroidSDK());
				androidTargetSdk.select(0);

				Label androidThemeLeble = new Label(container2, SWT.NONE);
				androidThemeLeble.setText("스타일");


				androidTheme = new Combo(container2, SWT.SELECTED);
				gridData = new GridData(SWT.FILL,SWT.CENTER,true,false,2,1);


				chkAndroid.addSelectionListener(new SelectionListener() {

					@Override
					public void widgetSelected(SelectionEvent e) {
						// TODO Auto-generated method stub

					}

					@Override
					public void widgetDefaultSelected(SelectionEvent e) {

						boolean isChk = chkAndroid.getSelection();
						androidAppName.setEnabled(isChk);
						androidMinimumSdk.setEnabled(isChk);

					}
				});
				androidAppName.addModifyListener(new ModifyListener() {

					@Override
					public void modifyText(ModifyEvent e) {
						if(chkAndroid.getSelection()){
							androidPath.setText(projectPath.getText() + LayoutManager.SEPARATOR + projectNameText.getText() + LayoutManager.SEPARATOR + "Android"+LayoutManager.SEPARATOR+androidAppName.getText());
							androidPackageName.setText("org"+LayoutManager.DOT_WORLD+projectNameText.getText()+LayoutManager.DOT_WORLD+androidAppName.getText());
						}

					}
				});
				chkAndroid.setSelection(true);


				controls[i] = container2;

			}else if((i % 5) ==2){
				final Composite container3 = new Composite(comp,SWT.None);

				container3.setLayout(new GridLayout(3, false));
				container3.setLayoutData(new GridData(GridData.FILL_BOTH));



				chkIos = new Button(container3, SWT.CHECK);
				GridData gridData = new GridData(SWT.FILL,SWT.LEFT,true,false,3,1);
				chkIos.setLayoutData(gridData);

				chkIos.setText("아이폰/아이패드(Ios)");


				Label iosSavePathLabel = new Label(container3, SWT.NONE);
				iosSavePathLabel.setText("저장 경로");

				iosPath = new Text(container3, SWT.BORDER);
				gridData = new GridData(SWT.FILL,SWT.CENTER,true,false,2,1);
				iosPath.setLayoutData(gridData);
				iosPath.setEnabled(false);

				Label iosProjectName = new Label(container3, SWT.NONE);
				iosProjectName.setText("앱 이름");
				iosAppName = new Text(container3, SWT.BORDER); 
				gridData = new GridData(SWT.FILL,SWT.CENTER,true,false,2,1);
				iosAppName.setLayoutData(gridData);


				chkIos.addSelectionListener(new SelectionListener() {

					@Override
					public void widgetSelected(SelectionEvent e) {
						// TODO Auto-generated method stub

					}

					@Override
					public void widgetDefaultSelected(SelectionEvent e) {

						boolean isChk = chkIos.getSelection();
						iosAppName.setEnabled(isChk);

					}
				});
				chkIos.setSelection(true);

				controls[i] = container3;

			}else if((i % 5) ==3){
				final Composite container4 = new Composite(comp,SWT.None);

				container4.setLayout(new GridLayout(3, false));
				container4.setLayoutData(new GridData(GridData.FILL_BOTH));



				Label iosProjectName = new Label(container4, SWT.NONE);
				iosProjectName.setText("이제 개발을 시작해보실까요?");


				controls[i] = container4;

			}else if((i % 5) ==4){
				final Composite container5 = new Composite(comp,SWT.None);

				controls[i] = container5;

			}else if((i % 5) ==5){
				final Composite container6 = new Composite(comp,SWT.None);

				controls[i] = container6;

			}
			statckLayout.topControl = controls[0];

		}
		projectPath.setText(ProjectManager.getInstance().getProjectPath().equals("")?"":ProjectManager.getInstance().getProjectPath().substring(0, ProjectManager.getInstance().getProjectPath().lastIndexOf(LayoutManager.SEPARATOR)));
		projectNameText.setText(ProjectManager.getInstance().getProjectPath().equals("")?"":ProjectManager.getInstance().getProjectPath().substring(ProjectManager.getInstance().getProjectPath().lastIndexOf(LayoutManager.SEPARATOR)+1, ProjectManager.getInstance().getProjectPath().length()));
		androidAppName.setText(ProjectManager.getInstance().getAndroidAppName());
		androidPackageName.setText(ProjectManager.getInstance().getAndroidPackage());
		androidMinimumSdk.select(ProjectManager.getInstance().getAndroid_MinSdkVersion());
		androidTargetSdk.select(ProjectManager.getInstance().getAndroid_TargetSdkVersion());


		return comp;
	}



	private TreeParent initialize(File f) {
		TreeParent root = new TreeParent(f.getPath());
		File[] list =f.listFiles();
		for(int i = 0; i < list.length; i ++){
			if(list[i].getName().endsWith(".xml")){
				root.addChild(new TreeObject(list[i].getName(), list[i].getPath()));
			}
		}
		return root;
	}




	protected void buttonPressed(int buttonId) {



		try{
			if (buttonId == IDialogConstants.BACK_ID) {    
				if(index[0]-1>-1){
					index[0] = (index[0] - 1) % BUTTON_MAX;
					statckLayout.topControl = controls[index[0]];

					comp.layout();


				}   
			}
			else if(buttonId == IDialogConstants.NEXT_ID) {    

				if((index[0] + 1) != BUTTON_MAX){

					androidPath.setText(projectPath.getText() + LayoutManager.SEPARATOR + projectNameText.getText() + LayoutManager.SEPARATOR + "Android"+LayoutManager.SEPARATOR+androidAppName.getText());
					androidPackageName.setText("org"+LayoutManager.DOT_WORLD+projectNameText.getText()+LayoutManager.DOT_WORLD+androidAppName.getText());

					iosPath.setText(projectPath.getText() + LayoutManager.SEPARATOR + projectNameText.getText() + LayoutManager.SEPARATOR + "IOS"+LayoutManager.SEPARATOR+iosAppName.getText());

					if(!checkPkValue(index[0]))
						return;
					index[0] = (index[0] + 1) % BUTTON_MAX;

					statckLayout.topControl = controls[index[0]];

					comp.layout();

					//Android Layout Import 
					if(index[0] == 1){



					}
				}   

			}
			else if(buttonId == IDialogConstants.FINISH_ID) {


				ProjectManager.getInstance().init();

				ModelBrowserManager.getInstance().getModelBrowser().getRoot().setName(projectNameText.getText());

				ProjectManager.getInstance().setProjectPath(projectPath.getText()+LayoutManager.SEPARATOR+projectNameText.getText());

				if(chkAndroid.getSelection()){

					ProjectManager.getInstance().setMakeAndroid(chkAndroid.getSelection());

					ProjectManager.getInstance().setAndroidAppName(androidAppName.getText());
					ProjectManager.getInstance().setAndroidPackage(androidPackageName.getText());
					ProjectManager.getInstance().setAndroidPath(androidPath.getText());
					ProjectManager.getInstance().setAndroid_MinSdkVersion(ProjectManager.getInstance().getAndroidSDKList().get(androidMinimumSdk.getSelectionIndex()).id);
					ProjectManager.getInstance().setAndroid_TargetSdkVersion(ProjectManager.getInstance().getAndroidSDKList().get(androidTargetSdk.getSelectionIndex()).id);
					LayoutManager.getInstance().initAndroidFiles(androidPath.getText());

				}
				if(chkIos.getSelection()){
					ProjectManager.getInstance().setMakeIos(chkIos.getSelection());
					ProjectManager.getInstance().setIosPath(iosPath.getText());
					ProjectManager.getInstance().setIosAppName(iosAppName.getText());
				}else{
					ProjectManager.getInstance().setIosPath("");
					ProjectManager.getInstance().setIosAppName("");
				}







				ModelBrowserManager.getInstance().getModelBrowser().getViewer().refresh();

				this.close();
			}
			if(buttonId != IDialogConstants.FINISH_ID)
				initPage(index[0]);
			super.buttonPressed(buttonId);
		}
		catch(Exception e){
			e.printStackTrace();
		}  

	}

	public void initPage(int index){

		Button finish = getButton(IDialogConstants.FINISH_ID);
		Button back = getButton(IDialogConstants.BACK_ID);
		Button next = getButton(IDialogConstants.NEXT_ID);		

		if(index==0){
			setTitle("새 프로젝트 작성");
			setMessage("새 프로젝트의 이름과 경로를 작성하여 주세요.");
			finish.setEnabled(false);
			back.setEnabled(false);
			next.setEnabled(true);
		}else if(index==1){
			setTitle("안드로이드 설정");
			setMessage("안드로이드의 내용을 설정하여 주세요.");		
			if(androidPath.getText().equals(""))
				androidPath.setText(projectPath.getText()+LayoutManager.SEPARATOR+projectNameText.getText()+LayoutManager.SEPARATOR+"android");
			if(androidAppName.getText().equals(""))
				androidAppName.setText(projectNameText.getText());


			back.setEnabled(true);
			next.setEnabled(true);
		}else if(index==2){
			setTitle("아이폰/아이패드 설정");
			setMessage("아이폰/아이패드의 내용을 설정하여 주세요.");
			back.setEnabled(true);
			next.setEnabled(true);
			if(iosAppName.getText().equals(""))
				iosAppName.setText(projectNameText.getText());


		}else if(index==3){
			setTitle("완료");
			setMessage("감사합니다. Finish 버튼을 누루면 완료됩니다.");
			finish.setEnabled(true);
			back.setEnabled(true);
			next.setEnabled(false);
		}else if(index==4){
			finish.setEnabled(true);
			back.setEnabled(true);
			next.setEnabled(false);
		}

	}

	public boolean checkPkValue(int index){
		if(index==0){
			if(projectNameText.getText().equals("")){
				ProjectManager.getInstance().showMessage(MessageDialog.ERROR, "필수값 오류", "프로젝트 이름을 작성하지 않으셨습니다.");
				return false;
			}
			if(projectPath.getText().equals("")){
				ProjectManager.getInstance().showMessage(MessageDialog.ERROR, "필수값 오류", "프로젝트 경로를 작성하지 않으셨습니다.");
				return false;
			}else{
				File f = new File(projectPath.getText());
				if(!f.exists()){
					ProjectManager.getInstance().showMessage(MessageDialog.ERROR, "필수값 오류", "프로젝트 경로가 존재하지 않습니다.");
					return false;
				}

			}

		}else if(index==1){

		}else if(index==2){

		}else if(index==3){

		}else if(index==4){

		}
		return true;
	}


	public void searchTree(TreeItem tree){

		if(tree.getChecked()){
			TreeObject treeModel = (TreeObject)tree.getData();
			LayoutManager.getInstance().androidLoad(treeModel.getPath());

		}

	}

	protected void createButtonsForButtonBar(Composite parent) {
		//		createButton(parent, IDialogConstants.OK_ID, IDialogConstants.OK_LABEL, true);  
		Button back = createButton(parent, IDialogConstants.BACK_ID, IDialogConstants.BACK_LABEL, false);
		back.setEnabled(false);
		createButton(parent, IDialogConstants.NEXT_ID, IDialogConstants.NEXT_LABEL, false);
		Button finish = createButton(parent, IDialogConstants.FINISH_ID, IDialogConstants.FINISH_LABEL, true);
		finish.setEnabled(false);
		createButton(parent, IDialogConstants.CANCEL_ID, IDialogConstants.CANCEL_LABEL, false);
	}
}





