package org.pky.uml.policy;

import java.util.List;

import org.eclipse.gef.EditPart;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.gef.editpolicies.ContainerEditPolicy;
import org.eclipse.gef.requests.CreateRequest;
import org.eclipse.gef.requests.GroupRequest;
import org.pky.uml.model.UMLDiagramModel;
import org.pky.uml.model.command.UMLOrphanChildCommand;
import org.pky.uml.model.common.UMLModel;

public class UMLContainerEditPolicy extends ContainerEditPolicy {
    protected Command getCreateCommand(CreateRequest request) {
        return null;
    }

    public Command getOrphanChildrenCommand(GroupRequest request) {
        List parts = request.getEditParts();
        CompoundCommand result = new CompoundCommand(PolicyCommand.LogicContainerEditPolicy_OrphanCommandLabelText);
        for (int i = 0; i < parts.size(); i++) {
            UMLOrphanChildCommand orphan = new UMLOrphanChildCommand();
            orphan.setChild((UMLModel)((EditPart)parts.get(i)).getModel());
            orphan.setParent((UMLDiagramModel)getHost().getModel());
            orphan.setLabel(PolicyCommand.LogicElementEditPolicy_OrphanCommandLabelText);
            result.add(orphan);
        }
        return result.unwrap();
    }
}
