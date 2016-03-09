package org.pky.uml.figures;

import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.PolylineConnection;
import org.eclipse.draw2d.ToolbarLayout;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.draw2d.text.TextFlow;

public class LinePanelFigure extends Figure {
    private IFigure pane;
    private TextFlow textFlow;
    public int w1 = 0;
    public int h1 = 0;
    public int oldh1 = 40;
    private int typeCompartment = 0; //attribute 0,operation 1

    public LinePanelFigure() {
        ToolbarLayout layout = new ToolbarLayout();
        layout.setMinorAlignment(ToolbarLayout.ALIGN_TOPLEFT);
        layout.setStretchMinorAxis(true);
        layout.setSpacing(2);
        setLayoutManager(layout);
    }

    public void setTypeCompartment(int type) {
        this.typeCompartment = type;
    }

    public int getTypeCompartment() {
        return typeCompartment;
    }

    public IFigure getContentsPane() {
        return pane;
    }

    public void setLocation(Point p) {
        if (getLocation().equals(p))
            return;
        Rectangle r = new Rectangle(getBounds());
        r.setLocation(p);
        setBounds(r);
    }

    public void setSize(int w, int h) {
        Rectangle bounds = getBounds();
        if (bounds.width == w && bounds.height == h)
            return;
        Rectangle r = new Rectangle(getBounds());
        r.setSize(w, h);
        setBounds(r);
    }

    //protected void primTranslate(int dx, int dy) {
    //	bounds.x += dx;
    //	bounds.y += dy;
    //	if (useLocalCoordinates()) {
    //		fireCoordinateSystemChanged();
    //		return;
    //	}
    //	for (int i = 0; i < children.size(); i++)
    //		((IFigure)children.get(i)).translate(dx, dy);
    //}
    public void setBounds(Rectangle rect) {
        int x = bounds.x, y = bounds.y;
        boolean resize = (rect.width != bounds.width) || (rect.height != bounds.height),
            translate = (rect.x != x) || (rect.y != y);
        if ((resize || translate) && isVisible())
            erase();
        if (translate) {
            int dx = rect.x - x;
            int dy = rect.y - y;
            int x1 = bounds.x + dx;
            int y1 = bounds.y + dy;
            System.out.println("this.getParent().getBounds():" + this.getParent().getBounds());
            System.out.println("rect:" + rect);
            //		System.out.println("rect:"+this);
            if (this.getParent() instanceof PolylineConnection) {
                PolylineConnection plc = (PolylineConnection)this.getParent();
                Point pt = plc.getEnd();
                Rectangle rt = Rectangle.SINGLETON;
                rt.setBounds(new Rectangle(x1, y1, rect.width, rect.height));
                System.out.println("x1:" + x1);
                System.out.println("y1:" + y1);
                //			if(rt.contains(pt)){
                ////				dx =
                //				System.out.println("포함"+pt);
                //			}
                //			else{
                //				System.out.println("노포함"+pt);
                //			}
                System.out.println("pt:" + pt);
                //			System.out.println("dy"+dy);
            }
            //		if(x1)
            primTranslate(dx, dy);
        }
        bounds.width = rect.width;
        bounds.height = rect.height;
        if (translate || resize) {
            if (resize)
                invalidate();
            fireFigureMoved();
            repaint();
        }
    }
}
