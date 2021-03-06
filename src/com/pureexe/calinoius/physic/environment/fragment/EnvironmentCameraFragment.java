package com.pureexe.calinoius.physic.environment.fragment;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import android.app.Fragment;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.media.ExifInterface;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.BatteryManager;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnSystemUiVisibilityChangeListener;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.pureexe.calinoius.physic.environment.R;
import com.pureexe.calinoius.physic.environment.utility.CameraPreview;
import com.pureexe.calinoius.physic.environment.utility.DataManager;
import com.pureexe.calinoius.physic.environment.utility.EnvSensorJSON;

public class EnvironmentCameraFragment extends Fragment implements SensorEventListener {
	public EnvironmentCameraFragment() {
	}

	private SensorManager sensorManager;
	private Sensor sensorAcceleration;
	private Sensor sensorGravity;
	private Sensor sensorGyroscope;
	private Sensor sensorMagneticField;
	private Sensor sensorOrientation;
	private Sensor sensorLight;
	private Sensor sensorPressure;
	private Sensor sensorTempurature;
	private Sensor sensorHumidity;
	private boolean hasAcceleration;
	
	private boolean hasGravity;
	private boolean hasGyroscope;
	private boolean hasMagneticField;
	private boolean hasOrientation;
	private boolean hasLight;
	private boolean hasPressure;
	private boolean hasTempurature;
	private boolean hasHumidity;
	
	
	private float[] Acceleration;
	private float[] Gravity;
	private float[] Gyroscope;
	private float[] MagneticField;
	private float[] Orientation;
	private float[] Light;
	private float[] Pressure;
	private float[] Tempurature;
	private float 	BatteryTempurature;
	private float[] Humidity;
	
	
	private TextView displayTxt;
	private CameraPreview mPreview;
	private FrameLayout cameraFramePreview;
	private Camera mCamera;
	private PictureCallback jpgePictureCallback;
	protected long currentTime;
	
	private DataManager dataManager;
	
	public static final int MEDIA_TYPE_IMAGE = 1;
	public static final int MEDIA_TYPE_VIDEO = 2;

	private static Uri getOutputMediaFileUri(int type){
	      return Uri.fromFile(getOutputMediaFile(type));
	}

