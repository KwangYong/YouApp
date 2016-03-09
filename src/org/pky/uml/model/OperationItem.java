package org.pky.uml.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.pky.uml.commons.managers.LayoutManager;
import org.pky.uml.commons.managers.ProjectManager;
import org.pky.uml.model.common.ArrayItem;

public class OperationItem extends ArrayItem{
	public String  modelID="";
	public String  parentID = "";
	public String  label = "";
	public String  id;
	public String  android_name;
	public Integer android_scope;
	public String  android_initValue;
	public Integer android_type;
	public String  android_stype = "void";
	public String  android_desc="";
	
	public String  ios_name;
	public Integer ios_scope;
	public String  ios_initValue;
	public Integer ios_type;
	public String  ios_stype = "void";
	public String  ios_desc="";
	
	
	public boolean isList = true;//Action리스트 등록여부 
	public int paramsCount=0;
	

	public String stereo="";

	private ArrayList<HashMap> actionDetailList = new ArrayList();
	private java.util.ArrayList android_Params = new java.util.ArrayList();
	private java.util.ArrayList ios_Params = new java.util.ArrayList();
	
	public OperationItem(String id,String iosValue){
		super();
		setIOSString(iosValue);
		
		this.id = id;
		this.isList = false;



	}
	public OperationItem(String id,String label,InterfaceItem parent,String androidValue,String iosValue,boolean isList){
		super();
		setAndroidString(androidValue);
		setIOSString(iosValue);
		this.parentID = parent.getId();
		this.label = label;

		this.id = id;
		this.isList = isList;
		parent.addItem(this);


	}

	public OperationItem(String id,String label,InterfaceItem parent,String androidValue,String iosValue){
		super();
		setAndroidString(androidValue);
	
		setIOSString(iosValue);
		this.parentID = parent.getId();
		this.label = label;

		this.id = id;

		parent.addItem(this);


	}
	public OperationItem(Integer s, String n, String v, String i) {
		android_scope = s;
		android_name = n;
		android_stype = v;
		android_initValue = i;
	}


	public void setName(String value){
		setAndroidString(value);
		setIOSString(value);
	}


