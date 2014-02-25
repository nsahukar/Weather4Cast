package com.devnik.android.weather4cast;

import com.devnik.android.weather4cast.data.Weather;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class WeatherActivity extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_weather);
		
		final String weather4CastJSON = getIntent().getStringExtra("weather4castjson");
		Weather forecast = Weather.createInstance(weather4CastJSON);
		
		showDetails(forecast);
	}
	
	private void showDetails(Weather forecast) {
		if(forecast != null) {
			String city = "";
			if(forecast.getCity().equals("")) {
				city += getResources().getString(R.string.forgot_to_enter_city);
			} else {
				city += forecast.getCity();
			}
			((TextView) findViewById(R.id.one_word_desc)).setText(getResources().getString(R.string.one_word_desc_prefix) + " " + forecast.getOneWordDescription() + " in " + city);
			((TextView) findViewById(R.id.complete_desc)).setText(forecast.getDescription());
			((TextView) findViewById(R.id.min_temperature)).setText("min temperature: " + forecast.getMinTemperature());
			((TextView) findViewById(R.id.max_temperature)).setText("max temperature: " + forecast.getMaxTemparature());
			((TextView) findViewById(R.id.humidity)).setText("humidity: " + forecast.getHumidity());
			((TextView) findViewById(R.id.windspeed)).setText("wind speed: " + forecast.getWindSpeed());
			((TextView) findViewById(R.id.enjoy)).setText(getResources().getString(R.string.enjoy));
		} else {
			((TextView) findViewById(R.id.one_word_desc)).setText(getResources().getString(R.string.no_such_city));
		}
	}

}
