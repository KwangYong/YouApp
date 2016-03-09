package org.pky.uml.figures.common;

import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.PositionConstants;
import org.eclipse.draw2d.StackLayout;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.text.TextFlow;

public class UMLElementFigure extends UMLBaseElementFigure {
	/** The inner TextFlow * */
	private TextFlow textFlow;
	//	private Text textField;
	Label tt = new Label();

	/**
	 *  Creates a new StickyNoteFigure with a default MarginBorder size of DEFAULT_CORNER_SIZE
	 * - 3 and a FlowPage containing a TextFlow with the style WORD_WRAP_SOFT.
	 */
	public UMLElementFigure() {
		this(UMLBentCornerFigure.DEFAULT_CORNER_SIZE - 3);
	}

	public UMLElementFigure(String text, int align) {
		this(UMLBentCornerFigure.DEFAULT_CORNER_SIZE - 3, text, align);
	}

	/**
	 * Creates a new StickyNoteFigure with a MarginBorder that is the given size and a
	 * FlowPage containing a TextFlow with the style WORD_WRAP_SOFT.
	 * @param borderSize the size of the MarginBorder
	 */
	public UMLElementFigure(int borderSize) {
		tt.setLabelAlignment(PositionConstants.CENTER);
		this.setPreferredSize(null);
		this.add(tt);
		setLayoutManager(new StackLayout());
	}

	public UMLElementFigure(int borderSize, String text, int align) {
		tt.setText(text);
		tt.setLabelAlignment(align);
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

	public Dimension getPreferredSize(int w, int h) {
		//		super.setPreferredSize(null);
		Dimension prefSize = super.getPreferredSize(w, h);
		Dimension defaultSize = new Dimension(w, 15);
		prefSize.union(defaultSize);
		return prefSize;
	}
}
