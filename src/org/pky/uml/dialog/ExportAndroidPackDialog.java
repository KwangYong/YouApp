package org.pky.uml.dialog;

import java.io.File;
import java.util.ArrayList;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.pky.uml.browser.common.propertybrowser.Property;
import org.pky.uml.commons.managers.LayoutManager;
import org.pky.uml.commons.managers.ModelDataManager;
import org.pky.uml.commons.managers.ProjectManager;
import org.pky.uml.model.common.UMLModel;



public class ExportAndroidPackDialog extends TitleAreaDialog implements FocusListener{

	Composite comp  = null;

	Label descLabel;
	Button keysotreLoadButton;
	Button keysotreNewButton;
	Text keyText;
	Text passwordText;
	Text passwordCheckText;
	Text keystorePathText;
	Text validtyText;
	Text nameText;
	Text groupLevelText;
	Text groupNameText;
	Text cityText;
	Text stateText;
	Text countryCodeText;
	Text androidExtText;
	Text androidIconText;


	public ExportAndroidPackDialog(Shell parentShell) {
		super(parentShell);
		// TODO Auto-generated constructor stub
	}
	protected Control createDialogArea(Composite parent) {

		//String alias,String validity,String path,String[] infoData
		//		test[1] = "123456";// 비밀번호 
		//		test[2] = "123456";//비밀번호 
		//		test[3] = "1\n";//이름 성 
		//		test[4] = "1\n";//조직단위 
		//		test[5] = "1\n";//조직이름 
		//		test[6] = "1\n";//시군시
		//		test[7] = "1\n";//시도이름  
		//		test[8] = "82\n";//국가코
		//		test[9] = "y\n";//확인여부
		//		test[10] = "123456\n";//패스워드
		//		test[11] = "123456\n";//패스워드 
		//		test[12] = "exit \n";


		GridData grid = new GridData(GridData.FILL_BOTH,SWT.CENTER,true,true,1,1);
		grid.minimumWidth = 365;

		GridData separatorGrid = new GridData(GridData.FILL_HORIZONTAL);
		separatorGrid.horizontalSpan = 3;

		comp = (Composite)super.createDialogArea(parent);


		Composite finalComp = new Composite(comp, SWT.NONE);
		finalComp.setLayout(new GridLayout(3, false));
		finalComp.setLayoutData(new GridData(GridData.FILL_BOTH));

		setTitle("안드로이드 파일생성");
		setMessage("안드로이드 APK를 생성합니다.");

		Label isKeyLabel = new Label(finalComp,SWT.NONE);
		isKeyLabel.setText("* KeyStore생성여부");
		//		isKeyLabel.setLayoutData(new GridData(GridData.BEGINNING,SWT.TOP,false,false,1,2));

		keysotreLoadButton = new Button(finalComp, SWT.RADIO);
		keysotreLoadButton.setText("기존Key파일");
		keysotreLoadButton.setLayoutData(new GridData(GridData.GRAB_VERTICAL,SWT.CENTER,false,false,2,1));
		keysotreLoadButton.addFocusListener(this);

		keysotreLoadButton.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {


				validtyText.setEnabled(false);

				nameText.setEnabled(false);

				groupLevelText.setEnabled(false);

				groupNameText.setEnabled(false);

				cityText.setEnabled(false);

				stateText.setEnabled(false);

				countryCodeText.setEnabled(false);

			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				// TODO Auto-generated method stub

			}
		});

		new Label(finalComp, SWT.NONE);

		keysotreNewButton = new Button(finalComp, SWT.RADIO);
		keysotreNewButton.setText("신규Key파일생성");
		keysotreNewButton.setLayoutData(new GridData(GridData.GRAB_VERTICAL,SWT.CENTER,false,false,2,1));		
		keysotreNewButton.setSelection(true);
		keysotreNewButton.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				// TODO Auto-generated method stub

				validtyText.setEnabled(true);

				nameText.setEnabled(true);

				groupLevelText.setEnabled(true);

				groupNameText.setEnabled(true);

				cityText.setEnabled(true);

				stateText.setEnabled(true);

				countryCodeText.setEnabled(true);
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				// TODO Auto-generated method stub

			}
		});

		Label filePathLabel = new Label(finalComp, SWT.NONE);
		filePathLabel.setText("* KeyStore 파일 위치");

		keystorePathText = new Text(finalComp, SWT.BORDER);
		keystorePathText.setLayoutData(grid);
		keystorePathText.addFocusListener(this);

		grid = new GridData(GridData.FILL_BOTH,SWT.CENTER,true,true,2,1);
		grid.minimumWidth = 400;


		Button dirButton = new Button(finalComp, SWT.PUSH);
		dirButton.setText("선택");

		dirButton.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {

				FileDialog dialog = null;
				
				if(keysotreNewButton.getSelection()){
					dialog = new FileDialog(ProjectManager.getInstance().window.getShell(),SWT.SAVE);
					dialog.setFilterPath("/");
					dialog.setFilterExtensions(new String[]{"*.keystore"});
				}else{
					dialog = new FileDialog(ProjectManager.getInstance().window.getShell(),SWT.OPEN);
					dialog.setFilterPath("/");
					dialog.setFilterExtensions(new String[]{"*.keystore"});
				}
				String path = dialog.open();
				
				
				keystorePathText.setText(path);

			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				// TODO Auto-generated method stub

			}
		});

		new Label(finalComp, SWT.NONE).setText("* 키이름");


		keyText = new Text(finalComp,SWT.BORDER);
		keyText.setLayoutData(grid);
		keyText.addFocusListener(this);

		new Label(finalComp, SWT.NONE).setText("* 패스워드");
		passwordText = new Text(finalComp,SWT.PASSWORD|SWT.BORDER);
		passwordText.setLayoutData(grid);
		passwordText.addFocusListener(this);

		new Label(finalComp, SWT.NONE).setText("* 패스워드 확인");
		passwordCheckText = new Text(finalComp,SWT.PASSWORD|SWT.BORDER);
		passwordCheckText.setLayoutData(grid); 
		passwordCheckText.addFocusListener(this);


		new Label(finalComp, SWT.NONE).setText("* 유효기간(년)");
		validtyText = new Text(finalComp,SWT.BORDER);
		validtyText.setLayoutData(grid);
		validtyText.addFocusListener(this);

		new Label(finalComp, SWT.NONE).setText("* 생성자 이름");
		nameText = new Text(finalComp, SWT.BORDER);
		nameText.setLayoutData(grid);
		nameText.addFocusListener(this);

		Label pkSeparator = new Label(finalComp, SWT.HORIZONTAL
				| SWT.SEPARATOR);
		pkSeparator.setLayoutData(separatorGrid);

		new Label(finalComp, SWT.NONE).setText("조직단위");
		groupLevelText = new Text(finalComp, SWT.BORDER);
		groupLevelText.setLayoutData(grid);
		groupLevelText.addFocusListener(this);

		new Label(finalComp, SWT.NONE).setText("조직이름");
		groupNameText = new Text(finalComp, SWT.BORDER);
		groupNameText.setLayoutData(grid);
		groupNameText.addFocusListener(this);

		new Label(finalComp, SWT.NONE).setText("주소 시/도");
		cityText = new Text(finalComp, SWT.BORDER);
		cityText.setLayoutData(grid);
		cityText.addFocusListener(this);

		new Label(finalComp, SWT.NONE).setText("주소 구/동");
		stateText = new Text(finalComp, SWT.BORDER);
		stateText.setLayoutData(grid);
		stateText.addFocusListener(this);

		grid = new GridData(GridData.FILL_BOTH,SWT.CENTER,true,true,2,1);

		new Label(finalComp, SWT.NONE).setText("국가코드(XX)");
		countryCodeText = new Text(finalComp, SWT.BORDER);
		countryCodeText.setLayoutData(grid);
		countryCodeText.setText("82");
		countryCodeText.addFocusListener(this);
		Label appPropertySeparator = new Label(finalComp, SWT.HORIZONTAL
				| SWT.SEPARATOR);
		appPropertySeparator.setLayoutData(separatorGrid);

		grid = new GridData(GridData.FILL_BOTH,SWT.CENTER,true,true,1,1);
		grid.minimumWidth = 365;
		
		new Label(finalComp, SWT.NONE).setText("안드로이드 앱 Icon");
		
		androidIconText= new Text(finalComp, SWT.BORDER);
		androidIconText.setLayoutData(grid);
		androidIconText.addFocusListener(this);
		Button iconPathButton = new Button(finalComp, SWT.PUSH);
		iconPathButton.setText("선택");
		iconPathButton.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {

				FileDialog dialog = new FileDialog(comp.getShell(),SWT.OPEN);
				dialog.setFilterPath("/");
				dialog.setFilterExtensions(new String[]{"*.png"});

				androidIconText.setText(dialog.open());


			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {


			}
		});
		
		Label extApkSeparator = new Label(finalComp, SWT.HORIZONTAL
				| SWT.SEPARATOR);
		extApkSeparator.setLayoutData(separatorGrid);

		grid = new GridData(GridData.FILL_BOTH,SWT.CENTER,true,true,1,1);
		grid.minimumWidth = 365;


		new Label(finalComp, SWT.NONE).setText("* 안드로이드 APK 저장위치");
		androidExtText = new Text(finalComp, SWT.BORDER);
		androidExtText.setLayoutData(grid);
		androidExtText.addFocusListener(this);
		Button apkExtButton = new Button(finalComp, SWT.PUSH);
		apkExtButton.setText("선택");
		apkExtButton.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {

				FileDialog dialog = new FileDialog(comp.getShell(),SWT.SAVE|SWT.OPEN);
				dialog.setFilterPath("/");
				dialog.setFilterExtensions(new String[]{"*.apk"});

				androidExtText.setText(dialog.open());


			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {


			}
		});
		Label descSeparator = new Label(finalComp, SWT.HORIZONTAL
				| SWT.SEPARATOR);
		descSeparator.setLayoutData(separatorGrid);



		GridData desc  = new GridData(GridData.FILL_BOTH,SWT.TOP,true,true,6,6);
		desc.minimumHeight = 90;
		desc.minimumWidth = 500;
		descLabel = new Label(finalComp, SWT.NONE);

		descLabel.setLayoutData(desc);
		descLabel.setText("도움말"+LayoutManager.ENTER_TOKEN);

		keyText.setText(ProjectManager.getInstance().getTempData("ExportAndroidPackDialog"+"_"+"keyText")==null?"":(String)ProjectManager.getInstance().getTempData("ExportAndroidPackDialog"+"_"+"keyText"));
		keystorePathText.setText(ProjectManager.getInstance().getTempData("ExportAndroidPackDialog"+"_"+"keystorePathText")==null?"":(String)ProjectManager.getInstance().getTempData("ExportAndroidPackDialog"+"_"+"keystorePathText"));
		validtyText.setText(ProjectManager.getInstance().getTempData("ExportAndroidPackDialog"+"_"+"validtyText")==null?"":(String)ProjectManager.getInstance().getTempData("ExportAndroidPackDialog"+"_"+"validtyText"));
		nameText.setText(ProjectManager.getInstance().getTempData("ExportAndroidPackDialog"+"_"+"nameText")==null?"":(String)ProjectManager.getInstance().getTempData("ExportAndroidPackDialog"+"_"+"nameText"));
		groupLevelText.setText(ProjectManager.getInstance().getTempData("ExportAndroidPackDialog"+"_"+"groupLevelText")==null?"":(String)ProjectManager.getInstance().getTempData("ExportAndroidPackDialog"+"_"+"groupLevelText"));
		groupNameText.setText(ProjectManager.getInstance().getTempData("ExportAndroidPackDialog"+"_"+"groupNameText")==null?"":(String)ProjectManager.getInstance().getTempData("ExportAndroidPackDialog"+"_"+"groupNameText"));
		cityText.setText(ProjectManager.getInstance().getTempData("ExportAndroidPackDialog"+"_"+"cityText")==null?"":(String)ProjectManager.getInstance().getTempData("ExportAndroidPackDialog"+"_"+"cityText"));
		stateText.setText(ProjectManager.getInstance().getTempData("ExportAndroidPackDialog"+"_"+"stateText")==null?"":(String)ProjectManager.getInstance().getTempData("ExportAndroidPackDialog"+"_"+"stateText"));
		countryCodeText.setText(ProjectManager.getInstance().getTempData("ExportAndroidPackDialog"+"_"+"countryCodeText")==null?"":(String)ProjectManager.getInstance().getTempData("ExportAndroidPackDialog"+"_"+"countryCodeText"));
		androidIconText.setText(ProjectManager.getInstance().getTempData("ExportAndroidPackDialog"+"_"+"androidIconText")==null?"":(String)ProjectManager.getInstance().getTempData("ExportAndroidPackDialog"+"_"+"androidIconText"));
		androidExtText.setText(ProjectManager.getInstance().getAndroidAPK());		

		return super.createDialogArea(parent);
	}
	@Override
	protected void okPressed() {

//		ProjectManager.getInstance().makeIOS();
//		
//		if(true)
//			return;
		if(keysotreNewButton.getSelection()){
			if(keystorePathText.getText().equals("")){
				ProjectManager.getInstance().showMessage(MessageDialog.WARNING, "확인", "파일 저장 위치를 지정하여 주시기 바랍니다.");
				keystorePathText.forceFocus();
				return;
			}else{
		
			}

			if(keyText.getText().trim().equals("")){
				ProjectManager.getInstance().showMessage(MessageDialog.WARNING, "확인", "키 이름을 입력하여 주세요.");
				return;
			}
			if(passwordCheckText.getText().equals("") || passwordText.getText().equals("")){
				ProjectManager.getInstance().showMessage(MessageDialog.WARNING, "확인", "패스워드가 입력 되지 않았습니다. 패스워드를 입력하여 주세요.");
				passwordCheckText.setText("");
				passwordText.setText("");
				passwordText.setFocus();
				return;
			}
			if(!passwordText.equals("")){
				if(!passwordText.getText().equals(passwordCheckText.getText())){
					ProjectManager.getInstance().showMessage(MessageDialog.WARNING, "확인", "패스워드가 서로 일치 하지 않습니다. 다시 입력하여 주세요.");
					passwordCheckText.setText("");
					passwordText.setText("");
					passwordText.setFocus();
					return;
				}
			}
			if(passwordText.getText().length()<6){
				ProjectManager.getInstance().showMessage(MessageDialog.WARNING, "확인", "패스워드는 6자리 이상가능합니다.");
				passwordCheckText.setText("");
				passwordText.setText("");
				passwordText.setFocus();
				return;
			}
			if(validtyText.getText().trim().equals("")){
				ProjectManager.getInstance().showMessage(MessageDialog.WARNING, "확인", "유효기간을 입력하여 주시기 바랍니다.");
				validtyText.forceFocus();
				return;
			}else{
				try{Integer.parseInt(validtyText.getText());}catch(Exception e){ProjectManager.getInstance().showMessage(MessageDialog.WARNING, "확인", "유효기간은 숫자로만 입력하여야 합니다."); validtyText.forceFocus();return;}
				try{if(Integer.parseInt(validtyText.getText())<30){ ProjectManager.getInstance().showMessage(MessageDialog.WARNING, "확인", "유효기간30년 이후로 입력하여야 합니다.");validtyText.forceFocus();return;}}catch(Exception e){}
			}
			if(nameText.getText().trim().equals("")){
				ProjectManager.getInstance().showMessage(MessageDialog.WARNING, "확인", "생성자 이름 입력하여 주시기 바랍니다.");
				nameText.forceFocus();
				return;
			}

			//Key 생성 
			String[] cmd = new String[11];

			cmd[0] = passwordText.getText();
			cmd[1] = passwordCheckText.getText();
			cmd[2] = nameText.getText();//이름 성 
			cmd[3] = groupLevelText.getText();//조직단위 
			cmd[4] = groupNameText.getText();//조직이름 
			cmd[5] = cityText.getText();//시군시
			cmd[6] = stateText.getText();//시도이름  
			cmd[7] = countryCodeText.getText();
			cmd[8] = "y";
			cmd[9] = passwordText.getText();
			cmd[10] = passwordCheckText.getText();

			ProjectManager.getInstance().showMessage(MessageDialog.INFORMATION, "정보", "키의 관리는 아주 중요합니다."+LayoutManager.ENTER_TOKEN+"Key의 암호가 유출되면 악용해서 앱을 망가뜨리고 유저 데이터를 훔쳐갈 수 있습니다."+LayoutManager.ENTER_TOKEN+"파일관리와 패스워드 관리에 주의를 기울여 주시기 바랍니다.");

			LayoutManager.getInstance().generatorAndroidKeyStore(keyText.getText(),String.valueOf(Integer.valueOf(validtyText.getText())*365),keystorePathText.getText(),cmd);


		}else{
			File f = new File(keystorePathText.getText());
			if(!f.exists()){
				ProjectManager.getInstance().showMessage(MessageDialog.WARNING, "확인", "파일이 존재하지 않습니다. 파일을 선택하여 주세요.");
				keystorePathText.setFocus();
				return;
			}else{
				f.mkdirs();
			}
		}

		if(androidExtText.getText().equals("")){
			ProjectManager.getInstance().showMessage(MessageDialog.WARNING, "확인", "경로를 지정하여주세요.");
			androidExtText.forceFocus(); 
		}
		//템프 내용 저장하기

		ProjectManager.getInstance().putTempData("ExportAndroidPackDialog"+"_"+"keyText",keyText.getText());
		ProjectManager.getInstance().putTempData("ExportAndroidPackDialog"+"_"+"keystorePathText",keystorePathText.getText());
		ProjectManager.getInstance().putTempData("ExportAndroidPackDialog"+"_"+"validtyText",validtyText.getText());
		ProjectManager.getInstance().putTempData("ExportAndroidPackDialog"+"_"+"nameText",nameText.getText());
		ProjectManager.getInstance().putTempData("ExportAndroidPackDialog"+"_"+"groupLevelText",groupLevelText.getText());
		ProjectManager.getInstance().putTempData("ExportAndroidPackDialog"+"_"+"groupNameText",groupNameText.getText());
		ProjectManager.getInstance().putTempData("ExportAndroidPackDialog"+"_"+"cityText",cityText.getText());
		ProjectManager.getInstance().putTempData("ExportAndroidPackDialog"+"_"+"stateText",stateText.getText());
		ProjectManager.getInstance().putTempData("ExportAndroidPackDialog"+"_"+"countryCodeText",countryCodeText.getText());
		ProjectManager.getInstance().putTempData("ExportAndroidPackDialog"+"_"+"androidIconText",androidIconText.getText());

		ProjectManager.getInstance().setAndroidKeyStore(keystorePathText.getText());

		ProjectManager.getInstance().setAndroidAPK(androidExtText.getText());
		
		ProjectManager.getInstance().setAndroid_icon(androidIconText.getText());
		
		

		// APK 생성

		ProjectManager.getInstance().makeAPK(keyText.getText(),passwordText.getText());


		if(ProjectManager.getInstance().showDialog(MessageDialog.QUESTION, "APK파일을 실행", "APK 파일을 실행 하시겠습니까?", new String[]{"Ok","Cancel"})==0){
			AndroidDevicesDialog dialog = new AndroidDevicesDialog(comp.getShell());
			dialog.open();
		}




		super.okPressed();
	}
	@Override
	protected void cancelPressed() {
		ProjectManager.getInstance().setAndroidKeyStore("");

		ProjectManager.getInstance().setAndroidAPK("");
		super.cancelPressed();
	}
	@Override
	protected void buttonPressed(int buttonId) {
		// TODO Auto-generated method stub
		super.buttonPressed(buttonId);
	}
	@Override
	public void focusGained(FocusEvent e) {
		StringBuffer str = new StringBuffer();
		str.append("도움말"+LayoutManager.ENTER_TOKEN+LayoutManager.ENTER_TOKEN);

		if(e.widget== keysotreLoadButton){

			str.append("기존파일을 불러옵니다." );
		}else if(e.widget== keysotreNewButton){
			str.append("신규 파일을생성하도록 합니다. 아래의 * 표시 필수 항목을 모두 기제하여주세요." );
		}else if(e.widget== keyText){
			str.append("키 이름을 지정합니다. 키 이름은 Key의 별명을 의미합니다."+LayoutManager.ENTER_TOKEN+" 차후 앱을 업데이트할때 필요한 내용입니다. 꼭 기억하여 주세요!" );
		}else if(e.widget== passwordText){
			str.append("키의 패스워드입니다. 패스워드 6자리 이상입니다. "+LayoutManager.ENTER_TOKEN +"키의 패스워드는 업데이트 인증에 필요하오니 꼭 기억하여 주시기 바랍니다. " );
		}else if(e.widget== passwordCheckText){
			str.append("위 패스워드가 동일하게 입력하여 주시기 바랍니다. " );
		}else if(e.widget== keystorePathText){
			str.append("keyStore 파일을의 위치를 결정합니다."+LayoutManager.ENTER_TOKEN +"신규Key파일생성 : 신규 저장위치 지정"+LayoutManager.ENTER_TOKEN+"기존 key파일 : 기존 key파일 위치" );
		}else if(e.widget== validtyText){
			str.append("유효기간은 최소 30년부터입니다. 30년 이상으로 기제하여 주시기 바랍니다. 이 Key의 유효기간입니다." );
		}else if(e.widget== nameText){
			str.append("앱의 개발자 이름을 작셩하여 주시기 바랍니다." );
		}else if(e.widget== groupLevelText){
			str.append("필수 입력 값이 아닙니다.");
			str.append("조직의 단위를 입력하여 주시기 바랍니다. 기업 / 개인 등 " );
		}else if(e.widget== groupNameText){
			str.append("필수 입력 값이 아닙니다.");
			str.append("그룹의 이름을 입력하여 주시기 바랍니다." );
		}else if(e.widget== cityText){
			str.append("필수 입력 값이 아닙니다.");
			str.append("거주하시는 도시의 이름을 입력하여 주시기 바랍니다." );
		}else if(e.widget== stateText){
			str.append("필수 입력 값이 아닙니다.");
			str.append("구 동/읍 단위의 주소를 입력하여 주시기 바랍니다." );
		}else if(e.widget== countryCodeText){
			str.append("필수 입력 값이 아닙니다.");
			str.append("국가코드입니다. 두자리 숫자로 입력하여야 합니다. "+LayoutManager.ENTER_TOKEN+"대한민국은 82번 국가코드로 지정입니다." );
		}else if(e.widget ==androidExtText){
			str.append("핸드폰에 추가될 APK 파일의 위치를 지정하여 주시기 바랍니다." );
		}

		descLabel.setText(str.toString());

	}
	@Override
	public void focusLost(FocusEvent e) {
		// TODO Auto-generated method stub

	}
	public Text getAndroidExtText() {
		return androidExtText;
	}
	public void setAndroidExtText(Text androidExtText) {
		this.androidExtText = androidExtText;
	}



}
