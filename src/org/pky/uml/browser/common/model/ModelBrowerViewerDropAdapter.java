package org.pky.uml.browser.common.model;

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerDropAdapter;
import org.eclipse.swt.dnd.DropTargetEvent;
import org.eclipse.swt.dnd.TransferData;

public class ModelBrowerViewerDropAdapter  extends ViewerDropAdapter {
    public ModelBrowerViewerDropAdapter(Viewer viewer) {
        super(viewer);
    }

    public boolean performDrop(Object data) {
        System.out.println("performDrop " + data.toString());
        if (data instanceof String[]) {
            //	       MessageDialog.openInformation(...);
        }
        return true;
    }

   
  

    public void drop(DropTargetEvent event) {
    	
    	
    }

	@Override
	public boolean validateDrop(Object target, int operation,
			TransferData transferType) {
		// TODO Auto-generated method stub
		return false;
	}
}
//