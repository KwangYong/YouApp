package org.pky.uml.editparts.common;

import org.eclipse.draw2d.ConnectionLocator;
import org.eclipse.draw2d.PolylineConnection;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPartFactory;
import org.eclipse.gef.editparts.AbstractGraphicalEditPart;
import org.pky.uml.browser.model.model.ModelTreeModel;
import org.pky.uml.commons.managers.ModelBrowserManager;
import org.pky.uml.commons.managers.ModelDataManager;
import org.pky.uml.commons.managers.ProjectManager;
import org.pky.uml.editparts.ButtonModelEditPart;
import org.pky.uml.editparts.ClassEditPart;
import org.pky.uml.editparts.EditTextEditPart;
import org.pky.uml.editparts.ElementEditPart;
import org.pky.uml.editparts.ElementGroupEditPart;
import org.pky.uml.editparts.FragmentEditPart;
import org.pky.uml.editparts.ImageViewEditPart;
import org.pky.uml.editparts.LayoutEditPart;
import org.pky.uml.editparts.LineEditPart;
import org.pky.uml.editparts.ListViewEditPart;
import org.pky.uml.editparts.PackageEditPart;
import org.pky.uml.editparts.StateBarEditPart;
import org.pky.uml.editparts.TextViewEditPart;
import org.pky.uml.editparts.TitleBarEditPart;
import org.pky.uml.editparts.UseCaseEditPart;
import org.pky.uml.editparts.VideoEditPart;
import org.pky.uml.editparts.ViewEditPart;
import org.pky.uml.editparts.WebViewEditPart;
import org.pky.uml.editparts.YoutubeEditPart;
import org.pky.uml.figures.UMLPolylineConnection;
import org.pky.uml.figures.common.UMLElementFigure;
import org.pky.uml.model.ButtonModel;
import org.pky.uml.model.ClassModel;
import org.pky.uml.model.EditTextModel;
import org.pky.uml.model.ElementGroupModel;
import org.pky.uml.model.ElementModel;
import org.pky.uml.model.FragmentModel;
import org.pky.uml.model.GoogleMapModel;
import org.pky.uml.model.ImageViewModel;
import org.pky.uml.model.LayoutModel;
import org.pky.uml.model.LineModel;
import org.pky.uml.model.ListViewModel;
import org.pky.uml.model.PackageModel;
import org.pky.uml.model.StateBarModel;
import org.pky.uml.model.TextViewModel;
import org.pky.uml.model.TitleBarModel;
import org.pky.uml.model.UMLDiagramModel;
import org.pky.uml.model.UseCaseModel;
import org.pky.uml.model.VideoModel;
import org.pky.uml.model.ViewModel;
import org.pky.uml.model.WebViewModel;
import org.pky.uml.model.YoutubeModel;
import org.pky.uml.model.common.UMLModel;

public class UMLEditPartFactory implements EditPartFactory {

	@Override
	//µî·Ï
	public EditPart createEditPart(EditPart context, Object model) {
		AbstractGraphicalEditPart part = null;
		if(model instanceof UMLModel){
			UMLModel umlModel = (UMLModel)model;
			boolean isModelTree = false;

			if (model instanceof UseCaseModel) {
				part = new UseCaseEditPart();
				isModelTree = true;
			}else if(model instanceof UMLDiagramModel){
				part = new UMLDiagramEditPart();
				isModelTree = true;
			}else if(model instanceof ClassModel){
				part = new ClassEditPart();
				isModelTree = true;
			}else if(model instanceof ElementModel){
				part = new ElementEditPart();
			}else if(model instanceof ElementGroupModel){
				part = new ElementGroupEditPart();
			}else if(model instanceof ViewModel){
				part = new ViewEditPart();
				isModelTree = true;
			}else if(model instanceof PackageModel){
				part = new PackageEditPart();
				isModelTree = true;
			}else if(model instanceof LayoutModel){
				part = new LayoutEditPart();
				isModelTree = true;
			}else if(model instanceof StateBarModel){
				part = new StateBarEditPart();
				//isModelTree = true;
			}else if(model instanceof TitleBarModel){
				part = new TitleBarEditPart();

			}
			else if(model instanceof EditTextModel){
				part = new EditTextEditPart();
				isModelTree = true;
			}
			else if(model instanceof ListViewModel){
				part = new ListViewEditPart();
				isModelTree = true;
			}else if(model instanceof ButtonModel){
				part = new ButtonModelEditPart();
				isModelTree = true;
			}else if(model instanceof WebViewModel){
				part = new WebViewEditPart();
				isModelTree = true;
			}else if(model instanceof ImageViewModel){
				part = new ImageViewEditPart();
				isModelTree = true;
			}else if(model instanceof GoogleMapModel){
				part = new FragmentEditPart();
				isModelTree = true;
			}else if(model instanceof FragmentModel){
				part = new FragmentEditPart();
				isModelTree = true;
			}else if(model instanceof VideoModel){
				part = new VideoEditPart();
				isModelTree = true;
			}else if(model instanceof TextViewModel){
				part = new TextViewEditPart();
				isModelTree = true;
			}else if(model instanceof YoutubeModel){
				part = new YoutubeEditPart();
				isModelTree = true;
			}
			if(ModelDataManager.getInstance().isLoad()){
				isModelTree = false;
			}
			if(umlModel.getModelTreeModel()==null && isModelTree && umlModel instanceof ViewModel){
				UMLDiagramModel diagramModel = (UMLDiagramModel)ProjectManager.getInstance().getActiveDiagram();

				ViewModel viewModel = (ViewModel)umlModel;
				viewModel.setName(ProjectManager.getInstance().getModelDefaultName(ProjectManager.getInstance().getModelType(viewModel),true));
				ModelTreeModel modelTreeModel = new ModelTreeModel(viewModel.getBasicModel());


				diagramModel.getModelTreeModel().getParentTreeModel().addChild(modelTreeModel);
				ModelBrowserManager.getInstance().getModelBrowser().getViewer().refresh();


			}
		}else if(model instanceof LineModel){
			if(model instanceof LineModel){
				part = new LineEditPart();
			}
		}
		part.setModel(model);
		return part;
	}
	public static PolylineConnection createNewWire(LineModel wire) {
		UMLPolylineConnection conn = new UMLPolylineConnection();//PKY 08101566 S
		UMLElementFigure uMLElementFigure = new UMLElementFigure();


		conn.add(uMLElementFigure, new ConnectionLocator(conn, ConnectionLocator.MIDDLE));
		return conn;
	}

}
