package org.pky.uml.rcp.action;

import java.util.ArrayList;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.ui.IEditorPart;
import org.pky.uml.browser.common.propertybrowser.Property;
import org.pky.uml.commons.managers.ProjectManager;
import org.pky.uml.dialog.ExportAndroidPackDialog;
import org.pky.uml.model.common.UMLModel;

public class IOSGeneratorAction extends AbstractHandler {

	IEditorPart test;
	@Override
	public boolean isEnabled() {
		if(ProjectManager.getInstance().getProjectPath().equals(""))
			return false;
		else return true;
		//		return super.isEnabled();
	}

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {

		

		//메인이 없으면 APK파일이 생성안되도록 
		ArrayList<UMLModel> list = ProjectManager.getInstance().getModels(ProjectManager.getInstance().getModelType("RelativeLayout"));
		boolean isMain = false;
		for(int i = 0; i < list.size(); i ++){
			int value =(Integer) list.get(i).getPropertyValue(Property.ID_MAIN);
			if(value==1){
				isMain = true;
				break;
			}
		}


		if(!isMain){
			ProjectManager.getInstance().showMessage(MessageDialog.WARNING, "확인", "시작 Layout이 존재하지 않습니다. Layout중 시작화면을 결정하여 주세요.");
			return event;
		}

		ProjectManager.getInstance().makeIOS();


		//		AndroidDevicesDialog dialog = new AndroidDevicesDialog(ProjectManager.getInstance().window.getShell());
		//		dialog.open();


		return event;

	}
}