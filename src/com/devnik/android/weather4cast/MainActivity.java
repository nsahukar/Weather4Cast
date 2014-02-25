package com.devnik.android.weather4cast;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.devnik.android.weather4cast.network.NetworkCommunicator;

public class MainActivity extends Activity {
	
	private static final String NO_NETWORK = "nonetwork";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		final Button showWeather4CastButton = (Button) findViewById(R.id.show_weather_forecast_button);
		showWeather4CastButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				showWeatherForecast();
			}
		});
	}
	
	private void showWeatherForecast() {
		Log.d("MainActivity", "call web service!!!");
		final String city = ((TextView) findViewById(R.id.city_text)).getText().toString().replace(" ", "%20");
		final String country = ((TextView) findViewById(R.id.country_text)).getText().toString().replace(" ", "%20");
		
		new AsyncTask<Void, Void, String> () {

			@Override
			protected String doInBackground(Void... params) {
				if(isNetworkAvailable()) {
					final String jsonResponse = NetworkCommunicator.getWeather4Cast(city, country);
					return jsonResponse;
				} else {
					return NO_NETWORK;
				}
			}
			
			protected void onPostExecute(String jsonResponse) {
				showWeatherActivity(jsonResponse);
			};
			
		}.execute();
	}
	
	private void showWeatherActivity(final String jsonResponse) {
		if(jsonResponse.equals(NO_NETWORK)) {
			Toast.makeText(MainActivity.this, getResources().getString(R.string.no_network_msg), Toast.LENGTH_LONG).show();
		} else {
			final Intent weatherIntent = new Intent(MainActivity.this, WeatherActivity.class);
			weatherIntent.putExtra("weather4castjson", jsonResponse);
			startActivity(weatherIntent);
		}
	}
	
	private boolean isNetworkAvailable() {
	    ConnectivityManager connectivityManager 
	          = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
	    NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
	    return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
	}

}
