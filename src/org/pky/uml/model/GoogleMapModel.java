package org.pky.uml.model;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class GoogleMapModel extends WebViewModel{

	public GoogleMapModel() {
		super(300,300);
	}
	public Element writeLayoutAndroid(Document doc) {
		// TODO Auto-generated method stub
		Element element = super.writeLayoutAndroid(doc);
//		element.setAttribute("class", "com.google.android.gms.maps.SupportMapFragment");
//		System.out.println(element.getTagName());
//		
//		
//		Element fragment = doc.createElement("fragment");
//		for(int i = 0 ; i < element.getAttributes().getLength(); i ++){
//			fragment.setAttribute(element.getAttributes().item(i).getNodeName(),element.getAttributes().item(i).getNodeValue());	
//		}

//		return fragment;
		return element;
	}
}
