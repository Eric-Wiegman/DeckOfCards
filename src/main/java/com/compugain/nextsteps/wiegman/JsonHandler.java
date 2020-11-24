/**
 * 
 */
package com.compugain.nextsteps.wiegman;

import org.json.JSONObject;

/**
 * @author Dad
 *
 */
public class JsonHandler {

	public static JSONObject convertResponseToJsonObject (String response) {
		if (response.substring(0,1).equals("[")) {
			response = response.substring(1);
		}
		return new JSONObject(response);
	}
}
