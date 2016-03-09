package org.pky.uml.model.command;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.draw2d.AbstractRouter;
import org.eclipse.draw2d.Connection;
import org.eclipse.draw2d.ConnectionAnchor;
import org.eclipse.draw2d.ConnectionRouter;
import org.eclipse.draw2d.PositionConstants;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.PointList;
import org.eclipse.draw2d.geometry.Ray;
import org.eclipse.draw2d.geometry.Rectangle;
import org.pky.uml.commons.managers.ProjectManager;

public class AutoRoutingCommand extends AbstractRouter{

	private Map rowsUsed = new HashMap();
	private Map colsUsed = new HashMap();
	//private Hashtable offsets = new Hashtable(7);

	private Map reservedInfo = new HashMap();

	private class ReservedInfo {
		public List reservedRows = new ArrayList(2);
		public List reservedCols = new ArrayList(2);
	}

	private static Ray 	UP		= new Ray(0, -1),
	DOWN	= new Ray(0, 1),
	LEFT	= new Ray(-1, 0),
	RIGHT	= new Ray(1, 0);


	/**
	 * @see ConnectionRouter#invalidate(Connection)
	 */
	public void invalidate(Connection connection) {
		removeReservedLines(connection);
	}

	private int getColumnNear(Connection connection, int r, int n, int x) {
		return r;
	}

	/**
	 * Returns the direction the point <i>p</i> is in relation to the given rectangle.
	 * Possible values are LEFT (-1,0), RIGHT (1,0), UP (0,-1) and DOWN (0,1).
	 * 
	 * @param r the rectangle
	 * @param p the point
	 * @return the direction from <i>r</i> to <i>p</i>
	 */
	protected Ray getDirection(Rectangle r, Point p) {
		int i, distance = Math.abs(r.x - p.x);
		Ray direction;

		direction = LEFT;

		i = Math.abs(r.y - p.y);
		if (i <= distance) {
			distance = i;
			direction = UP;
		}

		i = Math.abs(r.bottom() - p.y);
		if (i <= distance) {
			distance = i;
			direction = DOWN;
		}

		i = Math.abs(r.right() - p.x);
		if (i < distance) {
			distance = i;
			direction = RIGHT;
		}

		return direction;
	}

	protected Ray getEndDirection(Connection conn) {
		ConnectionAnchor anchor = conn.getTargetAnchor();
		Point p = getEndPoint(conn);
		Rectangle rect;
		if (anchor.getOwner() == null)
			rect = new Rectangle(p.x - 1, p.y - 1, 2, 2);
		else {
			rect = conn.getTargetAnchor().getOwner().getBounds().getCopy();
			conn.getTargetAnchor().getOwner().translateToAbsolute(rect);
		}
		return getDirection(rect, p);
	}

	protected int getRowNear(Connection connection, int r, int n, int x) {
//		int min = Math.min(n, x),
//		max = Math.max(n, x);
//		if (min > r) {
//		max = min;
//		min = r - (min - r);
//		}
//		if (max < r) {
//		min = max;
//		max = r + (r - max);
//		}

//		int proximity = 0;
//		int direction = -1;
//		if (r % 2 == 1)
//		r--;
//		Integer i;
//		while (proximity < r) {
//		i = new Integer(r + proximity * direction);
//		if (!rowsUsed.containsKey(i)) {
//		rowsUsed.put(i, i);
//		reserveRow(connection, i);
//		return i.intValue();
//		}
//		int j = i.intValue();
//		if (j <= min)
//		return j + 2;
//		if (j >= max)
//		return j - 2;
//		if (direction == 1)
//		direction = -1;
//		else {
//		direction = 1;
//		proximity += 2;
//		}
//		}
		return r;
	}

	protected Ray getStartDirection(Connection conn) {
		ConnectionAnchor anchor = conn.getSourceAnchor();
		Point p = getStartPoint(conn);
		Rectangle rect;
		if (anchor.getOwner() == null)
			rect = new Rectangle(p.x - 1, p.y - 1, 2, 2);
		else {
			rect = conn.getSourceAnchor().getOwner().getBounds().getCopy();
			conn.getSourceAnchor().getOwner().translateToAbsolute(rect);
		}

		return getDirection(rect, p);
	}

