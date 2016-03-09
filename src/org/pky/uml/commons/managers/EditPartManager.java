package org.pky.uml.commons.managers;

import org.eclipse.draw2d.Figure;
import org.eclipse.gef.EditPart;
import org.pky.uml.editparts.ButtonModelEditPart;
import org.pky.uml.editparts.ClassEditPart;
import org.pky.uml.editparts.EditTextEditPart;
import org.pky.uml.editparts.ElementEditPart;
import org.pky.uml.editparts.ElementGroupEditPart;
import org.pky.uml.editparts.FragmentEditPart;
import org.pky.uml.editparts.ImageViewEditPart;
import org.pky.uml.editparts.LayoutEditPart;
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
import org.pky.uml.editparts.common.UMLDiagramEditPart;
import org.pky.uml.editparts.common.UMLEditPart;
import org.pky.uml.figures.ButtonModelFigure;
import org.pky.uml.figures.ClassFigure;
import org.pky.uml.figures.EditTextFigure;
import org.pky.uml.figures.ElementFigure;
import org.pky.uml.figures.ElementGroupFigure;
import org.pky.uml.figures.FragmentFigure;
import org.pky.uml.figures.ImageViewFigure;
import org.pky.uml.figures.LayoutFigure;
import org.pky.uml.figures.ListViewFigure;
import org.pky.uml.figures.PackageFigure;
import org.pky.uml.figures.StateBarFigure;
import org.pky.uml.figures.TextViewFigure;
import org.pky.uml.figures.TitleBarFigure;
import org.pky.uml.figures.UseCaseFigure;
import org.pky.uml.figures.VideoFigure;
import org.pky.uml.figures.ViewFigure;
import org.pky.uml.figures.WebViewFigure;
import org.pky.uml.figures.YoutubeFigure;
import org.pky.uml.model.TextViewModel;

public class EditPartManager {
	//µî·Ï
	public static Figure getNewFigure(EditPart obj){
		UMLEditPart editPart = (UMLEditPart)obj;
		if(obj instanceof UseCaseEditPart){
			return new UseCaseFigure();
		}else if(obj instanceof ElementEditPart){
			return new ElementFigure();
		}else if(obj instanceof ClassEditPart){
			return new ClassFigure();
		}else if(obj instanceof ElementGroupEditPart){
			return new ElementGroupFigure();
		}else if(obj instanceof ViewEditPart){
			return new ViewFigure();
		}else if(obj instanceof PackageEditPart){
			return new PackageFigure();
		}else if(obj instanceof LayoutEditPart){
			return new LayoutFigure();
		}else if(obj instanceof StateBarEditPart){
			return new StateBarFigure();
		}else if(obj instanceof TitleBarEditPart){
			return new TitleBarFigure();
		}else if(obj instanceof EditTextEditPart){
			return new EditTextFigure();
		}else if(obj instanceof ListViewEditPart){
			return new ListViewFigure();
		}else if(obj instanceof ButtonModelEditPart){
			return new ButtonModelFigure();
		}else if(obj instanceof WebViewEditPart){
			return new WebViewFigure();
		}else if(obj instanceof ImageViewEditPart){
			return new ImageViewFigure();
		}else if(obj instanceof FragmentEditPart){
			return new FragmentFigure();
		}else if(obj instanceof VideoEditPart){
			return new VideoFigure();
		}else if(obj instanceof TextViewEditPart){
			return new TextViewFigure();
		}else if(obj instanceof YoutubeEditPart){
			return new YoutubeFigure();
		}
		else if(obj instanceof UMLDiagramEditPart){

		}
		return null;
	}



}
