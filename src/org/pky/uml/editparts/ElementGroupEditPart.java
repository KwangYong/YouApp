package org.pky.uml.editparts;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.ui.parts.ScrollingGraphicalViewer;
import org.pky.uml.commons.managers.EditPartManager;
import org.pky.uml.commons.managers.ProjectManager;
import org.pky.uml.editparts.common.UMLEditPart;
import org.pky.uml.figures.ElementGroupFigure;
import org.pky.uml.policy.UMLContainerEditPolicy;

public class ElementGroupEditPart extends UMLEditPart{
	@Override
	protected IFigure createFigure() {

		return EditPartManager.getNewFigure(this);
	}

	@Override
	protected void createEditPolicies() {
		installEditPolicy(EditPolicy.CONTAINER_ROLE, new UMLContainerEditPolicy());
	}

	public void refreshVisuals() {
		ElementGroupFigure figure = (ElementGroupFigure)getFigure();

		
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

//		for(int i = 0; i < this.getChildren().size(); i++){
//			((UMLEditPart)this.getChildren().get(i)).refreshVisuals();
//		}
		


        
        
    

	}
	@Override
	public void setSelected(int value) {
		ScrollingGraphicalViewer viewer = ProjectManager.getInstance().getUMLEditor().getScrollingGraphicalViewer();
		//viewer.select(this.getParent());
		super.setSelected(value);
	}

}
