package com.htm.flash;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity {

	private boolean isFlashOn;
	private boolean hasFlash;
	private Camera camera;
	private Button button1;
	private Parameters params;

	@Override
	protected void onStop() {
		super.onStop();
		if (camera != null) {
			camera.release();
			camera = null;
		}
	};
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
	};
	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		
		// turn off flash
		turnOffFlash();
	}
	
	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		super.onRestart();
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		
		if (hasFlash)
		{
			turnOnFlash();			
		}
	}
	
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		
		getCamera();
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		button1 = (Button) findViewById(R.id.button1);

		hasFlash = getApplicationContext().getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);
				
		if (!hasFlash) {
			Log.e("err", "Devices has no camera!");
			return;
		}
		
		getCamera();

		button1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (isFlashOn) {
					turnOffFlash();					
					button1.setText("ON");
				}
				else {
					turnOnFlash();					
					button1.setText("OFF");
				}
			}
		});
	}

	private void turnOnFlash() {
		// TODO Auto-generated method stub
		if (!isFlashOn) {
			if (camera == null || params == null) {
				return;
			}			
			
			Log.i("info", "torch is turn on!");
			params = camera.getParameters();
			params.setFlashMode(Parameters.FLASH_MODE_TORCH);
			camera.setParameters(params);
			camera.startPreview();
			isFlashOn = true;
		}
		
	}

	private void turnOffFlash() {
		// TODO Auto-generated method stub
		if (isFlashOn) {
			if (camera == null || params == null) {
				return;
			}

			Log.i("info", "torch is turn off!");
			params = camera.getParameters();
			params.setFlashMode(Parameters.FLASH_MODE_OFF);
			camera.setParameters(params);
			camera.stopPreview();
			isFlashOn = false;
		}
	}
	
	private void getCamera() {
		if (camera == null)
		{
			try {
				camera = Camera.open();
				params = camera.getParameters();
			} catch (Exception ex){}
		}
		
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
		// automatically handle clicks on the Home/Up button1, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
