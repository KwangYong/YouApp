package org.pky.uml.editor;

import org.eclipse.core.runtime.IPath;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IPathEditorInput;
import org.eclipse.ui.IPersistableElement;

public class LogicEditorInput implements IPathEditorInput {
	
	private IPath path;
	
	public LogicEditorInput(IPath path) {
		this.path = path;
	}
	
	public boolean exists() {
		return path.toFile().exists();
	}
	
	public ImageDescriptor getImageDescriptor() {
		return null;
	}
	
	public String getName() {
		return path.toString();
	}
	
	public IPersistableElement getPersistable() {
		return null;
	}
	
	public String getToolTipText() {
		return path.toString();
	}
	
	public Object getAdapter(Class adapter) {
		return null;
	}
	
	public int hashCode() {
		return path.hashCode();
	}
	
	
	
	public IPath getPath() {
		return path;
	}
	
}