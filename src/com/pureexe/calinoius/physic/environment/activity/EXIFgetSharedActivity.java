package com.pureexe.calinoius.physic.environment.activity;

import android.app.Activity;
import android.os.Bundle;

import com.pureexe.calinoius.physic.environment.R;
import com.pureexe.calinoius.physic.environment.fragment.EXIFgetSharedFragment;
import com.pureexe.calinoius.physic.environment.fragment.MainFragment;

public class EXIFgetSharedActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		if (savedInstanceState == null) {
			getFragmentManager().beginTransaction()
					.add(R.id.container, new EXIFgetSharedFragment()).commit(); /// Doesn't Finish yet
		}
	}
	
	
	
}