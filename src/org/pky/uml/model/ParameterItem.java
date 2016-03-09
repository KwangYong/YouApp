package org.pky.uml.model;

import org.pky.uml.model.common.ArrayItem;


public class ParameterItem extends ArrayItem{
	public String name;
	public String defalut;
	public Integer type;
	public String stype;
	public String id = null;
	public String desc="";

	public ParameterItem(String n, Integer v, String i) {
		name = n;
		type = v;
		defalut = i;
	}

	public ParameterItem(String n, String v, String i) {
		name = n;
		stype = v;
		defalut = i;
	}

	public ParameterItem() {

	}

	public String toString() {
		//	String scopep = AttributeDialog.SCOPE_SET[scope];
		String value = name + ":" + this.stype;
		return value;
	}

	public ParameterItem clone() {
		ParameterItem clone = new ParameterItem(this.name, this.stype, this.defalut);
		//2008042401PKY S
		clone.desc=this.defalut;
		clone.defalut=this.defalut;
		//PKY 08092607 S
		if(name!=null)
			clone.name=this.name;
		if(defalut!=null)
			clone.defalut=this.defalut;
		if(type!=null)
			clone.type=this.type;
		if(stype!=null)
			clone.stype=this.stype;
		if(id!=null)
			clone.id=this.id;
		//PKY 08092607 E


		//2008042401PKY E
		return clone;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String writeModel(){
		StringBuffer sb = new StringBuffer();
		sb.append(name+":"+this.stype);
		return sb.toString();

	}
	public String getLabel(){
		return "";
	}
	public String writeSourceAndroid(){
		return "";
	}

}
