package org.pky.uml.figures;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.geometry.Rectangle;
import org.pky.uml.figures.common.UMLFigure;

public class PackageFigure extends UMLFigure{

	public PackageFigure(){
//		setBorder(new UseCaseBorder());
//		setOpaque(true);

	}
	
	protected void paintFigure(Graphics g) {
		Rectangle r = Rectangle.SINGLETON;
		r.setBounds(getBounds());
        
//        g.drawRectangle(r);
        g.setBackgroundColor(ColorConstants.buttonDarker);
        Rectangle r2 = new Rectangle(r.x+r.width+1,r.y+3,r.width+2,r.height);
        g.fillRectangle(r2);
        Rectangle r3 = new Rectangle(r.x+3,r.y+r.height+1,r.width+10,r.height);
        g.fillRectangle(r3);
      //PKY 08072401 E 객체 테두리 넣기

    
	}
}
