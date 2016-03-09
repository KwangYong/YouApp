package org.pky.uml.model;

import java.io.File;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.ImageLoader;
import org.pky.uml.browser.common.propertybrowser.Property;
import org.pky.uml.commons.managers.LayoutManager;
import org.pky.uml.commons.managers.ProjectManager;
import org.pky.uml.model.common.UMLModel;

public class ImageViewModel extends UMLModel{

	public ImageViewModel() {
		super(150,150);
	}
	@Override
	public void writeLayoutIOS() {
		//		super.writeLayoutIOS();
		LayoutManager.getInstance().setSourceModel(this);

		

		OperationItem operationItem = ProjectManager.getInstance().getNullCreateOperation(this, Property.OPERATION_VOID_LOADVIEW);

		StringBuffer str = new StringBuffer();

		String[] imageSplit = ProjectManager.getInstance().getLayoutIOSLocation(this).split(",");

		str.append(getName()+LayoutManager.EQUALS_WORLD+LayoutManager.SPACE_TOKEN+LayoutManager.SQUARE_BRACKET_START_KEY_TOKEN+LayoutManager.SQUARE_BRACKET_START_KEY_TOKEN+ProjectManager.getInstance().getModelIOSType(ProjectManager.getInstance().getModelType(this))+LayoutManager.SPACE_TOKEN +"alloc"+LayoutManager.SQUARE_BRACKET_END_KEY_TOKEN+"initWithFrame"+LayoutManager.COLON_KEY_TOKEN+"CGRectMake"+LayoutManager.FUNCTION_START_TOKEN+"0"+LayoutManager.COMMAMA_WORLD+"0"+LayoutManager.COMMAMA_WORLD+imageSplit[2]+LayoutManager.COMMAMA_WORLD+imageSplit[3]+LayoutManager.FUNCTION_END_TOKEN+LayoutManager.SQUARE_BRACKET_END_KEY_TOKEN+LayoutManager.END_MARK_TOKEN+LayoutManager.ENTER_TOKEN);

		File image = new File((String)getPropertyValue(Property.ID_IMG));
		if(image.exists()){
			LayoutManager.getInstance().copyFile(image.getPath(), ProjectManager.getInstance().getIosPath()+LayoutManager.SEPARATOR+LayoutManager.IOS_IMAGES_PATH+LayoutManager.SEPARATOR+image.getName());
			str.append(getName() + LayoutManager.DOT_WORLD+"image" +LayoutManager.SPACE_TOKEN + "= [UIImage imageNamed:@"+LayoutManager.QUO_WORLD+image.getName()+LayoutManager.QUO_WORLD+LayoutManager.SQUARE_BRACKET_END_KEY_TOKEN+LayoutManager.END_MARK_TOKEN+LayoutManager.ENTER_TOKEN);
			String mask = getName()+"Mask";
			str.append("UIControl * "+mask+" = [[[UIControl alloc] initWithFrame:"+"CGRectMake"+LayoutManager.FUNCTION_START_TOKEN+ProjectManager.getInstance().getLayoutIOSLocation(this)+LayoutManager.FUNCTION_END_TOKEN+"] autorelease];"+LayoutManager.ENTER_TOKEN);
			str.append("["+mask+" addSubview:"+getName()+"];"+LayoutManager.ENTER_TOKEN);
			str.append("[scrollView addSubview: "+mask+"];"+LayoutManager.ENTER_TOKEN);
			operationItem.getActionDetailList().add(ProjectManager.getInstance().addSourceLine(str.toString()));
		}
		LayoutManager.getInstance().addOperationMap(this, operationItem);

		LayoutManager.getInstance().writeSourceIOS(this);


	}

	public String writeSourceAndroid() {

		StringBuffer str = new StringBuffer();


		File image = new File((String)getPropertyValue(Property.ID_IMG));	
		if(image.exists() && image.isFile()){
			LayoutManager.getInstance().copyFile(image.getPath(), ProjectManager.getInstance().getAndroidPath()+LayoutManager.SEPARATOR+LayoutManager.ANDROID_RES_DRAWABLE_HDPI_PATH+LayoutManager.SEPARATOR+image.getName());
//			image = LayoutManager.getInstance().reNameMedia(ProjectManager.getInstance().getAndroidPath()+LayoutManager.SEPARATOR+LayoutManager.ANDROID_RES_DRAWABLE_HDPI_PATH+LayoutManager.SEPARATOR+image.getName());
			str.append(this.getName()+LayoutManager.DOT_WORLD+"setImageBitmap"+LayoutManager.FUNCTION_START_TOKEN+LayoutManager.APPLICATION_ANDROID_UTIL_JAVA_PATH+LayoutManager.DOT_WORLD+"loadBackgroundBitmap"+LayoutManager.FUNCTION_START_TOKEN+"this"+LayoutManager.COMMAMA_WORLD+"R.drawable."+LayoutManager.getInstance().fileName(image)+LayoutManager.COMMAMA_WORLD+this.getViewModel().getSize().width()+LayoutManager.COMMAMA_WORLD+this.getViewModel().getSize().height()+LayoutManager.FUNCTION_END_TOKEN+LayoutManager.FUNCTION_END_TOKEN+LayoutManager.END_MARK_TOKEN+LayoutManager.ENTER_TOKEN);
		}





		str.append(super.writeSourceAndroid());
		return str.toString();
	}
}
