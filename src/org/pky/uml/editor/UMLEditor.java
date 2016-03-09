package org.pky.uml.editor;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.draw2d.PositionConstants;
import org.eclipse.gef.ContextMenuProvider;
import org.eclipse.gef.DefaultEditDomain;
import org.eclipse.gef.GraphicalViewer;
import org.eclipse.gef.SnapToGrid;
import org.eclipse.gef.dnd.TemplateTransferDragSourceListener;
import org.eclipse.gef.editparts.ScalableFreeformRootEditPart;
import org.eclipse.gef.editparts.ZoomManager;
import org.eclipse.gef.palette.CombinedTemplateCreationEntry;
import org.eclipse.gef.palette.MarqueeToolEntry;
import org.eclipse.gef.palette.PaletteContainer;
import org.eclipse.gef.palette.PaletteDrawer;
import org.eclipse.gef.palette.PaletteEntry;
import org.eclipse.gef.palette.PaletteGroup;
import org.eclipse.gef.palette.PaletteRoot;
import org.eclipse.gef.palette.PaletteStack;
import org.eclipse.gef.palette.PanningSelectionToolEntry;
import org.eclipse.gef.palette.ToolEntry;
import org.eclipse.gef.tools.MarqueeSelectionTool;
import org.eclipse.gef.ui.actions.ActionRegistry;
import org.eclipse.gef.ui.actions.AlignmentAction;
import org.eclipse.gef.ui.actions.CopyTemplateAction;
import org.eclipse.gef.ui.actions.GEFActionConstants;
import org.eclipse.gef.ui.actions.ToggleGridAction;
import org.eclipse.gef.ui.actions.ToggleRulerVisibilityAction;
import org.eclipse.gef.ui.actions.ToggleSnapToGeometryAction;
import org.eclipse.gef.ui.actions.ZoomInAction;
import org.eclipse.gef.ui.actions.ZoomOutAction;
import org.eclipse.gef.ui.palette.PaletteViewer;
import org.eclipse.gef.ui.palette.PaletteViewerProvider;
import org.eclipse.gef.ui.parts.GraphicalEditorWithFlyoutPalette;
import org.eclipse.gef.ui.parts.ScrollingGraphicalViewer;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.util.TransferDropTargetListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.actions.ActionFactory;
import org.pky.uml.browser.common.model.UMLTemplateTransferDropTargetListener;
import org.pky.uml.commons.managers.ModelDataManager;
import org.pky.uml.commons.managers.ProjectManager;
import org.pky.uml.commons.managers.UAppSelectionManager;
import org.pky.uml.editor.action.ModelDeleteAction;
import org.pky.uml.editor.action.ModelImageSizeChangeAction;
import org.pky.uml.editor.action.ModelImageWidthPercentAction;
import org.pky.uml.editparts.common.UMLEditPartFactory;
import org.pky.uml.image.ImagePoint;
import org.pky.uml.model.ButtonModel;
import org.pky.uml.model.EditTextModel;
import org.pky.uml.model.ImageViewModel;
import org.pky.uml.model.LayoutModel;
import org.pky.uml.model.ListViewModel;
import org.pky.uml.model.TextViewModel;
import org.pky.uml.model.UMLDiagramModel;
import org.pky.uml.model.VideoModel;
import org.pky.uml.model.WebViewModel;
import org.pky.uml.model.YoutubeModel;
import org.pky.uml.model.command.TreeSimpleFactory;
import org.pky.uml.model.command.YouAppKeyHandler;
import org.pky.uml.policy.PolicyCommand;
import org.pky.uml.rcp.action.UMLContextMenuProvider;

public class UMLEditor extends  GraphicalEditorWithFlyoutPalette {

	public static String ID = "org.pky.uml.editor.UMLEditor";
	private boolean editorSaving = false;

	private UMLDiagramModel logicDiagram = new UMLDiagramModel();

	private ISelection toolSelection =null;

