package org.pky.uml.commons.managers;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.jface.action.IAction;
import org.eclipse.swt.graphics.RGB;
import org.pky.uml.browser.common.propertybrowser.Property;
import org.pky.uml.browser.model.model.ModelTreeModel;
import org.pky.uml.model.UMLDiagramModel;
import org.pky.uml.model.ViewModel;
import org.pky.uml.model.WebViewModel;
import org.pky.uml.model.YoutubeModel;
import org.pky.uml.model.action.ActionAlertMessageItem;
import org.pky.uml.model.action.ActionAutoMoveActionItem;
import org.pky.uml.model.action.ActionItem;
import org.pky.uml.model.action.ActionMobileServiceCallItem;
import org.pky.uml.model.action.ActionMoveItem;
import org.pky.uml.model.action.IActionItem;
import org.pky.uml.model.action.ListViewerItem;
import org.pky.uml.model.command.TreeParent;
import org.pky.uml.model.common.UMLModel;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class ModelDataManager {
	private static ModelDataManager instance;

	private DocumentBuilderFactory dbFactory;
	private DocumentBuilder dBuilder;
	private Document doc;

	private Element verRootElement;
	private Element projectRootElement;
	private Element modelsRootElement;

	private Element diagramElement;
	private Element viewModelElement;
	private Element basicModelElement;

	public static final String VER_ELEMENT = "VER_ROOT_ELEMENT";
	public static final String PROJECT_ELEMENT = "PROJECT_ROOT_ELEMENT";
	public static final String MODELS_ELEMENT = "MODELS_ROOT_ELEMENT";

	public static final String DIAGRAM_ROOT_ELEMENT = "DIAGRAMMODELS_ELEMENT";
	public static final String DIAGRAM_ELEMENT = "DIAGRAMMODEL_ELEMENT";
	public static final String VIEW_ROOT_ELEMENT = "VIEWMODELS_ELEMENT";
	public static final String BASIC_ROOT_ELEMENT = "BASICMODELS_ELEMENT";

	public static final String ID_PARENT = "ID_PARENT";
	public static final String ID_VIEW = "ID_VIEW";
	public static final String ID_BASIC = "ID_BASIC";
	public static final String ID = "ID";
	public static final String ID_MODEL_PARENT_TREE = "ID_MODEL_PARENT_TREE";
	public static final String ID_MODEL_TREE = "ID_MODEL_TREE";
	public static final String ID_LOCATION = "ID_LOCATION";

	public static final String ACTION = "ACTION";
	public static final String TYPE = "TYPE";

	private boolean isLoad = false;



	public static ModelDataManager getInstance() {
		if (instance == null) {
			instance = new ModelDataManager();
			return instance;
		}
		else {
			return instance;
		}
	}
	private void writeProject(){
		projectRootElement = doc.createElement(PROJECT_ELEMENT);

		Element projectNameElement = doc.createElement("PROJECTPATH");
		projectNameElement.setTextContent(ProjectManager.getInstance().getProjectPath());
		projectRootElement.appendChild(projectNameElement);

		Element chkndroidElement = doc.createElement("CHKANDROID");
		chkndroidElement.setTextContent(String.valueOf(ProjectManager.getInstance().isMakeAndroid()));
		projectRootElement.appendChild(chkndroidElement);

		Element androidAppNameElement = doc.createElement("ANDROIDAPPNAME");
		androidAppNameElement.setTextContent(ProjectManager.getInstance().getAndroidAppName());
		projectRootElement.appendChild(androidAppNameElement);

		Element androidPackageNameElement = doc.createElement("ANDROIDPACKAGENAME");
		androidPackageNameElement.setTextContent(ProjectManager.getInstance().getAndroidPackage());
		projectRootElement.appendChild(androidPackageNameElement);

		Element androidPathElement = doc.createElement("ANDROIDPATH");
		androidPathElement.setTextContent(ProjectManager.getInstance().getAndroidPath());
		projectRootElement.appendChild(androidPathElement);

		Element android_MinSdkVersionElement = doc.createElement("ANDROID_MINSDKVERSION");
		android_MinSdkVersionElement.setTextContent(String.valueOf(ProjectManager.getInstance().getAndroid_MinSdkVersion()));
		projectRootElement.appendChild(android_MinSdkVersionElement);

		Element android_TargetSdkVersionElement = doc.createElement("ANDROID_TARGETSDKVERSION");
		android_TargetSdkVersionElement.setTextContent(String.valueOf(ProjectManager.getInstance().getAndroid_TargetSdkVersion()));
		projectRootElement.appendChild(android_TargetSdkVersionElement);

		Element initAndroidFilesElement = doc.createElement("INITANDROIDFILES");
		initAndroidFilesElement.setTextContent(String.valueOf(ProjectManager.getInstance().getAndroidPath()));
		projectRootElement.appendChild(androidPathElement);	

		Element chkIosElement = doc.createElement("CHKIOSELEMENT");
		chkIosElement.setTextContent(String.valueOf(ProjectManager.getInstance().isMakeIos()));
		projectRootElement.appendChild(chkIosElement);	

		Element iosPathElement = doc.createElement("IOSPATH");
		iosPathElement.setTextContent(String.valueOf(ProjectManager.getInstance().getIosPath()));
		projectRootElement.appendChild(iosPathElement);	

		Element iosAppName = doc.createElement("IOSAPPNAME");
		iosAppName.setTextContent(String.valueOf(ProjectManager.getInstance().getIosAppName()));
		projectRootElement.appendChild(iosAppName);



		if(ProjectManager.getInstance().getTitleBarBackgroundImage()!=null){
			Element iosTitleBarBackgorundImg = doc.createElement("IOS_TITLEBAR_BACKGROUND_IMG");
			iosTitleBarBackgorundImg.setTextContent(String.valueOf(ProjectManager.getInstance().getTitleBarBackgroundImage()));
			projectRootElement.appendChild(iosTitleBarBackgorundImg);
		}

		Element iosTitleBarBackColor = doc.createElement("IOS_TITLEBAR_BACK_COLOR");
		if(ProjectManager.getInstance().getTitleBarBackColor()!=null){
			iosTitleBarBackColor.setAttribute("RED", String.valueOf(ProjectManager.getInstance().getTitleBarBackColor().red));
			iosTitleBarBackColor.setAttribute("GREEN", String.valueOf(ProjectManager.getInstance().getTitleBarBackColor().green));
			iosTitleBarBackColor.setAttribute("BULE", String.valueOf(ProjectManager.getInstance().getTitleBarBackColor().blue));
			projectRootElement.appendChild(iosTitleBarBackColor);
		}


		verRootElement.appendChild(projectRootElement);


	}
	private void writeDiagram(){
		diagramElement = doc.createElement(DIAGRAM_ROOT_ELEMENT);

		ArrayList<UMLModel> list = ProjectManager.getInstance().getModels(1);

		for(int i=0; i < list.size(); i ++){
			for(int j = 0; j <list.get(i).getChildren().size(); j++){
				UMLModel viewModel = list.get(i).getChildren().get(j);
				Element element = doc.createElement(ProjectManager.getInstance().getModelDefaultName(ProjectManager.getInstance().getModelType(viewModel),false));


				Element parentDiagramElement = doc.createElement(ID_PARENT);
				parentDiagramElement.setTextContent(list.get(i).getID());
				element.appendChild(parentDiagramElement);

				Element viewElement = doc.createElement(ID_VIEW);
				viewElement.setTextContent(viewModel.getViewModel().getID());
				element.appendChild(viewElement);

				Element locationElement = doc.createElement(ID_LOCATION);
				locationElement.setTextContent(viewModel.getLocationString());
				element.appendChild(locationElement);
				diagramElement.appendChild(element);

			}

		}
		modelsRootElement.appendChild(diagramElement);

	}
	private void writeBasicModel(){
		basicModelElement = doc.createElement(BASIC_ROOT_ELEMENT);

		ArrayList<UMLModel> list = ProjectManager.getInstance().getModels(-1);

		for(int i=0; i < list.size(); i ++){
			//			for(int j = 0; j <list.get(i).getChildren().size(); j++){
			if(list.get(i) instanceof ViewModel)
				continue;
			if(list.get(i).getBasicModel() instanceof YoutubeModel)
				System.out.println("");
			Element element = doc.createElement(ProjectManager.getInstance().getModelDefaultName(ProjectManager.getInstance().getModelType(list.get(i)),false));

			Element modelTreeParentElement = doc.createElement(ID_MODEL_PARENT_TREE);


			if(list.get(i).getBasicModel().getModelTreeModel().getParentTreeModel()!=null&&!list.get(i).getBasicModel().getModelTreeModel().getParentTreeModel().getID().equals(ModelBrowserManager.getInstance().getModelBrowser().getRoot().getID()))
				modelTreeParentElement.setTextContent(list.get(i).getBasicModel().getModelTreeModel().getParentTreeModel().getID());
			else
				modelTreeParentElement.setTextContent("");	
			element.appendChild(modelTreeParentElement);

			Element modelTreeElement = doc.createElement(ID_MODEL_TREE);
			modelTreeElement.setTextContent(list.get(i).getBasicModel().getModelTreeModel().getID());
			element.appendChild(modelTreeElement);

			Element modelIDElement = doc.createElement(ID);
			modelIDElement.setTextContent(list.get(i).getID());
			element.appendChild(modelIDElement);

			writeUData(element,list.get(i));


			basicModelElement.appendChild(element);
			



		}

		modelsRootElement.appendChild(basicModelElement);

	}

	private void writeUData(Element element,UMLModel model){
		Iterator iterator = model.getUdata().keySet().iterator();

		while(iterator.hasNext()){
			String key = (String)iterator.next();

			if(model.getPropertyValue(key)!=null && !key.equals(Property.ID_ACTION_ITEM)){
				Object data = model.getPropertyValue(key);

				Element uDataelement = doc.createElement(key);
				if(key.equals(Property.ID_LISTVIEW_ITEM)){
					for(int i = 0; i < ((ListViewerItem)data).getChildren().length; i ++){
						wrtieActionListView(uDataelement,(ListViewerItem)((ListViewerItem)data).getChildren()[i]);	
					}


				}else if(key.equals(Property.ID_AUTO_MOVE)){
					ActionAutoMoveActionItem actionAutoMoveActionItem = (ActionAutoMoveActionItem)data;
					wrtieActionAutoMove(uDataelement,actionAutoMoveActionItem);
				}else if(key.equals(Property.ID_COLOR)){
					RGB rgb = (RGB)data;
					uDataelement.setAttribute("RED", String.valueOf(rgb.red));
					uDataelement.setAttribute("GREEN", String.valueOf(rgb.green));
					uDataelement.setAttribute("BULE", String.valueOf(rgb.blue));

				}else if(data instanceof String){
					String str = (String)data;
					str = str.replaceAll(LayoutManager.ENTER_TOKEN, LayoutManager.ENTER_TOKEN_TEMP);
					uDataelement.setTextContent(str);
				}else{
					uDataelement.setTextContent(data.toString());
				}
				element.appendChild(uDataelement);

			}

		}
	}
	private void wrtieActionListView(Element uDataelement,ListViewerItem item){
		uDataelement.appendChild(item.writeModel(doc));
		for(int i = 0; i < item.getChildren().length; i ++){
			wrtieActionListView(uDataelement,(ListViewerItem)item.getChildren()[i]);


		}
	}
	private void wrtieActionAutoMove(Element uDataelement,ActionAutoMoveActionItem item){

		//		uDataelement.appendChild(item.writeModel(doc));

		uDataelement.setAttribute("LAYOUT_ID", item.getLayoutID());
		uDataelement.setAttribute("TIME", String.valueOf(item.getTime()));

	}
	private void writeViewModel(){
		viewModelElement = doc.createElement(VIEW_ROOT_ELEMENT);



		ArrayList<UMLModel> list = ProjectManager.getInstance().getModels(1);

		for(int i=0; i < list.size(); i ++){
			for(int j = 0; j <list.get(i).getChildren().size(); j++){
				UMLModel viewModel = list.get(i).getChildren().get(j);

				Element element = doc.createElement(ProjectManager.getInstance().getModelDefaultName(ProjectManager.getInstance().getModelType(viewModel),false));

				Element basicIDElement = doc.createElement(ID_BASIC);
				basicIDElement.setTextContent(viewModel.getBasicModel().getID());
				element.appendChild(basicIDElement);

				Element modelIDElement = doc.createElement(ID_VIEW);
				modelIDElement.setTextContent(viewModel.getID());
				element.appendChild(modelIDElement);

				if(viewModel.getPropertyValue(Property.ID_ACTION_ITEM)!=null){
					ArrayList<ActionItem> actionList = (ArrayList)viewModel.getPropertyValue(Property.ID_ACTION_ITEM);
					for(int k = 0 ; k < actionList.size(); k++){
						Element actionElement = doc.createElement(ACTION);
						if(actionList.get(k).getOperation()==null)
							continue;
						actionElement.setAttribute(TYPE, String.valueOf(actionList.get(k).getOperation().id));
						//						Element actionElement = doc.createElement("tsss");
						Iterator<String> iterator = actionList.get(k).getIterator();
						while(iterator.hasNext()){
							Object obj = actionList.get(k).get(iterator.next());
							if(obj instanceof IActionItem){
								IActionItem item = (IActionItem)obj;
								Element detailActionElement  = item.writeModel(doc);
								detailActionElement.setAttribute(ModelDataManager.TYPE, Property.ID_ACTION_ITEM);
								detailActionElement.setAttribute(ModelDataManager.ID_PARENT, viewModel.getID());
								actionElement.appendChild(detailActionElement);
							}
						}
						element.appendChild(actionElement);
					}
				}
				viewModelElement.appendChild(element);

			}

		}


		modelsRootElement.appendChild(viewModelElement);

	}
	private void writeActionData(){

	}

	private void writeModels(){
		modelsRootElement = doc.createElement(MODELS_ELEMENT);
		verRootElement.appendChild(modelsRootElement);
		writeBasicModel();
		writeViewModel();		
		writeDiagram();

	}
	public void loadFile(String path){
		isLoad = true;
		try{


			SAXParserFactory factory = SAXParserFactory.newInstance();
			SAXParser saxParser = factory.newSAXParser();

			DefaultHandler handler = new DefaultHandler() {

				ElementDataModel data = new ElementDataModel();
				UMLModel model = null;
				UMLDiagramModel diagram = null;
				boolean isProjectElement = false;
				boolean isBasicElement = false;
				boolean isViewElement = false;
				boolean isDiagramElement = false;

				String stratElement = "";
				String endElement = "";
				String tempData = "";
				public void startElement(String uri, String localName,String qName, 
						Attributes attributes) throws SAXException {
					System.out.println("Start Element :" + qName);

					data.key = qName;
					data.attribute.clear();

					if(qName.equals(PROJECT_ELEMENT)){
						isProjectElement = true;

						isBasicElement = false;
						isViewElement = false;
						isDiagramElement = false;
					}else if(qName.equals(BASIC_ROOT_ELEMENT)){
						isBasicElement = true;

						isProjectElement = false;
						isViewElement = false;
						isDiagramElement = false;
					}else if(qName.equals(VIEW_ROOT_ELEMENT)){
						isViewElement = true;

						isProjectElement = false;
						isBasicElement = false;
						isDiagramElement = false;
					}else if(qName.equals(DIAGRAM_ROOT_ELEMENT)){
						isDiagramElement = true;

						isProjectElement = false;
						isBasicElement = false;
						isViewElement = false;
					}

					if(isBasicElement && ProjectManager.getInstance().getModelType(qName)!=-1){
						model = ProjectManager.getInstance().addUMLModel(null, ProjectManager.getInstance().getModelType(qName), null, null, false);
					}


					for(int i =0 ;i < attributes.getLength(); i++){
						String obj1 = attributes.getQName(i);
						String obj = attributes.getValue(i);

						data.attribute.put(obj1, obj);

					}

					if(isBasicElement && qName.equals(Property.ID_LISTVIEW_ITEM)){

						ListViewerItem modelItem = (ListViewerItem)model.getPropertyValue(Property.ID_LISTVIEW_ITEM);
						if(modelItem==null){
							modelItem = new ListViewerItem("List");
						}
						if(data.getIterator().hasNext()){
							Iterator<String> iterator = data.getIterator();
							if(iterator.hasNext()){
								ListViewerItem item = new ListViewerItem(data.attribute.get(Property.ID_NAME));
								item.setId(data.attribute.get(ID));
								TreeParent treeParent = ProjectManager.getInstance().getSearchID(modelItem, data.attribute.get(ID_PARENT));

								if(treeParent !=null){
									treeParent.addChild(item);
								}else if(modelItem!=null){
									modelItem.addChild(item);
								}


							}
						}
						model.setPropertyValue(data.key, modelItem);


					}else if(isBasicElement && qName.equals(Property.ID_AUTO_MOVE)){


						if(data.getIterator().hasNext()){
							Iterator<String> iterator = data.getIterator();
							String layoutID = "";
							int time = 0;
							while(iterator.hasNext()){
								String key = (String)iterator.next();
								if(key.equals("LAYOUT_ID")){
									Object obj =data.attribute.get(key);
									layoutID = data.attribute.get(key);
								}else if(key.equals("TIME")){
									time = Integer.parseInt(data.attribute.get(key));
								}
							}
							model.setPropertyValue(data.key, new ActionAutoMoveActionItem(layoutID, time));
						}



					}else if(isBasicElement && qName.equals(Property.ID_COLOR)){

						model.setPropertyValue(data.key, new RGB(Integer.parseInt(data.attribute.get("RED")),Integer.parseInt(data.attribute.get("GREEN")),Integer.parseInt(data.attribute.get("BULE"))));

					}else if(isViewElement && qName.equals(ModelDataManager.ACTION)){
						int type = -1;
						if(model!=null && model.getViewModel()!=null){
							for(int i = 0 ; i < model.getViewModel().getActionList().size(); i ++){
								if(model.getViewModel().getActionList().get(i).id.equals(data.attribute.get(TYPE))){
									type = i;
									break;
								}
							}
							Object ojb = model.getPropertyValue(Property.ID_ACTION_ITEM);
							ArrayList<ActionItem> list = (ArrayList)model.getPropertyValue(Property.ID_ACTION_ITEM);
							if(list==null)
								list = new ArrayList();
							if(type!=-1){
								ActionItem actionItem = new ActionItem(type);

								actionItem.setOperation(model.getViewModel().getActionBroserList().get(type));

								list.add(actionItem);
								model.setPropertyValue(Property.ID_ACTION_ITEM, list);

							}

						}
					}

					IActionItem item = null;

					if(qName.equals(Property.ACTION_ALERT)){
						item = new ActionAlertMessageItem(data.attribute.get("TITLE"), data.attribute.get("MESSAGE"));
					}
					else if(qName.equals(Property.ACTION_MOBILE_SERVICE_CALL)){
						item = new ActionMobileServiceCallItem(data.attribute.get("ID"), data.attribute.get("VALUE"));
					}else if(qName.equals(Property.ACTION_MOVE_LAYOUT)){
						item = new ActionMoveItem(data.attribute.get("ID"));
					}
					if(item!=null){
						if(data.attribute.get(TYPE).equals(Property.ID_ACTION_ITEM)){

							ArrayList<ActionItem> list = (ArrayList)ProjectManager.getInstance().getSearchID(data.attribute.get(ModelDataManager.ID_PARENT)).getPropertyValue(Property.ID_ACTION_ITEM);
							if(list!=null && list.size()>0){
								ActionItem actionItem = list.get(list.size()-1);
								actionItem.put(qName, item);

							}
							ProjectManager.getInstance().getSearchID(data.attribute.get(ModelDataManager.ID_PARENT)).setPropertyValue(Property.ID_ACTION_ITEM, list);
						}else if(data.attribute.get(TYPE).equals(Property.ID_LISTVIEW_ITEM)){

							ListViewerItem modelItem = (ListViewerItem)model.getPropertyValue(Property.ID_LISTVIEW_ITEM);

							ListViewerItem treeParent = (ListViewerItem)ProjectManager.getInstance().getSearchID(modelItem, data.attribute.get(ModelDataManager.ID_PARENT));
							if(treeParent!=null){
								treeParent.addAction(qName, item);
							}

						}
					}



				}

				public void endElement(String uri, String localName,
						String qName) throws SAXException {


					if(isProjectElement){
						if(data.key.equals("PROJECTPATH")){
							ProjectManager.getInstance().setProjectPath(data.value);
						}else if(data.key.equals("CHKANDROID")){
							ProjectManager.getInstance().setMakeAndroid(Boolean.parseBoolean(data.value));
						}else if(data.key.equals("ANDROIDAPPNAME")){
							ProjectManager.getInstance().setAndroidAppName(data.value);
						}else if(data.key.equals("ANDROIDPACKAGENAME")){
							ProjectManager.getInstance().setAndroidPackage(data.value);
						}else if(data.key.equals("ANDROIDPATH")){
							ProjectManager.getInstance().setAndroidPath(data.value);
						}else if(data.key.equals("ANDROID_MINSDKVERSION")){
							ProjectManager.getInstance().setAndroid_MinSdkVersion(Integer.parseInt(data.value));
						}else if(data.key.equals("ANDROID_TARGETSDKVERSION")){
							ProjectManager.getInstance().setAndroid_TargetSdkVersion(Integer.parseInt(data.value));
						}else if(data.key.equals("INITANDROIDFILES")){
							ProjectManager.getInstance().setAndroidPath(data.value);
						}else if(data.key.equals("CHKIOSELEMENT")){
							ProjectManager.getInstance().setMakeIos(Boolean.parseBoolean(data.value));
						}else if(data.key.equals("IOSPATH")){
							ProjectManager.getInstance().setIosPath(data.value);
						}else if(data.key.equals("IOSAPPNAME")){
							ProjectManager.getInstance().setIosAppName(data.value);
						}else if(data.key.equals("IOS_TITLEBAR_BACKGROUND_IMG")){
							ProjectManager.getInstance().setTitleBarBackgroundImage(data.value);
						}else if(data.key.equals("IOS_TITLEBAR_BACK_COLOR")){
							ProjectManager.getInstance().setTitleBarBackColor(new RGB(Integer.parseInt(data.attribute.get("RED")),Integer.parseInt(data.attribute.get("GREEN")),Integer.parseInt(data.attribute.get("BULE"))));
						}
					}else if(isBasicElement){

						if(model!=null){
							System.out.println("-------->"+model.getName());	


							if(data.key.equals(ModelDataManager.ID_MODEL_PARENT_TREE)){
								ModelTreeModel treeModel = null;
								if(!data.value.equals(""))
									treeModel = ModelBrowserManager.getInstance().getSearchID(ModelBrowserManager.getInstance().getModelBrowser().getRoot(),data.value);
								else
									treeModel = ModelBrowserManager.getInstance().getModelBrowser().getRoot();

								ModelTreeModel modelTreeModel = new ModelTreeModel(model.getBasicModel());
								treeModel.addChild(modelTreeModel);

							}else if(data.key.equals(ModelDataManager.ID_MODEL_TREE)){
								if(!data.value.equals(""))
									model.getBasicModel().getModelTreeModel().setID(data.value);
							}else if(data.key.equals(ModelDataManager.ID)){
								if(!data.value.equals(""))
									model.getBasicModel().setID(data.value);
							}else if(data.key.equals(Property.ACTION_MOVE_LAYOUT)){
								System.out.println("");
							}else if(data.key.equals(Property.ACTION_ALERT)){
								System.out.println("");
							}else{
								System.out.println(data.key);
								System.out.println(data.value);
								if(!data.value.equals("")){
									data.value = data.value.replaceAll(LayoutManager.ENTER_TOKEN_TEMP, LayoutManager.ENTER_TOKEN);
									model.setPropertyValue(data.key, data.value);
								}
							}
						}
					}else if(isViewElement){
						if(!data.value.equals("")){
							System.out.println("===========>"+data.value);
							if(data.key.equals(ModelDataManager.ID_BASIC))
								model = ProjectManager.getInstance().getSearchID(data.value);
							if(data.key.equals(ModelDataManager.ID_VIEW) && model!=null)
								model.getViewModel().setID(data.value);
						}
					}else if(isDiagramElement){
						if(data.key.equals(ModelDataManager.ID_PARENT))
							diagram = (UMLDiagramModel)ProjectManager.getInstance().getSearchID(data.value);
						if(data.key.equals(ModelDataManager.ID_VIEW)){
							model = ProjectManager.getInstance().getSearchID(data.value);
						}
						if(data.key.equals(ModelDataManager.ID_LOCATION)){
							if(!data.value.equals("")){
								diagram.addChild(model);
								String[] split = data.value.split("\\,");
								try{
								if(split.length==4 && model!=null)
									model.setLocation(new Rectangle(Integer.parseInt(split[0]),Integer.parseInt(split[1]),Integer.parseInt(split[2]),Integer.parseInt(split[3])));
								}catch (Exception e) {
									e.printStackTrace();
								}
							}
						}

					}
					endElement = qName;
				}



				public void characters(char ch[], int start, int length) throws SAXException {
					data.value = new String(ch, start, length).trim();


				}
				class ElementDataModel{
					public String key = "";
					public String value = "";
					public HashMap<String,String> attribute = new HashMap();

					public Iterator<String> getIterator(){
						return attribute.keySet().iterator();
					}

				}
			};

			saxParser.parse(path, handler);

			ModelBrowserManager.getInstance().getModelBrowser().getViewer().refresh();

		}catch(Exception e){

			e.printStackTrace();

		}finally{
			isLoad = false;
		}
	}

	public void saveFile(String filePath){

		filePath = filePath + ".apj";
		dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder;
		try{
			dBuilder = dbFactory.newDocumentBuilder();
			doc = dBuilder.newDocument();





		}catch(Exception e){
			e.printStackTrace();
		}


		try{
			verRootElement = doc.createElement(VER_ELEMENT);
			verRootElement.setAttribute("VER", "1.0");
			doc.appendChild(verRootElement);

			writeProject();
			writeModels();


			StreamResult console = new StreamResult(System.out);
			StreamResult file = new StreamResult(new File(filePath));

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

	public void loadData(){

	}
	public boolean isLoad() {
		return isLoad;
	}
	public void setLoad(boolean isLoad) {
		this.isLoad = isLoad;
	}
}


