package org.pky.uml.figures;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.swt.graphics.FontMetrics;
import org.pky.uml.figures.common.UMLFigure;

public class ButtonModelFigure extends UMLFigure{

	private String text = "";
	protected void paintFigure(Graphics graphics) {
		// TODO Auto-generated method stub
		//		super.paintFigure(graphics);

		if(text.equals("")){
			text = "Button";
		}
		Rectangle r = Rectangle.SINGLETON;
		r.setBounds(getBounds());
		
		r.width = r.width -1;
		r.height = r.height -1;
		
		FontMetrics fm = graphics.getFontMetrics();
		int charWidth = fm.getAverageCharWidth();
		int charHeight = fm.getHeight() / 2;
//		System.out.println(charWidth);
		int x = (text.length() * charWidth);// (text.length());
		x = r.width/2 - (x/2);


		graphics.setForegroundColor(ColorConstants.black);	
		
		graphics.setBackgroundColor(ColorConstants.lightGray);
		graphics.fillRectangle(r);
		graphics.drawString(text,x ,r.y+(r.height/2)-charHeight);
		
		graphics.drawLine(new Point(r.x,r.y+r.height), new Point(r.x+r.width,r.y+r.height));
		graphics.setBackgroundColor(ColorConstants.gray);
		graphics.drawLine(new Point(r.x+r.width,r.y), new Point(r.x+r.width,r.y+r.height));
		


	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	
}
