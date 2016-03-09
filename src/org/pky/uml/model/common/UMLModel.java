package org.pky.uml.model.common;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Vector;

import javax.imageio.ImageIO;

import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.views.properties.ComboBoxPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.PropertyDescriptor;
import org.eclipse.ui.views.properties.TextPropertyDescriptor;
import org.pky.uml.browser.common.propertybrowser.Property;
import org.pky.uml.browser.model.model.ModelTreeModel;
import org.pky.uml.commons.managers.LayoutManager;
import org.pky.uml.commons.managers.ProjectManager;
import org.pky.uml.descriptor.AutoMovePropertyDescriptor;
import org.pky.uml.descriptor.FileDialogPropertyDescriptor;
import org.pky.uml.descriptor.ListViewItemPropertyDescriptor;
import org.pky.uml.descriptor.ModelColorPropertyDescriptor;
import org.pky.uml.editor.UMLEditor;
import org.pky.uml.model.ElementGroupModel;
import org.pky.uml.model.ElementModel;
import org.pky.uml.model.InterfaceItem;
import org.pky.uml.model.LayoutModel;
import org.pky.uml.model.LineModel;
import org.pky.uml.model.OperationItem;
import org.pky.uml.model.UMLDiagramModel;
import org.pky.uml.model.UseCaseModel;
import org.pky.uml.model.ViewModel;
import org.pky.uml.policy.PolicyCommand;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class UMLModel extends UMLCommonModel{



	protected Rectangle location = new Rectangle(0,0,-1, -1);

	private ArrayList ports = new ArrayList();
	private ArrayList<UMLModel> childrens = new ArrayList();
	private ArrayList<UMLModel> parentModels = new ArrayList();
	private ArrayList<OperationItem> actionList = new ArrayList();

	private UMLModelGuide verticalGuide, horizontalGuide;
	protected UMLDataModel uDatas;
	//	private UMLModel parentModel;

	protected ElementModel nameEle;
	protected ElementModel stereoEle;


	protected IPropertyDescriptor[] descriptors = null;

	// 공통

	public static PropertyDescriptor nameProp = new TextPropertyDescriptor(Property.ID_NAME, "이름");
	public static PropertyDescriptor textProp = new TextPropertyDescriptor(Property.ID_TEXT, "Text");

	public static PropertyDescriptor colorProp = new ModelColorPropertyDescriptor(Property.ID_COLOR, "색상");

	public static PropertyDescriptor textSizeProp = new TextPropertyDescriptor(Property.ID_TEXT_SIZE, "Size");

	//LineTextModel
	public static String[] editTextPropString = new String[]{"None","textPersonName","textPassword","numberPassword","textEmailAddress","phone","textPostalAddress","textMultiLine","time","date","number","numberSigned"
		,"numberDecimal","AutoCompleteTextView","textMultiLine"};
	public static  PropertyDescriptor editTextProp = new ComboBoxPropertyDescriptor(Property.ID_TYPE, "타입",editTextPropString);

	public static String[] listViewTypePropString = new String[]{"None","expandable"};
	public static  PropertyDescriptor listViewTypeProp = new ComboBoxPropertyDescriptor(Property.ID_TYPE, "타입",listViewTypePropString);

	public static String[] goolgeMapTypePropString = new String[]{"GoogleMap v2"};
	public static  PropertyDescriptor goolgeMapTypeProp = new ComboBoxPropertyDescriptor(Property.ID_GOOGLE_MAP_TYPE, "타입",goolgeMapTypePropString);

	public static PropertyDescriptor goolgeMapKeyProp = new TextPropertyDescriptor(Property.ID_GOOGLE_MAP_KEY, "라이센스키");

	public static PropertyDescriptor webViewURLProp = new TextPropertyDescriptor(Property.ID_WEBVIEW_URL, "주소");
	public static PropertyDescriptor listViewItemProp = new ListViewItemPropertyDescriptor(Property.ID_LISTVIEW_ITEM, "리스트 아이템");
	public static PropertyDescriptor imageURLProp = new FileDialogPropertyDescriptor(Property.ID_IMG, "이미지",new String[]{"*.*"});

	public static PropertyDescriptor backgourndURLProp = new FileDialogPropertyDescriptor(Property.ID_IMG, "배경화면",new String[]{"*.*"});

	public static PropertyDescriptor widthProp = new TextPropertyDescriptor(Property.ID_WIDTH, "너비");
	public static PropertyDescriptor heightProp = new TextPropertyDescriptor(Property.ID_HEIGHT, "높이");
	public static PropertyDescriptor xProp = new TextPropertyDescriptor(Property.ID_X, "X");
	public static PropertyDescriptor yProp = new TextPropertyDescriptor(Property.ID_Y, "Y");

	public static PropertyDescriptor dipProp = new TextPropertyDescriptor(Property.ID_DENSITY, "density");
	public static PropertyDescriptor viedoURLProp = new FileDialogPropertyDescriptor(Property.ID_VIEDO_URL, "Viedo파일",new String[]{"*.mp4"});
	public static PropertyDescriptor viedoWebURLProp = new TextPropertyDescriptor(Property.ID_VIEDO_WEB_URL, "Viedo주소");
	public static PropertyDescriptor isMainProp = new ComboBoxPropertyDescriptor(Property.ID_MAIN, "시작 화면",
			new String[] {Property.ID_FALSE, Property.ID_TRUE });

	public static PropertyDescriptor isIOSViewRateProp = new ComboBoxPropertyDescriptor(Property.ID_RATE, "IOS 비율적용",
			new String[] { Property.ID_TRUE,Property.ID_FALSE });

	public static PropertyDescriptor isTitleMainProp = new ComboBoxPropertyDescriptor(Property.ID_TITLE_MAIN, "타이틀 표시",
			new String[] {Property.ID_FALSE, Property.ID_TRUE });

	public static PropertyDescriptor isAutoMoveProp = new AutoMovePropertyDescriptor(Property.ID_AUTO_MOVE, "자동화면이동");



	private String[] android_action_type = null;
	private HashMap<String,InterfaceItem> actionMap = new HashMap();

	protected Vector inputs = new Vector();

	protected Vector outputs = new Vector();

	private int modelType = 0;

	private ModelTreeModel modelTreeModel = null;



	public UMLModel(){
		super();
		if(uDatas==null)
			uDatas = new UMLDataModel(this);

		this.setModelType(ProjectManager.getInstance().getModelType(this));
		UMLModel basicModel = this.getBasicModel();

		descriptors = new IPropertyDescriptor[] {
				nameProp,dipProp
		};




	}
	public UMLModel(String name){
		this();
		this.setName(name);

	}
	public UMLModel(int width, int height){
		this();
		setSize(width,height);
	}
	public Object getPropertyValue(Object propName) {
		if(this.getUdata((String)propName)!=null)
			return this.getUdata((String)propName);
		else if(propName.equals(Property.ID_MAIN))
			return 0;
		else if(propName.equals(Property.ID_DENSITY))
			return "306";
		else if(propName.equals(Property.ID_TYPE))
			return 0;
		else if(propName.equals(Property.ID_TEXT_SIZE))
			return "10";
		else if(propName.equals(Property.ID_GOOGLE_MAP_TYPE))
			return 0;
		else if(propName.equals(Property.ID_LISTVIEW_ITEM))
			return null;
		else if(propName.equals(Property.ID_ACTION_ITEM))
			return null;
		else if(propName.equals(Property.ID_AUTO_MOVE))
			return null;
		else if(propName.equals(Property.ID_TITLE_MAIN))
			return 0;
		else if(propName.equals( Property.ID_RATE))
			return 0;
		else if(propName.equals(Property.ID_COLOR))
			return new RGB(0, 0, 0);
		else if(propName.equals(Property.ID_WIDTH))
			return String.valueOf(this.getViewModel().getSize().width());
		else if(propName.equals(Property.ID_HEIGHT))
			return String.valueOf(this.getViewModel().getSize().height());
		else if(propName.equals(Property.ID_X))
			return String.valueOf(this.getViewModel().getLocation().x);
		else if(propName.equals(Property.ID_Y))
			return String.valueOf(this.getViewModel().getLocation().y);
		else if(propName.equals(Property.ID_Y))
			return String.valueOf(this.getViewModel().getLocation().y);
		else
			return "";

	}
	public void setPropertyValue(Object id, Object value) {
		if(value instanceof String){
			String text = (String)value;

			//			if(id.equals(Property.ID_MAIN))
			//				value = Integer.parseInt(text);
			//			else if(id.equals(Property.ID_TYPE))
			//				value = Integer.parseInt(text);
			//			else if(id.equals(Property.ID_GOOGLE_MAP_TYPE))
			//				value = Integer.parseInt(text);
			//			else if(id.equals(Property.ID_TITLE_MAIN))
			//				value = Integer.parseInt(text);
			//			else if(id.equals(Property.ID_WIDTH))
			//				value = Integer.parseInt(text);
			//			else if(id.equals(Property.ID_HEIGHT))
			//				value = Integer.parseInt(text);
			//			else if(id.equals(Property.ID_X))
			//				value = Integer.parseInt(text);
			//			else if(id.equals(Property.ID_Y))
			//				value = Integer.parseInt(text);
			//			else if(id.equals(Property.ID_RATE))
			//				value = Integer.parseInt(text);

			if(getPropertyValue(id) instanceof Integer){
				value = Integer.parseInt(text);
			}


		}

		if(!ProjectManager.getInstance().isPropIntegrity(id, value)){
			return;
		}
		if(id.equals(Property.ID_TEXT)){
			System.out.println("");
		}
		if(id.equals(Property.ID_NAME)){
			setName((String)value);
		}else if(id.equals(Property.ID_LOCATION)){
			if(value instanceof Point)
				this.getViewModel().setLocation((Point)value);
		}else if(id.equals(Property.ID_DENSITY)){
			this.putUata(Property.ID_DENSITY, value);
		}else if(id.equals(Property.ID_MAIN)){

			// 프로젝트에 유일하게 하나만 시작 ID를 가질 수 있다. 

			int index = (Integer)value;
			if(index==1){
				ArrayList<UMLModel> list = ProjectManager.getInstance().getModels(ProjectManager.getInstance().getModelType("RelativeLayout"));

				for(int i = 0 ; i < list.size(); i ++){

					list.get(i).setPropertyValue(id, 0);
				}

			}
			this.putUata((String)id, value);
		}else if(id.equals(Property.ID_VIEDO_URL)){
			this.putUata(Property.ID_VIEDO_URL, value);
			this.putUata(Property.ID_VIEDO_WEB_URL, "");
		}else if(id.equals(Property.ID_VIEDO_WEB_URL)){

			this.putUata(Property.ID_VIEDO_WEB_URL, value);
			this.putUata(Property.ID_VIEDO_URL, "");
		}else if(id.equals(Property.ID_TITLE_MAIN)){
			this.putUata(Property.ID_TITLE_MAIN, value);
		}else if(id.equals(Property.ID_WIDTH)){
			if(isInteger(value))
				this.getViewModel().setSize(Integer.parseInt((String)value), this.getViewModel().getSize().height());
		}else if(id.equals(Property.ID_HEIGHT)){
			if(isInteger(value))
				this.getViewModel().setSize(this.getViewModel().getSize().width, Integer.parseInt((String)value));
		}else if(id.equals(Property.ID_X)){
			if(isInteger(value))
				this.getViewModel().setLocation(Integer.parseInt((String)value), this.getViewModel().getLocation().y);
		}else if(id.equals(Property.ID_Y)){
			if(isInteger(value))
				this.getViewModel().setLocation(this.getViewModel().getLocation().x, Integer.parseInt((String)value));
		}else if(id.equals(Property.ID_WEBVIEW_URL)){

			if(value instanceof String){
				String url = (String)value;

				if(url.startsWith("<iframe")){
					if(url.indexOf("http://")>=0){
						url=  url.substring(url.indexOf("http://"),url.length());
						url=  url.substring(0,url.indexOf(LayoutManager.QUO_WORLD));
					}else if(url.indexOf("//www")>=0){
						url=  "http://"+url.substring(url.indexOf("//www")+2,url.length());
						url=  url.substring(0,url.indexOf(LayoutManager.QUO_WORLD));	
					}
				}else if(!(url.startsWith("http://")||url.startsWith("https://")))
					url= "http://"+url;

				this.putUata((String)id, url);
			}
		}else if(id.equals(Property.ID_IMG)){
			if(value instanceof String){
				File f = new File((String)value);

				if(f.exists()&& f.isFile()){
					BufferedImage bimg = null;
					try{
						bimg = ImageIO.read(f);

						int imgWidth = bimg.getWidth();
						int imgHeight = bimg.getHeight();
						int modelWidth = this.getViewModel().getSize().width;
						int modelHeight = this.getViewModel().getSize().height;

						this.setSize(modelWidth, modelWidth*imgHeight/imgWidth);

					}catch(Exception e){
						e.printStackTrace();
					}


				}

				this.putUata((String)id, value);
			}
		}else{
			this.putUata((String)id, value);
		}
	}
	public void setLocation(int x, int y){
		location.x = x;
		location.y = y;
		setLocation(location);

	}
	public void setLocation(int x, int y,int width,int height){
		location.x = x;
		location.y = y;
		location.width = width;
		location.height = height;
		setLocation(location);

	}
	public void setSize(int width, int height){
		setSize(new Dimension(width, height));
	}
	public void setSize(Dimension size){
		this.location.width = size.width;
		this.location.height = size.height;

		firePropertyChange(PolicyCommand.PROPERTY_LAYOUT, null, location);

	}
	public Rectangle getLocationRect(){
		return new Rectangle(location);
	}
	public String getLocationString(){
		return location.x + LayoutManager.COMMAMA_WORLD + location.y + LayoutManager.COMMAMA_WORLD + location.width + LayoutManager.COMMAMA_WORLD+ location.height;
	}
	public Point getLocation(){
		return new Point(location.x,location.y);
	}
	public Dimension getSize(){
		return new Dimension(location.width,location.height);
	}

	public String getName() {
		if(this.getUdataString(Property.ID_NAME)==null)
			return "";
		else 
			return this.getUdataString(Property.ID_NAME);
	}
	public String getLayoutName(){
		String name = this.getName();

		return name.toLowerCase();
	}
	public void setName(String name) {
		this.putUata(Property.ID_NAME, name);
		if(this.getBasicModel().getModelTreeModel()!=null){
			this.getBasicModel().getModelTreeModel().setName(name);

		}else if(this.getModelTreeModel()!=null){
			this.getModelTreeModel().setName(name);
		}

		if(this instanceof UMLDiagramModel){
			UMLDiagramModel diagram  = (UMLDiagramModel)this;
			UMLEditor u = null;
			try {
				u = (UMLEditor)ProjectManager.getInstance().window.getActivePage().openEditor(diagram.getiEditorInput(), UMLEditor.ID, true);
			} catch (PartInitException e) {
				e.printStackTrace();
			}
			if(u!=null)
				u.setTitleName(name);
		}
		firePropertyChange(PolicyCommand.PROPERTY_LAYOUT, null, location);
	}
	public void addChild(UMLModel model){
		addChild(model,-1);
	}



	public void addChild(UMLModel model, int index) 
	{
		try{
			if(model==null)
				return;
			model.setParentModel(this);

			this.fireChildAdded(PolicyCommand.CHILDREN, model, index);




			if(!(this instanceof UMLDiagramModel)){
				int y = 0;
				if(this instanceof ElementGroupModel){
					y = this.getLocationRect().y();

				}
				if(this instanceof UseCaseModel && model instanceof ElementModel){
					if(y==0)
						y = 5;

				}
				for(int i = 0; i < this.getChildren().size(); i ++){
					y = y +this.getChildren().get(i).getSize().height();
				}

				model.setSize(this.getSize().width, model.getSize().height());
				model.setLocation(0, y);
				for(int i = 0; i < model.getChildren().size(); i ++){
					model.getChildren().get(i).setLocation(model.getChildren().get(i).getLocation().x,model.getChildren().get(i).getLocation().y+y);
				}
				if(this.getSize().height()<y){
					this.setSize(this.getSize().width, y);
				}
			}

			if (index >= 0){

				this.childrens.add(index, model);
			}else{
				this.childrens.add(model);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void connectInput(LineModel w) {
		//		inputs.put(w.getTargetTerminal(), w);
		inputs.addElement(w);
		update();
		fireStructureChange(Property.ID_INPUT, w);
	}

	public void connectOutput(LineModel w) {
		outputs.addElement(w);

		fireStructureChange(Property.ID_OUTPUT, w);
	}

	public void disconnectInput(LineModel w) {
		inputs.remove(w);
		update();
		fireStructureChange(Property.ID_INPUT, w);
	}

	public void disconnectOutput(LineModel w) {
		outputs.removeElement(w);
		update();
		fireStructureChange(Property.ID_OUTPUT, w);
	}

	public Vector getSourceConnections() {
		return (Vector)outputs.clone();
	}

	public Vector getTargetConnections() {
		Enumeration elements = inputs.elements();
		Vector v = new Vector(inputs.size());
		while (elements.hasMoreElements())
			v.addElement(elements.nextElement());
		return v;
	}
	public void update() {
	}

	public void setID(String id){
		uDatas.setID(id);
	}

	public String getID(){
		return uDatas.getID();
	}

	public HashMap<String, Object> getUdata(){
		return uDatas.getDataMap();
	}
	public void removeChild(UMLModel model){

		this.childrens.remove(model);
		fireChildRemoved(PolicyCommand.CHILDREN, model);
	}

	public ArrayList<UMLModel> getChildren() {
		return childrens;
	}

	public void setChildrens(ArrayList<UMLModel> childrens) {
		this.childrens = childrens;
	}

	public UMLModelGuide getVerticalGuide() {
		return verticalGuide;
	}

	public UMLModelGuide getHorizontalGuide() {
		return horizontalGuide;
	}

	public void setVerticalGuide(UMLModelGuide verticalGuide) {
		this.verticalGuide = verticalGuide;
	}

	public void setHorizontalGuide(UMLModelGuide horizontalGuide) {
		this.horizontalGuide = horizontalGuide;
	}

	public void setLocation(Point point){
		setLocation(new Rectangle(point.x, point.y, location.width, location.height));
	}
	public void setLocation(Rectangle location) {
		int x = location.x - this.getLocation().x;
		int y = location.y - this.getLocation().y;
		Rectangle rect = this.getLocationRect();
		this.location = location;
		firePropertyChange(PolicyCommand.PROPERTY_LAYOUT, null, location);

		if(this instanceof ViewModel && this.getBasicModel()!=null && this.getBasicModel() instanceof LayoutModel)
			ProjectManager.getInstance().setMoveAeroModel(this,rect,new Point(x,y));


		//location.x = 0;
		//location.y = 0;




	}
	public ArrayList<UMLModel> getParentModels(){
		return this.parentModels;
	}
	public UMLModel getParentModel() {
		for(int i = 0; i < this.parentModels.size(); i++){
			for(int j = 0; j < this.parentModels.get(i).getChildren().size(); j++){
				if(this.getID().equals(this.parentModels.get(i).getChildren().get(j).getID())){
					return this.parentModels.get(i);
				}
			}
		}
		return null;
	}
	public void fireProperty(){
		firePropertyChange(PolicyCommand.PROPERTY_LAYOUT, null, location);
	}
	public void setParentModel(UMLModel parentModel) {

		this.parentModels.add(parentModel);
	}

	public int getModelType() {
		return modelType;
	}
	public void setModelType(int modelType) {
		this.modelType = modelType;
	}
	public ModelTreeModel getModelTreeModel() {
		return modelTreeModel;
	}
	public void setModelTreeModel(ModelTreeModel modelTreeModel) {
		this.modelTreeModel = modelTreeModel;
	}
	public void putUata(String id,Object value){
		this.getUdata().put(id, value);
		firePropertyChange(PolicyCommand.CHANGE_VALUE, null, value);
	}
	public String getUdataString(String key){
		if(this.getUdata(key)!=null && this.getUdata(key) instanceof String)
			return (String)this.getUdata(key);
		else 
			return null;
	}
	public Object getUdata(String key){
		if(this.uDatas!=null)
			return this.uDatas.getDataMap().get(key);
		else{			
			return null;
		}
	}

	public IPropertyDescriptor[] getPropertyDescriptors() {
		ProjectManager.getInstance().getActionBrowser().refresh(this);
		if(getBasicModel() instanceof UMLDiagramModel)
			return descriptors;
		else
			return getViewModel().getDescriptors();
	}

	public UMLModel getBasicModel(){
		if(this instanceof ViewModel){
			return ((ViewModel)this).getBasiclModel();
		}else 
			return this;
	}

	public UMLModel getViewModel(){
		if(this instanceof ViewModel){
			return this;
		}else if(this.getParentModel() !=null && this.getParentModel() instanceof ViewModel){
			return this.getParentModel();
		}else{
			return this;
		}
	}
	public String getPackage(){
		StringBuffer sb = new StringBuffer();
		java.util.ArrayList list = new java.util.ArrayList();
		if(this.getBasicModel().modelTreeModel!=null){
			this.getBasicModel().modelTreeModel.getPackage(list);
		} 

		for(int i=list.size()-1;i>0;i--){
			if(i!=list.size()-1){
				String p = (String)list.get(i);

				sb.append(p);
				if(i<list.size()-1){
					if(i!=1)
						sb.append(".");
				}
			}
		}


		return sb.toString();
	}
	@Override
	public String writeSourceIOS() {
		// TODO Auto-generated method stub
		return null;
	}


	public void writeLayoutIOS() {
		LayoutManager.getInstance().setSourceModel(this);
		StringBuffer str = new StringBuffer();
		OperationItem operationItem = ProjectManager.getInstance().getNullCreateOperation(this, Property.OPERATION_VOID_LOADVIEW);
		str.append(getName()+LayoutManager.EQUALS_WORLD+LayoutManager.SPACE_TOKEN+LayoutManager.SQUARE_BRACKET_START_KEY_TOKEN+LayoutManager.SQUARE_BRACKET_START_KEY_TOKEN+ProjectManager.getInstance().getModelIOSType(ProjectManager.getInstance().getModelType(this))+LayoutManager.SPACE_TOKEN +"alloc"+LayoutManager.SQUARE_BRACKET_END_KEY_TOKEN+"initWithFrame"+LayoutManager.COLON_KEY_TOKEN+"CGRectMake("+ProjectManager.getInstance().getLayoutIOSLocation(this)+LayoutManager.FUNCTION_END_TOKEN+LayoutManager.SQUARE_BRACKET_END_KEY_TOKEN+LayoutManager.END_MARK_TOKEN+LayoutManager.ENTER_TOKEN);
		str.append(LayoutManager.SQUARE_BRACKET_START_KEY_TOKEN+"scrollView"+LayoutManager.SPACE_TOKEN+"addSubview"+LayoutManager.COLON_KEY_TOKEN+LayoutManager.SPACE_TOKEN+getName()+LayoutManager.SQUARE_BRACKET_END_KEY_TOKEN+LayoutManager.END_MARK_TOKEN+LayoutManager.ENTER_TOKEN);
		operationItem.getActionDetailList().add(ProjectManager.getInstance().addSourceLine(str.toString()));

		LayoutManager.getInstance().addOperationMap(this, operationItem);

		//		operationItem = ProjectManager.getInstance().getNullCreateOperation(LayoutManager.getInstance().getOperationMap(this), getViewModel().getID());
		//		LayoutManager.getInstance().addOperationMap(this, operationItem);



		LayoutManager.getInstance().writeSourceIOS(this);


		getPropertyValue(Property.ID_ACTION_ITEM);
	}
	public String writeSourceAndroid(){
		String text = LayoutManager.getInstance().writeSourceAndroid(this.getViewModel()).toString();
		if(!text.equals(""))
			return this.getName()+LayoutManager.getInstance().writeSourceAndroid(this.getViewModel()).toString();
		else 
			return "";
	}

	public boolean isInteger(Object value){
		try{
			Integer.parseInt((String)value);
		}catch (Exception e) {
			return false;
		}
		return true;
	}
	public Vector getInputs() {
		//PKY 08101587 S
		java.util.Vector arrayList = new java.util.Vector();
		arrayList.addAll(inputs);

		//PKY 08101587 E
		return arrayList;
	}
	public Vector getOutputs() {
		//PKY 08101587 S
		java.util.Vector arrayList = new java.util.Vector();
		arrayList.addAll(outputs);


		return arrayList;
	}

	public void setOutputs(Vector outputs) {
		this.outputs = outputs;
	}
	public void addAction(InterfaceItem interfaceItem){
		this.actionMap.put(interfaceItem.getId(), interfaceItem);
	}
	public InterfaceItem getInterface(String key) {
		if(this.actionMap.get(key)!=null)
			return this.actionMap.get(key);
		return null;
	}


	public OperationItem getAction(String key) {
		for(int i = 0; i < actionList.size(); i ++){
			if(actionList.get(i).id.equals(key))
				return actionList.get(i); 
		}
		return null;
	}
	public HashMap<String, InterfaceItem> getActionMap() {
		return actionMap;
	}
	public void setActions(HashMap<String, InterfaceItem> actions) {
		this.actionMap = actions;
	}
	public String[] getAndroid_Action_Type() {
		return android_action_type;
	}
	public void setAndroid_Action_Type(String[] aNDROID_ACTION_TYPE) {
		this.android_action_type = aNDROID_ACTION_TYPE;
	}
	public ArrayList<OperationItem> getActionBroserList(){
		ArrayList<OperationItem> list = new ArrayList();
		for(int i = 0 ; i < actionList.size(); i ++){
			if(actionList.get(i).isList){
				list.add(actionList.get(i));
			}
		}
		return list;
	}
	public String[] getActionBroserStringList(){
		ArrayList<OperationItem> list = new ArrayList();
		for(int i = 0 ; i < actionList.size(); i ++){
			if(actionList.get(i).isList){
				list.add(actionList.get(i));
			}
		}
		String[] toString = new String[list.size()];
		for(int i = 0 ; i < list.size(); i++ ){
			toString[i] = list.get(i).getLabel();
		}
		return toString;
	}

	public void setActionList(ArrayList<OperationItem> list) {
		String[] actionType = new String[list.size()];
		for(int i = 0 ; i < list.size(); i ++){
			list.get(i).modelID = this.getID();
			actionType[i] = list.get(i).getLabel();

		}

		this.setAndroid_Action_Type(actionType);
		this.actionList = list;
	}
	public ArrayList<OperationItem> getActionList() {
		return actionList;
	}
	public String getAndroidPackageName(){
		return ProjectManager.getInstance().getAndroidPackage() +LayoutManager.DOT_WORLD+ this.getViewModel().getPackage()+LayoutManager.DOT_WORLD+this.getName();
	}

	public String writeLayoutAndroid(){
		UMLModel viewModel = this.getViewModel();
		UMLModel viewLayoutModel = ProjectManager.getInstance().getAeroLayoutModel(viewModel);
		double x = ProjectManager.getInstance().getDistance(viewModel,viewLayoutModel,  SWT.LEFT);//1.3-7;
		double y = ProjectManager.getInstance().getDistance(viewModel,viewLayoutModel,  SWT.TOP);//1.3-7;

		double width = this.getViewModel().getSize().width;
		double height = this.getViewModel().getSize().height;

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
		System.out.println(this.getName());
		StringBuffer str = new StringBuffer();
		str.append(LayoutManager.ENTER_TOKEN);

		str.append("params"+LayoutManager.SPACE_TOKEN+LayoutManager.EQUALS_WORLD+LayoutManager.SPACE_TOKEN+LayoutManager.NEW_WORLD+LayoutManager.SPACE_TOKEN+"RelativeLayout"+LayoutManager.DOT_WORLD+"LayoutParams"+LayoutManager.FUNCTION_START_TOKEN+Integer.parseInt(String.valueOf(Math.round(width_percent)))+"*screenWidth/100"+LayoutManager.COMMAMA_WORLD+Integer.parseInt(String.valueOf(Math.round(height_percent)))+"*screenHeight/100"+LayoutManager.FUNCTION_END_TOKEN+LayoutManager.END_MARK_TOKEN+LayoutManager.ENTER_TOKEN);

		//		HashMap modelMap =ProjectManager.getInstance().getApproachModels(this.getViewModel());


		LayoutModel layoutModel =  (LayoutModel)viewLayoutModel.getBasicModel();
		Rectangle rect_Display = layoutModel.getDisplayRect();



		double left = 0;
		double top = 0;

		//		if(modelMap.get(SWT.LEFT)!=null){
		//			System.out.println( ((UMLModel)modelMap.get(SWT.LEFT)).getName());
		//			str.append("params"+LayoutManager.DOT_WORLD+"addRule"+LayoutManager.FUNCTION_START_TOKEN+"RelativeLayout.RIGHT_OF"+LayoutManager.COMMAMA_WORLD+LayoutManager.R_ID_WORD+LayoutManager.DOT_WORLD+((UMLModel)modelMap.get(SWT.LEFT)).getName()+LayoutManager.FUNCTION_END_TOKEN+LayoutManager.END_MARK_TOKEN+LayoutManager.ENTER_TOKEN);
		//			left = ProjectManager.getInstance().getDistance(this.getViewModel(), ((UMLModel)modelMap.get(SWT.LEFT)), SWT.RIGHT);
		//		}else{
		left = x;
		str.append("params"+LayoutManager.DOT_WORLD+"addRule"+LayoutManager.FUNCTION_START_TOKEN+"RelativeLayout.RIGHT_OF"+LayoutManager.COMMAMA_WORLD+LayoutManager.R_LAYOUT_WORD+LayoutManager.DOT_WORLD+layoutModel.getLayoutName()+LayoutManager.FUNCTION_END_TOKEN+LayoutManager.END_MARK_TOKEN+LayoutManager.ENTER_TOKEN);
		//		}

		//		if(modelMap.get(SWT.TOP)!=null){
		//			System.out.println( ((UMLModel)modelMap.get(SWT.TOP)).getName());
		//			str.append("params"+LayoutManager.DOT_WORLD+"addRule"+LayoutManager.FUNCTION_START_TOKEN+"RelativeLayout.BELOW"+LayoutManager.COMMAMA_WORLD+LayoutManager.R_ID_WORD+LayoutManager.DOT_WORLD+((UMLModel)modelMap.get(SWT.TOP)).getName()+LayoutManager.FUNCTION_END_TOKEN+LayoutManager.END_MARK_TOKEN+LayoutManager.ENTER_TOKEN);
		//			top = ProjectManager.getInstance().getDistance(this.getViewModel(), ((UMLModel)modelMap.get(SWT.TOP)), SWT.TOP);
		//		}else{
		top = y;
		str.append("params"+LayoutManager.DOT_WORLD+"addRule"+LayoutManager.FUNCTION_START_TOKEN+"RelativeLayout.BELOW"+LayoutManager.COMMAMA_WORLD+LayoutManager.R_LAYOUT_WORD+LayoutManager.DOT_WORLD+layoutModel.getLayoutName()+LayoutManager.FUNCTION_END_TOKEN+LayoutManager.END_MARK_TOKEN+LayoutManager.ENTER_TOKEN);


		//		}
		if(left>0){

			left = left / LayoutModel.DISPLAY_WIDTH * 100;
		}
		if(top>0){
			top = top / LayoutModel.DISPLAY_HEIGHT * 100;
		}
		str.append("params"+LayoutManager.DOT_WORLD+"setMargins"+LayoutManager.FUNCTION_START_TOKEN+Integer.parseInt(String.valueOf(Math.round(left))) +"*screenWidth/100"+LayoutManager.COMMAMA_WORLD+Integer.parseInt(String.valueOf(Math.round(top))) +"*screenHeight/100"+LayoutManager.COMMAMA_WORLD+"0"+LayoutManager.COMMAMA_WORLD+"0"+LayoutManager.FUNCTION_END_TOKEN+LayoutManager.END_MARK_TOKEN+LayoutManager.ENTER_TOKEN);
		str.append(this.getName()+LayoutManager.DOT_WORLD+"setLayoutParams"+LayoutManager.FUNCTION_START_TOKEN+"params"+LayoutManager.FUNCTION_END_TOKEN+LayoutManager.END_MARK_TOKEN+LayoutManager.ENTER_TOKEN);
		return str.toString();
	}

	public Element writeXMLLayoutAndroid(Document doc) {



		Element element = doc.createElement(ProjectManager.getInstance().getModelAndroidType(ProjectManager.getInstance().getModelType(this)));
		element.setAttribute("android:id", "@+id/"+this.getName());
		//		if(LayoutModel.DISPLAY_WIDTH == this.getViewModel().getSize().width){
		//			element.setAttribute("android:layout_width", "fill_parent");
		//		}else{
		element.setAttribute("android:layout_width", String.valueOf(ProjectManager.getInstance().getDip(ProjectManager.getInstance().getDiagramModel(this.getViewModel()), this.getViewModel().getSize().width))+"dp");
		//		}
		//		if(LayoutModel.DISPLAY_HEIGHT == this.getViewModel().getSize().height){
		//			element.setAttribute("android:layout_height", "fill_parent");
		//		}else{
		element.setAttribute("android:layout_height", String.valueOf(ProjectManager.getInstance().getDip(ProjectManager.getInstance().getDiagramModel(this.getViewModel()), this.getViewModel().getSize().height))+"dp");	
		//		}



		HashMap modelMap =ProjectManager.getInstance().getApproachModels(this.getViewModel());

		UMLModel viewLayoutModel = ProjectManager.getInstance().getAeroLayoutModel(this.getViewModel());
		LayoutModel layoutModel =  (LayoutModel)viewLayoutModel.getBasicModel();
		Rectangle rect_Display = layoutModel.getDisplayRect();

		if(modelMap.get(SWT.LEFT)!=null){
			element.setAttribute("android:layout_toRightOf", "@+id/"+((UMLModel)modelMap.get(SWT.LEFT)).getName());
			element.setAttribute("android:layout_marginLeft", ProjectManager.getInstance().getDip(ProjectManager.getInstance().getDiagramModel(this.getViewModel()),ProjectManager.getInstance().getDistance(this.getViewModel(), ((UMLModel)modelMap.get(SWT.LEFT)), SWT.LEFT))+"dp");			
		}else{
			element.setAttribute("android:layout_marginLeft", ProjectManager.getInstance().getDip(ProjectManager.getInstance().getDiagramModel(this.getViewModel()),ProjectManager.getInstance().getDistance(this.getViewModel(),viewLayoutModel,  SWT.LEFT))+"dp");
		}

		if(modelMap.get(SWT.TOP)!=null){
			element.setAttribute("android:layout_below", "@+id/"+((UMLModel)modelMap.get(SWT.TOP)).getName());
			element.setAttribute("android:layout_marginTop", ProjectManager.getInstance().getDip(ProjectManager.getInstance().getDiagramModel(this.getViewModel()),ProjectManager.getInstance().getDistance(this.getViewModel(), ((UMLModel)modelMap.get(SWT.TOP)), SWT.TOP))+"dp");
		}else{
			element.setAttribute("android:layout_marginTop", ProjectManager.getInstance().getDip(ProjectManager.getInstance().getDiagramModel(this.getViewModel()),ProjectManager.getInstance().getDistance(this.getViewModel(),viewLayoutModel,  SWT.TOP))+"dp");
		}

		//		if(modelMap.get(SWT.LEFT)!=null && modelMap.get(SWT.BOTTOM)!=null){
		//			element.setAttribute("android:layout_alignBottom", "@+id/"+((UMLModel)modelMap.get(SWT.BOTTOM)).getName());
		//			element.setAttribute("android:layout_marginBottom", ProjectManager.getInstance().getDip(ProjectManager.getInstance().getDiagramModel(this.getViewModel()),ProjectManager.getInstance().getDistance(this.getViewModel(), ((UMLModel)modelMap.get(SWT.BOTTOM)), SWT.BOTTOM))+"dp");
		//		}
		if(!this.getPropertyValue(Property.ID_TEXT).equals(""))
			element.setAttribute("android:text", (String)getPropertyValue(Property.ID_TEXT));


		return element;
	}
	public IPropertyDescriptor[] getDescriptors() {
		return descriptors;
	}
	public void setDescriptors(IPropertyDescriptor[] descriptors) {
		this.descriptors = descriptors;
	}




}
