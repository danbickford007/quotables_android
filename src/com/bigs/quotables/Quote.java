package com.bigs.quotables;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URL;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.R;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.TextView;


public class Quote extends AsyncTask<String, Integer, String>  {
	
	Activity activity;
	
	public Quote(Activity _activity){
		activity = _activity;
		
	}
	
	@Override
    protected void onPostExecute(String result) {
		QuoteActivity qa = new QuoteActivity();
        Intent myIntent=new Intent(activity, QuoteActivity.class);
        myIntent.putExtra("quote", result);
        activity.startActivity(myIntent);
    }
	
	@Override
	protected String doInBackground(String... params) {
		
	    BufferedReader in = null;

	    try{
	           HttpClient httpclient = new DefaultHttpClient();

	           HttpGet request = new HttpGet();
	           URI website = new URI("http://192.168.0.11:3000/quotes.json");
	           request.setURI(website);
	           HttpResponse response = httpclient.execute(request);
	           in = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

	           String line = in.readLine();
	           
	           
	           
	           return line;
	       }catch(Exception e){
	    	   Log.i("123", "????????????????????"+e);
	           return "FAILED";
	       }
	    
	    
		
		
	}

}
