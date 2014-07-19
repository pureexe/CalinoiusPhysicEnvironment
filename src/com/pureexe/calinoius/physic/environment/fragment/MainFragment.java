package com.pureexe.calinoius.physic.environment.fragment;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Fragment;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.Toast;

import com.pureexe.calinoius.physic.environment.R;
import com.pureexe.calinoius.physic.environment.activity.EXIFreadActivity;
import com.pureexe.calinoius.physic.environment.activity.EnvironmentCameraActivity;
import com.pureexe.calinoius.physic.environment.activity.FragmentDisplayActivity;
import com.pureexe.calinoius.physic.environment.activity.MainActivity;
import com.pureexe.calinoius.physic.environment.utility.DataManager;
import com.pureexe.calinoius.physic.environment.utility.EnvSensorJSON;
import com.pureexe.calinoius.physic.environment.utility.HomePageAdapter;

public class MainFragment extends Fragment  {
	DataManager dataManager;
	public MainFragment() {
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		setHasOptionsMenu(true);
		super.onCreate(savedInstanceState);
	}	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_main, container,
				false);

		dataManager = new DataManager(getActivity());
		
		Cursor c = getActivity().getApplication().getContentResolver().query(ContactsContract.Profile.CONTENT_URI, null, null, null, null); 
		c.moveToFirst();
		
		dataManager.setString("Researcher", c.getString(c.getColumnIndex("display_name")));
	    GridView gridview = (GridView) rootView.findViewById(R.id.gridView1);
	    gridview.setAdapter(new HomePageAdapter(getActivity()));
	    gridview.setOnItemClickListener(new OnItemClickListener() {
	        public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
	            //Toast.makeText(getActivity(), "" + position, Toast.LENGTH_SHORT).show();
	        	if(position==0){
	        		Toast.makeText(getActivity(), "No implement yet :P", Toast.LENGTH_SHORT).show();
	        	}
	        	if(position==1){
	    			Intent intent = new Intent(getActivity(), EnvironmentCameraActivity.class);
	    	        startActivity(intent);
	            }
	        	if(position==2){
	            	Intent beepActivity = new Intent(getActivity(),FragmentDisplayActivity.class);
	            	beepActivity.setAction(Intent.ACTION_SEND);
	            	beepActivity.putExtra(Intent.EXTRA_TEXT,"EXIFreadFragment");
	            	beepActivity.setType("beepActivity");
	            	startActivity(beepActivity);
	            }
	        	if(position==3){
	            	Intent beepActivity = new Intent(getActivity(),FragmentDisplayActivity.class);
	            	beepActivity.setAction(Intent.ACTION_SEND);
	            	beepActivity.putExtra(Intent.EXTRA_TEXT,"SettingPreferenceFragment");
	            	beepActivity.setType("beepActivity");
	            	startActivity(beepActivity);
	            }
	        }
	    });

	    
		return rootView;
	}
	
	


	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		inflater.inflate(R.menu.main, menu);
		super.onCreateOptionsMenu(menu, inflater);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.action_environmentcamera) {
			Intent intent = new Intent(getActivity(), EnvironmentCameraActivity.class);
	        startActivity(intent);
		}
		if (id == R.id.action_exifread) {
			Intent intent = new Intent(getActivity(), EXIFreadActivity.class);
	        startActivity(intent);
		}
		if (id == R.id.action_compass) {
        	Intent beepActivity = new Intent(getActivity(),FragmentDisplayActivity.class);
        	beepActivity.setAction(Intent.ACTION_SEND);
        	beepActivity.putExtra(Intent.EXTRA_TEXT,FragmentName.CompassFragment);
        	beepActivity.setType("beepActivity");
        	startActivity(beepActivity);
		}
		return super.onOptionsItemSelected(item);
	}

	
}
