package org.pky.uml.rcp.action;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IWorkbenchWindow;
import org.pky.uml.commons.managers.LayoutManager;
import org.pky.uml.commons.managers.ModelDataManager;
import org.pky.uml.commons.managers.ProjectManager;
import org.pky.uml.dialog.NewProjectDialog;
import org.pky.uml.model.LayoutModel;
import org.pky.uml.model.UMLDiagramModel;

public class SaveProjectAction extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		if(!ProjectManager.getInstance().getProjectPath().equals("")){
			ModelDataManager.getInstance().saveFile(ProjectManager.getInstance().getProjectPath());
			
		}else{
			ProjectManager.getInstance().showMessage(MessageDialog.WARNING, "", "프로젝트가 지정되어 있지 않습니다. 저장할 수 없습니다.");
		}
		return event;

	}
}