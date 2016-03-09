package org.pky.uml.rcp.action;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.pky.uml.commons.managers.EmulatorManager;
import org.pky.uml.commons.managers.ProjectManager;
import org.pky.uml.dialog.AndroidDevicesDialog;
import org.pky.uml.dialog.NewProjectDialog;

public class AndroidDevicesAction extends AbstractHandler {
	public static String ID = "org.pky.uml.rcp.action.AndroidDevicesAction";
	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		AndroidDevicesDialog dialog = new AndroidDevicesDialog(ProjectManager.getInstance().window.getShell());
		dialog.open();



		return event;
	}
	@Override
	public boolean isEnabled() {
//		if(ProjectManager.getInstance().getProjectPath().equals(""))
//			return true;
//		else return true;
		//		return super.isEnabled();
		
		return true;
	}
}
