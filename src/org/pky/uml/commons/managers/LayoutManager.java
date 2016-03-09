package org.pky.uml.commons.managers;

import java.awt.image.BufferedImage;
import java.awt.image.PixelGrabber;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.eclipse.draw2d.geometry.Point;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.pky.uml.browser.common.propertybrowser.Property;
import org.pky.uml.model.EditTextModel;
import org.pky.uml.model.ImageViewModel;
import org.pky.uml.model.InterfaceItem;
import org.pky.uml.model.LayoutModel;
import org.pky.uml.model.ListViewModel;
import org.pky.uml.model.OperationItem;
import org.pky.uml.model.ParameterItem;
import org.pky.uml.model.UMLDiagramModel;
import org.pky.uml.model.WebViewModel;
import org.pky.uml.model.YoutubeModel;
import org.pky.uml.model.action.ActionAlertMessageItem;
import org.pky.uml.model.action.ActionAutoMoveActionItem;
import org.pky.uml.model.action.ActionItem;
import org.pky.uml.model.action.ActionMobileServiceCallItem;
import org.pky.uml.model.action.ActionMoveItem;
import org.pky.uml.model.action.ListViewerItem;
import org.pky.uml.model.common.UMLModel;
import org.w3c.dom.Document;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class LayoutManager {
	public final static int ANDROID = 1;
	public final static int IOS = 2;
	public final static String EN  = "\n";
	public final static String SM  = ";\n";
	public final static String OVERRIDE  = "@Override"+EN;

	public final static String SEPARATOR  = "/";

	public final static String APPLICATION_CONFIG_PATH = "config";
	public final static String APPLICATION_JAVA_PATH = "java";
	public final static String APPLICATION_ANDROID_UTIL_JAVA_PATH = "AndroidUtil";
	public final static String APPLICATION_IMGVIEW_TOUCH_JAVA_PATH = "ImgViewTouch";

	public final static String ANDROID_EXT_PATH = "ext";
	public final static String ANDROID_SRC_PATH = "src";
	public final static String ANDROID_GEN_PATH = "gen";
	public final static String ANDROID_BIN_PATH = "bin";
	public final static String ANDROID_BIN_CLASSES = "bin"+SEPARATOR+"classes";
	public final static String ANDROID_LIBS_PATH = "libs";
	public final static String ANDROID_RES_PATH = "res";
	public final static String ANDROID_RES_LAYOUT_PATH = "res"+SEPARATOR+"layout";
	public final static String ANDROID_RES_RAW_PATH = "res"+SEPARATOR+"raw";
	public final static String ANDROID_RES_DRAWABLE_HDPI_PATH = "res"+SEPARATOR+"drawable-hdpi";

	public final static String ANDROID_GOOGLE_PLAY_SERVICE_PATH = "google/google_play_services/libproject/google-play-services_lib";

	public final static String ANDROID_GOOGLE_PLAY_SERVICE_JAR_PATH = "google-play-services.jar";

	public final static String IOS_IMAGES_PATH = "images";
	public final static String IOS_RESOURCES_PATH = "Resources";


	private static LayoutManager instance;

	private HashMap<String,UMLModel> loadTempMap = new HashMap();


	//Objective-C 
	public static String IMPLEMENTATION_IOS_KEY_WORLD = "@implementation";
	public static String SYNTHESIZE_IOS_KEY_WORLD = "@synthesize";
	public static String END_IOS_KEY_WORLD = "@end";

	public static String IMPORT_IOS_KEY_WORLD = "#import";
	public static String INTERFACE_IOS_KEY_WORLD = "@interface";
	public static String PROPERTY_KEY_WORLD = "@property";
	public static String NONATOMIC_STRONG_IOS_KEY_WORLD = "(nonatomic,strong)";
	public static String SQUARE_BRACKET_START_KEY_TOKEN = "[";
	public static String SQUARE_BRACKET_END_KEY_TOKEN = "]";
	public static String ASTERISK_KEY_TOKEN = "*";
	public static String COLON_KEY_TOKEN = ":";
	public static String ANGLE_BRACKET_START_KEY_TOKEN = "<";
	public static String ANGLE_BRACKET_END_KEY_TOKEN = ">";
	public static String PUBLIC_IOS_KEY_WORLD = "+";
	public static String PRIVATE_IOS_KEY_WORLD = "-";

	//Java
	public static String PACKAGE_KEY_WORLD = "package";
	public static String PUBLIC_KEY_WORLD = "public";
	public static String PRIVATE_KEY_WORLD = "private";
	public static String PROTECTED_KEY_WORLD = "protected";
	public static String VOID_KEY_WORLD = "void";
	public static String CLASS_KEY_WORLD = "class";
	public static String INTERFACE_KEY_WORLD = "interface";
	public static String BLOCK_START_TOKEN = "{";
	public static String BLOCK_END_TOKEN = "}";
	public static String FUNCTION_START_TOKEN = "(";
	public static String FUNCTION_END_TOKEN = ")";
	public static String EXTENDS__KEY_WORLD = "extends";
	public static String IMPLEMENTS_KEY_WORLD = "implements";
	public static String IMPORT_KEY_WORLD = "import";
	public static String NEW_WORLD = "new";
	public static String COMMAMA_WORLD = ",";
	public static String DOT_WORLD = ".";
	public static String QUO_WORLD = "\"";
	public static String EQUALS_WORLD = "=";
	public static String END_MARK_TOKEN = ";";
	public static String SPACE_TOKEN = " ";
	public static String ENTER_TOKEN = "\n";
	public static String R_ID_WORD = "R.id";
	public static String R_DRAWABLE_WORD = "R.drawable";
	public static String R_LAYOUT_WORD = "R.layout";
	public static String R_FIND_VIEW_BY_ID = "findViewById";
	public static String SUPPORT_MAP_FRAGMENT_WORD = "SupportMapFragment";
	public static String GET_SUPPORT_FRAGMENT_MANAGER_WORD = "getSupportFragmentManager";
	public static String FIND_FRAGMENT_BY_ID = "findFragmentById";
	public static String GET_MAP = "getMap()";
	public static String STKEND_TEMP = "STKEND";
	public static String BLOCK_START_TEMP = "BLOCK_START_TEMP";
	public static String BLOCK_END_TEMP = "BLOCK_END_TEMP";
	public static String ENTER_TOKEN_TEMP = "ENTER_TOKEN_TEMP";


	public final static String ANDROID_MAC_SDK_PATH = "android_sdk_mac";
	public final static String ANDROID_TOOLS_PATH = "tools";
	public final static String ANDROID_PLATFORM_TOOLS_PATH = "platform-tools";
	public final static String ANDROID_PLATFORMS_PATH = "platforms";
	public final static String ANDROID_EXTRAS_PATH = "extras";
	public final static String ANDROID_ANDROID_PATH = "android";
	public final static String ANDROID_SUPPORT_PATH = "support";
	public final static String ANDROID_V4_PATH = "v4";
	public final static String ANDROID_SUPPORT_V4_PATH = "android-support-v4.jar";

	public final static String ANDROID_CMD_ANDROID = "android";
	public final static String ANDROID_CMD_AAPK = "aapk";
	public final static String ANDROID_CMD_AAPT = "aapt";
	public final static String ANDROID_CMD_APKBUILDER = "apkbuilder";
	public final static String ANDROID_CMD_EMULATOR = "emulator";
	public final static String ANDROID_CMD_ADB = "adb";

	public final static String CMD_PACKAGE = "package";

	public final static String CMD_N_L = "-n";
	public final static String CMD_M_L = "-m";
	public final static String CMD_M_U = "-M";

	public final static String CMD_DIR = "-d";
	public final static String CMD_D_L = "-d";

	public final static String CMD_RF_L = "-rf";
	public final static String CMD_RJ_L = "-rj";

	public final static String CMD_J_L = "-j";
	public final static String CMD_J_U = "-J";
	public final static String CMD_S_L = "-s";
	public final static String CMD_S_U = "-S";	
	public final static String CMD_I_U = "-I";
	public final static String CMD_F_U = "-F";
	public final static String CMD_F_L = "-f";
	public final static String CMD_Z_U = "-Z";
	public final static String CMD_Z_L = "-z";




	public final static String JAVA_CMD_JARSIGNERL = "jarsigner";
	public final static String JAVA_CMD_KEYTOOL = "keytool";
	public final static String CMD_DUMP = "dump";
	public final static String CMD_BADGING = "badging";
	public final static String CMD_INSTALL = "install";
	public final static String CMD_UNINSTALL = "uninstall";
	public final static String CMD_GENKEY = "-genkey";
	public final static String CMD_ALIAS = "-alias";
	public final static String CMD_KEYSTORE = "keystore";
	public final static String CMD_KEYALG = "-keyalg";
	public final static String CMD_RSA = "RSA";
	public final static String CMD_VALIDITY = "-validity";
	public final static String CMD_VERBOSE = "-verbose";
	public final static String CMD_WIPE_DATA = "-wipe-data";
	public final static String CMD_AVD = "-avd";
	public final static String CMD_DEVICES = "devices";
	public final static String CMD_UN_AVD = "avd";
	public final static String CMD_LIST = "list";
	public final static String CMD_AM = "am";
	public final static String CMD_START = "start";
	public final static String CMD_SHELL = "shell";

	public final static String CMD_JAVAC = "javac";
	public final static String CMD_CLASSPATH = "-classpath";
	public final static String CMD_ENCODING = "-encoding";
	public final static String CMD_UTF8 = "utf-8";
	public final static String CMD_BOOT_CLASS_PATH = "-bootclasspath";
	public final static String CMD_SOURCE_PATH = "-sourcepath";
	public final static String CMD_DX ="dx";
	public final static String CMD_DEX ="--dex";
	public final static String CMD_OUTPUT ="--output";
	public final static String CMD_POSITIONS_LINES ="--positions=lines";

	public final static double IOS_WIDTH_DISPLAY_PERCENT = 0.8875; // 32000 /DISPLAY_WIDTH
	public final static double IOS_HEIGHT_DISPLAY_PERCENT = 0.5850; // 32000 /DISPLAY_WIDTH

	private Runtime runtime; 

	private int type = 1;//1:Java 2:Objective-c

	private ArrayList<String> varNameList = new ArrayList();
	private HashMap<String,ArrayList> operationsMap = new HashMap();
	UMLDiagramModel diagram = null;

	private UMLModel sourceModel = null;

	public LayoutManager(){

	}
	public static LayoutManager getInstance() {
		if (instance == null) {
			instance = new LayoutManager();

			return instance;
		}
		else {
			return instance;
		}
	}




	public void androidLoad(String path){
		try{
			loadTempMap.clear();

			SAXParserFactory factory = SAXParserFactory.newInstance();
			SAXParser saxParser = factory.newSAXParser();

			DefaultHandler handler = new DefaultHandler() {

				boolean bfname = false;
				boolean blname = false;
				boolean bnname = false;
				boolean bsalary = false;

				public void startElement(String uri, String localName,String qName, 
						Attributes attributes) throws SAXException {

					System.out.println("Start Element :" + qName);


					if(qName.indexOf("Layout")>=0)
						diagram = ProjectManager.getInstance().addUMLDiagram(null, null, 1, false);

					UMLModel model = ProjectManager.getInstance().addUMLModel(null, ProjectManager.getInstance().getModelType(qName), diagram, null, true);
					if(model!=null)
						for(int i =0 ;i < attributes.getLength(); i++){
							Object obj1 = attributes.getQName(i);
							Object obj = attributes.getValue(i);

							setAndroidProperty(model,attributes.getQName(i),attributes.getValue(i));
						}	





				}

				public void endElement(String uri, String localName,
						String qName) throws SAXException {

				}

				public void characters(char ch[], int start, int length) throws SAXException {
					System.out.println(new String(ch, start, length));
					if (bfname) {
						System.out.println("First Name : " + new String(ch, start, length));
						bfname = false;
					}

					if (blname) {
						System.out.println("Last Name : " + new String(ch, start, length));
						blname = false;
					}

					if (bnname) {
						System.out.println("Nick Name : " + new String(ch, start, length));
						bnname = false;
					}

					if (bsalary) {
						System.out.println("Salary : " + new String(ch, start, length));
						bsalary = false;
					}

				}

			};

			saxParser.parse(path, handler);

		}catch(Exception e){
			e.printStackTrace();
		}
	}

	public void setAndroidProperty(UMLModel model,String key,String value){

		if(key.equals("android:id")){
			String name = value.substring(value.indexOf("/")+1,value.length());
			model.setName(name);
			loadTempMap.put(model.getName(), model);
		}else if(key.equals("android:layout_marginLeft")){
			if(loadTempMap.get(SWT.LEFT+model.getName())!=null){
				model.setPropertyValue(Property.ID_LOCATION,new Point(loadTempMap.get(SWT.LEFT+model.getName()).getLocation().x+ProjectManager.getInstance().getPix(diagram,Float.parseFloat(value.replaceAll("dp", ""))), model.getLocation().y));
			}else{
				model.setPropertyValue(Property.ID_LOCATION,new Point(ProjectManager.getInstance().getAeroLayoutModel(model)!=null?ProjectManager.getInstance().getAeroLayoutModel(model).getLocation().x+ProjectManager.getInstance().getPix(diagram,Float.parseFloat(value.replaceAll("dp", ""))):ProjectManager.getInstance().getPix(diagram,Float.parseFloat(value.replaceAll("dp", ""))), model.getLocation().y));
			}
		}else if(key.equals("android:layout_marginTop")){
			if(loadTempMap.get(SWT.TOP+model.getName())!=null){
				model.setPropertyValue(Property.ID_LOCATION,new Point(model.getLocation().x, loadTempMap.get(SWT.TOP+model.getName()).getLocation().y+ProjectManager.getInstance().getPix(diagram,Float.parseFloat(value.replaceAll("dp", "")))));
			}else{
				model.setPropertyValue(Property.ID_LOCATION,new Point(model.getLocation().x, ProjectManager.getInstance().getAeroLayoutModel(model)!=null?ProjectManager.getInstance().getAeroLayoutModel(model).getLocation().y+ProjectManager.getInstance().getPix(diagram,Float.parseFloat(value.replaceAll("dp", ""))):ProjectManager.getInstance().getPix(diagram,Float.parseFloat(value.replaceAll("dp", "")))));
			}

		}else if(key.equals("android:ems")){
			model.setPropertyValue(Property.ID_EMS, value);
		}else if(key.equals("android:inputType")){
			model.setPropertyValue(Property.ID_TYPE, ProjectManager.getInstance().getStringIndex(UMLModel.editTextPropString, value));
		}else if(key.equals("android:layout_alignRight")){
			String name = value.substring(value.indexOf("/")+1,value.length());
			loadTempMap.put(SWT.RIGHT+model.getName(), loadTempMap.get(name));
		}else if(key.equals("android:layout_alignLeft")){
			String name = value.substring(value.indexOf("/")+1,value.length());
			loadTempMap.put(SWT.LEFT+model.getName(), loadTempMap.get(name));
		}else if(key.equals("android:layout_below")){
			String name = value.substring(value.indexOf("/")+1,value.length());
			loadTempMap.put(SWT.TOP+model.getName(), loadTempMap.get(name));
		}
	}

	//안드로이드/IOS 프로젝트 파일 배치 
	public void initFiles(String path){
		initAndroidFiles(path);
		initIOSFiles(path);
	}

	//안드로이드 프로젝트 파일 배치 작업 
	public void initAndroidFiles(String path){

		File f = new File(path);

		if(f.exists())
			f.delete();



		generatorCreateAndroidProject(ProjectManager.getInstance().getAndroid_MinSdkVersion(),path);

	}
	//안드로이드 프로젝트 생성 
	public void generatorCreateAndroidProject(int id,String path){
		String[] commend = new String[1];

		String toolsPath = "";
		if(ProjectManager.getInstance().getOsVer()==ProjectManager.OS_MAC)
			toolsPath = ProjectManager.getInstance().getAndroidSDKPath()+this.SEPARATOR+this.ANDROID_TOOLS_PATH+this.SEPARATOR;

		commend[0] = toolsPath+"android create project -t android-"+id+" -n TipssoftApp -p "+path+" -a MainActivity -k "+ProjectManager.getInstance().getAndroidPackage() + " -n test";

		ProjectManager.getInstance().commend(commend);


		File supportFile = new File(ProjectManager.getInstance().getAndroidSDKPath() + this.SEPARATOR +
				this.ANDROID_EXTRAS_PATH + this.SEPARATOR + this.ANDROID_ANDROID_PATH + this.SEPARATOR + this.ANDROID_SUPPORT_PATH+ this.SEPARATOR + this.ANDROID_V4_PATH + this.SEPARATOR + ANDROID_SUPPORT_V4_PATH);

		File copyFile = new File(getAndroidSupportPath());

		copyFile(supportFile.getPath(),copyFile.getPath());






	}
	public String getAndroidTempAPKPath(){
		return ProjectManager.getInstance().getAndroidPath()+this.SEPARATOR+this.ANDROID_EXT_PATH+this.SEPARATOR+ProjectManager.getInstance().getAndroidAppName()+".apk";
	}
	public String getAndroidDexPath(){
		return ProjectManager.getInstance().getAndroidPath()+this.SEPARATOR+this.ANDROID_EXT_PATH+this.SEPARATOR+ProjectManager.getInstance().getAndroidAppName()+".dex";
	}

	public String getAndroidSupportPath(){
		return ProjectManager.getInstance().getAndroidPath() + this.SEPARATOR + this.ANDROID_LIBS_PATH +this.SEPARATOR+ this.ANDROID_SUPPORT_V4_PATH;
	}
	//현재는 사용하지 않음 S  
	//구글 JarPath
	public String getGoogleMapJarPath(){

		return  ProjectManager.getInstance().getAndroidSDKPath() + this.SEPARATOR + LayoutManager.ANDROID_EXTRAS_PATH 
				+ LayoutManager.SEPARATOR + this.ANDROID_GOOGLE_PLAY_SERVICE_PATH + this.SEPARATOR + this.ANDROID_LIBS_PATH + this.SEPARATOR + this.ANDROID_GOOGLE_PLAY_SERVICE_JAR_PATH;
	}
	public String getGoogleMapPath(){

		return  ProjectManager.getInstance().getAndroidSDKPath() + this.SEPARATOR + LayoutManager.ANDROID_EXTRAS_PATH 
				+ LayoutManager.SEPARATOR + this.ANDROID_GOOGLE_PLAY_SERVICE_PATH;
	}

	//현재는 사용하지 않음 E
	public String getAndroidJarPath(){
		return ProjectManager.getInstance().getAndroidSDKPath() + this.SEPARATOR 
				+ this.ANDROID_PLATFORMS_PATH + this.SEPARATOR + "android-" +ProjectManager.getInstance().getAndroid_MinSdkVersion() + this.SEPARATOR + "android.jar";
	}
	//	public String androidKeyStore(){
	//		return ProjectManager.getInstance().getAndroidPath()+this.SEPARATOR+this.ANDROID_EXT_PATH+this.SEPARATOR+ProjectManager.getInstance().getAndroidAppName()+".keystore";
	//	}

	/**
	 *  1 : R 파일을 생성한다.
	 *  2 : DEX파일을 생성한다. 
	 *  3 : APK 파일을 생성해준다. 이 때 APK는 최종본이 아니다. Temp 파일로 서명파일이 입혀지지 않은 파일이다.
	 *  4 : 서명파일을 생성하여 준다 .
	 *  5 : DEX 파일과 APK 파일을 Build해준다.
	 *  6 : APK 생성된 파일에 서명을 추가하여준다. 
	 */
	//R 파일 생성 (1) 
	public void generatorAndroidR(){


		String cmd = "";

		cmd = ProjectManager.getInstance().getAndroidSDKPath()+this.SEPARATOR+this.ANDROID_PLATFORM_TOOLS_PATH+this.SEPARATOR;
		//
		cmd = cmd + this.ANDROID_CMD_AAPT + this.SPACE_TOKEN + this.CMD_PACKAGE + this.SPACE_TOKEN + this.CMD_M_L + this.SPACE_TOKEN + this.CMD_J_U +this.SPACE_TOKEN+ ProjectManager.getInstance().getAndroidPath()+this.SEPARATOR+this.ANDROID_GEN_PATH
				+ this.SPACE_TOKEN + this.CMD_M_U + this.SPACE_TOKEN + AndroidManifestManager.getInstance().getPath()
				+ this.SPACE_TOKEN + this.CMD_S_U + this.SPACE_TOKEN + ProjectManager.getInstance().getAndroidPath()+this.SEPARATOR+this.ANDROID_RES_PATH
				+ this.SPACE_TOKEN + this.CMD_J_L + this.SPACE_TOKEN + ProjectManager.getInstance().getAndroidPath()+this.SEPARATOR+this.ANDROID_LIBS_PATH
				+ this.SPACE_TOKEN + this.CMD_I_U + this.SPACE_TOKEN + getAndroidJarPath();

		//		cmd = "/Users/pky1030/Documents/Java/eclipse/android_sdk_mac/platform-tools/aapt package -m -J /Users/pky1030/Documents/test/Android/test/gen -M /Users/pky1030/Documents/test/Android/test/AndroidManifest.xml -S /Users/pky1030/Documents/test/Android/test/res -S /Users/pky1030/Documents/Java/eclipse/android_sdk_mac/extras/google/google_play_services/libproject/google-play-services_lib/res/ -j /Users/pky1030/Documents/test/Android/test/libs -I /Users/pky1030/Documents/Java/eclipse/android_sdk_mac/platforms/android-10/android.jar -I /Users/pky1030/Documents/Java/eclipse/android_sdk_mac/extras/google/google_play_services/libproject/google-play-services_lib/libs/google-play-services.jar --auto-add-overlay";
		//
		ProjectManager.getInstance().commend(cmd);

		//		cmd = "";

		//		cmd = ProjectManager.getInstance().getAndroidSDKPath()+this.SEPARATOR+this.ANDROID_PLATFORM_TOOLS_PATH+this.SEPARATOR;
		//
		//		cmd = cmd + this.ANDROID_CMD_AAPT + this.SPACE_TOKEN + this.CMD_PACKAGE + this.SPACE_TOKEN + this.CMD_M_L + this.SPACE_TOKEN + this.CMD_J_U +this.SPACE_TOKEN+ ProjectManager.getInstance().getAndroidPath()+this.SEPARATOR+this.ANDROID_GEN_PATH
		//				+ this.SPACE_TOKEN + this.CMD_M_U + this.SPACE_TOKEN + "/Users/pky1030/Documents/test/Android/google-play-services_lib/AndroidManifest.xml"
		//				+ this.SPACE_TOKEN + this.CMD_S_U + this.SPACE_TOKEN + ProjectManager.getInstance().getAndroidPath()+this.SEPARATOR+this.ANDROID_RES_PATH
		//				+ this.SPACE_TOKEN + this.CMD_J_L + this.SPACE_TOKEN + ProjectManager.getInstance().getAndroidPath()+this.SEPARATOR+this.ANDROID_LIBS_PATH
		//				+ this.SPACE_TOKEN + this.CMD_I_U + this.SPACE_TOKEN + getAndroidJarPath();
		//		ProjectManager.getInstance().commend(cmd);
		String pkg = ProjectManager.getInstance().getAndroidPackage().replaceAll("\\.", this.SEPARATOR);
		//		generatorjavaCompile("/Users/pky1030/Documents/test/Android/test/gen/com/google/android/gms"+this.SEPARATOR+"R.java");
		generatorjavaCompile(ProjectManager.getInstance().getAndroidPath()+this.SEPARATOR+this.ANDROID_GEN_PATH+this.SEPARATOR+pkg+this.SEPARATOR+"R.java");
		try{Thread.sleep(25); } catch(Exception e){e.printStackTrace();}

	}
	// DEX 파일생성 (2)
	/**
	 * ./dx --dex --output="/Users/pky1030/Documents/classes.dex" --positions=lines "/Users/pky1030/Documents/Java/android_workspace/tet/bin/classes"
	 * @param path
	 */
	public void generatorAndroidDex(){
		String cmd = "";

		ProjectManager.getInstance().commend(cmd);
		cmd = ProjectManager.getInstance().getAndroidSDKPath()+this.SEPARATOR+this.ANDROID_PLATFORM_TOOLS_PATH+this.SEPARATOR + this.CMD_DX 
				+ this.SPACE_TOKEN + this.CMD_DEX + this.SPACE_TOKEN + this.CMD_OUTPUT + this.EQUALS_WORLD+ getAndroidDexPath()
				+ this.SPACE_TOKEN + CMD_POSITIONS_LINES+ this.SPACE_TOKEN +ProjectManager.getInstance().getAndroidPath() + this.SEPARATOR + LayoutManager.ANDROID_BIN_CLASSES;

		ProjectManager.getInstance().commend(cmd);

	}

	// APK파일 생성 (3)
	public void generatorAndroidAPK(){
		String cmd = "";


		cmd = ProjectManager.getInstance().getAndroidSDKPath()+this.SEPARATOR+this.ANDROID_PLATFORM_TOOLS_PATH+this.SEPARATOR
				+ this.ANDROID_CMD_AAPT+this.SPACE_TOKEN+this.CMD_PACKAGE+this.SPACE_TOKEN+this.CMD_F_L+ this.SPACE_TOKEN+this.CMD_M_U + this.SPACE_TOKEN + AndroidManifestManager.getInstance().getPath()
				+ this.SPACE_TOKEN + this.CMD_S_U + this.SPACE_TOKEN + ProjectManager.getInstance().getAndroidPath()+this.SEPARATOR+this.ANDROID_RES_PATH
				+ this.SPACE_TOKEN + this.CMD_I_U + this.SPACE_TOKEN + getAndroidJarPath()
				+ this.SPACE_TOKEN + this.CMD_F_U + this.SPACE_TOKEN + getAndroidTempAPKPath();
		ProjectManager.getInstance().commend(cmd);

	}
	//서명 처리 (4)
	//keytool -genkey -alias test.keystore -keyalg RSA -validity 2000 -keystore /Users/pky1030/Documents/1.keystore\n
	public void generatorAndroidKeyStore(String alias,String validity,String path,String[] infoData){


		//서명 파일 작성 
		String[] cmd = new String[infoData.length+1];
		cmd[0] =  this.JAVA_CMD_KEYTOOL +this.SPACE_TOKEN + this.CMD_GENKEY + this.SPACE_TOKEN + this.CMD_ALIAS + this.SPACE_TOKEN +alias +this.DOT_WORLD+this.CMD_KEYSTORE
				+ this.SPACE_TOKEN + this.CMD_KEYALG+ this.SPACE_TOKEN + this.CMD_RSA + this.SPACE_TOKEN + this.CMD_VALIDITY + this.SPACE_TOKEN + validity
				+ this.SPACE_TOKEN + "-"+this.CMD_KEYSTORE + this.SPACE_TOKEN + path + this.ENTER_TOKEN;

		for(int i = 0; i < infoData.length; i ++){
			cmd[i+1] = infoData[i];
		}



		ProjectManager.getInstance().commendAppendExcute(cmd);



	}
	/**
	 * 
	 * 
		./apkbuilder "/Users/pky1030/Documents/Hello-debug.apk" 
		-z "/Users/pky1030/Documents/Hello.apk" 
		-f "/Users/pky1030/Documents/classes.dex" 
		-rf "/Users/pky1030/Documents/Java/android_workspace/tet/src" 
		-rj "/Users/pky1030/Documents/Java/android_workspace/tet/libs"
	 * @param path
	 */
	//DEX 파일과 APK 파일을 Build해준다. (5)
	public void generatorAPKbuilder(){
		String cmd = "";

		cmd = ProjectManager.getInstance().getAndroidSDKPath()+this.SEPARATOR+this.ANDROID_TOOLS_PATH+this.SEPARATOR 
				+ this.ANDROID_CMD_APKBUILDER + this.SPACE_TOKEN + ProjectManager.getInstance().getAndroidAPK()
				+ this.SPACE_TOKEN + this.CMD_Z_L + this.SPACE_TOKEN + this.getAndroidTempAPKPath() 
				+ this.SPACE_TOKEN + this.CMD_F_L + this.SPACE_TOKEN + this.getAndroidDexPath()
				+ this.SPACE_TOKEN + this.CMD_RF_L + this.SPACE_TOKEN + ProjectManager.getInstance().getAndroidPath() + this.SEPARATOR + this.ANDROID_SRC_PATH
				+ this.SPACE_TOKEN + this.CMD_RJ_L + this.SPACE_TOKEN + ProjectManager.getInstance().getAndroidPath()+ this.SEPARATOR + this.ANDROID_LIBS_PATH;
		//+ this.END_MARK_TOKEN + getGoogleMapPath();


		ProjectManager.getInstance().commend(cmd);
	}
	//keyStore랑 apk랑 통합한다. 
	//jarsigner -verbose -keystore /Users/pky1030/Documents/test.keystore /Users/pky1030/Documents/apk.apk appd.keystore
	public void generatorAndroidJarSigner(String alias,String password){
		String[] cmd = new String[2];
		cmd[0] = this.JAVA_CMD_JARSIGNERL + this.SPACE_TOKEN + this.CMD_VERBOSE + this.SPACE_TOKEN + "-"+ this.CMD_KEYSTORE
				+ this.SPACE_TOKEN + ProjectManager.getInstance().getAndroidKeyStore() + this.SPACE_TOKEN + ProjectManager.getInstance().getAndroidAPK() + this.SPACE_TOKEN + alias +this.DOT_WORLD+ this.CMD_KEYSTORE;
		cmd[1] = password;
		ProjectManager.getInstance().commendAppendExcute(cmd);
	}

	//폴더주소 넘겨주면 폴더안에있는 모든 내용을 컴파일한다.
	public void generatorCompileJavas(String path){
		File f = new File(path);

		File[] files = f.listFiles();

		for(int i = 0; i < files.length; i++){
			if(files[i].isDirectory()){
				generatorCompileJavas(files[i].getPath());
			}else if(files[i].getName().toLowerCase().endsWith(".java")){
				generatorjavaCompile(files[i].getPath());				
			}
		}
	}

	//
	//Java Compile
	/**
	 * javac /Users/pky1030/Documents/test/Android/test/src/org/test/test/Package1/RelativeLayout1.java 
	 * -classpath /Users/pky1030/Documents/test/Android/test/bin/classes/ 
	 * -d /Users/pky1030/Documents/test/Android/test/bin/ 
	 * -bootclasspath /Users/pky1030/Documents/Java/android_sdk/platforms/android-4.2/android.jar/
	 * @param path
	 */

	public void generatorjavaCompile(String path){
		String cmd = this.CMD_JAVAC;

		cmd += this.SPACE_TOKEN + path
				+  this.SPACE_TOKEN + this.CMD_CLASSPATH + this.SPACE_TOKEN + ProjectManager.getInstance().getAndroidPath() + this.SEPARATOR + LayoutManager.ANDROID_BIN_CLASSES
				+  this.SPACE_TOKEN + this.CMD_D_L + this.SPACE_TOKEN + ProjectManager.getInstance().getAndroidPath() + this.SEPARATOR + LayoutManager.ANDROID_BIN_CLASSES
				+  this.SPACE_TOKEN + this.CMD_BOOT_CLASS_PATH + this.SPACE_TOKEN + getAndroidJarPath() + this.END_MARK_TOKEN + getAndroidSupportPath() + this.END_MARK_TOKEN + "/Users/pky1030/Documents/Java/eclipse/android_sdk_mac/extras/google/google_play_services/libproject/google-play-services_lib/libs/google-play-services.jar"
				+  this.SPACE_TOKEN + this.CMD_SOURCE_PATH + this.SPACE_TOKEN + ProjectManager.getInstance().getAndroidPath()+this.SEPARATOR + this.ANDROID_SRC_PATH
				+  this.SPACE_TOKEN + this.CMD_ENCODING + this.SPACE_TOKEN + this.CMD_UTF8;


		ProjectManager.getInstance().commend(cmd);
	}
	//IOS 프로젝트 파일 배치 작업 
	public void initIOSFiles(String path){

	}

	public void makeFileIOS(char type ,String fileName,Object text){
		String folder_path = null;
		File file = null;
		BufferedWriter outFile = null;
		try{
			switch (type) {
			case 'D':
				String introLayout = "";
				ArrayList<UMLModel> list = ProjectManager.getInstance().getModels(ProjectManager.getInstance().getModelType("RelativeLayout"));
				boolean isMain = false;
				for(int i = 0; i < list.size(); i ++){
					int value =(Integer) list.get(i).getPropertyValue(Property.ID_MAIN);
					if(value==1){
						introLayout =  list.get(i).getName();
					}
				}

				folder_path = ProjectManager.getInstance().getIosPath()+this.SEPARATOR;

				fileName = "AppDelegate" + DOT_WORLD + "h";
				file = new File(folder_path+fileName);
				file.mkdirs();
				if(!file.exists())
					file.createNewFile();
				else 
					file.delete();
				outFile = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "utf-8"));

				StringBuffer str = new StringBuffer();

				str.append("#import <UIKit/UIKit.h>"+ENTER_TOKEN);
				str.append("#import "+QUO_WORLD+introLayout+".h"+QUO_WORLD+ENTER_TOKEN);
				str.append(""+ENTER_TOKEN);
				str.append("@interface AppDelegate : UIResponder <UIApplicationDelegate>"+ENTER_TOKEN);
				str.append("{"+ENTER_TOKEN);
				str.append("    UIWindow* windows;"+ENTER_TOKEN);
				str.append("    "+introLayout+"* ibView;"+ENTER_TOKEN);
				str.append("    UINavigationController* nav;"+ENTER_TOKEN);
				str.append("}"+ENTER_TOKEN);
				str.append(""+ENTER_TOKEN);
				str.append("@property (strong, nonatomic) UIWindow* windows;"+ENTER_TOKEN);
				str.append("@property (strong, nonatomic) "+introLayout+"* ibView;"+ENTER_TOKEN);
				str.append("@property (strong, nonatomic) UINavigationController* nav;"+ENTER_TOKEN);
				str.append(""+ENTER_TOKEN);
				str.append(""+ENTER_TOKEN);
				str.append("@end"+ENTER_TOKEN);
				outFile.write(str.toString());
				outFile.close();

				fileName = "AppDelegate" + DOT_WORLD + "m";
				file = new File(folder_path+fileName);
				file.mkdirs();
				if(!file.exists())
					file.createNewFile();
				else 
					file.delete();
				outFile = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "utf-8"));

				str = new StringBuffer();

				str.append("#import "+QUO_WORLD+"AppDelegate.h"+QUO_WORLD+ENTER_TOKEN);
				str.append(""+ENTER_TOKEN);
				str.append("@implementation AppDelegate"+ENTER_TOKEN);
				str.append(""+ENTER_TOKEN);
				str.append("@synthesize windows;"+ENTER_TOKEN);
				str.append("@synthesize ibView;"+ENTER_TOKEN);
				str.append("@synthesize nav;"+ENTER_TOKEN);
				str.append(""+ENTER_TOKEN);
				str.append(""+ENTER_TOKEN);
				str.append("- (BOOL)application:(UIApplication *)application didFinishLaunchingWithOptions:(NSDictionary *)launchOptions"+ENTER_TOKEN);
				str.append("{"+ENTER_TOKEN);
				str.append(""+ENTER_TOKEN);    
				str.append(""+ENTER_TOKEN);
				str.append(""+ENTER_TOKEN);
				str.append("windows = [[UIWindow alloc]initWithFrame:[[UIScreen mainScreen]bounds]];"+ENTER_TOKEN);
				str.append("nav = [[UINavigationController alloc] initWithRootViewController:[["+introLayout+" alloc]init]];"+ENTER_TOKEN);
				str.append("[windows addSubview:nav.view];"+ENTER_TOKEN);
				str.append("[windows makeKeyAndVisible];"+ENTER_TOKEN);
				str.append(LayoutManager.getInstance().writeTitleBarCustomIOS());//타이틀바 수
				str.append("return YES;"+ENTER_TOKEN);
				str.append("}"+ENTER_TOKEN);
				str.append(""+ENTER_TOKEN);
				str.append("- (void)applicationWillResignActive:(UIApplication *)application"+ENTER_TOKEN);
				str.append("{"+ENTER_TOKEN);
				str.append(""+ENTER_TOKEN);
				str.append("}"+ENTER_TOKEN);
				str.append(""+ENTER_TOKEN);
				str.append("- (void)applicationDidEnterBackground:(UIApplication *)application"+ENTER_TOKEN);
				str.append("{"+ENTER_TOKEN);
				str.append("}"+ENTER_TOKEN);
				str.append(""+ENTER_TOKEN);
				str.append("- (void)applicationWillEnterForeground:(UIApplication *)application"+ENTER_TOKEN);
				str.append("{"+ENTER_TOKEN);

				str.append("}"+ENTER_TOKEN);
				str.append(""+ENTER_TOKEN);
				str.append("- (void)applicationDidBecomeActive:(UIApplication *)application"+ENTER_TOKEN);
				str.append("{"+ENTER_TOKEN);

				str.append("}"+ENTER_TOKEN);
				str.append(""+ENTER_TOKEN);
				str.append("- (void)applicationWillTerminate:(UIApplication *)application"+ENTER_TOKEN);
				str.append("{"+ENTER_TOKEN);

				str.append("}"+ENTER_TOKEN);
				str.append(""+ENTER_TOKEN);
				str.append(""+ENTER_TOKEN);
				str.append("- (NSString * ) chgTest:(NSString *)targetString inTo:(BOOL)isToupperCase{"+ENTER_TOKEN);
				str.append("if(isToupperCase==YES){"+ENTER_TOKEN);
				str.append("return [targetString uppercaseString];"+ENTER_TOKEN);
				str.append("}else{"+ENTER_TOKEN);
				str.append("return [targetString uppercaseString];"+ENTER_TOKEN);
				str.append("}"+ENTER_TOKEN);
				str.append("}"+ENTER_TOKEN);
				str.append("@end"+ENTER_TOKEN);

				outFile.write(sourceIndent(str.toString()));

				break;

			case 'H':
				folder_path = ProjectManager.getInstance().getIosPath()+this.SEPARATOR;

				fileName = fileName.replaceAll("\\.", SEPARATOR) + DOT_WORLD + "h";
				file = new File(folder_path+fileName);
				file.mkdirs();
				if(!file.exists())
					file.createNewFile();
				else 
					file.delete();


				outFile = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "utf-8"));
				outFile.write(sourceIndent(text.toString()));

				break;
			case 'M':
				folder_path = ProjectManager.getInstance().getIosPath()+this.SEPARATOR;

				fileName = fileName.replaceAll("\\.", SEPARATOR) + DOT_WORLD + "m";
				file = new File(folder_path+fileName);
				file.mkdirs();
				if(!file.exists())
					file.createNewFile();
				else 
					file.delete();


				outFile = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "utf-8"));
				outFile.write(sourceIndent(text.toString()));

				break;

			}

		}catch (Exception e) {

		}finally{
			if(outFile!=null) try{ outFile.close(); }catch(Exception e){}
		}
	}
	public void makeFileAndroid(char type ,String fileName,Object text){
		File file = null;
		BufferedWriter outFile = null;
		BufferedReader fi =null;
		StreamResult streamResult =null;
		Transformer transformer = null;
		try{

			String folder_path = null;

			switch (type) {
			case 'C': //CommonJava

				copyCommon(APPLICATION_ANDROID_UTIL_JAVA_PATH);
				copyCommon(APPLICATION_IMGVIEW_TOUCH_JAVA_PATH);


				break;


			case 'J':
				folder_path = ProjectManager.getInstance().getAndroidPath()+this.SEPARATOR+ANDROID_SRC_PATH+this.SEPARATOR;

				fileName = fileName.replaceAll("\\.", SEPARATOR);
				folder_path = folder_path.replaceAll("\\.", SEPARATOR);

				fileName =fileName+".java";

				file = new File(folder_path+fileName);
				file.mkdirs();//폴더생성
				if(!file.exists())
					file.createNewFile();
				else 
					file.delete();


				outFile = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "utf-8"));
				outFile.write(sourceIndent(text.toString()));



				break;
			case 'L':
				folder_path = ProjectManager.getInstance().getAndroidPath()+this.SEPARATOR+ANDROID_RES_LAYOUT_PATH+this.SEPARATOR;

				fileName = fileName+".xml";

				try{


					streamResult = new StreamResult(new File(folder_path+fileName));

					TransformerFactory transformerFactory = TransformerFactory.newInstance();
					transformer = transformerFactory.newTransformer();

					transformer.setOutputProperty(OutputKeys.INDENT, "yes");
					DOMSource source = new DOMSource((Document)text);


					transformer.transform(source, streamResult);

				}catch(Exception e){
					e.printStackTrace();

				}
				break;
			default:
				break;
			}






		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(fi!=null) try{ fi.close();}catch(Exception e){}
			if(outFile!=null) try{ outFile.close();}catch(Exception e){}

		}
	}

	public void copyCommon(String fileName) throws Exception{
		BufferedWriter outFile =null;
		BufferedReader fi = null;
		try{
			String folder_path  = ProjectManager.getInstance().getAndroidPath()+this.SEPARATOR+ANDROID_SRC_PATH+this.SEPARATOR+ProjectManager.getInstance().getAndroidPackage()+this.SEPARATOR;
			folder_path = folder_path.replaceAll("\\.", SEPARATOR);

			File file = new File(folder_path+fileName+".java");
			file.mkdirs();//폴더생성
			if(!file.exists())
				file.createNewFile();
			else 
				file.delete();


			outFile = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "utf-8"));
			outFile.write(this.PACKAGE_KEY_WORLD+this.SPACE_TOKEN+ProjectManager.getInstance().getAndroidPackage()+this.END_MARK_TOKEN+this.ENTER_TOKEN);

			File inputFile = new File(ProjectManager.getInstance().getApplicationLocation()+this.SEPARATOR+APPLICATION_CONFIG_PATH+this.SEPARATOR+APPLICATION_JAVA_PATH+this.SEPARATOR+fileName+".java");

			// FileInputStream의 인스턴스를 만든다.
			fi = new BufferedReader(
					new InputStreamReader(
							new FileInputStream(inputFile), "UTF8"));

			// 입력 스트림에서 데이터를 읽어들여 출력하고 스트림을 닫는다.
			String str;
			while ((str = fi.readLine()) != null) {
				outFile.write(str+this.ENTER_TOKEN);
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(fi!=null) try{ fi.close();}catch(Exception e){}
			if(outFile!=null) try{ outFile.close();}catch(Exception e){}

		}
	}

	//사용되는 리스너 등록한다.
	public void initSourcePropertyAndroid(UMLModel model){
		if(model==null)
			return;
		ArrayList<OperationItem> list = new ArrayList();
		UMLModel basicModel = model.getBasicModel();
		if(basicModel instanceof EditTextModel){
			InterfaceItem textChangedListener = new InterfaceItem(Property.LISTENER_TEXT_CHANGED, "addTextChangedListener","", "TextWatcher",model);

			OperationItem afterTextChanged = new OperationItem(Property.OPERATION_AFTER_TEXT_CHANGED,"텍스트 입력(after)(Android 전용)",textChangedListener,"+ afterTextChanged(s:Editable)",""); 
			OperationItem beforeTextChanged = new OperationItem(Property.OPERATION_BEFORE_TEXT_CHANGED,"텍스트 입력(before)(Android 전용)",textChangedListener,"+ beforeTextChanged(s:CharSequence,start:int,count:int,after:int)","");
			OperationItem onTextChanged = new OperationItem(Property.OPERATION_ON_TEXT_CHANGED,"텍스트 입력(on)",textChangedListener,"+ onTextChanged(s:CharSequence, start:int, before:int,count:int)","- onTextChanged()");

			list.add(afterTextChanged);
			list.add(beforeTextChanged);
			list.add(onTextChanged);
		}

		if(!(model.getBasicModel() instanceof YoutubeModel)){
			InterfaceItem onClickListener = new InterfaceItem(Property.LISTENER_ON_CLICK, "setOnClickListener","", "OnClickListener",model);
			OperationItem onClick = new OperationItem(Property.OPERATION_ON_CLICK,"클릭",onClickListener,"+ onClick(v:View)","- onClick()");
			list.add(onClick);
		}

		if(model.getBasicModel() instanceof ImageViewModel){
			InterfaceItem setWebViewClientListener = new InterfaceItem(Property.LISTENER_WEBVIEW_CLIENT, "setWebViewClient","", "WebViewClient",model);
			OperationItem shouldOverridUrl = new OperationItem(Property.OPERATION_SHOULD_OVERRIDE_URL_LOADING,"화면 이동시",setWebViewClientListener,"+ shouldOverrideUrlLoading(view:WebView,url:String):boolean","");
			list.add(shouldOverridUrl);
		}

		if(model.getBasicModel() instanceof WebViewModel){
			InterfaceItem setWebViewClientListener = new InterfaceItem(Property.LISTENER_WEBVIEW_CLIENT, "setWebViewClient","", "WebViewClient",model);
			OperationItem shouldOverridUrl = new OperationItem(Property.OPERATION_SHOULD_OVERRIDE_URL_LOADING,"화면 이동시",setWebViewClientListener,"+ shouldOverrideUrlLoading(view:WebView,url:String):boolean","");
			list.add(shouldOverridUrl);
		}


		if(model.getBasicModel() instanceof ListViewModel){
			InterfaceItem onItemClickListener = new InterfaceItem(Property.LISTENER_LIST_ON_ITEM_CLICK, "setOnItemClickListener","", "OnItemClickListener",model);
			OperationItem onItemClick = new OperationItem(Property.OPERATION_ON_ITEM_CLICK,"ListView아이템 클릭",onItemClickListener,"+ onItemClick(arg0:AdapterView, arg1:View,arg2:int,arg3:long)","");
			list.add(onItemClick);
		}
		model.setActionList(list);

	}
	//동일한 액션이 여러개가 정의 되어있을수도있다. 
	//이런경우 액션의 리스트를 서로 머지하여 합쳐준다. 
	public void setSortActionIOSDetail(UMLModel model){
		ArrayList<ActionItem> list = (ArrayList)model.getPropertyValue(Property.ID_ACTION_ITEM);
		HashMap<String,ArrayList> map = new HashMap<String, ArrayList>();
		if(list!=null){	

			for(int i = 0; i < list.size(); i++){
				Iterator<String> iterator = list.get(i).getIterator();
				while(iterator.hasNext()){
					String key = iterator.next();

					// 같은 액션이 한개 이상 정의된경우를 대비하여 오퍼레이션끼리 머지시켜준다.
					if(list.get(i).getOperation()!=null)
						if(map.get(list.get(i).getOperation().id)==null){
							ArrayList array = new ArrayList();
							array.add(list.get(i).getActionData());
							map.put(list.get(i).getOperation().id, array);
						}else{
							map.get(list.get(i).getOperation().id).add(list.get(i).getActionData());					
						}
				}
			}
		}
		Iterator<String> iterator = map.keySet().iterator(); //정리한 오퍼레이션내용을 다시 모델의 OperationItem에 다시 넣어준다.
		while(iterator.hasNext()){
			String key = iterator.next();
			OperationItem item = model.getAction(key);
			if(item!=null){
				item.setActionDetailList(map.get(key));
			}
		}



	}
	//동일한 액션이 여러개가 정의 되어있을수도있다. 
	//이런경우 액션의 리스트를 서로 머지하여 합쳐준다. 
	public void setSortActionAndroidDetail(UMLModel model){
		ArrayList<ActionItem> list = (ArrayList)model.getPropertyValue(Property.ID_ACTION_ITEM);
		HashMap<String,ArrayList> map = new HashMap<String, ArrayList>();
		if(list!=null){	

			for(int i = 0; i < list.size(); i++){
				Iterator<String> iterator = list.get(i).getIterator();
				while(iterator.hasNext()){
					String key = iterator.next();

					// 같은 액션이 한개 이상 정의된경우를 대비하여 오퍼레이션끼리 머지시켜준다.
					if(map.get(list.get(i).getOperation().id)==null){
						ArrayList array = new ArrayList();
						array.add(list.get(i).getActionData());
						map.put(list.get(i).getOperation().id, array);
					}else{
						map.get(list.get(i).getOperation().id).add(list.get(i).getActionData());					
					}
				}
			}
		}
		Iterator<String> iterator = map.keySet().iterator(); //정리한 오퍼레이션내용을 다시 모델의 OperationItem에 다시 넣어준다.
		while(iterator.hasNext()){
			String key = iterator.next();
			OperationItem item = model.getAction(key);
			if(item!=null){
				item.setActionDetailList(map.get(key));
			}
		}



	}

	// 소스 라인 맞춰주는 소스 
	public String sourceIndent(String source){
		StringBuffer buff = new StringBuffer();
		source = source.replaceAll(LayoutManager.ENTER_TOKEN, "").replaceAll("	", "");
		String[] split = source.trim().split("||");
		int indent = 0;
		String space = "	";
		boolean isSpace = false;
		for(int i = 0; i < split.length; i ++){
			if(split.length>i&&split[i].equals(this.SPACE_TOKEN)&&split[i+1].equals(this.SPACE_TOKEN)){
				continue;
			}

			if(split[i].equals("@")){
				if(split.length>i&&!split[i+1].equals(this.QUO_WORLD))
					buff.append(this.ENTER_TOKEN);
			}
			if(split[i].equals("#")){
				buff.append(this.ENTER_TOKEN);
			}

			if(split[i].equals(this.BLOCK_END_TOKEN)){
				indent--;
				for(int j = 1; j <= indent; j++){
					buff.append(space);
				}
				isSpace = true;
			}else if(isSpace){
				for(int j = 1; j <= indent; j++){
					buff.append(space);
				}
				isSpace = false;
			}
			buff.append(split[i]);
			if(split[i].equals(this.BLOCK_END_TOKEN)){
				buff.append(this.ENTER_TOKEN);
			}

			if(split[i].equals(this.END_MARK_TOKEN)){

				if(split.length>i&&!split[i+1].equals(this.ENTER_TOKEN))
					buff.append(this.ENTER_TOKEN);

				if(split.length>i&&!split[i+1].equals(this.BLOCK_END_TOKEN)){
					for(int j = 1; j <= indent; j++){
						buff.append(space);
					}
				}
			}
			if(split[i].equals(this.BLOCK_START_TOKEN)){
				indent++;
				buff.append(this.ENTER_TOKEN);
				if(split.length>i&&!split[i+1].equals(this.BLOCK_END_TOKEN)){
					for(int j = 1; j <= indent; j++){
						buff.append(space);
					}
				}

			}




		}



		return replaceAll(buff);

	}

	public String replaceAll(StringBuffer str){
		String text = str.toString().replaceAll(STKEND_TEMP, END_MARK_TOKEN);
		text = text.replaceAll(BLOCK_START_TEMP, BLOCK_START_TOKEN);
		text = text.replaceAll(BLOCK_END_TEMP, BLOCK_END_TOKEN);
//		text = text.replaceAll(ENTER_TOKEN_TEMP, "\\\n");
		return text;
	}

	public String getIOSforControlEvents(String parentID){
		if(parentID.equals(Property.LISTENER_ON_CLICK)){
			return "UIControlEventTouchUpInside";
		}if(parentID.equals(Property.LISTENER_TEXT_CHANGED)){
			return "UIControlEventEditingChanged";
		}else{
			return "";
		}
	}
	//액션들 내용을 생성한다. 
	public void writeSourceIOS(UMLModel viewModel){
		viewModel = viewModel.getViewModel();
		this.setSourceModel(viewModel);
		this.setType(IOS);
		this.setSortActionIOSDetail(viewModel);

		ArrayList<OperationItem> operationItems = LayoutManager.getInstance().getOperationMap(viewModel);
		ArrayList<OperationItem> actionList = viewModel.getActionList();
		for(int i = 0 ; i < viewModel.getActionList().size(); i ++){
			if(viewModel.getActionList().get(i).getActionDetailList().size()>0){

				OperationItem operationItem = ProjectManager.getInstance().getNullCreateOperation(sourceModel.getBasicModel(), Property.OPERATION_VOID_LOADVIEW);
				if(sourceModel.getBasicModel() instanceof ImageViewModel){
					operationItem.getActionDetailList().add(ProjectManager.getInstance().addSourceLine("["+sourceModel.getName()+"Mask addTarget:self action:@selector("+getOperationName(viewModel.getActionList().get(i))+") forControlEvents:"+getIOSforControlEvents(viewModel.getActionList().get(i).parentID)+"];"+LayoutManager.ENTER_TOKEN));
				}else{
					operationItem.getActionDetailList().add(ProjectManager.getInstance().addSourceLine("["+sourceModel.getName()+" addTarget:self action:@selector("+getOperationName(viewModel.getActionList().get(i))+") forControlEvents:"+getIOSforControlEvents(viewModel.getActionList().get(i).parentID)+"];"+LayoutManager.ENTER_TOKEN));
				}
				LayoutManager.getInstance().addOperationMap(sourceModel, viewModel.getActionList().get(i));

			}

		}




	}
	//오퍼레이션 소스를 생성한다. 첫 시작부분.
	public StringBuffer writeSourceAndroid(UMLModel viewModel){
		this.setSourceModel(viewModel);
		this.setSortActionAndroidDetail(viewModel);


		//사용되는 인터페이스를 확인하여 체크한다. 
		//사용된 오퍼레이션만 작성한다.
		StringBuffer str = new StringBuffer();
		HashMap<String,Boolean> parentMap = new HashMap<String, Boolean>();
		for(int i = 0 ; i < viewModel.getActionList().size(); i ++){
			if(viewModel.getActionList().get(i).getActionDetailList().size()>0){

				ArrayList actionList = viewModel.getActionList().get(i).getActionDetailList();
				for(int j = 0; j < actionList.size(); j++ ){
					parentMap.put(viewModel.getActionList().get(i).parentID, true);
				}
			}
		}

		Iterator<String> iterator = parentMap.keySet().iterator();

		while(iterator.hasNext()){
			String key = iterator.next();

			str.append(viewModel.getInterface(key).writeSourceAndroid());

		}
		return str;
	}
	public String writeListener(InterfaceItem p){

		StringBuffer sb = new StringBuffer();
		//자기 자식들의 인스턴스를 작성한다.
		if(this.type==1){
			sb.append(this.DOT_WORLD + p.android_operation + this.FUNCTION_START_TOKEN + this.NEW_WORLD +
					this.SPACE_TOKEN + p.interfaceName + this.FUNCTION_START_TOKEN + this.FUNCTION_END_TOKEN + this.BLOCK_START_TOKEN + this.ENTER_TOKEN);
			for(int i = 0; i < p.getItem().size(); i ++){
				sb.append(writeOperation(p.getItem().get(i)));	
			}
			sb.append(this.BLOCK_END_TOKEN+this.FUNCTION_END_TOKEN+this.END_MARK_TOKEN);
			sb.append(this.ENTER_TOKEN);
		}


		return sb.toString();

	}


	// 오퍼레이션 소스 생성 
	// 오퍼레이션 헤더와 디테일값을 생성하기 시작한다.
	public String writeOperation(OperationItem p){
		StringBuffer sb = new StringBuffer();

		sb.append(this.writeOperationHead(p));
		sb.append(this.BLOCK_START_TOKEN+this.ENTER_TOKEN);
		sb.append(writeActionDetail(p));
		sb.append(this.BLOCK_END_TOKEN+this.ENTER_TOKEN);


		return sb.toString();

	}

	public String writeOperationHead(OperationItem p){
		StringBuffer sb = new StringBuffer();
		String  str = "";
		String param = "";
		if(this.type==1){
			str = getScope(p.android_scope.toString())+this.SPACE_TOKEN
					+getStype(p.android_stype)+this.SPACE_TOKEN+p.android_name.trim();
			param = this.FUNCTION_START_TOKEN+this.writeParameters(p.getAndroid_Params())+this.FUNCTION_END_TOKEN;

		}
		else if(this.type==2){ //IOS


			str = getScope(p.ios_scope.toString())+this.SPACE_TOKEN
					+this.FUNCTION_START_TOKEN+getStype(p.ios_stype)+this.FUNCTION_END_TOKEN+getOperationName(p);
			param = this.writeParameters(p.getIos_Params());

		}



		sb.append(str+param);
		return sb.toString();
	}
	public String getOperationName(OperationItem p){
		StringBuffer str = new StringBuffer();
		if(this.type==ANDROID){
			return p.android_name.trim();
		}else if(this.type==IOS){
			UMLModel model = null;
			if(!p.modelID.equals("")){
				model = ProjectManager.getInstance().getSearchID(p.modelID);
			}
			if(model!=null && model.getAction(p.id)!=null){
				System.out.println(model.getName()+"_"+p.ios_name.trim());
				return model.getName()+"_"+p.ios_name.trim();
			}else{
				return p.ios_name.trim();
			}
		}
		return str.toString();
	}
	public String getStype(String p){

		if(this.type==1){

		}else if(this.type==2){
			if(p.toLowerCase().equals("boolean")){
				return "BOOL";
			}
		}
		return p;
	}
	public String getScope(String p){
		String scope = "";
		if(this.type==1){
			if("0".equals(p)){
				scope = this.PUBLIC_KEY_WORLD;
			}
			else if("2".equals(p)){
				scope = this.PRIVATE_KEY_WORLD;
			}
			else{
				scope = this.PROTECTED_KEY_WORLD;
			}
		}else if(this.type==2){
			if("0".equals(p)){
				scope = this.PUBLIC_IOS_KEY_WORLD;
			}
			else if("2".equals(p)){
				scope = this.PRIVATE_IOS_KEY_WORLD;
			}
			else{
				scope = this.PROTECTED_KEY_WORLD;
			}
		}
		return  scope;
	}
	public String writeParameters(java.util.ArrayList params){
		StringBuffer sb = new StringBuffer();
		if(type==1){
			for(int i=0;i<params.size();i++){
				ParameterItem p =(ParameterItem)params.get(i);
				sb.append(this.writeParameter(p));
				if(i<params.size()-1){
					sb.append(",");
				}
			}
		}else if(type==2){
			for(int i=0;i<params.size();i++){
				sb.append(this.COLON_KEY_TOKEN);
				ParameterItem p =(ParameterItem)params.get(i);
				sb.append(this.writeParameter(p));

			}
		}
		return sb.toString();
	}
	public String writeParameter(ParameterItem p){
		if(type==1){
			String str = p.stype+this.SPACE_TOKEN
					+p.name;
			return str;
		}else if(type==2){


			String str = this.FUNCTION_START_TOKEN + p.stype+this.SPACE_TOKEN+(isStype(p.stype)==false?"":this.ASTERISK_KEY_TOKEN )+ this.FUNCTION_END_TOKEN+p.name;
			return str;
		}
		return "";

	}
	public boolean isStype(String stype){


		if(stype.equals("BOOL"))
			return false;

		return true;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}

	public String writeIosAppDelegate(){
		return "";
	}


	//세부 소스 작성 
	public String writeActionDetail(OperationItem operationItem){
		varNameList.clear(); //변수명 중복방지를 위한 ArrayList를 초기화 시켜준다.
		StringBuffer str = new StringBuffer();
		for(int i = 0; i < operationItem.getActionDetailList().size(); i ++){
			HashMap map = operationItem.getActionDetailList().get(i); //액션타입(오퍼레이션)에서 수행될 액션들을 불러온다.
			Iterator<String> iterator = map.keySet().iterator();

			while(iterator.hasNext()){
				String key = iterator.next();
				//해당 오퍼레이션을 작성하여 준다.
				if(key.equals(Property.ACTION_MOVE_LAYOUT)){
					str.append(writeActionMoveLayout((ActionMoveItem)map.get(key)));
				}else if(key.equals(Property.ACTION_LISTVIEW_ITEM_ONCLICK)){
					str.append(writeActionListViewItemClick((ListViewerItem)map.get(key)));
				}else if(key.equals(Property.ACTION_ALERT)){
					str.append(writeActionAlertMessage((ActionAlertMessageItem)map.get(key)));
				}else if(key.equals(Property.ACTION_WEBVIEW_LOADURL)){
					str.append(writeActionLoadURL((String)map.get(key)));
				}else if(key.equals(Property.ACTION_SOURCE_TEXT)){
					str.append((String)map.get(key));
				}else if(key.equals(Property.ACTION_MOBILE_SERVICE_CALL)){
					str.append(writeMobileServiceCall((ActionMobileServiceCallItem)map.get(key)));
				}else if(key.equals(Property.ACTION_AUTO_MOVE_LAYOUT)){
					str.append(writeAutoMoveLayout((ActionAutoMoveActionItem)map.get(key)));
				}
			}

		}
		if(type == 2){
			if(operationItem.id.equals(Property.OPERATION_ID_INIT)){
				str.append("return self"+END_MARK_TOKEN+ENTER_TOKEN);
			}
		}
		operationItem.getActionDetailList().add(ProjectManager.getInstance().addSourceLine(str.toString()));

		return str.toString();
	}
	public String writeAutoMoveLayout(ActionAutoMoveActionItem item){
		StringBuffer str = new StringBuffer();
		if(type==ANDROID){

		}else if(type==IOS){

		}
		return str.toString();
	}
	public String writeMobileServiceCall(ActionMobileServiceCallItem item){
		StringBuffer str = new StringBuffer();
		if(type==ANDROID){
			String varName = getVarNumberCount("intent");
			if(item.getId().equals(Property.ACTION_MOBILE_SERVICE_CALL_APP)){
				str.append("Intent"+this.SPACE_TOKEN+varName+this.SPACE_TOKEN+"="+this.SPACE_TOKEN+"getPackageManager().getLaunchIntentForPackage("+QUO_WORLD+item.getValue()+QUO_WORLD+")"+this.END_MARK_TOKEN+this.ENTER_TOKEN);
				str.append("startActivity"+this.FUNCTION_START_TOKEN+varName+this.FUNCTION_END_TOKEN+this.END_MARK_TOKEN+this.ENTER_TOKEN);								
			}else if(item.getId().equals(Property.ACTION_MOBILE_SERVICE_CALL_TEL)){
				str.append("Intent"+this.SPACE_TOKEN+varName+this.SPACE_TOKEN+"="+this.SPACE_TOKEN+this.NEW_WORLD+this.SPACE_TOKEN+"Intent"
						+this.FUNCTION_START_TOKEN+QUO_WORLD+"android.intent.action.CALL"+QUO_WORLD+","+"Uri.parse"+FUNCTION_START_TOKEN+QUO_WORLD+"tel"+COLON_KEY_TOKEN+item.getValue()+QUO_WORLD+FUNCTION_END_TOKEN+this.FUNCTION_END_TOKEN+this.END_MARK_TOKEN+this.ENTER_TOKEN);
				str.append("startActivity"+this.FUNCTION_START_TOKEN+varName+this.FUNCTION_END_TOKEN+this.END_MARK_TOKEN+this.ENTER_TOKEN);
			}else if(item.getId().equals(Property.ACTION_MOBILE_SERVICE_CALL_KAKAO)){
				str.append("Intent"+this.SPACE_TOKEN+varName+this.SPACE_TOKEN+"="+this.SPACE_TOKEN+this.NEW_WORLD+this.SPACE_TOKEN+"Intent"+this.FUNCTION_START_TOKEN+"Intent.ACTION_SEND"+this.FUNCTION_END_TOKEN+this.END_MARK_TOKEN+this.ENTER_TOKEN);
				str.append(varName+DOT_WORLD+"setType"+FUNCTION_START_TOKEN+QUO_WORLD+"text/plain"+QUO_WORLD+FUNCTION_END_TOKEN+this.END_MARK_TOKEN+this.ENTER_TOKEN);	
				str.append(varName+DOT_WORLD+"putExtra"+FUNCTION_START_TOKEN+"Intent.EXTRA_SUBJECT"+COMMAMA_WORLD+QUO_WORLD+ProjectManager.getInstance().getAndroidAppName()+QUO_WORLD+FUNCTION_END_TOKEN+END_MARK_TOKEN+ENTER_TOKEN);
				str.append(varName+DOT_WORLD+"putExtra"+FUNCTION_START_TOKEN+"Intent.EXTRA_TEXT"+COMMAMA_WORLD+QUO_WORLD+item.getValue()+QUO_WORLD+FUNCTION_END_TOKEN+END_MARK_TOKEN+ENTER_TOKEN);
				str.append(varName+DOT_WORLD+"setPackage"+FUNCTION_START_TOKEN+QUO_WORLD+"com.kakao.talk"+QUO_WORLD+FUNCTION_END_TOKEN+END_MARK_TOKEN+ENTER_TOKEN);
				str.append("startActivity"+this.FUNCTION_START_TOKEN+varName+this.FUNCTION_END_TOKEN+this.END_MARK_TOKEN+this.ENTER_TOKEN);				
			}else if(item.getId().equals(Property.ACTION_MOBILE_SERVICE_CALL_MESSAGE)){
				str.append("Intent"+this.SPACE_TOKEN+varName+this.SPACE_TOKEN+"="+this.SPACE_TOKEN+this.NEW_WORLD+this.SPACE_TOKEN+"Intent"+this.FUNCTION_START_TOKEN+"Intent.ACTION_SENDTO"+this.FUNCTION_END_TOKEN+this.END_MARK_TOKEN+this.ENTER_TOKEN);
				str.append(varName+DOT_WORLD+"setType"+FUNCTION_START_TOKEN+QUO_WORLD+"text/plain"+QUO_WORLD+FUNCTION_END_TOKEN+this.END_MARK_TOKEN+this.ENTER_TOKEN);	
				str.append(varName+DOT_WORLD+"putExtra"+FUNCTION_START_TOKEN+QUO_WORLD+"sms_body"+QUO_WORLD+COMMAMA_WORLD+QUO_WORLD+item.getValue()+QUO_WORLD+FUNCTION_END_TOKEN+END_MARK_TOKEN+ENTER_TOKEN);
				str.append(varName+DOT_WORLD+"setData"+FUNCTION_START_TOKEN+"Uri.parse"+FUNCTION_START_TOKEN+QUO_WORLD+"smsto"+COLON_KEY_TOKEN+QUO_WORLD+FUNCTION_END_TOKEN+FUNCTION_END_TOKEN+END_MARK_TOKEN+ENTER_TOKEN);
				//				str.append(varName+DOT_WORLD+"setPackage"+FUNCTION_START_TOKEN+QUO_WORLD+"com.kakao.talk"+QUO_WORLD+FUNCTION_END_TOKEN+END_MARK_TOKEN+ENTER_TOKEN);
				str.append("startActivity"+this.FUNCTION_START_TOKEN+varName+this.FUNCTION_END_TOKEN+this.END_MARK_TOKEN+this.ENTER_TOKEN);
			}
		}else if(type==IOS){
			if(item.getId().equals(Property.ACTION_MOBILE_SERVICE_CALL_APP)){
				str.append(SQUARE_BRACKET_START_KEY_TOKEN+SQUARE_BRACKET_START_KEY_TOKEN+"UIApplication"+SPACE_TOKEN+"sharedApplication"+SQUARE_BRACKET_END_KEY_TOKEN+SPACE_TOKEN+"openURL"+COLON_KEY_TOKEN+SQUARE_BRACKET_START_KEY_TOKEN+"NSURL"+SPACE_TOKEN+"URLWithString"+COLON_KEY_TOKEN+"@"+QUO_WORLD+"AppB://"+item.getValue()+QUO_WORLD+SQUARE_BRACKET_END_KEY_TOKEN+SQUARE_BRACKET_END_KEY_TOKEN+END_MARK_TOKEN+ENTER_TOKEN);    
			}else if(item.getId().equals(Property.ACTION_MOBILE_SERVICE_CALL_TEL)){
				String varName = getVarNumberCount("controller");
				str.append("UIWebView"+SPACE_TOKEN+ASTERISK_KEY_TOKEN+varName+SPACE_TOKEN+EQUALS_WORLD+SQUARE_BRACKET_START_KEY_TOKEN+SQUARE_BRACKET_START_KEY_TOKEN+"UIWebView"+SPACE_TOKEN+"alloc"+SQUARE_BRACKET_END_KEY_TOKEN+"init"+SQUARE_BRACKET_END_KEY_TOKEN+END_MARK_TOKEN+ENTER_TOKEN);
				str.append(SQUARE_BRACKET_START_KEY_TOKEN+varName+SPACE_TOKEN+"loadRequest"+COLON_KEY_TOKEN+SQUARE_BRACKET_START_KEY_TOKEN+"NSURLRequest"+SPACE_TOKEN+"requestWithURL"+COLON_KEY_TOKEN+SQUARE_BRACKET_START_KEY_TOKEN+"NSURL"+SPACE_TOKEN+"URLWithString"+COLON_KEY_TOKEN+SQUARE_BRACKET_START_KEY_TOKEN+"@"+QUO_WORLD+"tel://"+QUO_WORLD);
				str.append("stringByAppendingString"+COLON_KEY_TOKEN+"@"+QUO_WORLD+item.getValue()+QUO_WORLD+SQUARE_BRACKET_END_KEY_TOKEN+SQUARE_BRACKET_END_KEY_TOKEN+SQUARE_BRACKET_END_KEY_TOKEN+SQUARE_BRACKET_END_KEY_TOKEN+END_MARK_TOKEN+ENTER_TOKEN);
			}else if(item.getId().equals(Property.ACTION_MOBILE_SERVICE_CALL_KAKAO)){
				str.append(SQUARE_BRACKET_START_KEY_TOKEN+SQUARE_BRACKET_START_KEY_TOKEN+"UIApplication"+SPACE_TOKEN+"sharedApplication"+SQUARE_BRACKET_END_KEY_TOKEN+SPACE_TOKEN+"openURL"+COLON_KEY_TOKEN+SQUARE_BRACKET_START_KEY_TOKEN+"NSURL"+SPACE_TOKEN+"URLWithString"+COLON_KEY_TOKEN+"@"+QUO_WORLD+"kakaolink://sendurl?msg=["+item.getValue()+"]&url=[]&appid=[]&appver=[]"+QUO_WORLD+SQUARE_BRACKET_END_KEY_TOKEN+SQUARE_BRACKET_END_KEY_TOKEN+END_MARK_TOKEN+ENTER_TOKEN);
			}else if(item.getId().equals(Property.ACTION_MOBILE_SERVICE_CALL_MESSAGE)){
				String varName = getVarNumberCount("controller");
				str.append("MFMessageComposeViewController"+SPACE_TOKEN+ASTERISK_KEY_TOKEN+varName+SPACE_TOKEN+EQUALS_WORLD+SPACE_TOKEN+SQUARE_BRACKET_START_KEY_TOKEN+SQUARE_BRACKET_START_KEY_TOKEN+"MFMessageComposeViewController"+SPACE_TOKEN+"alloc"+SQUARE_BRACKET_END_KEY_TOKEN+"init"+SQUARE_BRACKET_END_KEY_TOKEN+END_MARK_TOKEN+ENTER_TOKEN);
				str.append(varName+DOT_WORLD+"delegate"+SPACE_TOKEN +EQUALS_WORLD +"self"+END_MARK_TOKEN+ENTER_TOKEN);
				str.append("if"+FUNCTION_START_TOKEN+SQUARE_BRACKET_START_KEY_TOKEN+"MFMessageComposeViewController" +SPACE_TOKEN+"canSendText"+SQUARE_BRACKET_END_KEY_TOKEN+FUNCTION_END_TOKEN+BLOCK_START_TOKEN+ENTER_TOKEN);
				str.append(varName+DOT_WORLD+"body"+SPACE_TOKEN+EQUALS_WORLD+"@"+QUO_WORLD+item.getValue()+QUO_WORLD+END_MARK_TOKEN+ENTER_TOKEN);
				str.append(varName+DOT_WORLD+"messageComposeDelegate"+SPACE_TOKEN+EQUALS_WORLD+"self"+END_MARK_TOKEN+ENTER_TOKEN);
				str.append(SQUARE_BRACKET_START_KEY_TOKEN+"self"+SPACE_TOKEN+"presentModalViewController"+COLON_KEY_TOKEN+varName+SPACE_TOKEN+"animated"+COLON_KEY_TOKEN+"YES"+SQUARE_BRACKET_END_KEY_TOKEN+END_MARK_TOKEN+BLOCK_END_TOKEN+ENTER_TOKEN);

			}
		}
		return str.toString();
	}
	public String writeActionLoadURL(String text){
		StringBuffer str = new StringBuffer();
		if(type==ANDROID){
			str.append("view"+this.DOT_WORLD+"loadUrl"+FUNCTION_START_TOKEN+"url"+FUNCTION_END_TOKEN+END_MARK_TOKEN+ENTER_TOKEN);

			str.append("return"+this.SPACE_TOKEN+"true"+END_MARK_TOKEN+ENTER_TOKEN);
		}else if(type==IOS){
		}
		return str.toString();
	}
	public String writeActionAlertMessage(ActionAlertMessageItem model){
		StringBuffer str = new StringBuffer();
		String var = getVarNumberCount("alert");
		if(type==ANDROID){
			if(model!=null){
				String title = model.getTitle();
				String message = model.getMessage();

				str.append("AlertDialog.Builder"+this.SPACE_TOKEN+var+this.SPACE_TOKEN + this.EQUALS_WORLD + this.SPACE_TOKEN + this.NEW_WORLD + this.SPACE_TOKEN+ "AlertDialog.Builder" + this.FUNCTION_START_TOKEN + ProjectManager.getInstance().getAeroLayoutModel(this.getSourceModel()).getName()+this.DOT_WORLD+"this" + this.FUNCTION_END_TOKEN+this.END_MARK_TOKEN+this.ENTER_TOKEN);
				str.append(var+this.DOT_WORLD+"setTitle"+this.FUNCTION_START_TOKEN+this.QUO_WORLD+title+this.QUO_WORLD+this.FUNCTION_END_TOKEN+this.END_MARK_TOKEN+this.ENTER_TOKEN);
				str.append(var+this.DOT_WORLD+"setMessage"+this.FUNCTION_START_TOKEN+this.QUO_WORLD+message+this.QUO_WORLD+this.FUNCTION_END_TOKEN+this.END_MARK_TOKEN+this.ENTER_TOKEN);
				str.append(var+this.DOT_WORLD+"setPositiveButton"+this.FUNCTION_START_TOKEN
						+this.QUO_WORLD+"OK"+this.QUO_WORLD+this.COMMAMA_WORLD+ this.SPACE_TOKEN + this.NEW_WORLD +this.SPACE_TOKEN + "DialogInterface"+this.DOT_WORLD+"OnClickListener"+this.FUNCTION_START_TOKEN+this.FUNCTION_END_TOKEN+this.BLOCK_START_TOKEN+this.ENTER_TOKEN);
				str.append(this.PUBLIC_KEY_WORLD+this.SPACE_TOKEN + this.VOID_KEY_WORLD + this.SPACE_TOKEN + "onClick" + this.FUNCTION_START_TOKEN +"DialogInterface" + this.SPACE_TOKEN + "dialog" + this.COMMAMA_WORLD + "int" + this.SPACE_TOKEN + "width" + this.FUNCTION_END_TOKEN + this.BLOCK_START_TOKEN+this.ENTER_TOKEN);
				str.append("dialog.dismiss"+this.FUNCTION_START_TOKEN+this.FUNCTION_END_TOKEN+this.END_MARK_TOKEN+this.ENTER_TOKEN);
				str.append(this.BLOCK_END_TOKEN + this.ENTER_TOKEN);
				str.append(this.BLOCK_END_TOKEN+this.FUNCTION_END_TOKEN+this.DOT_WORLD+"show"+this.FUNCTION_START_TOKEN+this.FUNCTION_END_TOKEN+this.END_MARK_TOKEN+this.ENTER_TOKEN);

			}
		}else if(type==IOS){
			if(model!=null){
				String title = model.getTitle();
				String message = model.getMessage();

				str.append("UIAlertView"+SPACE_TOKEN+ASTERISK_KEY_TOKEN+var+SPACE_TOKEN+EQUALS_WORLD+SPACE_TOKEN+SQUARE_BRACKET_START_KEY_TOKEN+SQUARE_BRACKET_START_KEY_TOKEN+"UIAlertView"+SPACE_TOKEN+"alloc"+
						SQUARE_BRACKET_END_KEY_TOKEN+"initWithTitle:@"+QUO_WORLD+title+QUO_WORLD+SPACE_TOKEN+"message"+COLON_KEY_TOKEN+"@"+QUO_WORLD+message+QUO_WORLD+SPACE_TOKEN+"delegate"+COLON_KEY_TOKEN+"self"+SPACE_TOKEN+"cancelButtonTitle"+COLON_KEY_TOKEN+"@"+QUO_WORLD+"OK"+QUO_WORLD+SPACE_TOKEN+"otherButtonTitles"+ COLON_KEY_TOKEN+"nil"+SQUARE_BRACKET_END_KEY_TOKEN+END_MARK_TOKEN+ ENTER_TOKEN);
				str.append(SQUARE_BRACKET_START_KEY_TOKEN+var+SPACE_TOKEN+"show"+SQUARE_BRACKET_END_KEY_TOKEN+END_MARK_TOKEN+ENTER_TOKEN);


			}
		}
		return str.toString();
	}
	public String writeActionListViewItemClick(ListViewerItem item){
		StringBuffer str = new StringBuffer();
		if(this.type==ANDROID){
			for(int i = 0 ; i < item.getChildren().length; i ++){
				ListViewerItem listViewerItem = (ListViewerItem)item.getChildren()[i];
				str.append("if"+this.FUNCTION_START_TOKEN+"arg2=="+i+this.FUNCTION_END_TOKEN+this.BLOCK_START_TOKEN+this.ENTER_TOKEN);
				Iterator<String> iterator = listViewerItem.getActionMap().keySet().iterator();

				while(iterator.hasNext()){
					String key = iterator.next();
					if(key.equals(Property.ACTION_MOVE_LAYOUT)){
						str.append(writeActionMoveLayout((ActionMoveItem)listViewerItem.getActionMap().get(key)));
					}else if(key.equals(Property.ACTION_ALERT)){
						str.append(writeActionAlertMessage((ActionAlertMessageItem)listViewerItem.getActionMap().get(key)));
					}
				}

				str.append(this.BLOCK_END_TOKEN+this.ENTER_TOKEN);
				//item.getChildren()[i]
			}
		}else if(this.type==IOS){
			for(int i = 0 ; i < item.getChildren().length; i ++){
				ListViewerItem listViewerItem = (ListViewerItem)item.getChildren()[i];
				str.append("if"+LayoutManager.FUNCTION_START_TOKEN+LayoutManager.SQUARE_BRACKET_START_KEY_TOKEN+"indexPath row"+LayoutManager.SQUARE_BRACKET_END_KEY_TOKEN+"=="+i+LayoutManager.FUNCTION_END_TOKEN+LayoutManager.BLOCK_START_TOKEN+LayoutManager.ENTER_TOKEN);
				Iterator<String> iterator = listViewerItem.getActionMap().keySet().iterator();

				while(iterator.hasNext()){
					String key = iterator.next();
					if(key.equals(Property.ACTION_MOVE_LAYOUT)){
						str.append(writeActionMoveLayout((ActionMoveItem)listViewerItem.getActionMap().get(key)));
					}else if(key.equals(Property.ACTION_ALERT)){
						str.append(writeActionAlertMessage((ActionAlertMessageItem)listViewerItem.getActionMap().get(key)));
					}
				}
				str.append(LayoutManager.BLOCK_END_TOKEN+LayoutManager.ENTER_TOKEN);
				//item.getChildren()[i]
			}
		}
		return str.toString();
	}

	public String writeActionMoveLayout(ActionMoveItem actionMoveItem){
		StringBuffer str = new StringBuffer();

		UMLModel viewModel = ProjectManager.getInstance().getSearchID(actionMoveItem.getModelID());
		if(type==ANDROID){
			String varName = getVarNumberCount("intent");
			str.append("Intent"+this.SPACE_TOKEN+varName+this.SPACE_TOKEN+"="+this.SPACE_TOKEN+this.NEW_WORLD+this.SPACE_TOKEN+"Intent"
					+this.FUNCTION_START_TOKEN+ProjectManager.getInstance().getAeroLayoutModel(sourceModel).getName()+this.DOT_WORLD+"this"+","+viewModel.getName()+this.DOT_WORLD+"class"+this.FUNCTION_END_TOKEN+this.END_MARK_TOKEN+this.ENTER_TOKEN);


			str.append("startActivity"+this.FUNCTION_START_TOKEN+varName+this.FUNCTION_END_TOKEN+this.END_MARK_TOKEN+this.ENTER_TOKEN);
		}else if(type==IOS){
			String varName = getVarNumberCount("viewController");

			str.append(viewModel.getName()+SPACE_TOKEN+ASTERISK_KEY_TOKEN+varName+SPACE_TOKEN+EQUALS_WORLD+SQUARE_BRACKET_START_KEY_TOKEN+SQUARE_BRACKET_START_KEY_TOKEN+viewModel.getName()+SPACE_TOKEN+"alloc"+SQUARE_BRACKET_END_KEY_TOKEN+"init"+SQUARE_BRACKET_END_KEY_TOKEN+END_MARK_TOKEN+ENTER_TOKEN);
			str.append(SQUARE_BRACKET_START_KEY_TOKEN+varName +SPACE_TOKEN+"setModalTransitionStyle"+COLON_KEY_TOKEN+"UIModalTransitionStylePartialCurl"+SQUARE_BRACKET_END_KEY_TOKEN+END_MARK_TOKEN+ENTER_TOKEN);
			str.append(SQUARE_BRACKET_START_KEY_TOKEN+"self"+DOT_WORLD+"navigationController"+SPACE_TOKEN+"pushViewController"+COLON_KEY_TOKEN+varName+SPACE_TOKEN+"animated"+COLON_KEY_TOKEN+"YES"+SQUARE_BRACKET_END_KEY_TOKEN+END_MARK_TOKEN+ENTER_TOKEN);

		}
		return str.toString();
	}

	public void writeBackgroundImageIOS(UMLModel basicModel){
		LayoutManager.getInstance().setSourceModel(basicModel);
		if(basicModel.getPropertyValue(Property.ID_IMG)!=null){
			OperationItem operationItem = ProjectManager.getInstance().getNullCreateOperation(basicModel, Property.OPERATION_VOID_LOADVIEW);
			String imageFile = (String)basicModel.getPropertyValue(Property.ID_IMG);
			File backgroundFile = new File(imageFile);
			if(backgroundFile.exists()&& backgroundFile.isFile()){
				LayoutManager.getInstance().copyFile(backgroundFile.getPath(), ProjectManager.getInstance().getIosPath()+LayoutManager.SEPARATOR+LayoutManager.IOS_IMAGES_PATH+LayoutManager.SEPARATOR+backgroundFile.getName());
//				backgroundFile = LayoutManager.getInstance().reNameMedia(ProjectManager.getInstance().getIosPath()+LayoutManager.SEPARATOR+LayoutManager.IOS_IMAGES_PATH+LayoutManager.SEPARATOR+backgroundFile.getName());

				String image = LayoutManager.getInstance().getVarNumberCount(basicModel.getName()+"image");
				StringBuffer stringBuffer = new StringBuffer();
				stringBuffer.append("scrollView.backgroundColor = [UIColor clearColor];"+LayoutManager.ENTER_TOKEN);
				stringBuffer.append("self.view.backgroundColor = [[UIColor alloc] initWithPatternImage:[UIImage imageNamed:@"+LayoutManager.QUO_WORLD+backgroundFile.getName()+LayoutManager.QUO_WORLD+"]];"+LayoutManager.ENTER_TOKEN);
				//				stringBuffer.append("UIImageView *"+image+" = [[UIImageView alloc] initWithImage:[UIImage imageNamed:@"+LayoutManager.QUO_WORLD+backgroundFile.getName()+LayoutManager.QUO_WORLD+"]];"+LayoutManager.ENTER_TOKEN);
				//				stringBuffer.append("[scrollView addSubview:"+image+"];");

				operationItem.getActionDetailList().add(ProjectManager.getInstance().addSourceLine(stringBuffer.toString()));

				LayoutManager.getInstance().addOperationMap(basicModel, operationItem);
			}



		}
	}

	public String writeAutoMoveLayoutAndroid(UMLModel basicModel){
		StringBuffer buffer = new StringBuffer();

		if(basicModel.getPropertyValue(Property.ID_AUTO_MOVE)!=null){
			String varName = LayoutManager.getInstance().getVarNumberCount("intent");
			ActionAutoMoveActionItem actionAutoMoveActionItem = (ActionAutoMoveActionItem)basicModel.getPropertyValue(Property.ID_AUTO_MOVE);
			UMLModel model = ProjectManager.getInstance().getSearchID(actionAutoMoveActionItem.getLayoutID());
			if(model!=null){
				buffer.append(LayoutManager.NEW_WORLD+LayoutManager.SPACE_TOKEN+"Thread"+LayoutManager.FUNCTION_START_TOKEN+"new"+LayoutManager.SPACE_TOKEN+"Runnable"+LayoutManager.FUNCTION_START_TOKEN+LayoutManager.FUNCTION_END_TOKEN+LayoutManager.BLOCK_START_TOKEN+LayoutManager.ENTER_TOKEN);
				buffer.append(LayoutManager.PUBLIC_KEY_WORLD+LayoutManager.SPACE_TOKEN+LayoutManager.VOID_KEY_WORLD+LayoutManager.SPACE_TOKEN+"run"+LayoutManager.FUNCTION_START_TOKEN+LayoutManager.FUNCTION_END_TOKEN+LayoutManager.BLOCK_START_TOKEN+LayoutManager.ENTER_TOKEN);

				buffer.append("try"+LayoutManager.BLOCK_START_TOKEN+LayoutManager.ENTER_TOKEN);
				buffer.append("Thread.sleep"+LayoutManager.FUNCTION_START_TOKEN+actionAutoMoveActionItem.getTime()+"*1000"+LayoutManager.FUNCTION_END_TOKEN+LayoutManager.END_MARK_TOKEN+LayoutManager.ENTER_TOKEN);
				buffer.append(LayoutManager.BLOCK_END_TOKEN+"catch"+LayoutManager.FUNCTION_START_TOKEN+"Throwable ex"+LayoutManager.FUNCTION_END_TOKEN+LayoutManager.BLOCK_START_TOKEN+LayoutManager.BLOCK_END_TOKEN+LayoutManager.ENTER_TOKEN);
				buffer.append("Intent"+LayoutManager.SPACE_TOKEN+varName+LayoutManager.SPACE_TOKEN+"="+LayoutManager.SPACE_TOKEN+LayoutManager.NEW_WORLD+LayoutManager.SPACE_TOKEN+"Intent"+LayoutManager.FUNCTION_START_TOKEN+basicModel.getName()+LayoutManager.DOT_WORLD+"this"+LayoutManager.COMMAMA_WORLD+model.getName()+LayoutManager.DOT_WORLD+"class"+LayoutManager.FUNCTION_END_TOKEN+LayoutManager.END_MARK_TOKEN+LayoutManager.ENTER_TOKEN);
				buffer.append("startActivity"+LayoutManager.FUNCTION_START_TOKEN+varName+LayoutManager.FUNCTION_END_TOKEN+LayoutManager.END_MARK_TOKEN+LayoutManager.ENTER_TOKEN+LayoutManager.ENTER_TOKEN);
				buffer.append("finish"+LayoutManager.FUNCTION_START_TOKEN+LayoutManager.FUNCTION_END_TOKEN+LayoutManager.END_MARK_TOKEN+LayoutManager.ENTER_TOKEN);
				buffer.append(LayoutManager.BLOCK_END_TOKEN+LayoutManager.ENTER_TOKEN);
				buffer.append(LayoutManager.BLOCK_END_TOKEN+LayoutManager.FUNCTION_END_TOKEN+LayoutManager.DOT_WORLD+"start"+LayoutManager.FUNCTION_START_TOKEN+LayoutManager.FUNCTION_END_TOKEN+LayoutManager.END_MARK_TOKEN+LayoutManager.ENTER_TOKEN);

			}
		}
		return buffer.toString();
	}
	public String writeTitleBarCustomIOS(){



		StringBuffer str = new StringBuffer();


		File image = new File(ProjectManager.getInstance().getTitleBarBackgroundImage());
		if(image.exists()&&image.isFile()){
			LayoutManager.getInstance().copyFile(image.getPath(), ProjectManager.getInstance().getIosPath()+LayoutManager.SEPARATOR+LayoutManager.IOS_IMAGES_PATH+LayoutManager.SEPARATOR+image.getName());
//			image = LayoutManager.getInstance().reNameMedia(ProjectManager.getInstance().getIosPath()+LayoutManager.SEPARATOR+LayoutManager.IOS_IMAGES_PATH+LayoutManager.SEPARATOR+image.getName());
			str.append("[[UINavigationBar appearance] setBackgroundImage:[UIImage imageNamed:@"+QUO_WORLD+image.getName()+QUO_WORLD+"] forBarMetrics:UIBarMetricsDefault];");
		}


		if(ProjectManager.getInstance().getTitleBarBackColor()!=null){


			str.append("[nav.navigationBar setTintColor:[UIColor colorWithRed:"+ProjectManager.getInstance().getTitleBarBackColor().red+"/255.0f green:"+ProjectManager.getInstance().getTitleBarBackColor().green+"/255.0f blue:"+ProjectManager.getInstance().getTitleBarBackColor().blue+"/255.0f alpha:1.0]];");
		}


		return str.toString();




	}
	public void writeAutoMoveLayoutIOS(UMLModel basicModel){
		LayoutManager.getInstance().setSourceModel(basicModel);
		OperationItem operationItem = ProjectManager.getInstance().getNullCreateOperation(basicModel.getBasicModel(), Property.OPERATION_VOID_LOADVIEW);
		boolean isNavigationHide = false;
		if(basicModel.getPropertyValue(Property.ID_AUTO_MOVE)!=null){
			String varName = LayoutManager.getInstance().getVarNumberCount("intent");
			ActionAutoMoveActionItem actionAutoMoveActionItem = (ActionAutoMoveActionItem)basicModel.getPropertyValue(Property.ID_AUTO_MOVE);
			UMLModel model = ProjectManager.getInstance().getSearchID(actionAutoMoveActionItem.getLayoutID());
			if(model!=null){
				isNavigationHide = true;
				LayoutManager.getInstance().setSourceModel(basicModel);
				StringBuffer str = new StringBuffer();
				str.append("[self.navigationController setToolbarHidden:YES animated:NO];");
				str.append("[[self navigationController]setNavigationBarHidden:YES animated:NO];");
				operationItem.getActionDetailList().add(ProjectManager.getInstance().addSourceLine(str.toString()));
				LayoutManager.getInstance().addOperationMap(basicModel, operationItem);



				OperationItem viewDidAppearoperationItem = ProjectManager.getInstance().getNullCreateOperation(basicModel, Property.OPERATION_VOID_VIEWDIDAPPEAR);
				str = new StringBuffer();		

				str.append("[NSThread sleepForTimeInterval:"+actionAutoMoveActionItem.getTime()+"];");
				str.append(LayoutManager.getInstance().writeActionMoveLayout(new ActionMoveItem(actionAutoMoveActionItem.getLayoutID())));
				viewDidAppearoperationItem.getActionDetailList().add(ProjectManager.getInstance().addSourceLine(str.toString()));
				LayoutManager.getInstance().addOperationMap(basicModel, viewDidAppearoperationItem);


				LayoutManager.getInstance().setSourceModel(model);				
				OperationItem modelViewDidAppearoperationItem = ProjectManager.getInstance().getNullCreateOperation(model, Property.OPERATION_VOID_VIEWDIDAPPEAR);
				str = new StringBuffer();		
				str.append("self.navigationItem.hidesBackButton = YES;");
				str.append("[self.navigationController setToolbarHidden:YES animated:YES];");
				str.append("[[self navigationController]setNavigationBarHidden:NO animated:NO];");
				modelViewDidAppearoperationItem.getActionDetailList().add(ProjectManager.getInstance().addSourceLine(str.toString()));
				LayoutManager.getInstance().addOperationMap(model, modelViewDidAppearoperationItem);

				LayoutManager.getInstance().setSourceModel(basicModel);

			}
		}


	}


	public String getVarNumberCount(String name){
		int count = 1;
		for(int i = 0 ; i< varNameList.size(); i++){
			Pattern p = Pattern.compile(name+"|[\\p{Digit}]");

			Matcher matcher = p.matcher(varNameList.get(i));
			matcher.find();
			String text = "";
			while(matcher.find()){
				text = text + matcher.group();

			}	
			try{
				int number = Integer.
						parseInt(text);
				if(count<=number){
					count =number+1;
				}
			}catch(Exception e){}
		}

		varNameList.add(name+count);


		return name+count;
	}

	public Document getXMLNewDoc(){

		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder;
		try{
			dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.newDocument();
			return doc;

			//			makeDoc();
			//			makeFile(ProjectManager.getInstance().getProjectPath());

		}catch(Exception e){
			e.printStackTrace();
		}
		return null;

	}


	public void resetFile(String path){
		deleteFile(path);
		makeFile(path);
	}
	public void makeFile(String path){
		try {
			File f = new File(path);
			if(!f.exists()){
				f.mkdirs();
				f.createNewFile();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void deleteFile(String path){

		File f = new File(path);
		if(f.isDirectory()){
			File[] list = f.listFiles();
			for(int i = 0; i < list.length; i++){
				if(list[i].isDirectory())
					deleteFile(list[i].getPath());
				list[i].delete();
			}
			f.delete();
		}else{
			f.delete();
		}

	}

	public File reNameMedia(String path){
		StringBuffer buffer = new StringBuffer();
		Random random = new Random();

		String chars[] = 
				"a,b,c,d,e,f,g,h,i,j,k,l,m,n,o,p,q,r,s,t,u,v,w,x,y,z".split(",");

		for (int i=0 ; i<15 ; i++)
		{
			buffer.append(chars[random.nextInt(chars.length)]);
		}
		File f = new File(path);
		String ext = f.getName().substring(f.getName().indexOf("."),f.getName().length());
		File reNameFile = new File(f.getParent()+SEPARATOR+buffer.toString()+ext);
		f.renameTo(reNameFile);
		return reNameFile;
	}
	public String copyFile(String source, String target){

		if(type==IOS && !new File(source).getName().toLowerCase().endsWith(".mp4")){
			iosResizeImage(source,target);
			return target;
		}else{
			//복사 대상이 되는 파일 생성 
			File sourceFile = new File( source );
			File targetFile = new File( target );

			if(targetFile.exists()){
				if(targetFile.length()==sourceFile.length()){
					return targetFile.getName();
				}
			}
			targetFile.getParentFile().mkdirs();


			//스트림, 채널 선언
			FileInputStream inputStream = null;
			FileOutputStream outputStream = null;
			FileChannel fcin = null;
			FileChannel fcout = null;

			try {
				//스트림 생성
				inputStream = new FileInputStream(sourceFile);
				outputStream = new FileOutputStream(target);
				//채널 생성
				fcin = inputStream.getChannel();
				fcout = outputStream.getChannel();

				//채널을 통한 스트림 전송
				long size = fcin.size();
				fcin.transferTo(0, size, fcout);

			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				//자원 해제
				if(fcout!=null)
					try{
						fcout.close();
					}catch(IOException ioe){}
				if(fcin!=null)
					try{
						fcin.close();
					}catch(IOException ioe){}
				if(outputStream!=null)
					try{
						outputStream.close();
					}catch(IOException ioe){}
				if(inputStream!=null)
					try{
						inputStream.close();
					}catch(IOException ioe){}
			}

			return targetFile.getName();
		}
	}

	public void iosResizeImage(String source, String target){
		try {
			File sourceFile = new File( source );
			File targetFile = new File( target );
			if(targetFile.exists()){
				if(targetFile.length()==sourceFile.length()){
					return;
				}
			}
			UMLModel layoutModel = ProjectManager.getInstance().getAeroLayoutModel(this.getSourceModel().getViewModel());
			int rateType = 0;
			if(layoutModel!=null){
				rateType = (Integer)layoutModel.getPropertyValue(Property.ID_RATE);
			}
			Image image1 = ProjectManager.getInstance().getExtImage(source);
			if(image1!=null){


				int width = Integer.parseInt(String.valueOf(Math.round(image1.getImageData().width*IOS_WIDTH_DISPLAY_PERCENT)));
				int height = 0 ;
				if(rateType==0)
					height = Integer.parseInt(String.valueOf(Math.round(image1.getImageData().height*IOS_HEIGHT_DISPLAY_PERCENT)));
				else
					height = Integer.parseInt(String.valueOf(Math.round(image1.getImageData().height*IOS_WIDTH_DISPLAY_PERCENT)));

				if(layoutModel!=null && layoutModel.getViewModel()==this.getSourceModel().getViewModel()){//배경화면이면 너비 높이 변경
					width = 320;
//					height = 416;
					height = 522;
				}

				BufferedImage srcImg = ImageIO.read(new File(source));
				java.awt.Image imgTarget = srcImg.getScaledInstance(width, height, 4);
				int pixels[] = new int[width * height]; 
				PixelGrabber pg = new PixelGrabber(imgTarget, 0, 0, width, height, pixels, 0, width); 

				pg.grabPixels();

				BufferedImage destImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB); 
				destImg.setRGB(0, 0, width, height, pixels, 0, width);
				
				
				targetFile.mkdirs();
				if(targetFile.exists())
					targetFile.delete();
				
				ImageIO.write(destImg, "PNG", new File(target));


			}
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}

	public void addOperationMap(UMLModel model,OperationItem operationItem){
		model = model.getViewModel();
		String id = "";
		if(model.getBasicModel() instanceof LayoutModel)
			id = model.getID();
		else
			id = ProjectManager.getInstance().getAeroLayoutModel(model.getViewModel()).getID();

		if(operationsMap.get(id)==null){
			ArrayList list = new ArrayList();
			list.add(operationItem);
			operationsMap.put(id, list);
		}else{
			ArrayList list = operationsMap.get(id);
			if(model.getAction(operationItem.id)==null)
				ProjectManager.getInstance().removeOperation(list, operationItem.id);

			list.add(operationItem);

			operationsMap.put(id, list);
		}
	}

	public void clearOperationMap(){
		ArrayList<UMLModel> list = ProjectManager.getInstance().getModels(-1);
		for(int i = 0 ; i < list.size(); i ++){
			if(list.get(i).getViewModel()!=null){
				for(int j = 0; j < list.get(i).getViewModel().getActionList().size(); j++){
					list.get(i).getViewModel().getActionList().get(j).getActionDetailList().clear();	
				}
				
			}
		}
		operationsMap.clear();
	}
	public ArrayList<OperationItem> getOperationMap(UMLModel model){
		String id = "";
		if(model.getBasicModel() instanceof LayoutModel)
			id = model.getViewModel().getID();
		else
			id = ProjectManager.getInstance().getAeroLayoutModel(model.getViewModel()).getID();


		if(operationsMap.get(id)!=null){
			return operationsMap.get(id);
		}
		return null;
	}
	public UMLModel getSourceModel() {
		return sourceModel;
	}
	public void setSourceModel(UMLModel sourceModel) {
		this.sourceModel = sourceModel;
	}
	public  String fileName(File f){
		return f.getName().substring(0,f.getName().lastIndexOf("."));
	}
	public  String fileExt(File f){
		return f.getName().substring(f.getName().lastIndexOf(".")+1,f.getName().length());
	}
}
