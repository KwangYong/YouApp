package org.pky.uml.model;

import java.util.ArrayList;
import java.util.HashMap;

import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.pky.uml.commons.managers.LayoutManager;
import org.pky.uml.commons.managers.ProjectManager;
import org.pky.uml.model.common.UMLModel;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class ViewModel extends UMLModel{
	private UMLModel basiclModel = null;

	public ViewModel(UMLModel basicModel){
		super(basicModel.getSize().width,basicModel.getSize().height);
		this.basiclModel = basicModel;	
		this.addChild(this.basiclModel);
		//		this.location = new Rectangle(0, 0, -1, -1);
		//µî·Ï 
		if(basicModel instanceof EditTextModel){
			descriptors = new IPropertyDescriptor[] {
					nameProp,textProp,editTextProp,xProp,yProp,widthProp,heightProp
			};
		}else if(basicModel instanceof LayoutModel){
			descriptors = new IPropertyDescriptor[] {
					nameProp,textProp,isMainProp,isTitleMainProp,isIOSViewRateProp,backgourndURLProp,isAutoMoveProp,xProp,yProp
			};
		}else if(basicModel instanceof ListViewModel){
			descriptors = new IPropertyDescriptor[] {
					nameProp,listViewItemProp,xProp,yProp,widthProp,heightProp
			};
		}else if(basicModel instanceof ButtonModel){
			descriptors = new IPropertyDescriptor[] {
					nameProp,textProp,xProp,yProp,widthProp,heightProp
			};
		}else if(basicModel instanceof UMLDiagramModel){
			descriptors = new IPropertyDescriptor[] {
					nameProp
			};
		}else if(basicModel instanceof WebViewModel){
			descriptors = new IPropertyDescriptor[] {
					nameProp,webViewURLProp,xProp,yProp,widthProp,heightProp
			};
		}else if(basicModel instanceof ImageViewModel){
			descriptors = new IPropertyDescriptor[] {
					nameProp,imageURLProp,xProp,yProp,widthProp,heightProp
			};
		}else if(basicModel instanceof VideoModel){
			descriptors = new IPropertyDescriptor[] {
					nameProp,viedoURLProp,viedoWebURLProp,xProp,yProp,widthProp,heightProp
			};
		}else if(basicModel instanceof GoogleMapModel){
			descriptors = new IPropertyDescriptor[] {
					nameProp,goolgeMapTypeProp,goolgeMapKeyProp,xProp,yProp,widthProp,heightProp
			};
		}else if(basicModel instanceof TextViewModel){
			descriptors = new IPropertyDescriptor[] {
					nameProp,textSizeProp,colorProp,xProp,yProp,widthProp,heightProp
			};
		}else if(basicModel instanceof YoutubeModel){
			descriptors = new IPropertyDescriptor[] {
					nameProp,webViewURLProp,xProp,yProp,widthProp,heightProp
			};
		}
		LayoutManager.getInstance().initSourcePropertyAndroid(this);
	}

	public UMLModel getBasiclModel() {
		return basiclModel;
	}

	public void setBasiclModel(UMLModel basiclModel) {
		this.basiclModel = basiclModel;
	}
	@Override
	public void setSize(Dimension size) {
		// TODO Auto-generated method stub
		if(this.getBasiclModel() instanceof LayoutModel){

		}else{

			super.setSize(size);			
		}

	}

	@Override
	public void putUata(String id, Object value) {
		// TODO Auto-generated method stub
		this.getBasiclModel().putUata(id, value);
	}
	@Override
	public Object getUdata(String key) {
		// TODO Auto-generated method stub
		return this.getBasiclModel().getUdata(key);
	}
	@Override
	public String getUdataString(String key) {
		// TODO Auto-generated method stub
		return this.getBasiclModel().getUdataString(key);
	}
	@Override
	public HashMap<String, Object> getUdata() {
		// TODO Auto-generated method stub
		return this.getBasiclModel().getUdata();
	}
	@Override
	public Element writeLayoutAndroid(Document doc) {
		// TODO Auto-generated method stub
		return basiclModel.writeLayoutAndroid(doc);
	}
	@Override
	public void writeLayoutIOS() {
		// TODO Auto-generated method stub
		basiclModel.writeLayoutIOS();
	}
	@Override
	public String writeSourceIOS() {
		// TODO Auto-generated method stub
		return super.writeSourceIOS();
	}
	public String writeSourceAndroid(){

		return basiclModel.writeSourceAndroid();
	}
	public String getID(){
		return this.uDatas.getID();
	}
	public void setID(String id){
		this.uDatas.setID(id);
		
		for(int i =0; i < this.getActionList().size(); i ++){
			this.getActionList().get(i).modelID = id;
		}
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		if(basiclModel!=null)
			return getViewModel().getID() + ":"+ getBasiclModel().getID();
		else 
			return "";
	}
}
