package org.pky.uml.editparts;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.draw2d.Connection;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.ManhattanConnectionRouter;
import org.eclipse.draw2d.PolylineConnection;
import org.eclipse.draw2d.RelativeBendpoint;
import org.eclipse.draw2d.RoutingAnimator;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.gef.AccessibleEditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.editparts.AbstractConnectionEditPart;
import org.eclipse.swt.accessibility.AccessibleEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Display;
import org.pky.uml.editor.LogicMessages;
import org.pky.uml.figures.UMLPolylineConnection;
import org.pky.uml.model.LineModel;
import org.pky.uml.model.common.LineBendpointModel;
import org.pky.uml.policy.LineBendpointEditPolicy;
import org.pky.uml.policy.LineEditPolicy;
import org.pky.uml.policy.LineEndpointEditPolicy;

public class LineEditPart extends AbstractConnectionEditPart implements PropertyChangeListener {
	AccessibleEditPart acc;

	public LineEditPart() {
		System.out.println("ddddfdf");
	}

	public static final Color alive = new Color(Display.getDefault(), 0, 74, 168),
			dead = new Color(Display.getDefault(), 0, 0, 0);

	public void activate() {
		super.activate();
		getWire().addPropertyChangeListener(this);
	}
	protected IFigure createFigure() {
		UMLPolylineConnection connx = new UMLPolylineConnection();//PKY 08101566 S
		connx.addRoutingListener(RoutingAnimator.getDefault());
		if(connx instanceof UMLPolylineConnection){
			((UMLPolylineConnection)connx).setLineEditPart(this);
		}

		return connx;

	}

	public void activateFigure() {
		super.activateFigure();

		/*Once the figure has been added to the ConnectionLayer, start listening for its
		 * router to change.
		 */

		getFigure().addPropertyChangeListener(Connection.PROPERTY_CONNECTION_ROUTER, this);
	}

	/** Adds extra EditPolicies as required. */
	protected void createEditPolicies() {
		//	installEditPolicy(EditPolicy.CONNECTION_ENDPOINTS_ROLE, null);
		installEditPolicy(EditPolicy.CONNECTION_ENDPOINTS_ROLE, new LineEndpointEditPolicy());
		//Note that the Connection is already added to the diagram and knows its Router.
		refreshBendpointEditPolicy();
		installEditPolicy(EditPolicy.CONNECTION_ROLE, new LineEditPolicy());

	}



	public void deactivate() {
		getWire().removePropertyChangeListener(this);
		super.deactivate();
	}

	public void deactivateFigure() {
		getFigure().removePropertyChangeListener(Connection.PROPERTY_CONNECTION_ROUTER, this);
		super.deactivateFigure();
	}

	public AccessibleEditPart getAccessibleEditPart() {
		if (acc == null)
			acc = new AccessibleGraphicalEditPart() {
			public void getName(AccessibleEvent e) {
				e.result = LogicMessages.Wire_LabelText;
			}
		};
		return acc;
	}

	/**
	 * Returns the model of this represented as a Wire.
	 * @return  Model of this as <code>Wire</code>
	 */
	protected LineModel getWire() {
		return (LineModel)getModel();
	}

	/**
	 * Returns the Figure associated with this, which draws the Wire.
	 * @return  Figure of this.
	 */
	protected IFigure getWireFigure() {
		return (PolylineConnection)getFigure();
	}

	/**
	 * Listens to changes in properties of the Wire (like the contents being carried), and reflects is in the visuals.
	 * @param event  Event notifying the change.
	 */
	public void propertyChange(PropertyChangeEvent event) {
		try {

		}
		catch (Exception e) {
			e.printStackTrace();
		}

	}

	/** Updates the bendpoints, based on the model. */
	protected void refreshBendpoints() {
		//    	getConnectionFigure().setConnectionRouter(new ManhattanConnectionRouter());
		if (getConnectionFigure().getConnectionRouter() instanceof ManhattanConnectionRouter)
			return;
		List modelConstraint = getWire().getBendpoints();
		List figureConstraint = new ArrayList();
		if(modelConstraint.size()==0){  
			LineModel lmd = (LineModel)this.getModel();
			if(lmd.getTarget()==lmd.getSource()){
				LineBendpointModel lm = new LineBendpointModel();
				lm.setRelativeDimensions(new Dimension(lmd.getTarget().getSize().width/2+50,0), new Dimension(lmd.getTarget().getSize().width/2+50,0));

				modelConstraint.add(lm);
				lm = new LineBendpointModel();
				lm.setRelativeDimensions(new Dimension(lmd.getTarget().getSize().width/2+50,lmd.getTarget().getSize().height/2+50), new Dimension(lmd.getTarget().getSize().width/2+50,lmd.getTarget().getSize().height/2+50));
				modelConstraint.add(lm);
				lm = new LineBendpointModel();
				lm.setRelativeDimensions(new Dimension(0,lmd.getTarget().getSize().height/2+50), new Dimension(0,lmd.getTarget().getSize().height/2+50));
				modelConstraint.add(lm);
			}}

		for (int i = 0; i < modelConstraint.size(); i++) {
			LineBendpointModel wbp = (LineBendpointModel)modelConstraint.get(i);
			RelativeBendpoint rbp = new RelativeBendpoint(getConnectionFigure());
			rbp.setRelativeDimensions(wbp.getFirstRelativeDimension(), wbp.getSecondRelativeDimension());
			rbp.setWeight((i + 1) / ((float)modelConstraint.size() + 1));
			figureConstraint.add(rbp);
		}
		getConnectionFigure().setRoutingConstraint(figureConstraint);
	}

	private void refreshBendpointEditPolicy() {
		if (getConnectionFigure().getConnectionRouter() instanceof ManhattanConnectionRouter)
			installEditPolicy(EditPolicy.CONNECTION_BENDPOINTS_ROLE, null);
		else
			installEditPolicy(EditPolicy.CONNECTION_BENDPOINTS_ROLE, new LineBendpointEditPolicy());
	}

	/**
	 * Refreshes the visual aspects of this, based upon the
	 * model (Wire). It changes the wire color depending on the state of Wire.
	 */
	protected void refreshVisuals() {
		refreshBendpoints();

		getWireFigure().setForegroundColor(dead);
	}


	//    public void setSelected(int value) {
	//    super.setSelected(value);
	//    }
}
