package com.pureexe.calinoius.physic.environment.fragment;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.pureexe.calinoius.physic.environment.R;
import com.pureexe.calinoius.physic.environment.activity.EnvironmentCameraActivity;
import com.pureexe.calinoius.physic.environment.activity.MainActivity;
import com.pureexe.calinoius.physic.environment.utility.EnvironmentSensorJSON;

public class MainFragment extends Fragment  {
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
		return super.onOptionsItemSelected(item);
	}

	
}
