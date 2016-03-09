package org.pky.uml.model;

import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.geometry.Rectangle;
import org.pky.uml.figures.common.UMLFigure;
import org.pky.uml.model.common.UMLModel;

public class TitleBarModel extends UMLModel{


	public TitleBarModel(){
		
		super(30,30);
		
		nameEle = new ElementModel("ClassModel");
		
		this.addChild(nameEle);
	}
	
}