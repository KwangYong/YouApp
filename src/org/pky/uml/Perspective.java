package org.pky.uml;

import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;
import org.pky.uml.commons.managers.ProjectManager;

public class Perspective implements IPerspectiveFactory {

	public void createInitialLayout(IPageLayout layout) {
		
		ProjectManager.getInstance();
		String editorArea = layout.getEditorArea();
			
		layout.addStandaloneView("org.pky.uml.ModelBrowser",false ,IPageLayout.LEFT, .15f, editorArea);
		layout.addStandaloneView("org.pky.uml.browser.PropertyBrowser",true ,IPageLayout.RIGHT, .700f, editorArea);
		layout.addStandaloneView("org.pky.uml.browser.ActionBrowser",true ,IPageLayout.BOTTOM, .50f, "org.pky.uml.browser.PropertyBrowser");
	}
}
