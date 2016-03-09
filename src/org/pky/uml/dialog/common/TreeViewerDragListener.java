package org.pky.uml.dialog.common;

import org.eclipse.gef.dnd.TemplateTransfer;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.dnd.DragSourceEvent;
import org.eclipse.swt.dnd.DragSourceListener;
import org.pky.uml.browser.model.model.ModelTreeModel;
import org.pky.uml.commons.managers.ModelBrowserManager;
import org.pky.uml.model.command.TreeParent;
import org.pky.uml.model.common.UMLModel;

public class TreeViewerDragListener  implements DragSourceListener {
	private TreeViewer viewer;
	
	DialogTreeSimpleFactory template = null;

	public TreeViewerDragListener(TreeViewer viewer) {
		this.viewer = viewer;
	}

	public void dragStart(DragSourceEvent event) {
//		template.setTreeParent((TreeParent)event.data);
		template = new DialogTreeSimpleFactory(null);

		TreeSelection iSelection = (TreeSelection)viewer.getSelection();
		TreeParent treeObject = (TreeParent)iSelection.getFirstElement();
		
		if(treeObject!=null)
			template.setTreeParent(treeObject);
		

		TemplateTransfer.getInstance().setTemplate(template);
	}

	public void dragSetData(DragSourceEvent event) {
		event.data = template;
	}

	public void dragFinished(DragSourceEvent event) {
		template = null;


		TemplateTransfer.getInstance().setTemplate(null);
		viewer.refresh();
		System.out.println("22222222222");

	}



}