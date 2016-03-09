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
import org.pky.uml.browser.common.propertybrowser.Property;
import org.pky.uml.commons.managers.EditPartManager;
import org.pky.uml.commons.managers.ProjectManager;
import org.pky.uml.editparts.common.UMLEditPart;
import org.pky.uml.figures.ImageViewFigure;
import org.pky.uml.figures.LayoutFigure;
import org.pky.uml.policy.ContainerHighlightEditPolicy;
import org.pky.uml.policy.UMLContainerEditPolicy;
import org.pky.uml.policy.UMLDeleteEditPolicy;

public class LayoutEditPart extends UMLEditPart{


	@Override
	protected IFigure createFigure() {

		return EditPartManager.getNewFigure(this);
	}

	@Override
	protected void createEditPolicies() {
		installEditPolicy(EditPolicy.CONTAINER_ROLE, new UMLContainerEditPolicy());
		installEditPolicy(EditPolicy.SELECTION_FEEDBACK_ROLE, new ContainerHighlightEditPolicy());

	}

	public void refreshVisuals() {
		//		// TODO Auto-generated method stub
		//		LayoutFigure figure = (LayoutFigure)getFigure();
		//		//		ClassModel model = (ClassModel)getModel();
		//		//		figure.setLocation(model.getLocation());
		//		//		
		//		//		figure.setSize(model.getSize());
		//		//		super.refreshVisuals();
		//
		//		if(figure.getLocation().equals(0, 0)&&figure.getSize().equals(0, 0)&&!ProjectManager.getInstance().isLoad()){
		//			Rectangle rect = this.getParentLocation();
		//			Point loc = new  Point(rect.x,getModel().getLocation().y);
		//			loc.setLocation(figure.getLocation().x+loc.x, figure.getLocation().y+loc.y);
		//			figure.setLocation(loc);
		//			Dimension size = new Dimension(rect.width(),getModel().getSize().height());
		//			figure.setSize(size);
		//		}else{
		//			Rectangle rect = this.getParentLocation();
		//			Point loc = new Point(rect.x,rect.y);//getUMLModel().getLocation();
		//			Dimension size = new Dimension(rect.width(),rect.height());
		//			Rectangle r = new Rectangle(loc, size);
		//			getFigure().setBackgroundColor(ColorConstants.tooltipBackground);
		//
		//
		//			((GraphicalEditPart)getParent()).setLayoutConstraint(this, getFigure(), r);
		//			figure.setSize(size);
		//		}
		
		if(getUMLModel().getPropertyValue(Property.ID_IMG)!=null){
			LayoutFigure figure = (LayoutFigure)getFigure();
			if(figure.getImgData()!=null &&
					figure.getImg() !=null &&
					(figure.getImgData().getImageData().width != getUMLModel().getViewModel().getSize().width|| figure.getImgData().getImageData().height != getUMLModel().getViewModel().getSize().height|| !figure.getImg().equals((String)getUMLModel().getPropertyValue(Property.ID_IMG)))){
					figure.setImgData(null);
			}
			figure.setImg((String)getUMLModel().getPropertyValue(Property.ID_IMG));
			
		}
		super.refreshVisuals();
		
		
		
	}
	@Override
	public void setSelected(int value) {
		ScrollingGraphicalViewer viewer = ProjectManager.getInstance().getUMLEditor().getScrollingGraphicalViewer();
		//viewer.select(this.getParent());
		super.setSelected(value);
	}

	@Override
	public DragTracker getDragTracker(Request request) {
		// TODO Auto-generated method stub
		return super.getDragTracker(request);
	}
}
