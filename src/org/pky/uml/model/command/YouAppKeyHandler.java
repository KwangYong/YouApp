package org.pky.uml.model.command;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.List;

import javax.imageio.ImageIO;

import org.eclipse.gef.GraphicalViewer;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.gef.ui.parts.GraphicalViewerKeyHandler;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.pky.uml.browser.common.propertybrowser.Property;
import org.pky.uml.commons.managers.ProjectManager;
import org.pky.uml.model.common.UMLModel;

public class YouAppKeyHandler extends GraphicalViewerKeyHandler {

	private ISelectionProvider selectionProvider;

	private CommandStack commandStack;

	GraphicalViewer viewer;

	public YouAppKeyHandler(GraphicalViewer viewer, ISelectionProvider selectionProvider, CommandStack commandStack) {
		super(viewer);
		this.viewer = viewer;
		setSelectionProvider(selectionProvider);
		setCommandStack(commandStack);
	}

	public void setSelectionProvider(ISelectionProvider selectionProvider) {
		this.selectionProvider = selectionProvider;
	}

	public void setCommandStack(CommandStack commandStack) {
		this.commandStack = commandStack;
	}

	@Override
	public boolean keyReleased(KeyEvent event) {
//		System.out.println(event.keyCode);
//		System.out.println(SWT.CONTROL);
		if(viewer !=null && event.keyLocation + event.keyCode == (SWT.CONTROL)){
			
		}
		return true;
	}
	public boolean keyPressed(KeyEvent event) {

		if (viewer != null
				&& (event.keyCode == SWT.ARROW_UP || event.keyCode == SWT.ARROW_DOWN || event.keyCode == SWT.ARROW_LEFT || event.keyCode == SWT.ARROW_RIGHT)) {
			ISelection selection = viewer.getSelection();

			if (selection instanceof StructuredSelection) {
				StructuredSelection structuredSelection = (StructuredSelection) selection;

				commandStack.execute(createChangeLayoutCommand(structuredSelection.toList(), event));
			}

			return false;
		}

		return super.keyPressed(event);
	}

	/**
	 * 이벤트에 따라 새로운 Command 객체를 생성합니다.
	 * 
	 * @param selectedEditPartList
	 * @param event
	 * @return
	 */
	private Command createChangeLayoutCommand(List selectedEditPartList, KeyEvent event) {
		int differenceWidthOrX = 0;
		int differenceHeightOrY = 0;

		switch (event.keyCode) {
		case SWT.ARROW_UP:
			differenceHeightOrY = -1;
			break;
		case SWT.ARROW_DOWN:
			differenceHeightOrY = 1;
			break;
		case SWT.ARROW_LEFT:
			differenceWidthOrX = -1;
			break;
		case SWT.ARROW_RIGHT:
			differenceWidthOrX = 1;
			break;
		}

		Command command = new ArrowKeyChangeLayoutCommand(selectedEditPartList, differenceWidthOrX,
				differenceHeightOrY, event.stateMask != SWT.SHIFT);

		return command;
	}

}