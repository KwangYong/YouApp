package org.pky.uml.figures;

import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.PointList;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.widgets.Display;
import org.pky.uml.figures.common.UMLFigure;

public class EditTextFigure extends UMLFigure{
	private int type = 0;

	private String text = "";

	protected void paintFigure(Graphics graphics) {

		
		Rectangle r = Rectangle.SINGLETON;
		r.setBounds(getBounds());

		if(type==0){ // None
			PointList pointList = new PointList();
			pointList.addPoint(r.x+1, r.y+r.height-(r.height/3));
			pointList.addPoint(r.x+1, r.y+(r.height)-1);
			pointList.addPoint(r.x+r.width-1, r.y+(r.height)-1);
			pointList.addPoint(r.x+r.width-1, r.y+r.height-(r.height/3));
			graphics.setLineWidth(2);
			graphics.drawPolyline(pointList);

			
		}else if(type ==1){// "textPersonName"
			PointList pointList = new PointList();
			pointList.addPoint(r.x+1, r.y+r.height-(r.height/3));
			pointList.addPoint(r.x+1, r.y+(r.height)-1);
			pointList.addPoint(r.x+r.width-1, r.y+(r.height)-1);
			pointList.addPoint(r.x+r.width-1, r.y+r.height-(r.height/3));
			graphics.setLineWidth(2);
			graphics.drawPolyline(pointList);

			
		}else if(type ==2){// "textPassword"
			PointList pointList = new PointList();
			pointList.addPoint(r.x+1, r.y+r.height-(r.height/3));
			pointList.addPoint(r.x+1, r.y+(r.height)-1);
			pointList.addPoint(r.x+r.width-1, r.y+(r.height)-1);
			pointList.addPoint(r.x+r.width-1, r.y+r.height-(r.height/3));
			graphics.setLineWidth(2);
			graphics.drawPolyline(pointList);

			
		}else if(type ==3){//"numberPassword"
			PointList pointList = new PointList();
			pointList.addPoint(r.x+1, r.y+r.height-(r.height/3));
			pointList.addPoint(r.x+1, r.y+(r.height)-1);
			pointList.addPoint(r.x+r.width-1, r.y+(r.height)-1);
			pointList.addPoint(r.x+r.width-1, r.y+r.height-(r.height/3));
			graphics.setLineWidth(2);
			graphics.drawPolyline(pointList);

			
		}else if(type ==4){//"textEmailAddress"
			PointList pointList = new PointList();
			pointList.addPoint(r.x+1, r.y+r.height-(r.height/3));
			pointList.addPoint(r.x+1, r.y+(r.height)-1);
			pointList.addPoint(r.x+r.width-1, r.y+(r.height)-1);
			pointList.addPoint(r.x+r.width-1, r.y+r.height-(r.height/3));
			graphics.setLineWidth(2);
			graphics.drawPolyline(pointList);

			
		}else if(type ==5){//,"phone"
			PointList pointList = new PointList();
			pointList.addPoint(r.x+1, r.y+r.height-(r.height/3));
			pointList.addPoint(r.x+1, r.y+(r.height)-1);
			pointList.addPoint(r.x+r.width-1, r.y+(r.height)-1);
			pointList.addPoint(r.x+r.width-1, r.y+r.height-(r.height/3));
			graphics.setLineWidth(2);
			graphics.drawPolyline(pointList);

			
		}else if(type ==6){//,"textPostalAddress"
			PointList pointList = new PointList();
			pointList.addPoint(r.x+1, r.y+r.height-(r.height/3));
			pointList.addPoint(r.x+1, r.y+(r.height)-1);
			pointList.addPoint(r.x+r.width-1, r.y+(r.height)-1);
			pointList.addPoint(r.x+r.width-1, r.y+r.height-(r.height/3));
			graphics.setLineWidth(2);
			graphics.drawPolyline(pointList);

			
		}else if(type ==7){//,"textMultiLine"
			PointList pointList = new PointList();
			pointList.addPoint(r.x+1, r.y+r.height-(r.height/3));
			pointList.addPoint(r.x+1, r.y+(r.height)-1);
			pointList.addPoint(r.x+r.width-1, r.y+(r.height)-1);
			pointList.addPoint(r.x+r.width-1, r.y+r.height-(r.height/3));
			graphics.setLineWidth(2);
			graphics.drawPolyline(pointList);

			
		}else if(type ==8){//,"time"
			PointList pointList = new PointList();
			pointList.addPoint(r.x+1, r.y+r.height-(r.height/3));
			pointList.addPoint(r.x+1, r.y+(r.height)-1);
			pointList.addPoint(r.x+r.width-1, r.y+(r.height)-1);
			pointList.addPoint(r.x+r.width-1, r.y+r.height-(r.height/3));
			graphics.setLineWidth(2);
			graphics.drawPolyline(pointList);

			
		}else if(type ==9){//,"date"
			PointList pointList = new PointList();
			pointList.addPoint(r.x+1, r.y+r.height-(r.height/3));
			pointList.addPoint(r.x+1, r.y+(r.height)-1);
			pointList.addPoint(r.x+r.width-1, r.y+(r.height)-1);
			pointList.addPoint(r.x+r.width-1, r.y+r.height-(r.height/3));
			graphics.setLineWidth(2);
			graphics.drawPolyline(pointList);

			
		}else if(type ==10){//,"number"
			PointList pointList = new PointList();
			pointList.addPoint(r.x+1, r.y+r.height-(r.height/3));
			pointList.addPoint(r.x+1, r.y+(r.height)-1);
			pointList.addPoint(r.x+r.width-1, r.y+(r.height)-1);
			pointList.addPoint(r.x+r.width-1, r.y+r.height-(r.height/3));
			graphics.setLineWidth(2);
			graphics.drawPolyline(pointList);

			
		}else if(type ==11){//"numberSigned"
			PointList pointList = new PointList();
			pointList.addPoint(r.x+1, r.y+r.height-(r.height/3));
			pointList.addPoint(r.x+1, r.y+(r.height)-1);
			pointList.addPoint(r.x+r.width-1, r.y+(r.height)-1);
			pointList.addPoint(r.x+r.width-1, r.y+r.height-(r.height/3));
			graphics.setLineWidth(2);
			graphics.drawPolyline(pointList);

			

		}else if(type ==12){//"numberDecimal"
			PointList pointList = new PointList();
			pointList.addPoint(r.x+1, r.y+r.height-(r.height/3));
			pointList.addPoint(r.x+1, r.y+(r.height)-1);
			pointList.addPoint(r.x+r.width-1, r.y+(r.height)-1);
			pointList.addPoint(r.x+r.width-1, r.y+r.height-(r.height/3));
			graphics.setLineWidth(2);
			graphics.drawPolyline(pointList);

			
		}else if(type ==13){//"AutoCompleteTextView"
			PointList pointList = new PointList();
			pointList.addPoint(r.x+1, r.y+r.height-(r.height/3));
			pointList.addPoint(r.x+1, r.y+(r.height)-1);
			pointList.addPoint(r.x+r.width-1, r.y+(r.height)-1);
			pointList.addPoint(r.x+r.width-1, r.y+r.height-(r.height/3));
			graphics.setLineWidth(2);
			graphics.drawPolyline(pointList);

			
		}else if(type ==14){//"MultiAutoCompleteTextView"
			PointList pointList = new PointList();
			pointList.addPoint(r.x+1, r.y+r.height-(r.height/3));
			pointList.addPoint(r.x+1, r.y+(r.height)-1);
			pointList.addPoint(r.x+r.width-1, r.y+(r.height)-1);
			pointList.addPoint(r.x+r.width-1, r.y+r.height-(r.height/3));
			graphics.setLineWidth(2);
			graphics.drawPolyline(pointList);

			
		}
		if(text.equals("")){
			PointList pointList = new PointList();
			pointList.addPoint(r.x+(r.width/20), r.y+(r.height/2));
			pointList.addPoint(r.x+(r.width/20), r.y+(r.height-3));
			//graphics.setLineWidth(1);
			graphics.drawPolyline(pointList);			
		}else{
			Font font = new Font( Display.getDefault(), "±¼¸²", 16,	SWT.NORMAL );
			graphics.setFont( font );
			graphics.drawString(text, new Point(r.x+10, r.y+r.height-25));
			
		}
		
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
}