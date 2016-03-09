package org.pky.uml.policy;

import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.RequestConstants;
import org.eclipse.swt.graphics.Color;
import org.pky.uml.commons.managers.ProjectManager;
import org.pky.uml.editparts.common.UMLEditPart;
import org.pky.uml.figures.common.UMLFigure;

public class ContainerHighlightEditPolicy 
extends org.eclipse.gef.editpolicies.GraphicalEditPolicy
{

	private Color revertColor;

	public void eraseTargetFeedback(Request request){
//		ProjectManager.getInstance().getOpenDiagramModel().getShadowModel().setLocation(-1,-1,0,0);
		ProjectManager.getInstance().setSelectShodwID("");
	}

	private Color getContainerBackground(){
		return getContainerFigure().getBackgroundColor();
	}

	private IFigure getContainerFigure(){
		return ((GraphicalEditPart)getHost()).getFigure();
	}

	public EditPart getTargetEditPart(Request request){
		return request.getType().equals(RequestConstants.REQ_SELECTION_HOVER) ?
				getHost() : null;
	}

	private void setContainerBackground(Color c){
		getContainerFigure().setBackgroundColor(c);
	}

	protected void showHighlight(){
		UMLEditPart umlEditPart = (UMLEditPart)getHost();
		
		paintHighlight();

//		paintHighlight();
		
	}

	protected void paintHighlight() {
		UMLEditPart umlEditPart = (UMLEditPart)getHost();
		ProjectManager.getInstance().setSelectShodwID(umlEditPart.getUMLModel().getID());
		umlEditPart.getUMLModel().fireProperty();
		
	}
	public void showTargetFeedback(Request request){
		//		if(request.getType().equals(RequestConstants.REQ_MOVE) ||
		//				request.getType().equals(RequestConstants.REQ_ADD) ||
		//				request.getType().equals(RequestConstants.REQ_CLONE) ||
		//				request.getType().equals(RequestConstants.REQ_CONNECTION_START) ||
		//				request.getType().equals(RequestConstants.REQ_CONNECTION_END) ||
		//				request.getType().equals(RequestConstants.REQ_CREATE)
		//				)

		showHighlight();


	}

}