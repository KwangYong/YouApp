package org.pky.uml.editparts;

import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.PointList;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.widgets.Display;
import org.pky.uml.browser.common.propertybrowser.Property;
import org.pky.uml.commons.managers.ProjectManager;
import org.pky.uml.editparts.common.UMLEditPart;
import org.pky.uml.figures.ButtonModelFigure;
import org.pky.uml.figures.common.UMLFigure;

public class ButtonModelEditPart extends UMLEditPart{
	
	public void refreshVisuals() {
		super.refreshVisuals();
		// TODO Auto-generated method stub
		if(getFigure() instanceof ButtonModelFigure){
			ButtonModelFigure figure = (ButtonModelFigure)getFigure();
			figure.setText((String)	getUMLModel().getPropertyValue(Property.ID_TEXT));
		}

	}
}
