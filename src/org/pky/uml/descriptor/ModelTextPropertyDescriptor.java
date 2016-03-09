package org.pky.uml.descriptor;

import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.views.properties.PropertyDescriptor;


public class ModelTextPropertyDescriptor extends PropertyDescriptor {
	 /* Creates an property descriptor with the given id and display name.
    *
    * @param id the id of the property
    * @param displayName the name to display for the property
    */

   public ModelTextPropertyDescriptor(Object id, String displayName) {
       super(id, displayName);
   }

   /**
    * The <code>ColorPropertyDescriptor</code> implementation of this <code>IPropertyDescriptor</code> method creates and
    * returns a new <code>ColorCellEditor</code>. <p>
    * The editor is configured with the current validator if there is one. </p>
    */
   public CellEditor createPropertyEditor(Composite parent) {
       CellEditor editor = new ModelTextCellEditor(parent);
       if (getValidator() != null) {
           editor.setValidator(getValidator());
       }
       return editor;
   }
}
