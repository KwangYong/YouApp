package org.pky.uml.model.command;

import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Insets;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.dnd.TemplateTransfer;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.graphics.Color;
import org.pky.uml.commons.managers.ModelBrowserManager;
import org.pky.uml.commons.managers.ProjectManager;
import org.pky.uml.model.LayoutModel;
import org.pky.uml.model.UMLDiagramModel;
import org.pky.uml.model.ViewModel;
import org.pky.uml.model.common.UMLModel;
import org.pky.uml.policy.PolicyCommand;

public class UMLCreateCommand extends org.eclipse.gef.commands.Command {
	private UMLModel child;
	private Rectangle rect;
	private UMLDiagramModel parent;
	private int index = -1;
	private boolean isTreeAdd = false;

	public UMLCreateCommand() {
		super(PolicyCommand.CreateCommand_Label);
	}

	public boolean canExecute() {

		return child != null && parent != null;
	}

	public void execute() {

		try {
			if(child instanceof ViewModel && child.getBasicModel() instanceof LayoutModel){
				if(ProjectManager.getInstance().getInterModel(parent,new Rectangle(rect.x, rect.y, LayoutModel.DEFAULT_WIDTH, LayoutModel.DEFAULT_HEIGHT))!=null){
					ProjectManager.getInstance().showMessage(MessageDialog.WARNING, "안내","Layout안에 layout을 생성할 수 없습니다.");
					return;
				}
			}
			ProjectManager.getInstance().setCreate(true);
			if(child.getBasicModel() instanceof LayoutModel)
				parent.addChild(child,0);
			else
				parent.addChild(child);
			if (rect != null) {
				Insets expansion = getInsets();
				if (!rect.isEmpty())
					rect.expand(expansion);
				else {
					rect.x -= expansion.left;
					rect.y -= expansion.top;
				}
				child.setLocation(rect.getLocation());
				if (!rect.isEmpty())
					child.setSize(rect.getSize());
				
				Rectangle reRect = ProjectManager.getInstance().getModelRePosition(child, new Rectangle(rect.x, rect.y,child.getSize().width, child.getSize().height));
				if(reRect!=null && child instanceof ViewModel){
					child.setLocation(reRect);
				}else if(reRect==null && child instanceof ViewModel && parent !=null){
					ProjectManager.getInstance().showMessage(MessageDialog.INFORMATION, "안내", "Layout안에만 모델을 생성하셔야 합니다.");
					parent.removeChild(child);
				}
					
				
			}
		
		}
		catch (Exception e) {
			e.printStackTrace();
		}finally{
			ProjectManager.getInstance().setCreate(false);
		}
	}

	private Insets getInsets() {
		//		if (child instanceof LED || child instanceof Circuit)
		//			return new Insets(2, 0, 2, 0);
		return new Insets();
	}

	public void redo() {
		try {
			ProjectManager.getInstance().setCreate(true);
			
			ViewModel viewModel = new ViewModel(child.getBasicModel());
			viewModel.setModelTreeModel(child.getBasicModel().getModelTreeModel());
			child = viewModel;
			execute();
			ProjectManager.getInstance().setCreate(false);
			
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		ProjectManager.getInstance().setCreate(false);
//		super.redo();
	}

	public void setChild(UMLModel subpart) {
		child = subpart;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public void setLocation(Rectangle r) {
		rect = r;
	}

	public void setParent(UMLDiagramModel newParent) {
		parent = newParent;
	}


	public void undo() {
		for(int i = 0 ; i < parent.getChildren().size(); i ++){
			if(parent.getChildren().get(i)==child){
				index = i;
				break;
			}
		}
		parent.removeChild(child);
//		super.undo();
	}
}
