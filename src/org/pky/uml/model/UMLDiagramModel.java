package org.pky.uml.model;

import org.eclipse.ui.IEditorInput;
import org.pky.uml.editor.LogicEditorInput;
import org.pky.uml.editor.UMLEditor;
import org.pky.uml.editor.UMLEditorInput;
import org.pky.uml.model.common.UMLModel;

public class UMLDiagramModel extends UMLModel{

	private UMLEditor umlEditor = null; 
	IEditorInput iEditorInput = null;
	

	public UMLDiagramModel(){
		super();
	}
	public UMLDiagramModel(String name){
		super(name);
	}
	public UMLEditor getUmlEditor() {
		return umlEditor;
	}
	public void setUmlEditor(UMLEditor umlEditor) {
		this.umlEditor = umlEditor;
	}
	public IEditorInput getiEditorInput() {
		return iEditorInput;
	}
	public void setiEditorInput(LogicEditorInput input) {
		this.iEditorInput = input;
	}
	@Override
	public void addChild(UMLModel model, int index) {
		// TODO Auto-generated method stub
		super.addChild(model, index);
	}
	@Override
	public void removeChild(UMLModel model) {
		// TODO Auto-generated method stub
		model.getBasicModel().getParentModels().remove(model.getViewModel());
		super.removeChild(model);
	}
	
}
