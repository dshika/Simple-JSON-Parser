package com.test.jsonparser;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;

public class JsonParserUtility {
	public static JSONObject createJSONObject(String key, String val, JSONObject jsonObj) {
		if(!StringUtils.contains(key, ".")){
			jsonObj.put(key, getJsonData(val));
		}
		else{
			String key1 = key.substring(0, key.indexOf("."));
			String key2 = key.substring(key.indexOf(".") + 1);
			if(!jsonObj.has(key1)){
				jsonObj.put(key1, createJSONObject(key2, val, new JSONObject()));
			}
			else{
				jsonObj.put(key1, createJSONObject(key2, val, jsonObj.getJSONObject(key1)));
			}
		}
		return jsonObj;
	}
	
	public static Object getJsonData(String val){
		if(val.startsWith("[") && val.endsWith("]")){
			JSONArray jsonArray = new JSONArray(val);
			return jsonArray;
		}
		return val;
	}
	
	public static JSONObject formatInputParam(String input) {
		JSONObject jsonObj = new JSONObject();
		if (StringUtils.isNotBlank(input)) {
			String[] values = input.split("[, ]{2,2}");
			for (String string : values) {
				String[] keyVals = StringUtils.split(string,"=");
				String key = StringUtils.trim(keyVals[0]);
				String val = StringUtils.trim((keyVals.length>1)? keyVals[1] : "");
				
				jsonObj = createJSONObject(key, val, jsonObj);
			}
			
			/*String[] values = StringUtils.split(input, ",");
			for (String string : values) {
				String[] keyValue = StringUtils.split(string, "=");
				jsonObj.put(StringUtils.trim(keyValue[0]), StringUtils.trim((keyValue.length>1)? keyValue[1] : ""));
			}*/
		}

		return jsonObj;
	}
	
	public static JSONObject formatExpectedOutput(String output){
		JSONObject jsonObj = new JSONObject();
		if (StringUtils.isNotBlank(output)) {
			String[] values = StringUtils.split(output, ",");
			for (String string : values) {
				String[] keyValue = StringUtils.split(string, "=");
				jsonObj.put(StringUtils.trim(keyValue[0]), StringUtils.trim((keyValue.length>1)? keyValue[1] : ""));
			}
		}
		
		return jsonObj;
	}

	public static Object getValueForKey(String key, JSONObject jsonObj) {

		if (!StringUtils.contains(key, ".")) {
			return jsonObj.get(key);
		} else {
			String key1 = key.substring(0, key.indexOf("."));
			String key2 = key.substring(key.indexOf(".") + 1);
			return getValueForKey(key2, jsonObj.getJSONObject(key1));
		}
	}
	
	public static Boolean validateExpectedResult(JSONObject responseData, String expectedOutput){
		Boolean result = null;
		Map<String, Object> expOutputMap = formatExpectedOutput(expectedOutput).toMap();
		for (Entry<String, Object> keyValue : expOutputMap.entrySet()) {
			String expectedVal = (String) keyValue.getValue();
			String actualVal = String.valueOf(getValueForKey(keyValue.getKey(), responseData));
			result = StringUtils.equals(expectedVal, actualVal);
			if(!result) break;
		}
		return result;
	}

}
