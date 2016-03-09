package org.pky.uml.browser;

import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.internal.views.ViewsPlugin;
import org.eclipse.ui.part.IPage;
import org.eclipse.ui.part.IPageBookViewPage;
import org.eclipse.ui.part.PageBook;
import org.eclipse.ui.views.properties.IPropertySheetPage;
import org.eclipse.ui.views.properties.PropertySheet;
import org.pky.uml.browser.common.propertybrowser.PropertyPage;


public class PropertyBrowser extends PropertySheet{
	public PropertyBrowser() {

		super();

	}

	protected IPage createDefaultPage(PageBook book) {
		PropertyPage page = new PropertyPage();

		initPage(page);
		page.createControl(book);
		return page;

	}
	protected PageRec doCreatePage(IWorkbenchPart part) {
		// Try to get a custom property sheet page.
		IPropertySheetPage page = (IPropertySheetPage) ViewsPlugin.getAdapter(part,
				IPropertySheetPage.class, false);
		if (page != null) {
			if (page instanceof IPageBookViewPage) {
				initPage((IPageBookViewPage) page);
			}
			page.createControl(getPageBook());
			page = (IPropertySheetPage) createDefaultPage(getPageBook());
			return new PageRec(part, page);
		}

		return null;
	}
	@Override
	public void setPartProperty(String key, String value) {
		// TODO Auto-generated method stub
		super.setPartProperty(key, value);
	}
}