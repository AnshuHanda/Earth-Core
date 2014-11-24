package com.example.earth_core;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;

import android.widget.TextView;
import android.widget.AdapterView.OnItemSelectedListener;

public class state_registration extends Activity {
	String s1;
	Spinner spinner1;
	TextView tv,tv2,tv3,tv4,tv5,tv6,tv7,tv8,tv9,tv10,tv11;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
			setContentView(R.layout.state_registration1);
		
	        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.window_title);
	        ImageButton   icon  = (ImageButton) findViewById(R.id.button);
	        icon.setOnClickListener(new OnClickListener(){

	   				@Override
	   				public void onClick(View v) {
	   					SharedPreferences settings = getSharedPreferences("MYPREFS", 0);
		     	        String mainMenuView = settings.getString("MAIN_MENU_VIEW", "");
		     	        Intent mainIntent = new Intent(state_registration.this,MainActivity.class);
		               	if(mainMenuView.equalsIgnoreCase("grid view"))
		               		mainIntent = new Intent(state_registration.this,MainActivity.class);
		            	else if(mainMenuView.equalsIgnoreCase("list view"))
		            		mainIntent = new Intent(state_registration.this,MainActivityList.class);
		            	
		                startActivity(mainIntent);
	   					
	   				}
	   	        	
	   	        });

		tv = (TextView) findViewById(R.id.textView1);
		
		tv2 = (TextView) findViewById(R.id.textView2);
		tv4 = (TextView) findViewById(R.id.textView4);
		tv6 = (TextView) findViewById(R.id.textView6);
		tv8 = (TextView) findViewById(R.id.textView8);
		tv10 = (TextView) findViewById(R.id.textView10);
		tv2.setText("Registration Under End Users Business Activity");
		tv4.setText("Registration Under Traders Business Activity");
		tv6.setText("Registration Under Stocklists Business Activity");
		tv8.setText("Registration Under Exporters Business Activity");
		tv10.setText("Registration Under Mining Business Activity");
		
		
		
		tv3 = (TextView) findViewById(R.id.textView3);
		tv5 = (TextView) findViewById(R.id.textView5);
		tv7 = (TextView) findViewById(R.id.textView7);
		tv9 = (TextView) findViewById(R.id.textView9);
		tv11 = (TextView) findViewById(R.id.textView11);
		
		 spinner1= (Spinner) findViewById(R.id.spinner1);
		 getSpinner();
		 setText();
		 spinner1.setOnItemSelectedListener(new OnItemSelectedListener(){

				@Override
				public void onItemSelected(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					// TODO Auto-generated method stub
					s1=""+arg0.getSelectedItem();
					setText();
				}

				@Override
				public void onNothingSelected(AdapterView<?> arg0) {
					// TODO Auto-generated method stub
					
				}
			});
	        
		
	}
	void setText()
	{
		TestAdapter mDbHelper=new TestAdapter(this); ;
		mDbHelper.open();
		Cursor testdata = mDbHelper.getData("select * from State_registration where state like '%"+s1+"%'");
		testdata.moveToFirst();
		tv.setText(s1.toUpperCase());
		tv3.setText(testdata.getString(2));
		tv5.setText(testdata.getString(3));
		tv7.setText(testdata.getString(4));
		tv9.setText(testdata.getString(5));
		tv11.setText(testdata.getString(6));
		  mDbHelper.close();
		
	}
	
	void getSpinner()
	{
		spinner1= (Spinner) findViewById(R.id.spinner1);
		
		TestAdapter mDbHelper = new TestAdapter(this);          
		mDbHelper.open();
		
		ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, android.R.id.text1);
		spinnerAdapter.clear();
		
		spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner1.setAdapter(spinnerAdapter); 
		
		Cursor testdata = mDbHelper.getData("SELECT state FROM state_registration where state not like 'total'  order by state collate nocase");
		s1=testdata.getString(0);
		for(testdata.moveToFirst();!testdata.isAfterLast();testdata.moveToNext())
		 {
			 spinnerAdapter.add(testdata.getString(0));
		 }
		spinnerAdapter.notifyDataSetChanged();
		mDbHelper.close();
		
	}
}
