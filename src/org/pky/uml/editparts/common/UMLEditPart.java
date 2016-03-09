package org.pky.uml.editparts.common;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.draw2d.ConnectionAnchor;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.XYLayout;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.ConnectionEditPart;
import org.eclipse.gef.DragTracker;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.NodeEditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.editparts.AbstractGraphicalEditPart;
import org.eclipse.gef.requests.DropRequest;
import org.eclipse.gef.requests.SelectionRequest;
import org.eclipse.gef.ui.parts.ScrollingGraphicalViewer;
import org.eclipse.jface.viewers.StructuredSelection;
import org.pky.uml.browser.common.propertybrowser.Property;
import org.pky.uml.commons.managers.EditPartManager;
import org.pky.uml.commons.managers.ProjectManager;
import org.pky.uml.figures.common.UMLFigure;
import org.pky.uml.model.LineModel;
import org.pky.uml.model.common.UMLModel;
import org.pky.uml.policy.PolicyCommand;
import org.pky.uml.policy.UMLContainerEditPolicy;
import org.pky.uml.policy.UMLDeleteEditPolicy;
import org.pky.uml.policy.UMLXYLayoutEditPolicy;

public class UMLEditPart extends AbstractGraphicalEditPart implements NodeEditPart,PropertyChangeListener{



	public void activate(){
		if (isActive())
			return;
		super.activate();
		getUMLModel().addPropertyChangeListener(this);

	}
	public UMLModel getUMLModel(){
		return getModel();
	}
	@Override
	public void deactivate() {
		((UMLModel) getModel()).removePropertyChangeListener(this);
		super.deactivate();
	}
	@Override
	protected IFigure createFigure() {

		return EditPartManager.getNewFigure(this);
	}

	@Override
	protected void addChild(EditPart child, int index) {
		// TODO Auto-generated method stub
		//		((UMLModel)getModel()).addPropertyChangeListener((UMLEditPart)child);
		super.addChild(child, index);
	}
	@Override
	protected void createEditPolicies() {

		//		installEditPolicy(EditPolicy.LAYOUT_ROLE, new UMLXYLayoutEditPolicy((XYLayout)getContentPane().getLayoutManager()));
		installEditPolicy(EditPolicy.CONTAINER_ROLE, new UMLContainerEditPolicy());
		installEditPolicy(EditPolicy.COMPONENT_ROLE, new UMLDeleteEditPolicy());
	}
	@Override
	protected List<UMLModel> getModelChildren() {
		return ((UMLModel) getModel()).getChildren();
	}
	public UMLModel getModel(){
		return (UMLModel)super.getModel();
	}
	public Rectangle getParentLocation(){
		UMLModel umlModel = getParentEdit(this).getModel();
		return umlModel.getLocationRect();

	}


	public UMLEditPart getParentEdit(UMLEditPart umlEditPart){
		if(umlEditPart.getParent() !=null && !(umlEditPart.getParent() instanceof UMLDiagramEditPart)){
			return getParentEdit((UMLEditPart)umlEditPart.getParent());
		}else{
			return umlEditPart;
		}

	}

