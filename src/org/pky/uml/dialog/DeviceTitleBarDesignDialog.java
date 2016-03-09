package org.pky.uml.dialog;

import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Device;
import org.eclipse.swt.graphics.GCData;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.ColorDialog;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.pky.uml.commons.managers.ProjectManager;

public class DeviceTitleBarDesignDialog  extends TitleAreaDialog implements SelectionListener{
	Composite comp = null;
	Button applyButton;
	Text backgroundText = null;

	Label backButtonLabel = null;
	Button backgroundButton,backButton;

	RGB backRGB = null;
	public DeviceTitleBarDesignDialog(Shell parentShell) {
		super(parentShell);

	}

	protected Control createDialogArea(Composite parent) {

		setTitle("Title Bar ������");
		setMessage("Title Bar�� ���� �ٸ纸����~!");

		comp = new Composite((Composite)super.createDialogArea(parent),SWT.NONE);
		comp.setLayout(new GridLayout(3, false));
		comp.setLayoutData(new GridData(GridData.FILL_BOTH));

		GridData data = new GridData(GridData.FILL_HORIZONTAL);
		data.horizontalSpan = 3;

		//		new Label(comp, SWT.NONE).setText("����");
		applyButton = new Button(comp, SWT.CHECK);
		applyButton.setText("����");
		applyButton.setLayoutData(data);
		//		applyButton.addSelectionListener(this);

		data = new GridData(GridData.FILL_HORIZONTAL);
		data.horizontalSpan = 1;

		new Label(comp, SWT.NONE).setText("����̹���");
		backgroundText = new Text(comp, SWT.BORDER);
		backgroundText.setLayoutData(data);
		backgroundButton = new Button(comp, SWT.PUSH);
		backgroundButton.addSelectionListener(this);



		new Label(comp, SWT.NONE).setText("back ��ư �� ��� ����");
		backButtonLabel = new Label(comp, SWT.BORDER);
		backButtonLabel.setLayoutData(data);
		backButton = new Button(comp, SWT.PUSH);
		backButton.addSelectionListener(this);

		inti();
		return parent;
	}


	private void inti(){
		backRGB = ProjectManager.getInstance().getTitleBarBackColor();
		backgroundText.setText(ProjectManager.getInstance().getTitleBarBackgroundImage());
		if(backRGB!=null)
			backButtonLabel.setBackground(new Color(null,backRGB));

		if(!(backgroundText.getText()).equals("")){
			applyButton.setSelection(true);
		}


	}

	protected void okPressed() {
		if(applyButton.getEnabled()){
			ProjectManager.getInstance().setTitleBarBackgroundImage(backgroundText.getText());
			ProjectManager.getInstance().setTitleBarBackColor(backRGB);
		}else{
			ProjectManager.getInstance().setTitleBarBackgroundImage("");
			ProjectManager.getInstance().setTitleBarBackColor(null);
		}
		super.okPressed();
	}


	protected void cancelPressed() {

		super.cancelPressed();
	}


	@Override
	public void widgetSelected(SelectionEvent e) {
		if(e.getSource()==backgroundButton){
			FileDialog dialog = new FileDialog(comp.getShell(),SWT.OPEN);

			dialog.setFilterExtensions(new String[]{"*.png"});

			String text = dialog.open();
			if(text!=null && !text.equals("")){
				if(e.getSource()==backgroundButton){
					backgroundText.setText(text);
				}
				applyButton.setSelection(true);

			}
		}else if(e.getSource()==backButton){
			ColorDialog clordialog = new ColorDialog(ProjectManager.getInstance().window.getShell());

			backRGB =  clordialog.open();
			if(backRGB!=null)
				backButtonLabel.setBackground(new Color(null,backRGB));
		}
	}


	@Override
	public void widgetDefaultSelected(SelectionEvent e) {}

}
