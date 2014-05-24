package nice.quotables;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import android.R;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.TextView;


public class Quote extends AsyncTask<List<String>, Integer, List<String>>  {
	
	Activity activity;
	String direction = "";
	String id = "";
	ProgressDialog dialog;
	
	
	public Quote(Activity _activity, String _direction, String _id, ProgressDialog _dialog){
		activity = _activity;
		direction = _direction;
		id = _id;
		dialog = _dialog;
	}
	
	@Override
    protected void onPostExecute(List<String> result) {
		QuoteActivity qa = new QuoteActivity();
        Intent myIntent=new Intent(activity, QuoteActivity.class);
        myIntent.putExtra("content", result.get(0));
        myIntent.putExtra("author", result.get(1));
        myIntent.putExtra("id", result.get(2));
        activity.startActivity(myIntent);
        if(dialog != null){
        	dialog.dismiss();
        }
        activity.finish();
    }
	
	@Override
	protected List<String> doInBackground(List<String>... params) {
		
	    BufferedReader in = null;
	    List<String> returnString = new ArrayList<String>();
	    LocalQuote lq = new LocalQuote(activity.getBaseContext());
	    try{
	           HttpClient httpclient = new DefaultHttpClient();

	           HttpGet request = new HttpGet();
	           //URI website = new URI("http://quotablesonline.com/quotes.json");
	           URI website = new URI("http://192.168.0.11:3000/quotes.json?direction="+direction+"&id="+id);
	           request.setURI(website);
	           HttpResponse response = httpclient.execute(request);
	           in = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

	           String line = in.readLine();
	           
	           JSONObject jObject = new JSONObject(line);
	           
	           returnString.add(jObject.getString("content")+"\n\n");
	           returnString.add(jObject.getString("author"));
	           returnString.add(jObject.getString("id"));
	           
	           lq.createQuote(jObject.getString("id"), jObject.getString("content"), jObject.getString("author"));
	           
	           return returnString;
	       }catch(Exception e){
	    	   Log.i("123", "????????????????????"+e+"::"+e.getStackTrace());
	    	   
	    	   List<String> backups = lq.getQuote();
	           return backups;
	       }
	    
	    
		
		
	}

}
