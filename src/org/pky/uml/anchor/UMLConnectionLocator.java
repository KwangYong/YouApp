package org.pky.uml.anchor;

import org.eclipse.draw2d.Connection;
import org.eclipse.draw2d.ConnectionLocator;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.PointList;
import org.pky.uml.model.LineModel;
import org.pky.uml.model.common.UMLModel;

public class UMLConnectionLocator extends ConnectionLocator {
	//msg
	UMLModel pp = null;
	public Figure figure = null;
	public Point oldPoint = new Point(0,0);
	public Point initPoint = new Point(0,0);
	private boolean isBegin = false;
	private Connection connection=null;

	public UMLConnectionLocator(Connection connection) {
		super(connection);
		this.connection = connection;
	}

	public UMLConnectionLocator(Connection connection, int align) {
		super(connection, align);
		this.connection = connection;
	}

	public void setModel(UMLModel e) {
		pp = e;

	}
	public void setAlignment(int align) {
		super.setAlignment(align);
	}

	protected Point getReferencePoint() {
		Point p = getLocation(getConnection().getPoints());
		//		this.getConnection().getParent().

		


		oldPoint = new Point(p.x,p.y);
		return p;
	}

	protected Point getLocation(PointList points) {
		//msg
		switch (getAlignment()) {
		case SOURCE:
			return points.getPoint(Point.SINGLETON, 0);
		case TARGET:
			return points.getPoint(Point.SINGLETON, points.size() - 1);
		case MIDDLE:

			if (points.size() % 2 == 0) {
				int i = points.size() / 2;
				int j = i - 1;
				Point p1 = points.getPoint(j);
				Point p2 = points.getPoint(i);
				Dimension d = p2.getDifference(p1);
				return Point.SINGLETON.setLocation(p1.x + d.width / 2, p1.y + d.height / 2);
			}
			int i = (points.size() - 1) / 2;
			return points.getPoint(Point.SINGLETON, i);
		default:
			return new Point();
		}
	}
	//	public void relocate(IFigure target) {
	//		Dimension prefSize = target.getPreferredSize();
	//		Dimension nPrefSize = new Dimension(prefSize.width+100,prefSize.height+100);
	//		Point center = getReferencePoint();
	//		target.translateToRelative(center);
	//		target.setBounds(getNewBounds(nPrefSize, center));
	//	}

	public boolean isBegin() {
		return isBegin;
	}

	public void setBegin(boolean isBegin) {
		this.isBegin = isBegin;
	}
	//PKY 08052801 S LineOladPoint 추가
	public UMLModel getPp() {
		return pp;
	}


	public Point getOldPoint() {
		return oldPoint;
	}


	public void setOldPoint(Point oldPoint) {
		this.oldPoint = oldPoint;
	}
	//PKY 08052801 E LineOladPoint 추가

	public Point getInitPoint() {
		return initPoint;
	}

	public void setInitPoint(Point initPoint) {
		this.initPoint = initPoint;
	}

	public Connection getConnection() {
		return connection;
	}

	public void setConnection(Connection connection) {
		this.connection = connection;
	}
}
