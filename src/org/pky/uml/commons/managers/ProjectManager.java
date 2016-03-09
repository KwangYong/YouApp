package org.pky.uml.commons.managers;

import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.draw2d.PositionConstants;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.ui.parts.ScrollingGraphicalViewer;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.FontMetrics;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.ImageLoader;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.pky.uml.browser.ActionBrowser;
import org.pky.uml.browser.common.propertybrowser.Property;
import org.pky.uml.browser.model.model.ModelTreeModel;
import org.pky.uml.editor.LogicEditorInput;
import org.pky.uml.editor.UMLEditor;
import org.pky.uml.editparts.common.UMLEditPart;
import org.pky.uml.image.ImagePoint;
import org.pky.uml.model.AndroidSDKItem;
import org.pky.uml.model.ButtonModel;
import org.pky.uml.model.EditTextAutoCompleteTextViewModel;
import org.pky.uml.model.EditTextDateModel;
import org.pky.uml.model.EditTextModel;
import org.pky.uml.model.EditTextMultiAutoCompleteTextViewModel;
import org.pky.uml.model.EditTextNumberDecimalModel;
import org.pky.uml.model.EditTextNumberModel;
import org.pky.uml.model.EditTextNumberSignedModel;
import org.pky.uml.model.EditTextNumericPasswordModel;
import org.pky.uml.model.EditTextPasswordModel;
import org.pky.uml.model.EditTextPersonNameModel;
import org.pky.uml.model.EditTextPhoneModel;
import org.pky.uml.model.EditTextTextEmailAddressModel;
import org.pky.uml.model.EditTextTextMultiLineModel;
import org.pky.uml.model.EditTextTextPostalAddressModel;
import org.pky.uml.model.EditTextTimeModel;
import org.pky.uml.model.ExpandableListViewModel;
import org.pky.uml.model.FragmentModel;
import org.pky.uml.model.GoogleMapModel;
import org.pky.uml.model.ImageViewModel;
import org.pky.uml.model.LayoutModel;
import org.pky.uml.model.ListViewModel;
import org.pky.uml.model.OperationItem;
import org.pky.uml.model.PackageModel;
import org.pky.uml.model.TextViewModel;
import org.pky.uml.model.UMLDiagramModel;
import org.pky.uml.model.VideoModel;
import org.pky.uml.model.ViewModel;
import org.pky.uml.model.WebViewModel;
import org.pky.uml.model.YoutubeModel;
import org.pky.uml.model.command.TreeParent;
import org.pky.uml.model.common.UMLModel;
import org.w3c.dom.Document;

public class ProjectManager {

	public static final int OS_MAC = 1;
	public static final int OS_WINDOWS = 2;
	public static final String TEXT_TOKEN = "<tool_token>";

	private static ProjectManager instance;
	private static UMLEditManager umlEditManager; 
	private UMLEditor umlEditor = null; //ModelBrowser와 상호 연동을 위해서 임시 UmlEditor 
	public IWorkbenchWindow window = null;

	private ArrayList<UMLEditPart> refreshList = new ArrayList();
	private ArrayList<AndroidSDKItem> androidSDKList = new ArrayList<AndroidSDKItem>();
	private ArrayList selectList = new ArrayList();

	private HashMap<String, Image> imageMap = new HashMap(); //불러온 이미지 내역 저장 Map
	private HashMap<String, Long> imageHistoryMap = new HashMap(); //불러온 이미지 내역 저장 Map
	private UMLDiagramModel openDiagramModel; //현재 오픈 되어있는 다이어그램
	private boolean isMakeAndroid = false;
	private boolean isMakeIos = false;
	private boolean isLoad = false;
	private boolean isCreate = false;//모델 생성 
	private boolean isActionSelection = false;
	private boolean isMultiClick = false;

	private String selectShodwID = "";
	private String projectPath ="";

	private String android_versionCode = "1";
	private String android_versionName = "1.0";
	private int android_MinSdkVersion = 8;
	private int android_TargetSdkVersion = 16;

	private String android_allowBackup = "true";
	private String android_icon = "@drawable/";
	private String android_label = "@string/app_name";
	private String android_theme = "@style/AppTheme";

	private String androidPackage = "";//안드로이드 패키지 
	private String androidAppName = "";//안드로이드 앱 이름
	private String androidPath = "";//안드로이드 저장경로
	private String androidKeyStore = ""; //keyStore 저장경로 
	private String androidAPK = ""; //APK저장 위치 

	private String iosAppName = ""; // IOS 앱 이름 
	private String iosPath = ""; //IOS 저장경로

	private String applicationLocation = ""; //프로그램 위치 
	private int osVer = -1; 


	private HashMap<String,Object> tempData = new HashMap();

	private String titleBarBackgroundImage = "";

	private RGB titleBarBackColor = null;;





	public static final String[] SCOPEA_SET = new String[] {
		"+", "#", "-"
	};

	public static String[] STATE_TYPE_SET = new String[] {
		"do", "entry", "exit"
	};




	public ProjectManager(){
		window = (IWorkbenchWindow)PlatformUI.getWorkbench().getActiveWorkbenchWindow();

		setApplicationLocation(System.getProperty("java.class.path"));//실행된 위치 기록

		String osName = System.getProperty("os.name");

		if(osName.toLowerCase().startsWith("windows")){
			this.setOsVer(this.OS_WINDOWS);
		}else if(osName.toLowerCase().startsWith("mac")){
			this.setOsVer(this.OS_MAC);
		}

		initAndroid();

	}
	public static ProjectManager getInstance() {
		if (instance == null) {
			instance = new ProjectManager();


			return instance;
		}
		else {
			return instance;
		}
	}
	public TreeParent getSearchID(TreeParent model,String id){
		if(model==null)
			return null;
		if(model.getId().equals(id)){
			return model;
		}
		for(int i = 0; i < model.getChildren().length; i ++){

			if(model.getChildren()[i].getId().equals(id)){
				return (TreeParent)model.getChildren()[i]; 
			}else{
				getSearchID((TreeParent)model.getChildren()[i],id);	
			}



		}

		return null;	
	}
	public void init(){


		ArrayList<UMLModel> list = ProjectManager.getInstance().getModels(1);

		for(int i=0; i < list.size(); i ++){
			UMLDiagramModel diagram = (UMLDiagramModel)list.get(i);
			window.getActivePage().closeEditor(diagram.getUmlEditor(), false);
		}
		refreshList.clear();
		openDiagramModel = null;
		ModelBrowserManager.getInstance().getModelBrowser().init();
	}

	//Android 프로젝트 기본 세팅 프로그램 구동 될때 실
	public void initAndroid(){
		ArrayList sdkList = new ArrayList();

		//		list.add(new AndroidSDKItem("Android 2.3(Gingerbread","",9));
		sdkList.add(new AndroidSDKItem("Android 2.3.3(Gingerbread)","",10));
		sdkList.add(new AndroidSDKItem("Android 3.0(Honeycomb)","",11));
		sdkList.add(new AndroidSDKItem("Android 3.1(Honeycomb)","",12));
		sdkList.add(new AndroidSDKItem("Android 3.2(Honeycomb)","",13));
		//		list.add(new AndroidSDKItem("Android 4.0(IceCreamSandwich)","",15));
		sdkList.add(new AndroidSDKItem("Android 4.0.3(IceCreamSandwich)","",15));
		sdkList.add(new AndroidSDKItem("Android 4.1(Jelly Bean)","",16));
		sdkList.add(new AndroidSDKItem("Android 4.2(Jelly Bean)","",17));



		this.setAndroidSDKList(sdkList);

		ArrayList themeList = new ArrayList();

		//		themeList.add(new AndroidThemeItem("AppTheme", name))
	}
	public Rectangle setModelContainsReLocation(UMLModel model,Rectangle chgRect){
		UMLDiagramModel diagramModel = getDiagramModel(model);
		Rectangle modelRect = model.getLocationRect();
		Rectangle reLocationRect = new Rectangle(modelRect);

		boolean isIntersection = true;

		boolean isLoop = false;//무한루프 방지용 

		while(isIntersection){
			for(int i = 0; i < diagramModel.getChildren().size(); i ++){
				UMLModel childModel = diagramModel.getChildren().get(i);

				boolean isChkLocation = true; // 현재 Child에서는 겹치는값 없는지 여부 조사 
				if(childModel.getBasicModel()!=null && childModel.getBasicModel() instanceof LayoutModel && model != childModel){
					while(isChkLocation){
						Rectangle interRect = childModel.getLocationRect().getIntersection(chgRect);

						if(interRect.width!=0 || interRect.height!=0){
							if(!isLoop)
								isLoop = true;
							else
								return modelRect; // 만약 isLoop가 true 상태에서 또 들어왔다면 여러곳에서 겹치는것으로 간주하고 메세지를 뿌려주고 진행하지 않는다.
							if(interRect.height>interRect.width){
								if(interRect.width!=0){
									i = 0;

									if(chgRect.x < childModel.getLocation().x) //왼쪽 - > 오른쪽 접근 
										chgRect.x = chgRect.x - interRect.width -1;
									else//오른쪽 -> 왼쪽 접근 
										chgRect.x = chgRect.x + interRect.width +1;	
									interRect = childModel.getLocationRect().getIntersection(chgRect);// 재 계산 하여 겹치는 부분있는지 확인한다 .
								}
							}else{
								if(interRect.height!=0){
									i = 0;

									if(chgRect.y <childModel.getLocation().y){ //위쪽 - > 하단 접근
										chgRect.y = chgRect.y - interRect.height -1 ;
									}else{
										chgRect.y = chgRect.y + interRect.height +1 ;
									}
									interRect = childModel.getLocationRect().getIntersection(chgRect); // 재 계산 하여 겹치는 부분있는지 확인한다 .
								}
							}
						}
						if(interRect.width==0 && interRect.height==0){
							isChkLocation = false; // 현재 Child에서는 겹치는값 없으므로 while 문 종료 
						}

					}


				}
				if(i == diagramModel.getChildren().size()-1){
					isIntersection = false;
				}
			}


		}
		return chgRect;
	}
	// 자신이 속한 하위 객체들을 같이 움직이도록 하는 Operation
	public void setMoveAeroModel(UMLModel model,Rectangle rect,Point p){ 
		if(isCreate)
			return;
		UMLDiagramModel diagram = getDiagramModel(model);
		if(!(model instanceof ViewModel))
			return;
		if(diagram!=null){

			ArrayList<UMLModel> list = ProjectManager.getInstance().getAeroModels(rect);
			for(int i = 0; i < list.size(); i ++){
				if(model!=list.get(i))
					list.get(i).setLocation(list.get(i).getLocation().x + p.x , list.get(i).getLocation().y + p.y);
			}
		}
	}
	//
	public Rectangle setLayouyRectPosition(UMLModel model, Rectangle location){
		if(model.getBasicModel() instanceof LayoutModel)
			return location;

		UMLModel viewModel = ProjectManager.getInstance().getAeroLayoutModel(model);

		if(viewModel!=null){


			Rectangle rect = new Rectangle(viewModel.getLocation().x+28, viewModel.getLocation().y+89, 318, 538);
			return location;


		}
		return location;
	}

