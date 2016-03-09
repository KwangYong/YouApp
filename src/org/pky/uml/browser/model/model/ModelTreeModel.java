package org.pky.uml.browser.model.model;

import java.util.ArrayList;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.ui.views.properties.IPropertySource;
import org.pky.uml.commons.managers.ModelBrowserManager;
import org.pky.uml.commons.managers.ProjectManager;
import org.pky.uml.model.common.UMLModel;

public class ModelTreeModel implements IAdaptable {

	private UMLModel basicModel;
	private ModelTreeModel parentTreeModel;
	private ArrayList<ModelTreeModel> children = new ArrayList<ModelTreeModel>();
	private String name;
	private String stereo;
	private int modelType = 0;
	private String id;
	public ModelTreeModel(){
		id = ProjectManager.getInstance().getNewID(this);
	}
	public ModelTreeModel(String text){

		this.setName(text);
		id = ProjectManager.getInstance().getNewID(this);
	}
	public ModelTreeModel(UMLModel basicModel){
		this.setName(basicModel.getName());
		this.basicModel = basicModel;
		basicModel.setModelTreeModel(this);
		id = ProjectManager.getInstance().getNewID(this);
	}



	public Object getAdapter(Class adapter) {
		if (adapter == IPropertySource.class) {
			if (basicModel != null)
				return basicModel;
		}
		return null;
	}

	public void getPackage(java.util.ArrayList p){
		if(this.getParentTreeModel()!=null){
			p.add(this.getName());
			this.getParentTreeModel().getPackage(p);
		}
		else
			return ;
	}

	public void addChild(ModelTreeModel mbTreeModel){
		mbTreeModel.setParentTreeModel(this);
		this.children.add(mbTreeModel);

	}
	public void removeChild(ModelTreeModel mbTreeModel){
		this.children.remove(mbTreeModel);

	}
	public UMLModel getBasicModel() {
		return basicModel;
	}

	public ModelTreeModel getParentTreeModel() {
		return parentTreeModel;
	}

	public String getName() {
		return name;
	}

	public String getStereo() {
		return stereo;
	}

	public int getModelType() {
		return modelType;
	}

	public void setBasicModel(UMLModel basicModel) {
		this.basicModel = basicModel;
	}

	public void setParentTreeModel(ModelTreeModel parentTreeModel) {
		this.parentTreeModel = parentTreeModel;
	}

	public void setName(String name) {
		this.name = name;
		if(ModelBrowserManager.getInstance().getModelBrowser()!=null && ModelBrowserManager.getInstance().getModelBrowser().getViewer()!=null){
			ModelBrowserManager.getInstance().getModelBrowser().getViewer().refresh(this);
		}
	}

	public void setStereo(String stereo) {
		this.stereo = stereo;
	}

	public void setModelType(int modelType) {
		this.modelType = modelType;
	}

	public ModelTreeModel[] getChildren() {
		return (ModelTreeModel[]) children.toArray(new ModelTreeModel[children.size()]);
	}
	public boolean hasChildren() {
		return children.size() > 0;
	}
	public ArrayList<ModelTreeModel> getChildrens() {
		return children;
	}


	public void setChildren(ArrayList<ModelTreeModel> children) {
		this.children = children;
	}


	public String toString() {
		stereo = this.getStereo();
		if(stereo!=null){
			return stereo+getName();
		}
		else{
			return getName();
		}
	}
	public String getID() {
		return id;
	}
	public void setID(String id) {
		this.id = id;
	}
}
