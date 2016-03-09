package org.pky.uml.editor;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.gef.DefaultEditDomain;
import org.eclipse.gef.EditPartViewer;
import org.eclipse.gef.ui.parts.ScrollingGraphicalViewer;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.ui.IEditorPart;
import org.pky.uml.commons.managers.ProjectManager;
import org.pky.uml.editparts.common.UMLEditPart;
import org.pky.uml.model.common.UMLModel;

public class UMLDefaultEditDomain extends DefaultEditDomain {

	int keycode = -1;
	
	public UMLDefaultEditDomain(IEditorPart editorPart) {
		super(editorPart);
		// TODO Auto-generated constructor stub
	}
	@Override
	public void keyDown(KeyEvent keyEvent, EditPartViewer viewer) {
		// TODO Auto-generated method stub
		keycode = keyEvent.keyCode;
		super.keyDown(keyEvent, viewer);
	}
	@Override
	public void keyUp(KeyEvent keyEvent, EditPartViewer viewer) {
		// TODO Auto-generated method stub
		super.keyUp(keyEvent, viewer);
		keycode = -1;
	}
	@Override
	public void mouseDrag(MouseEvent mouseEvent, EditPartViewer viewer) {
		
		
		if(viewer!=null && viewer instanceof ScrollingGraphicalViewer){
			ScrollingGraphicalViewer graphicalViewer = (ScrollingGraphicalViewer)viewer;
			StructuredSelection selection = (StructuredSelection)graphicalViewer.getSelection();
			List<UMLEditPart> selectionList = selection.toList();
			
			if(selectionList.size()>1){
				
			}else{
//				System.out.println(mouseEvent.x);
				
			}
			
					
		}
		super.mouseDrag(mouseEvent, viewer);
	}
	@Override
	public void mouseUp(MouseEvent mouseEvent, EditPartViewer viewer) {
		// TODO Auto-generated method stub
		super.mouseUp(mouseEvent, viewer);
	}

}
