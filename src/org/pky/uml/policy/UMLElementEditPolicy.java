package org.pky.uml.policy;

import org.eclipse.gef.commands.Command;
import org.eclipse.gef.requests.GroupRequest;
import org.pky.uml.model.UMLDiagramModel;
import org.pky.uml.model.command.UMLDeleteCommand;
import org.pky.uml.model.common.UMLModel;

public class UMLElementEditPolicy extends org.eclipse.gef.editpolicies.ComponentEditPolicy {
    protected Command createDeleteCommand(GroupRequest request) {
        Object parent = getHost().getParent().getModel();
        UMLDeleteCommand deleteCmd = new UMLDeleteCommand();
//        deleteCmd.setParent((UMLDiagramModel)parent);
//        deleteCmd.setChild((UMLModel)getHost().getModel());
        return deleteCmd;
    }
}
