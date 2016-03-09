package org.pky.uml.descriptor;

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
import org.pky.uml.commons.managers.LayoutManager;
import org.pky.uml.dialog.AutoMoveDialog;

public class AutoMovePropertyCellEditor extends DialogCellEditor {
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

	private String[] filter = null; 

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
	public AutoMovePropertyCellEditor(Composite parent) {
		super(parent, SWT.NONE);
	}

	/**
	 * Creates a new color cell editor parented under the given control.
	 * The cell editor value is black (<code>RGB(0,0,0)</code>) initially, and has no validator.
	 * @param parent the parent control
	 * @param style the style bits
	 * @since 2.1
	 */
	public AutoMovePropertyCellEditor(Composite parent, int style) {
		super(parent, style);
//		doSetValue("");
	}
	public AutoMovePropertyCellEditor(Composite parent,String[] filter) {
		super(parent, SWT.NONE);
		this.filter = filter;
//		doSetValue("");
	}
	/**
	 * Creates and returns the color image data for the given control
	 * and RGB value. The image's size is either the control's item extent or the cell editor's default extent,
	 * which is 16 pixels square.
	 * @param w the control
	 * @param color the color
	 */
	//    private ImageData createColorImage(Control w, RGB color) {
	//
	//        GC gc = new GC(w);
	//        FontMetrics fm = gc.getFontMetrics();
	//        int size = fm.getAscent();
	//        gc.dispose();
	//
	//        int indent = 6;
	//        int extent = DEFAULT_EXTENT;
	//        if (w instanceof Table) {
	//			extent = ((Table) w).getItemHeight() - 1;
	//		} else if (w instanceof Tree) {
	//			extent = ((Tree) w).getItemHeight() - 1;
	//		} else if (w instanceof TableTree) {
	//			extent = ((TableTree) w).getItemHeight() - 1;
	//		}
	//
	//        if (size > extent) {
	//			size = extent;
	//		}
	//
	//        int width = indent + size;
	//        int height = extent;
	//
	//        int xoffset = indent;
	//        int yoffset = (height - size) / 2;
	//
	//        RGB black = new RGB(0, 0, 0);
	//        PaletteData dataPalette = new PaletteData(new RGB[] { black, black,
	//                color });
	//        ImageData data = new ImageData(width, height, 4, dataPalette);
	//        data.transparentPixel = 0;
	//
	//        int end = size - 1;
	//        for (int y = 0; y < size; y++) {
	//            for (int x = 0; x < size; x++) {
	//                if (x == 0 || y == 0 || x == end || y == end) {
	//					data.setPixel(x + xoffset, y + yoffset, 1);
	//				} else {
	//					data.setPixel(x + xoffset, y + yoffset, 2);
	//				}
	//            }
	//        }
	//
	//        return data;
	//    }

	/* (non-Javadoc)
	 * Method declared on DialogCellEditor.
	 */

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
	@Override
	protected void fireApplyEditorValue() {
		// TODO Auto-generated method stub
//		super.fireApplyEditorValue();
	}

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

		AutoMoveDialog dialog = new AutoMoveDialog(cellEditorWindow.getShell());

		dialog.open();
		
		return dialog.getActionItem();
	}

	/* (non-Javadoc)
	 * Method declared on DialogCellEditor.
	 */

	protected void updateContents(Object value) {
		Object obj = value;
		//       if(obj!=null)
		//        rgbLabel
		//                .setText((String)value);//$NON-NLS-4$//$NON-NLS-3$//$NON-NLS-2$//$NON-NLS-1$
	}
}