package com.example.earth_core;

import android.app.Activity;



import android.content.Intent;

import android.database.Cursor;
import android.os.Bundle;

import android.view.View;
import android.view.View.OnClickListener;


import android.widget.ArrayAdapter;
import android.widget.Button;

import android.widget.Spinner;


public class compare_minerals extends Activity{
	String state,mineral1,mineral2,mineral3,year;
	Spinner spinner1,spinner2,spinner3,spinner4;
	//SharedPreferences compare_min;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);	    
	    setContentView(R.layout.compare_minerals);
	   
	    state=getIntent().getExtras().getString("state");
	    year=getIntent().getExtras().getString("year");
	//    Log.d("anmol",state);
	 //   Log.d("anmol",year);
	    spinner1 = (Spinner) findViewById(R.id.spinner1);
	    spinner2 = (Spinner) findViewById(R.id.spinner2);
	    spinner3 = (Spinner) findViewById(R.id.spinner3);
	    spinner4 = (Spinner) findViewById(R.id.spinner4);
	    spinner1=getSpinner(spinner1);
	    spinner2=getSpinner(spinner2);
	    spinner3=getSpinner(spinner3);
	    spinner4=getSpinner(spinner4);
	   	    
	 /*
	    spinner1.setOnItemSelectedListener(new OnItemSelectedListener(){

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				 spinner2=getSpinner(spinner2);
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
	    	
	    });
	    spinner2.setOnItemSelectedListener(new OnItemSelectedListener(){

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				AlertDialog.Builder builder=new AlertDialog.Builder(compare_minerals.this);
				builder.setMessage("Do you want to choose another mineral?");
				builder.setCancelable(true);
				builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						spinner3.setVisibility(Spinner.VISIBLE);
						 spinner3= getSpinner(spinner3);
						
					}
				});
				builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						dialog.cancel();
						
					}
				});
				
				AlertDialog alert=builder.create();
				alert.show();
			
				
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
		});
	    
	    spinner3.setOnItemSelectedListener(new OnItemSelectedListener(){

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				AlertDialog.Builder builder=new AlertDialog.Builder(compare_minerals.this);
				builder.setMessage("Do you want to choose another mineral?");
				builder.setCancelable(true);
				builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						spinner4.setVisibility(Spinner.VISIBLE);
						spinner4=  getSpinner(spinner4);
					}
				});
				builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						dialog.cancel();
						
					}
				});
				
				AlertDialog alert=builder.create();
				alert.show();
				 
				
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
		});
*/
	    Button b=(Button) findViewById(R.id.button1);
	    b.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//compare_min=getSharedPreferences("compare_min",0);
				//SharedPreferences.Editor editor=compare_min.edit();
				Intent intent=new Intent (compare_minerals.this,state_production_compare.class);
				intent.putExtra("min1", ""+spinner1.getSelectedItem());
				intent.putExtra("state", state);
				intent.putExtra("year", year);
				intent.putExtra("min2", ""+spinner2.getSelectedItem());
			
				intent.putExtra("min3", ""+spinner3.getSelectedItem());
				
				intent.putExtra("min4", ""+spinner4.getSelectedItem());
				
				
			    
			    startActivity(intent);
				
			}
	    	
	    });
	    

	}
	Spinner getSpinner(Spinner spinner)
	{
		TestAdapter mDbHelper = new TestAdapter(this);          
		mDbHelper.open();
		
		ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, android.R.id.text1);
		spinnerAdapter.clear();
		
		spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(spinnerAdapter); 
		
		Cursor testdata = mDbHelper.getData("SELECT distinct mineral FROM production where state like '%"+state+"%' order by mineral");
		//s1=testdata.getString(0);
		for(testdata.moveToFirst();!testdata.isAfterLast();testdata.moveToNext())
		 {
			 spinnerAdapter.add(testdata.getString(0));
		 }
		spinnerAdapter.notifyDataSetChanged();
		mDbHelper.close();
		return spinner;
		
	}
	
	
}
