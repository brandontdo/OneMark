package com.example.onemarkeah;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

public class Marks extends Activity {
	public static Button[] b;
	TextView average;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		  requestWindowFeature(Window.FEATURE_NO_TITLE);
	        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
	                                WindowManager.LayoutParams.FLAG_FULLSCREEN);
	        
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_marks);
		b = new Button[6];
        b[0] = (Button) findViewById(R.id.imageButton1);
        b[1] = (Button) findViewById(R.id.imageButton2);
        b[2] = (Button) findViewById(R.id.imageButton3);
        b[3] = (Button) findViewById(R.id.imageButton4);
        b[4] = (Button) findViewById(R.id.imageButton5);
        b[5] = (Button) findViewById(R.id.imageButton6);
        average = (TextView) findViewById(R.id.avg);
        average.setText( String.format("%.1f", MainActivity.updateFiles()) + "%");
	}
	
	public void openCourse(View view){
		Intent intent = new Intent (this, Course.class);
		
		startActivity(intent);
	}
	public void openAdd(View view){
		Intent intent = new Intent (this, CourseAddition.class);
		
		startActivity(intent);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.marks, menu);
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
