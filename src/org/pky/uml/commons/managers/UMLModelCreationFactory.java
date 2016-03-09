package org.pky.uml.commons.managers;

import org.eclipse.gef.requests.CreationFactory;
import org.pky.uml.model.UseCaseModel;

public class UMLModelCreationFactory implements CreationFactory {

	private Class<?> template;

	public UMLModelCreationFactory(Class<?> template) {
		this.template = template;
	}

	@Override
	public Object getNewObject() {
		if (template == null)
			return null;
		if (template == UseCaseModel.class) {
			return new UseCaseModel();
		}			
		return null;
	}

	@Override
	public Object getObjectType() {
		return template;
	}
}