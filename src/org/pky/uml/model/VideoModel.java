package org.pky.uml.model;

import java.io.File;

import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.swt.widgets.Layout;
import org.pky.uml.browser.common.propertybrowser.Property;
import org.pky.uml.commons.managers.LayoutManager;
import org.pky.uml.commons.managers.ProjectManager;
import org.pky.uml.model.common.UMLModel;

public class VideoModel extends UMLModel{
	public VideoModel() {
//		super(LayoutModel.DISPLAY_WIDTH,LayoutModel.DISPLAY_HEIGHT);
		super(100,100);
	}

	@Override
	public void setSize(Dimension size) {


		super.setSize(size);
	}
	@Override
	public String writeSourceAndroid() {
		StringBuffer str = new StringBuffer();
		str.append(super.writeSourceAndroid());
		File file = null;
		boolean isMovie = false;
		if(getPropertyValue(Property.ID_VIEDO_URL)!=null && !getPropertyValue(Property.ID_VIEDO_URL).equals("")){
			file = new File((String)getPropertyValue(Property.ID_VIEDO_URL));	

			if(file.exists()){
				LayoutManager.getInstance().copyFile(file.getPath(), ProjectManager.getInstance().getAndroidPath()+LayoutManager.SEPARATOR+LayoutManager.ANDROID_RES_RAW_PATH+LayoutManager.SEPARATOR+file.getName());
//				file = LayoutManager.getInstance().reNameMedia(ProjectManager.getInstance().getAndroidPath()+LayoutManager.SEPARATOR+LayoutManager.ANDROID_RES_RAW_PATH+LayoutManager.SEPARATOR+file.getName());

				isMovie = true;

			}		

		}else if(getPropertyValue(Property.ID_VIEDO_WEB_URL)!=null && !getPropertyValue(Property.ID_VIEDO_WEB_URL).equals("")){
			isMovie = true;
		}
		if(isMovie){
			String mediaController = LayoutManager.getInstance().getVarNumberCount("mediaController");
			str.append("MediaController"+LayoutManager.SPACE_TOKEN+mediaController+LayoutManager.SPACE_TOKEN+LayoutManager.EQUALS_WORLD+LayoutManager.SPACE_TOKEN+LayoutManager.NEW_WORLD+LayoutManager.SPACE_TOKEN+"MediaController"+LayoutManager.FUNCTION_START_TOKEN+"this"+LayoutManager.FUNCTION_END_TOKEN+LayoutManager.END_MARK_TOKEN+LayoutManager.ENTER_TOKEN);
			str.append(mediaController+LayoutManager.DOT_WORLD+"setAnchorView"+LayoutManager.FUNCTION_START_TOKEN+getName()+LayoutManager.FUNCTION_END_TOKEN+LayoutManager.END_MARK_TOKEN+LayoutManager.ENTER_TOKEN);
			str.append(getName()+LayoutManager.DOT_WORLD+"setMediaController"+LayoutManager.FUNCTION_START_TOKEN+mediaController+LayoutManager.FUNCTION_END_TOKEN+LayoutManager.END_MARK_TOKEN+LayoutManager.ENTER_TOKEN);

			//			str.append(getName()+LayoutManager.DOT_WORLD+"setVideoURI"+LayoutManager.FUNCTION_START_TOKEN+"Uri"+LayoutManager.DOT_WORLD+"parse"+LayoutManager.FUNCTION_START_TOKEN+LayoutManager.QUO_WORLD+"android.resource://"+LayoutManager.QUO_WORLD+"+getPackageName()+"+LayoutManager.QUO_WORLD+"/raw/"+file.getName()+LayoutManager.QUO_WORLD +LayoutManager.FUNCTION_END_TOKEN+LayoutManager.FUNCTION_END_TOKEN+LayoutManager.END_MARK_TOKEN+LayoutManager.ENTER_TOKEN);
			if(file!=null && file.exists()){
				str.append(getName()+LayoutManager.DOT_WORLD+"setVideoURI"+LayoutManager.FUNCTION_START_TOKEN+"Uri"+LayoutManager.DOT_WORLD+"parse"+LayoutManager.FUNCTION_START_TOKEN+LayoutManager.QUO_WORLD+"android.resource://"+LayoutManager.QUO_WORLD+"+getPackageName()+"+LayoutManager.QUO_WORLD+"/raw/"+file.getName().substring(0,file.getName().indexOf("."))+LayoutManager.QUO_WORLD +LayoutManager.FUNCTION_END_TOKEN+LayoutManager.FUNCTION_END_TOKEN+LayoutManager.END_MARK_TOKEN+LayoutManager.ENTER_TOKEN);
			}else{
				str.append(getName()+LayoutManager.DOT_WORLD+"setVideoURI"+LayoutManager.FUNCTION_START_TOKEN+"Uri"+LayoutManager.DOT_WORLD+"parse"+LayoutManager.FUNCTION_START_TOKEN+LayoutManager.QUO_WORLD+getPropertyValue(Property.ID_VIEDO_URL)+LayoutManager.QUO_WORLD +LayoutManager.FUNCTION_END_TOKEN+LayoutManager.FUNCTION_END_TOKEN+LayoutManager.END_MARK_TOKEN+LayoutManager.ENTER_TOKEN);	
			}
			str.append(getName()+LayoutManager.DOT_WORLD+"requestFocus"+LayoutManager.FUNCTION_START_TOKEN+LayoutManager.FUNCTION_END_TOKEN+LayoutManager.END_MARK_TOKEN+LayoutManager.ENTER_TOKEN);
			str.append(getName()+LayoutManager.DOT_WORLD+"start"+LayoutManager.FUNCTION_START_TOKEN+LayoutManager.FUNCTION_END_TOKEN+LayoutManager.END_MARK_TOKEN+LayoutManager.ENTER_TOKEN);

		}
		return str.toString();
	}
	@Override
	public void writeLayoutIOS() {
		// TODO Auto-generated method stub
		//		super.writeLayoutIOS();
		LayoutManager.getInstance().setSourceModel(this);
		OperationItem operationItem = ProjectManager.getInstance().getNullCreateOperation(this, Property.OPERATION_VOID_LOADVIEW);

		StringBuffer str = new StringBuffer();
		if(getPropertyValue(Property.ID_VIEDO_URL)!=null && !getPropertyValue(Property.ID_VIEDO_URL).equals("")){
			File file = new File((String)getPropertyValue(Property.ID_VIEDO_URL));
			if(file.exists()){
				LayoutManager.getInstance().copyFile(file.getPath(), ProjectManager.getInstance().getIosPath()+LayoutManager.SEPARATOR+LayoutManager.IOS_RESOURCES_PATH+LayoutManager.SEPARATOR+file.getName());
//				file = LayoutManager.getInstance().reNameMedia(ProjectManager.getInstance().getIosPath()+LayoutManager.SEPARATOR+LayoutManager.IOS_RESOURCES_PATH+LayoutManager.SEPARATOR+file.getName());
				
				str.append(getName()+LayoutManager.SPACE_TOKEN + LayoutManager.EQUALS_WORLD +LayoutManager.SPACE_TOKEN+
						LayoutManager.SQUARE_BRACKET_START_KEY_TOKEN+LayoutManager.SQUARE_BRACKET_START_KEY_TOKEN+ProjectManager.getInstance().getModelIOSType(ProjectManager.getInstance().getModelType(this))+LayoutManager.SPACE_TOKEN+"alloc"+
						LayoutManager.SQUARE_BRACKET_END_KEY_TOKEN+LayoutManager.SPACE_TOKEN+"initWithContentURL"+
						LayoutManager.COLON_KEY_TOKEN+LayoutManager.SQUARE_BRACKET_START_KEY_TOKEN+"NSURL"+LayoutManager.SPACE_TOKEN+"fileURLWithPath"+LayoutManager.COLON_KEY_TOKEN+
						LayoutManager.SQUARE_BRACKET_START_KEY_TOKEN+LayoutManager.SQUARE_BRACKET_START_KEY_TOKEN+"NSBundle"+LayoutManager.SPACE_TOKEN+"mainBundle"+
						LayoutManager.SQUARE_BRACKET_END_KEY_TOKEN+LayoutManager.SPACE_TOKEN+"pathForResource"+LayoutManager.COLON_KEY_TOKEN+"@"+LayoutManager.QUO_WORLD+LayoutManager.getInstance().fileName(file)+LayoutManager.QUO_WORLD+LayoutManager.SPACE_TOKEN+ "ofType"+LayoutManager.COLON_KEY_TOKEN+"@"+LayoutManager.QUO_WORLD + LayoutManager.getInstance().fileExt(file)+LayoutManager.QUO_WORLD +LayoutManager.SQUARE_BRACKET_END_KEY_TOKEN+LayoutManager.SQUARE_BRACKET_END_KEY_TOKEN+LayoutManager.SQUARE_BRACKET_END_KEY_TOKEN+LayoutManager.END_MARK_TOKEN+LayoutManager.ENTER_TOKEN);
				str.append(LayoutManager.SQUARE_BRACKET_START_KEY_TOKEN+LayoutManager.SQUARE_BRACKET_START_KEY_TOKEN+"NSNotificationCenter"+LayoutManager.SPACE_TOKEN+"defaultCenter"+LayoutManager.SQUARE_BRACKET_END_KEY_TOKEN+LayoutManager.SPACE_TOKEN+"addObserver:self selector:@selector(playbackDidFinish:)name:MPMoviePlayerPlaybackDidFinishNotification object:"+getName()+LayoutManager.SQUARE_BRACKET_END_KEY_TOKEN+LayoutManager.END_MARK_TOKEN+LayoutManager.ENTER_TOKEN);
				str.append(LayoutManager.SQUARE_BRACKET_START_KEY_TOKEN+"self"+LayoutManager.SPACE_TOKEN+"presentMoviePlayerViewControllerAnimated"+LayoutManager.COLON_KEY_TOKEN+getName()+LayoutManager.SQUARE_BRACKET_END_KEY_TOKEN+LayoutManager.END_MARK_TOKEN+LayoutManager.ENTER_TOKEN);
				str.append(LayoutManager.SQUARE_BRACKET_START_KEY_TOKEN+getName()+LayoutManager.SPACE_TOKEN+"release"+LayoutManager.SQUARE_BRACKET_END_KEY_TOKEN+LayoutManager.END_MARK_TOKEN+LayoutManager.ENTER_TOKEN);
				System.out.println("");
				operationItem.getActionDetailList().add(ProjectManager.getInstance().addSourceLine(str.toString()));
			}
			LayoutManager.getInstance().addOperationMap(this, operationItem);
			
			LayoutManager.getInstance().writeSourceIOS(this);
		}
	}

	

}
