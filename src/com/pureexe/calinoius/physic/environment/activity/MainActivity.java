package com.pureexe.calinoius.physic.environment.activity;

import com.pureexe.calinoius.physic.environment.R;
import com.pureexe.calinoius.physic.environment.fragment.MainFragment;

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
import android.os.Build;




public class MainActivity extends Activity {

	private CharSequence mTitle;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

       // mNavigationDrawerFragment = (NavigationDrawerFragment) getFragmentManager().findFragmentById(R.id.navigation_drawer);
    //    mTitle = getTitle();
    //    mNavigationDrawerFragment.setUp( R.id.navigation_drawer,(DrawerLayout) findViewById(R.id.drawer_layout));
        
		
		if (savedInstanceState == null) {
			getFragmentManager().beginTransaction()
					.add(R.id.container, new MainFragment()).commit();
		}
	}

}
