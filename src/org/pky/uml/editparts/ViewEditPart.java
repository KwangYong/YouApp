package org.pky.uml.editparts;

import org.eclipse.draw2d.IFigure;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.Request;
import org.eclipse.gef.ui.parts.ScrollingGraphicalViewer;
import org.pky.uml.commons.managers.EditPartManager;
import org.pky.uml.commons.managers.ProjectManager;
import org.pky.uml.commons.managers.UMLEditManager;
import org.pky.uml.editparts.common.UMLEditPart;
import org.pky.uml.figures.ElementFigure;
import org.pky.uml.figures.TextViewFigure;
import org.pky.uml.figures.ViewFigure;
import org.pky.uml.model.TextViewModel;
import org.pky.uml.model.ViewModel;
import org.pky.uml.model.common.UMLElementCellEditorLocator;
import org.pky.uml.policy.UMLContainerEditPolicy;
import org.pky.uml.policy.UMLDeleteEditPolicy;
import org.pky.uml.policy.UMLElementDirectEditPolicy;
import org.pky.uml.policy.UMLNodeEditPolicy;

public class ViewEditPart extends UMLEditPart{

	@Override
	protected IFigure createFigure() {

		return EditPartManager.getNewFigure(this);
	}

	@Override
	protected void createEditPolicies() {
		
		installEditPolicy(EditPolicy.COMPONENT_ROLE, new UMLDeleteEditPolicy());
		installEditPolicy(EditPolicy.CONTAINER_ROLE, new UMLContainerEditPolicy());
		installEditPolicy(EditPolicy.GRAPHICAL_NODE_ROLE, new UMLNodeEditPolicy());
		//		installEditPolicy(EditPolicy.GRAPHICAL_NODE_ROLE, new UMLNodeEditPolicy());
		//		installEditPolicy(EditPolicy.COMPONENT_ROLE, new UMLElementEditPolicy());
		//		super.createEditPolicies();
		if(getModel().getBasicModel() instanceof TextViewModel){
			installEditPolicy(EditPolicy.DIRECT_EDIT_ROLE, new UMLElementDirectEditPolicy());
		}
	}

	public void refreshVisuals() {
		// TODO Auto-generated method stub
		ViewFigure figure = (ViewFigure)getFigure();
		ViewModel model = (ViewModel)getModel();
		figure.setLocation(model.getLocation());

		figure.setSize(model.getSize());

		super.refreshVisuals();
	}
	@Override
	public void setSelected(int value) {
		ScrollingGraphicalViewer viewer = ProjectManager.getInstance().getUMLEditor().getScrollingGraphicalViewer();
		//viewer.select(this.getParent());
		super.setSelected(value);
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

}