	public void propertyChange(PropertyChangeEvent evt) {
		try {
			String prop = evt.getPropertyName();
			if (PolicyCommand.CHILDREN.equals(prop)) {
				if (evt.getOldValue() instanceof Integer) {
					EditPart editPart = (EditPart)createChild(evt.getNewValue());
					if(editPart!=null)
						addChild(editPart, ((Integer)evt.getOldValue()).intValue());
				} else {

					removeChild((EditPart)getViewer().getEditPartRegistry().get(evt.getOldValue()));
				}
			}else if (evt.getPropertyName().equals(PolicyCommand.PROPERTY_LAYOUT)){

				refreshVisuals();
				ProjectManager.getInstance().setRefreshList(new ArrayList<UMLEditPart>());
				childrenRefresh(this);


			}else if(evt.getPropertyName().equals(PolicyCommand.REQ_DIRECT_EDT)){

			}else if(evt.getPropertyName().equals(PolicyCommand.CHANGE_VALUE)){
				refreshVisuals();
			}else if(evt.getPropertyName().equals(Property.ID_INPUT)){
				refreshTargetConnections();
			}else if(evt.getPropertyName().equals(Property.ID_OUTPUT)){
				refreshSourceConnections();
			}



		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void childrenRefresh(UMLEditPart editPart){
		for(int i = 0; i < editPart.getChildren().size(); i++){
			UMLEditPart umlEditPart = (UMLEditPart)editPart.getChildren().get(i);
			if(!ProjectManager.getInstance().getRefreshList().contains(umlEditPart)){
				ProjectManager.getInstance().getRefreshList().add(umlEditPart);
				umlEditPart.refresh();
				childrenRefresh(umlEditPart);
			}
		}
	}
	@Override
	public void setSelected(int value) {
		ScrollingGraphicalViewer viewer = ProjectManager.getInstance().getUMLEditor().getScrollingGraphicalViewer();

		if(this.getParent()!=null && this.getParent() instanceof UMLDiagramEditPart){
			super.setSelected(value);	
		}else if(value == 2){
			UMLEditPart edit = this.getParentEdit(this);
			Object obj =viewer.getSelection();

//			if(viewer.getSelectedEditParts().size()==1)
				viewer.select(edit);

		}


	}

	protected void  performDirectEdit() {
		//		if(ProjectManager.getInstance().getUmlEditManager()!=null){
		//			ProjectManager.getInstance().setUmlEditManager(new UMLEditManager(this, TextCellEditor.class, new Activite))
		//		}

	}

	final public String mapConnectionAnchorToTerminal(ConnectionAnchor c) {
		return getNodeFigure().getConnectionAnchorName(c);
	}

	protected List getModelSourceConnections() {
		return getUMLModel().getSourceConnections();
	}
	protected List getModelTargetConnections() {
		return getUMLModel().getTargetConnections();
	}
	@Override
	public IFigure getFigure() {
		// TODO Auto-generated method stub
		return super.getFigure();
	}

	protected UMLFigure getNodeFigure() {
		if(!(getFigure() instanceof UMLFigure)){
			Object obj = getFigure();
			System.out.println("");
		}
		return (UMLFigure)getFigure();
	}

	public ConnectionAnchor getSourceConnectionAnchor(ConnectionEditPart connEditPart) {
		LineModel wire = (LineModel)connEditPart.getModel();
		ConnectionAnchor connectionAnchor = getNodeFigure().getConnectionAnchor(wire.getSourceTerminal());

		return connectionAnchor;
	}

	public ConnectionAnchor getSourceConnectionAnchor(Request request) {
		Point pt = new Point(((DropRequest)request).getLocation());
		return getNodeFigure().getSourceConnectionAnchorAt(pt);
	}

	public ConnectionAnchor getTargetConnectionAnchor(ConnectionEditPart connEditPart) {
		LineModel wire = (LineModel)connEditPart.getModel();
		ConnectionAnchor connectionAnchor = getNodeFigure().getConnectionAnchor(wire.getSourceTerminal());
		return getNodeFigure().getConnectionAnchor(wire.getTargetTerminal());
	}

	public ConnectionAnchor getTargetConnectionAnchor(Request request) {

		Point pt = new Point(((DropRequest)request).getLocation());

		return getNodeFigure().getTargetConnectionAnchorAt(pt);



	}

	@Override
	public DragTracker getDragTracker(Request request) {
		// TODO Auto-generated method stub
		if(request instanceof SelectionRequest){
			SelectionRequest select = (SelectionRequest)request;

		}
		return super.getDragTracker(request);
	}
	public void refreshVisuals() {

		// TODO Auto-generated method stub
		if(getFigure() instanceof UMLFigure){
			UMLFigure figure = (UMLFigure)getFigure();
			//		ClassModel model = (ClassModel)getModel();
			//		figure.setLocation(model.getLocation());
			//		
			//		figure.setSize(model.getSize());
			//		super.refreshVisuals();

			if(figure.getLocation().equals(0, 0)&&figure.getSize().equals(0, 0)&&!ProjectManager.getInstance().isLoad()){
				Rectangle rect = this.getParentLocation();
				Point loc = new  Point(rect.x,getModel().getLocation().y);
				loc.setLocation(figure.getLocation().x+loc.x, figure.getLocation().y+loc.y);
				figure.setLocation(loc);
				Dimension size = new Dimension(rect.width(),getModel().getSize().height());
				figure.setSize(size);
			}else{
				Rectangle rect = getUMLModel().getViewModel().getLocationRect();
				Point loc = new Point(rect.x,rect.y);//getUMLModel().getLocation();
				Dimension size = new Dimension(rect.width(),rect.height());
				Rectangle r = new Rectangle(loc, size);

				((GraphicalEditPart)getParent()).setLayoutConstraint(this, getFigure(), r);
				figure.setSize(size);
			}
		}

	}

}
