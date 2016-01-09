package com.example.onemarkeah;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GraphViewSeries;
import com.jjoe64.graphview.LineGraphView;

public class Course extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		  requestWindowFeature(Window.FEATURE_NO_TITLE);
	        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
	                                WindowManager.LayoutParams.FLAG_FULLSCREEN);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_course);
		
		// init example series data
		GraphViewSeries exampleSeries = new GraphViewSeries(new GraphViewData[] {
				new GraphViewData(0, 0.0d),		
		new GraphViewData(1, 90.0d)
		, new GraphViewData(2, 95.0d)
		, new GraphViewData(3, 98.5d)
		, new GraphViewData(4, 86.0d)
		, new GraphViewData(5, 80.0d)
		, new GraphViewData(6, 94.0d)
		, new GraphViewData(7, 96.0d)
		, new GraphViewData(8, 97.8d)
		});
	
		GraphView graphView = new LineGraphView(
				this, "Progress" // heading
		);
		graphView.addSeries(exampleSeries); // data
		
		LinearLayout layout = (LinearLayout) findViewById(R.id.layout);
		layout.addView(graphView);		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.course, menu);
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
}
