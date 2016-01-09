package com.example.onemarkeah;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;

public class CourseAddition extends Activity {
	
	EditText URL, className;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
                                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        
		URL = (EditText) findViewById(R.id.URL);
		className = (EditText) findViewById(R.id.ClassName);
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_course_addition);

	}

	public void addCourse(View view){
		//MainActivity.courseList.add(new CourseInfo(URL.getText().toString(), className.getText().toString(), "BFBYN"));
		if (MainActivity.courseList.size()<6){
			MainActivity.courseList.add(new CourseInfo("http://alexli.ca/onemark/MHF4U6.htm", "CompSci", "HVPQZ"));
		}
		
		Intent intent = new Intent (this, Marks.class);
		
		startActivity(intent);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.course_addition, menu);
		return true;
	}
	
	/*public void addCourses(View view){
		courseList = new ArrayList<Student>();
	
		link = (EditText)findViewById(R.id.Link);
		code = (EditText)findViewById(R.id.Code);
	
		s = new Student(link.getText().toString(), code.getText().toString());
		courseList.add(s);
	}*/
	
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
