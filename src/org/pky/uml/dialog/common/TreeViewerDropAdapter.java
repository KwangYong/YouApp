package org.pky.uml.dialog.common;

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerDropAdapter;
import org.eclipse.swt.dnd.DropTargetEvent;
import org.eclipse.swt.dnd.TransferData;
import org.pky.uml.model.command.TreeParent;

public class TreeViewerDropAdapter  extends ViewerDropAdapter {
    public TreeViewerDropAdapter(Viewer viewer) {
        super(viewer);
    }

    public boolean performDrop(Object data) {
        System.out.println("performDrop " + data.toString());
        if (data instanceof String[]) {
            //	       MessageDialog.openInformation(...);
        }
        return true;
    }

    
    public boolean validateDrop(Object target, int operation, TransferData transferType) {
      

        return true;
    }
 


    public void drop(DropTargetEvent event) {
    	try {
    		DialogTreeSimpleFactory treeSimpleFactory =  (DialogTreeSimpleFactory)event.data;
    		
    		TreeParent treeParent = (TreeParent)event.item.getData();
    		treeParent.addChild(treeSimpleFactory.getTreeParent());
    		System.out.println("1111111");
    	}
    	catch (Exception e) {
    		e.printStackTrace();
    	}
    }
}
