package com.example.onemarkeah;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

public class MainActivity extends Activity {
	public static List<CourseInfo> courseList;
	public static CourseObject c;
	public static CourseInfo info;
	public static double avgTotal, overallAverage;
	// 
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
                                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}
	
	public void openMenu(View view){
		courseList = new ArrayList<CourseInfo>();
		courseList = new ArrayList<CourseInfo>();
		courseList.add(new CourseInfo("http://alexli.ca/onemark/MHF4U6.htm", "Functions", "BKPDC"));
//		courseList.add(new CourseInfo("http://alexli.ca/onemark/MHF4U6.htm", "Chemistry", "CBYLN"));
//		courseList.add(new CourseInfo("http://alexli.ca/onemark/MHF4U6.htm", "English", "CMDQC"));
//		courseList.add(new CourseInfo("http://alexli.ca/onemark/MHF4U6.htm", "Physics", "RFBKJ"));
//		courseList.add(new CourseInfo("http://alexli.ca/onemark/MHF4U6.htm", "Biology", "NWFKW"));
//		courseList.add(new CourseInfo("http://alexli.ca/onemark/MHF4U6.htm", "CompSci", "LGBPZ"));
		courseList.add(new CourseInfo("http://alexli.ca/onemark/SCH4U6.htm", "Chem", "BakedPotato"));
		courseList.add(new CourseInfo("http://alexli.ca/onemark/SNC12L1.htm", "Biology", "342637998"));
		courseList.add(new CourseInfo("http://alexli.ca/onemark/SNC2D1.htm", "Comp-Eng", "323138164"));
		courseList.add(new CourseInfo("http://alexli.ca/onemark/SPH4C1.htm", "Physics", "310184742"));
		Intent intent = new Intent (this, Marks.class);	
		
		startActivity(intent);
	}
	public static double updateFiles(){
		avgTotal = 0;
		for(int i = 0; i < courseList.size(); i++){
			info = courseList.get(i);
			c = new CourseObject(info.URL, info.code);
			avgTotal+=c.getOverallMark();
			Marks.b[i].setText(info.className + "\n" + String.format("%.1f", c.getOverallMark()) + "%");
		}
		return avgTotal/courseList.size();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
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




