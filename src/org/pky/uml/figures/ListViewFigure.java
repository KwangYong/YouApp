package org.pky.uml.figures;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.PointList;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.widgets.Display;
import org.pky.uml.figures.common.UMLFigure;

public class ListViewFigure extends UMLFigure{

	int type = 0;

	public ListViewFigure(){

		setOpaque(true);

	}
	protected void paintFigure(Graphics graphics) {
		Rectangle r = Rectangle.SINGLETON;
		r.setBounds(getBounds());
		graphics.setBackgroundColor(ColorConstants.white);
		graphics.fillRectangle(r);
		r.width = r.width-1;
		r.height = r.height-1;
		int count = 0;
		if(type == 1){
			PointList pointList = new PointList();
			pointList.addPoint(r.x+4, r.y+13);
			pointList.addPoint(r.x+6, r.y+17);
			pointList.addPoint(r.x+8, r.y+13);
			graphics.setLineWidth(1);
	 		graphics.drawPolyline(pointList);
		}
		for(int i = 0 ; i < r.height; i ++){
			
			if(i%40==0){
				count++;
				Font font = new Font( Display.getDefault(), "Times New Roman", 20,	SWT.NORMAL );
				graphics.setFont( font ); 
				graphics.drawString("Item "+count, new Point(r.x+15, i));
				font = new Font( Display.getDefault(), "Times New Roman", 10,	SWT.NORMAL );
				graphics.setFont( font ); 
				graphics.drawString("Sub item "+count, new Point(r.x+15, i+20));
			}
		}
		
		graphics.setLineWidth(2);
		graphics.drawRectangle(r);

	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
}