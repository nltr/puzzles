/**
 * 
 */
package in.inishant.puzzles.arcesium.pricipal;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * @author Nishant
 *
 */
public class Solution {

	/**
	 * 
	 */
	public Solution() {
	}

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		System.out.println(calculateHoldingValue("20190509"));
		
		
	}
	
	
	 /*
     * Complete the 'calculateHoldingValue' function below.
     *
     * The function is expected to return a DOUBLE.
     * The function accepts STRING date (in yyyymmdd format) as parameter.
     */

    public static double calculateHoldingValue(String date) {
    	if(date==null || date.length() !=8) {
    		return 0;
    	}
    	 //get from system variable or proeprties file or db as required
        String holdingApiWithPage="https://api.myjson.com/bins/1eleys";
        //TODO: use paging 
//        String holdingApiWithPage="https://api.myjson.com/bins/10ysxg";

        //get from system variable or proeprties file or db as required
        String pricingApiWithPage="https://api.myjson.com/bins/vf9ac";
       Map<String,Integer> holdingMap=new HashMap<>();
       Map<String,Double> priceMap=new HashMap<>();
        
        //TODO: can use executor here both calls
        try {
            String holdingApiRes = sendGet(holdingApiWithPage);
			System.out.println(holdingApiRes);
        	 JsonParser jsonParser = new JsonParser();
        	 JsonElement el =jsonParser.parse(holdingApiRes);
    	       	if(!el.isJsonNull() && el.isJsonObject()) {
    	       		JsonObject jsonObject = el.getAsJsonObject();
//    	       		JsonElement nextPage = jsonObject.get("nextPage");
    	       		JsonElement data = jsonObject.get("data");
    	       		if(data.isJsonArray()) {
    	       			JsonArray dataArray = data.getAsJsonArray();
    	       			for (JsonElement pa : dataArray) {
    	       				JsonObject dataObj = pa.getAsJsonObject();
    	       				//TODO: add null checks here for fields
    	       				if(dataObj.get("date").getAsString().equals(date) ){
    	       					holdingMap.put(dataObj.get("security").getAsString(), dataObj.get("quantity").getAsInt());
    	       				}
    	       			}
    	       		}
    	       	}
        } catch (Exception e) {
            // use logger here 
            e.printStackTrace();
        }
        try {
            String pricingApiRes = sendGet(pricingApiWithPage);
			System.out.println(pricingApiRes);
			JsonParser jsonParser = new JsonParser();
	       	JsonElement el = jsonParser.parse(pricingApiRes);
	       	if(!el.isJsonNull() && el.isJsonObject()) {
	       		populatePriceMap(date, priceMap, el);
//	       		while(nextPage!=null &&
	       	}
       	 
        } catch (Exception e) {
            // use logger here
            e.printStackTrace();
        }
       
        
       double sum=0;
        for (Map.Entry<String, Integer> entry : holdingMap.entrySet()) {
        	if(priceMap.get(entry.getKey())!=null){
        		
        		sum+= entry.getValue()*priceMap.get(entry.getKey());
        	}
        }
       
        return sum;
		
    }

	/**
	 * @param date
	 * @param priceMap
	 * @param el
	 */
	private static void populatePriceMap(String date, Map<String, Double> priceMap, JsonElement el) {
		JsonObject jsonObject = el.getAsJsonObject();
//	       		JsonElement nextPage = jsonObject.get("nextPage");
		JsonElement data = jsonObject.get("data");
		if(data.isJsonArray()) {
			JsonArray dataArray = data.getAsJsonArray();
			for (JsonElement pa : dataArray) {
				JsonObject dataObj = pa.getAsJsonObject();
				//TODO: add null checks here for fields
				if(dataObj.get("date").getAsString().equals(date) ){
					priceMap.put(dataObj.get("security").getAsString(), dataObj.get("price").getAsDouble());
				}
			}
		}
		if(priceMap.size()==0) {
			date = new Integer(Integer.parseInt(date)-1).toString();
			populatePriceMap(date, priceMap, el);
		}
	}
    
    
    

	
	
	// HTTP GET request
		protected static String sendGet(String url) throws Exception {

			
			URL obj = new URL(url);
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();

			// optional default is GET
			con.setRequestMethod("GET");

			//add request header
			con.setRequestProperty("User-Agent", "Mozilla/5.0");

			int responseCode = con.getResponseCode();
			System.out.println("\nSending 'GET' request to URL : " + url);
			System.out.println("Response Code : " + responseCode);

			BufferedReader in = new BufferedReader(
			        new InputStreamReader(con.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();

			//print result
			return response.toString();

		}

}
