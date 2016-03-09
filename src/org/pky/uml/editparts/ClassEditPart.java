package org.pky.uml.editparts;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.DragTracker;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.ui.parts.ScrollingGraphicalViewer;
import org.pky.uml.commons.managers.EditPartManager;
import org.pky.uml.commons.managers.ProjectManager;
import org.pky.uml.editparts.common.UMLEditPart;
import org.pky.uml.figures.ClassFigure;
import org.pky.uml.figures.UseCaseFigure;
import org.pky.uml.model.ClassModel;
import org.pky.uml.model.UseCaseModel;
import org.pky.uml.policy.UMLContainerEditPolicy;

public class ClassEditPart extends UMLEditPart{

	@Override
	protected IFigure createFigure() {

		return EditPartManager.getNewFigure(this);
	}

	@Override
	protected void createEditPolicies() {
		installEditPolicy(EditPolicy.CONTAINER_ROLE, new UMLContainerEditPolicy());
	}

	public void refreshVisuals() {
		// TODO Auto-generated method stub
		ClassFigure figure = (ClassFigure)getFigure();
//		ClassModel model = (ClassModel)getModel();
//		figure.setLocation(model.getLocation());
//		
//		figure.setSize(model.getSize());
//		super.refreshVisuals();

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
	@Override
	public void setSelected(int value) {
		ScrollingGraphicalViewer viewer = ProjectManager.getInstance().getUMLEditor().getScrollingGraphicalViewer();
		//viewer.select(this.getParent());
		super.setSelected(value);
	}

}