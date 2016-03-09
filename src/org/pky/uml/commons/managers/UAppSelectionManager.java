package org.pky.uml.commons.managers;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.gef.EditPart;
import org.eclipse.gef.SelectionManager;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.pky.uml.editparts.ViewEditPart;

public class UAppSelectionManager extends SelectionManager {
	@Override
	public void appendSelection(EditPart editpart) {
		
		try{
			super.appendSelection(editpart);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void setSelection(ISelection newSelection) {
		// TODO Auto-generated method stub
		StructuredSelection selection = (StructuredSelection)newSelection;
		List list = selection.toList();
		ArrayList viewList = new ArrayList();
		for(int i = 0 ; i < list.size(); i ++){
			if(list.get(i) instanceof ViewEditPart){
				viewList.add(list.get(i));
			}
		}
		
		
		super.setSelection(new StructuredSelection(viewList));
	}
	@Override
	public void deselect(EditPart editpart) {
		// TODO Auto-generated method stub
		//		paretRemoveEditPart(editpart);
		super.deselect(editpart);
	}
	@Override
	protected EditPart getFocus() {
		// TODO Auto-generated method stub
		return super.getFocus();

	}
	@Override
	public void setFocus(EditPart part) {
		// TODO Auto-generated method stub
		super.setFocus(part);
	}


}
