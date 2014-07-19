package com.pureexe.calinoius.physic.environment.activity;

import com.pureexe.calinoius.physic.environment.R;
import com.pureexe.calinoius.physic.environment.fragment.CompassFragment;
import com.pureexe.calinoius.physic.environment.fragment.EXIFreadFragment;
import com.pureexe.calinoius.physic.environment.fragment.FragmentName;
import com.pureexe.calinoius.physic.environment.fragment.MainFragment;
import com.pureexe.calinoius.physic.environment.fragment.SettingPreferenceFragment;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import android.os.Build;

public class FragmentDisplayActivity extends Activity {
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		 Intent intent = getIntent();
		 String type = intent.getType();
		 String start = intent.getExtras().getString(Intent.EXTRA_TEXT);
		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setTitle(getFragmentReadableName(start));
		if(type.equals("beepActivity")){
			if(start.equals("EXIFreadFragment")){
					getFragmentManager().beginTransaction()
							.replace(R.id.container, new EXIFreadFragment()).commit();
			} else if(start.equals("CompassFragment")){
					getFragmentManager().beginTransaction()
							.replace(R.id.container, new CompassFragment()).commit();
			}
			else if(start.equals("SettingPreferenceFragment")){
					getFragmentManager().beginTransaction()
							.replace(R.id.container, new SettingPreferenceFragment()).commit();
			}else{
				Toast.makeText(getApplicationContext(), "Not Found Intent : "+start, Toast.LENGTH_LONG).show();
			}
		}

	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId()) {

	    case android.R.id.home:
	    	   Intent intent = new Intent(this, MainActivity.class);
	           intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
	           startActivity(intent);
	    	//NavUtils.navigateUpFromSameTask(this);
	        return true;
	    }
	    return super.onOptionsItemSelected(item);
	}
	
	private CharSequence getFragmentReadableName(String name) {
		if(name.equals(FragmentName.CompassFragment)){
			return getString(R.string.action_compass);
		}
		if(name.equals("EXIFreadFragment")){
			return "Tag detail";
		}
		if(name.equals("SettingPreferenceFragment")){
			return "Setting";
		}
		return null;
	}
}
