package org.pky.uml.model.command;

import java.util.List;

import org.eclipse.draw2d.geometry.Point;
import org.eclipse.gef.commands.Command;
import org.pky.uml.commons.managers.ProjectManager;
import org.pky.uml.model.ElementGroupModel;
import org.pky.uml.model.ElementModel;
import org.pky.uml.model.UMLDiagramModel;
import org.pky.uml.model.common.UMLModel;
import org.pky.uml.policy.PolicyCommand;

public class UMLOrphanChildCommand extends Command {
	private Point oldLocation;
	private UMLDiagramModel diagram;
	private UMLModel child;
	private int index;

	public UMLOrphanChildCommand() {
		super(PolicyCommand.OrphanChildCommand_Label);
	}

	public void execute() {


		
		List children = diagram.getChildren();
		index = children.indexOf(child);
		oldLocation = child.getLocation();
		diagram.removeChild(child);
		/**
		//등록
		if (child instanceof PortContainerModel) {
			PortContainerModel ipc = (PortContainerModel)child;
			ipc.orphanChildPort(null, diagram);
		}
		if (child instanceof TextAttachModel) {
			TextAttachModel ipc = (TextAttachModel)child;
			ipc.removeTextAttachParentDiagram(diagram, null);
		}
		**/
	}

	public void redo() {
				diagram.removeChild(child);
	}

	public void setChild(UMLModel child) {
		this.child = child;
	}

	public void setParent(UMLDiagramModel parent) {
		diagram = parent;
	}

	public void undo() {
		//
		child.setLocation(oldLocation);
		diagram.addChild(child, index);
		/**
		//등록
		if (child instanceof ElementModel) {
			ElementModel ipc = (ElementModel)child;
			ipc.undoOrphanChildPort(null, diagram);
		}
		if (child instanceof TextAttachModel) {
			TextAttachModel ipc = (TextAttachModel)child;
			ipc.addTextAttachParentDiagram(diagram, null);
		}
		**/
	}
}