	public UMLEditor(){

		try {
			setEditDomain(new UMLDefaultEditDomain(this));
			ProjectManager.getInstance().setUMLEditor(this);
			if (ProjectManager.getInstance().getOpenDiagramModel() != null) {
				this.logicDiagram = (UMLDiagramModel)ProjectManager.getInstance().getOpenDiagramModel();
			}
			if (this.logicDiagram != null) {
				this.logicDiagram.setUmlEditor(this);
			}

		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public Object getAdapter(Class type) {
		if (type == ZoomManager.class)
			return getGraphicalViewer().getProperty(ZoomManager.class.toString());
		return super.getAdapter(type);
	}

	@Override
	public void doSave(IProgressMonitor monitor) {
		editorSaving = true;
		//		Platform.run(
		//				new SafeRunnable() {
		//					public void run() throws Exception {
		//						//						saveProperties();
		//						ByteArrayOutputStream out = new ByteArrayOutputStream();
		//						writeToOutputStream(out);
		//						////			IFile file = ((IFileEditorInput)getEditorInput()).getFile();
		//						//			file.setContents(new ByteArrayInputStream(out.toByteArray()),
		//						//							true, false, progressMonitor);
		//						//			getCommandStack().markSaveLocation();
		//					}
		//				});
		editorSaving = false;
	}
	protected void writeToOutputStream(OutputStream os) throws IOException {
		if(!ProjectManager.getInstance().getProjectPath().equals("")){
			ModelDataManager.getInstance().saveFile(ProjectManager.getInstance().getProjectPath());

		}else{
			ProjectManager.getInstance().showMessage(MessageDialog.WARNING, "", "프로젝트가 지정되어 있지 않습니다. 저장할 수 없습니다.");
		}
	}
	@Override
	public boolean isDirty() {
		// TODO Auto-generated method stub
	return false;
	}
	public boolean isEditorSaving() {
		return false;
	}
	@Override
	public void doSaveAs() {
		// TODO Auto-generated method stub
		//		super.doSaveAs();
	}
	public boolean isSaveOnCloseNeeded() {
		return false;
	}
	@Override
	protected void configureGraphicalViewer() {
		super.configureGraphicalViewer();
		GraphicalViewer viewer = getGraphicalViewer();
		viewer.setSelectionManager(new UAppSelectionManager());

		ScalableFreeformRootEditPart root = new ScalableFreeformRootEditPart();
		viewer.setRootEditPart(root);
		viewer.setEditPartFactory(new UMLEditPartFactory());

		ContextMenuProvider provider = new UMLContextMenuProvider(viewer, getActionRegistry());
		viewer.setContextMenu(provider);

		List zoomLevels = new ArrayList();
		zoomLevels.add(ZoomManager.FIT_ALL);
		zoomLevels.add(ZoomManager.FIT_WIDTH);
		zoomLevels.add(ZoomManager.FIT_HEIGHT);
		root.getZoomManager().setZoomLevelContributions(zoomLevels);
		root.getZoomManager().setZoom(1.0);
		IAction zoomIn = new ZoomInAction(root.getZoomManager());
		IAction zoomOut = new ZoomOutAction(root.getZoomManager());
		getActionRegistry().registerAction(zoomIn);
		getActionRegistry().registerAction(zoomOut);
		getSite().getKeyBindingService().registerAction(zoomIn);
		getSite().getKeyBindingService().registerAction(zoomOut);

		IAction showRulers = new ToggleRulerVisibilityAction(getGraphicalViewer());
		getActionRegistry().registerAction(showRulers);
		ToggleSnapToGeometryAction snapAction = new ToggleSnapToGeometryAction(getGraphicalViewer());
		getActionRegistry().registerAction(snapAction);
		snapAction.setChecked(false);
		ToggleGridAction showGrid = new ToggleGridAction(getGraphicalViewer());
		getActionRegistry().registerAction(showGrid);
		
		
		viewer.setProperty(SnapToGrid.PROPERTY_GRID_ENABLED, false);
		viewer.setProperty(SnapToGrid.PROPERTY_GRID_VISIBLE, false);



		Object obj = getSite().getSelectionProvider();
		viewer.setKeyHandler(new YouAppKeyHandler(viewer, getSite().getSelectionProvider(), getCommandStack()));


	}
	@Override
	public void selectionChanged(IWorkbenchPart part, ISelection selection) {
		// TODO Auto-generated method stub
		super.selectionChanged(part, selection);
	}
	@Override
	protected void initializeGraphicalViewer() {


		super.initializeGraphicalViewer();
		ProjectManager.getInstance().setLoad(true);
		getGraphicalViewer().setContents(getUMLDiagramModel());
		getGraphicalViewer().addDropTargetListener((TransferDropTargetListener)new
				UMLTemplateTransferDropTargetListener(getGraphicalViewer()));
		ProjectManager.getInstance().setLoad(false);

	}

	protected void createSuperActions() {

		ActionRegistry registry = getActionRegistry();
		IAction action;

		action = new ModelDeleteAction((IWorkbenchPart)this);
		registry.registerAction(action);
		getSelectionActions().add(action.getId());
		
		action = new ModelImageWidthPercentAction(this);
		registry.registerAction(action);
		getSelectionActions().add(action.getId());
		
		action = new ModelImageSizeChangeAction(this);
		registry.registerAction(action);
		getSelectionActions().add(action.getId());
		
		action = new AlignmentAction((IWorkbenchPart)this, PositionConstants.LEFT);
		registry.registerAction(action);
		getSelectionActions().add(action.getId());
		action = new AlignmentAction((IWorkbenchPart)this, PositionConstants.RIGHT);
		registry.registerAction(action);
		getSelectionActions().add(action.getId());
		action = new AlignmentAction((IWorkbenchPart)this, PositionConstants.TOP);
		registry.registerAction(action);
		getSelectionActions().add(action.getId());
		action = new AlignmentAction((IWorkbenchPart)this, PositionConstants.BOTTOM);
		registry.registerAction(action);
		getSelectionActions().add(action.getId());
		action = new AlignmentAction((IWorkbenchPart)this, PositionConstants.CENTER);
		registry.registerAction(action);
		getSelectionActions().add(action.getId());
		action = new AlignmentAction((IWorkbenchPart)this, PositionConstants.MIDDLE);
		
		


	}
	@Override
	protected void createActions() {
		// TODO Auto-generated method stub
		super.createActions();
		
		createSuperActions();
	}

	public ScrollingGraphicalViewer getScrollingGraphicalViewer() {
		ScrollingGraphicalViewer viewer = (ScrollingGraphicalViewer)getGraphicalViewer();

		return viewer;
	}



	protected PaletteViewerProvider createPaletteViewerProvider() {
		return new PaletteViewerProvider(getEditDomain()) {
			private IMenuListener menuListener;
			protected void configurePaletteViewer(PaletteViewer viewer) {
				super.configurePaletteViewer(viewer);
				viewer.setCustomizer(new LogicPaletteCustomizer());
				viewer.addDragSourceListener(new TemplateTransferDragSourceListener(viewer));
			}
			protected void hookPaletteViewer(PaletteViewer viewer) {
				super.hookPaletteViewer(viewer);
				final CopyTemplateAction copy = (CopyTemplateAction)getActionRegistry().getAction(ActionFactory.COPY.getId());
				viewer.addSelectionChangedListener(copy);
				if (menuListener == null)
					menuListener = new IMenuListener() {
					public void menuAboutToShow(IMenuManager manager) {
						manager.appendToGroup(GEFActionConstants.GROUP_COPY, copy);
					}
				};

				viewer.addSelectionChangedListener(new ISelectionChangedListener(){

					public void selectionChanged(SelectionChangedEvent event) {
						toolSelection=  event.getSelection();

					}

				});

				viewer.getContextMenu().addMenuListener(menuListener);
			}
		};
	}
	@Override
	protected void createGraphicalViewer(Composite parent) {
		// TODO Auto-generated method stub
		super.createGraphicalViewer(parent);
	}

	public boolean isSaveAsAllowed() {
		return false;
	}

	public void setTitleName(String titleName) {

		this.setTitle(titleName);
	}
	@Override
	protected void setEditDomain(DefaultEditDomain ed) {
		// TODO Auto-generated method stub
		super.setEditDomain(ed);


	}
	@Override
	public void setFocus() {
		ProjectManager.getInstance().setUMLEditor(this);
		getGraphicalViewer().getControl().setFocus();
		super.setFocus();
	}
	protected void closeEditor(boolean save) {
		getSite().getPage().closeEditor(UMLEditor.this, false);
	}
	@Override
	protected PaletteRoot getPaletteRoot() {
		PaletteRoot palette = new PaletteRoot();
		palette.addAll(createUseCaseDiagramCategories(palette));
		palette.moveUp(new PaletteEntry("", ""));
		return palette;
	}
	private List createUseCaseDiagramCategories(PaletteRoot root) {





		List categories = new ArrayList();
		categories.add(createControlGroup(root));
		categories.add(createLayoutsDrawer());

		categories.add(createCompositesDrawer());

		return categories;
	}
	private PaletteContainer createLayoutsDrawer() {

		ToolEntry tool = new PanningSelectionToolEntry();
		PaletteDrawer drawer = new PaletteDrawer("Layouts", ImageDescriptor.createFromFile(ImagePoint.class, "layouts_title.png")); //$NON-NLS-1$
		List entries = new ArrayList();


		CombinedTemplateCreationEntry combined = new
				CombinedTemplateCreationEntry("Layout", "Layout",
						new TreeSimpleFactory(LayoutModel.class), ImageDescriptor.createFromFile(ImagePoint.class, "layout.png"), //$NON-NLS-1$
						ImageDescriptor.createFromFile(ImagePoint.class, "layout.png") //$NON-NLS-1$
						);
		entries.add(combined);

		drawer.addAll(entries);
		return drawer;
	}

	private PaletteContainer createCompositesDrawer() {
		ToolEntry tool = new PanningSelectionToolEntry();
		PaletteDrawer drawer = new PaletteDrawer("Controller", ImageDescriptor.createFromFile(ImagePoint.class, "controller_title.png")); //$NON-NLS-1$
		List entries = new ArrayList();


		CombinedTemplateCreationEntry combined = new
				CombinedTemplateCreationEntry("ListView", "",
						new TreeSimpleFactory(ListViewModel.class), ImageDescriptor.createFromFile(ImagePoint.class, "listView.png"), //$NON-NLS-1$
						ImageDescriptor.createFromFile(ImagePoint.class, "listView.png") //$NON-NLS-1$
						);
		entries.add(combined);
		combined = new
				CombinedTemplateCreationEntry("Button", "",
						new TreeSimpleFactory(ButtonModel.class), ImageDescriptor.createFromFile(ImagePoint.class, "button.png"), //$NON-NLS-1$
						ImageDescriptor.createFromFile(ImagePoint.class, "button.png") //$NON-NLS-1$
						);
		entries.add(combined);
		combined = new
				CombinedTemplateCreationEntry("WebView", "",
						new TreeSimpleFactory(WebViewModel.class), ImageDescriptor.createFromFile(ImagePoint.class, "webview.png"), //$NON-NLS-1$
						ImageDescriptor.createFromFile(ImagePoint.class, "webview.png") //$NON-NLS-1$
						);
		entries.add(combined);
		combined = new
				CombinedTemplateCreationEntry("ImageView", "",
						new TreeSimpleFactory(ImageViewModel.class), ImageDescriptor.createFromFile(ImagePoint.class, "image.png"), //$NON-NLS-1$
						ImageDescriptor.createFromFile(ImagePoint.class, "image.png") //$NON-NLS-1$
						);
		entries.add(combined);

		combined = new
				CombinedTemplateCreationEntry("Video", "",
						new TreeSimpleFactory(VideoModel.class), ImageDescriptor.createFromFile(ImagePoint.class, "video.png"), //$NON-NLS-1$
						ImageDescriptor.createFromFile(ImagePoint.class, "video.png") //$NON-NLS-1$
						);
		entries.add(combined);
		combined = new
				CombinedTemplateCreationEntry("Youtube", "",
						new TreeSimpleFactory(YoutubeModel.class), ImageDescriptor.createFromFile(ImagePoint.class, "youtube.png"), //$NON-NLS-1$
						ImageDescriptor.createFromFile(ImagePoint.class, "youtube.png") //$NON-NLS-1$
						);
		entries.add(combined);

		combined = new
				CombinedTemplateCreationEntry("Label", "",
						new TreeSimpleFactory(TextViewModel.class), ImageDescriptor.createFromFile(ImagePoint.class, "label.png"), //$NON-NLS-1$
						ImageDescriptor.createFromFile(ImagePoint.class, "label.png") //$NON-NLS-1$
						);
		entries.add(combined);

		combined = new
				CombinedTemplateCreationEntry("Plain Text", "",
						new TreeSimpleFactory(EditTextModel.class), ImageDescriptor.createFromFile(ImagePoint.class, "text.png"), //$NON-NLS-1$
						ImageDescriptor.createFromFile(ImagePoint.class, "text.png") //$NON-NLS-1$
						);
		entries.add(combined);



		//		tool = new ConnectionCreationToolEntry("ddd",
		//				"",
		//				new SimpleFactory(LineModel.class), ImageDescriptor.createFromFile(UseCaseModel.class, "icons/object_flow.gif"), //$NON-NLS-1$
		//				ImageDescriptor.createFromFile(UseCaseModel.class, "icons/object_flow.gif") //$NON-NLS-1$
		//		);
		//		entries.add(tool);
		drawer.addAll(entries);
		return drawer;

	}

	private PaletteContainer createControlGroup(PaletteRoot root) {
		PaletteGroup controlGroup = new PaletteGroup(PolicyCommand.LogicPlugin_Category_ControlGroup_Label);
		List entries = new ArrayList();
		ToolEntry tool = new PanningSelectionToolEntry();
		tool.setLabel("선택");
		entries.add(tool);
		root.setDefaultEntry(tool);
		//		PanningSelectionTool
		PaletteStack marqueeStack = new PaletteStack(PolicyCommand.Marquee_Stack, "", null); //$NON-NLS-1$
		MarqueeToolEntry marquee1 = new MarqueeToolEntry();
		marquee1.setLabel("다중선택");
		marqueeStack.add(marquee1);

		MarqueeToolEntry marquee = new MarqueeToolEntry();
		marquee.setToolProperty(MarqueeSelectionTool.PROPERTY_MARQUEE_BEHAVIOR,
				new Integer(MarqueeSelectionTool.BEHAVIOR_CONNECTIONS_TOUCHED));
		//		marqueeStack.add(marquee);
		marquee = new MarqueeToolEntry();
		marquee.setToolProperty(MarqueeSelectionTool.PROPERTY_MARQUEE_BEHAVIOR,
				new Integer(MarqueeSelectionTool.BEHAVIOR_CONNECTIONS_TOUCHED | MarqueeSelectionTool.BEHAVIOR_NODES_CONTAINED));
		//		marqueeStack.add(marquee);
		marqueeStack.setUserModificationPermission(PaletteEntry.PERMISSION_NO_MODIFICATION);
		entries.add(marqueeStack);

		controlGroup.addAll(entries);		
		controlGroup.addPropertyChangeListener(new PropertyChangeListener(){

			public void propertyChange(PropertyChangeEvent evt) {
				System.out.print("");

			}

		});
		return controlGroup;
	}

	public GraphicalViewer getGraphicalViewer() {
		return super.getGraphicalViewer();
	}

	public UMLDiagramModel getUMLDiagramModel() {
		return logicDiagram;
	}

	public void setUMLDiagramModel(UMLDiagramModel umlDiagramModel) {
		this.logicDiagram = umlDiagramModel;
	};
}
