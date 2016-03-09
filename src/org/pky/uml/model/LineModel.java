package org.pky.uml.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import org.eclipse.draw2d.Bendpoint;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.pky.uml.model.common.UMLCommonModel;
import org.pky.uml.model.common.UMLDataModel;
import org.pky.uml.model.common.UMLModel;
import org.w3c.dom.Document;
import org.w3c.dom.Element;


public class LineModel extends UMLCommonModel{


	protected UMLModel source, target;
	protected UMLModel oldSource, oldTarget;
	protected String sourceTerminal, targetTerminal;

	private UMLDataModel uDataModel;

	protected IPropertyDescriptor[] descriptors = null;

	protected List bendpoints = new ArrayList();

	public LineModel(){
		uDataModel =  new UMLDataModel(this);

		descriptors = new IPropertyDescriptor[] {

		};

	}
	public void attachSource() {
		if (getSource() == null || getSource().getSourceConnections().contains(this))
			return;
		Object obj = getSource();
		getSource().connectOutput(this);
	}
	public void attachTarget() {
		if (getTarget() == null || getTarget().getTargetConnections().contains(this))
			return;
		//inmsg
		getTarget().connectInput(this);

	}

	public void detachSource() {
		if (getSource() == null)
			return;
		getSource().disconnectOutput(this);
	}
	public void detachTarget() {
		if (getTarget() == null)
			return;
		getTarget().disconnectInput(this);

	}

	public void setTarget(UMLModel e) {
		Object old = target;
		target = e;

		firePropertyChange("target", old, target); //$NON-NLS-1$
	}

	public void setSource(UMLModel e) {
		Object old = source;
		source = e;

		firePropertyChange("source", old, source); //$NON-NLS-1$
	}

	public void setSourceTerminal(String s) {
		Object old = sourceTerminal;
		sourceTerminal = s;
		//0801001IJS
		sourceTerminal = "source:"+this.uDataModel.getID();
		firePropertyChange("sourceTerminal", old, sourceTerminal); //$NON-NLS-1$
	}
	public void setTargetTerminal(String s) {
		Object old = targetTerminal;
		targetTerminal = s;
		//0801001IJS
		targetTerminal = "targetTerminal:"+this.uDataModel.getID();
		firePropertyChange("targetTerminal", old, targetTerminal); //$NON-NLS-1$
	}

	public void insertBendpoint(int index, Bendpoint point) {
		getBendpoints().add(index, point);
		firePropertyChange("bendpoint", null, null); //$NON-NLS-1$
		//		}
	}
	//PKY 08051401 S 라인 꺽인것 바로 직선으로 만들기
	public void directBendpoint() {
		getBendpoints().clear();
		firePropertyChange("bendpoint", null, null); //$NON-NLS-1$
		//		}
	}
	//PKY 08051401 E 라인 꺽인것 바로 직선으로 만들기
	public void removeBendpoint(int index) {
		getBendpoints().remove(index);
		firePropertyChange("bendpoint", null, null); //$NON-NLS-1$
	}

	public void setBendpoint(int index, Bendpoint point) {
		getBendpoints().set(index, point);
		firePropertyChange("bendpoint", null, null); //$NON-NLS-1$
	}

	public void setBendpoints(Vector points) {
		bendpoints = points;
		firePropertyChange("bendpoint", null, null); //$NON-NLS-1$
	}

	public UMLModel getSource() {
		return source;
	}

	public UMLModel getTarget() {
		return target;
	}
	public String getSourceTerminal() {
		return sourceTerminal;
	}
	public String getTargetTerminal() {
		return targetTerminal;
	}
	public String toString() {
		return "Wire(" + getSource() + "," + getSourceTerminal() + "->" + getTarget() + "," + getTargetTerminal() + ")"; //$NON-NLS-5$//$NON-NLS-4$//$NON-NLS-3$//$NON-NLS-2$//$NON-NLS-1$
	}
	public List getBendpoints() {
		return bendpoints;
	}
	public void setBendpoints(List bendpoints) {
		this.bendpoints = bendpoints;
	}
	@Override
	public String writeLayoutAndroid() {
		// TODO Auto-generated method stub
		return null;
	}
	public Element writeXMLLayoutAndroid(Document doc) {
		return null;
	}



}