	// 모델 위치 재조정 
	public Rectangle getModelRePosition(UMLModel model,Rectangle location){

		if(model!=null && !(model.getBasicModel() instanceof LayoutModel)){
			UMLModel layoutModel = ProjectManager.getInstance().getAeroLayoutModel(ProjectManager.getInstance().getActiveDiagram(),location ); // 이동하는 위치가 Rect안에 들어가는 확인 
			if(layoutModel==null){//벗어났을경우 가장자리 확인후 붙여준다.
				//이동전 자기 상위의 Layout모델의 Rect를 가지고온다.
				layoutModel = ProjectManager.getInstance().getAeroLayoutModel(model.getLocation());
				if(layoutModel!=null){
					Rectangle beforeRect = layoutModel.getLocationRect();
					Rectangle rect = new Rectangle(beforeRect.x+LayoutModel.DISPLAY_X,beforeRect.y+LayoutModel.DISPLAY_Y,LayoutModel.DISPLAY_WIDTH,LayoutModel.DISPLAY_HEIGHT);

					/**
				NORTH 북 
				SOUTH 남 
				WEST 서 
				EAST 동 
					 **/

					int position = rect.getPosition(new Point(location.x,location.y));
					System.out.println("--------------------->"+position);
					if(position==PositionConstants.NORTH){
						location.y = rect.y;
						Rectangle interRectangle = location.getIntersection(rect);

						if(interRectangle!=null){

							location.x = location.x - (location.width - interRectangle.width )-1 ;
						}
					}else if(position==PositionConstants.NORTH_EAST){
						location.y = rect.y;
						location.x = rect.x + rect.width - location.width;
					}else if(position==PositionConstants.NORTH_WEST){
						location.y = rect.y;
						location.x = rect.x;
					}else if(position==PositionConstants.WEST){
						location.x = rect.x;
						Rectangle interRectangle = location.getIntersection(rect);

						if(interRectangle!=null){
							location.y = location.y - (location.height-interRectangle.height)-1;
						}
					}else if(position==PositionConstants.EAST){
						location.x = rect.x + rect.width - location.width;
						Rectangle interRectangle = location.getIntersection(rect);
						if(interRectangle!=null){
							location.y = location.y - (location.height-interRectangle.height)-1;
						}
					}else if(position==PositionConstants.SOUTH){
						location.y = (rect.y + rect.height) -location.height;
						Rectangle interRectangle = location.getIntersection(rect);
						if(interRectangle!=null){
							location.x = location.x - (location.width - interRectangle.width )-1 ;
						}
					}else if(position==PositionConstants.SOUTH_EAST){
						location.y = (rect.y + rect.height) -location.height;
						location.x = rect.x + rect.width - location.width;
					}else if(position==PositionConstants.SOUTH_WEST){
						location.y = (rect.y + rect.height) -location.height;
						location.x = rect.x;
					}else{
						if(location.width>rect.width){
							location.width = rect.width;
						}
						if(location.height>rect.height){
							location.height = rect.height;
						}   
						Rectangle interRectangle = location.getIntersection(rect);

						if(interRectangle!=null){
							location.x = location.x - (location.width - interRectangle.width )-1 ;
							location.y = location.y - (location.height-interRectangle.height)-1;
						}



						return location;
					}



					if(location.width >= LayoutModel.DISPLAY_WIDTH){
						location.width = LayoutModel.DISPLAY_WIDTH;
					}
					if(location.height >= LayoutModel.DISPLAY_HEIGHT){
						location.height = LayoutModel.DISPLAY_HEIGHT;
					}
				}else{
					return null;
				}

			}
		}
		return location;
	}
	// 자신이 속한 LayoutModel  검색  포인터로만 확인 
	public UMLModel getAeroLayoutModel(Point point){

		UMLDiagramModel umlDiagramModel = getActiveDiagram();
		for(int i = 0; i < umlDiagramModel.getChildren().size(); i ++){
			if(umlDiagramModel.getChildren().get(i) instanceof ViewModel){
				ViewModel model =(ViewModel)umlDiagramModel.getChildren().get(i);
				if( model.getBasiclModel()!=null 
						&& model.getBasiclModel() instanceof LayoutModel){
					// 액정 LCD 부분 

					Rectangle layoutRect = new Rectangle(model.getLocation().x+LayoutModel.DISPLAY_X-1 ,model.getLocation().y+LayoutModel.DISPLAY_Y-1,LayoutModel.DISPLAY_WIDTH+2,LayoutModel.DISPLAY_HEIGHT+2); 
					if(layoutRect.contains(point)){

						return model;
					}
				}
			}
		}
		return null;

	}
	public ArrayList<UMLModel> getAeroModels(Rectangle rect){
		ArrayList<UMLModel> list = new ArrayList();
		UMLDiagramModel umlDiagramModel = getActiveDiagram();
		for(int i = 0; i < umlDiagramModel.getChildren().size(); i ++){
			if(umlDiagramModel.getChildren().get(i) instanceof ViewModel){
				ViewModel model =(ViewModel)umlDiagramModel.getChildren().get(i);
				if(rect.contains(model.getLocationRect())){
					list.add(model);
				}

			}
		}
		return list;

	}
	public UMLModel getAeroLayoutModel(UMLDiagramModel umlDiagramModel,Rectangle rectangle){

		if(umlDiagramModel!=null)
			for(int i = 0; i < umlDiagramModel.getChildren().size(); i ++){
				if(umlDiagramModel.getChildren().get(i) instanceof ViewModel){
					ViewModel model =(ViewModel)umlDiagramModel.getChildren().get(i);
					if( model.getBasiclModel()!=null 
							&& model.getBasiclModel() instanceof LayoutModel){
						// 액정 LCD 부분 

						Rectangle layoutRect = new Rectangle(model.getLocation().x+LayoutModel.DISPLAY_X-1 ,model.getLocation().y+LayoutModel.DISPLAY_Y-1,LayoutModel.DISPLAY_WIDTH+2,LayoutModel.DISPLAY_HEIGHT+2); 
						if(layoutRect.contains(rectangle)){

							return model;
						}
					}
				}
			}
		return null;

	}

	// 자신이랑 겹치는 모델이 있는지 여부 확인 
	public UMLModel getInterModel(UMLDiagramModel umlDiagramModel,Rectangle rect){

		if(umlDiagramModel!=null)
			for(int i = 0; i < umlDiagramModel.getChildren().size(); i ++){
				if(umlDiagramModel.getChildren().get(i) instanceof ViewModel){
					ViewModel model =(ViewModel)umlDiagramModel.getChildren().get(i);

					Rectangle layoutRect = model.getLocationRect(); 

					Rectangle interRectangle = layoutRect.getIntersection(rect);
					if(interRectangle.width > 0 || interRectangle.height > 0){

						return model;
					}

				}
			}
		return null;

	}

