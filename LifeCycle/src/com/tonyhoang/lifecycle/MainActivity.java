package com.tonyhoang.lifecycle;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.*;

public class MainActivity extends Activity {

	public static final String  MY_PREF_SID = "MyPrefs001";
	public static final int actMode = Activity.MODE_PRIVATE;
	
	LinearLayout myScreen;
	EditText txtColorSelect;
	TextView txtToDo;
	Button btnFinish;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Toast.makeText(this, "Create", Toast.LENGTH_SHORT).show();
			
		myScreen = (LinearLayout)findViewById(R.id.myScreen);		
		txtToDo = (TextView)findViewById(R.id.txtToDo);
		String msg = "Instructions: \n"
				+ "0. New instance (onCreate, onStart, onResume)\n"
				+ "1. Back arrow   (onPause, onStop, onDestroy\n"
				+ "2. Finish       (onPause, onStop, onDestroy\n"
				+ "3. Home         (onPause, onStop)\n"
				+ "4. After 3 > App Tab > re-excute current app (onRestart, onStart, onResume)\n"
				+ "5. Run DDMS > Receive a phone call or SMS (onRestart, onStart, onResume) \n"
				+ "6. Enter some data - repeat step 1 - 5 \n";
		txtToDo.setText(msg);
		
		txtColorSelect = (EditText)findViewById(R.id.txtColorSelect);
		txtColorSelect.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void afterTextChanged(Editable arg0) {
				// TODO Auto-generated method stub
				changeBackgroudColor(arg0.toString());
				
			}
		});
		
		btnFinish = (Button)findViewById(R.id.btnFinish);
		btnFinish.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		
	}
	
	private void saveDataFromCurrentState() {
		SharedPreferences myPrefs = getSharedPreferences(MY_PREF_SID, actMode);
		SharedPreferences.Editor myEditor = myPrefs.edit();
		myEditor.putString("myBkColor", txtColorSelect.getText().toString());
		myEditor.commit();
	}
	
	private void updateFromSavedState()
	{
		SharedPreferences myPrefs = getSharedPreferences(MY_PREF_SID, actMode);
		if ((myPrefs != null) && (myPrefs.contains("myBkColor"))){
			String color = myPrefs.getString("myBkColor", "");
			txtColorSelect.setText(color);
			changeBackgroudColor(color);
		}
	}
	
	private void clearMyPreferences()
	{
		SharedPreferences myPrefs = getSharedPreferences(MY_PREF_SID, actMode);
		SharedPreferences.Editor myEditor = myPrefs.edit();
		myEditor.clear();
		myEditor.commit();		
	}
	
	private void changeBackgroudColor(String color)
	{
		if (color.contains("red"))
			myScreen.setBackgroundColor(0xffff0000);
		else if (color.contains("green"))
			myScreen.setBackgroundColor(0xff00ff00);
		else if (color.contains("blue"))
			myScreen.setBackgroundColor(0xff0000ff);
		else
		{
			clearMyPreferences();
			myScreen.setBackgroundColor(0xff000000);
		}
	}
	
	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		super.onRestart();
		Toast.makeText(this, "Restart", Toast.LENGTH_SHORT).show();
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		Toast.makeText(this, "Start", Toast.LENGTH_SHORT).show();
		updateFromSavedState();
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		Toast.makeText(this, "Resume", Toast.LENGTH_SHORT).show();
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		Toast.makeText(this, "Pause", Toast.LENGTH_SHORT).show();
		saveDataFromCurrentState();
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		Toast.makeText(this, "Stop", Toast.LENGTH_SHORT).show();
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		Toast.makeText(this, "Destroy", Toast.LENGTH_SHORT).show();
	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onRestoreInstanceState(savedInstanceState);
		Toast.makeText(this, "RestoreInstanceState...", Toast.LENGTH_SHORT).show();
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		super.onSaveInstanceState(outState);
		Toast.makeText(this, "SaveInstanceState...", Toast.LENGTH_SHORT).show();
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
			Toast.makeText(this, "Menu 1", Toast.LENGTH_SHORT).show();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
