package org.pky.uml.editor;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IPersistableElement;

public class UMLEditorInput implements IEditorInput {
	
	public String name;

	public UMLEditorInput(String name) {
		this.name = name;
	}

	@Override
	public boolean exists() {
		return (this.name != null);
	}

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof UMLEditorInput))
			return false;
		return ((UMLEditorInput) o).getName().equals(getName());
	}

	@Override
	public ImageDescriptor getImageDescriptor() {
		return ImageDescriptor.getMissingImageDescriptor();
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public IPersistableElement getPersistable() {
		return null;
	}

	@Override
	public String getToolTipText() {
		return name;
	}

	@Override
	public Object getAdapter(Class adapter) {
		return null;
	}

}
