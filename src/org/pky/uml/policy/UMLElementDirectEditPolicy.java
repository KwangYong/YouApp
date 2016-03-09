package org.pky.uml.policy;

import org.eclipse.draw2d.FigureCanvas;
import org.eclipse.swt.graphics.Point;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.DirectEditPolicy;
import org.eclipse.gef.requests.DirectEditRequest;
import org.pky.uml.celleditor.AttributeQuickCellEditor;
import org.pky.uml.commons.managers.ProjectManager;
import org.pky.uml.editparts.ElementEditPart;
import org.pky.uml.editparts.TextViewEditPart;
import org.pky.uml.figures.ElementFigure;
import org.pky.uml.figures.TextViewFigure;
import org.pky.uml.model.ElementModel;
import org.pky.uml.model.TextViewModel;
import org.pky.uml.model.command.UMLElementLabelCommand;

public class UMLElementDirectEditPolicy extends DirectEditPolicy {
    /** @see DirectEditPolicy#getDirectEditCommand(DirectEditRequest) */
    protected Command getDirectEditCommand(DirectEditRequest edit) {
        String labelText = (String)edit.getCellEditor().getValue();
        TextViewEditPart label = (TextViewEditPart)getHost();
        UMLElementLabelCommand command = new UMLElementLabelCommand((TextViewModel)label.getModel(), labelText);
        return command;
    }

    /** @see DirectEditPolicy#showCurrentEditValue(DirectEditRequest) */
    protected void showCurrentEditValue(DirectEditRequest request) {
        String value = (String)request.getCellEditor().getValue();
        ((TextViewFigure)getHostFigure()).setText(value);
        //hack to prevent async layout from placing the cell editor twice.
        getHostFigure().getUpdateManager().performUpdate();

       
		
    }
    @Override
    protected void showDirectEditFeedback(DirectEditRequest request) {
    	// TODO Auto-generated method stub
    	super.showDirectEditFeedback(request);
    }
}
