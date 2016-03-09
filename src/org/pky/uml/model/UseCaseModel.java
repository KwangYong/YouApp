package org.pky.uml.model;

import org.pky.uml.model.common.UMLModel;

public class UseCaseModel extends UMLModel {
	
//	ElementModel nameElementModel = null; 
	public UseCaseModel(){
		super(150,30);

		
		nameEle = new ElementModel("UseCase");
		
		this.addChild(nameEle);
		
		
	}

}
