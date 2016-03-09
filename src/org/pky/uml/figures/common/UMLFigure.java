package org.pky.uml.figures.common;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Vector;

import org.eclipse.draw2d.ChopboxAnchor;
import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.ConnectionAnchor;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.PointList;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.swt.graphics.Color;
import org.pky.uml.commons.managers.ProjectManager;

public class UMLFigure extends Figure{

	protected Hashtable connectionAnchors = new Hashtable(7);
	protected Vector inputConnectionAnchors = new Vector(2, 2);
	protected Vector outputConnectionAnchors = new Vector(2, 2);
	Color backGroundColor = ColorConstants.tooltipBackground;
	public HashMap property = new HashMap();
	
	public UMLFigure(){
		setOpaque(true);
	}
	protected void paintFigure(Graphics graphics) {
		// TODO Auto-generated method stub
//		super.paintFigure(graphics);
		
		if(ProjectManager.getInstance().isActionSelection()){
			Rectangle r = Rectangle.SINGLETON;
			r.setBounds(getBounds());
			PointList pointList = new PointList();
			
			pointList.addPoint(r.x+3, r.y+3);
			pointList.addPoint(r.x+r.width-3, r.y+3);
			pointList.addPoint(r.x+r.width-3, r.y+r.height-3);
			pointList.addPoint(r.x+3, r.y+r.height-3);
			pointList.addPoint(r.x+3, r.y+3);
			graphics.setLineWidth(5);
			graphics.drawPolyline(pointList);

		}
	}


	public ConnectionAnchor connectionAnchorAt(Point p) {
		ConnectionAnchor closest = null;
		long min = Long.MAX_VALUE;
		Enumeration e = getSourceConnectionAnchors().elements();
		System.out.println("ddd");
		while (e.hasMoreElements()) {
			ConnectionAnchor c = (ConnectionAnchor)e.nextElement();
			Point p2 = c.getLocation(null);
			long d = p.getDistance2(p2);
			if (d < min) {
				min = d;
				closest = c;
			}
		}
		e = getTargetConnectionAnchors().elements();
		while (e.hasMoreElements()) {
			ConnectionAnchor c = (ConnectionAnchor)e.nextElement();
			Point p2 = c.getLocation(null);
			long d = p.getDistance2(p2);
			if (d < min) {
				min = d;
				closest = c;
			}
		}
		return closest;
	}

	public ConnectionAnchor getConnectionAnchor(String terminal) {
		//		ChopboxAnchor anchor = new ChopboxAnchor(this);
		//		return anchor;
		//		ChopboxAnchor anchor= (ChopboxAnchor)connectionAnchors.get(terminal);
		//terminal
//		terminal = "";
		Object anchor = connectionAnchors.get(terminal);
//		if(this instanceof LifeLineFigure){
//		SeqChopboxAnchor chopboxAnchor = new SeqChopboxAnchor(this);
//		return chopboxAnchor;
//		
		if (anchor instanceof ChopboxAnchor) {
			ChopboxAnchor chopboxAnchor = (ChopboxAnchor)anchor;
			return chopboxAnchor;
		}

//		if (anchor instanceof SeqChopboxAnchor) {
//	SeqChopboxAnchor chopboxAnchor = (SeqChopboxAnchor)anchor;
//		return chopboxAnchor;
//		}
		if (anchor == null) {
		
				ChopboxAnchor chopboxAnchor = new ChopboxAnchor(this);
				return chopboxAnchor;
			
		}
		return null;
		//		return anchor;
	}
	// seq
	public String getConnectionAnchorName(ConnectionAnchor c) {
		Enumeration keys = connectionAnchors.keys();
		String key;
		while (keys.hasMoreElements()) {
			key = (String)keys.nextElement();
			System.out.println("key:"+connectionAnchors.get(key));
			System.out.println("c:"+c);
			if (connectionAnchors.get(key).equals(c))
				return key;
		}
		return null;
	}

	public ConnectionAnchor getSourceConnectionAnchorAt(Point p) {
		ConnectionAnchor closest = null;
		long min = Long.MAX_VALUE;
		Enumeration e = getSourceConnectionAnchors().elements();
		while (e.hasMoreElements()) {
			ConnectionAnchor c = (ConnectionAnchor)e.nextElement();
			Point p2 = c.getLocation(null);
			long d = p.getDistance2(p2);
			if (d < min) {
				min = d;
				closest = c;
			}
		}
		return closest;
	}

	public Vector getSourceConnectionAnchors() {
		return outputConnectionAnchors;
	}

	public ConnectionAnchor getTargetConnectionAnchorAt(Point p) {
		ConnectionAnchor closest = null;
		long min = Long.MAX_VALUE;
		Enumeration e = getTargetConnectionAnchors().elements();
		while (e.hasMoreElements()) {
			ConnectionAnchor c = (ConnectionAnchor)e.nextElement();
			Point p2 = c.getLocation(null);
			long d = p.getDistance2(p2);
			if (d < min) {
				min = d;
				closest = c;
			}
		}
		return closest;
	}

	public Vector getTargetConnectionAnchors() {
		return inputConnectionAnchors;
	}

	public void setFigureBackgroundColor(Color p) {
		this.backGroundColor = p;
	}

	public Color getFigureBackgroundColor() {
		return this.backGroundColor;
	}

	public HashMap getProperty() {
		return property;
	}

	public void setProperty(HashMap property) {
		this.property = property;

	}




}
