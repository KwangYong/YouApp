package org.pky.uml.browser.common.model;

import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.ui.IViewSite;
import org.pky.uml.browser.model.model.ModelTreeModel;
import org.pky.uml.commons.managers.ModelBrowserManager;

public class ModelViewContentProvider implements IStructuredContentProvider, ITreeContentProvider {
	private ModelTreeModel invisibleRoot;
	IViewSite viewSite = null;
	private ModelTreeModel root = null;

	public void inputChanged(Viewer v, Object oldInput, Object newInput) {
	}

	public void dispose() {
	}

	public Object[] getElements(Object parent) {
		if (parent.equals(viewSite)) {
			if (invisibleRoot == null) initialize();
			return getChildren(invisibleRoot);
		}
		return getChildren(parent);
	}

	public Object getParent(Object child) {
		if (child instanceof ModelTreeModel) {
			return ((ModelTreeModel)child).getParentTreeModel();
		}
		return null;
	}

	public Object[] getChildren(Object parent) {
		if (parent instanceof ModelTreeModel) {
			return ((ModelTreeModel)parent).getChildren();
		}
		return new Object[0];
	}

	public boolean hasChildren(Object parent) {
		if (parent instanceof ModelTreeModel)
			return ((ModelTreeModel)parent).hasChildren();
		return false;
	}

	/*
	 * We will set up a dummy model to initialize tree heararchy.
	 * In a real code, you will connect to a real model and
	 * expose its hierarchy.
	 */

	public void initialize() {
		root = new ModelTreeModel();
		invisibleRoot = new ModelTreeModel("");
		invisibleRoot.addChild(root);
		ModelBrowserManager.getInstance().getModelBrowser().setRoot(root);
		
		root.setName("Model");
	}

	public IViewSite getViewSite() {
		return viewSite;
	}

	public void setViewSite(IViewSite viewSite) {
		this.viewSite = viewSite;
	}

	public ModelTreeModel getRoot() {
		return root;
	}

	public void setRoot(ModelTreeModel root) {
		this.root = root;
	}
}
