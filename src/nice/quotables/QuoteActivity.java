package nice.quotables;

import java.util.List;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.view.MotionEventCompat;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Build;

public class QuoteActivity extends ActionBarActivity {

	private static final String DEBUG_TAG = "11";
	float x1,x2;
    float y1, y2;
    String quote_string;
    String id = "";
    String author = "";
    Activity activity = this;
    

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_quote);

		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
		
		
		//Bundle extras = getIntent().getExtras();
		
		quote_string  = getIntent().getStringExtra("content");
		id = getIntent().getStringExtra("id");
		author = getIntent().getStringExtra("author");
		
		TextView tv = (TextView)findViewById(R.id.quoteText);
		Typeface font = Typeface.createFromAsset(getAssets(), "Chantelli_Antiqua.ttf");
		tv.setTypeface(font);
		tv.setText(quote_string);
		TextView author_view = (TextView)findViewById(R.id.author);
		author_view.setTypeface(font);
		author_view.setText(author);
		setHandlers();
	}
	
	public void setHandlers(){
		ImageView iv = (ImageView) findViewById(R.id.down);
		iv.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				Quote quote = new Quote(activity, "previous", id, null);
        		 quote.execute();
			}
			
		});
		
		ImageView iv2 = (ImageView) findViewById(R.id.up);
		iv2.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				Quote quote = new Quote(activity, "next", id, null);
        		 quote.execute();
			}
			
		});
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
                            	 Quote quote = new Quote(this, "previous", id, null);
                         		 quote.execute();
                              }
                            
                             // if right to left sweep event on screen
                             if (x1 > x2)
                             {
                            	 Quote quote = new Quote(this, "next", id, null);
                         		 quote.execute();
                             }
                            
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
			startActivity(new Intent(Intent.ACTION_VIEW).setData(Uri.parse("http://www.quotablesonline.com")));
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
