package org.pky.uml.dialog;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.pky.uml.browser.common.propertybrowser.Property;
import org.pky.uml.commons.managers.ProjectManager;
import org.pky.uml.editparts.common.UMLEditPart;
import org.pky.uml.model.action.ActionAlertMessageItem;
import org.pky.uml.model.action.ActionAutoMoveActionItem;
import org.pky.uml.model.common.UMLModel;

public class AutoMoveDialog  extends TitleAreaDialog implements SelectionListener{

	Composite comp;

	Text moveTimeText;
	Combo moveCombo;
	Button chkButton;
	ArrayList<UMLModel> moveComboRef;
	ActionAutoMoveActionItem actionItem =null;
	public AutoMoveDialog(Shell parentShell) {
		super(parentShell);
	}

	protected Control createDialogArea(Composite parent) {
		// TODO Auto-generated method stub
		Composite composite = (Composite)super.createDialogArea(parent);

		comp = new Composite(composite, SWT.NONE); 

		setTitle("자동화면이동");
		setMessage("인트로 화면등을 설정할 수 있습니다.");

		comp.setLayout(new GridLayout(4, false));
		comp.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		chkButton = new Button(comp, SWT.CHECK);
		chkButton.setText("적용");
		chkButton.setLayoutData(new GridData(SWT.LEFT,SWT.CENTER,false,false,4,1));
		chkButton.addSelectionListener(this);

		Label label = new Label(comp,SWT.NONE);
		label.setText("화면명:");		
		label.setLayoutData(new GridData(SWT.LEFT,SWT.CENTER,false,false,1,1));

		moveCombo = new Combo(comp, SWT.BORDER);
		moveCombo.setLayoutData(new GridData(SWT.FILL,SWT.CENTER,true,false,3,1));

		label = new Label(comp,SWT.NONE);
		label.setText("이동시간:");		
		label.setLayoutData(new GridData(SWT.LEFT,SWT.CENTER,false,false,1,1));

		moveTimeText = new Text(comp, SWT.BORDER);
		moveTimeText.setLayoutData(new GridData(SWT.FILL,SWT.CENTER,false,false,1,1));

		label = new Label(comp,SWT.NONE);
		label.setText("초");		
		label.setLayoutData(new GridData(SWT.LEFT,SWT.CENTER,false,false,1,1));


		moveComboRef = ProjectManager.getInstance().getModels(2);
		for(int i = 0; i < moveComboRef.size(); i ++){
			UMLModel model = moveComboRef.get(i);
			String name = moveComboRef.get(i).getName();
			String text =(String)model.getPropertyValue(Property.ID_TEXT);

			if(text!=null && !text.equals("")){
				name = name + "[" + text + "]";
			}

			moveCombo.add(name);		
		}

		init();
		return super.createDialogArea(parent);

	}

	private void init(){
		List<UMLEditPart> list = ProjectManager.getInstance().getSelections();
		moveTimeText.setEnabled(chkButton.getSelection());
		moveCombo.setEnabled(chkButton.getSelection());

		if(list.size()>0){
			if(list.get(0).getModel().getPropertyValue(Property.ID_AUTO_MOVE)!=null && list.get(0).getModel().getPropertyValue(Property.ID_AUTO_MOVE) instanceof ActionAutoMoveActionItem){
				ActionAutoMoveActionItem actionItem = (ActionAutoMoveActionItem)list.get(0).getModel().getPropertyValue(Property.ID_AUTO_MOVE);
				for(int i = 0 ; i < moveComboRef.size(); i++){
					if(moveComboRef.get(i).getID().equals(actionItem.getLayoutID())){
						moveCombo.select(i);
						moveTimeText.setText(String.valueOf(actionItem.getTime()));
						moveTimeText.setEnabled(true);
						moveCombo.setEnabled(true);				
					}
				}
			}

		}
	}

	protected void buttonPressed(int buttonId) {

		if(buttonId == IDialogConstants.OK_ID ) {

			List<UMLEditPart> list = ProjectManager.getInstance().getSelections();
			for(int i = 0;i< list.size(); i++){

				if(chkButton.getSelection())
					actionItem = new ActionAutoMoveActionItem(moveComboRef.get(moveCombo.getSelectionIndex()).getID(),Integer.parseInt(moveTimeText.getText()));
				else
					list.get(i).getModel().setPropertyValue(Property.ID_AUTO_MOVE, null);

			}
		}else{

		}
		super.buttonPressed(buttonId);
	}

	@Override
	public void widgetSelected(SelectionEvent e) {

		if(e.getSource()==chkButton){
			moveTimeText.setEnabled(chkButton.getSelection());
			moveCombo.setEnabled(chkButton.getSelection());


		}
	}

	@Override
	public void widgetDefaultSelected(SelectionEvent e) {


	}

	public ActionAutoMoveActionItem getActionItem() {
		return actionItem;
	}

	public void setActionItem(ActionAutoMoveActionItem actionItem) {
		this.actionItem = actionItem;
	}

}