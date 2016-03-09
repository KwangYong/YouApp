package org.pky.uml.figures;

import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.StackLayout;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.graphics.Image;
import org.pky.uml.commons.managers.ProjectManager;
import org.pky.uml.figures.common.UMLFigure;
import org.pky.uml.model.LayoutModel;

public class LayoutFigure extends UMLFigure{
	private Image imgData;
	private String img;
	public LayoutFigure(){

		setOpaque(true);
		setLayoutManager(new StackLayout());

	}
	
	protected void paintFigure(Graphics graphics) {
		Rectangle r = Rectangle.SINGLETON;
		r.setBounds(getBounds());
//		r.width = r.width-1;
//		r.height = r.height-1;
		setOpaque(false);
		
//		setLayoutManager(new StackLayout());
//		graphics.setAlpha(140);

		
		Image image = ProjectManager.getInstance().getImage("layout_phone.png");
//		
		graphics.drawImage(image, new Point(r.x, r.y));
		
		graphics.setAlpha(950);
		graphics.fillRectangle(r.x+LayoutModel.DISPLAY_X, r.y+LayoutModel.DISPLAY_Y, LayoutModel.DISPLAY_WIDTH, LayoutModel.DISPLAY_HEIGHT);
		
		if(imgData==null && img!=null && !img.equals("")){

			Image backImg = ProjectManager.getInstance().getExtImage(img);
			if(backImg!=null){
				imgData = ImageDescriptor.createFromImageData(backImg.getImageData().scaledTo(LayoutModel.DISPLAY_WIDTH, LayoutModel.DISPLAY_HEIGHT)).createImage();
//				graphics.setAlpha(100);
				graphics.drawImage(imgData, new Point(r.x+LayoutModel.DISPLAY_X,r.y+LayoutModel.DISPLAY_Y));
			}
		}else if(imgData!=null){
//			graphics.setAlpha(100);
			graphics.drawImage(imgData, new Point(r.x+LayoutModel.DISPLAY_X,r.y+LayoutModel.DISPLAY_Y));
		}		
		
//		graphics.drawRectangle(new Rectangle(r.x+LayoutModel.DISPLAY_X+21, r.y+LayoutModel.DISPLAY_Y+102, LayoutModel.DISPLAY_WIDTH-20, 100));
		super.paintFigure(graphics);


	}
	public Image getImgData() {
		return imgData;
	}
	public String getImg() {
		return img;
	}
	public void setImgData(Image imgData) {
		this.imgData = imgData;
	}
	public void setImg(String img) {
		this.img = img;
	}
}