	public UMLModel getAeroLayoutModel(UMLModel uModel){
		UMLDiagramModel umlDiagramModel = (UMLDiagramModel)getDiagramModel(uModel);
		if(umlDiagramModel!=null)
			for(int i = 0; i < umlDiagramModel.getChildren().size(); i ++){
				if(umlDiagramModel.getChildren().get(i) instanceof ViewModel){
					ViewModel model =(ViewModel)umlDiagramModel.getChildren().get(i);
					if( model.getBasiclModel()!=null 
							&& model.getBasiclModel() instanceof LayoutModel){

						Rectangle layoutRect = model.getLocationRect();//new Rectangle(model.getLocation().x+25 ,model.getLocation().y+90,320+,538);
						Rectangle modelRect = new Rectangle(uModel.getLocationRect());
						if(modelRect.x !=0 && modelRect.y != 0)
							if(layoutRect.contains(modelRect)){


								return model;
							}
					}
				}
			}
		return null;

	}
	// Layout 의 자기 주변들의 모델을 반환한다. 
	public HashMap getApproachModels(UMLModel umlModel){
		Rectangle rect = umlModel.getLocationRect();
		HashMap modelMap = new HashMap();
		if(getAeroLayoutModel(umlModel)!=null){
			ArrayList<UMLModel> list = getAeroUMLModel(getAeroLayoutModel(umlModel));

			UMLModel topModel = null;
			UMLModel bottomModel = null;
			UMLModel rightModel =null;
			UMLModel leftModel =null;

			int top 	= 10000;
			int bottom 	= 10000;
			int left 	= 10000;
			int right 	= 10000;

			/**
			NORTH 북 
			SOUTH 남 
			WEST 서 
			EAST 동 
			 **/
			for(int i = 0; i < list.size(); i++){
				UMLModel friendModel = list.get(i);
				if(friendModel==umlModel)
					continue;

				if(rect.getPosition(friendModel.getLocation())==PositionConstants.NORTH){
					if(top>rect.y - friendModel.getLocation().y){
						top = rect.y - friendModel.getLocation().y; 
						topModel = friendModel;
					}
				}else if(rect.getPosition(friendModel.getLocation())==PositionConstants.NORTH_SOUTH){
					if(top>rect.y - friendModel.getLocation().y){
						top = rect.y - friendModel.getLocation().y; 
						topModel = friendModel;
					}
					if(bottom>friendModel.getLocation().y - rect.y){
						bottom = friendModel.getLocation().y - rect.y;
						bottomModel = friendModel;
					}
				}else if(rect.getPosition(friendModel.getLocation())==PositionConstants.NORTH_WEST){
					if(top>rect.y - friendModel.getLocation().y){
						top = rect.y - friendModel.getLocation().y; 
						topModel = friendModel;
					}
					if(left>rect.x -  friendModel.getLocation().x){
						left = rect.x -  friendModel.getLocation().x;
						leftModel = friendModel;
					}
				}else if(rect.getPosition(friendModel.getLocation())==PositionConstants.SOUTH){
					if(bottom>friendModel.getLocation().y - rect.y){
						bottom = friendModel.getLocation().y - rect.y;
						bottomModel = friendModel;
					}
				}else if(rect.getPosition(friendModel.getLocation())==PositionConstants.SOUTH_EAST){
					if(bottom>friendModel.getLocation().y - rect.y){
						bottom = friendModel.getLocation().y - rect.y;
						bottomModel = friendModel;
					}
					if(right>friendModel.getLocation().x - rect.x){
						right = friendModel.getLocation().x - rect.x ;
						rightModel = friendModel;
					}
				}else if(rect.getPosition(friendModel.getLocation())==PositionConstants.SOUTH_WEST){
					if(bottom>friendModel.getLocation().y - rect.y){
						bottom = friendModel.getLocation().y - rect.y;
						bottomModel = friendModel;
					}
					if(left>rect.x -  friendModel.getLocation().x){
						left = rect.x -  friendModel.getLocation().x;
						leftModel = friendModel;
					}
				}else if(rect.getPosition(friendModel.getLocation())==PositionConstants.WEST){
					if(left>rect.x -  friendModel.getLocation().x){
						left = rect.x -  friendModel.getLocation().x;
						leftModel = friendModel;
					}
				}else if(rect.getPosition(friendModel.getLocation())==PositionConstants.EAST){
					if(right>friendModel.getLocation().x - rect.x){
						right = friendModel.getLocation().x - rect.x ;
						rightModel = friendModel;

					}
				}

			}
			modelMap.put(SWT.LEFT, leftModel);
			modelMap.put(SWT.RIGHT, rightModel);
			modelMap.put(SWT.TOP, topModel);
			modelMap.put(SWT.BOTTOM, bottomModel);


		}
		return modelMap;
	}
	//현재 활성화 되어있는 다이어그램 모델 반환 
	public UMLDiagramModel getActiveDiagram(){
		IEditorPart editor = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActiveEditor();
		if(editor!=null && editor instanceof UMLEditor){
			UMLEditor umlEditor = (UMLEditor)PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActiveEditor();
			return umlEditor.getUMLDiagramModel();
		}else{
			return null;
		}
	}
	//오퍼레이션에 소스 라인 넣을때 사용하는 오퍼레이션 
	public HashMap addSourceLine(String text){


		HashMap<String,String> hashMap = new HashMap();
		hashMap.put(Property.ACTION_SOURCE_TEXT, text);

		return hashMap;
	}
	private OperationItem createIosOperation(String id){
		OperationItem operationItem = null;
		if(id.equals(Property.OPERATION_VOID_LOADVIEW)){
			operationItem = new OperationItem(Property.OPERATION_VOID_LOADVIEW, "- loadView():void");
			StringBuffer str = new StringBuffer();
			str.append("[super loadView];"+LayoutManager.ENTER_TOKEN);
			str.append("CGRect screenRect = [[UIScreen mainScreen] bounds];"+LayoutManager.ENTER_TOKEN);
			str.append("NSInteger screenWidth = screenRect.size.width; "+LayoutManager.ENTER_TOKEN);
			str.append("NSInteger screenHeight = screenRect.size.height; "+LayoutManager.ENTER_TOKEN);

			if(LayoutManager.getInstance().getSourceModel().getPropertyValue(Property.ID_AUTO_MOVE)==null)
				str.append("screenHeight = screenHeight -20-44; "+LayoutManager.ENTER_TOKEN);

			str.append("self.view = [[UIView alloc] initWithFrame:[[UIScreen mainScreen] applicationFrame]];"+LayoutManager.ENTER_TOKEN);
			str.append("self.view.backgroundColor = [UIColor whiteColor];"+LayoutManager.ENTER_TOKEN);
			str.append("UIScrollView *scrollView = [[UIScrollView alloc] initWithFrame:CGRectMake(0, 0, screenWidth, screenHeight)];"+LayoutManager.ENTER_TOKEN);
			int rateType = (Integer)LayoutManager.getInstance().getSourceModel().getPropertyValue(Property.ID_RATE);
			if(rateType==0){
				str.append("[scrollView setContentSize:CGSizeMake("+"screenWidth"+", "+"screenHeight"+")];    //스크롤 영역 사이즈 설정"+LayoutManager.ENTER_TOKEN);

			}else{
				str.append("[scrollView setContentSize:CGSizeMake("+LayoutModel.DISPLAY_WIDTH+", "+LayoutModel.DISPLAY_HEIGHT+")];    //스크롤 영역 사이즈 설정"+LayoutManager.ENTER_TOKEN);
			}

			str.append("[scrollView setShowsVerticalScrollIndicator:NO];      //스크롤 막대 숨기기 여부"+LayoutManager.ENTER_TOKEN);
			str.append("[scrollView setShowsHorizontalScrollIndicator:NO];"+LayoutManager.ENTER_TOKEN);
			str.append("[self.view addSubview:scrollView];"+LayoutManager.ENTER_TOKEN);
			operationItem.getActionDetailList().add(addSourceLine(str.toString()));

		}else if(id.equals(Property.OPERATION_ID_INITWITHNIBNAME)){
			operationItem = new OperationItem(Property.OPERATION_ID_INITWITHNIBNAME, "- initWithNibName(nibNameOrNil bundle:NSString,nibBundleOrNil:NSBundle):id");
			StringBuffer str = new StringBuffer();
			str.append("self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil];"+LayoutManager.ENTER_TOKEN);
			str.append("return self;"+LayoutManager.ENTER_TOKEN);
			operationItem.getActionDetailList().add(addSourceLine(str.toString()));
		}else if(id.equals(Property.OPERATION_ID_INIT)){
			operationItem = new OperationItem(Property.OPERATION_ID_INIT, "- init():id");
			StringBuffer str = new StringBuffer();
			str.append("self = [super init];"+LayoutManager.ENTER_TOKEN);
			operationItem.getActionDetailList().add(addSourceLine(str.toString()));	
		}else if(id.equals(Property.OPERATION_INT_TABLEVIEW_NUMBEROFROWSINSECTION)){
			operationItem = new OperationItem(Property.OPERATION_INT_TABLEVIEW_NUMBEROFROWSINSECTION, "- tableView(table numberOfRowsInSection:UITableView,section:NSInteger):NSInteger");
		}else if(id.equals(Property.OPERATION_UITABLEVIEWCELL_CELLFORROWATINDEXPATH)){
			operationItem = new OperationItem(Property.OPERATION_UITABLEVIEWCELL_CELLFORROWATINDEXPATH, "- tableView(tableView cellForRowAtIndexPath:UITableView,indexPath:NSIndexPath):UITableViewCell *");
		}else if(id.equals(Property.OPERATION_VOID_TABLEVIEW_DIDSELECTROWATINDEXPATH)){
			operationItem = new OperationItem(Property.OPERATION_VOID_TABLEVIEW_DIDSELECTROWATINDEXPATH, "- tableView(tableView didSelectRowAtIndexPath:UITableView,indexPath:NSIndexPath):void");
		}else if(id.equals(Property.OPERATION_VOID_VIEWDIDAPPEAR)){
			operationItem = new OperationItem(Property.OPERATION_VOID_VIEWDIDAPPEAR, "- viewDidAppear(animated:BOOL):void");
		}

		return operationItem;
	}
	//IOS 레이아웃 위치 재조정 및 값 전달  
	public String getLayoutIOSLocation(UMLModel viewModel){


		viewModel = viewModel.getViewModel();
		UMLModel viewLayoutModel = ProjectManager.getInstance().getAeroLayoutModel(viewModel);

		int rateType = (Integer)viewLayoutModel.getPropertyValue(Property.ID_RATE);

		double percent = 0;

		if(rateType==0)
			percent = LayoutManager.IOS_HEIGHT_DISPLAY_PERCENT;
		else
			percent = LayoutManager.IOS_WIDTH_DISPLAY_PERCENT;

		double x = ProjectManager.getInstance().getDistance(viewModel,viewLayoutModel,  SWT.LEFT);
		double y = ProjectManager.getInstance().getDistance(viewModel,viewLayoutModel,  SWT.TOP);//1.3-7;


		double width = viewModel.getSize().width;
		double height = viewModel.getSize().height;

		double gap_Width = ((viewModel.getSize().width - width)/2)*percent;
		double gap_Height =((viewModel.getSize().height - height)/2)*percent;

		x -=gap_Width;
		y -=gap_Height;

		double x_percent = x / LayoutModel.DISPLAY_WIDTH * 100;
		double y_percent = y / LayoutModel.DISPLAY_HEIGHT * 100;

		double width_percent = width / LayoutModel.DISPLAY_WIDTH * 100;
		double height_percent = height / LayoutModel.DISPLAY_HEIGHT * 100;

		if(x_percent<0)
			x_percent = 0;
		if(y_percent<0)
			y_percent = 0;

		if(x<0)
			x = 0;
		if(y<0)
			y = 0;


		if(rateType ==0){
			return x_percent+"*screenWidth/100" + LayoutManager.COMMAMA_WORLD + y_percent +"*screenHeight/100" + LayoutManager.COMMAMA_WORLD +  width_percent+"*screenWidth/100"  + LayoutManager.COMMAMA_WORLD +  height_percent+"*screenHeight/100" ;
		}else{
			return x+ LayoutManager.COMMAMA_WORLD + y + LayoutManager.COMMAMA_WORLD + width + LayoutManager.COMMAMA_WORLD + height;
		}

	}


