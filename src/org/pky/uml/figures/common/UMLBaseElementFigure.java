package org.pky.uml.figures.common;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.Graphics;

public class UMLBaseElementFigure extends Figure {
	/** The default amount of pixels subtracted from the figure's height and width to determine the size of the corner. */
	protected static int DEFAULT_CORNER_SIZE = 10;
	private int cornerSize;

	/**
	 * Constructs an empty BentCornerFigure with default background color of ColorConstants.tooltipBackground
	 * and default corner size.
	 */
	public UMLBaseElementFigure() {
		setBackgroundColor(ColorConstants.tooltipBackground);
		setForegroundColor(ColorConstants.tooltipForeground);
		setCornerSize(DEFAULT_CORNER_SIZE);
	}

	/**
	 * Returns the size, in pixels, that the figure should use to draw its bent corner.
	 * @return size of the corner
	 */
	public int getCornerSize() {
		return cornerSize;
	}

	/** @see org.eclipse.draw2d.Figure#paintFigure(org.eclipse.draw2d.Graphics) */
	protected void paintFigure(Graphics graphics) {
//		super.paintFigure(graphics);
	}

	/**
	 * Sets the size of the figure's corner to the given offset.
	 * @param newSize the new size to use.
	 */
	public void setCornerSize(int newSize) {
		cornerSize = newSize;
	}
}
