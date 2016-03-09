package org.pky.uml.commons.managers;

import java.text.AttributedCharacterIterator.Attribute;

import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.editparts.ZoomListener;
import org.eclipse.gef.editparts.ZoomManager;
import org.eclipse.gef.tools.CellEditorLocator;
import org.eclipse.gef.tools.DirectEditManager;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.part.CellEditorActionHandler;
import org.pky.uml.celleditor.AttributeQuickCellEditor;
import org.pky.uml.figures.ElementFigure;
import org.pky.uml.figures.TextViewFigure;
import org.pky.uml.model.AttributeElementModel;
import org.pky.uml.model.AttributeItem;
import org.pky.uml.model.ElementModel;

public class UMLEditManager extends DirectEditManager {
	private IActionBars actionBars;
	private CellEditorActionHandler actionHandler;
	private IAction copy, cut, paste, undo, redo, find, selectAll, delete;
	private double cachedZoom = -1.0;
	private Font scaledFont;
	

	private ZoomListener zoomListener = new ZoomListener() {
		public void zoomChanged(double newZoom) {
			updateScaledFont(newZoom);
		}
	};

	public UMLEditManager(GraphicalEditPart source, CellEditorLocator locator) {
		super(source, null, locator);
	}
	@Override
	public void show() {
		// TODO Auto-generated method stub
		super.show();
		
	}

	@Override
	protected void commit() {
		// TODO Auto-generated method stub
		super.commit();
	}
	/** @see org.eclipse.gef.tools.DirectEditManager#bringDown() */
	public void bringDown() {
		try{
			System.out.println("´ÝÇôÁø´Ù.");
			ZoomManager zoomMgr = (ZoomManager)getEditPart().getViewer().getProperty(ZoomManager.class.toString());
			if (zoomMgr != null)
				zoomMgr.removeZoomListener(zoomListener);
			if (actionHandler != null) {
				actionHandler.dispose();
				actionHandler = null;
			}
			if (actionBars != null) {
				restoreSavedActions(actionBars);
				actionBars.updateActionBars();
				actionBars = null;
			}
			super.bringDown();
			// dispose any scaled fonts that might have been created
			disposeScaledFont();

		}catch(Exception e ){

		}
	}

	protected CellEditor createCellEditorOn(Composite composite) {
		//		    	return new TextCellEditor(composite, SWT.SINGLE | SWT.WRAP);
		//return new TextCellEditor(composite, SWT.MULTI | SWT.WRAP);
		if(this.getEditPart().getModel() instanceof AttributeElementModel){
			AttributeElementModel elementModel = (AttributeElementModel)this.getEditPart().getModel();
			AttributeQuickCellEditor attributeQuickCellEditor = new AttributeQuickCellEditor(composite,elementModel);
			Object obj = attributeQuickCellEditor.getText();
			return attributeQuickCellEditor;
		}else{
			return new TextCellEditor(composite, SWT.WRAP|SWT.MULTI);
		}
	}

	private void disposeScaledFont() {
		if (scaledFont != null) {
			scaledFont.dispose();
			scaledFont = null;
		}
	}

	protected void initCellEditor() {
		// update text
		TextViewFigure stickyNote = (TextViewFigure)getEditPart().getFigure();
		getCellEditor().setValue(stickyNote.getText());
		// update font
		ZoomManager zoomMgr = (ZoomManager)getEditPart().getViewer().getProperty(ZoomManager.class.toString());
		if (zoomMgr != null) {
			// this will force the font to be set
			cachedZoom = -1.0;
			updateScaledFont(zoomMgr.getZoom());
			zoomMgr.addZoomListener(zoomListener);
		} else
			getCellEditor().getControl().setFont(stickyNote.getFont());
		// Hook the cell editor's copy/paste actions to the actionBars so that they can
		// be invoked via keyboard shortcuts.
		actionBars = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage()
				.getActiveEditor().getEditorSite().getActionBars();
		saveCurrentActions(actionBars);
		actionHandler = new CellEditorActionHandler(actionBars);
		actionHandler.addCellEditor(getCellEditor());
		actionBars.updateActionBars();

	}

	private void restoreSavedActions(IActionBars actionBars) {
		actionBars.setGlobalActionHandler(ActionFactory.COPY.getId(), copy);
		actionBars.setGlobalActionHandler(ActionFactory.PASTE.getId(), paste);
		actionBars.setGlobalActionHandler(ActionFactory.DELETE.getId(), delete);
		actionBars.setGlobalActionHandler(ActionFactory.SELECT_ALL.getId(), selectAll);
		actionBars.setGlobalActionHandler(ActionFactory.CUT.getId(), cut);
		actionBars.setGlobalActionHandler(ActionFactory.FIND.getId(), find);
		actionBars.setGlobalActionHandler(ActionFactory.UNDO.getId(), undo);
		actionBars.setGlobalActionHandler(ActionFactory.REDO.getId(), redo);
	}

	private void saveCurrentActions(IActionBars actionBars) {
		copy = actionBars.getGlobalActionHandler(ActionFactory.COPY.getId());
		paste = actionBars.getGlobalActionHandler(ActionFactory.PASTE.getId());
		delete = actionBars.getGlobalActionHandler(ActionFactory.DELETE.getId());
		selectAll = actionBars.getGlobalActionHandler(ActionFactory.SELECT_ALL.getId());
		cut = actionBars.getGlobalActionHandler(ActionFactory.CUT.getId());
		find = actionBars.getGlobalActionHandler(ActionFactory.FIND.getId());
		undo = actionBars.getGlobalActionHandler(ActionFactory.UNDO.getId());
		redo = actionBars.getGlobalActionHandler(ActionFactory.REDO.getId());
	}

	private void updateScaledFont(double zoom) {
		ProjectManager.getInstance().setUmlEditManager(this);
		if (cachedZoom == zoom)
			return;
		//        Text text = (Text)getCellEditor().getControl();
		//        Font font = getEditPart().getFigure().getFont();
		//        disposeScaledFont();
		//        cachedZoom = zoom;
		//        if (zoom == 1.0)
		//            text.setFont(font);
		//        else {
		//            FontData fd = font.getFontData() [0];
		//            fd.setHeight((int)(fd.getHeight() * zoom));
		//            text.setFont(scaledFont = new Font(null, fd));
		//        }
	}
}