	protected void processPositions(Ray start, Ray end, List positions, 
			boolean horizontal, Connection conn) {
		removeReservedLines(conn);

		int pos[] = new int[positions.size() + 2];
		if (horizontal)
			pos[0] = start.x;
		else
			pos[0] = start.y;
		int i;
		for (i = 0; i < positions.size(); i++) {
			pos[i + 1] = ((Integer)positions.get(i)).intValue();
		}
		if (horizontal == (positions.size() % 2 == 1))
			pos[++i] = end.x;
		else
			pos[++i] = end.y;

		PointList points = new PointList();
		points.addPoint(new Point(start.x, start.y));
		Point p;
		int current, prev, min, max;
		boolean adjust;
		for (i = 2; i < pos.length - 1; i++) {
			horizontal = !horizontal;
			prev = pos[i - 1];
			current = pos[i];

			adjust = (i != pos.length - 2);
			if (horizontal) {
				if (adjust) {
					min = pos[i - 2];
					max = pos[i + 2];
					pos[i] = current = getRowNear(conn, current, min, max);
				}
				p = new Point(prev, current);
			} else {
				if (adjust) {
					min = pos[i - 2];
					max = pos[i + 2];
					pos[i] = current = getColumnNear(conn, current, min, max);
				}
				p = new Point(current, prev);
			}
			points.addPoint(p);
		}
		points.addPoint(new Point(end.x, end.y));

		conn.setPoints(points);

	}

	/**
	 * @see ConnectionRouter#remove(Connection)
	 */
	public void remove(Connection connection) {
		removeReservedLines(connection);
	}

	protected void removeReservedLines(Connection connection) {
		ReservedInfo rInfo = (ReservedInfo) reservedInfo.get(connection);
		if (rInfo == null) 
			return;

		for (int i = 0; i < rInfo.reservedRows.size(); i++) {
			rowsUsed.remove(rInfo.reservedRows.get(i));
		}
		for (int i = 0; i < rInfo.reservedCols.size(); i++) {
			colsUsed.remove(rInfo.reservedCols.get(i));
		}
		reservedInfo.remove(connection);
	}

	protected void reserveColumn(Connection connection, Integer column) {
		ReservedInfo info = (ReservedInfo) reservedInfo.get(connection);
		if (info == null) {
			info = new ReservedInfo();
			reservedInfo.put(connection, info);
		}
		info.reservedCols.add(column);
	}

	protected void reserveRow(Connection connection, Integer row) {
		ReservedInfo info = (ReservedInfo) reservedInfo.get(connection);
		if (info == null) {
			info = new ReservedInfo();
			reservedInfo.put(connection, info);
		}
		info.reservedRows.add(row);
	}

