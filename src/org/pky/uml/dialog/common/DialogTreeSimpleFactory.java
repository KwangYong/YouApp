package org.pky.uml.dialog.common;

import org.eclipse.gef.requests.SimpleFactory;
import org.pky.uml.model.command.TreeParent;

public class DialogTreeSimpleFactory extends SimpleFactory {

	private TreeParent treeParent = null; 
	public DialogTreeSimpleFactory(Class aClass) {
		super(aClass);
		// TODO Auto-generated constructor stub
	}
	public TreeParent getTreeParent() {
		return treeParent;
	}
	public void setTreeParent(TreeParent treeParent) {
		this.treeParent = treeParent;
	}

}
