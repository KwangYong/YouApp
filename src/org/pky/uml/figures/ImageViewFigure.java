package org.pky.uml.figures;

import java.io.File;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.graphics.FontMetrics;
import org.eclipse.swt.graphics.Image;
import org.pky.uml.commons.managers.ProjectManager;
import org.pky.uml.figures.common.UMLFigure;

public class ImageViewFigure extends UMLFigure{

	String img = "";
	Image imgData;
	@Override
	public void paint(Graphics graphics) {
		// TODO Auto-generated method stub

		Rectangle r = Rectangle.SINGLETON;
		r.setBounds(getBounds());
		String str = "ImageView";


		FontMetrics fm = graphics.getFontMetrics();
		int charWidth = fm.getAverageCharWidth();
		int charHeight = fm.getHeight() / 2;

		int width = (str.length() * charWidth) / 2;
		width = r.width/2 -width;

		r.width = r.width -1;
		r.height = r.height -1;
		graphics.setBackgroundColor(ColorConstants.lightGray);
		graphics.fillRectangle(r);
		graphics.drawRectangle(r);

		if(!new File(img).exists()&&ProjectManager.getInstance().getExtImage(img)==null){
			graphics.drawString(str,width ,r.y+(r.height/2)-charHeight);
		}else if(imgData==null){
			
			Image image = ProjectManager.getInstance().getExtImage(img);
			if(image!=null){

				
				imgData = ImageDescriptor.createFromImageData(image.getImageData().scaledTo(r.width, r.height)).createImage();
				graphics.drawImage(imgData, new Point(r.x,r.y));
			}
		}else if(imgData!=null){
			graphics.drawImage(imgData, new Point(r.x,r.y));
		}

		super.paintFigure(graphics);

	}
	public String getImg() {
		return img;
	}
	public void setImg(String img) {
		this.img = img;
	}
	public Image getImgData() {
		return imgData;
	}
	public void setImgData(Image imgData) {
		this.imgData = imgData;
	}

}