	public int getDistance(UMLModel model1,UMLModel model2,int position){


		Rectangle rect1 = null;
		Rectangle rect2 = null;

		if(model1.getBasicModel() instanceof LayoutModel){
			rect1 = ((LayoutModel)model1.getBasicModel()).getDisplayRect();
			rect1.height = 0;
			rect1.width = 0;
		}else
			rect1 = model1.getLocationRect();

		if(model2.getBasicModel() instanceof LayoutModel){
			rect2 = ((LayoutModel)model2.getBasicModel()).getDisplayRect();
			rect2.height = 0;
			rect2.width = 0;
		}else
			rect2 = model2.getLocationRect();

		int distance = 0;
		if(position == SWT.LEFT){
			distance =  rect1.x - rect2.x-rect2.width;
		}else if(position == SWT.RIGHT){
			distance =  rect2.x - rect1.x ;
		}else if(position == SWT.TOP){
			distance =  rect1.y - rect2.y - rect2.height;
		}else if(position == SWT.BOTTOM){
			distance = rect2.y+rect2.height - rect1.y;
		}
		return distance;
	}
	//자신이 속한 다이어그램 모
	public UMLDiagramModel getDiagramModel(UMLModel model){
		if(model==null)
			return null;
		if(model.getParentModel()!=null && !(model.getParentModel() instanceof UMLDiagramModel)){
			return getDiagramModel(model.getParentModel());
		}else if(model.getParentModel()!=null && model.getParentModel() instanceof UMLDiagramModel){
			return (UMLDiagramModel) model.getParentModel();
		}else {
			return null;
		}
	}
	//자신  Rect 안에 들어가있는 모델 리스트 List 로 반환 
	public ArrayList<UMLModel> getAeroUMLModel(UMLModel model){
		ArrayList<UMLModel> list = new ArrayList();

		UMLDiagramModel diagramModel = getDiagramModel(model);
		if(diagramModel!=null)
			for(int i = 0; i < diagramModel.getChildren().size(); i ++){

				if(diagramModel.getChildren().get(i).getLocationRect().x!=0&&diagramModel.getChildren().get(i).getLocationRect().y!=0&&model.getLocationRect().contains(diagramModel.getChildren().get(i).getLocationRect()) && diagramModel.getChildren().get(i)!=model)
					list.add(diagramModel.getChildren().get(i));
			}

		return list;
	}
	public ArrayList getLayoutSortList(ArrayList<UMLModel> aeroList){

		Collections.sort(aeroList, new LayoutCompare());
		return aeroList;
	}
	public String getModelAndroidType(int modelType){
		String text = "";
		if(modelType==1){
			text = "StoryBoard";
		}else if(modelType==2){
			text = "RelativeLayout";
		}else if(modelType==3){
			text = "AutoCompleteTextView";
		}else if(modelType==4){
			text = "EditText";
		}else if(modelType==5){
			text = "EditText";
		}else if(modelType==6){
			text = "EditText";
		}else if(modelType==7){
			text = "EditText";
		}else if(modelType==8){
			text = "EditText";
		}else if(modelType==9){
			text = "EditText";
		}else if(modelType==10){
			text = "EditText";
		}else if(modelType==11){
			text = "EditText";
		}else if(modelType==12){
			text = "EditText";
		}else if(modelType==13){
			text = "EditText";
		}else if(modelType==14){
			text = "EditText";
		}else if(modelType==15){
			text = "EditText";
		}else if(modelType==16){
			text = "EditText";
		}else if(modelType==17){
			text = "EditText";
		}else if(modelType==18){
			text = "ExpandableListView";
		}else if(modelType==19){
			text = "ListView";
		}else if(modelType==20){
			text = "Package";
		}else if(modelType==21){
			text = "Button";
		}else if(modelType==22){
			text = "WebView";
		}else if(modelType==23){
			text = "ImageView";
		}else if(modelType==24){
			text = "fragment";
		}else if(modelType==25){
			text = "GoogleMap";
		}else if(modelType==26){
			text = "VideoView";
		}else if(modelType==27){
			text = "TextView";
		}else if(modelType==28){
			text = "WebView";
		}

		return text;



	}
	//IOS 용 객체 클래스타입 
	public String getModelIOSType(int modelType){
		String text = "";
		if(modelType==1){
			text = "StoryBoard";
		}else if(modelType==2){
			text = "RelativeLayout";
		}else if(modelType==3){
			text = "UITextField";
		}else if(modelType==4){
			text = "UITextField";
		}else if(modelType==5){
			text = "UITextField";
		}else if(modelType==6){
			text = "UITextField";
		}else if(modelType==7){
			text = "UITextField";
		}else if(modelType==8){
			text = "UITextField";
		}else if(modelType==9){
			text = "UITextField";
		}else if(modelType==10){
			text = "UITextField";
		}else if(modelType==11){
			text = "UITextField";
		}else if(modelType==12){
			text = "UITextField";
		}else if(modelType==13){
			text = "UITextField";
		}else if(modelType==14){
			text = "UITextField";
		}else if(modelType==15){
			text = "UITextField";
		}else if(modelType==16){
			text = "UITextField";
		}else if(modelType==17){
			text = "UITextField";
		}else if(modelType==18){
			text = "UITableView";
		}else if(modelType==19){
			text = "UITableView";
		}else if(modelType==20){
			text = "Package";
		}else if(modelType==21){
			text = "UIButton";
		}else if(modelType==22){
			text = "UIWebView";
		}else if(modelType==23){
			text = "UIImageView";
		}else if(modelType==24){
			text = "fragment";
		}else if(modelType==25){
			text = "GoogleMap";
		}else if(modelType==26){
			text = "MPMoviePlayerViewController";
		}else if(modelType==27){
			text = "UILabel";
		}else if(modelType==28){
			text = "UIWebView";
		}
		return text;
	}
	//  등록 
	public String getModelDefaultName(int modelType,boolean isNumbering){
		String text = "";
		if(modelType==1){
			text = "StoryBoard";
		}else if(modelType==2){
			if(isNumbering)
				text = "layout";
			else
				text = "RelativeLayout";
		}else if(modelType==3){
			text = "AutoCompleteTextView";
		}else if(modelType==4){
			text = "EditText";
		}else if(modelType==5){
			text = "EditText";
		}else if(modelType==6){
			text = "EditText";
		}else if(modelType==7){
			text = "EditText";
		}else if(modelType==8){
			text = "EditText";
		}else if(modelType==9){
			text = "EditText";
		}else if(modelType==10){
			text = "EditText";
		}else if(modelType==11){
			text = "EditText";
		}else if(modelType==12){
			text = "EditText";
		}else if(modelType==13){
			text = "EditText";
		}else if(modelType==14){
			text = "EditText";
		}else if(modelType==15){
			text = "EditText";
		}else if(modelType==16){
			text = "EditText";
		}else if(modelType==17){
			text = "EditText";
		}else if(modelType==18){
			text = "ExpandableListView";
		}else if(modelType==19){
			text = "ListView";
		}else if(modelType==20){
			text = "Package";
		}else if(modelType==21){
			text = "Button";
		}else if(modelType==22){
			text = "WebView";
		}else if(modelType==23){
			text = "ImageView";
		}else if(modelType==24){
			text = "fragment";
		}else if(modelType==25){
			text = "GoogleMap";
		}else if(modelType==26){
			text = "VideoView";
		}else if(modelType==27){
			text = "TextView";
		}else if(modelType==28){
			text = "YoutubeView";
		}
		if(!isNumbering)
			return text;
		else{
			int number = getModelNumbering(text);
			if(number==-1){
				return text;
			}else{
				return text + number;
			}
		}

	}




