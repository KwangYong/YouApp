package org.pky.uml.policy;

import org.eclipse.gef.commands.Command;
import org.eclipse.gef.requests.GroupRequest;
import org.pky.uml.model.LineModel;
import org.pky.uml.model.command.UMLConnectionCommand;

public class LineEditPolicy extends org.eclipse.gef.editpolicies.ConnectionEditPolicy {
    protected Command getDeleteCommand(GroupRequest request) {
        UMLConnectionCommand c = new UMLConnectionCommand();
        c.setWire((LineModel)getHost().getModel());
        return c;
    }
}
