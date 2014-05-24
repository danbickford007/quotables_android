package com.bigs.quotables;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.view.MotionEventCompat;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Build;

public class QuoteActivity extends ActionBarActivity {

	private static final String DEBUG_TAG = "11";
	float x1,x2;
    float y1, y2;
    String quote_string;
    String id = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_quote);

		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
		
		
		Bundle extras = getIntent().getExtras();
		quote_string  = getIntent().getStringExtra("quote");
		if(quote_string.contains("ID:")){
			id = quote_string.split("ID:")[1];
			quote_string = quote_string.split("ID:")[0];
        }
		TextView tv = (TextView)findViewById(R.id.quoteText);
		tv.setText(quote_string);
		Typeface font = Typeface.createFromAsset(getAssets(), "Chantelli_Antiqua.ttf");
		tv.setTypeface(font);
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent touchevent) 
    {
                 switch (touchevent.getAction())
                 {
                        // when user first touches the screen we get x and y coordinate
                         case MotionEvent.ACTION_DOWN: 
                         {
                             x1 = touchevent.getX();
                             y1 = touchevent.getY();
                             break;
                        }
                         case MotionEvent.ACTION_UP: 
                         {
                             x2 = touchevent.getX();
                             y2 = touchevent.getY(); 

                             //if left to right sweep event on screen
                             if (x1 < x2) 
                             {
                            	 Quote quote = new Quote(this, "previous", id);
                         		 quote.execute();
                              }
                            
                             // if right to left sweep event on screen
                             if (x1 > x2)
                             {
                            	 Quote quote = new Quote(this, "next", id);
                         		 quote.execute();
                             }
                            
                             // if UP to Down sweep event on screen
                             /*if (y1 < y2) 
                             {
                                 Toast.makeText(this, "UP to Down Swap Performed", Toast.LENGTH_LONG).show();
                             }
                            
                             //if Down to UP sweep event on screen
                             if (y1 > y2)
                             {
                                 Toast.makeText(this, "Down to UP Swap Performed", Toast.LENGTH_LONG).show();
                              }*/
                             break;
                         }
                 }
                 return false;
    }


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.quote, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_quote,
					container, false);
			return rootView;
		}
	}

}
