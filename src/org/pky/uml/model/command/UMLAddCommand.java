package org.pky.uml.model.command;

import org.pky.uml.model.UMLDiagramModel;
import org.pky.uml.model.common.UMLModel;
import org.pky.uml.policy.PolicyCommand;

public class UMLAddCommand extends org.eclipse.gef.commands.Command {
    private UMLModel child;
    private UMLDiagramModel parent;
    private int index = -1;

    public UMLAddCommand() {
        super(PolicyCommand.AddCommand_Label);
    }

    public void execute() {
    	

    	if (index < 0) {
            parent.addChild(child);
        }
        else {
            parent.addChild(child, index);
        }
    	
        
       
        
    }

    public UMLDiagramModel getParent() {
        return parent;
    }

    public void redo() {
        if (index < 0)
            parent.addChild(child);
        else
            parent.addChild(child, index);
    }

    public void setChild(UMLModel subpart) {
        child = subpart;
    }

    public void setIndex(int i) {
        index = i;
    }

    public void setParent(UMLDiagramModel newParent) {
        parent = newParent;
    }

    public void undo() {

		
        parent.removeChild(child);
        
       
    }
}
