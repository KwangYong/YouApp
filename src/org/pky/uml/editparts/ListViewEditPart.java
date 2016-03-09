package org.pky.uml.editparts;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.ui.parts.ScrollingGraphicalViewer;
import org.pky.uml.browser.common.propertybrowser.Property;
import org.pky.uml.commons.managers.EditPartManager;
import org.pky.uml.commons.managers.ProjectManager;
import org.pky.uml.editparts.common.UMLEditPart;
import org.pky.uml.figures.ClassFigure;
import org.pky.uml.figures.ListViewFigure;
import org.pky.uml.figures.common.UMLFigure;
import org.pky.uml.policy.UMLContainerEditPolicy;

public class ListViewEditPart  extends UMLEditPart{


	@Override
	public void refreshVisuals() {
		// TODO Auto-generated method stub
		super.refreshVisuals();

		if(getFigure()!=null){
			ListViewFigure figure = (ListViewFigure)getFigure();
			
			figure.setType((Integer)getUMLModel().getPropertyValue(Property.ID_TYPE));
		}
		
	}
}