package org.pky.uml.model.command;

import java.util.List;

import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.commands.Command;
import org.pky.uml.editparts.common.UMLEditPart;
import org.pky.uml.model.common.UMLModel;

public class ArrowKeyChangeLayoutCommand extends Command {

	private List selectedEditPartList;

	private Integer differenceWidthOrX;

	private Integer differenceHeightOrY;

	private boolean bTranslate = true;

	private boolean bChanged;

	public ArrowKeyChangeLayoutCommand(List selectedEditPartList, Integer differenceWidthOrX, Integer differenceHeightOrY) {
		super();
		this.selectedEditPartList = selectedEditPartList;
		this.differenceWidthOrX = differenceWidthOrX;
		this.differenceHeightOrY = differenceHeightOrY;
	}

	public ArrowKeyChangeLayoutCommand(List selectedEditPartList, Integer differenceWidthOrX, Integer differenceHeightOrY, boolean bTranslate) {
		this(selectedEditPartList, differenceWidthOrX, differenceHeightOrY);
		this.bTranslate = bTranslate;
	}

	@Override
	public boolean canExecute() {
		return selectedEditPartList != null && differenceWidthOrX != null && differenceHeightOrY != null;
	}

	@Override
	public void execute() {
		//		changeLayout();

		System.out.println("");

		for(int i = 0 ; i< selectedEditPartList.size(); i ++){
			UMLEditPart editPart = (UMLEditPart)selectedEditPartList.get(i);
			UMLModel model = editPart.getUMLModel();

			UMLChangeLayoutCommand command = new UMLChangeLayoutCommand();

			command.setModel(model);

			Rectangle rect = model.getLocationRect();
			rect.setLocation(model.getLocation().x+differenceWidthOrX, model.getLocation().y+differenceHeightOrY);
			command.setLocation(rect);	
			command.execute();

		}
	}

	@Override
	public void redo() {
		//		changeLayout();
	}

	@Override
	public boolean canUndo() {
		return bChanged;
	}

	@Override
	public void undo() {
		//		changeLayout(true);
	}

}