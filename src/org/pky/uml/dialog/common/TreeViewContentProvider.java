package org.pky.uml.dialog.common;

import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;
import org.pky.uml.model.command.TreeObject;
import org.pky.uml.model.command.TreeParent;

public class TreeViewContentProvider  implements IStructuredContentProvider, ITreeContentProvider {
	//		private TreeParent invisibleRoot;
	public void inputChanged(Viewer v, Object oldInput, Object newInput) {
	}

	public void dispose() {
	}

	public Object[] getElements(Object parent) {
		//			if (invisibleRoot==null){ initialize();
		//			return getChildren(invisibleRoot);
		//			}
		return getChildren(parent);
	}

	public Object getParent(Object child) {
		if (child instanceof TreeObject) {
			return ((TreeObject)child).getParent();
		}
		return null;
	}

	public Object[] getChildren(Object parent) {
		if (parent instanceof TreeParent) {
			return ((TreeParent)parent).getChildren();
		}
		return new Object[0];
	}

	public boolean hasChildren(Object parent) {
		if (parent instanceof TreeParent)
			return ((TreeParent)parent).hasChildren();
		return false;
	}
}