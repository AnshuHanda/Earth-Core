package com.example.earth_core;


import java.util.ArrayList;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivityList extends Activity {
	
	EditText et;
    TextView tv;
	String s1,state_cut;
	ProgressDialog pd;
	ArrayList<String> count;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.main_activity_list);
			 pd=new ProgressDialog(this);
				pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
				pd.setMessage("Loading");
				pd.setCancelable(true);
				pd.setIndeterminate(true);
			
	      		ListView listview = (ListView)findViewById(R.id.list);
	      		listview.setAdapter(new MyAdapter(this, 
	      				android.R.layout.simple_list_item_1,
	      				R.id.textView, 
	      				getResources().getStringArray(R.array.main_menu_options)));
	
		
	}
	
	private class MyAdapter extends ArrayAdapter<String>{
		
		public MyAdapter(Context context, int resource,
				int textViewResourceId, String[] strings) {
			super(context, resource, textViewResourceId, strings);
		}
		
		@Override
		public View getView(int position,View convertView, ViewGroup parent){
			
			LayoutInflater inflater=(LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View row=inflater.inflate(R.layout.main_activity_list_item,parent,false);
				row.setBackgroundColor(Color.BLACK);
			String[] items = getResources().getStringArray(R.array.main_menu_options);
			ImageView iv = (ImageView)row.findViewById(R.id.map12);
			
			TextView tv = (TextView) row.findViewById(R.id.textView);
			 tv.setText(items[position]);
			 tv.setTextColor(Color.WHITE);
			 
			 if(items[position].equalsIgnoreCase("ORES AND MINERALS"))
			 {
				 iv.setImageResource(R.drawable.icon11);
			}
			 else
				 if(items[position].equalsIgnoreCase("IMPORT AND EXPORT"))
				 {
					 iv.setImageResource(R.drawable.icon12);
				 }
				 else
					 if(items[position].equalsIgnoreCase("MINERAL PRODUCTION"))
					 {
						 iv.setImageResource(R.drawable.icon13);
					 }
					 else
						 if(items[position].equalsIgnoreCase("LEASE"))
						 {
							 iv.setImageResource(R.drawable.icon14);
						 }
						 else
							 if(items[position].equalsIgnoreCase("RESERVES"))
							 {
								 iv.setImageResource(R.drawable.icon15);
							 }
							 else
								 if(items[position].equalsIgnoreCase("ACTIVITY REGISTRATION"))
								 {
									 iv.setImageResource(R.drawable.icon16);
								 }
			 final String pos =""+items[position];
			 row.setOnClickListener(new OnClickListener(){

					@Override
					public void onClick(View v) {
						Intent intent=new Intent(MainActivityList.this,dialogore.class);
				        if(pos.equalsIgnoreCase("ORES AND MINERALS"))
				        {
				        	intent=new Intent(MainActivityList.this,dialogore.class); 
				        }
				        else
				        	if(pos.equalsIgnoreCase("IMPORT AND EXPORT"))
				        	{
				        		intent=new Intent(MainActivityList.this,impexpdialog.class); 
				        	}
					     else
					       	if(pos.equalsIgnoreCase("MINERAL PRODUCTION"))
					       	{
					       		intent=new Intent(MainActivityList.this,statedialogue1.class); 
					       	}
						 else
						   	if(pos.equalsIgnoreCase("LEASE"))
						   	{
						   		intent=new Intent(MainActivityList.this,lease_dialog.class); 
						   	}
						 else
						   	if(pos.equalsIgnoreCase("RESERVES"))
						   	{
						   		pd.show();
						   		intent=new Intent(MainActivityList.this,state_reserves.class); 	
				        	}
					    else
					    	if(pos.equalsIgnoreCase("ACTIVITY REGISTRATION"))
							{
					    		intent=new Intent(MainActivityList.this,state_registration.class); 	
							}
				        startActivity(intent);
						
					}
		        	
		        });
			return row;
		}
		
		
			
	}
	@Override
	public void onResume() {
	    super.onResume();  
	    if (pd.isShowing()) {
	        pd.cancel();
	    }
	
	}
	@Override
	public void onBackPressed()
	{
		Intent intent = new Intent(Intent.ACTION_MAIN);
	    intent.addCategory(Intent.CATEGORY_HOME);
	    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	    startActivity(intent);
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		//if(item.getItemId()==)
		if(item.getItemId()==R.id.about)
		{
			Intent intent= new Intent(MainActivityList.this,aboutus.class);
			startActivity(intent);
		
		}
		if(item.getItemId()==R.id.settings)
		{
			Intent intent= new Intent(MainActivityList.this,Settings.class);
			startActivity(intent);
		
		}
		return super.onOptionsItemSelected(item);
	}
}
