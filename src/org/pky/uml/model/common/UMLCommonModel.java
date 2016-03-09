package org.pky.uml.model.common;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;
import java.util.ArrayList;

import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertySource;
import org.pky.uml.model.OperationItem;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public abstract class UMLCommonModel implements IPropertySource, Cloneable, Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	transient protected PropertyChangeSupport listeners = new PropertyChangeSupport(this);
	
	public void addPropertyChangeListener(PropertyChangeListener l) {
		listeners.addPropertyChangeListener(l);
	}

	protected void firePropertyChange(String prop, Object old, Object newValue) {
		
		listeners.firePropertyChange(prop, old, newValue);
	}

	protected void fireChildAdded(String prop, Object child, Object index) {
		
		listeners.firePropertyChange(prop, index, child);
		
	}
	public void removePropertyChangeListener(PropertyChangeListener listener) {
		listeners.removePropertyChangeListener(listener);
	}
	protected void fireChildRemoved(String prop, Object child) {
		listeners.firePropertyChange(prop, child, null);
	}

	protected void fireStructureChange(String prop, Object child) {
		listeners.firePropertyChange(prop, null, child);
	}
	
	public String writeSourceIOS(){
		return "";
	}
	public abstract String  writeLayoutAndroid();  //android 에서 재조정
	public abstract Element writeXMLLayoutAndroid(Document doc); //XML 
	public Element writeLayoutAndroid(Document doc){
		return null;
	}

	@Override
	public Object getEditableValue() {
		// TODO Auto-generatehd method stub
		return null;
	}

	@Override
	public IPropertyDescriptor[] getPropertyDescriptors() {
		// TODO Auto-generated method stub
		return new IPropertyDescriptor[0];
	}

	@Override
	public Object getPropertyValue(Object id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isPropertySet(Object id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void resetPropertyValue(Object id) {
		// TODO Auto-generated method stubh
		
	}

	@Override
	public void setPropertyValue(Object id, Object value) {
		// TODO Auto-generated method stub
		
	}


}
