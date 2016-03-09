package org.pky.uml.editor.action;

import java.util.List;

import org.eclipse.gef.ui.actions.DeleteAction;
import org.eclipse.ui.IWorkbenchPart;
import org.pky.uml.commons.managers.ProjectManager;

public class ModelDeleteAction extends DeleteAction {
	
	public final static String ID = "org.pky.uml.editor.action.ModelDeleteAction";
	IWorkbenchPart workbenchPart =null;
	

	public ModelDeleteAction(IWorkbenchPart part) {
		super(part);
		this.setId(ID);
		this.setText("ªË¡¶");
		
		this.workbenchPart = workbenchPart;
	}
	
	protected boolean calculateEnabled() {
		
		return true;
	}
	
	public void run() {
		List list = ProjectManager.getInstance().getSelections();
		
		
		execute(createDeleteCommand(ProjectManager.getInstance().getSelections()));
	
		super.run();
	}

}
