package org.pky.uml.commons.managers;

import java.io.File;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.pky.uml.browser.common.propertybrowser.Property;
import org.pky.uml.model.common.UMLModel;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class AndroidManifestManager {

	public static AndroidManifestManager instance;

	private Element manifestElement;
	private Element uses_sdkElement;
	private Element uses_featureElement;
	private Element applicationElement;

	private ArrayList<Element> activityList = new ArrayList();

	private DocumentBuilderFactory dbFactory;
	private DocumentBuilder dBuilder;
	private Document doc;

	public static final String FILE_NAME = "AndroidManifest.xml";



	public AndroidManifestManager(){


	}

	public static AndroidManifestManager getInstance() {

		if (instance == null) {
			instance = new AndroidManifestManager();

			return instance;
		}
		else {
			return instance;
		}
	}

	private void initDoc(){

		dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder;
		try{
			dBuilder = dbFactory.newDocumentBuilder();
			doc = dBuilder.newDocument();





		}catch(Exception e){
			e.printStackTrace();
		}


	}


	public void makeFile(ArrayList list){
		try{

			initDoc();
			makeManifest();
			makeManifestApplication(list);// 엑티비티 추가 한다.
			StreamResult console = new StreamResult(System.out);
			StreamResult file = new StreamResult(new File(ProjectManager.getInstance().getAndroidPath()+LayoutManager.SEPARATOR+this.FILE_NAME));

			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
			DOMSource source = new DOMSource(doc);

			transformer.transform(source, console);
			transformer.transform(source, file);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	public void makeManifest(){

		manifestElement = doc.createElement("manifest");
		manifestElement.setAttribute("xmlns:android", "http://schemas.android.com/apk/res/android");



		manifestElement.setAttribute("package", ProjectManager.getInstance().getAndroidPackage());


		manifestElement.setAttribute("android:versionCode", ProjectManager.getInstance().getAndroid_versionCode());
		manifestElement.setAttribute("android:versionName", ProjectManager.getInstance().getAndroid_versionName());


		doc.appendChild(manifestElement);

		uses_sdkElement = doc.createElement("uses-sdk");
		uses_sdkElement.setAttribute("android:minSdkVersion",String.valueOf(ProjectManager.getInstance().getAndroid_MinSdkVersion()));//; 6
		uses_sdkElement.setAttribute("android:targetSdkVersion",String.valueOf(ProjectManager.getInstance().getAndroid_TargetSdkVersion()));// ; 16

		manifestElement.appendChild(uses_sdkElement);

		for(int i = 0 ; i < manifestElement.getChildNodes().getLength(); i ++){

			if(manifestElement.getChildNodes().item(i).getNodeName().equals("uses-permission")){
				manifestElement.removeChild(manifestElement.getChildNodes().item(i));
				i = 0;
			}else if(manifestElement.getChildNodes().item(i).getNodeName().equals("uses-feature")){
				manifestElement.removeChild(manifestElement.getChildNodes().item(i));
				i = 0;
			}else if(manifestElement.getChildNodes().item(i).getNodeName().equals("meta-data")){
				manifestElement.removeChild(manifestElement.getChildNodes().item(i));
				i = 0;
			}else if(manifestElement.getChildNodes().item(i).getNodeName().equals("permission")){
				manifestElement.removeChild(manifestElement.getChildNodes().item(i));
				i = 0;
			}else if(manifestElement.getChildNodes().item(i).getNodeName().equals("uses-library")){
				manifestElement.removeChild(manifestElement.getChildNodes().item(i));
				i = 0;
			}else if(manifestElement.getChildNodes().item(i).getNodeName().equals("application")){
				manifestElement.removeChild(manifestElement.getChildNodes().item(i));
				i = 0;
			}

		}

		ArrayList webViewList = ProjectManager.getInstance().getModels(ProjectManager.getInstance().getModelType("WebView"));
		ArrayList googleMapList = ProjectManager.getInstance().getModels(ProjectManager.getInstance().getModelType("GoogleMap"));
		




		Element uses_permission_write_external_storageElement = doc.createElement("uses-permission");
		uses_permission_write_external_storageElement.setAttribute("android:name", "android.permission.WRITE_EXTERNAL_STORAGE");
		manifestElement.appendChild(uses_permission_write_external_storageElement);

		Element uses_permission_access_coarse_locationelement = doc.createElement("uses-permission");
		uses_permission_access_coarse_locationelement.setAttribute("android:name", "android.permission.ACCESS_COARSE_LOCATION");
		manifestElement.appendChild(uses_permission_access_coarse_locationelement);

		Element uses_permission_access_fine_location = doc.createElement("uses-permission");
		uses_permission_access_fine_location.setAttribute("android:name", "android.permission.ACCESS_FINE_LOCATION");
		manifestElement.appendChild(uses_permission_access_fine_location);

		Element uses_permission_send_sms = doc.createElement("uses-permission");
		uses_permission_send_sms.setAttribute("android:name", "android.permission.SEND_SMS");
		manifestElement.appendChild(uses_permission_send_sms);

		Element uses_permission_receive_sms = doc.createElement("uses-permission");
		uses_permission_receive_sms.setAttribute("android:name", "android.permission.RECEIVE_SMS");
		manifestElement.appendChild(uses_permission_receive_sms);

		Element uses_permission_call_phone = doc.createElement("uses-permission");
		uses_permission_call_phone.setAttribute("android:name", "android.permission.CALL_PHONE");
		manifestElement.appendChild(uses_permission_call_phone);

		if(googleMapList.size()>0){
			Element uses_permission_read_gservices = doc.createElement("uses-permission");
			uses_permission_read_gservices.setAttribute("android:name", "com.google.android.providers.gsf.permission.READ_GSERVICES");
			manifestElement.appendChild(uses_permission_read_gservices);

			Element permissionElement = doc.createElement("permission");
			permissionElement.setAttribute("android:protectionLevel", "signature");
			permissionElement.setAttribute("android:name", ProjectManager.getInstance().getAndroidPackage()+".permission.MAPS_RECEIVE");
			manifestElement.appendChild(permissionElement);


			Element uses_feature = doc.createElement("uses-feature");
			uses_feature.setAttribute("android:glEsVersion", "0x00020000");
			uses_feature.setAttribute("android:required", "true");
			manifestElement.appendChild(uses_feature);


		}

//		if(webViewList.size()>0 || googleMapList.size()>0){
			Element uses_permission_internet = doc.createElement("uses-permission");
			uses_permission_internet.setAttribute("android:name", "android.permission.INTERNET");
			manifestElement.appendChild(uses_permission_internet);

//		}






	}
	public void makeManifestApplication(ArrayList list){


		ArrayList googleMapList = ProjectManager.getInstance().getModels(ProjectManager.getInstance().getModelType("GoogleMap"));

		applicationElement = doc.createElement("application");
		applicationElement.setAttribute("android:allowBackup", ProjectManager.getInstance().getAndroid_allowBackup());
		applicationElement.setAttribute("android:theme", "@android:style/android:Theme.Light");
		if(ProjectManager.getInstance().getAndroid_icon()!=null)
			applicationElement.setAttribute("android:icon", "@drawable/"+ProjectManager.getInstance().getAndroid_icon().substring(0,ProjectManager.getInstance().getAndroid_icon().lastIndexOf(".")));

		manifestElement.appendChild(applicationElement);

		if(googleMapList.size()>0){
			Element user_lib = doc.createElement("uses-library");
			user_lib.setAttribute("android:name", "com.google.android.maps");
			applicationElement.appendChild(user_lib);
		}



		for(int i = 0; i < list.size(); i ++){
			UMLModel umlModel = (UMLModel)list.get(i);
			Element activityElement = doc.createElement("activity");

			activityElement.setAttribute("android:name", umlModel.getAndroidPackageName());
			activityElement.setAttribute("android:label", ((String)umlModel.getPropertyValue(Property.ID_TEXT)).equals("")?umlModel.getName():(String)umlModel.getPropertyValue(Property.ID_TEXT));
			if(umlModel.getPropertyValue(Property.ID_TITLE_MAIN).equals(0))
				activityElement.setAttribute("android:theme", "@android:style/android:Theme.Light.NoTitleBar");
			activityElement.setAttribute("android:screenOrientation", "portrait");
			
			if(!umlModel.getPropertyValue(Property.ID_MAIN).equals(0)){
				Element intent_filter = doc.createElement("intent-filter");

				Element actionElement = doc.createElement("action");
				actionElement.setAttribute("android:name", "android.intent.action.MAIN");

				Element category = doc.createElement("category");
				category.setAttribute("android:name", "android.intent.category.LAUNCHER");

				intent_filter.appendChild(actionElement);
				intent_filter.appendChild(category);

				activityElement.appendChild(intent_filter);
			}

			applicationElement.appendChild(activityElement);
		}


		if( googleMapList.size()>0){
			Element meta_dataElement = doc.createElement("meta-data");
			meta_dataElement.setAttribute("android:value", "AIzaSyAvjw17_kghsplU1xsFgje-hyQWlF5MkSQ");
			meta_dataElement.setAttribute("android:name", "com.google.android.maps.v2.API_KEY");
			applicationElement.appendChild(meta_dataElement);
		}
	}

	public String getPath(){
		return ProjectManager.getInstance().getAndroidPath()+LayoutManager.SEPARATOR+this.FILE_NAME;
	}

}
