package org.pky.uml.model.command;

import org.pky.uml.commons.managers.ProjectManager;
import org.pky.uml.model.LineModel;
import org.pky.uml.model.common.LineBendpointModel;

public class UMLCreateBendpointCommand extends UMLBendpointCommand {
    public void execute() {

        LineBendpointModel wbp = new LineBendpointModel();
        wbp.setRelativeDimensions(getFirstRelativeDimension(), getSecondRelativeDimension());
      

        getWire().insertBendpoint(getIndex(), wbp);
        
    }

    public void undo() {

        super.undo();
        getWire().removeBendpoint(getIndex());
    }
}
