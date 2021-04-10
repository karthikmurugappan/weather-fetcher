import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class WeatherFetcher 
{
	
	public static String getResponse(String city)
	{
		try {
			String res = startWebRequest(city);
			return res;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} 
	}
	
	private static String startWebRequest(String city) throws IOException
	{
		String weatherURL = "http://api.openweathermap.org/data/2.5/weather?q=" + city + "&units=Imperial&APPID=26aa1d90a24c98fad4beaac70ddbf274"; 			//please get your own token to use from API.Openweathermap
		
		  StringBuilder result = new StringBuilder(); //this is going to hold the JSON Response from the server
	      URL url = new URL(weatherURL);
	      HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	      conn.setRequestMethod("GET");
	      BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
	      String line;
	      while ((line = rd.readLine()) != null) {
	         result.append(line);
	      }
	      rd.close();
	      System.out.println(result.toString()); 
	      String res = result.toString();
	     
	     double temp =  getTemperature(res.toString());
	     double tempmin = getLowTemp(res.toString());
	     double tempmax = getHighTemp(res.toString());
	     String forecast = getWeather(res.toString());
	     
	     System.out.println("The weather's going to be " + forecast);
	     System.out.println("The temperature right now is "+ temp + " degrees Fahrenheit");
	     System.out.println("The low temperature is " + tempmin +" degrees Fahrenheit");
	     System.out.println("The high temperature is " + tempmax +" degrees Fahrenheit");
	     
	     return res;
	   }
	
	private static double parseJson(String json) //json is a string of json, we get this from making our request
	{
		JsonElement jelement = new JsonParser().parse(json); //you will parse it first into a JSONElement
        JsonObject  MasterWeatherObject = jelement.getAsJsonObject();  //You will then take that jelement, and then break it down into a json object. Use the JSONEDITORONLINE website, basically, you are trying narrow down to whatever you want
        
        JsonObject  coordinateObject = MasterWeatherObject.getAsJsonObject("coord"); //we will get the coordinate object 
        double  longitude = coordinateObject.get("lon").getAsDouble(); //now we will narrow down to get the value of the longitute
        return longitude;  //return our longitude
	}
	
	public static double getTemperature(String json) {
		JsonElement jel = new JsonParser().parse(json);
		JsonObject MasterWeatherObject = jel.getAsJsonObject();
		
		JsonObject weatherObject = MasterWeatherObject.getAsJsonObject("main");
		double temp = weatherObject.get("temp").getAsDouble();
		return temp;
	}
	
	public static double getHighTemp(String json) {
		JsonElement jel = new JsonParser().parse(json);
		JsonObject MasterWeatherObject = jel.getAsJsonObject();
		
		JsonObject weatherObject = MasterWeatherObject.getAsJsonObject("main");
		double tempmax = weatherObject.get("temp_max").getAsDouble();
		return tempmax;
	}
	
	public static double getLowTemp(String json) {
		JsonElement jel = new JsonParser().parse(json);
		JsonObject MasterWeatherObject = jel.getAsJsonObject();
		
		JsonObject weatherObject = MasterWeatherObject.getAsJsonObject("main");
		double tempmin = weatherObject.get("temp_min").getAsDouble();
		return tempmin;
	}
	
	public static String getWeather(String json) {
		JsonElement jel = new JsonParser().parse(json);
		JsonObject MasterWeatherObject = jel.getAsJsonObject();
		
		JsonArray weatherArray = MasterWeatherObject.getAsJsonArray("weather");
		JsonElement element = weatherArray.get(0);
		JsonObject elementAsObject = element.getAsJsonObject();
		String weather = elementAsObject.get("description").getAsString();
		return weather;
	}
		
}