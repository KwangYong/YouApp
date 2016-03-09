package org.pky.uml.browser.common.propertybrowser;

import java.util.Arrays;
import java.util.Comparator;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.views.properties.IPropertySheetEntry;
import org.eclipse.ui.views.properties.PropertySheetEntry;
import org.eclipse.ui.views.properties.PropertySheetPage;
import org.eclipse.ui.views.properties.PropertySheetSorter;
//PKY 08100607 S
public class PropertyPage extends PropertySheetPage {
	public PropertyPage() {

		super();

	}

	public void createControl(Composite parent) {

		super.createControl(parent);
		
		super.setSorter(new NameSorter2());
	}
}
class NameSorter2 extends PropertySheetSorter {
	public void sort(IPropertySheetEntry[] entries) {
		System.out.print("");
		Arrays.sort(entries, new Comparator() {

			public int compare(Object obj1, Object obj2) {
				// TODO Auto-generated method stub
				if(obj1 instanceof PropertySheetEntry && obj2 instanceof PropertySheetEntry  ){
					PropertySheetEntry propery1 = (PropertySheetEntry)obj1;
					PropertySheetEntry propery2 = (PropertySheetEntry)obj2;
					String proName1 = propery1.getDisplayName();
					String proName2 = propery2.getDisplayName();
					int proIndex1 = -1;
					int proIndex2 = -1;
					if(proName1.equals(Property.ID_NAME)){
						proIndex1 = 0;
					}else if(proName1.equals(Property.ID_STEREO)){
						proIndex1 = 1;
					}else if(proName1.equals(Property.ID_TYPE)){
						proIndex1 = 2;
					}

					if(proName2.equals(Property.ID_NAME)){
						proIndex2 = 0;
					}else if(proName2.equals(Property.ID_STEREO)){
						proIndex2 = 1;
					}else if(proName2.equals(Property.ID_TYPE)){
						proIndex2 = 2;
					}
					if(proIndex1<=proIndex2){
						return -1;
					}else{
						return 1;
					}
				}
				return -1;
			}

		});

	}

}