	/**
	 * @see ConnectionRouter#route(Connection)
	 */
	public void route(Connection conn) {
		if ((conn.getSourceAnchor() == null) || (conn.getTargetAnchor() == null)) 
			return;
		int i;
		boolean isHorizon = false; 
		Point startPoint = getStartPoint(conn);
		conn.translateToRelative(startPoint);
		Point endPoint = getEndPoint(conn);
		conn.translateToRelative(endPoint);

		Ray start = new Ray(startPoint);
		Ray end = new Ray(endPoint);
		Ray average = start.getAveraged(end);

		Ray direction = new Ray(start, end);
		Ray startNormal = getStartDirection(conn);
		Ray endNormal   = getEndDirection(conn);

		List positions = new ArrayList(5);
		boolean horizontal = startNormal.isHorizontal();
		if (horizontal) 
			positions.add(new Integer(start.y));
		else
			positions.add(new Integer(start.x));
		horizontal = !horizontal;

		if (startNormal.dotProduct(endNormal) == 0) {
			if ((startNormal.dotProduct(direction) >= 0) 
					&& (endNormal.dotProduct(direction) <= 0)) {
				// 0
			} else {
				// 2
				if (startNormal.dotProduct(direction) < 0)
					i = startNormal.similarity(start.getAdded(startNormal.getScaled(10)));
				else {
					if (horizontal) 
						i = average.y;
					else 
						i = average.x;
				}
				positions.add(new Integer(i));
				horizontal = !horizontal;

				if (endNormal.dotProduct(direction) > 0)
					i = endNormal.similarity(end.getAdded(endNormal.getScaled(10)));
				else {
					if (horizontal) 
						i = average.y;
					else 
						i = average.x;
				}
				positions.add(new Integer(i));
				horizontal = !horizontal;
			}
		} else {
			if (startNormal.dotProduct(endNormal) > 0) {
				//1
				if (startNormal.dotProduct(direction) >= 0)
					i = startNormal.similarity(start.getAdded(startNormal.getScaled(10)));
				else
					i = endNormal.similarity(end.getAdded(endNormal.getScaled(10)));
				positions.add(new Integer(i));
				horizontal = !horizontal;
			} else {
				//3 or 1
				if (startNormal.dotProduct(direction) < 0) {
					i = startNormal.similarity(start.getAdded(startNormal.getScaled(10)));
					positions.add(new Integer(i));
					horizontal = !horizontal;
				}

				if (horizontal) 
					i = average.y;
				else 
					i = average.x;
				positions.add(new Integer(i));
				horizontal = !horizontal;

				if (startNormal.dotProduct(direction) < 0) {
					i = endNormal.similarity(end.getAdded(endNormal.getScaled(10)));
					positions.add(new Integer(i));
					horizontal = !horizontal;
				}
			}
		}
		if (horizontal) 
			positions.add(new Integer(end.y));
		else 
			positions.add(new Integer(end.x));
		
			ConnectionAnchor anchorSource = conn.getSourceAnchor();
			Rectangle rectSource = anchorSource.getOwner().getBounds();
			if(rectSource.contains(start.x,start.y)){
				int position = rectSource.getPosition(new Point(end.x,end.y));
				if(PositionConstants.WEST==position){
					if(positions.size()>2){
						positions.remove(0);
						positions.add(0,start.x = rectSource.x);
						positions.remove(1);
						positions.add(1,rectSource.y+(rectSource.height/2));
					}

					start.x = rectSource.x; 
					start.y = rectSource.y+(rectSource.height/2);
					isHorizon = true;

				}else if(PositionConstants.EAST==position){
					if(positions.size()>2){
						positions.remove(0);
						positions.add(0,rectSource.x+rectSource.width+10);

						positions.remove(1);
						positions.add(1,rectSource.y+rectSource.height/2+1);
					}

					start.x = rectSource.x+rectSource.width; 
					start.y = rectSource.y+rectSource.height/2+1; 
					isHorizon = true;
				}else if(PositionConstants.WEST+PositionConstants.NORTH==position){
					start.y = rectSource.y;
				}else if(PositionConstants.WEST+PositionConstants.SOUTH==position){
//					start.x = rectSource.x+rectSource.width;
					start.y = rectSource.y+rectSource.height;
					//PKY 08101569 S
					if(start.y>(Integer)positions.get(1)){
						positions.remove(1);
						positions.add(1, rectSource.y+rectSource.height);
					}
					//PKY 08101569 E
				}else if(PositionConstants.EAST+PositionConstants.NORTH==position){
					if(positions.size()>2){
						positions.remove(0);
						positions.add(0,rectSource.x+rectSource.width+10);

						positions.remove(1);
						positions.add(1, rectSource.y+(rectSource.height/2+1));
					}
					start.x = rectSource.x+rectSource.width;
					start.y = rectSource.y+rectSource.height/2+1; 
				}else if(PositionConstants.EAST+PositionConstants.SOUTH==position){
					start.y = rectSource.y+rectSource.height;
					if(positions.size()>2){
						//PKY 08101569 S
						if(start.y>(Integer)positions.get(1)){
							positions.remove(1);
							positions.add(1, rectSource.y+rectSource.height);
						}
						//PKY 08101569 E

					}
				}
			}
			ConnectionAnchor anchorTarget = conn.getTargetAnchor();
			Rectangle rectTarget = anchorTarget.getOwner().getBounds();
			if(rectTarget.contains(end.x,end.y)){
				int position = rectTarget.getPosition(new Point(start.x,start.y));
				if(PositionConstants.WEST==position){
					for(int count = 0 ; count < positions.size(); count ++){
						if(((Integer)positions.get(count))==end.x){
							positions.remove(count);
							positions.add(count,rectTarget.x-30);
							System.out.print("");
						}
					}
					end.x = rectTarget.x; 
//					end.y = rectTarget.y + rectTarget.height/2+1;
					isHorizon = true;
				}else if(PositionConstants.EAST==position){

					if(positions.size()>2){
						positions.remove(2);
						positions.add(2,rectTarget.x+rectTarget.width+10);
						if(positions.size()>4)
							positions.remove(4);
						if(positions.size()>3)
							positions.remove(3);

					}
					end.x = rectTarget.x+rectTarget.width; 
					isHorizon = true;
				}else if(PositionConstants.WEST+PositionConstants.NORTH==position){
					for(int count = 0 ; count < positions.size(); count ++){
						if(((Integer)positions.get(count))==end.x){
							positions.remove(count);
							positions.add(count,rectTarget.x+rectTarget.width/2);
						}
					}
					end.x = rectTarget.x+rectTarget.width/2;
					end.y = rectTarget.y;
				}else if(PositionConstants.WEST+PositionConstants.SOUTH==position){
					for(int count = 0 ; count < positions.size(); count ++){
						if(((Integer)positions.get(count))==end.x){
							positions.remove(count);
							positions.add(count,rectTarget.x+(rectTarget.width/2));
						}
					}
					end.x =rectTarget.x+(rectTarget.width/2);
					end.y = rectTarget.y+rectTarget.height;
				}else if(PositionConstants.EAST+PositionConstants.NORTH==position){
					end.y = rectTarget.y;
				}else if(PositionConstants.EAST+PositionConstants.SOUTH==position){
					for(int count = 0 ; count < positions.size(); count ++){
						if(((Integer)positions.get(count))==end.x){
							positions.remove(count);
							positions.add(count,rectTarget.x+(rectTarget.width/2));
						}
					}
					end.x = rectTarget.x+(rectTarget.width/2);

					end.y = rectTarget.y+rectTarget.height;
				}

			}
		
		//PKY 08101530 E

		processPositions(start, end, positions, startNormal.isHorizontal(), conn);
		if(isHorizon && conn.getPoints().size()==4){
			PointList points = conn.getPoints();
			Point point0 = points.getPoint(0);
			Point point1 = points.getPoint(1);
			Point point2 = points.getPoint(2);
			Point point3 = points.getPoint(3);
			int isHorizonHieght = point2.y - point3.y;
			int isHorizonWidth = point0.x - point3.x;
			isHorizonWidth =  isHorizonWidth/2;
			if(isHorizonHieght!=0){
				point1.x = point1.x-isHorizonWidth; 
				Point insertPoint = new Point(point1.x,point2.y-isHorizonHieght);

				point2.y = point3.y;
				conn.getPoints().removeAllPoints();
				conn.getPoints().addPoint(point0);
				conn.getPoints().addPoint(point1);
				conn.getPoints().addPoint(insertPoint);
				conn.getPoints().addPoint(point2);
				conn.getPoints().addPoint(point3);
			}


		}

	}
	protected Point getEndPoint(Connection connection) {
//		Point ref = connection.getSourceAnchor().getReferencePoint();
//		return END.setLocation(connection.getTargetAnchor().getLocation(ref));
		
		//PKY 08101506 S
		Point point = new Point();
		Point ref = connection.getSourceAnchor().getReferencePoint();
		point.setLocation(connection.getTargetAnchor().getLocation(ref));







		point.x = connection.getTargetAnchor().getReferencePoint().x;
		return point;
		//PKY 08101506 E


	}
	protected Point getStartPoint(Connection conn) {
		//PKY 08101506 S
		ConnectionAnchor anchor = conn.getSourceAnchor();

		Point point = new Point();
		Point ref = conn.getTargetAnchor().getReferencePoint();
		point.setLocation(conn.getSourceAnchor().getLocation(ref));

		point.x = conn.getSourceAnchor().getReferencePoint().x;
		return point;
		//PKY 08101506 E

	}

}
