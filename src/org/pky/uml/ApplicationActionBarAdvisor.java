package org.pky.uml;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.application.ActionBarAdvisor;
import org.eclipse.ui.application.IActionBarConfigurer;
import org.pky.uml.commons.managers.ProjectManager;
import org.pky.uml.rcp.action.AndroidGeneratorAction;
import org.pky.uml.rcp.action.NewProjectAction;

public class ApplicationActionBarAdvisor extends ActionBarAdvisor {
	
	private IAction newProjectAction;
	private IAction layoutGeneratorAction;
	
	
    public ApplicationActionBarAdvisor(IActionBarConfigurer configurer) {
        super(configurer);
    }

    protected void makeActions(IWorkbenchWindow window) {
    	ProjectManager.getInstance().window = window;
//    	newProjectAction = new NewProjectAction(window);
//    	layoutGeneratorAction = new LayoutGeneratorAction(window);
    	
    }
   

    protected void fillMenuBar(IMenuManager menuBar) {
//    	MenuManager fileMenu = 
//    			new MenuManager("File",IWorkbenchActionConstants.M_FILE);
//    	fileMenu.add(newProjectAction);
//    	fileMenu.add(layoutGeneratorAction);
//    	
//    	
//    	menuBar.add(fileMenu);
    
    }
    
}
