package org.pky.uml.model.command;

import org.eclipse.gef.requests.SimpleFactory;
import org.pky.uml.browser.model.model.ModelTreeModel;
import org.pky.uml.commons.managers.ProjectManager;
import org.pky.uml.model.UMLDiagramModel;
import org.pky.uml.model.ViewModel;
import org.pky.uml.model.common.UMLModel;

public class TreeSimpleFactory extends SimpleFactory {
	private UMLModel uMLModel = null;
	private ModelTreeModel modelTreeModel = null;
	private UMLDiagramModel diagramModel;

	public TreeSimpleFactory(Class aClass) {
		super(aClass);	
	}

	public TreeSimpleFactory() {
		super(null);
	}

	public Object getNewObject() {
		try {
			return this.createModel(uMLModel);
		} catch (Exception exc) {
			return null;
		}
	}
	private UMLModel createModel(UMLModel umlmodel){
		ProjectManager.getInstance().setCreate(true);
		if(umlmodel==null){
			umlmodel =  (UMLModel)super.getNewObject();	
			
		}
		ViewModel viewModel = new ViewModel(umlmodel);
		viewModel.setModelTreeModel(umlmodel.getModelTreeModel());
		ProjectManager.getInstance().setCreate(false);
		return viewModel;
	}

	public void setUMLModel(UMLModel p) {
		uMLModel = p;
	}

	public UMLModel getUMLModel() {
		return uMLModel;
	}

	public ModelTreeModel getModelTreeModel() {
		return modelTreeModel;
	}

	public void setModelTreeModel(ModelTreeModel modelTreeModel) {
		this.modelTreeModel = modelTreeModel;
	}

	public UMLDiagramModel getDiagramModel() {
		return diagramModel;
	}

	public void setDiagramModel(UMLDiagramModel diagramModel) {
		this.diagramModel = diagramModel;
	}


	
}
