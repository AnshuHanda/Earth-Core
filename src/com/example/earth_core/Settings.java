package com.example.earth_core;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class Settings extends Activity{
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.settings);
	
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.window_title);
        ImageButton   icon  = (ImageButton) findViewById(R.id.button);
        ImageView icon2 = (ImageView) findViewById(R.id.header);
        icon2.setImageResource(R.drawable.ic_menu_settings_holo_light);
        TextView t =(TextView) findViewById(R.id.t);
        t.setText("Settings");
        icon.setOnClickListener(new OnClickListener(){

   				@Override
   				public void onClick(View v) {
	            	SharedPreferences settings = getSharedPreferences("MYPREFS", 0);
	     	        String mainMenuView = settings.getString("MAIN_MENU_VIEW", "");
	     	        Intent mainIntent = new Intent(Settings.this,MainActivity.class);
	               	if(mainMenuView.equalsIgnoreCase("grid view"))
	               		mainIntent = new Intent(Settings.this,MainActivity.class);
	            	else if(mainMenuView.equalsIgnoreCase("list view"))
	            		mainIntent = new Intent(Settings.this,MainActivityList.class);
	            	
	                startActivity(mainIntent);
   				}
   	        });
        
		RadioGroup select=(RadioGroup) findViewById(R.id.radioGroup1);
		SharedPreferences settings = getSharedPreferences("MYPREFS", 0);
	    String mainMenuView = settings.getString("MAIN_MENU_VIEW", "");
		for(int i =0; i < select.getChildCount(); i++)
		{
			RadioButton rb = (RadioButton)select.getChildAt(i);
			if(rb.getText().toString().equalsIgnoreCase(mainMenuView))
			{
				rb.setChecked(true);
				break;
			}
		}
       
        Button b= (Button) findViewById(R.id.button1);
		
		b.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub			
				RadioGroup select=(RadioGroup) findViewById(R.id.radioGroup1);
				RadioButton radiobutton=(RadioButton) findViewById(select.getCheckedRadioButtonId());
				String s=""+radiobutton.getText();
				 SharedPreferences settings = getSharedPreferences("MYPREFS", 0);
				 SharedPreferences.Editor editor = settings.edit();
				 editor.putString("MAIN_MENU_VIEW", s);
				 editor.commit();
				Toast.makeText(Settings.this, "Settings saved", Toast.LENGTH_SHORT).show();

			}
		});
	}
	@Override
	public void onBackPressed()
	{
		SharedPreferences settings = getSharedPreferences("MYPREFS", 0);
	        String mainMenuView = settings.getString("MAIN_MENU_VIEW", "");
	        Intent mainIntent = new Intent(Settings.this,MainActivity.class);
       	if(mainMenuView.equalsIgnoreCase("grid view"))
       		mainIntent = new Intent(Settings.this,MainActivity.class);
    	else if(mainMenuView.equalsIgnoreCase("list view"))
    		mainIntent = new Intent(Settings.this,MainActivityList.class);
    	
        startActivity(mainIntent);
	}
}
