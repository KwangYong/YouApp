package org.pky.uml.policy;

import org.eclipse.draw2d.Connection;
import org.eclipse.draw2d.ConnectionAnchor;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.PolylineConnection;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.LayerConstants;
import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.requests.CreateConnectionRequest;
import org.eclipse.gef.requests.ReconnectRequest;
import org.pky.uml.commons.managers.ProjectManager;
import org.pky.uml.editparts.common.UMLDiagramEditPart;
import org.pky.uml.editparts.common.UMLEditPart;
import org.pky.uml.editparts.common.UMLEditPartFactory;
import org.pky.uml.figures.common.UMLFigure;
import org.pky.uml.model.LineModel;
import org.pky.uml.model.command.UMLConnectionCommand;
import org.pky.uml.model.common.UMLModel;

public class UMLNodeEditPolicy extends org.eclipse.gef.editpolicies.GraphicalNodeEditPolicy {
	//ijs090113-2 시작
	boolean isChgHost = false;
	UMLEditPart oldUMLEditPart = null;
	//ijs090113-2 끝
	protected Connection createDummyConnection(Request req) {
		PolylineConnection conn = UMLEditPartFactory.createNewWire(null);
		return conn;
	}

	protected Command getConnectionCompleteCommand(CreateConnectionRequest request) {
		UMLConnectionCommand command = (UMLConnectionCommand)request.getStartCommand();
		//20080822IJS
		command.setTarget(this.getParentModel());
		ConnectionAnchor ctor = getUMLEditPart().getTargetConnectionAnchor(request);
		
			command.setTargetTerminal(getUMLEditPart().mapConnectionAnchorToTerminal(ctor));
		if (ctor == null)
			return null;
		
		//ijs090113-2 끝

		return command;
	}

	protected Command getConnectionCreateCommand(CreateConnectionRequest request) {
		//선 등록
		UMLConnectionCommand command = new UMLConnectionCommand();
		Object type = request.getNewObject();

		command.setWire(new LineModel());

		//		command.setWire(new LineModel());
		//20080822IJS
		command.setSource(getParentModel());
		ConnectionAnchor ctor = getUMLEditPart().getSourceConnectionAnchor(request);
		//seq
		//        if(getUMLEditPart() instanceof LifeLineEditPart){
		//        	LifeLineEditPart lep = (LifeLineEditPart)getUMLEditPart();
		//        	lep.createSourceConnectionAnchor(ctor, m.getId());
		//        	 command.setSourceTerminal(m.getId());
		//        }
		//        else
		command.setSourceTerminal(getUMLEditPart().mapConnectionAnchorToTerminal(ctor));

		request.setStartCommand(command);
		return command;
	}

	/**
	 * Feedback should be added to the scaled feedback layer.
	 * @see org.eclipse.gef.editpolicies.GraphicalEditPolicy#getFeedbackLayer()
	 */
	protected IFigure getFeedbackLayer() {
		/*
		 * Fix for Bug# 66590
		 * Feedback needs to be added to the scaled feedback layer
		 */

		return getLayer(LayerConstants.SCALED_FEEDBACK_LAYER);
	}

	protected UMLEditPart getUMLEditPart() {
		return (UMLEditPart)getHost();
	}

	protected UMLModel getUMLModel() {
		return (UMLModel)getHost().getModel();
	}

	//20080822IJS 시작

	public UMLModel getParentModel(){
		return this.getParentModel(this.getUMLEditPart());

	}

	public UMLModel  getParentModel(UMLEditPart part){
		if(part.getParent() instanceof UMLDiagramEditPart){

			return (UMLModel)part.getModel();
		}
		else if(part.getParent()!=null
				&& part.getParent() instanceof UMLEditPart){
			return this.getParentModel((UMLEditPart )part.getParent());
		}
		return null;

	}
	//20080822IJS 끝



	protected Command getReconnectTargetCommand(ReconnectRequest request) {

		UMLConnectionCommand cmd = new UMLConnectionCommand();
		//PKY 08101531 S
		((LineModel)request.getConnectionEditPart().getModel()).getTarget().getOutputs().remove((LineModel)request.getConnectionEditPart().getModel());
		((LineModel)request.getConnectionEditPart().getModel()).getTarget().getInputs().remove((LineModel)request.getConnectionEditPart().getModel());
		//PKY 08101531 E

		cmd.setWire((LineModel)request.getConnectionEditPart().getModel());
		ConnectionAnchor ctor = getUMLEditPart().getTargetConnectionAnchor(request);
		//20080822IJS
		cmd.setTarget(getParentModel());
		cmd.setTargetTerminal(getUMLEditPart().mapConnectionAnchorToTerminal(ctor));
		changeConnection(request);
		return cmd;
	}

	protected Command getReconnectSourceCommand(ReconnectRequest request) {

		UMLConnectionCommand cmd = new UMLConnectionCommand();
//		((LineModel)request.getConnectionEditPart().getModel()).setDetailProp(((LineModel)request.getConnectionEditPart().getModel()).getDetailProp());

		ConnectionAnchor ctor = getUMLEditPart().getSourceConnectionAnchor(request);
		//20080822IJS
		//PKY 08101531 S
		((LineModel)request.getConnectionEditPart().getModel()).getSource().getOutputs().remove((LineModel)request.getConnectionEditPart().getModel());
		((LineModel)request.getConnectionEditPart().getModel()).getSource().getInputs().remove((LineModel)request.getConnectionEditPart().getModel());
		//PKY 08101531 E

		cmd.setSource(this.getParentModel());
		cmd.setSourceTerminal(getUMLEditPart().mapConnectionAnchorToTerminal(ctor));
		cmd.setWire((LineModel)request.getConnectionEditPart().getModel()); //PKY 08101505 S
		changeConnection(request);
		return cmd;
	}
	protected void changeConnection(ReconnectRequest request){
		

	}

	protected UMLFigure getNodeFigure() {
		return (UMLFigure)((GraphicalEditPart)getHost()).getFigure();
	}
	public Command getCommand(Request request) {
		if (REQ_RECONNECT_TARGET.equals(request.getType())||REQ_RECONNECT_SOURCE.equals(request.getType())){
			
		}
		return super.getCommand(request);
	}

}
