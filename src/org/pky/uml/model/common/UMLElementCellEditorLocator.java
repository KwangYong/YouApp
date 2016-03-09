package org.pky.uml.model.common;

import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.tools.CellEditorLocator;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.pky.uml.figures.ElementFigure;
import org.pky.uml.figures.TextViewFigure;

public class UMLElementCellEditorLocator implements CellEditorLocator {
	private TextViewFigure stickyNote;

	public UMLElementCellEditorLocator(TextViewFigure stickyNote) {
		setLabel(stickyNote);
	}

	public void relocate(CellEditor celleditor) {
		Rectangle rect = stickyNote.getClientArea();
		if(celleditor.getControl() instanceof Text){
			Text text = (Text)celleditor.getControl();

			stickyNote.translateToAbsolute(rect);
			org.eclipse.swt.graphics.Rectangle trim = text.computeTrim(0, 0, 0, 0);
			rect.translate(trim.x, trim.y);
			rect.width += trim.width;
			rect.height += trim.height;

			text.setBounds(rect.x, rect.y, rect.width, rect.height);
			System.out.println(rect.x-5+","+rect.y+","+rect.width+10+","+ rect.height+10);
		}else{
			Composite obj= (Composite)celleditor.getControl();
			stickyNote.translateToAbsolute(rect);
			org.eclipse.swt.graphics.Rectangle trim = obj.computeTrim(0, 0, 0, 0);
			rect.translate(trim.x, trim.y);
			rect.width += trim.width;
			rect.height += trim.height;

			obj.setBounds(rect.x-5, rect.y, rect.width+10, rect.height+10);
			System.out.println(rect.x-5+","+rect.y+","+rect.width+10+","+ rect.height+10);
		}

	}

	/** Returns the stickyNote figure. */
	protected TextViewFigure getLabel() {
		return stickyNote;
	}

	/**
	 * Sets the Sticky note figure.
	 * @param stickyNote The stickyNote to set
	 */
	protected void setLabel(TextViewFigure stickyNote) {
		this.stickyNote = stickyNote;
	}
}