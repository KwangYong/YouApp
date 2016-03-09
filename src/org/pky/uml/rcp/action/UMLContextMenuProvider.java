package org.pky.uml.rcp.action;

import org.eclipse.gef.ContextMenuProvider;
import org.eclipse.gef.EditPartViewer;
import org.eclipse.gef.ui.actions.ActionRegistry;
import org.eclipse.gef.ui.actions.GEFActionConstants;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IMenuManager;
import org.pky.uml.commons.managers.ProjectManager;
import org.pky.uml.editor.action.ModelDeleteAction;
import org.pky.uml.editor.action.ModelImageSizeChangeAction;
import org.pky.uml.editor.action.ModelImageWidthPercentAction;
import org.pky.uml.model.ImageViewModel;

public class UMLContextMenuProvider extends ContextMenuProvider {

	private ActionRegistry actionRegistry;

	public UMLContextMenuProvider(EditPartViewer viewer, ActionRegistry registry) {
		super(viewer);
		setActionRegistry(registry);
	}

	private ActionRegistry getActionRegistry() {
		return actionRegistry;
	}

	private void setActionRegistry(ActionRegistry registry) {
		actionRegistry = registry;
	}

	@Override
	public void buildContextMenu(IMenuManager manager) {
		UMLContextMenuProvider nmp = 	(UMLContextMenuProvider)manager;
		GEFActionConstants.addStandardActionGroups(manager);

		IAction action;

		action = getActionRegistry().getAction(ModelDeleteAction.ID);
		if (action.isEnabled())
			manager.appendToGroup(GEFActionConstants.GROUP_EDIT, action);

		boolean isImageModel = false;
		for(int i = 0; i < ProjectManager.getInstance().getSelections().size(); i ++){
			if(ProjectManager.getInstance().getSelections().get(i).getModel().getBasicModel() instanceof ImageViewModel)
				isImageModel =true;
		}
		if(isImageModel){
			action = getActionRegistry().getAction(ModelImageWidthPercentAction.ID);
			if (action.isEnabled())
				manager.appendToGroup(GEFActionConstants.GROUP_EDIT, action);

			action = getActionRegistry().getAction(ModelImageSizeChangeAction.ID);
			if (action.isEnabled())
				manager.appendToGroup(GEFActionConstants.GROUP_EDIT, action);
		}

	}
}
