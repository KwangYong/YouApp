package org.pky.uml.figures;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.graphics.FontMetrics;
import org.eclipse.swt.graphics.Image;
import org.pky.uml.commons.managers.ProjectManager;
import org.pky.uml.figures.common.UMLFigure;

public class FragmentFigure extends UMLFigure{
	@Override
	public void paint(Graphics graphics) {
		// TODO Auto-generated method stub

		Rectangle r = Rectangle.SINGLETON;
		r.setBounds(getBounds());
		String str = "Fragment";


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


		graphics.drawString(str,width ,r.y+(r.height/2)-charHeight);


		super.paintFigure(graphics);

	}
}
