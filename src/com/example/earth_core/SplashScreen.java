package com.example.earth_core;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;


public class SplashScreen extends Activity{
	TextView tv;
	 private final int SPLASH_DISPLAY_LENGHT = 4000;
	 String mainMenuView;
	    /** Called when the activity is first created. */
	    @Override
	    public void onCreate(Bundle icicle) {
	        super.onCreate(icicle);
	        setContentView(R.layout.splashscreen);
	        tv = (TextView)findViewById(R.id.textView1); 
	        
	        SharedPreferences settings = getSharedPreferences("MYPREFS", 0);
 	        mainMenuView = settings.getString("MAIN_MENU_VIEW", "");
        	if(mainMenuView.equalsIgnoreCase(""))
        	{
        		tv.setText("Initializing App for the first time\nMay take some time...");
        		tv.setTextColor(Color.WHITE);
        		 SharedPreferences.Editor editor = settings.edit();
				 editor.putString("MAIN_MENU_VIEW", "grid view");
				 editor.commit();
        	}
       	 TestAdapter mDbHelper = new TestAdapter(this);        
			mDbHelper.createDatabase();   
	        /* New Handler to start the Menu-Activity 
	         * and close this Splash-Screen after some seconds.*/
	        new Handler().postDelayed(new Runnable(){
	            @Override
	            public void run() {
	                /* Create an Intent that will start the Menu-Activity. */
	            	Intent mainIntent;
	            	SharedPreferences settings = getSharedPreferences("MYPREFS", 0);
	     	        mainMenuView = settings.getString("MAIN_MENU_VIEW", "");
	            	/*if(mainMenuView.equalsIgnoreCase(""))
	            	{
	            		tv.setText("Initializing App for the first time/nMay take some time...");
	            		tv.setTextColor(Color.WHITE);
	            		 SharedPreferences.Editor editor = settings.edit();
	    				 editor.putString("MAIN_MENU_VIEW", "grid view");
	    				 editor.commit();
	            	}*/
	            	if(mainMenuView.equalsIgnoreCase("grid view"))
	            	{
	                mainIntent = new Intent(SplashScreen.this,MainActivity.class);
	                SplashScreen.this.startActivity(mainIntent);
	                SplashScreen.this.finish();
	            	}
	            	else if(mainMenuView.equalsIgnoreCase("list view"))
	            	{
	                mainIntent = new Intent(SplashScreen.this,MainActivityList.class);
	                SplashScreen.this.startActivity(mainIntent);
	                SplashScreen.this.finish();
	            	}
	            }
	        }, SPLASH_DISPLAY_LENGHT);
	    }
}