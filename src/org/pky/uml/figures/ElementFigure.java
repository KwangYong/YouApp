package org.pky.uml.figures;

import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.PositionConstants;
import org.eclipse.draw2d.StackLayout;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.draw2d.text.TextFlow;
import org.eclipse.swt.SWT;
import org.pky.uml.figures.common.UMLFigure;

public class ElementFigure extends UMLFigure{
	protected static int DEFAULT_CORNER_SIZE = 8;
	/** The inner TextFlow * */
	private TextFlow textFlow;
	//	private Text textField;
	Label tt = new Label();

	/**
	 *  Creates a new StickyNoteFigure with a default MarginBorder size of DEFAULT_CORNER_SIZE
	 * - 3 and a FlowPage containing a TextFlow with the style WORD_WRAP_SOFT.
	 */
	public ElementFigure() {
		this(DEFAULT_CORNER_SIZE - 3);
	}

	public ElementFigure(String text, int align) {
		this(DEFAULT_CORNER_SIZE - 3, text, align);
	}

	/**
	 * Creates a new StickyNoteFigure with a MarginBorder that is the given size and a
	 * FlowPage containing a TextFlow with the style WORD_WRAP_SOFT.
	 * @param borderSize the size of the MarginBorder
	 */
	public ElementFigure(int borderSize) {
		tt.setLabelAlignment(PositionConstants.CENTER);
		this.setPreferredSize(null);
		this.add(tt);
		setLayoutManager(new StackLayout());
	}

	public ElementFigure(int borderSize, String text, int align) {
		tt.setText(text);
		tt.setLabelAlignment(SWT.CENTER);
		this.setPreferredSize(null);
		this.add(tt);
		setLayoutManager(new StackLayout());
	}

	/**
	 * Returns the text inside the TextFlow.
	 * @return the text flow inside the text.
	 */
	public String getText() {
		return tt.getText();
	}

	/**
	 * Sets the text of the TextFlow to the given value.
	 * @param newText the new text value.
	 */
	public void setText(String newText) {
		tt.setText(newText);
	}
	public void setAlign(int align){
		tt.setLabelAlignment(align);
	}

	public void setSize(int w,int h){
		tt.setLabelAlignment(SWT.CENTER);
		this.setPreferredSize(null);
		super.setSize(w, h);
	}
	public Dimension getPreferredSize(int w, int h) {
		//		super.setPreferredSize(null);
		Dimension prefSize = super.getPreferredSize(w, h);
		Dimension defaultSize = new Dimension(w, 15);
		prefSize.union(defaultSize);
		return prefSize;
	}
	@Override
	public void paintFigure(Graphics graphics) {
		// TODO Auto-generated method stub
		Rectangle r = Rectangle.SINGLETON;
		r.setBounds(getBounds());
		
		super.paintFigure(graphics);
	}
}
