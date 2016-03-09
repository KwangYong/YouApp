package org.pky.uml.figures;

import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;

import org.eclipse.draw2d.ChopboxAnchor;
import org.eclipse.draw2d.ConnectionAnchor;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Insets;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.draw2d.text.TextFlow;
import org.eclipse.gef.handles.HandleBounds;
import org.pky.uml.commons.managers.ProjectManager;
import org.pky.uml.figures.common.UMLFigure;

public class ViewFigure  extends UMLFigure implements HandleBounds {
	private IFigure pane;
	private TextFlow textFlow;
	private boolean isMode = true;
	String multi = "";
	public void setMultiplicity(String p){
		this.multi = p;
	}

	public ViewFigure() {
		//	setBorder(new BoundaryBorder());
		//	setBorder(new TitleBarBorder(ColorConstants.black, 1));

		//	setBorder(new ButtonBorder());

		setOpaque(true);
	}


	public ConnectionAnchor getTargetConnectionAnchorAt(Point p) {
		ConnectionAnchor anchor = new ChopboxAnchor(this);
		anchor.getLocation(p);
		//	inputConnectionAnchors.addElement(anchor);
		return anchor;
	}

	public ConnectionAnchor getSourceConnectionAnchorAt(Point p) {
		ConnectionAnchor anchor = new ChopboxAnchor(this);
		//	outputConnectionAnchors.addElement(anchor);
		return anchor;
	}

	public IFigure getContentsPane() {
		return pane;
	}



	/** @see org.eclipse.gef.handles.HandleBounds#getHandleBounds() */
	public Rectangle getHandleBounds() {
		return getBounds().getCropped(new Insets(2, 0, 2, 0));
	}



	public Dimension getPreferredSize(int w, int h) {
		Dimension prefSize = super.getPreferredSize(w, h);
		Dimension defaultSize = new Dimension(100, 50);
		prefSize.union(defaultSize);
		return prefSize;
	}



	//public void setMode(boolean p){
	//	if(this.isMode!=p){
	//		if(p==true){
	//			 setBorder(new LineBorder(ColorConstants.black,1));
	//		}
	//		else{
	//			setBorder(new InitialBorder());
	//		}
	//	}
	//	this.isMode = p;
	//	
	//}
	public boolean getMode() {
		return this.isMode;
	}

	/** @see org.eclipse.draw2d.Figure#paintFigure(Graphics) */
	protected void paintFigure(Graphics graphics) {
		Rectangle r = Rectangle.SINGLETON;
		r.setBounds(getBounds());
		r.width = r.width -4;
		r.height = r.height -4;
		graphics.setLineWidth(2);
//		graphics.fillRectangle(r);

		//        this.setForegroundColor(ProjectManager.getInstance().getDefaultColor());//PKY 08081101 S 프로젝트 전체의 객체 테두리 색상 설정 할  수있도록 수정
	}

	public Rectangle getActorBounds(Rectangle bounds) {
		int height = bounds.height * 7 / 10;
		int width = bounds.width;
		int x = 0;
		int y = 0;
		if (width * 4 >= height * 3) { // Width가 더 클경우
			width = height * 3 / 4;
			x += (bounds.width - width) / 2;
		} else {
			height = width * 4 / 3;
			y += (bounds.height * 7 / 10 - height) / 2;
		}
		return new Rectangle(x, y, width, height);
	}

	protected void paintBody(Graphics g2) {
		Ellipse2D.Float bounds = getCircle();
		g2.fillOval((int)bounds.getX() + (int)Math.round(1), (int)bounds.getY() + (int)Math.round(1), (int)bounds.getWidth() - (int)Math.round(1),
				(int)bounds.getHeight() - (int)Math.round(1));
		//오른쪽에 있는 컴포넌트의 사각형을 그린다 ///
		g2.fillOval((int)bounds.getX(), (int)bounds.getY(), (int)bounds.getWidth() - (int)Math.round(2),
				(int)bounds.getHeight() - (int)Math.round(2));
		g2.drawOval((int)bounds.getX(), (int)bounds.getY(), (int)bounds.getWidth() - (int)Math.round(2),
				(int)bounds.getHeight() - (int)Math.round(2));
		Line2D[] lines = getBodyLines();
		for (int i = 0; i < lines.length; i++) {

			g2.drawLine((int)lines[i].getX1(), (int)lines[i].getY1(), (int)lines[i].getX2(), (int)lines[i].getY2());
		}
	}

	protected Line2D[] getBodyLines() {
		Rectangle r = Rectangle.SINGLETON;
		r.setBounds(getBounds());
		Rectangle actorBounds = r;
		int x = actorBounds.x;
		int y = actorBounds.y;
		Line2D[] _lines = new Line2D[4];
		_lines[0] = new Line2D.Float(x + actorBounds.width / 2, y + actorBounds.height * 4 / 10 + 1, x + actorBounds.width / 2,
				y + actorBounds.height * 7 / 10);
		_lines[1] = new Line2D.Float(x + 1, y + actorBounds.height * 5 / 10, x + actorBounds.width - 1 * 2,
				y + actorBounds.height * 5 / 10);
		_lines[2] = new Line2D.Float(x + actorBounds.width / 2, y + actorBounds.height * 7 / 10, x + 1,
				y + actorBounds.height - 1);
		_lines[3] = new Line2D.Float(x + actorBounds.width / 2, y + actorBounds.height * 7 / 10, x + actorBounds.width - 1 * 2,
				y + actorBounds.height - 1);
		//N3soft V1.3013 2004.04.08 안성수 2줄이상 보여지도록 수정 end
		return _lines;
	}

	protected Ellipse2D.Float getCircle() {
		Rectangle r = Rectangle.SINGLETON;
		r.setBounds(getBounds());
		Rectangle actorBounds = r;
		float _x = actorBounds.width * 2 / 9 + actorBounds.x;
		float _y = 1 + actorBounds.y;
		float _width = actorBounds.width * 5 / 9;
		float _height = 1 + actorBounds.height * 4 / 10;
		return new Ellipse2D.Float(_x, _y, _width, _height);
	}



	public String toString() {
		return "ViewFigure"; //$NON-NLS-1$
	}

	public void validate() {
		if (isValid()) return;
		//	layoutConnectionAnchors();
		super.validate();
	}

	protected boolean useLocalCoordinates() { return true; }
}
