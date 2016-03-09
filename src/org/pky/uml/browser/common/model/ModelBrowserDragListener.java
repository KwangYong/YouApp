package org.pky.uml.browser.common.model;

import org.eclipse.gef.dnd.TemplateTransfer;
import org.eclipse.swt.dnd.DragSourceEvent;
import org.eclipse.swt.dnd.DragSourceListener;
import org.pky.uml.browser.ModelBrowser;
import org.pky.uml.browser.model.model.ModelTreeModel;
import org.pky.uml.commons.managers.ModelBrowserManager;
import org.pky.uml.model.command.TreeSimpleFactory;
import org.pky.uml.model.common.UMLModel;

public class ModelBrowserDragListener implements DragSourceListener {
	private ModelBrowser browser;
	private UMLModel umlModel = null;
	private ModelTreeModel treeModel = null;
	TreeSimpleFactory template = null;

	public ModelBrowserDragListener(ModelBrowser browser) {
		this.browser = browser;
	}

	public void dragStart(DragSourceEvent event) {
		
System.out.println("------------->");
		
        event.doit = true;
        template = new TreeSimpleFactory(null);
        if (template == null)
            event.doit = false;
        ModelBrowserManager.getInstance().initDragUMLModel();
        UMLModel um = ModelBrowserManager.getInstance().getDragUMLModel();
     
        template.setUMLModel(ModelBrowserManager.getInstance().getDragUMLModel());
        template.setModelTreeModel(ModelBrowserManager.getInstance().getDragModelTreeModel());
        TemplateTransfer.getInstance().setTemplate(template);
	
        
	}

	public void dragSetData(DragSourceEvent event) {
		event.data = template;
	}

	public void dragFinished(DragSourceEvent event) {
		template = null;
		ModelBrowserManager.getInstance().successDragUMLModel();

        TemplateTransfer.getInstance().setTemplate(null);
        System.out.println("------------->1");
    
	}

	public UMLModel getUmlModel() {
		return umlModel;
	}

	public ModelTreeModel getTreeModel() {
		return treeModel;
	}

	public void setUmlModel(UMLModel umlModel) {
		this.umlModel = umlModel;
	}

	public void setTreeModel(ModelTreeModel treeModel) {
		this.treeModel = treeModel;
	}


}