package org.pky.uml.model.command;

import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.pky.uml.model.common.UMLModel;
import org.pky.uml.policy.PolicyCommand;

public class UMLSetConstraintCommand extends org.eclipse.gef.commands.Command {
	private Point newPos;
	private Dimension newSize;
	private Point oldPos;
	private Dimension oldSize;
	private UMLModel part;

	public void execute() {
		

		oldSize = part.getSize();
		oldPos = part.getLocation();
		redo();

	}

	public String getLabel() {
		if (oldSize.equals(newSize))
			return PolicyCommand.SetLocationCommand_Label_Location;
		return PolicyCommand.SetLocationCommand_Label_Resize;
	}

	public void redo() {
		

	}

	public void setLocation(Rectangle r) {
		setLocation(r.getLocation());
		setSize(r.getSize());
	}

	public void setLocation(Point p) {
		newPos = p;
	}

	public void setPart(UMLModel part) {
		this.part = part;
	}

	public void setSize(Dimension p) {
		newSize = p;
	}

	public void undo() {
		
		part.setSize(oldSize);
		part.setLocation(oldPos);
	}
}
