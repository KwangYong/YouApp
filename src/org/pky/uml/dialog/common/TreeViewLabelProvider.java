package org.pky.uml.dialog.common;

import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;
import org.pky.uml.model.command.TreeParent;

public class TreeViewLabelProvider extends LabelProvider {
	public TreeViewLabelProvider(){

	}
	public String getText(Object obj) {
		return obj.toString();
	}

	public Image getImage(Object obj) {
		String imageKey = ISharedImages.IMG_OBJ_ELEMENT;
		if(obj instanceof TreeParent){
			TreeParent tp = (TreeParent)obj;
			return null;
		}

		return PlatformUI.getWorkbench().getSharedImages().getImage(imageKey);
	}
}