	private static File getOutputMediaFile(int type){
	    File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
	              Environment.DIRECTORY_PICTURES), "Calinoius");
	    if (! mediaStorageDir.exists()){
	        if (! mediaStorageDir.mkdirs()){
	        	return null;
	        }
	    }

	    String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
	    File mediaFile;
	    if (type == MEDIA_TYPE_IMAGE){
	        mediaFile = new File(mediaStorageDir.getPath() + File.separator +
	        "IMG_"+ timeStamp + ".jpg");
	    } else if(type == MEDIA_TYPE_VIDEO) {
	        mediaFile = new File(mediaStorageDir.getPath() + File.separator +
	        "VID_"+ timeStamp + ".mp4");
	    } else {
	        return null;
	    }
	    return mediaFile;
	}
	private BroadcastReceiver mBatInfoReceiver = new BroadcastReceiver(){
		@Override
		public void onReceive(Context context, Intent intent) {
		      BatteryTempurature = intent.getIntExtra(BatteryManager.EXTRA_TEMPERATURE, 0)/10.0f;
		}
	  };
	
	  public void shootSound()
	  {
	      AudioManager meng = (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);
	      int volume = meng.getStreamVolume( AudioManager.STREAM_NOTIFICATION);

	      if (volume != 0)
	      {
	    	  MediaPlayer _shootMP = null;
			if (_shootMP == null)
	              _shootMP = MediaPlayer.create(getActivity(), Uri.parse("file:///system/media/audio/ui/camera_click.ogg"));
	          if (_shootMP != null)
	              _shootMP.start();
	      }
	  }
	@Override
	public void onCreate(Bundle savedInstanceState) {
		sensorManager = (SensorManager) getActivity().getSystemService(getActivity().SENSOR_SERVICE);
		sensorAcceleration = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		sensorGravity =  sensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY);
		sensorGyroscope = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
		sensorMagneticField = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
		sensorOrientation = sensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION);
		sensorLight  = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
		sensorPressure = sensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE);
		sensorTempurature = sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);
		sensorHumidity = sensorManager.getDefaultSensor(Sensor.TYPE_RELATIVE_HUMIDITY);
	
		dataManager = new DataManager(getActivity());
		
		jpgePictureCallback = new PictureCallback() {
		    @Override
		    public void onPictureTaken(byte[] data, Camera camera) {
		    	shootSound();
		    	
		       File pictureFile = (File)getOutputMediaFile(MEDIA_TYPE_IMAGE);
		        if (pictureFile == null){
		         	Toast.makeText(getActivity(), "NULL Eject", Toast.LENGTH_LONG).show();
		        	return;
		        }
		        
		        try {
		            FileOutputStream fos = new FileOutputStream(pictureFile);
		            fos.write(data);
		            fos.close();
		            addImageGallery(pictureFile);
		            EnvSensorJSON envJson = new EnvSensorJSON();
		            ExifInterface exif = new ExifInterface(pictureFile.toString());
		            envJson.setSoftware();
		            envJson.setSoftwareVersion();
		            envJson.setResearcher("Pakkapon Phongthawee, Mr.");
		            if(hasAcceleration)
		            	envJson.setAcceleration(Acceleration);
		            if(hasGravity)
		            	envJson.setGravity(Gravity);
		            if(hasGyroscope)
		            	envJson.setGyroscope(Gyroscope);
		            if(hasMagneticField)
		            	envJson.setMagneticField(MagneticField);
		            if(hasOrientation)
		            	envJson.setOrientation(Orientation);
		            if(hasHumidity)
		            	envJson.setHumidity(Humidity);
		            if(hasLight)
		            	envJson.setLight(Light);
		            if(hasLight)
		            	envJson.setPressure(Pressure);
		            if(hasTempurature){
		            	envJson.setTempurature(Tempurature);
		            }else {
		            	float[] setTempuratureC=new float[1];
		            	setTempuratureC[0]=BatteryTempurature;
		            	envJson.setTempurature(setTempuratureC);
		            }
		            exif.setAttribute("UserComment", envJson.getJSON().toString());
		            exif.saveAttributes();
		            
		            mCamera.startPreview();
		        } catch (FileNotFoundException e) {
		        } catch (IOException e) {
		        }

		    }
		};
		super.onCreate(savedInstanceState);
	}

	private void addImageGallery( File file ) {
	    ContentValues values = new ContentValues();
	    values.put(MediaStore.Images.Media.DATA, file.getAbsolutePath());
	    values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
	    getActivity().getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
	}
	@Override
	public void onPause() {
		getActivity().unregisterReceiver(this.mBatInfoReceiver);
		sensorManager.unregisterListener(this);
		cameraFramePreview.removeAllViews();
		mCamera.stopPreview();
		mCamera.release();
		mCamera = null;
		super.onPause();
	}

	@Override
	public void onResume() {
		getActivity().registerReceiver(this.mBatInfoReceiver, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
		hasAcceleration = sensorManager.registerListener(this, sensorAcceleration,SensorManager.SENSOR_DELAY_NORMAL);
		hasGravity = sensorManager.registerListener(this, sensorGravity,SensorManager.SENSOR_DELAY_NORMAL);
		hasGyroscope = sensorManager.registerListener(this, sensorGyroscope,SensorManager.SENSOR_DELAY_NORMAL);
		hasMagneticField = sensorManager.registerListener(this, sensorMagneticField,SensorManager.SENSOR_DELAY_NORMAL);
		hasOrientation = sensorManager.registerListener(this, sensorOrientation,SensorManager.SENSOR_DELAY_NORMAL);
		hasLight = sensorManager.registerListener(this, sensorLight,SensorManager.SENSOR_DELAY_NORMAL);
		hasPressure = sensorManager.registerListener(this, sensorPressure,SensorManager.SENSOR_DELAY_NORMAL);
		hasTempurature = sensorManager.registerListener(this, sensorTempurature,SensorManager.SENSOR_DELAY_NORMAL);
		hasHumidity = sensorManager.registerListener(this, sensorHumidity,SensorManager.SENSOR_DELAY_NORMAL);
		
		 mCamera = CameraPreview.getCameraInstance();
	     if(mCamera==null){
	    	 Toast.makeText(getActivity(), "Can\'t connect camera", Toast.LENGTH_LONG).show();
	    	 getActivity().finish();

	     }
	     Camera.Parameters camParam = mCamera.getParameters();
	     List<Camera.Size> picture_size= camParam.getSupportedPictureSizes();    
	 
	     camParam.setPictureSize(picture_size.get(0).width, picture_size.get(0).height);
	     camParam.setFocusMode(Camera.Parameters.FOCUS_MODE_AUTO);
	     mCamera.setParameters(camParam);
	     mPreview = new CameraPreview(getActivity(), mCamera);
	     cameraFramePreview.addView(mPreview);
	     if(dataManager.getSettingBoolean(SettingPreferenceFragment.AIM_POINT)){
	    	 getView().findViewById(R.id.aim_point).setVisibility(View.VISIBLE);
	     } else {
	    	 getView().findViewById(R.id.aim_point).setVisibility(View.GONE);
	     }
		super.onResume();
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View decorView = getActivity().getWindow().getDecorView();
		decorView.setOnSystemUiVisibilityChangeListener(new OnSystemUiVisibilityChangeListener() {
			public void onSystemUiVisibilityChange(int visibility) {
				if(visibility == View.SYSTEM_UI_FLAG_VISIBLE) {
					currentTime = System.currentTimeMillis();
				}
			}
		});
		View rootView = inflater.inflate(R.layout.fragment_environmentcamera, container,
				false);
		getActivity().setRequestedOrientation(0);
		cameraFramePreview = (FrameLayout) rootView.findViewById(R.id.camera_preview);
		cameraFramePreview.setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				mCamera.autoFocus(null);
				return false;
			}
		});
		
		
		ImageView shutter = (ImageView) rootView.findViewById(R.id.imageView1);
		shutter.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				mCamera.takePicture(null, null, jpgePictureCallback);
				mCamera.autoFocus(null);
			}
		});
		
		
		
		return rootView;
	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onSensorChanged(SensorEvent event) {
		if(event.sensor.getType() == Sensor.TYPE_ACCELEROMETER){
			Acceleration = event.values;
		}
		if(event.sensor.getType() == Sensor.TYPE_GRAVITY){
			Gravity = event.values;
		}
		if(event.sensor.getType() == Sensor.TYPE_GYROSCOPE){
			Gyroscope = event.values;
		}
		if(event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD){
			MagneticField = event.values;
		}
		if(event.sensor.getType() == Sensor.TYPE_ORIENTATION){
			Orientation = event.values;
		}
		if(event.sensor.getType() == Sensor.TYPE_PRESSURE){
			Pressure = event.values;
		}
		if(event.sensor.getType() == Sensor.TYPE_LIGHT){
			Light = event.values;
		}
		if(event.sensor.getType() == Sensor.TYPE_AMBIENT_TEMPERATURE){
			Tempurature = event.values;
		}
		if(event.sensor.getType() == Sensor.TYPE_RELATIVE_HUMIDITY){
			Humidity = event.values;
		}
		
	}
	
}