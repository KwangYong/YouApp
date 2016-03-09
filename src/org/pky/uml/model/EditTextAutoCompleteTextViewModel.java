package org.pky.uml.model;

import org.pky.uml.browser.common.propertybrowser.Property;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class EditTextAutoCompleteTextViewModel extends EditTextModel{                             

	public EditTextAutoCompleteTextViewModel(){            
		super();                                        
		setPropertyValue(Property.ID_TYPE, 13);
	}  
	public Element writeLayoutAndroid(Document doc) {
		Element element = super.writeLayoutAndroid(doc);
		return element;
	}
}     