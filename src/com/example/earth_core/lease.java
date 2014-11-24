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
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.TableRow.LayoutParams;

public class lease extends Activity {

	EditText et;
	TableLayout tl;
	TableRow tr;
    TextView colname,value,tv;
	String s1,order,text;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.lease);
	
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.window_title);
        ImageButton   icon  = (ImageButton) findViewById(R.id.button);
        icon.setOnClickListener(new OnClickListener(){

   				@Override
   				public void onClick(View v) {
   					SharedPreferences settings = getSharedPreferences("MYPREFS", 0);
	     	        String mainMenuView = settings.getString("MAIN_MENU_VIEW", "");
	     	        Intent mainIntent = new Intent(lease.this,MainActivity.class);
	               	if(mainMenuView.equalsIgnoreCase("grid view"))
	               		mainIntent = new Intent(lease.this,MainActivity.class);
	            	else if(mainMenuView.equalsIgnoreCase("list view"))
	            		mainIntent = new Intent(lease.this,MainActivityList.class);
	            	
	                startActivity(mainIntent);
   					
   				}
   	        	
   	        });
		
        s1= getIntent().getExtras().getString("tablename");
        et=(EditText) findViewById(R.id.editText1);
        tv = (TextView) findViewById(R.id.textView1);
        if(s1.equalsIgnoreCase("mineralwise_leases"))
        {
        	text="Mineral Wise";
        	et.setHint("Enter Mineral");
        	order="mineral";
        }
        else
        {
        	order="state";
        	et.setHint("Enter State");
        	text="State Wise";
        }
        tv.setText(text);
        tl = (TableLayout) findViewById(R.id.maintable);
        getdata("SELECT * FROM " + s1+" order by "+order);
        
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
				
					int n=getdata("SELECT * FROM " + s1+" where "+ order +" like '"+s+"%' order by "+order);
					if(n==0)
					{
						tv.setText("No Records Found!!!");
					}
					else
					{
						tv.setText(text);
					}
				}
				
			
			
		});
	}
	
	int getdata(String s)
	{
		TestAdapter mDbHelper=new TestAdapter(this); ;
		mDbHelper.open();
		String colname1="";
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
					case 1:colname1="No. of Leases";
						break;
					case 2:colname1="Lease Area(in hectares)";
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
}

