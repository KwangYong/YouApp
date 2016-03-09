package org.pky.uml.model;

import java.util.ArrayList;

import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.pky.uml.model.common.UMLModel;

public class ClassModel extends UMLModel{




	public ClassModel(){
		super(150,150);
		nameEle = new ElementModel("ClassModel");
//		stereoEle = new ElementModel("Class");

//		attributeGroup = new  ElementGroupModel(true);
//		operationGroup = new  ElementGroupModel(true);


		this.addChild(nameEle);
//		this.addChild(stereoEle);

//		this.addChild(attributeGroup);



		ArrayList list = new ArrayList();
		list.add(new AttributeItem("+ test1:String()"));
		
		addAttributes(list);
		//		addAttribute(new AttributeElementModel("+ attribute:String"));
		/**
		UseCaseModel useCaseModel =new UseCaseModel();

		this.addChild(useCaseModel);
		useCaseModel.setLocation(0, 16);
		 **/		
	}

	public void addAttributes(ArrayList<AttributeItem> list){
//		this.setAttributes(list);
		for(int i = 0 ; i < list.size(); i++){
//			attributeGroup.addChild(new AttributeElementModel(list.get(i)));
		}
	}
	public void addOperations(ArrayList<OperationItem> list){
		//		operationGroup.addChild();

	}
	@Override
	public void setLocation(int x, int y) {
		// TODO Auto-generated method stub
		super.setLocation(x, y);
	}
	@Override
	public void setLocation(Point point) {
		// TODO Auto-generated method stub
		super.setLocation(point);
	}
	@Override
	public void setLocation(Rectangle location) {
		// TODO Auto-generated method stub
		super.setLocation(location);
	}
	

}
