package org.pky.uml.editparts;

import org.pky.uml.browser.common.propertybrowser.Property;
import org.pky.uml.editparts.common.UMLEditPart;
import org.pky.uml.figures.EditTextFigure;
import org.pky.uml.figures.common.UMLFigure;

public class EditTextEditPart extends UMLEditPart{
	
	public void refreshVisuals() {
		
		// TODO Auto-generated method stub
		if(getFigure() instanceof UMLFigure){
			EditTextFigure figure = (EditTextFigure)getFigure();
			figure.setText((String)this.getUMLModel().getPropertyValue(Property.ID_TEXT));
			figure.setType((Integer)this.getUMLModel().getPropertyValue(Property.ID_TYPE));
			
		}
		super.refreshVisuals();
	}

}
