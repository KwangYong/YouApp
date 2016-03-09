package org.pky.uml.editparts.common;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.draw2d.ConnectionAnchor;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.FreeformLayer;
import org.eclipse.draw2d.FreeformLayout;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.MarginBorder;
import org.eclipse.draw2d.XYLayout;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.CompoundSnapToHelper;
import org.eclipse.gef.ConnectionEditPart;
import org.eclipse.gef.DragTracker;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.LayerConstants;
import org.eclipse.gef.Request;
import org.eclipse.gef.SnapToGeometry;
import org.eclipse.gef.SnapToGrid;
import org.eclipse.gef.SnapToGuides;
import org.eclipse.gef.SnapToHelper;
import org.eclipse.gef.editpolicies.RootComponentEditPolicy;
import org.eclipse.gef.editpolicies.SnapFeedbackPolicy;
import org.eclipse.gef.requests.SelectionRequest;
import org.eclipse.gef.rulers.RulerProvider;
import org.eclipse.gef.tools.DeselectAllTracker;
import org.eclipse.gef.tools.MarqueeDragTracker;
import org.eclipse.gef.ui.parts.ScrollingGraphicalViewer;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Device;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.RGB;
import org.pky.uml.commons.managers.ProjectManager;
import org.pky.uml.policy.UMLXYLayoutEditPolicy;

public class UMLDiagramEditPart extends UMLEditPart implements LayerConstants{

	public UMLDiagramEditPart() {
		super();

	}
	protected IFigure createFigure() {
		Figure f = new FreeformLayer(){
			@Override
			protected void paintFigure(Graphics graphics) {
				// TODO Auto-generated method stub
				Rectangle r = Rectangle.SINGLETON;
				r.setBounds(getBounds());
				graphics.setAlpha(30);
//				if(true)
//					return;
				
				Color color = getBackgroundColor();
				Device device = color.getDevice();
//				graphics.setForegroundColor(new Color(device,new RGB(255,255,255)));
//				graphics.setForegroundColor(new Color(device,new RGB(200,200,200)));
				
				graphics.fillGradient(r.x, r.y, r.width, r.height, true);  
//				graphics.dispose();

				
				
			}
		};

		f.setLayoutManager(new FreeformLayout());
		f.setBorder(new MarginBorder(5));
		//    BoundaryFigure f = new BoundaryFigure();
		return f;
	}

	@Override
	protected void createEditPolicies() {
		super.createEditPolicies();
		installEditPolicy(EditPolicy.NODE_ROLE, null);
		//installEditPolicy(EditPolicy.GRAPHICAL_NODE_ROLE, null);
		installEditPolicy(EditPolicy.SELECTION_FEEDBACK_ROLE, null);
		installEditPolicy(EditPolicy.COMPONENT_ROLE, new RootComponentEditPolicy());
		installEditPolicy(EditPolicy.LAYOUT_ROLE, new UMLXYLayoutEditPolicy((XYLayout)getContentPane().getLayoutManager()));
		installEditPolicy("Snap Feedback", new SnapFeedbackPolicy()); //$NON-NLS-1$

	}
	@Override
	public void setSelected(int value) {
		ScrollingGraphicalViewer viewer = ProjectManager.getInstance().getUMLEditor().getScrollingGraphicalViewer();

	}
	public Object getAdapter(Class adapter) {
		if (adapter == SnapToHelper.class) {
			List snapStrategies = new ArrayList();
			Boolean val = (Boolean)getViewer().getProperty(RulerProvider.PROPERTY_RULER_VISIBILITY);
			if (val != null && val.booleanValue())
				snapStrategies.add(new SnapToGuides(this));
			val = (Boolean)getViewer().getProperty(SnapToGeometry.PROPERTY_SNAP_ENABLED);
			if (val != null && val.booleanValue())
				snapStrategies.add(new SnapToGeometry(this));
			val = (Boolean)getViewer().getProperty(SnapToGrid.PROPERTY_GRID_ENABLED);
			if (val != null && val.booleanValue())
				snapStrategies.add(new SnapToGrid(this));
			if (snapStrategies.size() == 0)
				return null;
			if (snapStrategies.size() == 1)
				return snapStrategies.get(0);
			SnapToHelper ss[] = new SnapToHelper[snapStrategies.size()];
			for (int i = 0; i < snapStrategies.size(); i++)
				ss[i] = (SnapToHelper)snapStrategies.get(i);
			return new CompoundSnapToHelper(ss);
		}
		return super.getAdapter(adapter);
	}
	public DragTracker getDragTracker(Request req) {
		if (req instanceof SelectionRequest && ((SelectionRequest)req).getLastButtonPressed() == 3)
			return new DeselectAllTracker(this);
		return new MarqueeDragTracker();
	}

	@Override
	public ConnectionAnchor getSourceConnectionAnchor(Request request) {
		// TODO Auto-generated method stub
		return null;
	}

	public ConnectionAnchor getSourceConnectionAnchor(ConnectionEditPart editPart) {
		return null;
	}

	public ConnectionAnchor getSourceConnectionAnchor(int x, int y) {
		return null;
	}
	@Override
	public ConnectionAnchor getTargetConnectionAnchor(Request request) {
		// TODO Auto-generated method stub
		return null;
	}
	public void refreshVisuals() {
		super.refreshVisuals();

	}

	public ConnectionAnchor getTargetConnectionAnchor(int x, int y) {
		return null;
	}
	public ConnectionAnchor getTargetConnectionAnchor(ConnectionEditPart editPart) {
		return null;
	}
}
