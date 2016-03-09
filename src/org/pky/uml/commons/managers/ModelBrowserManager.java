package org.pky.uml.commons.managers;

import java.util.ArrayList;

import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.pky.uml.browser.ModelBrowser;
import org.pky.uml.browser.model.model.ModelTreeModel;
import org.pky.uml.editor.UMLEditor;
import org.pky.uml.model.UMLDiagramModel;
import org.pky.uml.model.common.UMLModel;

public class ModelBrowserManager {


	private static ModelBrowserManager instance;
	private boolean isDrag =false;

	private UMLModel dragUMLModel = null;
	private ModelTreeModel dragModelTreeModel = null;
	private ModelBrowser modelBrowser;

	public static ModelBrowserManager getInstance() {
		if (instance == null) {
			instance = new ModelBrowserManager();
			return instance;
		}
		else {
			return instance;
		}
	}


	public void initDragUMLModel() {
		try{
			this.isDrag = true;
			TreeSelection umlTreeModels = (TreeSelection)ModelBrowserManager.getInstance().getModelBrowser().getViewer().getSelection();
			ModelTreeModel uMLTreeModel = ModelBrowserManager.getInstance().getModelBrowser().getTreeModelSelected();
			Object obj = uMLTreeModel.getBasicModel();
			if (obj instanceof UMLModel)
				this.setDragUMLModel((UMLModel)obj);
			this.setDragModelTreeModel(uMLTreeModel);
		}catch(Exception e){
			e.printStackTrace();
		}
	}



	public void successDragUMLModel() {
		this.isDrag = false;
		this.setDragUMLModel(null);
		this.setDragModelTreeModel(null);
	}


	public ModelTreeModel getDragModelTreeModel() {
		if (isDrag) {
			return this.dragModelTreeModel;
		}
		else {
			return null;
		}
	}


	public void openDiagram(UMLDiagramModel opendg){

		IWorkbenchWindow workbenchWindow = ProjectManager.getInstance().window;

		try {
			UMLEditor u = (UMLEditor)workbenchWindow.getActivePage().openEditor(opendg.getiEditorInput(), UMLEditor.ID);
			u.setTitleName(opendg.getName());
		} catch (PartInitException e) {
			e.printStackTrace();
		}


	}
	public void open(ModelTreeModel modelTreeModel){
		try{
			ModelDataManager.getInstance().setLoad(true);
			if(modelTreeModel!=null){
				if(modelTreeModel.getBasicModel()!=null && modelTreeModel.getBasicModel() instanceof UMLDiagramModel){
					ProjectManager.getInstance().setLoad(true);
					UMLDiagramModel diagramModel = (UMLDiagramModel)modelTreeModel.getBasicModel();					
					ProjectManager.getInstance().setOpenDiagramModel(diagramModel);
					UMLEditor u = (UMLEditor)ProjectManager.getInstance().window.getActivePage().openEditor(diagramModel.getiEditorInput(), UMLEditor.ID);
					u.setTitleName(diagramModel.getName());
					ProjectManager.getInstance().setOpenDiagramModel(null);
					ProjectManager.getInstance().setLoad(false);
				}

			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			ModelDataManager.getInstance().setLoad(false);
		}

	}

	
	public ModelTreeModel getSearchID(ModelTreeModel model,String id){
		for(int i = 0; i < model.getChildren().length; i ++){

			if(model.getChildren()[i].getID().equals(id)){
					return model.getChildren()[i]; 
			}else{
				return getSearchID(model.getChildren()[i],id);	
			}

				

		}

	return null;	
	}
	
	//모델 타입별 리스트 출력 
	public ArrayList<UMLModel> getList(ModelTreeModel treeModel,int modelType){
		ArrayList list = new ArrayList();
		return getList(this.getModelBrowser().getRoot(),modelType,list);
	}
	private ArrayList<UMLModel> getList(ModelTreeModel model,int modelType,ArrayList list){
		for(int i = 0; i < model.getChildren().length; i ++){
			if(modelType==-1){
				if(!list.contains(model.getChildren()[i].getBasicModel())){
					list.add(model.getChildren()[i].getBasicModel());
				}
			}else{
				if(ProjectManager.getInstance().getModelType(model.getChildren()[i].getBasicModel().getBasicModel())==modelType){
					if(!list.contains(model.getChildren()[i].getBasicModel())){
						list.add(model.getChildren()[i].getBasicModel());

					}
				}
			}
			getList(model.getChildren()[i],modelType,list);	

		}

		return list;
	}
	public UMLModel getDragUMLModel() {
		return dragUMLModel;
	}
	public void setDragUMLModel(UMLModel dragUMLModel) {
		this.dragUMLModel = dragUMLModel;
	}
	public void setDragModelTreeModel(ModelTreeModel dragModelTreeModel) {
		this.dragModelTreeModel = dragModelTreeModel;
	}
	public ModelBrowser getModelBrowser() {
		return modelBrowser;
	}
	public void setModelBrowser(ModelBrowser modelBrowser) {
		this.modelBrowser = modelBrowser;
	}

}
