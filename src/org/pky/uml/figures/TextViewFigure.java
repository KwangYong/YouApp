package org.pky.uml.figures;

import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontMetrics;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Display;
import org.pky.uml.figures.common.UMLFigure;

public class TextViewFigure extends UMLFigure{
	private String text = "";
	private RGB color = null;
	private int fontSize = 10;
	@Override
	protected void paintFigure(Graphics graphics) {
		// TODO Auto-generated method stub
		super.paintFigure(graphics);

		Rectangle r = Rectangle.SINGLETON;
		r.setBounds(getBounds());
		
		
		Font font = new Font( Display.getDefault(), "±¼¸²", fontSize,	SWT.NORMAL );
		graphics.setFont( font );
		
		FontMetrics fm = graphics.getFontMetrics();
		int charWidth = fm.getAverageCharWidth();
		
		int charHeight = fm.getHeight() / 2;
		int fontHeight = fm.getHeight();
		
		int width = (text.length() * charWidth) / 2;
		width = r.width/2 -width;
		graphics.setBackgroundColor(new Color(font.getDevice(), color));
		graphics.setForegroundColor(new Color(font.getDevice(), color));
		String[] textSplit = text.split("\n");
		for(int i = 0; i < textSplit.length; i++){
			graphics.drawString(textSplit[i],r.x ,r.y+i*fontHeight);	
		}
		
	
		

	
	}
	public String getText() {
		return text;
	}
	public int getFontSize() {
		return fontSize;
	}
	public void setText(String text) {
		this.text = text;
	}
	public void setFontSize(int fontSize) {
		this.fontSize = fontSize;
	}
	public RGB getColor() {
		return color;
	}
	public void setColor(RGB color) {
		this.color = color;
	}

}
