package org.pky.uml.policy;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.draw2d.XYLayout;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.SnapToGuides;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.gef.editpolicies.XYLayoutEditPolicy;
import org.eclipse.gef.requests.ChangeBoundsRequest;
import org.eclipse.gef.requests.CreateRequest;
import org.eclipse.gef.rulers.RulerProvider;
import org.pky.uml.commons.managers.ProjectManager;
import org.pky.uml.editparts.common.UMLEditPart;
import org.pky.uml.model.UMLDiagramModel;
import org.pky.uml.model.command.UMLAddCommand;
import org.pky.uml.model.command.UMLChangeGuideCommand;
import org.pky.uml.model.command.UMLChangeLayoutCommand;
import org.pky.uml.model.command.UMLCreateCommand;
import org.pky.uml.model.command.UMLSetConstraintCommand;
import org.pky.uml.model.common.UMLModel;
import org.pky.uml.model.common.UMLModelGuide;

public class UMLXYLayoutEditPolicy extends XYLayoutEditPolicy {
	public UMLXYLayoutEditPolicy(XYLayout layout) {
		super();
		setXyLayout(layout);
	}
	@Override
	protected Command createChangeConstraintCommand(EditPart child, Object constraint) {
		
		UMLChangeLayoutCommand command = new UMLChangeLayoutCommand();

		command.setModel(((UMLEditPart)child).getModel());
		command.setLocation((Rectangle) constraint);	

		return command;
	}

