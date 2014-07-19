package com.pureexe.calinoius.physic.environment.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.pureexe.calinoius.physic.environment.R;
import com.pureexe.calinoius.physic.environment.fragment.EXIFreadFragment;
import com.pureexe.calinoius.physic.environment.fragment.MainFragment;

public class EXIFreadActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		if (savedInstanceState == null) {
			getFragmentManager().beginTransaction()
					.add(R.id.container, new EXIFreadFragment()).commit(); /// Doesn't Finish yet
		}
	}
	
	
	
	
}