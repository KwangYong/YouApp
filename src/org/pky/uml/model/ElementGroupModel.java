package org.pky.uml.model;

import org.pky.uml.model.common.UMLModel;

public class ElementGroupModel extends UMLModel {

	private boolean isRoundDraw = false;
	
	public ElementGroupModel() {
		// TODO Auto-generated constructor stub
		setSize(150,20);
	}
	public ElementGroupModel(boolean isRoundDraw){
		this();
		this.isRoundDraw = isRoundDraw;
	}
	

	
	
}
