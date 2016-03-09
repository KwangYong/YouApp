package org.pky.uml.descriptor;

import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.DialogCellEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Layout;
import org.eclipse.ui.views.properties.PropertyDescriptor;
import org.pky.uml.dialog.ListViewItemDialog;

public class ListViewItemPropertyDescriptor extends PropertyDescriptor {
	 /* Creates an property descriptor with the given id and display name.
    *
    * @param id the id of the property
    * @param displayName the name to display for the property
    */

   public ListViewItemPropertyDescriptor(Object id, String displayName) {
       super(id, displayName);
   }

   /**
    * The <code>ColorPropertyDescriptor</code> implementation of this <code>IPropertyDescriptor</code> method creates and
    * returns a new <code>ColorCellEditor</code>. <p>
    * The editor is configured with the current validator if there is one. </p>
    */
   public CellEditor createPropertyEditor(Composite parent) {
       CellEditor editor = new ListViewItemCellEditor(parent);
       if (getValidator() != null) {
           editor.setValidator(getValidator());
       }
       return editor;
   }
}

class ListViewItemCellEditor extends DialogCellEditor {
    /** The default extent in pixels. */
    private static final int DEFAULT_EXTENT = 16;

    /** Gap between between image and text in pixels. */
    private static final int GAP = 6;

    /** The composite widget containing the color and RGB label widgets */
    private Composite composite;

    /** The label widget showing the current color. */
    private Label colorLabel;

    /** The label widget showing the RGB values. */
    private Label rgbLabel;

    /** The image. */
    private Image image;
    private String text = "";

    /** Internal class for laying out this cell editor. */
    private class ColorCellLayout extends Layout {
        public Point computeSize(Composite editor, int wHint, int hHint, boolean force) {
            if (wHint != SWT.DEFAULT && hHint != SWT.DEFAULT) {
                return new Point(wHint, hHint);
            }
            //            Point colorSize = colorLabel.computeSize(SWT.DEFAULT, SWT.DEFAULT,
            //                    force);
            Point rgbSize = rgbLabel.computeSize(SWT.DEFAULT, SWT.DEFAULT, force);
            return new Point(rgbSize.x, rgbSize.y);
        }

        public void layout(Composite editor, boolean force) {
            Rectangle bounds = editor.getClientArea();
            //            Point colorSize = colorLabel.computeSize(SWT.DEFAULT, SWT.DEFAULT,
            //                    force);
            Point rgbSize = rgbLabel.computeSize(SWT.DEFAULT, SWT.DEFAULT, force);
            int ty = (bounds.height - rgbSize.y) / 2;
            if (ty < 0) {
                ty = 0;
            }
            //            colorLabel.setBounds(-1, 0, colorSize.x, colorSize.y);
            rgbLabel.setBounds(bounds);
        }
    }


    /**
     * Creates a new color cell editor parented under the given control.
     * The cell editor value is black (<code>RGB(0,0,0)</code>) initially, and has no validator.
     * @param parent the parent control
     */
    public ListViewItemCellEditor(Composite parent) {
        super(parent, SWT.NONE);
    }

    /**
     * Creates a new color cell editor parented under the given control.
     * The cell editor value is black (<code>RGB(0,0,0)</code>) initially, and has no validator.
     * @param parent the parent control
     * @param style the style bits
     * @since 2.1
     */
    public ListViewItemCellEditor(Composite parent, int style) {
        super(parent, style);
        doSetValue("");
    }


    protected Control createContents(Composite cell) {
        try {
            Color bg = cell.getBackground();
            composite = new Composite(cell, getStyle());
            composite.setBackground(bg);
            composite.setLayout(new ColorCellLayout());
            rgbLabel = new Label(composite, SWT.LEFT);
            rgbLabel.setBackground(bg);
            rgbLabel.setFont(cell.getFont());
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return composite;
    }

    /* (non-Javadoc)
     * Method declared on CellEditor.
     */

    public void dispose() {
        if (image != null) {
            image.dispose();
            image = null;
        }
        super.dispose();
    }

    /* (non-Javadoc)
     * Method declared on DialogCellEditor.
     */

    protected Object openDialogBox(Control cellEditorWindow) {



    	ListViewItemDialog dialog = new ListViewItemDialog(cellEditorWindow.getShell());
    	Object value = getValue();

    	int i = dialog.open();
    	
    	return null;



    }

    protected void updateContents(Object value) {
        Object obj = value;

    }
}
