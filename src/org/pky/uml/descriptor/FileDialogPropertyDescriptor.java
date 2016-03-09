package org.pky.uml.descriptor;

import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.views.properties.PropertyDescriptor;

public class FileDialogPropertyDescriptor extends PropertyDescriptor {
	/* Creates an property descriptor with the given id and display name.
	 *
	 * @param id the id of the property
	 * @param displayName the name to display for the property
	 */

	String[]filter = null;
	public FileDialogPropertyDescriptor(Object id, String displayName,String[] filter) {
		super(id, displayName);
		this.filter = filter;
	}

	/**
	 * The <code>ColorPropertyDescriptor</code> implementation of this <code>IPropertyDescriptor</code> method creates and
	 * returns a new <code>ColorCellEditor</code>. <p>
	 * The editor is configured with the current validator if there is one. </p>
	 */
	public CellEditor createPropertyEditor(Composite parent) {
		CellEditor editor = new FileDialogPropertyCellEditor(parent,filter);
		if (getValidator() != null) {
			editor.setValidator(getValidator());
		}
		return editor;
	}
}
