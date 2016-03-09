package org.pky.uml.model.command;

import org.eclipse.core.runtime.IAdaptable;
import org.pky.uml.commons.managers.ProjectManager;

public class TreeObject implements IAdaptable {
	private String name;
	private String path;
	private TreeParent parent;
	private String id = ProjectManager.getInstance().getNewID(this);
	
	public TreeObject(String name) {
		this.name = name;
	}

	public TreeObject(String name, String p) {
		this.name = name;
		this.path = p;
	}
	
	public String getName() {
		return name;
	}
	public String getPath(){
		return path;
	}



	public void setParent(TreeParent parent) {
		this.parent = parent;
	}

	public TreeParent getParent() {
		return parent;
	}

	public String toString() {
		return getName();
	}

	public Object getAdapter(Class key) {
		return null;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}