package org.pky.uml.editparts;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.XYLayout;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.ui.parts.ScrollingGraphicalViewer;
import org.pky.uml.commons.managers.EditPartManager;
import org.pky.uml.commons.managers.ProjectManager;
import org.pky.uml.editparts.common.UMLDiagramEditPart;
import org.pky.uml.editparts.common.UMLEditPart;
import org.pky.uml.figures.ClassFigure;
import org.pky.uml.figures.UseCaseFigure;
import org.pky.uml.model.UseCaseModel;
import org.pky.uml.policy.UMLXYLayoutEditPolicy;

public class UseCaseEditPart extends UMLEditPart{

	@Override
	protected IFigure createFigure() {

		return EditPartManager.getNewFigure(this);
	}

	@Override
	protected void createEditPolicies() {

	}
	@Override
	protected void fireActivated() {
		// TODO Auto-generated method stub
		super.fireActivated();
	}
	@Override
	protected void fireSelectionChanged() {
		// TODO Auto-generated method stub
		super.fireSelectionChanged();
	}
	public void refreshVisuals() {
		// TODO Auto-generated method stub
		UseCaseFigure figure = (UseCaseFigure)getFigure();

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
	        Dimension size = new Dimension(rect.width(),rect.height());
	        Rectangle r = new Rectangle(loc, size);
	       // getFigure().setBackgroundColor(ColorConstants.tooltipBackground);
	       
	        
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
