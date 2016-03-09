package org.pky.uml.figures;

import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.geometry.Rectangle;
import org.pky.uml.figures.common.UMLFigure;

public class ElementGroupFigure extends UMLFigure{


	public ElementGroupFigure(){
		setOpaque(true);

	}
	protected void paintFigure(Graphics graphics) {
		Rectangle r = Rectangle.SINGLETON;
		
		r.setBounds(getBounds());
		r.width = r.width-1;
		r.height = r.height-1;
		graphics.drawRectangle(r);

	}
}