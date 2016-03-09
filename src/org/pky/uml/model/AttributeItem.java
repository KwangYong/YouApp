package org.pky.uml.model;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.pky.uml.commons.managers.ProjectManager;
import org.pky.uml.model.common.ArrayItem;

public class AttributeItem extends ArrayItem{

	
    public Integer scope;
    public String initValue;
    public Integer type;
    public String stype;
    public String id = null;
    public Integer isDerived = 0;
    public Integer isStatic = 0;
    public Integer isConst = 0;
    public boolean isinitValue  = false;
    public String desc="";
    public String name;


	
	public AttributeItem(){
		this("+ attribute:String()");
	}
	public AttributeItem(String value){
		super();
		setName(value);
		
	}
	public void setName(String value){
		setString(value);
	}
	public void setString(String value){
		if(value!=null){
			Matcher matcher;
			Pattern p = Pattern.compile("[/\\+\\-\\#\\=\\:]");
			matcher = p.matcher(value);
			Matcher overMapping = p.matcher(value);
			boolean isFind = false;
			boolean isFindDerived = false;
			boolean isFindScope= false;
			boolean isReadOnlyStart = false;
			boolean isinitValue= false;
			boolean isOverMapping=false;
			int index = 0;
			int beforeIndex = 0;

			int overMappingFindDerived = 0;
			int overMappingFindScope = 0;
			int overMappingFind = 0;
			int overMappinginitValue = 0;

			while(overMapping.find()) {
				if(overMapping.group().equals("/")){
					isFindDerived = true;
					overMappingFindDerived=overMappingFindDerived+1;
					if(overMappingFind>1){
						isOverMapping=true;
						return;
					}
				} else if(overMapping.group().equals("+")){
					isFindScope = true;
					overMappingFindScope=overMappingFindScope+1;
					if(overMappingFindScope>1){
						isOverMapping=true;
						isinitValue = true;
						return;
					}
				} else if(overMapping.group().equals("-")){
					isFindScope = true;
					overMappingFindScope=overMappingFindScope+1;
					if(overMappingFindScope>1){
						isOverMapping=true;
						isinitValue = true;
						return;
					}
				} else if(overMapping.group().equals("#")){
					isFindScope = true;
					overMappingFindScope=overMappingFindScope+1;
					if(overMappingFindScope>1){
						isOverMapping=true;
						isinitValue = true;
						return;
					}
				} else if(overMapping.group().equals(":")){
					isFind = true;
					overMappingFind=overMappingFind+1;
					if(overMappingFind>1){
						isOverMapping=true;
						isinitValue = true;
						return;
					}
				} else if(overMapping.group().equals("=")){
					isinitValue = true;
					overMappinginitValue=overMappinginitValue+1;
					if(overMappinginitValue>1){
						isOverMapping=true;
						isinitValue = true;
						return;
					}
				}
			}
			if(isOverMapping==false)
				while(matcher.find()) {
					if(matcher.group().equals("/")){
						isDerived = 1;
						index = matcher.end();
						isFindDerived = true;
					}
					else if(matcher.group().equals("+")){
						scope = 0;
						index = matcher.end();
						isFindScope = true;
					}
					else if(matcher.group().equals("-")){
						scope = 2;
						index = matcher.end();
						isFindScope = true;
					}
					else if(matcher.group().equals("#")){
						scope = 1;
						index = matcher.end();
						isFindScope = true;
					}
					else if(matcher.group().equals(":")){
						isFind = true;
						name = value.substring(index, matcher.start());
						index = matcher.end();
					}
					else if(matcher.group().equals("=")){
						isinitValue = true;
						this.stype = value.substring(index,matcher.start());
						String value1 = value.substring(matcher.end());
						if(value1.lastIndexOf("{")>0){
							//							
							String data = value1.substring(0,value1.lastIndexOf("{"));
							this.initValue = data;
							this.isConst = 1;
						}
						else{
							this.initValue = value1;
						}
						index = matcher.end();
					}
				} 

			if(!isinitValue){
				this.stype = value.substring(index);
			}
			if(!isFind){
				name = value.substring(index);

				if(name.trim().equals("")){
					name="attribute";
				}

				if(!isFindScope)
					scope = 0;
				if(!isFindDerived)
					this.isDerived =0;
				stype="String";
			}
		}
		//setName(name);

	}
	public String toString() {   
		try{
//			if(this.isDerived==null)
//				return toString();
			String isDerivedValue = "";
			String isConstValue = "";
			String isInitValue = "";
			if(this.isDerived==1){
				isDerivedValue = "/";
			}
			if(this.isConst==1){
				isConstValue = "{readOnly}";
			}
			if(this.initValue!=null && !this.initValue.trim().equals("")){
				isInitValue = "="+initValue;
			}
			
			if(stype.lastIndexOf("{readOnly}")>-1){
				stype=stype.substring(0, stype.lastIndexOf("{readOnly}"));
			}
			
			String value = ProjectManager.SCOPEA_SET[scope]+isDerivedValue + " " + name.trim() + ":" + this.stype+isInitValue+isConstValue;
			if(!value.trim().equals("")){
				return value;	
			}

		}catch(Exception e){
			e.printStackTrace();

		}
		return "";
	}
	public Integer getScope() {
		return scope;
	}
	public void setScope(Integer scope) {
		this.scope = scope;
	}
	
	public String getLabel(){
		return "";
	}
	public String writeSourceAndroid(){
		return "";
	}

}
