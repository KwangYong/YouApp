package org.pky.uml.dialog;

import javax.jws.WebParam.Mode;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.pky.uml.browser.model.model.ModelTreeModel;
import org.pky.uml.commons.managers.ModelBrowserManager;
import org.pky.uml.commons.managers.ProjectManager;

public class NewPackageDialog extends TitleAreaDialog {

	Composite comp;

	Text packageName;
	ModelTreeModel tp;
	public NewPackageDialog(Shell parentShell) {
		super(parentShell);


	}
	public NewPackageDialog(Shell parentShell,ModelTreeModel tp) {
		super(parentShell);
		this.tp = tp;

	}
	@Override
	protected Control createDialogArea(Composite parent) {
		// TODO Auto-generated method stub
		Composite composite = (Composite)super.createDialogArea(parent);

		comp = new Composite(composite, SWT.NONE); 

		setTitle("패키지 추가");
		setMessage("새 패키지의 이름을 입력하여 주세요.");

		comp.setLayout(new GridLayout(3, false));
		comp.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));


		Label label = new Label(comp,SWT.NONE);
		label.setText("패키지 이름");		
		label.setLayoutData(new GridData(SWT.LEFT,SWT.CENTER,false,false,1,1));

		packageName = new Text(comp, SWT.BORDER);
		packageName.setLayoutData(new GridData(SWT.FILL,SWT.CENTER,true,false,2,1));

		//		Label titleBarSeparator = new Label(comp, SWT.NONE| SWT.SEPARATOR | SWT.HORIZONTAL);
		//		GridData grid = new GridData(SWT.FILL,SWT.CENTER,true,false,2,1);
		//		grid.heightHint=22;
		//		titleBarSeparator.setLayoutData(grid);
		//		
		//		
		//		label = new Label(comp,SWT.NONE);
		//		label.setText("Android");		
		//		label.setLayoutData(new GridData(SWT.LEFT,SWT.CENTER,false,false,3,1));
		//		

		return super.createDialogArea(parent);
	}

	protected void buttonPressed(int buttonId) {

		if(buttonId == IDialogConstants.OK_ID) {

			int type = ProjectManager.getInstance().getModelType("Package");
			ProjectManager.getInstance().addUMLModel(packageName.getText(), type, null, tp, true);
			ModelBrowserManager.getInstance().getModelBrowser().expend(tp);			

		}
		super.buttonPressed(buttonId);
	}

}