	//등록 
	public int getModelType(String text){
		if(text.equals("StoryBoard")){
			return 1;
		}else if(text.equals("RelativeLayout")){
			return 2;
		}else if(text.equals("AutoCompleteTextView")){
			return 3;
		}else if(text.equals("EditText")){
			return 17;
		}else if(text.equals("ExpandableListView")){
			return 18;
		}else if(text.equals("ListView")){
			return 19;
		}else if(text.equals("Package")){
			return 20;
		}else if(text.equals("Button")){
			return 21;
		}else if(text.equals("WebView")){
			return 22;
		}else if(text.equals("ImageView")){
			return 23;
		}else if(text.equals("fragment")){
			return 24;
		}else if(text.equals("GoogleMap")){
			return 25;
		}else if(text.equals("VideoView")){
			return 26;
		}else if(text.equals("TextView")){
			return 27;
		}else if(text.equals("YoutubeView")){
			return 28;
		}
		else{
			return -1;
		}
	}
	//등록 
	public int getModelType(UMLModel viewModel){
		UMLModel model = viewModel.getBasicModel();
		if(model instanceof UMLDiagramModel){
			return 1;
		}else if(model instanceof LayoutModel){
			return 2;
		}else if(model instanceof EditTextAutoCompleteTextViewModel){
			return 3;
		}else if(model instanceof EditTextDateModel){
			return 4;
		}else if(model instanceof EditTextMultiAutoCompleteTextViewModel){
			return 5;
		}else if(model instanceof EditTextNumberDecimalModel){
			return 6;
		}else if(model instanceof EditTextNumberModel){
			return 7;
		}else if(model instanceof EditTextNumberSignedModel){
			return 8;
		}else if(model instanceof EditTextNumericPasswordModel){
			return 9;
		}else if(model instanceof EditTextPasswordModel){
			return 10;
		}else if(model instanceof EditTextPersonNameModel){
			return 11;
		}else if(model instanceof EditTextPhoneModel){
			return 12;
		}else if(model instanceof EditTextTextEmailAddressModel){
			return 13;
		}else if(model instanceof EditTextTextMultiLineModel){
			return 14;
		}else if(model instanceof EditTextTextPostalAddressModel){
			return 15;
		}else if(model instanceof EditTextTimeModel){
			return 16;
		}else if(model instanceof EditTextModel){
			return 17;
		}else if(model instanceof ExpandableListViewModel){
			return 18;
		}else if(model instanceof ListViewModel){
			return 19;
		}else if(model instanceof PackageModel){
			return 20;
		}else if(model instanceof ButtonModel){
			return 21;
		}else if(model instanceof WebViewModel){
			return 22;
		}else if(model instanceof ImageViewModel){
			return 23;
		}else if(model instanceof FragmentModel){
			return 24;
		}else if(model instanceof GoogleMapModel){
			return 25;
		}else if(model instanceof VideoModel){
			return 26;
		}else if(model instanceof TextViewModel){
			return 27;
		}else if(model instanceof YoutubeModel){
			return 28;
		}    
		return -1;
	}
	//등록 
	public UMLModel addUMLModel(String name,int modelType, UMLDiagramModel diagramModel,ModelTreeModel tp,boolean isTree){

		UMLModel model = null; 



		if(modelType==1){
			return addUMLDiagram(name, tp, 1, false);
		}else if(modelType==2){
			model = new ViewModel(new LayoutModel());
		}else if(modelType==3){
			model = new ViewModel(new EditTextAutoCompleteTextViewModel());
		}else if(modelType==4){
			model = new ViewModel(new EditTextDateModel());
		}else if(modelType==5){
			model = new ViewModel(new EditTextMultiAutoCompleteTextViewModel());
		}else if(modelType==6){
			model = new ViewModel(new EditTextNumberDecimalModel());
		}else if(modelType==7){
			model = new ViewModel(new EditTextNumberModel());
		}else if(modelType==8){
			model = new ViewModel(new EditTextNumberSignedModel());
		}else if(modelType==9){
			model = new ViewModel(new EditTextNumericPasswordModel());
		}else if(modelType==10){
			model = new ViewModel(new EditTextPasswordModel());
		}else if(modelType==11){
			model = new ViewModel(new EditTextPersonNameModel());
		}else if(modelType==12){
			model = new ViewModel(new EditTextPhoneModel());
		}else if(modelType==13){
			model = new ViewModel(new EditTextTextEmailAddressModel());
		}else if(modelType==14){
			model = new ViewModel(new EditTextTextMultiLineModel());
		}else if(modelType==15){
			model = new ViewModel(new EditTextTextPostalAddressModel());
		}else if(modelType==16){
			model = new ViewModel(new EditTextTimeModel());
		}else if(modelType==17){
			model = new ViewModel(new EditTextModel());
		}else if(modelType==18){
			model = new ViewModel(new ExpandableListViewModel());
		}else if(modelType==19){
			model = new ViewModel(new ListViewModel());
		}else if(modelType==20){
			model = new PackageModel();
		}else if(modelType==21){
			model = new ViewModel(new ButtonModel());
		}else if(modelType==22){
			model = new ViewModel(new WebViewModel());
		}else if(modelType==23){
			model = new ViewModel(new ImageViewModel());
		}else if(modelType==24){
			model = new ViewModel(new FragmentModel());
		}else if(modelType==25){
			model = new ViewModel(new GoogleMapModel());
		}else if(modelType==26){
			model = new ViewModel(new VideoModel());
		}else if(modelType==27){
			model = new ViewModel(new TextViewModel());
		}else if(modelType==28){
			model = new ViewModel(new YoutubeModel());
		}

		if(model==null)
			return null;
		if(tp==null)
			tp = ModelBrowserManager.getInstance().getModelBrowser().getRoot();
		if(name!=null && !name.equals("")){
			int number = -1;
			if(modelType ==20){
				number = getModelNumbering(name);
				for(int i = 0 ; i < tp.getChildren().length; i ++){
					if(tp.getChildren()[i].getName().equals(name)){
						ProjectManager.getInstance().showMessage(MessageDialog.ERROR, "무결성 오류", "동일한 패키지명이 존재합니다.");
						return null;
					}
				}
			}
			if(number==-1)
				model.setName(name);
			else
				model.setName(name+number);
		}else
			model.setName(getModelDefaultName(modelType,true));

		if(!isPropIntegrity(Property.ID_NAME, model.getName())){
			return null;
		}
		if(diagramModel!=null){
			diagramModel.addChild(model);
		}


		if(isTree){

			ModelTreeModel modelTreeModel = new ModelTreeModel(model);
			tp.addChild(modelTreeModel);
			ModelBrowserManager.getInstance().getModelBrowser().getViewer().refresh();
		}




		return model;

	}