	@Override
	protected Command getMoveChildrenCommand(Request request) {
		
		int inside = 8;
		ChangeBoundsRequest changeBoundsRequest = (ChangeBoundsRequest)request;
		ArrayList<UMLModel> selectionList = new ArrayList();
		if(changeBoundsRequest.getEditParts().size()>0){
			for(int i = 0; i < changeBoundsRequest.getEditParts().size(); i ++){
				UMLEditPart umlEditPart = (UMLEditPart)changeBoundsRequest.getEditParts().get(i);
				selectionList.add(umlEditPart.getUMLModel());
			}
		}
		UMLModel aeroUMLModel = null;
		if(changeBoundsRequest.getEditParts().size()>0){
			aeroUMLModel = ProjectManager.getInstance().getAeroLayoutModel(((UMLEditPart)changeBoundsRequest.getEditParts().get(0)).getUMLModel());
			if(aeroUMLModel!=null)
				for(int i = 0; i < changeBoundsRequest.getEditParts().size(); i ++){
					UMLEditPart umlEditPart = (UMLEditPart)changeBoundsRequest.getEditParts().get(i);
					UMLModel model = umlEditPart.getUMLModel();
					ArrayList<UMLModel> list = ProjectManager.getInstance().getAeroUMLModel(aeroUMLModel);

					for(int j = 0; j < list.size(); j++){
						UMLModel targetModel = list.get(j);

						if(!selectionList.contains(targetModel) &&
								(targetModel.getLocation().x -inside < changeBoundsRequest.getMoveDelta().x+model.getLocation().x
										&&targetModel.getLocation().x + inside > changeBoundsRequest.getMoveDelta().x+model.getLocation().x)){


							changeBoundsRequest.setMoveDelta(new Point(targetModel.getLocation().x-model.getLocation().x,changeBoundsRequest.getMoveDelta().y));


						}else if(!selectionList.contains(targetModel) &&
								(targetModel.getLocation().x+targetModel.getSize().width -inside < changeBoundsRequest.getMoveDelta().x+model.getLocation().x
										&&targetModel.getLocation().x+targetModel.getSize().width + inside > changeBoundsRequest.getMoveDelta().x+model.getLocation().x)){

							changeBoundsRequest.setMoveDelta(new Point(targetModel.getLocation().x+targetModel.getSize().width-model.getLocation().x+1,changeBoundsRequest.getMoveDelta().y));


						}else if(!selectionList.contains(targetModel) &&
								(targetModel.getLocation().x+targetModel.getSize().width -inside < changeBoundsRequest.getMoveDelta().x+model.getLocation().x+model.getSize().width
										&&targetModel.getLocation().x+targetModel.getSize().width + inside > changeBoundsRequest.getMoveDelta().x+model.getLocation().x+model.getSize().width)){

							changeBoundsRequest.setMoveDelta(new Point(targetModel.getLocation().x+targetModel.getSize().width-model.getLocation().x-model.getSize().width,changeBoundsRequest.getMoveDelta().y));

						}else if(!selectionList.contains(targetModel) &&
								(targetModel.getLocation().x -inside < changeBoundsRequest.getMoveDelta().x+model.getLocation().x+model.getSize().width
										&&targetModel.getLocation().x + inside > changeBoundsRequest.getMoveDelta().x+model.getLocation().x+model.getSize().width)){

							changeBoundsRequest.setMoveDelta(new Point(targetModel.getLocation().x-model.getLocation().x-model.getSize().width-1,changeBoundsRequest.getMoveDelta().y));

						}

						if(!selectionList.contains(targetModel) &&
								(targetModel.getLocation().y -inside < changeBoundsRequest.getMoveDelta().y+model.getLocation().y
										&&targetModel.getLocation().y + inside > changeBoundsRequest.getMoveDelta().y+model.getLocation().y)){


							changeBoundsRequest.setMoveDelta(new Point(changeBoundsRequest.getMoveDelta().x,targetModel.getLocation().y-model.getLocation().y-1));


						}else if(!selectionList.contains(targetModel) &&
								(targetModel.getLocation().y+targetModel.getSize().height -inside < changeBoundsRequest.getMoveDelta().y+model.getLocation().y
										&&targetModel.getLocation().y+targetModel.getSize().height + inside > changeBoundsRequest.getMoveDelta().y+model.getLocation().y)){

							changeBoundsRequest.setMoveDelta(new Point(changeBoundsRequest.getMoveDelta().x,targetModel.getLocation().y+targetModel.getSize().height-model.getLocation().y+1));


						}else if(!selectionList.contains(targetModel) &&
								(targetModel.getLocation().y+targetModel.getSize().height -inside < changeBoundsRequest.getMoveDelta().y+model.getLocation().y+model.getSize().height
										&&targetModel.getLocation().y+targetModel.getSize().height + inside > changeBoundsRequest.getMoveDelta().y+model.getLocation().y+model.getSize().height)){

							changeBoundsRequest.setMoveDelta(new Point(changeBoundsRequest.getMoveDelta().x,targetModel.getLocation().y+targetModel.getSize().height-model.getLocation().y-model.getSize().height));


						}else if(!selectionList.contains(targetModel) &&
								(targetModel.getLocation().y -inside < changeBoundsRequest.getMoveDelta().y+model.getLocation().y+model.getSize().height
										&&targetModel.getLocation().y + inside > changeBoundsRequest.getMoveDelta().y+model.getLocation().y+model.getSize().height)){

							changeBoundsRequest.setMoveDelta(new Point(changeBoundsRequest.getMoveDelta().x,targetModel.getLocation().y+targetModel.getSize().height-model.getLocation().y-model.getSize().height-targetModel.getSize().height-1));


						}
					}


				}
		}
		return super.getMoveChildrenCommand(request);
	}
	@Override
	public Command getCommand(Request request) {
		// TODO Auto-generated method stub
		return super.getCommand(request);
	}
	@Override
	protected Command getChangeConstraintCommand(ChangeBoundsRequest changeBoundsRequest) {
		int inside = 8;
		// TODO Auto-generated method stub
		ArrayList<UMLModel> selectionList = new ArrayList();
		if(changeBoundsRequest.getEditParts().size()>0){
			for(int i = 0; i < changeBoundsRequest.getEditParts().size(); i ++){
				UMLEditPart umlEditPart = (UMLEditPart)changeBoundsRequest.getEditParts().get(i);
				selectionList.add(umlEditPart.getUMLModel());
			}
		}
		UMLModel aeroUMLModel = null;
		if(changeBoundsRequest.getEditParts().size()>0){
			aeroUMLModel = ProjectManager.getInstance().getAeroLayoutModel(((UMLEditPart)changeBoundsRequest.getEditParts().get(0)).getUMLModel());
			if(aeroUMLModel!=null)
				for(int i = 0; i < changeBoundsRequest.getEditParts().size(); i ++){
					UMLEditPart umlEditPart = (UMLEditPart)changeBoundsRequest.getEditParts().get(i);
					UMLModel model = umlEditPart.getUMLModel();
					ArrayList<UMLModel> list = ProjectManager.getInstance().getAeroUMLModel(aeroUMLModel);

					for(int j = 0; j < list.size(); j++){
						UMLModel targetModel = list.get(j);
						if(!selectionList.contains(targetModel)){
							Rectangle rect = targetModel.getLocationRect();

							int width = rect.x + rect.width;
							int height = rect.y + rect.height;
							
							
							for(int k = 0 ; k < selectionList.size(); k ++){
								Rectangle selectionRect = selectionList.get(k).getLocationRect();
								
								int selectionWidth = selectionRect.x + selectionRect.width+changeBoundsRequest.getSizeDelta().width;
								int selectionHeight = selectionRect.y + selectionRect.height+changeBoundsRequest.getSizeDelta().height;
								
								if(width > selectionWidth-inside && width < selectionWidth+inside){
									
									
									changeBoundsRequest.setSizeDelta(new Dimension(width - (selectionRect.x + selectionRect.width), changeBoundsRequest.getSizeDelta().height));
									
								}
								
								if(height > selectionHeight-inside && height < selectionHeight+inside){
									
									
									changeBoundsRequest.setSizeDelta(new Dimension(changeBoundsRequest.getSizeDelta().width, height - (selectionRect.y + selectionRect.height)));
									
								}
								
							}
							
							

						}


					}
				}
		}

		return super.getChangeConstraintCommand(changeBoundsRequest);
	}
	@Override
	protected Command getCreateCommand(CreateRequest request) {
		if(getHost().getModel()!=null && getHost().getModel() instanceof UMLDiagramModel){
			UMLCreateCommand create = new UMLCreateCommand();
			create.setParent((UMLDiagramModel)getHost().getModel());
			UMLModel newPart = (UMLModel)request.getNewObject();


			create.setChild(newPart);
			Rectangle constraint = (Rectangle)getConstraintFor(request);

			//			newPart.getViewModel().setSize(newPart.getBasicModel().getSize());
			//			constraint.setLocation(1000000, 1000000);
			create.setLocation(constraint);
			create.setLabel(PolicyCommand.LogicXYLayoutEditPolicy_CreateCommandLabelText);
			Command cmd = chainGuideAttachmentCommand(request, newPart, create, true);
			return chainGuideAttachmentCommand(request, newPart, cmd, false);
		}else{
			return null;
		}
	}
	protected Command getAddCommand(Request generic) {
		ChangeBoundsRequest request = (ChangeBoundsRequest)generic;
		List editParts = request.getEditParts();
		CompoundCommand command = new CompoundCommand();
		command.setDebugLabel("Add in ConstrainedLayoutEditPolicy"); //$NON-NLS-1$
		GraphicalEditPart childPart;
		Rectangle r;
		Object constraint;
		for (int i = 0; i < editParts.size(); i++) {
			childPart = (GraphicalEditPart)editParts.get(i);
			r = childPart.getFigure().getBounds().getCopy();
			//convert r to absolute from childpart figure
			childPart.getFigure().translateToAbsolute(r);
			r = request.getTransformedRectangle(r);
			//convert this figure to relative
			getLayoutContainer().translateToRelative(r);
			getLayoutContainer().translateFromParent(r);
			r.translate(getLayoutOrigin().getNegated());
			constraint = getConstraintFor(r);
			command.add(createAddCommand(generic, childPart, translateToModelConstraint(constraint)));
		}
		return command.unwrap();
	}
	protected Command createAddCommand(Request request, EditPart childEditPart, Object constraint) {
		//port
		UMLModel part = (UMLModel)childEditPart.getModel();

		Rectangle rect = (Rectangle)constraint;
		UMLAddCommand add = new UMLAddCommand();
		add.setParent((UMLDiagramModel)getHost().getModel());
		add.setChild(part);
		add.setLabel(PolicyCommand.LogicXYLayoutEditPolicy_AddCommandLabelText);
		add.setDebugLabel("LogicXYEP add subpart"); //$NON-NLS-1$
		UMLSetConstraintCommand setConstraint = new UMLSetConstraintCommand();
		setConstraint.setLocation(rect);
		setConstraint.setPart(part);
		setConstraint.setLabel(PolicyCommand.LogicXYLayoutEditPolicy_AddCommandLabelText);
		setConstraint.setDebugLabel("LogicXYEP setConstraint"); //$NON-NLS-1$
		Command cmd = add.chain(setConstraint);
		cmd = chainGuideAttachmentCommand(request, part, cmd, true);
		cmd = chainGuideAttachmentCommand(request, part, cmd, false);
		cmd = chainGuideDetachmentCommand(request, part, cmd, true);
		return chainGuideDetachmentCommand(request, part, cmd, false);
	}
	protected Command chainGuideDetachmentCommand(Request request, UMLModel part, Command cmd, boolean horizontal) {
		Command result = cmd;
		// Detach from guide, if none is given
		Integer guidePos = (Integer)request.getExtendedData()
				.get(horizontal ? SnapToGuides.KEY_HORIZONTAL_GUIDE : SnapToGuides.KEY_VERTICAL_GUIDE);
		if (guidePos == null)
			result = result.chain(new UMLChangeGuideCommand(part, horizontal));
		return result;
	}
	protected Command chainGuideAttachmentCommand(Request request, UMLModel part, Command cmd, boolean horizontal) {
		Command result = cmd;
		// Attach to guide, if one is given
		Integer guidePos = (Integer)request.getExtendedData()
				.get(horizontal ? SnapToGuides.KEY_HORIZONTAL_GUIDE : SnapToGuides.KEY_VERTICAL_GUIDE);
		if (guidePos != null) {
			int alignment = ((Integer)request.getExtendedData()
					.get(horizontal ? SnapToGuides.KEY_HORIZONTAL_ANCHOR : SnapToGuides.KEY_VERTICAL_ANCHOR)).intValue();
			UMLChangeGuideCommand cgm = new UMLChangeGuideCommand(part, horizontal);
			cgm.setNewGuide(findGuideAt(guidePos.intValue(), horizontal), alignment);
			result = result.chain(cgm);
		}
		return result;
	}

	protected UMLModelGuide findGuideAt(int pos, boolean horizontal) {
		RulerProvider provider = ((RulerProvider)getHost().getViewer().getProperty(horizontal ?
				RulerProvider.PROPERTY_VERTICAL_RULER : RulerProvider.PROPERTY_HORIZONTAL_RULER));
		return (UMLModelGuide)provider.getGuideAt(pos);
	}

}
