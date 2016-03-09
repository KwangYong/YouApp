package org.pky.uml.figures;

import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.swt.SWT;
import org.pky.uml.figures.common.UMLFigure;

public class UseCaseFigure extends UMLFigure{


	public UseCaseFigure(){
		this.setOpaque(false);
	}

	protected void paintFigure(Graphics graphics) {
		
		Rectangle r = Rectangle.SINGLETON;
		r.setBounds(getBounds());
		r.width = r.width-1;
		r.height = r.height-1;
		
		
		graphics.setAntialias(SWT.ON);
		graphics.setAdvanced(true);
		graphics.drawOval(r);

	}
}
