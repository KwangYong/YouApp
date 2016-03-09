package org.pky.uml.editor.action;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.jface.action.Action;
import org.eclipse.ui.IEditorPart;
import org.pky.uml.browser.common.propertybrowser.Property;
import org.pky.uml.commons.managers.ProjectManager;
import org.pky.uml.model.command.UMLChangeLayoutCommand;
import org.pky.uml.model.common.UMLModel;

public class ModelImageSizeChangeAction  extends Action {
	public final static String ID = "org.pky.uml.editor.action.ModelImageSizeChangeAction";
	public ModelImageSizeChangeAction(IEditorPart editor) {
		this.setId("org.pky.uml.editor.action.ModelImageSizeChangeAction");
		this.setText("이미지 크기로 변경");

		this.setEnabled(true);

	}
	protected ModelImageSizeChangeAction() {
		System.out.print("");
	}

	public void setEnable(boolean enable) {
		this.setEnabled(enable);
	}
	
	protected boolean calculateEnabled() {
		return true;
	}

	public void run() {


		for(int i = 0 ; i < ProjectManager.getInstance().getSelections().size(); i ++){
			UMLModel model = ProjectManager.getInstance().getSelections().get(i).getUMLModel();
			String value = (String)model.getPropertyValue(Property.ID_IMG);
			if(value==null)
				continue;
			File f = new File((String)value);

			if(f.exists()&& f.isFile()){
				BufferedImage bimg = null;
				try{
					bimg = ImageIO.read(f);

					int imgWidth = bimg.getWidth();
					int imgHeight = bimg.getHeight();
					int modelWidth = model.getViewModel().getSize().width;
					int modelHeight = model.getViewModel().getSize().height;

					UMLChangeLayoutCommand command = new UMLChangeLayoutCommand();
					command.setModel(model);
					command.setLocation(new Rectangle(model.getLocationRect().x,model.getLocationRect().y,imgWidth, imgHeight));
					command.execute();

				}catch(Exception e){
					e.printStackTrace();
				}


			}
		}




	}
}
