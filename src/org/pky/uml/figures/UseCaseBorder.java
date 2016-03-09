package org.pky.uml.figures;

import org.eclipse.draw2d.AbstractBorder;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Insets;
import org.eclipse.draw2d.geometry.PointList;
import org.eclipse.draw2d.geometry.Rectangle;

public class UseCaseBorder extends AbstractBorder {
    protected static Insets insets = new Insets(8, 6, 8, 6);
    protected static PointList connector = new PointList();
    protected static PointList bottomConnector = new PointList();

   
    public Insets getInsets(IFigure figure) {
    	
        return insets;
    }

    public void paint(IFigure figure, Graphics g, Insets in) {
        Rectangle r = figure.getBounds().getCropped(in);
        //		r.setBounds(getBounds());
        r.width = r.width - 3;
        r.height = r.height - 3;
       // g.drawOval(r);

        

    }
}
