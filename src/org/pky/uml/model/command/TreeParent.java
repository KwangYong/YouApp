package org.pky.uml.model.command;

import java.util.ArrayList;

import org.pky.uml.commons.managers.ProjectManager;

public class TreeParent extends TreeObject {
	private ArrayList children;
	

	public TreeParent(String name) {
		super(name);
		children = new ArrayList();
	}

	public TreeParent(String name, String p) {
		super(name, p);
		children = new ArrayList();
	}

	public void addChild(TreeObject child) {
		if(child.getParent()!=null){
			child.getParent().children.remove(child);
		}
		children.add(child);
		child.setParent(this);
	}

	public void removeChild(TreeObject child) {
		children.remove(child);
		child.setParent(null);
	}

	public TreeObject[] getChildren() {
		return (TreeObject[]) children.toArray(new TreeObject[children.size()]);
	}

	public boolean hasChildren() {
		return children.size() > 0;
	}
}