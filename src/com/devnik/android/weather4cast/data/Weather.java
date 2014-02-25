package com.devnik.android.weather4cast.data;

import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class Weather {

	private String city;
	private String oneWordDescription;
	private String description;
	private double minTemperature;
	private double maxTemparature;
	private double humidity;
	private double windSpeed;
	
	private Weather() {
		
	}
	
	public String getCity() {
		return city;
	}
	
	public void setCity(String city) {
		this.city = city;
	}

	public String getOneWordDescription() {
		return oneWordDescription;
	}

	public void setOneWordDescription(String oneWordDescription) {
		this.oneWordDescription = oneWordDescription;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public double getMinTemperature() {
		return minTemperature;
	}

	public void setMinTemperature(double minTemperature) {
		this.minTemperature = minTemperature;
	}

	public double getMaxTemparature() {
		return maxTemparature;
	}

	public void setMaxTemparature(double maxTemparature) {
		this.maxTemparature = maxTemparature;
	}

	public double getHumidity() {
		return humidity;
	}

	public void setHumidity(double humidity) {
		this.humidity = humidity;
	}

	public double getWindSpeed() {
		return windSpeed;
	}

	public void setWindSpeed(double windSpeed) {
		this.windSpeed = windSpeed;
	}
	
	public static Weather createInstance(final String weather4castJSON) {
		JSONObject weather4CastJSONObject = null;
		// try parse the string to a JSON object
        try {
        	weather4CastJSONObject = new JSONObject(weather4castJSON);
            
            Weather weather = new Weather();
            weather.setCity(weather4CastJSONObject.getString("name"));
            
            final JSONObject weatherJSONObject = weather4CastJSONObject.getJSONArray("weather").getJSONObject(0);
            weather.setOneWordDescription(weatherJSONObject.getString("main"));
            weather.setDescription(weatherJSONObject.getString("description"));

            final JSONObject temperatureJSONObject = weather4CastJSONObject.getJSONObject("main");
            weather.setMinTemperature(temperatureJSONObject.getDouble("temp_min"));
            weather.setMaxTemparature(temperatureJSONObject.getDouble("temp_max"));
            weather.setHumidity(temperatureJSONObject.getDouble("humidity"));
            
            final JSONObject windJSONObject = weather4CastJSONObject.getJSONObject("wind");
            weather.setWindSpeed(windJSONObject.getDouble("speed"));
            
            return weather;
        } catch (JSONException e) {
            Log.e("JSON Parser", "Error parsing json " + e.toString());
        }
	
		
		return null;
	}

}
