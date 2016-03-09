package org.pky.uml.editparts;

import org.eclipse.draw2d.ImageFigure;
import org.pky.uml.browser.common.propertybrowser.Property;
import org.pky.uml.editparts.common.UMLEditPart;
import org.pky.uml.figures.ImageViewFigure;

public class ImageViewEditPart extends UMLEditPart{
	@Override
	public void refreshVisuals() {
		// TODO Auto-generated method stub
		super.refreshVisuals();

		if(getUMLModel().getPropertyValue(Property.ID_IMG)!=null){
			ImageViewFigure figure = (ImageViewFigure)getFigure();
			if(figure.getImgData()!=null && (figure.getImgData().getImageData().width != getUMLModel().getViewModel().getSize().width
					|| figure.getImgData().getImageData().height != getUMLModel().getViewModel().getSize().height)
					|| !figure.getImg().equals((String)getUMLModel().getPropertyValue(Property.ID_IMG))){
					figure.setImgData(null);
			}
			figure.setImg((String)getUMLModel().getPropertyValue(Property.ID_IMG));
			
		}
	}
}
