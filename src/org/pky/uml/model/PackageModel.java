package org.pky.uml.model;

import org.pky.uml.model.common.UMLModel;

public class PackageModel extends UMLModel{
	
	public PackageModel(){
		super(150,150);
		nameEle = new ElementModel("PackageName");
		this.addChild(nameEle);
		this.setName("PackageName");

	}
}
