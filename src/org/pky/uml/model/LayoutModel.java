package org.pky.uml.model;

import java.io.File;
import java.util.ArrayList;

import org.eclipse.draw2d.geometry.Rectangle;
import org.pky.uml.browser.common.propertybrowser.Property;
import org.pky.uml.commons.managers.LayoutManager;
import org.pky.uml.commons.managers.ProjectManager;
import org.pky.uml.model.action.ActionAutoMoveActionItem;
import org.pky.uml.model.action.ActionItem;
import org.pky.uml.model.action.ActionMobileServiceCallItem;
import org.pky.uml.model.common.UMLModel;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class LayoutModel extends UMLModel {

	public static int DISPLAY_X= 27;
	public static int DISPLAY_Y= 91;
	public static int DISPLAY_WIDTH= 361;
	public static int DISPLAY_HEIGHT= 617;

	public static int DEFAULT_WIDTH = 415;
	public static int DEFAULT_HEIGHT = 795;

	public LayoutModel(){
		super(DEFAULT_WIDTH,DEFAULT_HEIGHT);
		setLocation(0, 0);

		if(ProjectManager.getInstance().getModels(ProjectManager.getInstance().getModelType("RelativeLayout")).size()==0){
			this.setPropertyValue(Property.ID_MAIN, 1);
		}


		//		addChild(new TitleBarModel());
	}	

	@Override
	public void writeLayoutIOS() {
		LayoutManager.getInstance().setType(LayoutManager.IOS);
		LayoutManager.getInstance().setSourceModel(this);
		OperationItem operationItem = ProjectManager.getInstance().getNullCreateOperation(this, Property.OPERATION_VOID_LOADVIEW);
		int isTitleBar = (Integer)getPropertyValue(Property.ID_TITLE_MAIN);
		if(!getViewModel().getPropertyValue(Property.ID_TEXT).equals("")){
			operationItem.getActionDetailList().add(ProjectManager.getInstance().addSourceLine("[[self navigationItem] setTitle:@"+LayoutManager.QUO_WORLD+(String)getViewModel().getPropertyValue(Property.ID_TEXT)+LayoutManager.QUO_WORLD+LayoutManager.SQUARE_BRACKET_END_KEY_TOKEN+LayoutManager.END_MARK_TOKEN+LayoutManager.ENTER_TOKEN));
		}
		LayoutManager.getInstance().addOperationMap(this, operationItem);


		LayoutManager.getInstance().writeAutoMoveLayoutIOS(this);//화면이동

		LayoutManager.getInstance().writeBackgroundImageIOS(this);//배경화면 

		
		
		LayoutManager.getInstance().addOperationMap(this, ProjectManager.getInstance().getNullCreateOperation(this, Property.OPERATION_ID_INITWITHNIBNAME));


		ArrayList<UMLModel> list = ProjectManager.getInstance().getLayoutSortList(ProjectManager.getInstance().getAeroUMLModel(this.getParentModel()));

		for(int i = 0 ; i < list.size(); i++){
			list.get(i).writeLayoutIOS();

		}

	}


	public String writeSourceIOSH() {
		ArrayList<UMLModel> list = ProjectManager.getInstance().getLayoutSortList(ProjectManager.getInstance().getAeroUMLModel(this.getParentModel()));

		boolean isListView = false;
		boolean isWebView = false;
		boolean isMessage = false;
		boolean isViedo = false;
		for(int i = 0 ; i < list.size(); i ++){
			if(list.get(i).getBasicModel() instanceof ListViewModel){
				isListView  = true;
			}else if(list.get(i).getBasicModel() instanceof WebViewModel){
				isWebView = true;
			}else if(list.get(i).getBasicModel() instanceof VideoModel){
				isViedo = true;
			}
			if(list.get(i).getPropertyValue(Property.ID_ACTION_ITEM)!=null){
				ArrayList<ActionItem> actionList = (ArrayList)list.get(i).getPropertyValue(Property.ID_ACTION_ITEM);
				for(int k = 0 ; k < actionList.size(); k++){
					if(actionList.get(k).get(Property.ACTION_MOBILE_SERVICE_CALL)!=null && actionList.get(k).get(Property.ACTION_MOBILE_SERVICE_CALL) instanceof ActionMobileServiceCallItem){
						ActionMobileServiceCallItem actionMobileServiceCallItem = (ActionMobileServiceCallItem)actionList.get(k).get(Property.ACTION_MOBILE_SERVICE_CALL);
						if(actionMobileServiceCallItem.getId().equals(Property.ACTION_MOBILE_SERVICE_CALL_MESSAGE)){
							isMessage = true;
						}
					}

				}

			}
		}

		StringBuffer buffer = new StringBuffer();
		buffer.append(LayoutManager.IMPORT_IOS_KEY_WORLD+LayoutManager.SPACE_TOKEN+LayoutManager.ANGLE_BRACKET_START_KEY_TOKEN+"UIKit/UIKit.h"+LayoutManager.ANGLE_BRACKET_END_KEY_TOKEN+LayoutManager.ENTER_TOKEN);
		if(isViedo){
			buffer.append(LayoutManager.IMPORT_IOS_KEY_WORLD+LayoutManager.SPACE_TOKEN+LayoutManager.ANGLE_BRACKET_START_KEY_TOKEN+"MediaPlayer/MediaPlayer.h"+LayoutManager.ANGLE_BRACKET_END_KEY_TOKEN+LayoutManager.ENTER_TOKEN);
		}

		if(isMessage)
			buffer.append(LayoutManager.IMPORT_IOS_KEY_WORLD+LayoutManager.SPACE_TOKEN+LayoutManager.ANGLE_BRACKET_START_KEY_TOKEN+"MessageUI/MessageUI.h"+LayoutManager.ANGLE_BRACKET_END_KEY_TOKEN+LayoutManager.ENTER_TOKEN);
		buffer.append(LayoutManager.INTERFACE_IOS_KEY_WORLD+LayoutManager.SPACE_TOKEN+getName()+LayoutManager.SPACE_TOKEN+LayoutManager.COLON_KEY_TOKEN+LayoutManager.SPACE_TOKEN+"UIViewController");

		ArrayList<String> delegate = new ArrayList();

		if(isListView){
			delegate.add("UITableViewDelegate");
			delegate.add("UITableViewDataSource");
		}
		if(isWebView){
			delegate.add("UIWebViewDelegate");
		}
		if(isMessage){
			delegate.add("MFMessageComposeViewControllerDelegate");
		}
		if(delegate.size()>0){
			buffer.append(LayoutManager.ANGLE_BRACKET_START_KEY_TOKEN);
		}
		for(int i = 0; i<delegate.size(); i ++){
			if(i>0)
				buffer.append(",");
			buffer.append(delegate.get(i));

		}
		if(delegate.size()>0){
			buffer.append(LayoutManager.ANGLE_BRACKET_END_KEY_TOKEN);
		}

		buffer.append(LayoutManager.BLOCK_START_TOKEN+LayoutManager.ENTER_TOKEN);

		for(int i = 0 ; i < list.size(); i ++){
			String classType = ProjectManager.getInstance().getModelIOSType(ProjectManager.getInstance().getModelType(list.get(i)));
			if(!classType.equals(""))
				buffer.append(classType+LayoutManager.ASTERISK_KEY_TOKEN+LayoutManager.SPACE_TOKEN+list.get(i).getName()+LayoutManager.END_MARK_TOKEN+LayoutManager.ENTER_TOKEN);
		}
		buffer.append(LayoutManager.BLOCK_END_TOKEN+LayoutManager.ENTER_TOKEN);
		for(int i = 0 ; i < list.size(); i ++){
			String classType = ProjectManager.getInstance().getModelIOSType(ProjectManager.getInstance().getModelType(list.get(i)));
			buffer.append(LayoutManager.PROPERTY_KEY_WORLD+LayoutManager.SPACE_TOKEN+LayoutManager.NONATOMIC_STRONG_IOS_KEY_WORLD+LayoutManager.SPACE_TOKEN+classType+LayoutManager.ASTERISK_KEY_TOKEN+LayoutManager.SPACE_TOKEN+list.get(i).getName()+LayoutManager.END_MARK_TOKEN+LayoutManager.ENTER_TOKEN);
		}


		buffer.append(LayoutManager.END_IOS_KEY_WORLD+LayoutManager.ENTER_TOKEN);

		return buffer.toString();
	}
	public String writeSourceIOSM() {

		ArrayList<UMLModel> list = ProjectManager.getInstance().getLayoutSortList(ProjectManager.getInstance().getAeroUMLModel(this.getParentModel()));
		ArrayList<UMLModel> layoutList = ProjectManager.getInstance().getModels(2);
		StringBuffer buffer = new StringBuffer();



		buffer.append(LayoutManager.IMPORT_IOS_KEY_WORLD+LayoutManager.SPACE_TOKEN+LayoutManager.QUO_WORLD+getName()+LayoutManager.DOT_WORLD+"h"+LayoutManager.QUO_WORLD+LayoutManager.ENTER_TOKEN);
		for(int i = 0; i < layoutList.size(); i++){
			if(layoutList.get(i).getBasicModel()!=this)
				buffer.append(LayoutManager.IMPORT_IOS_KEY_WORLD+LayoutManager.SPACE_TOKEN+LayoutManager.QUO_WORLD+layoutList.get(i).getName()+LayoutManager.DOT_WORLD+"h"+LayoutManager.QUO_WORLD+LayoutManager.ENTER_TOKEN);	
		}
		buffer.append(LayoutManager.INTERFACE_IOS_KEY_WORLD+LayoutManager.SPACE_TOKEN+getName()+LayoutManager.FUNCTION_START_TOKEN+LayoutManager.FUNCTION_END_TOKEN+LayoutManager.ENTER_TOKEN);
		buffer.append(LayoutManager.END_IOS_KEY_WORLD+LayoutManager.ENTER_TOKEN);
		buffer.append(LayoutManager.IMPLEMENTATION_IOS_KEY_WORLD+LayoutManager.SPACE_TOKEN+getName()+LayoutManager.ENTER_TOKEN);
		for(int i = 0 ; i < list.size(); i ++){
			buffer.append(LayoutManager.SYNTHESIZE_IOS_KEY_WORLD + LayoutManager.SPACE_TOKEN + list.get(i).getName() +LayoutManager.END_MARK_TOKEN+LayoutManager.ENTER_TOKEN );
		}



		ArrayList<OperationItem> operations = LayoutManager.getInstance().getOperationMap(this);
		for(int i = 0 ; i < operations.size(); i ++){
			buffer.append(operations.get(i).writeSourceIOS());
		}




		buffer.append(LayoutManager.END_IOS_KEY_WORLD+LayoutManager.ENTER_TOKEN);

		return buffer.toString();
	}

	@Override
	public String writeLayoutAndroid() {
		StringBuffer buffer = new StringBuffer();
		buffer.append(LayoutManager.PROTECTED_KEY_WORLD+LayoutManager.SPACE_TOKEN+LayoutManager.VOID_KEY_WORLD+LayoutManager.SPACE_TOKEN+"init_Layout"+LayoutManager.FUNCTION_START_TOKEN+LayoutManager.FUNCTION_END_TOKEN+LayoutManager.SPACE_TOKEN+LayoutManager.BLOCK_START_TOKEN+LayoutManager.ENTER_TOKEN);
		
		buffer.append("WindowManager"+LayoutManager.SPACE_TOKEN+"windowmanager"+LayoutManager.SPACE_TOKEN+LayoutManager.EQUALS_WORLD+LayoutManager.FUNCTION_START_TOKEN+"WindowManager"+LayoutManager.FUNCTION_END_TOKEN+LayoutManager.SPACE_TOKEN+"getSystemService"+LayoutManager.FUNCTION_START_TOKEN+"WINDOW_SERVICE"+LayoutManager.FUNCTION_END_TOKEN+LayoutManager.END_MARK_TOKEN+LayoutManager.ENTER_TOKEN);
		buffer.append("Display"+LayoutManager.SPACE_TOKEN+"disp"+LayoutManager.SPACE_TOKEN+LayoutManager.EQUALS_WORLD+"windowmanager"+LayoutManager.DOT_WORLD+"getDefaultDisplay"+LayoutManager.FUNCTION_START_TOKEN+LayoutManager.FUNCTION_END_TOKEN+LayoutManager.END_MARK_TOKEN+LayoutManager.ENTER_TOKEN);
		buffer.append("int"+LayoutManager.SPACE_TOKEN+"screenWidth"+LayoutManager.SPACE_TOKEN+LayoutManager.EQUALS_WORLD+"disp"+LayoutManager.DOT_WORLD+"getWidth"+LayoutManager.FUNCTION_START_TOKEN+LayoutManager.FUNCTION_END_TOKEN+LayoutManager.END_MARK_TOKEN+LayoutManager.ENTER_TOKEN);
		buffer.append("int"+LayoutManager.SPACE_TOKEN+"screenHeight"+LayoutManager.SPACE_TOKEN+LayoutManager.EQUALS_WORLD+"disp"+LayoutManager.DOT_WORLD+"getHeight"+LayoutManager.FUNCTION_START_TOKEN+LayoutManager.FUNCTION_END_TOKEN+LayoutManager.END_MARK_TOKEN+LayoutManager.ENTER_TOKEN);
		
		
		buffer.append("RelativeLayout"+LayoutManager.DOT_WORLD+"LayoutParams"+LayoutManager.SPACE_TOKEN+"params"+LayoutManager.END_MARK_TOKEN+LayoutManager.ENTER_TOKEN);
		
		ArrayList<UMLModel> list = ProjectManager.getInstance().getLayoutSortList(ProjectManager.getInstance().getAeroUMLModel(this.getParentModel()));

		for(int i = 0 ; i < list.size(); i ++){
			buffer.append(list.get(i).writeLayoutAndroid());
		}

		buffer.append(LayoutManager.BLOCK_END_TOKEN+LayoutManager.ENTER_TOKEN);
		return buffer.toString();

	}

	public Element writeLayoutAndroid(Document doc) {


		Element layout = doc.createElement("RelativeLayout");

		layout.setAttribute("xmlns:android", "http://schemas.android.com/apk/res/android");
		layout.setAttribute("xmlns:tools", "http://schemas.android.com/tools");
		layout.setAttribute("android:layout_width", "fill_parent");
		layout.setAttribute("android:layout_height", "fill_parent");

		ArrayList<UMLModel> list = ProjectManager.getInstance().getLayoutSortList(ProjectManager.getInstance().getAeroUMLModel(this.getParentModel()));

		for(int i = 0 ; i < list.size(); i ++){
			layout.appendChild(list.get(i).writeXMLLayoutAndroid(doc));
		}

		//배경화면 
		if(getPropertyValue(Property.ID_IMG)!=null){
			String imageFile = (String)getPropertyValue(Property.ID_IMG);
			File backgroundFile = new File(imageFile);
			if(backgroundFile.exists()&& backgroundFile.isFile()){
				LayoutManager.getInstance().copyFile(backgroundFile.getPath(), ProjectManager.getInstance().getAndroidPath()+LayoutManager.SEPARATOR+LayoutManager.ANDROID_RES_DRAWABLE_HDPI_PATH+LayoutManager.SEPARATOR+backgroundFile.getName());
//				backgroundFile = LayoutManager.getInstance().reNameMedia(ProjectManager.getInstance().getAndroidPath()+LayoutManager.SEPARATOR+LayoutManager.ANDROID_RES_DRAWABLE_HDPI_PATH+LayoutManager.SEPARATOR+backgroundFile.getName());
				layout.setAttribute("android:background", "@drawable/"+LayoutManager.getInstance().fileName(backgroundFile));
			}



		}
		return layout;

	}


	public String writeSourceAndroid(){
		ArrayList<UMLModel> list = ProjectManager.getInstance().getAeroUMLModel(this.getParentModel());
		boolean isGoogleMap = false;
		boolean isFragment = false;
		boolean isImageModel = false;
		boolean isWebView = false;
		boolean isViedo = false;
		String extendClass = "Activity";
		for(int i = 0 ; i < list.size(); i ++){
			if(list.get(i).getBasicModel() instanceof GoogleMapModel){
				extendClass = "FragmentActivity";
				isGoogleMap = true;
				isFragment = true;

			}else if(list.get(i).getBasicModel() instanceof ImageViewModel){
				isImageModel = true;
			}else if(list.get(i).getBasicModel() instanceof WebViewModel || list.get(i).getBasicModel() instanceof YoutubeModel){
				isWebView = true;
			}else if(list.get(i).getBasicModel() instanceof VideoModel){
				isViedo = true;
			}
		}


		StringBuffer buffer = new StringBuffer();
		buffer.append(LayoutManager.PACKAGE_KEY_WORLD+LayoutManager.SPACE_TOKEN+ProjectManager.getInstance().getAndroidPackage()+LayoutManager.DOT_WORLD+this.getViewModel().getPackage()+LayoutManager.END_MARK_TOKEN+LayoutManager.ENTER_TOKEN);
		buffer.append(LayoutManager.EN);

		buffer.append("import android.app.Activity"+LayoutManager.END_MARK_TOKEN+LayoutManager.ENTER_TOKEN);
		buffer.append("import android.content.Intent"+LayoutManager.END_MARK_TOKEN+LayoutManager.ENTER_TOKEN);
		buffer.append("import android.os.Bundle"+LayoutManager.END_MARK_TOKEN+LayoutManager.ENTER_TOKEN);
		buffer.append("import android.app.AlertDialog"+LayoutManager.END_MARK_TOKEN+LayoutManager.ENTER_TOKEN);
		buffer.append("import android.view.*"+LayoutManager.END_MARK_TOKEN+LayoutManager.ENTER_TOKEN);
		buffer.append("import android.view.View.*"+LayoutManager.END_MARK_TOKEN+LayoutManager.ENTER_TOKEN);
		buffer.append("import android.view.WindowManager"+LayoutManager.END_MARK_TOKEN+LayoutManager.ENTER_TOKEN);
		buffer.append("import android.view.Display"+LayoutManager.END_MARK_TOKEN+LayoutManager.ENTER_TOKEN);
		buffer.append("import android.widget.*"+LayoutManager.END_MARK_TOKEN+LayoutManager.ENTER_TOKEN);
		buffer.append("import android.widget.AdapterView.*"+LayoutManager.END_MARK_TOKEN+LayoutManager.ENTER_TOKEN);
		buffer.append("import android.widget.RelativeLayout.*"+LayoutManager.END_MARK_TOKEN+LayoutManager.ENTER_TOKEN);
		buffer.append("import android.content.DialogInterface"+LayoutManager.END_MARK_TOKEN+LayoutManager.ENTER_TOKEN);
		buffer.append("import android.util.TypedValue"+LayoutManager.END_MARK_TOKEN+LayoutManager.ENTER_TOKEN);
		buffer.append("import android.net.Uri"+LayoutManager.END_MARK_TOKEN+LayoutManager.ENTER_TOKEN);
		buffer.append("import android.graphics.Color"+LayoutManager.END_MARK_TOKEN+LayoutManager.ENTER_TOKEN);
		
		
		
		if(isWebView){
			buffer.append("import android.webkit.WebView"+LayoutManager.END_MARK_TOKEN+LayoutManager.ENTER_TOKEN);
			buffer.append("import android.webkit.WebViewClient"+LayoutManager.END_MARK_TOKEN+LayoutManager.ENTER_TOKEN);
		}
		if(isImageModel){
			buffer.append("import android.graphics.Bitmap"+LayoutManager.END_MARK_TOKEN+LayoutManager.ENTER_TOKEN);
			buffer.append("import android.graphics.BitmapFactory"+LayoutManager.END_MARK_TOKEN+LayoutManager.ENTER_TOKEN);
		}
		if(isGoogleMap){
			buffer.append("import com.google.android.gms.maps.GoogleMap"+LayoutManager.END_MARK_TOKEN+LayoutManager.ENTER_TOKEN);
			buffer.append("import com.google.android.gms.maps.SupportMapFragment"+LayoutManager.END_MARK_TOKEN+LayoutManager.ENTER_TOKEN);
		}
		if(isFragment){
			buffer.append("import android.support.v4.app.FragmentActivity"+LayoutManager.END_MARK_TOKEN+LayoutManager.ENTER_TOKEN);
		}

		if(isViedo){
			buffer.append("import android.net.Uri"+LayoutManager.END_MARK_TOKEN+LayoutManager.ENTER_TOKEN);
			buffer.append("import android.widget.MediaController"+LayoutManager.END_MARK_TOKEN+LayoutManager.ENTER_TOKEN);
		}

		buffer.append("import "+ProjectManager.getInstance().getAndroidPackage()+LayoutManager.DOT_WORLD+"*"+LayoutManager.END_MARK_TOKEN+LayoutManager.ENTER_TOKEN);

		if(getPropertyValue(Property.ID_AUTO_MOVE)!=null){
			String varName = LayoutManager.getInstance().getVarNumberCount("intent");
			ActionAutoMoveActionItem actionAutoMoveActionItem = (ActionAutoMoveActionItem)getPropertyValue(Property.ID_AUTO_MOVE);
			UMLModel model = ProjectManager.getInstance().getSearchID(actionAutoMoveActionItem.getLayoutID());
			if(model!=null){
				buffer.append("import"+LayoutManager.SPACE_TOKEN+ProjectManager.getInstance().getAndroidPackage()+LayoutManager.DOT_WORLD+model.getViewModel().getPackage()+LayoutManager.DOT_WORLD+"*"+LayoutManager.END_MARK_TOKEN+LayoutManager.ENTER_TOKEN);
			}
		}

		buffer.append(LayoutManager.ENTER_TOKEN);


		buffer.append(LayoutManager.PUBLIC_KEY_WORLD+LayoutManager.SPACE_TOKEN+LayoutManager.CLASS_KEY_WORLD+LayoutManager.SPACE_TOKEN+this.getName()+LayoutManager.SPACE_TOKEN+LayoutManager.EXTENDS__KEY_WORLD+LayoutManager.SPACE_TOKEN+extendClass+LayoutManager.BLOCK_START_TOKEN+LayoutManager.ENTER_TOKEN);
		for(int i = 0; i < list.size(); i++ ){
			String classType = ProjectManager.getInstance().getModelAndroidType(ProjectManager.getInstance().getModelType(list.get(i)));

			classType = classType.substring(0,1).toUpperCase() + classType.substring(1, classType.length()); 
			String name = list.get(i).getName();
			
			
			buffer.append(LayoutManager.PRIVATE_KEY_WORLD+LayoutManager.SPACE_TOKEN+classType+LayoutManager.SPACE_TOKEN+name+LayoutManager.END_MARK_TOKEN+LayoutManager.ENTER_TOKEN);
		}
		
		buffer.append(LayoutManager.ENTER_TOKEN);
		buffer.append(LayoutManager.ENTER_TOKEN);

		//		buffer.append(LayoutManager.OVERRIDE);


		buffer.append(LayoutManager.PROTECTED_KEY_WORLD+LayoutManager.SPACE_TOKEN+LayoutManager.VOID_KEY_WORLD+LayoutManager.SPACE_TOKEN+"onCreate"+LayoutManager.FUNCTION_START_TOKEN+"Bundle savedInstanceState"+LayoutManager.FUNCTION_END_TOKEN+LayoutManager.SPACE_TOKEN+LayoutManager.BLOCK_START_TOKEN+LayoutManager.ENTER_TOKEN);
		buffer.append("super.onCreate"+LayoutManager.FUNCTION_START_TOKEN+"savedInstanceState"+LayoutManager.FUNCTION_END_TOKEN+LayoutManager.END_MARK_TOKEN+LayoutManager.ENTER_TOKEN);
		//		buffer.append("requestWindowFeature"+LayoutManager.FUNCTION_START_TOKEN+"Window"+LayoutManager.DOT_WORLD+"FEATURE_CUSTOM_TITLE"+LayoutManager.FUNCTION_END_TOKEN+LayoutManager.END_MARK_TOKEN+LayoutManager.ENTER_TOKEN);



		buffer.append("setContentView"+LayoutManager.FUNCTION_START_TOKEN+LayoutManager.R_LAYOUT_WORD+LayoutManager.DOT_WORLD+this.getLayoutName()+LayoutManager.FUNCTION_END_TOKEN+LayoutManager.END_MARK_TOKEN+LayoutManager.ENTER_TOKEN);
		buffer.append("getWindow"+LayoutManager.FUNCTION_START_TOKEN+LayoutManager.FUNCTION_END_TOKEN+LayoutManager.DOT_WORLD+"setTitle"+LayoutManager.FUNCTION_START_TOKEN+LayoutManager.QUO_WORLD+getPropertyValue(Property.ID_TEXT)+LayoutManager.QUO_WORLD+LayoutManager.FUNCTION_END_TOKEN+LayoutManager.END_MARK_TOKEN+LayoutManager.ENTER_TOKEN);


		for(int i = 0; i < list.size(); i++ ){
			String classType = ProjectManager.getInstance().getModelAndroidType(ProjectManager.getInstance().getModelType(list.get(i)));

			classType = classType.substring(0,1).toUpperCase() + classType.substring(1, classType.length()); 
			String name = list.get(i).getName();
			if(list.get(i).getBasicModel() instanceof FragmentModel){
				buffer.append(name+LayoutManager.SPACE_TOKEN+LayoutManager.EQUALS_WORLD+LayoutManager.FUNCTION_START_TOKEN+LayoutManager.FUNCTION_START_TOKEN+LayoutManager.SUPPORT_MAP_FRAGMENT_WORD+LayoutManager.FUNCTION_END_TOKEN + LayoutManager.GET_SUPPORT_FRAGMENT_MANAGER_WORD + LayoutManager.FUNCTION_START_TOKEN+LayoutManager.FUNCTION_END_TOKEN + LayoutManager.DOT_WORLD + LayoutManager.FIND_FRAGMENT_BY_ID+LayoutManager.FUNCTION_START_TOKEN+LayoutManager.R_ID_WORD+LayoutManager.DOT_WORLD+name+LayoutManager.FUNCTION_END_TOKEN+LayoutManager.FUNCTION_END_TOKEN+LayoutManager.DOT_WORLD+LayoutManager.GET_MAP+LayoutManager.END_MARK_TOKEN+LayoutManager.ENTER_TOKEN);
			}else{
				buffer.append(name+LayoutManager.SPACE_TOKEN+LayoutManager.EQUALS_WORLD+LayoutManager.SPACE_TOKEN+LayoutManager.FUNCTION_START_TOKEN+classType+LayoutManager.FUNCTION_END_TOKEN+LayoutManager.R_FIND_VIEW_BY_ID+LayoutManager.FUNCTION_START_TOKEN+LayoutManager.R_ID_WORD+LayoutManager.DOT_WORLD+name+LayoutManager.FUNCTION_END_TOKEN+LayoutManager.END_MARK_TOKEN+LayoutManager.ENTER_TOKEN);	
			}
			buffer.append(list.get(i).writeSourceAndroid());

		}

		buffer.append(LayoutManager.getInstance().writeAutoMoveLayoutAndroid(this));

		buffer.append("init_Layout"+LayoutManager.FUNCTION_START_TOKEN+LayoutManager.FUNCTION_END_TOKEN+LayoutManager.END_MARK_TOKEN+LayoutManager.ENTER_TOKEN);
		buffer.append(LayoutManager.BLOCK_END_TOKEN+LayoutManager.ENTER_TOKEN);

		buffer.append(LayoutManager.PROTECTED_KEY_WORLD+LayoutManager.SPACE_TOKEN+LayoutManager.VOID_KEY_WORLD+LayoutManager.SPACE_TOKEN+"run"+LayoutManager.FUNCTION_START_TOKEN+LayoutManager.FUNCTION_END_TOKEN+LayoutManager.SPACE_TOKEN+LayoutManager.BLOCK_START_TOKEN+LayoutManager.ENTER_TOKEN);

		buffer.append(LayoutManager.BLOCK_END_TOKEN+LayoutManager.ENTER_TOKEN);
		
		buffer.append(writeLayoutAndroid());
		buffer.append(LayoutManager.BLOCK_END_TOKEN+LayoutManager.ENTER_TOKEN);


		return buffer.toString();
	}



	public Rectangle getDisplayRect(){

		Rectangle rect = getParentModel().getLocationRect();
		return new Rectangle(rect.x+DISPLAY_X,rect.y+DISPLAY_Y,DISPLAY_WIDTH,DISPLAY_HEIGHT);
	}

}
