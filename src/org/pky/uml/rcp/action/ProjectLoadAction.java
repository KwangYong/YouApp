package org.pky.uml.rcp.action;

import java.util.ArrayList;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.ui.IEditorPart;
import org.pky.uml.browser.common.propertybrowser.Property;
import org.pky.uml.commons.managers.ModelDataManager;
import org.pky.uml.commons.managers.ProjectManager;
import org.pky.uml.dialog.ExportAndroidPackDialog;
import org.pky.uml.model.common.UMLModel;

public class ProjectLoadAction  extends AbstractHandler {
	public static String ID = "org.pky.uml.rcp.action.ProjectLoadAction";
	IEditorPart test;
	@Override
	public boolean isEnabled() {
		return true;
		//		return super.isEnabled();
	}

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		
		FileDialog dialog = new FileDialog(ProjectManager.getInstance().window.getShell(),SWT.OPEN);

		dialog.setFilterExtensions(new String[]{"*.apj"});

		String path = dialog.open();
		if(path!=null && !path.equals("")){
			ProjectManager.getInstance().init();
			ModelDataManager.getInstance().loadFile(path);
		}
		return event;
	}
}