	public UMLDiagramModel addUMLDiagram(String name, ModelTreeModel tp, int diagramType,boolean isOpen) {

		try {
			//ModelDataManager.getInstance().setLoad(true);
			ProjectManager.getInstance().setOpenDiagramModel(null);
			if(name==null)
				name = getModelDefaultName(diagramType,true);
			if(!isPropIntegrity(Property.ID_NAME, name)){
				return null;
			}
			//			if(tp==null){
			//				tp = ModelBrowserManager.getInstance().getModelBrowser().getRoot();
			//			}
			LogicEditorInput input = new LogicEditorInput(new Path("Diagram"));
			UMLEditor u = (UMLEditor)ProjectManager.getInstance().window.getActivePage().openEditor(input, UMLEditor.ID, true);
			//			u.setUMLDiagramModel(new UMLDiagramModel());

			u.setTitleName(name);
			u.getUMLDiagramModel().setModelType(diagramType);

			if(tp!=null){
				ModelTreeModel to1 = new ModelTreeModel(u.getUMLDiagramModel());
				tp.addChild(to1);
			}

			u.getUMLDiagramModel().setiEditorInput(input);
			u.getUMLDiagramModel().setName(name);
			//u.setUmlDiagramModel(diagram);


			ProjectManager.getInstance().setOpenDiagramModel(u.getUMLDiagramModel());


			//			u.getUMLDiagramModel().setShadowModel((ViewModel)getInstance().addUMLModel(name, 99, u.getUMLDiagramModel(), tp, false));
			return u.getUMLDiagramModel();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{

		}
		return null;

	}

	//프로퍼티 무결성 체크 
	public boolean isPropIntegrity(Object id,Object value){

		if(id == Property.ID_DENSITY || id == Property.ID_TEXT_SIZE || id == Property.ID_WIDTH || id == Property.ID_HEIGHT
				|| id == Property.ID_X || id == Property.ID_Y){
			Pattern pattern = Pattern.compile("\\D");
			if(value instanceof String){
				Matcher matcher = pattern.matcher((String)value);				

				if(matcher.find()){
					ProjectManager.getInstance().showMessage(MessageDialog.WARNING, "경고","내용에는 숫자만 입력을 할 수 있습니다.");
					return false;
				}
			}
		}else if(id==Property.ID_NAME){
			Pattern pattern = Pattern.compile("^[A-Z|a-z]");
			Matcher matcher = pattern.matcher((String)value);				

			if(!matcher.find()){
				ProjectManager.getInstance().showMessage(MessageDialog.WARNING, "경고","첫 글자는 영문으로 시작하여야 합니다.");
				return false;
			}			
		}else if(id==Property.ID_MEDIA){
			Pattern pattern = Pattern.compile("[a-z0-9_.]");
			File f = new File((String) value);

			Matcher matcher = pattern.matcher((String)f.getName());				
			if(isMediaExistsName((String)value)){
				ProjectManager.getInstance().showMessage(MessageDialog.WARNING, "경고","동일한 파일명이 존재합니다. 다른 폴더에 존재하여도 이름이 동일하면 불가능합니다.");
				return false;
			}
			if(!matcher.find()){
				ProjectManager.getInstance().showMessage(MessageDialog.WARNING, "경고","파일명은 영문(소문자),숫자,_ 이외의 것은 불가능합니다. 파일명을 변경하여 주시기 바랍니다.");
				return false;
			}			
		}
		return true;
	}

	public int getTextGraphicsWidth(String sText){
		Display display = new Display();
		Shell shell = new Shell(display);

		Text text = new Text(shell, SWT.NONE);
		text.setText(sText);
		GC gc = new GC(text);
		FontMetrics fm = gc.getFontMetrics();
		int charWidth = fm.getAverageCharWidth();
		int width = text.computeSize(charWidth * 8, SWT.DEFAULT).x;
		gc.dispose();

		System.out.println(width);

		shell.isDisposed();
		display.dispose();
		return width;
	}
	public UMLModel getSearchID(String id){
		ArrayList<UMLModel> list = getModels(-1);

		for(int i = 0 ; i < list.size(); i ++){

			if(list.get(i).getViewModel().getID().equals(id)){
				return list.get(i).getViewModel();
			}else if(list.get(i).getBasicModel().getID().equals(id)){
				return list.get(i).getBasicModel();
			}else{
				for(int j = 0; j < list.get(i).getParentModels().size(); j++){
					System.out.println(list.get(i).getParentModels().get(j).getID());
					if(list.get(i).getParentModels().get(j).getID().equals(id)){
						return list.get(i).getParentModels().get(j); 
					}
				}
			}
		}
		return null;


	}
	public ArrayList<UMLModel> getModels(int modelType){
		return ModelBrowserManager.getInstance().getList(ModelBrowserManager.getInstance().getModelBrowser().getRoot(), modelType);
	}

	public ActionBrowser getActionBrowser(){
		IViewPart iv = window.getActivePage().findView(ActionBrowser.ID);
		if(iv!=null)
			return (ActionBrowser)iv;
		else
			return null;
	}

	public List<UMLEditPart> getSelections() {
		if(ProjectManager.getInstance().getUMLEditor()!=null)

			if(ProjectManager.getInstance().getUMLEditor().getScrollingGraphicalViewer()!=null){
				ScrollingGraphicalViewer viewer = ProjectManager.getInstance().getUMLEditor().getScrollingGraphicalViewer();
				List list = viewer.getSelectedEditParts();
				return list;
			}
		return null;

	}

	public java.util.ArrayList getSelectList() {
		return selectList;
	}

	public void addSelectList(Object obj) {
		if(!this.selectList.contains(obj))
			this.selectList.add(obj);
	}

	public void setSelectList(java.util.ArrayList selectList) {
		this.selectList = selectList;
	}

	public boolean isMultiClick() {
		return isMultiClick;
	}

	public void setMultiClick(boolean isMultiClick) {
		this.isMultiClick = isMultiClick;
		if(!isMultiClick)
			this.selectList.clear();

	}

	public void addChild(UMLDiagramModel diagram,UMLModel model){
		if(diagram!=null){
			diagram.addChild(model);
		}

	}
	public void removeUMLNode(ModelTreeModel parent, ModelTreeModel child) {

		if (child != null) {
			for(int i = 0; i < child.getChildren().length; i ++){
				removeUMLNode(child,child.getChildren()[i]);

			}
			UMLModel model = child.getBasicModel();
			if(model instanceof UMLDiagramModel){
				UMLDiagramModel diagram = (UMLDiagramModel)model;
				ProjectManager.getInstance().window.getActivePage().closeEditor(diagram.getUmlEditor(), false);
			}else{
				for(int i = 0 ; i < model.getParentModels().size(); i ++){
					UMLDiagramModel diagram = ProjectManager.getInstance().getDiagramModel(model.getParentModels().get(i));
					if(diagram!=null)
						diagram.removeChild(model.getParentModels().get(i));
				}
			}
			parent.removeChild(child);
			ModelBrowserManager.getInstance().getModelBrowser().getViewer().refresh(parent);
		}
	}
	public String getNewID(Object obj){
		String hashcode = Integer.toHexString(obj.hashCode());
		String time = Long.toHexString(new Date().getTime());
		String second = Long.toHexString(new Date().getSeconds());
		String random = Integer.toHexString((int)(Math.random() * 10000));

		String id = obj.toString() + "@" + hashcode + time + second + ":" + random;

		return id;
	}


	public int getModelNumbering(String name){
		int nubmer = getModelNumberCount(name.toLowerCase(),ModelBrowserManager.getInstance().getModelBrowser().getRoot(),-1);

		return nubmer;

	}
	public int getModelNumberCount(String name,ModelTreeModel modelTreeModel,int count){
		for(int i = 0; i < modelTreeModel.getChildrens().size(); i ++){
			Pattern p = Pattern.compile("[(0-9)]");
			if(modelTreeModel.getChildrens().get(i).getName().trim().toLowerCase().equals(name.trim())){
				count = count +1;
			}else if(modelTreeModel.getChildrens().get(i).getName().toLowerCase().startsWith(name)){
				Matcher matcher = p.matcher(modelTreeModel.getChildrens().get(i).getName());				
				String text = "";
				while(matcher.find()){
					text = text+ matcher.group();
					try{
						int number = Integer.
								parseInt(text);
						if(count<=number)
							count =number+1;
					}catch(Exception e){}
				}
			}
			count = getModelNumberCount(name,modelTreeModel.getChildrens().get(i),count);
		}
		return count;
	}
	public int showDialog(int style,String title,String msg,String[] strButton){
		MessageDialog msgDialog = new MessageDialog(window.getShell(), title, null,
				msg,style, strButton, 0);
		return msgDialog.open();
	}
	public void showMessage(int style,String title,String msg){
		try{
			MessageDialog msgDialog = new MessageDialog(window.getShell(), title, null,
					msg,style, new String[] { "OK"  }, 0);
			msgDialog.open();
		}catch(Exception e){

		}
	}
	public void makeIOS(){
		LayoutManager.getInstance().clearOperationMap();
		
		LayoutManager.getInstance().setType(LayoutManager.IOS);
		IRunnableWithProgress iRunnableWithProgress = new IRunnableWithProgress() {

			@Override
			public void run(IProgressMonitor monitor)
					throws InvocationTargetException, InterruptedException {
				monitor.setTaskName("폴더 초기화 중..");
				ArrayList<UMLModel> imageViewModels = getModels(getModelType("ImageView"));
				HashMap<String,Boolean> imgMap = new HashMap();
				for(int i = 0; i < imageViewModels.size(); i ++){
					if(imageViewModels.get(i).getPropertyValue(Property.ID_IMG)!=null && !imageViewModels.get(i).getPropertyValue(Property.ID_IMG).equals("")){
						File imgFile = new File((String)imageViewModels.get(i).getPropertyValue(Property.ID_IMG));
						imgMap.put(imgFile.getName(), true);
					}
				}
				File folder = new File(ProjectManager.getInstance().getIosPath()+LayoutManager.SEPARATOR+LayoutManager.IOS_IMAGES_PATH+LayoutManager.SEPARATOR);
				if(folder.exists())
					for(int i = 0 ; i < folder.listFiles().length; i++){
						if(imgMap.get(folder.listFiles()[i].getName())==null){
							LayoutManager.getInstance().deleteFile(folder.listFiles()[i].getPath());
						}
					}


				ArrayList<UMLModel> list = ModelBrowserManager.getInstance().getList(ModelBrowserManager.getInstance().getModelBrowser().getRoot(), 2);


				for(int i = 0; i < list.size(); i ++){
					ArrayList operations = null;
					for(int j = 0 ; j < list.get(i).getActionList().size(); i ++){ 
						list.get(i).getActionList().get(j).getActionDetailList().clear();
					}
				}
				LayoutManager.getInstance().makeFileIOS('D', null, null);
				for(int i = 0; i < list.size(); i ++){
					ArrayList operations = null;
					
					if(list.get(i).getBasicModel()!=null && list.get(i).getBasicModel() instanceof LayoutModel){
						LayoutModel model = (LayoutModel)list.get(i).getBasicModel();
						model.writeLayoutIOS();
						monitor.setTaskName("Source 생성중..");
						LayoutManager.getInstance().makeFileIOS('M', model.getName(),model.writeSourceIOSM());
						monitor.setTaskName("Image 리사이징 및 복사중...");
						LayoutManager.getInstance().makeFileIOS('H', model.getName(),model.writeSourceIOSH());

					}

				}
				
			}
		};
		try{
			ProgressMonitorDialog pd = new ProgressMonitorDialog(ProjectManager.getInstance().window.getShell());
			pd.run(true, true, iRunnableWithProgress);
			
			 Desktop.getDesktop().open(new File(ProjectManager.getInstance().getIosPath()+LayoutManager.SEPARATOR));
			 

		}catch(Exception e) {
			e.printStackTrace();
		}
		
		
		

	}
	public void makeAPK(String alias,String password){
		LayoutManager.getInstance().clearOperationMap();
		
		final String inAlias = alias;
		final String inPassword = password;
		//사용하고 이미지 제외하고 나머지 이미지는 다 삭제처리한다.	Start

		IRunnableWithProgress iRunnableWithProgress = new IRunnableWithProgress() {

			@Override
			public void run(IProgressMonitor monitor) throws InvocationTargetException,
			InterruptedException {
				monitor.setTaskName("이미지 & 비디오 파일 복사중...");
				ArrayList<UMLModel> imageViewModels = getModels(getModelType("ImageView"));
				HashMap<String,Boolean> imgMap = new HashMap();
				for(int i = 0; i < imageViewModels.size(); i ++){
					if(imageViewModels.get(i).getPropertyValue(Property.ID_IMG)!=null && !imageViewModels.get(i).getPropertyValue(Property.ID_IMG).equals("")){
						File imgFile = new File((String)imageViewModels.get(i).getPropertyValue(Property.ID_IMG));
						imgMap.put(imgFile.getName(), true);
					}
				}
				File folder = new File(ProjectManager.getInstance().getAndroidPath()+LayoutManager.SEPARATOR+LayoutManager.ANDROID_RES_DRAWABLE_HDPI_PATH+LayoutManager.SEPARATOR);
				if(folder.exists())
					for(int i = 0 ; i < folder.listFiles().length; i++){
						if(imgMap.get(folder.listFiles()[i].getName())==null){
							LayoutManager.getInstance().deleteFile(folder.listFiles()[i].getPath());
						}
					}
				//End
				//사용하고 비디오 제외하고 나머지 이미지는 다 삭제처리한다.	Start
				ArrayList<UMLModel> viedoModels = getModels(getModelType("VideoView"));
				HashMap<String,Boolean> viedoMap = new HashMap();
				for(int i = 0; i < imageViewModels.size(); i ++){
					if(imageViewModels.get(i).getPropertyValue(Property.ID_VIEDO_URL)!=null && !imageViewModels.get(i).getPropertyValue(Property.ID_VIEDO_URL).equals("")){
						File imgFile = new File((String)imageViewModels.get(i).getPropertyValue(Property.ID_VIEDO_URL));
						viedoMap.put(imgFile.getName(), true);
					}
				}
				folder = new File(ProjectManager.getInstance().getAndroidPath()+LayoutManager.SEPARATOR+LayoutManager.ANDROID_RES_RAW_PATH+LayoutManager.SEPARATOR);
				if(folder.exists())
					for(int i = 0 ; i < folder.listFiles().length; i++){
						if(viedoMap.get(folder.listFiles()[i].getName())==null){
							LayoutManager.getInstance().deleteFile(folder.listFiles()[i].getPath());
						}
					}
				//End
				monitor.setTaskName("파일 초기화 중...");

				LayoutManager.getInstance().resetFile(ProjectManager.getInstance().getAndroidPath()+LayoutManager.SEPARATOR+LayoutManager.ANDROID_EXT_PATH+LayoutManager.SEPARATOR);
				LayoutManager.getInstance().resetFile(ProjectManager.getInstance().getAndroidPath()+LayoutManager.SEPARATOR+LayoutManager.ANDROID_SRC_PATH+LayoutManager.SEPARATOR);
				LayoutManager.getInstance().resetFile(ProjectManager.getInstance().getAndroidPath()+LayoutManager.SEPARATOR+LayoutManager.ANDROID_RES_LAYOUT_PATH+LayoutManager.SEPARATOR);
				LayoutManager.getInstance().makeFile(ProjectManager.getInstance().getAndroidPath()+LayoutManager.SEPARATOR+LayoutManager.ANDROID_RES_DRAWABLE_HDPI_PATH+LayoutManager.SEPARATOR);
				LayoutManager.getInstance().resetFile(ProjectManager.getInstance().getAndroidPath()+LayoutManager.SEPARATOR+LayoutManager.ANDROID_BIN_CLASSES+LayoutManager.SEPARATOR);
				LayoutManager.getInstance().resetFile(ProjectManager.getInstance().getAndroidPath()+LayoutManager.SEPARATOR+LayoutManager.ANDROID_GEN_PATH);
				//		LayoutManager.getInstance().makeFile(ProjectManager.getInstance().getAndroidPath()+LayoutManager.SEPARATOR+LayoutManager.ANDROID_BIN_CLASSES+LayoutManager.SEPARATOR);
				//		LayoutManager.getInstance().makeFile(ProjectManager.getInstance().getAndroidPath()+LayoutManager.SEPARATOR+LayoutManager.ANDROID_GEN_PATH);
				//		LayoutManager.getInstance().makeFile(ProjectManager.getInstance().getAndroidPath()+LayoutManager.SEPARATOR+LayoutManager.ANDROID_EXT_PATH+LayoutManager.SEPARATOR);
				//		LayoutManager.getInstance().makeFile(ProjectManager.getInstance().getAndroidPath()+LayoutManager.SEPARATOR+LayoutManager.ANDROID_RES_LAYOUT_PATH+LayoutManager.SEPARATOR);



				//Icon 추가 
				File iconFile = new File(ProjectManager.getInstance().getAndroid_icon());
				if(iconFile.exists()&& iconFile.isFile()){
					LayoutManager.getInstance().copyFile(iconFile.getPath(), ProjectManager.getInstance().getAndroidPath()+LayoutManager.SEPARATOR+LayoutManager.ANDROID_RES_DRAWABLE_HDPI_PATH+LayoutManager.SEPARATOR+iconFile.getName());
					//			iconFile = LayoutManager.getInstance().reNameMedia(ProjectManager.getInstance().getAndroidPath()+LayoutManager.SEPARATOR+LayoutManager.ANDROID_RES_DRAWABLE_HDPI_PATH+LayoutManager.SEPARATOR+iconFile.getName());
					ProjectManager.getInstance().setAndroid_icon(iconFile.getName());
				}else{
					ProjectManager.getInstance().setAndroid_icon(null);	
				}

				ArrayList<UMLModel> list = ModelBrowserManager.getInstance().getList(ModelBrowserManager.getInstance().getModelBrowser().getRoot(), 2);
				AndroidManifestManager.getInstance().makeFile(list); //activity 작성
				monitor.setTaskName("Source 파일 생성중...");
				LayoutManager.getInstance().makeFileAndroid('C', null, null);

				for(int i = 0; i < list.size(); i ++){

					Document document = LayoutManager.getInstance().getXMLNewDoc();
					document.appendChild(list.get(i).getBasicModel().writeLayoutAndroid(document));

					LayoutManager.getInstance().makeFileAndroid('L',list.get(i).getBasicModel().getLayoutName(),document);
					LayoutManager.getInstance().makeFileAndroid('J', list.get(i).getAndroidPackageName(), list.get(i).getBasicModel().writeSourceAndroid());


				}
				if(true)
					return;
				monitor.setTaskName("R.Java 파일 생성중...");
				LayoutManager.getInstance().generatorAndroidR();
				monitor.setTaskName("소스 컴파일 중...");
				LayoutManager.getInstance().generatorCompileJavas(ProjectManager.getInstance().getAndroidPath()+LayoutManager.SEPARATOR+LayoutManager.ANDROID_SRC_PATH+LayoutManager.SEPARATOR);
				monitor.setTaskName("Dex 파일 생성중...");
				LayoutManager.getInstance().generatorAndroidDex();
				monitor.setTaskName("APK 파일 생성중...");
				LayoutManager.getInstance().generatorAndroidAPK();
				monitor.setTaskName("APK DEX 빌드중...");
				LayoutManager.getInstance().generatorAPKbuilder();
				monitor.setTaskName("APK 서명중...");
				LayoutManager.getInstance().generatorAndroidJarSigner(inAlias, inPassword);



			}
		};
		try{
			ProgressMonitorDialog pd = new ProgressMonitorDialog(ProjectManager.getInstance().window.getShell());
			pd.run(true, true, iRunnableWithProgress);
			
			

		}catch(Exception e) {
			e.printStackTrace();
		}
		
	
	}
	public ArrayList<String> commend(String cmd){
		//		getInstance().showMessage(0, "", cmd);
		return commend(new String[]{cmd});
	}

	public ArrayList<String> commend(String[] str){

		String os = System.getProperty("os.name");

		String[] command = null;
		//		     String [] command = { "/bin/bsh", "-c", comstr }; //리눅스에서
		if(os.startsWith("Windows")){
			command = new String[3];
			command [0] = "command.com"; 
			command [1] = "/c";

		}else if(os.startsWith("Mac")){
			command = new String[3];
			command [0] = "/bin/csh"; 
			command [1] = "-c";	
			for(int i = 0; i< str.length; i ++){
				str[i] = str[i].replaceAll(LayoutManager.END_MARK_TOKEN, ":");
			}
		}
		for(int i = 0; i< str.length; i ++){
			if(command [2]!=null && !command[2].equals("")){

				command [2] = command[2] + LayoutManager.SPACE_TOKEN + "&" + LayoutManager.SPACE_TOKEN + str[i];
			}else{
				command [2] = str[i];
			}			
		}

		return commendExcute(command);
	}
	public void commanedExe(String cmd){
		Runtime rt=Runtime.getRuntime();
		try {
			Process pr=rt.exec(cmd);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	//명령어 실행시 내용입력 필요 시 사용 
	public ArrayList<String> commendAppendExcute(String[] cmd){
		ArrayList<String> list = new ArrayList();


		Process proc = null; 
		try
		{

			Runtime runtime = Runtime.getRuntime ();
			proc = runtime.getRuntime().exec("/bin/csh");

			DataOutputStream dao = new DataOutputStream(proc.getOutputStream());
			DataInputStream ins = new DataInputStream(proc.getInputStream());

			for(int i = 0; i < cmd.length; i++){
				dao.writeBytes(cmd[i]+LayoutManager.ENTER_TOKEN);
				dao.flush();
				Thread.sleep(100);
			}

			dao.writeBytes("exit"+LayoutManager.SPACE_TOKEN+LayoutManager.ENTER_TOKEN);
			dao.flush();
			Thread.sleep(100);

			int av = -1;
			while(av!=0){
				av=ins.available();
				if(av!=0){
					byte[] b = new byte[av];
					ins.read(b);
					System.out.println(new String(b));
				}

			}


		}
		catch ( Exception e ) {
			System.out.println ( e ); 
		}finally{

			if(proc!=null)try{proc.getErrorStream().close();}catch(Exception e){}
			if(proc!=null)try{proc.getInputStream().close();}catch(Exception e){}
			if(proc!=null)try{proc.getOutputStream().close();}catch(Exception e){}
			if(proc!=null)try{proc.waitFor();}catch(Exception e){}
		}
		return list;
	}
	//단일실행방식 
	public ArrayList<String> commendExcute(String[] cmd){
		ArrayList<String> list = new ArrayList();
		ArrayList<String> erros = new ArrayList();

		Process proc = null; 
		try
		{

			Runtime runtime = Runtime.getRuntime ();
			proc = runtime.getRuntime().exec(cmd);
			proc.waitFor ( );
			if ( proc.exitValue ( ) != 0 ) {
				BufferedReader err = new BufferedReader ( new InputStreamReader ( proc.getErrorStream ( ) ) );
				while ( err.ready ( ) )
					erros.add(err.readLine ( ));
				err.close ( );
			}
			else
			{
				BufferedReader out = new BufferedReader ( new InputStreamReader ( proc.getInputStream ( ) ) );
				while ( out.ready ( ) ){
					String text = out.readLine ( );
					System.out.println(text);
					list.add(text );
				}
				out.close ( );
			}
			proc.destroy ( );


		}
		catch ( Exception e ) {
			System.out.println ( e ); 
		}finally{

			if(proc!=null)try{proc.getErrorStream().close();}catch(Exception e){}
			if(proc!=null)try{proc.getInputStream().close();}catch(Exception e){}
			if(proc!=null)try{proc.getOutputStream().close();}catch(Exception e){}
			if(proc!=null)try{proc.waitFor();}catch(Exception e){}
			StringBuffer errorMsg = new StringBuffer();
			for(int i = 0 ; i < erros.size(); i ++){
				errorMsg.append(erros.get(i)+LayoutManager.ENTER_TOKEN);
			}
			System.out.println("---------------------------------------------------------------------------");
			System.out.println(cmd[cmd.length-1]);
			System.out.println(errorMsg.toString());

			if(erros.size()>0){
				errorMsg.append("위와 같은 에러 발생으로 중단하였습니다. 개발자에게 문의하세요." +LayoutManager.ENTER_TOKEN+ "pky1030@nate.com");
				ProjectManager.getInstance().showMessage(MessageDialog.ERROR, "에러", errorMsg.toString());
			}
		}
		return list;
	}
	public Image getExtImage(String fileName){


		ImageLoader loader = new ImageLoader();

		File file = new File(fileName);
		if(getImageMap(fileName)==null){
			if(file.canRead()&&file.isFile()){
				ImageData[] imageData =loader.load(fileName);
				if(imageData.length>0){
					ImageData ViewImageData=imageData[0];
					ImageDescriptor ViewImage = ImageDescriptor.createFromImageData(ViewImageData);
					Image viewImg =ViewImage.createImage();
					addImage(fileName,viewImg);

					return viewImg;

				}

			}
		}else{
			return getImageMap(fileName);
		}

		return null;

	}
	public Image getImage(String fileName){
		Image img = null;
		if(getImageMap(fileName)==null){
			img = ImageDescriptor.createFromFile(ImagePoint.class, fileName).createImage();
			addImage(fileName, img);
		}else{
			img = getImageMap(fileName);
		}
		return img;
	}

	public void addImage(String path,Image img){

		File f = new File(path);
		System.out.println(f.getPath());
		if(f.exists()){
			this.imageHistoryMap.put(path, f.lastModified());
		}else if(f.getPath().equals(f.getName())){
			this.imageHistoryMap.put(path, new Long("0"));
		}
		this.imageMap.put(path, img);
	}
	public Image getImageMap(String path){
		if(this.imageHistoryMap.get(path)!=null){
			File f = new File(path);
			if(f.exists()){
				if(this.imageHistoryMap.get(path)==f.lastModified()){
					return this.imageMap.get(path);
				}
			}else if(f.getPath().equals(f.getName())){
				return this.imageMap.get(path);
			}
			else if(path.indexOf(LayoutManager.SEPARATOR)==-1){
				return this.imageMap.get(path);
			}
		}
		return null;
	}

	public boolean isMediaExistsName(String path){
		ArrayList<UMLModel> list = ProjectManager.getInstance().getModels(-1);

		for(int i = 0; i < list.size(); i++){
			String text = (String)list.get(i).getPropertyValue(Property.ID_IMG);
			if(!text.equals("") && !path.equals(text))
				if(new File(text).getName().equals(new File(path).getName()))
					return true;

			text = (String)list.get(i).getPropertyValue(Property.ID_VIEDO_URL);
			if(!text.equals("") && !path.equals(text))
				if(new File(text).getName().equals(new File(path).getName()))
					return true;

		}
		return false;
	}

	//String [] 에 Index를 찾는 오퍼레이션.
	public int getStringIndex(String [] str ,String text){
		if(str!=null)
			for(int i = 0; i < str.length; i ++){
				if(str[i].equals(text)){
					return i;
				}
			}
		return -1;
	}

	//공백을 문자로 반환한다 
	public String getReplace(String s,String regx ,String replaceText){
		return s.replaceAll(regx, replaceText);
	}

	public HashMap<String, Image> getImageMap() {
		return imageMap;
	}
	public void setImageMap(HashMap<String, Image> imageMap) {
		this.imageMap = imageMap;
	}
	public UMLDiagramModel getOpenDiagramModel() {
		return openDiagramModel;
	}
	public void setOpenDiagramModel(UMLDiagramModel openDiagramModel) {
		this.openDiagramModel = openDiagramModel;
	}
	public UMLEditor getUMLEditor() {
		return umlEditor;
	}
	public void setUMLEditor(UMLEditor umlEditor) {
		this.umlEditor = umlEditor;
	}
	public static UMLEditManager getUmlEditManager() {
		return umlEditManager;
	}
	public static void setUmlEditManager(UMLEditManager umlEditManager) {
		ProjectManager.umlEditManager = umlEditManager;
	}
	public ArrayList<UMLEditPart> getRefreshList() {
		return refreshList;
	}
	public void setRefreshList(ArrayList<UMLEditPart> refreshList) {
		this.refreshList = refreshList;
	}
	public float getDip(UMLDiagramModel diagram,int px){		
		return px / (Integer.parseInt((String)diagram.getPropertyValue(Property.ID_DENSITY))/ 160);
	}
	public int getPix(UMLDiagramModel diagram,float px){
		return (int)px * (Integer.parseInt((String)diagram.getPropertyValue(Property.ID_DENSITY))/160);
	}
	public int getPix(UMLDiagramModel diagram,String px){
		px = px.replaceAll("dp", "");
		getPix(diagram,Float.parseFloat(px));
		return 0;
	}
	public void removeOperation(ArrayList<OperationItem> operations,String id){
		if(operations!=null)
			for(int i = 0 ; i < operations.size(); i ++){
				if(operations.get(i).id.equals(id)){
					operations.remove(i);
					break;
				}
			}

	}
	public OperationItem getOperation(ArrayList<OperationItem> operations,String id){
		if(operations!=null)
			for(int i = 0 ; i < operations.size(); i ++){
				if(operations.get(i).id.equals(id)){
					return operations.get(i);
				}
			}
		return null;
	}
	public OperationItem getNullCreateOperation(UMLModel basicModel,String id){
		basicModel = basicModel.getBasicModel();
		ArrayList<OperationItem> operations = LayoutManager.getInstance().getOperationMap(basicModel);
		OperationItem operationItem=null;
		if(operations!=null)
			operationItem = getOperation(operations,id);
		if(operationItem==null)
			operationItem = createIosOperation(id);

		return operationItem;
	}

	public boolean isMakeAndroid() {
		return isMakeAndroid;
	}


	public boolean isMakeIos() {
		return isMakeIos;
	}


	public void setMakeAndroid(boolean isMakeAndroid) {
		this.isMakeAndroid = isMakeAndroid;
	}


	public void setMakeIos(boolean isMakeIos) {
		this.isMakeIos = isMakeIos;
	}


	public boolean isLoad() {
		return isLoad;
	}


	public void setLoad(boolean isLoad) {
		this.isLoad = isLoad;
	}


	public boolean isActionSelection() {
		return isActionSelection;
	}


	public void setActionSelection(boolean isActionSelection) {
		this.isActionSelection = isActionSelection;
	}


	public String getSelectShodwID() {
		return selectShodwID;
	}


	public void setSelectShodwID(String selectShodwID) {
		this.selectShodwID = selectShodwID;
	}


	public String getProjectPath() {
		return projectPath;
	}


	public void setProjectPath(String projectPath) {
		this.projectPath = projectPath;
	}
	public String getAndroid_versionCode() {
		return android_versionCode;
	}
	public String getAndroid_versionName() {
		return android_versionName;
	}
	public void setAndroid_versionCode(String android_versionCode) {
		this.android_versionCode = android_versionCode;
	}
	public void setAndroid_versionName(String android_versionName) {
		this.android_versionName = android_versionName;
	}

	public String getAndroid_allowBackup() {
		return android_allowBackup;
	}
	public String getAndroid_icon() {
		return android_icon;
	}
	public String getAndroid_label() {
		return android_label;
	}
	public String getAndroid_theme() {
		return android_theme;
	}
	public void setAndroid_allowBackup(String android_allowBackup) {
		this.android_allowBackup = android_allowBackup;
	}
	public void setAndroid_icon(String android_icon) {

		this.android_icon = android_icon;
	}
	public void setAndroid_label(String android_label) {
		this.android_label = android_label;
	}
	public void setAndroid_theme(String android_theme) {
		this.android_theme = android_theme;
	}
	public String[] getAndroidSDK() {
		String[] str = new String[this.androidSDKList.size()];
		for(int i =0; i < this.androidSDKList.size(); i ++){
			str[i] = this.androidSDKList.get(i).toString();
		}
		return str;
	}

	public String getAndroidPackage() {
		return androidPackage;
	}
	public String getAndroidAppName() {
		return androidAppName;
	}
	public void setAndroidPackage(String androidPackage) {
		this.androidPackage = androidPackage;
	}
	public void setAndroidAppName(String androidAppName) {
		this.androidAppName = androidAppName;
	}
	public String getAndroidPath() {
		return androidPath;
	}
	public void setAndroidPath(String androidPath) {
		this.androidPath = androidPath;
	}
	public ArrayList<AndroidSDKItem> getAndroidSDKList() {
		return androidSDKList;
	}
	public void setAndroidSDKList(ArrayList<AndroidSDKItem> androidSDKList) {
		this.androidSDKList = androidSDKList;
	}
	public int getAndroid_MinSdkVersion() {
		return android_MinSdkVersion;
	}
	public int getAndroid_TargetSdkVersion() {
		return android_TargetSdkVersion;
	}
	public void setAndroid_MinSdkVersion(int android_MinSdkVersion) {
		this.android_MinSdkVersion = android_MinSdkVersion;
	}
	public void setAndroid_TargetSdkVersion(int android_TargetSdkVersion) {
		this.android_TargetSdkVersion = android_TargetSdkVersion;
	}
	public String getApplicationLocation() {
		return applicationLocation;
	}


	public void setApplicationLocation(String applicationLocation) {

		if(applicationLocation.lastIndexOf("plugins")>0){
			applicationLocation = applicationLocation.substring(0, applicationLocation.lastIndexOf("plugins"));
		}
		this.applicationLocation = applicationLocation;

		//		getInstance().showMessage(0, "", this.applicationLocation);
	}
	public int getOsVer() {
		return osVer;
	}
	public void setOsVer(int osVer) {
		this.osVer = osVer;
	}

	public String getAndroidSDKPath(){
		return getApplicationLocation()+LayoutManager.getInstance().ANDROID_MAC_SDK_PATH;
	}
	//다이얼로그 내용 입력시 다이얼로그이름_객체이름
	public void putTempData(String key,Object obj){
		tempData.put(key, obj);
	}
	public Object getTempData(String key){
		return tempData.get(key);
	}
	public HashMap<String, Object> getTempData() {
		return tempData;
	}
	public void setTempData(HashMap<String, Object> tempData) {
		this.tempData = tempData;
	}
	public String getAndroidKeyStore() {
		return androidKeyStore;
	}
	public void setAndroidKeyStore(String androidKeyStore) {
		this.androidKeyStore = androidKeyStore;
	}
	public String getAndroidAPK() {
		return androidAPK;
	}
	public void setAndroidAPK(String androidAPK) {
		this.androidAPK = androidAPK;
	}
	public String getIosAppName() {
		return iosAppName;
	}
	public String getIosPath() {
		return iosPath;
	}
	public void setIosAppName(String iosAppName) {
		this.iosAppName = iosAppName;
	}
	public void setIosPath(String iosPath) {
		this.iosPath = iosPath;
	}
	public boolean isCreate() {
		return isCreate;
	}
	public void setCreate(boolean isCreate) {
		this.isCreate = isCreate;
	}
	public String getTitleBarBackgroundImage() {
		return titleBarBackgroundImage;
	}


	public void setTitleBarBackgroundImage(String titleBarBackgroundImage) {
		this.titleBarBackgroundImage = titleBarBackgroundImage;
	}
	public RGB getTitleBarBackColor() {
		return titleBarBackColor;
	}
	public void setTitleBarBackColor(RGB titleBarBackColor) {
		this.titleBarBackColor = titleBarBackColor;
	}



}
