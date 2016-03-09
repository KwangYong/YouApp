package org.pky.uml.editparts;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.ui.parts.ScrollingGraphicalViewer;
import org.pky.uml.commons.managers.EditPartManager;
import org.pky.uml.commons.managers.ProjectManager;
import org.pky.uml.commons.managers.UMLEditManager;
import org.pky.uml.editparts.common.UMLDiagramEditPart;
import org.pky.uml.editparts.common.UMLEditPart;
import org.pky.uml.figures.ElementFigure;
import org.pky.uml.model.AttributeElementModel;
import org.pky.uml.model.OperationElementModel;
import org.pky.uml.model.OperationItem;
import org.pky.uml.model.ParameterItem;
import org.pky.uml.model.common.UMLElementCellEditorLocator;
import org.pky.uml.policy.UMLElementDirectEditPolicy;

public class ElementEditPart extends UMLEditPart{


	@Override
	protected IFigure createFigure() {

		return EditPartManager.getNewFigure(this);
	}

	@Override
	protected void createEditPolicies() {
		installEditPolicy(EditPolicy.GRAPHICAL_NODE_ROLE, null);
		installEditPolicy(EditPolicy.DIRECT_EDIT_ROLE, new UMLElementDirectEditPolicy());
	}

	public void refreshVisuals() {
		ElementFigure figure = (ElementFigure)getFigure();

		if(getModel() instanceof AttributeElementModel || getModel() instanceof OperationElementModel){

			figure.setText(getModel().toString());	
		}else{

			figure.setText(getModel().getName());


		}

		if(figure.getLocation().equals(0, 0)&&figure.getSize().equals(0, 0)){
			Rectangle rect = this.getParentLocation();
			Point loc = new  Point(rect.x,getModel().getLocation().y);
			loc.setLocation(figure.getLocation().x+loc.x, figure.getLocation().y+loc.y);
			figure.setLocation(loc);
			Dimension size = new Dimension(rect.width(),getModel().getSize().height());
			figure.setSize(size);
		}else{
			Rectangle rect = this.getParentLocation();
			Point loc = new Point(rect.x,rect.y);//getUMLModel().getLocation();
			Dimension size = new Dimension(rect.width(),getModel().getSize().height());
			Rectangle r = new Rectangle(loc, size);
			getFigure().setBackgroundColor(ColorConstants.tooltipBackground);


			((GraphicalEditPart)getParent()).setLayoutConstraint(this, getFigure(), r);		
			figure.setSize(size);
		}
	}

	protected void performDirectEdit() {
//		new UMLEditManager(this, new UMLElementCellEditorLocator((ElementFigure)getFigure())).show();

	}

	public void performRequest(Request request) {

		performDirectEdit();
	}

	public void performRequest() {
		//		if (request.getType() == RequestConstants.REQ_DIRECT_EDIT)
		performDirectEdit();
	}
}