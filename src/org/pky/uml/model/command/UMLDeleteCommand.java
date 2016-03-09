package org.pky.uml.model.command;

import org.eclipse.draw2d.geometry.Insets;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.commands.Command;
import org.eclipse.jface.dialogs.MessageDialog;
import org.pky.uml.commons.managers.ProjectManager;
import org.pky.uml.model.LayoutModel;
import org.pky.uml.model.UMLDiagramModel;
import org.pky.uml.model.ViewModel;
import org.pky.uml.model.common.UMLModel;

public class UMLDeleteCommand extends Command{
	private UMLModel child;
	private UMLDiagramModel parent;
	private int index = 0;
	private Rectangle rect;

	public void execute() {
		for(int i = 0 ; i < parent.getChildren().size(); i ++){
			if(parent.getChildren().get(i)==child){
				index = i;
				break;
			}
		}
		rect = child.getLocationRect();
		parent.removeChild(child);
	}

	public UMLModel getChild() {
		return child;
	}
	public UMLDiagramModel getParent() {
		return parent;
	}
	public void setChild(UMLModel child) {
		this.child = child;
	}
	public void setParent(UMLDiagramModel parent) {
		this.parent = parent;
	}
	private Insets getInsets() {
		//		if (child instanceof LED || child instanceof Circuit)
		//			return new Insets(2, 0, 2, 0);
		return new Insets();
	}
	@Override
	public void undo() {
		try {
			
			
			ViewModel viewModel = new ViewModel(child.getBasicModel());
			viewModel.setModelTreeModel(child.getBasicModel().getModelTreeModel());
			child = viewModel;
			
			UMLCreateCommand command = new UMLCreateCommand();
			command.setChild(child);
			command.setLocation(rect);
			command.setParent(parent);
			command.execute();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
//		super.undo();
	}
	
	@Override
	public void redo() {
		execute();
//		super.redo();
	}
}
