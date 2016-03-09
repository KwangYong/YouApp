package org.pky.uml.model.command;

import org.eclipse.draw2d.PositionConstants;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.commands.Command;
import org.pky.uml.commons.managers.ProjectManager;
import org.pky.uml.model.LayoutModel;
import org.pky.uml.model.common.UMLModel;

public class UMLChangeLayoutCommand extends Command {

	private UMLModel model;

	private Rectangle location;
	private Rectangle undo_location;
	private Rectangle redo_location;
	
	public void execute() {
		undo_location = model.getLocationRect();
		
		location =ProjectManager.getInstance().getModelRePosition(model,location);
		if(location==null)
			return;
		if(model!=null && model.getBasicModel() instanceof LayoutModel){
			location = ProjectManager.getInstance().setModelContainsReLocation(model,location);
		}
		if(model.getLocation().x != location.x || model.getLocation().y!= location.y)
			model.setLocation(location);
		else
			model.setSize(location.width,location.height);




	}

	/**
	 * @return the location
	 */
	public Rectangle getLocation() {
		return location;
	}

	/**
	 * @param location the location to set
	 */
	public void setLocation(Rectangle location) {

		this.location = location; 
	}

	public UMLModel getModel() {
		return model;
	}

	public void setModel(UMLModel model) {
		this.model = model;
	}
	
	@Override
	public void undo() {
//		undo_location = model.getLocationRect();
		
		if(model!=null && model.getBasicModel() instanceof LayoutModel){
			undo_location = ProjectManager.getInstance().setModelContainsReLocation(model,undo_location);
		}
		if(model.getLocation().x != undo_location.x || model.getLocation().y!= undo_location.y)
			model.setLocation(undo_location);
		else
			model.setSize(undo_location.width,undo_location.height);
	}
	
	@Override
	public void redo() {

		execute();
	}


}