	public void setIOSString(String value){


		if(value!=null){
			Matcher matcher;

			Pattern p = Pattern.compile("[+\\-\\#|<<|>>|(|,|)|:]");
			matcher = p.matcher(value);

			boolean isParamEnd = false;
			boolean isBegin = false;
			boolean isFind = false;
			boolean isParamStart = false;
			boolean isSteroType =false;
			int steroEndIndex=0;
			int index = 0;
			int paramindex=0;
			int beforeIndex = 0;
			int steroStart=0;
			int steroEnd=0;
			ios_Params.clear();

			while(matcher.find()) {
				isFind = true;
				System.out.print(matcher.group());
				if(matcher.group().equals("+")){
					ios_scope = 0;

					index = matcher.end();

				}
				else if(matcher.group().equals("-")){
					ios_scope = 2;

					index = matcher.end();
				}
				else if(matcher.group().equals("#")){
					ios_scope = 1;

					index = matcher.end();
				}

				if(matcher.group().equals("<")&&!isSteroType){
					steroStart = matcher.end();
				}
				if(matcher.group().equals(">")&&steroEndIndex<2){
					steroEnd = matcher.end();
					index = matcher.end();
					steroEndIndex++;
					isSteroType=true;

				}	


				if(matcher.group().equals("(")){


					ios_name = value.substring(index, matcher.start());
					index = matcher.end();  		        	 
					isParamStart = true;
					paramindex=index;


				}
				else if(matcher.group().equals(",")){
					if(isParamStart){
						paramsCount=paramsCount+1;

						String param = value.substring(paramindex,matcher.end()-1);
						if(!param.equals("")){
							String[] data = param.split(":");
							ParameterItem p1 = new ParameterItem(data[0],data[1],"");
							this.ios_Params.add(p1);
						}

						index = matcher.end();
						paramindex=index;
					}

				}
				else if(matcher.group().equals(":")){

					index = matcher.end();
					if(isParamEnd){
						ios_stype = value.substring(index);
						
					}

				}
				else if(matcher.group().equals(")")){
					index = matcher.end();
					paramsCount=paramsCount+1;
					String param = value.substring(paramindex,matcher.end()-1);

					if(!param.equals("")){
						String[] data = param.split(":");
						ParameterItem p1 = new ParameterItem(data[0],data[1],"");
						this.ios_Params.add(p1);

					}
					isParamEnd = true;


					isParamStart=false;
				}



			}

			if(steroStart>0&&steroEnd>0){
				stereo= value.substring(steroStart,steroEnd-2);
			}else{
				stereo="";
			}

			if(!isFind){
				ios_name = value;

				if(ios_name.trim().equals("")){
					ios_name="operation";
				}

				ios_scope = 0;
				ios_stype="void";

			}
		}
		

	
	}
	public void setAndroidString(String value){

		if(value!=null){
			Matcher matcher;

			Pattern p = Pattern.compile("[+\\-\\#|<<|>>|(|,|)|:]");
			matcher = p.matcher(value);

			boolean isParamEnd = false;
			boolean isBegin = false;
			boolean isFind = false;
			boolean isParamStart = false;
			boolean isSteroType =false;
			int steroEndIndex=0;
			int index = 0;
			int paramindex=0;
			int beforeIndex = 0;
			int steroStart=0;
			int steroEnd=0;
			android_Params.clear();

			while(matcher.find()) {
				isFind = true;
				System.out.print(matcher.group());
				if(matcher.group().equals("+")){
					android_scope = 0;

					index = matcher.end();

				}
				else if(matcher.group().equals("-")){
					android_scope = 2;

					index = matcher.end();
				}
				else if(matcher.group().equals("#")){
					android_scope = 1;

					index = matcher.end();
				}

				if(matcher.group().equals("<")&&!isSteroType){
					steroStart = matcher.end();
				}
				if(matcher.group().equals(">")&&steroEndIndex<2){
					steroEnd = matcher.end();
					index = matcher.end();
					steroEndIndex++;
					isSteroType=true;

				}	


				if(matcher.group().equals("(")){


					android_name = value.substring(index, matcher.start());
					index = matcher.end();  		        	 
					isParamStart = true;
					paramindex=index;


				}
				else if(matcher.group().equals(",")){
					if(isParamStart){
						paramsCount=paramsCount+1;

						String param = value.substring(paramindex,matcher.end()-1);
						if(!param.equals("")){
							String[] data = param.split(":");
							ParameterItem p1 = new ParameterItem(data[0],data[1],"");
							this.android_Params.add(p1);
						}

						index = matcher.end();
						paramindex=index;
					}

				}
				else if(matcher.group().equals(":")){

					index = matcher.end();
					if(isParamEnd){
						android_stype = value.substring(index);
						
					}

				}
				else if(matcher.group().equals(")")){
					index = matcher.end();
					paramsCount=paramsCount+1;
					String param = value.substring(paramindex,matcher.end()-1);

					if(!param.equals("")){
						String[] data = param.split(":");
						ParameterItem p1 = new ParameterItem(data[0],data[1],"");
						this.android_Params.add(p1);

					}
					isParamEnd = true;


					isParamStart=false;
				}



			}

			if(steroStart>0&&steroEnd>0){
				stereo= value.substring(steroStart,steroEnd-2);
			}else{
				stereo="";
			}

			if(!isFind){
				android_name = value;

				if(android_name.trim().equals("")){
					android_name="operation";
				}

				android_scope = 0;
				android_stype="void";

			}
		}
		

	}


	public String toString() {
	
		StringBuffer pBuffer = new StringBuffer("");
		String value = "";

		for (int i = 0; i < android_Params.size(); i++) {
			ParameterItem p = (ParameterItem)android_Params.get(i);
			pBuffer.append(p.toString());
			if (i != android_Params.size() - 1) {
				pBuffer.append(",");
			}
		}
		if(!stereo.trim().equals(""))
			value =  ProjectManager.SCOPEA_SET[android_scope] +" " + "<<"+stereo.trim()+">>"+ " "+android_name.trim() + "(" + pBuffer.toString() + "):" +
					android_stype;
		else{
			value =  ProjectManager.SCOPEA_SET[android_scope] +" "+ android_name.trim() + "(" + pBuffer.toString() + "):" +
					android_stype;	
		}

		return value;
	}

	
	
	public ArrayList<HashMap> getActionDetailList() {
		return actionDetailList;
	}
	//오퍼레이션에 기제될 내용들이 들어간다.
	public void setActionDetailList(ArrayList<HashMap> actionDetailList) {
		this.actionDetailList = actionDetailList;
	}
	//IOS 상세 오퍼레이션 내용을 작성한다.
	public String writeSourceIOS(){
		LayoutManager.getInstance().setType(LayoutManager.IOS);
		String str = LayoutManager.getInstance().writeOperation(this);
		return str;
	}
	//안드로이드 상세 오퍼레이션 내용을 작성한다.
	public String writeSourceAndroid(){
		LayoutManager.getInstance().setType(LayoutManager.ANDROID);
		String str = LayoutManager.getInstance().writeOperation(this);
		return str;
	}
	public String getLabel() {
		return label;
	}
	
	public java.util.ArrayList getAndroid_Params() {
		return android_Params;
	}
	public java.util.ArrayList getIos_Params() {
		return ios_Params;
	}
	public void setAndroid_Params(java.util.ArrayList android_Params) {
		this.android_Params = android_Params;
	}
	public void setIos_Params(java.util.ArrayList ios_Params) {
		this.ios_Params = ios_Params;
	}
	

}
