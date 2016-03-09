package org.pky.uml.rcp.action;

import org.eclipse.draw2d.PositionConstants;
import org.eclipse.gef.internal.GEFMessages;
import org.eclipse.gef.ui.actions.ActionBarContributor;
import org.eclipse.gef.ui.actions.AlignmentRetargetAction;
import org.eclipse.gef.ui.actions.GEFActionConstants;
import org.eclipse.gef.ui.actions.MatchWidthRetargetAction;
import org.eclipse.gef.ui.actions.RedoRetargetAction;
import org.eclipse.gef.ui.actions.UndoRetargetAction;
import org.eclipse.gef.ui.actions.ZoomComboContributionItem;
import org.eclipse.gef.ui.actions.ZoomInRetargetAction;
import org.eclipse.gef.ui.actions.ZoomOutRetargetAction;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.actions.RetargetAction;

public class UAppActionBarContributor  extends ActionBarContributor {

	@Override
	protected void buildActions() {
		// TODO Auto-generated method stub
		
		addRetargetAction(new UndoRetargetAction());
		addRetargetAction(new RedoRetargetAction());
		
		addRetargetAction(new ZoomInRetargetAction());
		addRetargetAction(new ZoomOutRetargetAction());

		addRetargetAction(new AlignmentRetargetAction(PositionConstants.LEFT));
		addRetargetAction(new AlignmentRetargetAction(PositionConstants.CENTER));
		addRetargetAction(new AlignmentRetargetAction(PositionConstants.RIGHT));
		addRetargetAction(new AlignmentRetargetAction(PositionConstants.TOP));
		addRetargetAction(new AlignmentRetargetAction(PositionConstants.MIDDLE));
		addRetargetAction(new AlignmentRetargetAction(PositionConstants.BOTTOM));
		addRetargetAction(new ZoomInRetargetAction());
		addRetargetAction(new ZoomOutRetargetAction());
//		Object obj  = new MatchWidthRetargetAction();
		
		addRetargetAction(new MatchWidthRetargetAction());
		addRetargetAction(new MatchWidthRetargetAction());
		addRetargetAction(new RetargetAction(GEFActionConstants.TOGGLE_GRID_VISIBILITY,GEFMessages.ToggleGrid_Label,IAction.AS_CHECK_BOX));
	}

	@Override
	protected void declareGlobalActionKeys() {
		// TODO Auto-generated method stub

	}
	@Override
	public void contributeToToolBar(IToolBarManager toolBarManager) {
		// TODO Auto-generated method stub
		super.contributeToToolBar(toolBarManager);
		toolBarManager.add(new Separator());
		toolBarManager.add(getAction(ActionFactory.UNDO.getId()));
		toolBarManager.add(getAction(ActionFactory.REDO.getId()));
		toolBarManager.add(new Separator());
		toolBarManager.add(new ZoomComboContributionItem(getPage()));
		toolBarManager.add(getAction(GEFActionConstants.TOGGLE_GRID_VISIBILITY));
		toolBarManager.add(getAction(GEFActionConstants.ALIGN_LEFT));
		toolBarManager.add(getAction(GEFActionConstants.ALIGN_LEFT));
		toolBarManager.add(getAction(GEFActionConstants.ALIGN_CENTER));
		toolBarManager.add(getAction(GEFActionConstants.ALIGN_RIGHT));
		toolBarManager.add(new Separator());
		toolBarManager.add(getAction(GEFActionConstants.ALIGN_TOP));
		toolBarManager.add(getAction(GEFActionConstants.ALIGN_MIDDLE));
		toolBarManager.add(getAction(GEFActionConstants.ALIGN_BOTTOM));
		toolBarManager.add(new Separator());
//		toolBarManager.add(getAction(GEFActionConstants.MATCH_WIDTH));
//		toolBarManager.add(getAction(GEFActionConstants.MATCH_HEIGHT));
	}

}
