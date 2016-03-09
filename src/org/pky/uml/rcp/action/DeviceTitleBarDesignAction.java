package org.pky.uml.rcp.action;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.pky.uml.commons.managers.ProjectManager;
import org.pky.uml.dialog.DeviceTitleBarDesignDialog;

public class DeviceTitleBarDesignAction  extends AbstractHandler {
	public static String ID = "org.pky.uml.rcp.action.DeviceTitleBarDesignAction";
	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		DeviceTitleBarDesignDialog dialog = new DeviceTitleBarDesignDialog(ProjectManager.getInstance().window.getShell());
		dialog.open();



		return event;
	}
	@Override
	public boolean isEnabled() {
		if(ProjectManager.getInstance().getProjectPath().equals(""))
			return true;
		else return true;
		//		return super.isEnabled();
	}
}
