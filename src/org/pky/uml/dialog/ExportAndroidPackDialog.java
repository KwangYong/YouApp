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
		//		test[1] = "123456";// ��й�ȣ 
		//		test[2] = "123456";//��й�ȣ 
		//		test[3] = "1\n";//�̸� �� 
		//		test[4] = "1\n";//�������� 
		//		test[5] = "1\n";//�����̸� 
		//		test[6] = "1\n";//�ñ���
		//		test[7] = "1\n";//�õ��̸�  
		//		test[8] = "82\n";//������
		//		test[9] = "y\n";//Ȯ�ο���
		//		test[10] = "123456\n";//�н�����
		//		test[11] = "123456\n";//�н����� 
		//		test[12] = "exit \n";


		GridData grid = new GridData(GridData.FILL_BOTH,SWT.CENTER,true,true,1,1);
		grid.minimumWidth = 365;

		GridData separatorGrid = new GridData(GridData.FILL_HORIZONTAL);
		separatorGrid.horizontalSpan = 3;

		comp = (Composite)super.createDialogArea(parent);


		Composite finalComp = new Composite(comp, SWT.NONE);
		finalComp.setLayout(new GridLayout(3, false));
		finalComp.setLayoutData(new GridData(GridData.FILL_BOTH));

		setTitle("�ȵ���̵� ���ϻ���");
		setMessage("�ȵ���̵� APK�� �����մϴ�.");

		Label isKeyLabel = new Label(finalComp,SWT.NONE);
		isKeyLabel.setText("* KeyStore��������");
		//		isKeyLabel.setLayoutData(new GridData(GridData.BEGINNING,SWT.TOP,false,false,1,2));

		keysotreLoadButton = new Button(finalComp, SWT.RADIO);
		keysotreLoadButton.setText("����Key����");
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
		keysotreNewButton.setText("�ű�Key���ϻ���");
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
		filePathLabel.setText("* KeyStore ���� ��ġ");

		keystorePathText = new Text(finalComp, SWT.BORDER);
		keystorePathText.setLayoutData(grid);
		keystorePathText.addFocusListener(this);

		grid = new GridData(GridData.FILL_BOTH,SWT.CENTER,true,true,2,1);
		grid.minimumWidth = 400;


		Button dirButton = new Button(finalComp, SWT.PUSH);
		dirButton.setText("����");

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

		new Label(finalComp, SWT.NONE).setText("* Ű�̸�");


		keyText = new Text(finalComp,SWT.BORDER);
		keyText.setLayoutData(grid);
		keyText.addFocusListener(this);

		new Label(finalComp, SWT.NONE).setText("* �н�����");
		passwordText = new Text(finalComp,SWT.PASSWORD|SWT.BORDER);
		passwordText.setLayoutData(grid);
		passwordText.addFocusListener(this);

		new Label(finalComp, SWT.NONE).setText("* �н����� Ȯ��");
		passwordCheckText = new Text(finalComp,SWT.PASSWORD|SWT.BORDER);
		passwordCheckText.setLayoutData(grid); 
		passwordCheckText.addFocusListener(this);


		new Label(finalComp, SWT.NONE).setText("* ��ȿ�Ⱓ(��)");
		validtyText = new Text(finalComp,SWT.BORDER);
		validtyText.setLayoutData(grid);
		validtyText.addFocusListener(this);

		new Label(finalComp, SWT.NONE).setText("* ������ �̸�");
		nameText = new Text(finalComp, SWT.BORDER);
		nameText.setLayoutData(grid);
		nameText.addFocusListener(this);

		Label pkSeparator = new Label(finalComp, SWT.HORIZONTAL
				| SWT.SEPARATOR);
		pkSeparator.setLayoutData(separatorGrid);

		new Label(finalComp, SWT.NONE).setText("��������");
		groupLevelText = new Text(finalComp, SWT.BORDER);
		groupLevelText.setLayoutData(grid);
		groupLevelText.addFocusListener(this);

		new Label(finalComp, SWT.NONE).setText("�����̸�");
		groupNameText = new Text(finalComp, SWT.BORDER);
		groupNameText.setLayoutData(grid);
		groupNameText.addFocusListener(this);

		new Label(finalComp, SWT.NONE).setText("�ּ� ��/��");
		cityText = new Text(finalComp, SWT.BORDER);
		cityText.setLayoutData(grid);
		cityText.addFocusListener(this);

		new Label(finalComp, SWT.NONE).setText("�ּ� ��/��");
		stateText = new Text(finalComp, SWT.BORDER);
		stateText.setLayoutData(grid);
		stateText.addFocusListener(this);

		grid = new GridData(GridData.FILL_BOTH,SWT.CENTER,true,true,2,1);

		new Label(finalComp, SWT.NONE).setText("�����ڵ�(XX)");
		countryCodeText = new Text(finalComp, SWT.BORDER);
		countryCodeText.setLayoutData(grid);
		countryCodeText.setText("82");
		countryCodeText.addFocusListener(this);
		Label appPropertySeparator = new Label(finalComp, SWT.HORIZONTAL
				| SWT.SEPARATOR);
		appPropertySeparator.setLayoutData(separatorGrid);

		grid = new GridData(GridData.FILL_BOTH,SWT.CENTER,true,true,1,1);
		grid.minimumWidth = 365;
		
		new Label(finalComp, SWT.NONE).setText("�ȵ���̵� �� Icon");
		
		androidIconText= new Text(finalComp, SWT.BORDER);
		androidIconText.setLayoutData(grid);
		androidIconText.addFocusListener(this);
		Button iconPathButton = new Button(finalComp, SWT.PUSH);
		iconPathButton.setText("����");
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


		new Label(finalComp, SWT.NONE).setText("* �ȵ���̵� APK ������ġ");
		androidExtText = new Text(finalComp, SWT.BORDER);
		androidExtText.setLayoutData(grid);
		androidExtText.addFocusListener(this);
		Button apkExtButton = new Button(finalComp, SWT.PUSH);
		apkExtButton.setText("����");
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
		descLabel.setText("����"+LayoutManager.ENTER_TOKEN);

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
				ProjectManager.getInstance().showMessage(MessageDialog.WARNING, "Ȯ��", "���� ���� ��ġ�� �����Ͽ� �ֽñ� �ٶ��ϴ�.");
				keystorePathText.forceFocus();
				return;
			}else{
		
			}

			if(keyText.getText().trim().equals("")){
				ProjectManager.getInstance().showMessage(MessageDialog.WARNING, "Ȯ��", "Ű �̸��� �Է��Ͽ� �ּ���.");
				return;
			}
			if(passwordCheckText.getText().equals("") || passwordText.getText().equals("")){
				ProjectManager.getInstance().showMessage(MessageDialog.WARNING, "Ȯ��", "�н����尡 �Է� ���� �ʾҽ��ϴ�. �н����带 �Է��Ͽ� �ּ���.");
				passwordCheckText.setText("");
				passwordText.setText("");
				passwordText.setFocus();
				return;
			}
			if(!passwordText.equals("")){
				if(!passwordText.getText().equals(passwordCheckText.getText())){
					ProjectManager.getInstance().showMessage(MessageDialog.WARNING, "Ȯ��", "�н����尡 ���� ��ġ ���� �ʽ��ϴ�. �ٽ� �Է��Ͽ� �ּ���.");
					passwordCheckText.setText("");
					passwordText.setText("");
					passwordText.setFocus();
					return;
				}
			}
			if(passwordText.getText().length()<6){
				ProjectManager.getInstance().showMessage(MessageDialog.WARNING, "Ȯ��", "�н������ 6�ڸ� �̻󰡴��մϴ�.");
				passwordCheckText.setText("");
				passwordText.setText("");
				passwordText.setFocus();
				return;
			}
			if(validtyText.getText().trim().equals("")){
				ProjectManager.getInstance().showMessage(MessageDialog.WARNING, "Ȯ��", "��ȿ�Ⱓ�� �Է��Ͽ� �ֽñ� �ٶ��ϴ�.");
				validtyText.forceFocus();
				return;
			}else{
				try{Integer.parseInt(validtyText.getText());}catch(Exception e){ProjectManager.getInstance().showMessage(MessageDialog.WARNING, "Ȯ��", "��ȿ�Ⱓ�� ���ڷθ� �Է��Ͽ��� �մϴ�."); validtyText.forceFocus();return;}
				try{if(Integer.parseInt(validtyText.getText())<30){ ProjectManager.getInstance().showMessage(MessageDialog.WARNING, "Ȯ��", "��ȿ�Ⱓ30�� ���ķ� �Է��Ͽ��� �մϴ�.");validtyText.forceFocus();return;}}catch(Exception e){}
			}
			if(nameText.getText().trim().equals("")){
				ProjectManager.getInstance().showMessage(MessageDialog.WARNING, "Ȯ��", "������ �̸� �Է��Ͽ� �ֽñ� �ٶ��ϴ�.");
				nameText.forceFocus();
				return;
			}

			//Key ���� 
			String[] cmd = new String[11];

			cmd[0] = passwordText.getText();
			cmd[1] = passwordCheckText.getText();
			cmd[2] = nameText.getText();//�̸� �� 
			cmd[3] = groupLevelText.getText();//�������� 
			cmd[4] = groupNameText.getText();//�����̸� 
			cmd[5] = cityText.getText();//�ñ���
			cmd[6] = stateText.getText();//�õ��̸�  
			cmd[7] = countryCodeText.getText();
			cmd[8] = "y";
			cmd[9] = passwordText.getText();
			cmd[10] = passwordCheckText.getText();

			ProjectManager.getInstance().showMessage(MessageDialog.INFORMATION, "����", "Ű�� ������ ���� �߿��մϴ�."+LayoutManager.ENTER_TOKEN+"Key�� ��ȣ�� ����Ǹ� �ǿ��ؼ� ���� �����߸��� ���� �����͸� ���İ� �� �ֽ��ϴ�."+LayoutManager.ENTER_TOKEN+"���ϰ����� �н����� ������ ���Ǹ� ��￩ �ֽñ� �ٶ��ϴ�.");

			LayoutManager.getInstance().generatorAndroidKeyStore(keyText.getText(),String.valueOf(Integer.valueOf(validtyText.getText())*365),keystorePathText.getText(),cmd);


		}else{
			File f = new File(keystorePathText.getText());
			if(!f.exists()){
				ProjectManager.getInstance().showMessage(MessageDialog.WARNING, "Ȯ��", "������ �������� �ʽ��ϴ�. ������ �����Ͽ� �ּ���.");
				keystorePathText.setFocus();
				return;
			}else{
				f.mkdirs();
			}
		}

		if(androidExtText.getText().equals("")){
			ProjectManager.getInstance().showMessage(MessageDialog.WARNING, "Ȯ��", "��θ� �����Ͽ��ּ���.");
			androidExtText.forceFocus(); 
		}
		//���� ���� �����ϱ�

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
		
		

		// APK ����

		ProjectManager.getInstance().makeAPK(keyText.getText(),passwordText.getText());


		if(ProjectManager.getInstance().showDialog(MessageDialog.QUESTION, "APK������ ����", "APK ������ ���� �Ͻðڽ��ϱ�?", new String[]{"Ok","Cancel"})==0){
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
		str.append("����"+LayoutManager.ENTER_TOKEN+LayoutManager.ENTER_TOKEN);

		if(e.widget== keysotreLoadButton){

			str.append("���������� �ҷ��ɴϴ�." );
		}else if(e.widget== keysotreNewButton){
			str.append("�ű� �����������ϵ��� �մϴ�. �Ʒ��� * ǥ�� �ʼ� �׸��� ��� �����Ͽ��ּ���." );
		}else if(e.widget== keyText){
			str.append("Ű �̸��� �����մϴ�. Ű �̸��� Key�� ������ �ǹ��մϴ�."+LayoutManager.ENTER_TOKEN+" ���� ���� ������Ʈ�Ҷ� �ʿ��� �����Դϴ�. �� ����Ͽ� �ּ���!" );
		}else if(e.widget== passwordText){
			str.append("Ű�� �н������Դϴ�. �н����� 6�ڸ� �̻��Դϴ�. "+LayoutManager.ENTER_TOKEN +"Ű�� �н������ ������Ʈ ������ �ʿ��Ͽ��� �� ����Ͽ� �ֽñ� �ٶ��ϴ�. " );
		}else if(e.widget== passwordCheckText){
			str.append("�� �н����尡 �����ϰ� �Է��Ͽ� �ֽñ� �ٶ��ϴ�. " );
		}else if(e.widget== keystorePathText){
			str.append("keyStore �������� ��ġ�� �����մϴ�."+LayoutManager.ENTER_TOKEN +"�ű�Key���ϻ��� : �ű� ������ġ ����"+LayoutManager.ENTER_TOKEN+"���� key���� : ���� key���� ��ġ" );
		}else if(e.widget== validtyText){
			str.append("��ȿ�Ⱓ�� �ּ� 30������Դϴ�. 30�� �̻����� �����Ͽ� �ֽñ� �ٶ��ϴ�. �� Key�� ��ȿ�Ⱓ�Դϴ�." );
		}else if(e.widget== nameText){
			str.append("���� ������ �̸��� �ۼ��Ͽ� �ֽñ� �ٶ��ϴ�." );
		}else if(e.widget== groupLevelText){
			str.append("�ʼ� �Է� ���� �ƴմϴ�.");
			str.append("������ ������ �Է��Ͽ� �ֽñ� �ٶ��ϴ�. ��� / ���� �� " );
		}else if(e.widget== groupNameText){
			str.append("�ʼ� �Է� ���� �ƴմϴ�.");
			str.append("�׷��� �̸��� �Է��Ͽ� �ֽñ� �ٶ��ϴ�." );
		}else if(e.widget== cityText){
			str.append("�ʼ� �Է� ���� �ƴմϴ�.");
			str.append("�����Ͻô� ������ �̸��� �Է��Ͽ� �ֽñ� �ٶ��ϴ�." );
		}else if(e.widget== stateText){
			str.append("�ʼ� �Է� ���� �ƴմϴ�.");
			str.append("�� ��/�� ������ �ּҸ� �Է��Ͽ� �ֽñ� �ٶ��ϴ�." );
		}else if(e.widget== countryCodeText){
			str.append("�ʼ� �Է� ���� �ƴմϴ�.");
			str.append("�����ڵ��Դϴ�. ���ڸ� ���ڷ� �Է��Ͽ��� �մϴ�. "+LayoutManager.ENTER_TOKEN+"���ѹα��� 82�� �����ڵ�� �����Դϴ�." );
		}else if(e.widget ==androidExtText){
			str.append("�ڵ����� �߰��� APK ������ ��ġ�� �����Ͽ� �ֽñ� �ٶ��ϴ�." );
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
