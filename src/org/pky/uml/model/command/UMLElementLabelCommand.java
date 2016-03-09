package org.pky.uml.model.command;

import org.eclipse.gef.commands.Command;
import org.pky.uml.browser.common.propertybrowser.Property;
import org.pky.uml.model.AttributeItem;
import org.pky.uml.model.ElementModel;
import org.pky.uml.model.TextViewModel;

public class UMLElementLabelCommand extends Command {
	private String newName, oldName;
	private TextViewModel label;
	private int type = -1;

	public UMLElementLabelCommand(TextViewModel l, String s) {
		label = l;
		if (s != null)
			newName = s;
		else
			newName = ""; //$NON-NLS-1$
	}
	public void execute() {

		label.setPropertyValue(Property.ID_TEXT, newName);


	}

}
