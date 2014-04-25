package com.hugo.obsplatform;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;

public class XmlToJsonConvertion {

	public String convertion(String output) {
		try {
			JSONObject xmlJSONObj = XML.toJSONObject(output);
			//System.out.println("before command:"+xmlJSONObj);
			JSONObject element = xmlJSONObj.getJSONObject("COMMAND");
			//System.out.println("after command:"+element);
			String RESULT=element.getString("RESULT");
			Long ERRORCODE=element.getLong("ERRORCODE");
			String ERRORDESC=element.getString("ERRORDESC");
			String CONTENT=element.getString("CONTENT");
			String TXNID=element.getString("TXNID");
			//String FLAG=element.getString("FLAG");
			String MSISDN=element.getString("MSISDN");
			String REFID=element.getString("REFID");
			String TYPE=element.getString("TYPE");
			String Success=null;
			String ErrorCode=null;
			String ErrorDesc=null;
			if(RESULT.equalsIgnoreCase("SUCCESS")){
				//element.remove("RESULT");
				//element.put("RESULT" , "TS");
				Success= "TS";
			}else if(RESULT.equalsIgnoreCase("DUPLICATE_TXN")){
				//element.remove("RESULT");
				//element.put("RESULT" , "TF");
				Success= "TF";
			}
			
			if(ERRORCODE==0){
				/*element.remove("ERRORCODE");
				element.put("ERRORCODE" , "error000");	*/
				ErrorCode= "error000";
			}else if(ERRORCODE==1){
				/*element.remove("ERRORCODE");
				element.put("ERRORCODE" , "error100");	*/
				ErrorCode= "error100";
			}
			
			if(ERRORDESC.equalsIgnoreCase("")){
				/*element.remove("ERRORDESC");
				element.put("ERRORDESC" , "Successful transaction");*/
				ErrorDesc="Successful transaction";
			}else if(ERRORDESC.equalsIgnoreCase("DUPLICATE")){
			/*	element.remove("ERRORDESC");
				element.put("ERRORDESC" , "General error. Refer Error description.");*/
				ErrorDesc="General error. Refer Error description.";
			}
			String CONTENT1=null;
			if(CONTENT.equalsIgnoreCase("OBSTRANSACTIONID=null")){
				CONTENT1="";
				Success= "TS";
				ErrorDesc="Successful transaction";
				ErrorCode= "error000";
			}else if(CONTENT.equalsIgnoreCase("TXNID ALREADY EXIST")){
				CONTENT1=CONTENT;
			}else{
				String data=CONTENT.replaceAll("OBSTRANSACTIONID=", "payment id is : ");
				CONTENT1="The payment is successfull and "+data;
			}
			
			/*String xml = XML.toString(element);
			 StringBuilder builder = new StringBuilder();
	            builder.append("<?xml version=\"1.0\"?>")
	                .append("<!DOCTYPE COMMAND PUBLIC \"-//Ocam//DTD XML Command 1.0//EN\" \"xml/command.dtd\">")
	                .append("<COMMAND>")
	                .append("<TYPE>")
	                .append(TYPE)
	                .append("</TYPE>")
	                .append(xml)
	                .append("</COMMAND>");
			return builder.toString();*/
			
			 StringBuilder builder = new StringBuilder();
	            builder.append("<?xml version=\"1.0\"?>")
	                .append("<!DOCTYPE COMMAND PUBLIC \"-//Ocam//DTD XML Command 1.0//EN\" \"xml/command.dtd\">")
	                .append("<COMMAND>")
	                .append("<TYPE>"+"SYNC_BILLPAY_RESPONSE")
	                .append("</TYPE>")
	                .append("<TXNID>"+TXNID)
	                .append("</TXNID>")
	                .append("<REFID>"+REFID)
	                .append("</REFID>")
	                .append("<RESULT>"+Success)
	                .append("</RESULT>")
	                .append("<ERRORCODE>"+ErrorCode)
	                .append("</ERRORCODE>")
	                .append("<ERRORDESC>"+ErrorDesc)
	                .append("</ERRORDESC>")
	                .append("<MSISDN>"+MSISDN)
	                .append("</MSISDN>")
	                .append("<FLAG>"+"Y")
	                .append("</FLAG>")
	                .append("<CONTENT>"+CONTENT1)
	                .append("</CONTENT>")
	                .append("</COMMAND>");
	            
	            return builder.toString();			 
		
		} catch (JSONException e) {
			return e.toString();
		}
		
		
		
	}
	
	/*public String Validation(String data){
		try {
		JSONObject xmlJSONObj = XML.toJSONObject(data);
		//System.out.println("before command:"+xmlJSONObj);
		JSONObject element = xmlJSONObj.getJSONObject("COMMAND");
		//System.out.println("after command:"+element);
		String STATUS=element.getString("STATUS");
		String AMOUNT=element.getString("AMOUNT");
		String TXNID=element.getString("TXNID");
		String MSISDN=element.getString("MSISDN");
		String CUSTOMERREFERENCEID = element.getString("CUSTOMERREFERENCEID");
		String TYPE = element.getString("TYPE");
		
		if(STATUS == null && STATUS ==""){
			return "<STATUS/> Should Not Empty ";
		}
		if(AMOUNT == null && AMOUNT == ""){
			return "<AMOUNT/> Should Not Empty ";
		}
		if(TXNID == null && TXNID ==""){
			return "<TXNID/> Should Not Empty ";
		}
		if(MSISDN == null && MSISDN ==""){
			return "<MSISDN/> Should Not Empty ";
		}
		if(CUSTOMERREFERENCEID == null && CUSTOMERREFERENCEID ==""){
			return "<CUSTOMERREFERENCEID/> Should Not Empty ";
		}
		if(TYPE == null && TYPE ==""){
			return "<TYPE/> Should Not Empty ";
		}	
		
		return "success";
		} catch (JSONException e) {
			return e.toString();
		}
	}*/
}
