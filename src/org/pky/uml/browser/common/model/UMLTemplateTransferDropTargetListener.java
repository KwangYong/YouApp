package org.pky.uml.browser.common.model;

import org.eclipse.gef.EditPartViewer;
import org.eclipse.gef.dnd.TemplateTransferDropTargetListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.dnd.DropTargetEvent;
import org.pky.uml.browser.model.model.ModelTreeModel;
import org.pky.uml.commons.managers.ModelBrowserManager;
import org.pky.uml.commons.managers.ProjectManager;
import org.pky.uml.model.command.TreeSimpleFactory;
import org.pky.uml.model.common.UMLModel;

public class UMLTemplateTransferDropTargetListener extends TemplateTransferDropTargetListener {
	public UMLTemplateTransferDropTargetListener(EditPartViewer viewer) {
		super(viewer);
	}

	public void dragOver(DropTargetEvent event) {
		try {
			super.dragOver(event);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void drop(DropTargetEvent event) {
		Object obj = getViewer();

		boolean isDrage = false;
		try{
			if(event.data!=null && event.data instanceof TreeSimpleFactory ){
				TreeSimpleFactory treeSimple = (TreeSimpleFactory)event.data;
				if(ModelBrowserManager.getInstance().getDragModelTreeModel()!=null){
					if(ProjectManager.getInstance()!=null && ModelBrowserManager.getInstance().getModelBrowser()!=null && ModelBrowserManager.getInstance().getModelBrowser().getViewer()!=null){
						IStructuredSelection istr= (IStructuredSelection)ModelBrowserManager.getInstance().getModelBrowser().getViewer().getSelection();
						java.util.List list = istr.toList();
						for(int i = 0; i < list.size(); i++){
							if(list.get(i) instanceof ModelTreeModel){
								if(((ModelTreeModel)list.get(i)).getBasicModel()!=null){
									treeSimple.setUMLModel((UMLModel)((ModelTreeModel)list.get(i)).getBasicModel());
									treeSimple.setModelTreeModel((ModelTreeModel)list.get(i));
									isDrage=true;
									super.drop(event);
								}
							}
						}
					}
				}else{
					
//					treeSimple.setUMLModel((UMLModel)treeSimple.getNewObject());
//					treeSimple.setModelTreeModel(ProjectManager.getInstance().getActiveDiagram().getModelTreeModel().getParentTreeModel());
//					
//					super.drop(event);
//					treeSimple = new TreeSimpleFactory(null);
	
				}
			}
			if(!isDrage){
				super.drop(event);
			}
		}catch(Exception e){
			e.printStackTrace();
		}


	}
}
