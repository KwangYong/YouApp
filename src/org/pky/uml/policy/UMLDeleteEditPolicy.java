package org.pky.uml.policy;

import org.eclipse.gef.commands.Command;
import org.eclipse.gef.requests.GroupRequest;
import org.pky.uml.model.UMLDiagramModel;
import org.pky.uml.model.command.UMLDeleteCommand;
import org.pky.uml.model.common.UMLModel;

public class UMLDeleteEditPolicy extends org.eclipse.gef.editpolicies.ComponentEditPolicy {
	protected Command createDeleteCommand(GroupRequest request) {

		Object parent = getHost().getParent().getModel();

		UMLDeleteCommand command = new UMLDeleteCommand();
		if(parent instanceof UMLDiagramModel){
			command.setParent((UMLDiagramModel)parent);
		}
		command.setChild((UMLModel)getHost().getModel());

		return command;
	}
}
