package org.pky.uml.rcp.action;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.action.Action;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IWorkbenchWindow;
import org.pky.uml.commons.managers.LayoutManager;
import org.pky.uml.commons.managers.ProjectManager;
import org.pky.uml.dialog.NewProjectDialog;
import org.pky.uml.model.LayoutModel;
import org.pky.uml.model.UMLDiagramModel;

public class NewProjectAction extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		NewProjectDialog dialog = new NewProjectDialog(ProjectManager.getInstance().window.getShell());
		dialog.open();


		return event;

	}
}/**extends Action{
	IWorkbenchWindow workbenchWindow;
	
	IEditorInput input = null;
	public static String ID = "org.pky.uml.action.NewProjectAction"; 
	public NewProjectAction(IWorkbenchWindow window) {
		setId(ID);
		setText("New Project");
		workbenchWindow = window;
		
	}
	
	public void run() {
		NewProjectDialog dialog = new NewProjectDialog(workbenchWindow.getShell());
		dialog.open();
	}
	
	
}
**/