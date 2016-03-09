package org.pky.uml.editparts;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.Request;
import org.eclipse.swt.graphics.RGB;
import org.pky.uml.browser.common.propertybrowser.Property;
import org.pky.uml.commons.managers.UMLEditManager;
import org.pky.uml.editparts.common.UMLEditPart;
import org.pky.uml.figures.ElementFigure;
import org.pky.uml.figures.TextViewFigure;
import org.pky.uml.model.AttributeElementModel;
import org.pky.uml.model.OperationElementModel;
import org.pky.uml.model.common.UMLElementCellEditorLocator;
import org.pky.uml.policy.UMLElementDirectEditPolicy;

public class TextViewEditPart extends UMLEditPart{
	
	
	@Override
	protected void createEditPolicies() {
		installEditPolicy(EditPolicy.GRAPHICAL_NODE_ROLE, null);
		installEditPolicy(EditPolicy.DIRECT_EDIT_ROLE, new UMLElementDirectEditPolicy());
	}


	protected void performDirectEdit() {
		new UMLEditManager(this, new UMLElementCellEditorLocator((TextViewFigure)getFigure())).show();

	}

	public void performRequest(Request request) {

		performDirectEdit();
	}

	public void performRequest() {
		//		if (request.getType() == RequestConstants.REQ_DIRECT_EDIT)
		performDirectEdit();
	}
	public void refreshVisuals() {
		// TODO Auto-generated method stub
		TextViewFigure figure = (TextViewFigure)getFigure();
		figure.setText((String)getUMLModel().getPropertyValue(Property.ID_TEXT));
		figure.setFontSize(Integer.parseInt((String)getUMLModel().getPropertyValue(Property.ID_TEXT_SIZE)));
		figure.setColor((RGB)getUMLModel().getPropertyValue(Property.ID_COLOR));
		super.refreshVisuals();
		
		
		
	}

}
