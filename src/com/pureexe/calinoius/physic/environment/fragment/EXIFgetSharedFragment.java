package com.pureexe.calinoius.physic.environment.fragment;

import java.io.File;
import java.io.IOException;

import com.pureexe.calinoius.physic.environment.R;
import com.pureexe.calinoius.physic.environment.activity.EnvironmentCameraActivity;

import android.app.Fragment;
import android.content.Intent;
import android.database.Cursor;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class EXIFgetSharedFragment extends Fragment {
	public EXIFgetSharedFragment() {
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		setHasOptionsMenu(true);
		super.onCreate(savedInstanceState);
	
	}
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_exifgetshared, container,
				false);
		Intent intent = getActivity().getIntent();
		String action = intent.getAction();
		String type = intent.getType();
		if (Intent.ACTION_SEND.equals(action) && type != null) {
			if (type.startsWith("image/")) {
				Uri imageUri = (Uri) intent.getParcelableExtra(Intent.EXTRA_STREAM);
				if (imageUri != null) {
					String path = null;
					  String[] projection = { MediaStore.Images.Media.DATA };
					    Cursor cursor = getActivity().getContentResolver().query(imageUri, projection, null, null, null);
					    if (cursor.moveToFirst()) {
					        int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
					        path = cursor.getString(columnIndex);
					    }
					    
					    cursor.close();
						try {
							ExifInterface exif = new ExifInterface(path);
							TextView txt = (TextView)rootView.findViewById(R.id.textView1);
							txt.setText(exif.getAttribute("UserComment"));
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					
					
				}
			}
		}
		
		
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
