package nice.quotables;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ScrollView;

public class ScrollViewWatcher extends ScrollView {

	public ScrollViewWatcher(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onScrollChanged(int l, int t, int oldl, int oldt) {
	        View view = (View) getChildAt(getChildCount()-1);
	        int diff = (view.getBottom()-(getHeight()+getScrollY()));// Calculate the scrolldiff
	        if( diff == 0 ){  // if diff is zero, then the bottom has been reached
	            Log.d("1", "MyScrollView: Bottom has been reached" );
	        }
	        super.onScrollChanged(l, t, oldl, oldt);
	}
	
}
