package org.pky.uml.celleditor;

import java.text.MessageFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.events.TraverseEvent;
import org.eclipse.swt.events.TraverseListener;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Text;
import org.pky.uml.commons.managers.ProjectManager;
import org.pky.uml.model.AttributeElementModel;
import org.pky.uml.model.ClassModel;
import org.pky.uml.model.UMLDiagramModel;
import org.pky.uml.model.ViewModel;
import org.pky.uml.model.common.UMLModel;

public class AttributeQuickCellEditor extends CellEditor{

	private boolean isSelection = false;
	private boolean isDeleteable = false;
	private boolean isSelectable = false;

	private String chgValue = "";
	private ModifyListener modifyListener;

	AttributeElementModel attributeElementModel = null;
	Text text;
	

	public AttributeQuickCellEditor(Composite composite,AttributeElementModel attribute) {
		super(composite, SWT.SINGLE);
		this.attributeElementModel = attribute;
		text.setText(attributeElementModel.toString());

		




	}
	public void create(Composite parent) {
		super.create(parent);
		
	}

	protected Control createControl(Composite parent){
		// TODO Auto-generated method stub
		
		Composite composite = new Composite(parent, SWT.NONE);
		FillLayout layout = new FillLayout();
		layout.spacing = 5;
		composite.setLayout(layout);
		composite.setLayoutData(new GridData(GridData.FILL));

		try{
			FillLayout gridLayout = (FillLayout)composite.getLayout();

			Button menu = new Button(composite, SWT.FLAT);

			menu.setText("Menu");

			Menu popupMenu = new Menu(menu);
			MenuItem typeItem = new MenuItem(popupMenu, SWT.CASCADE);
			typeItem.setText("Type");

			Menu newMenu = new Menu(popupMenu);
			typeItem.setMenu(newMenu);

			MenuItem item_public = new MenuItem(newMenu, SWT.RADIO);
			item_public.setText("public");
			MenuItem item_private = new MenuItem(newMenu, SWT.RADIO);
			item_private.setText("private");
			MenuItem item_protected = new MenuItem(newMenu, SWT.RADIO);
			item_protected.setText("protected");

			menu.setMenu(popupMenu);





			text = new Text(composite, SWT.SINGLE| SWT.BORDER);

			GridData data = new GridData(GridData.VERTICAL_ALIGN_FILL);
			data.verticalSpan = 3;
			text.setLayoutData(data);

			text.addModifyListener(new ModifyListener() {

				@Override
				public void modifyText(ModifyEvent e) {
					chgValue  = text.getText();
					editOccured(e);

				}
			});


			Button upButton = new Button(composite,SWT.FLAT);
			upButton.setText("u");
			upButton.addSelectionListener(new SelectionListener() {

				@Override
				public void widgetSelected(SelectionEvent e) {
					UMLDiagramModel umlDiagramModel = (UMLDiagramModel)ProjectManager.getInstance().getUMLEditor().getGraphicalViewer().getContents().getModel();
					for(int i = 0 ; i < umlDiagramModel.getChildren().size(); i ++){
						if(umlDiagramModel.getChildren().get(i) instanceof ClassModel){
							for(int j = 0; j < 10; j++){
								ViewModel viewModel = new ViewModel((UMLModel)umlDiagramModel.getChildren().get(i));
								umlDiagramModel.addChild(viewModel);

								viewModel.setLocation(j,j);

							}
							break;
						}
					}


				}

				@Override
				public void widgetDefaultSelected(SelectionEvent e) {
					// TODO Auto-generated method stub

				}
			});

			Button downButton = new Button(composite,SWT.FLAT);
			downButton.setText("d");

			item_public.addSelectionListener(new SelectionListener() {

				@Override
				public void widgetSelected(SelectionEvent e) {

					String value = text.getText().trim();

					Pattern p = Pattern.compile("[/\\+\\-\\#]");
					Matcher matcher = p.matcher(value);
					Matcher overMapping = p.matcher(value);

					if(overMapping.find()) {
						value = value.substring(overMapping.end(),value.length());
					}else{

					}
					int type = 0;
					value = value.trim();
					value =  ProjectManager.SCOPEA_SET[type]+" "+value;
					text.setText(value);

				}

				@Override
				public void widgetDefaultSelected(SelectionEvent e) {
					// TODO Auto-generated method stub

				}
			});

			item_private.addSelectionListener(new SelectionListener() {

				@Override
				public void widgetSelected(SelectionEvent e) {


					String value = text.getText().trim();

					Pattern p = Pattern.compile("[/\\+\\-\\#]");
					Matcher matcher = p.matcher(value);
					Matcher overMapping = p.matcher(value);

					if(overMapping.find()) {
						value = value.substring(overMapping.end(),value.length());
					}else{

					}
					int type = 2;
					value = value.trim();
					value =  ProjectManager.SCOPEA_SET[type]+" "+value;
					text.setText(value);

				}

				@Override
				public void widgetDefaultSelected(SelectionEvent e) {
					// TODO Auto-generated method stub

				}
			});

			item_protected.addSelectionListener(new SelectionListener() {

				@Override
				public void widgetSelected(SelectionEvent e) {

					String value = text.getText().trim();

					Pattern p = Pattern.compile("[/\\+\\-\\#]");
					Matcher matcher = p.matcher(value);
					Matcher overMapping = p.matcher(value);

					if(overMapping.find()) {
						value = value.substring(overMapping.end(),value.length());
					}else{

					}
					int type = 1;
					value = value.trim();
					value =  ProjectManager.SCOPEA_SET[type]+" "+value;
					text.setText(value);

				}

				@Override
				public void widgetDefaultSelected(SelectionEvent e) {
					// TODO Auto-generated method stub

				}
			});

			text.addSelectionListener(new SelectionAdapter() {
				public void widgetDefaultSelected(SelectionEvent e) {
					handleDefaultSelection(e);
				}
			});
			text.addKeyListener(new KeyAdapter() {
				// hook key pressed - see PR 14201  
				public void keyPressed(KeyEvent e) {
					keyReleaseOccured(e);

					// as a result of processing the above call, clients may have
					// disposed this cell editor
					if ((getControl() == null) || getControl().isDisposed()) {
						return;
					}

				}
			});
			text.addTraverseListener(new TraverseListener() {
				public void keyTraversed(TraverseEvent e) {
					if (e.detail == SWT.TRAVERSE_ESCAPE
							|| e.detail == SWT.TRAVERSE_RETURN) {
						e.doit = false;
					}
				}
			});
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return composite;
	}
	protected void handleDefaultSelection(SelectionEvent event) {
		// same with enter-key handling code in keyReleaseOccured(e);
		fireApplyEditorValue();
		deactivate();
	}
	@Override
	protected Object doGetValue() {
		// TODO Auto-generated method stub

		return chgValue;
	}


	@Override
	protected void doSetFocus() {
		// TODO Auto-generated method stub


	}
	@Override
	protected void focusLost() {
		// TODO Auto-generated method stub
		super.focusLost();


	}
	protected void doSetValue(Object value) {
		
	}

	protected void editOccured(ModifyEvent e) {
		String value = chgValue;
		if (value == null) {
			value = "";//$NON-NLS-1$
		}
		Object typedValue = value;
		boolean oldValidState = isValueValid();
		boolean newValidState = isCorrect(typedValue);
		if (!newValidState) {
			// try to insert the current value into the error message.
			setErrorMessage(MessageFormat.format(getErrorMessage(),
					new Object[] { value }));
		}
		valueChanged(oldValidState, newValidState);
	}

	public Text getText() {
		return text;
	}

	public void setText(Text text) {
		this.text = text;
	}







}



