package org.pky.uml.figures;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.swt.graphics.FontMetrics;
import org.pky.uml.figures.common.UMLFigure;

public class VideoFigure extends UMLFigure{
	protected void paintFigure(Graphics graphics) {
		Rectangle r = Rectangle.SINGLETON;
		r.setBounds(getBounds());
		String url = "Video";


		FontMetrics fm = graphics.getFontMetrics();
		int charWidth = fm.getAverageCharWidth();
		int charHeight = fm.getHeight() / 2;

		int width = (url.length() * charWidth) / 2;
		width = r.width/2 -width;

		r.width = r.width -1;
		r.height = r.height -1;
		graphics.setBackgroundColor(ColorConstants.lightGray);
		graphics.fillRectangle(r);
		graphics.drawRectangle(r);

		graphics.drawString(url,width ,r.y+(r.height/2)-charHeight);

		super.paintFigure(graphics);
	}
}
