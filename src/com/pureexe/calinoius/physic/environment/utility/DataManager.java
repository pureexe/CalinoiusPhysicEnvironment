package com.pureexe.calinoius.physic.environment.utility;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class DataManager {
	private SharedPreferences sharedPref;
	private SharedPreferences.Editor editor;
	private SharedPreferences defaultPref;
	private SharedPreferences.Editor defaultEditor;
	private static Context context;
	public DataManager(Context activity)
	{ 
		sharedPref = activity.getSharedPreferences("preferences", Context.MODE_PRIVATE);
		editor = sharedPref.edit();
		context = activity;
		defaultPref = PreferenceManager.getDefaultSharedPreferences(context);
		defaultEditor = defaultPref.edit();
	}
	/*** Easy Define ***/
	public void setBool(String name,boolean in)
	{
		editor.putBoolean(name, in);
		editor.commit();
	}
	public boolean getBool(String name)
	{
		return sharedPref.getBoolean(name, false);
	}
	public void setInt(String name,int in)
	{
		editor.putInt(name, in);
		editor.commit();
	}
	public int getInt(String name)
	{
		return sharedPref.getInt(name,0);
	}
	public void setFloat(String name,float in)
	{
		editor.putFloat(name, in);
		editor.commit();
	}
	public float getFloat(String name)
	{
		return sharedPref.getFloat(name,0);
	}
	public void setString(String name,String in)
	{
		editor.putString(name, in);
		editor.commit();
	}
	public String getString(String name)
	{
		return sharedPref.getString(name,null);
	}
	public void setLong(String name,long in)
	{
		editor.putLong(name, in);
		editor.commit();
	}
	public long getLong(String name){
		return sharedPref.getLong(name, 0);
	}

	
	/** get Setting **/
	public String getSettingString(String key){
        return defaultPref.getString(key, "");
    }
	public boolean getSettingBoolean(String key){
        return defaultPref.getBoolean(key,false);
    }

	
}
