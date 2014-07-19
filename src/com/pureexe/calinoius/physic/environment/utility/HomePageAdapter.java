package com.pureexe.calinoius.physic.environment.utility;


import com.pureexe.calinoius.physic.environment.R;

import android.app.Activity;
import android.content.Context;
import android.text.BoringLayout.Metrics;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class HomePageAdapter extends BaseAdapter {
    private Context mContext;
    private DataManager dataManager;

    public HomePageAdapter(Context c) {
        mContext = c;
        dataManager = new DataManager(c);
    }

    
    public int getCount() {
        return mThumbIds.length;
    }
    
    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }
    
    
    // create a new ImageView for each item referenced by the Adapter
	@Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        if (convertView == null) {
            Activity aContext;
            aContext = (Activity) mContext;
            LayoutInflater inflater  = aContext.getLayoutInflater(); 
            row = inflater.inflate(R.layout.grid_homepage, parent, false);
            DisplayMetrics disMer = mContext.getResources().getDisplayMetrics();
            row.setLayoutParams(new GridView.LayoutParams((int)((float)120*disMer.density),(int)((float)120*disMer.density)));
            //row.setPadding(8,8, 8, 8);            
            ImageView img = (ImageView) row.findViewById(R.id.tileImage);
            TextView txt = (TextView) row.findViewById(R.id.tileLabel);
            RelativeLayout relativeLayout = (RelativeLayout) row.findViewById(R.id.Block);
            relativeLayout.setBackgroundResource(R.color.teal200);

            if(position == 0){
                img.setImageResource(mThumbIds[position].picture);
            	txt.setText(dataManager.getString("Researcher"));
            } else {
                img.setImageResource(mThumbIds[position].picture);
                txt.setText(mThumbIds[position].label);
            }
            return row;
          
        } else {
            return row;
        }

    }

    public static class GridStructer {
    	Integer picture;
    	String label;
    	GridStructer(Integer a,String b){
    		picture = a;
    		label = b;
    	}
    }
    
    
    
    // references to our images
    private GridStructer[] mThumbIds = {
    		GridStructer(R.drawable.ic_action_person,"Me"),
    		GridStructer(R.drawable.ic_action_camera,"Camera"),
    		GridStructer(R.drawable.ic_action_labels,"Read Tag"),
    		GridStructer(R.drawable.ic_action_settings,"Setting"),
    		GridStructer(R.drawable.ic_action_help,"Help"),
    		GridStructer(R.drawable.ic_action_about,"About"),
    };

	private GridStructer GridStructer(int ic, String string) {
		return new GridStructer(ic,string);
	}


}