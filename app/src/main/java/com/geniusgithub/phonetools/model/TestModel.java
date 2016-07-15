package com.geniusgithub.phonetools.model;

import java.io.Serializable;

public class TestModel implements Serializable{

 //   private static final long serialVersionUID = 2451716801614350437L;
    
	public int type;
	public String value;
	
	public  TestModel(int type, String value){
		this.type = type;
		this.value = value;
	}

	
	public String toString(){
		StringBuffer stringBuffer  = new StringBuffer();
		stringBuffer.append("type = "  + type + "\nvalue = " + value);
		return stringBuffer.toString();
	}
}
