package com.example.earth_core;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.TableRow.LayoutParams;

public class state_reserves extends Activity {

	EditText et;
	TableLayout tl;
	TableRow tr;
    TextView colname,value,tv;
	String s1;
	Spinner spinner1;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.state_reserves);
	
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.window_title);
        ImageButton   icon  = (ImageButton) findViewById(R.id.button);

        icon.setOnClickListener(new OnClickListener(){

   				@Override
   				public void onClick(View v) {
   					SharedPreferences settings = getSharedPreferences("MYPREFS", 0);
	     	        String mainMenuView = settings.getString("MAIN_MENU_VIEW", "");
	     	        Intent mainIntent = new Intent(state_reserves.this,MainActivity.class);
	               	if(mainMenuView.equalsIgnoreCase("grid view"))
	               		mainIntent = new Intent(state_reserves.this,MainActivity.class);
	            	else if(mainMenuView.equalsIgnoreCase("list view"))
	            		mainIntent = new Intent(state_reserves.this,MainActivityList.class);
	            	
	                startActivity(mainIntent);
   					
   				}
   	        	
   	        });
		
        //s1= getIntent().getExtras().getString("State");
        spinner1= (Spinner) findViewById(R.id.spinner1);
        getSpinner();
        
        et=(EditText) findViewById(R.id.editText1);
        et.setHint("Enter Mineral");
        tv = (TextView) findViewById(R.id.textView1);
       
        
        tl = (TableLayout) findViewById(R.id.maintable);
        getdata("SELECT * FROM reserves where state like '%"+s1+"%' order by mineral");
        
        spinner1.setOnItemSelectedListener(new OnItemSelectedListener(){

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				s1=""+arg0.getSelectedItem();
				tl.removeAllViews();
				getdata("SELECT * FROM reserves where state like '%"+s1+"%' order by mineral");
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
		});
        
        
        et.addTextChangedListener(new TextWatcher(){

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// TODO Auto-generated method stub
					tl.removeAllViews();
				
					int n=getdata("SELECT * FROM reserves where mineral like '"+s+"%'and state like '%"+s1+"%' order by mineral");
					if(n==0)
					{
						tv.setText("No Records Found!!!");
					}
					else
					{
						tv.setText(s1.toUpperCase());
					}
				}
				
			
			
		});
	}
	
	int getdata(String s)
	{
		TestAdapter mDbHelper=new TestAdapter(this); ;
		mDbHelper.open();
		String colname1="";
		tv.setText(s1.toUpperCase());
		Cursor testdata = mDbHelper.getData(s);
		int n=testdata.getCount();
        int i=1,j,color;
	    for(testdata.moveToFirst();!testdata.isAfterLast();testdata.moveToNext(),i++)
		{
			if(i%2==0)
				color=Color.BLACK;
			else
				color=Color.WHITE;
			
			for(j=0;j<testdata.getColumnCount();j++)
			{
				
				switch(j)
				{
					case 0:colname1=testdata.getColumnName(0);
						break;
					case 1:colname1="Unit";
					break;
					case 2:colname1="State";
					break;
					case 3:colname1="Reserves";
					break;
					case 4:colname1="Remaining Resources";
						break;
					case 5:colname1="Total Resources";
						break;
				}
				tr = new TableRow(this);
	            tr.setLayoutParams(new LayoutParams(
	                    LayoutParams.MATCH_PARENT,
	                    LayoutParams.MATCH_PARENT));
	 
	            /** Creating a TextView to add to the row **/
	            colname = new TextView(this);
	            colname.setText(""+colname1+": ");
	            colname.setTextColor(Color.RED);
	            //  colname.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
	            colname.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT));
	            colname.setPadding(5, 5, 5, 5);
	            tr.addView(colname);  // Adding textView to tablerow.
	            
	            value = new TextView(this);
	            value.setText(testdata.getString(j));
	            value.setTextColor(Color.MAGENTA);
	            value.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT));
	            value.setPadding(5, 5, 5, 5);
	            //value.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
	            tr.addView(value); // Adding textView to tablerow.
	
	            // Add the TableRow to the TableLayout
	            tr.setBackgroundColor(color);
	            tl.addView(tr, new TableLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
			}
		}
	
	    mDbHelper.close();
	    return n;
	}
	void getSpinner()
	{
		
		
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


