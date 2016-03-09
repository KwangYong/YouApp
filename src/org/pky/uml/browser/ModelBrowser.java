package org.pky.uml.browser;

import java.util.Arrays;

import org.eclipse.gef.dnd.TemplateTransfer;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerDropAdapter;
import org.eclipse.jface.viewers.ViewerSorter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.ui.part.DrillDownAdapter;
import org.eclipse.ui.part.ViewPart;
import org.pky.uml.browser.common.model.ModelBrowerViewerDropAdapter;
import org.pky.uml.browser.common.model.ModelBrowserDragListener;
import org.pky.uml.browser.common.model.ModelViewContentProvider;
import org.pky.uml.browser.model.model.ModelTreeModel;
import org.pky.uml.commons.managers.ModelBrowserManager;
import org.pky.uml.commons.managers.ProjectManager;
import org.pky.uml.dialog.NewDiagramDialog;
import org.pky.uml.dialog.NewPackageDialog;
import org.pky.uml.model.UMLDiagramModel;

public class ModelBrowser extends ViewPart{


	private TreeViewer viewer;

	private ModelViewContentProvider modelProvider = null;
	public static String ID = "org.pky.uml.ModelBrowser";

	private ModelTreeModel root;
	
	private DrillDownAdapter drillDownAdapter;
	
	private Action add_package = null;
	private Action add_diagram = null;
	private Action delete_model = null;

	public ModelBrowser(){
		ModelBrowserManager.getInstance().setModelBrowser(this);
		
	}

	public void init(){
		modelProvider = new ModelViewContentProvider();
		modelProvider.setViewSite(this.getViewSite());
		modelProvider.setRoot(root);
		viewer.setContentProvider(modelProvider);
		root = modelProvider.getRoot();		
		
	}
	public void createPartControl(Composite parent) {
		
		viewer = new TreeViewer(parent, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL | SWT.SELECTED);
		viewer.setSorter(new NameSorter());
		this.getSite().setSelectionProvider(viewer);
		Transfer[] types = new Transfer[] {
				TemplateTransfer.getInstance()
		};
		
		ViewerDropAdapter testdropper;
		viewer.addDragSupport(DND.DROP_COPY | DND.DROP_MOVE, types,	new ModelBrowserDragListener(this));
		viewer.addDropSupport(DND.DROP_COPY | DND.DROP_MOVE, types,	new ModelBrowerViewerDropAdapter(viewer));
		drillDownAdapter = new DrillDownAdapter(viewer);
		modelProvider = new ModelViewContentProvider();
		modelProvider.setViewSite(this.getViewSite());
		modelProvider.setRoot(root);
		viewer.setContentProvider(modelProvider);
		this.getSite().setSelectionProvider(viewer);

		CellEditor editors[] = new CellEditor[1];
		
		editors[0] = new TextCellEditor(viewer.getTree(), SWT.NONE);
		viewer.setCellEditors(editors);
		
		viewer.setInput(getViewSite());
		
		
		//메뉴 
		MenuManager menuMgr = new MenuManager("#PopupMenu");
		menuMgr.setRemoveAllWhenShown(true);
		menuMgr.addMenuListener(
				new IMenuListener() {
					public void menuAboutToShow(IMenuManager manager) {
						ModelBrowser.this.menuinit(manager);
					}
				});
		Menu menu = menuMgr.createContextMenu(viewer.getControl());
		viewer.getControl().setMenu(menu);
		getSite().registerContextMenu(menuMgr, viewer);
		viewer.addDoubleClickListener(new IDoubleClickListener() {
			
			@Override
			public void doubleClick(DoubleClickEvent event) {
				ModelBrowserManager.getInstance().open(getTreeModelSelected());
				
			}
		});
		add_package = new Action() {
			public void run() {
				
				
				NewPackageDialog dialog = new NewPackageDialog(ProjectManager.getInstance().window.getShell(),getTreeModelSelected());
				dialog.open();
				
				
				
			}
		};
		add_package.setText("패키지 추가");
		
		add_diagram = new Action(){
			public void run(){
				NewDiagramDialog diagramDialog = new NewDiagramDialog(ProjectManager.getInstance().window.getShell(),getTreeModelSelected());
				diagramDialog.open();
			}
		};
		add_diagram.setText("스토리보드 추가");
		
		delete_model = new Action() {
			public void run(){
				ProjectManager.getInstance().removeUMLNode(getTreeModelSelected().getParentTreeModel(), getTreeModelSelected());
			}
		};
		delete_model.setText("모델 삭제");
	}
	public ModelTreeModel getTreeModelSelected() {
		TreeSelection iSelection = (TreeSelection)viewer.getSelection();
		ModelTreeModel treeObject = (ModelTreeModel)iSelection.getFirstElement();
		return treeObject;
	}

	public void expend(ModelTreeModel p) {
		viewer.setExpandedState(p, true);
	}

	@Override
	public void setFocus() {


	}
	public TreeViewer getViewer(){
		return this.viewer;
	}
	private void menuinit(IMenuManager manager) {
		if(getTreeModelSelected().getBasicModel()==null){
			manager.add(add_package);
		}else if(getTreeModelSelected().getBasicModel()!=null && getTreeModelSelected().getBasicModel() instanceof UMLDiagramModel){
			
		}else{
			manager.add(add_package);
			manager.add(add_diagram);
			manager.add(delete_model);	
		}
		
	}

	public ModelTreeModel getRoot() {
		return root;
	}

	public void setRoot(ModelTreeModel root) {
		this.root = root;
	}
	
	
	
	
	
	
	

	

}
class NameSorter extends ViewerSorter {
	// PKY 08090201 S ModelBrowser Sort
	public int compare(Viewer viewer, Object obj1, Object obj2) {
		int i = 0;
		int index1 = 0;
		int index2 = 0;
		if (obj1 instanceof ModelTreeModel && obj2 instanceof ModelTreeModel) {
			ModelTreeModel model1 = (ModelTreeModel) obj1;
			ModelTreeModel model2 = (ModelTreeModel) obj2;

			if (model1.getBasicModel() != null
					&& model2.getBasicModel() != null) {
				index1 = ProjectManager.getInstance().getModelType(model1.getBasicModel());
				index2 = ProjectManager.getInstance().getModelType(model2.getBasicModel());
				

				if (index2 >= index1)
					i = -1;
				else
					i = 1;

				if (index2 == index1) {

					String name = model1.getName().toLowerCase() + ","
							+ model2.getName().toLowerCase();

					String[] items = name.split(",");
					Arrays.sort(items);
					if (items.length == 2) {
						String umlName = model2.getName().toLowerCase();
						if (umlName.equals(items[0]))
							i = 1;
						else
							i = -1;
					}
				}

			}

		}
		return i;
	}
}
