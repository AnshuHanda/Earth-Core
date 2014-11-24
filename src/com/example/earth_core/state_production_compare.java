package com.example.earth_core;

import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;



import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;

import android.database.Cursor;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.AdapterView;

import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

public class state_production_compare extends Activity{
	private LinearLayout lay;
	HorizontalListView listview;
	private double highest;
	private int[] grossheight; 
	private int[] netheight;
	private Double[] grossSal;
	private Double[] netSal;
	private String[] datelabel;
	private String[] unit;
	private String[] str_quan;
	private String[] str_val;
	Spinner spinner1;
	TextView tv;
	String cutyear,state,year,mineral1,mineral2,mineral3,mineral4,q;
	//SharedPreferences compare_min;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		
	    requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
	    
	        setContentView(R.layout.state_production_compare);
	 
	        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.window_title);
	        ImageButton icon  = (ImageButton) findViewById(R.id.button);
	        icon.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View v) {
					SharedPreferences settings = getSharedPreferences("MYPREFS", 0);
	     	        String mainMenuView = settings.getString("MAIN_MENU_VIEW", "");
	     	        Intent mainIntent = new Intent(state_production_compare.this,MainActivity.class);
	               	if(mainMenuView.equalsIgnoreCase("grid view"))
	               		mainIntent = new Intent(state_production_compare.this,MainActivity.class);
	            	else if(mainMenuView.equalsIgnoreCase("list view"))
	            		mainIntent = new Intent(state_production_compare.this,MainActivityList.class);
	            	
	                startActivity(mainIntent);
					
				}
	        	
	        });
	        Button b1=(Button) findViewById(R.id.button1);
			b1.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent intent=new Intent(state_production_compare.this, state_production.class);
					startActivity(intent);
					
				}
				
			});
			
			tv=(TextView) findViewById(R.id.tv);
			//code to be written
			//compare_min=getSharedPreferences("compare_min",0);
			
			state=getIntent().getExtras().getString("state");
			year=getIntent().getExtras().getString("year");
			cutyear=year.replace('-', '_');
			mineral1=getIntent().getExtras().getString("min1");
			mineral2=getIntent().getExtras().getString("min2");
			mineral3=getIntent().getExtras().getString("min3");
			mineral4=getIntent().getExtras().getString("min4");
			
			spinner1 = (Spinner) findViewById(R.id.spinner1);
	       
			q="state like '%"+state+"%' and mineral like '%"+mineral1+"%' or state like '%"+state+"%' and mineral like '%"+mineral2+"%' ";
			if(!mineral3.equalsIgnoreCase("null"))
			{
				q+=" or state like '%"+state+"%' and mineral like '%"+mineral3+"%'";
			}
			if(!mineral4.equalsIgnoreCase("null"))
			{
				q+=" or state like '%"+state+"%' and mineral like '%"+mineral4+"%'";
			}
		
			drawGraph("SELECT unit,mineral,quantity"+cutyear+" as quantity,value"+cutyear+" as value FROM production where "+q+" order by mineral");
			spinner1.setOnItemSelectedListener(new OnItemSelectedListener(){

				@Override
				public void onItemSelected(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					// TODO Auto-generated method stub
					year =""+arg0.getSelectedItem();
					cutyear=year.replace('-', '_');
					
					drawGraph("SELECT unit,mineral,quantity"+cutyear+" as quantity,value"+cutyear+" as value FROM production where "+q+" order by mineral");
				}

				@Override
				public void onNothingSelected(AdapterView<?> arg0) {
					// TODO Auto-generated method stub
					
				}
				
			});
			
			
	}
	
	void drawGraph(String querry)
	{
		TestAdapter mDbHelper = new TestAdapter(this);        
		mDbHelper.open();
		
		
		Cursor testdata = mDbHelper.getData(querry);
		
		
		tv.setText(state+" ("+year+")");
		
		grossSal=new Double[testdata.getCount()];
		netSal=new Double[testdata.getCount()];
		datelabel=new String[testdata.getCount()];
		unit=new String[testdata.getCount()];
		str_val=new String[testdata.getCount()];
		str_quan=new String[testdata.getCount()];
		int i=0;
		//code of api to be implemented
		
		 for(testdata.moveToFirst();!testdata.isAfterLast();testdata.moveToNext(),i++)
		 {
			 grossSal[i]=(double) testdata.getDouble(2);
			 str_quan[i]=testdata.getString(2);
			 netSal[i]=(double) testdata.getDouble(3);
			 str_val[i]=testdata.getString(3);
			 datelabel[i]=testdata.getString(1);
			 unit[i]=testdata.getString(0);
			 
		 }
		
		
		
		mDbHelper.close();

		lay = (LinearLayout)findViewById(R.id.linearlay);
		listview = (HorizontalListView) findViewById(R.id.listview);
        listview.setVisibility(HorizontalListView.VISIBLE);
		List<Double> b = Arrays.asList(grossSal);
        highest = (Collections.max(b));

        netheight = new int[netSal.length];
        grossheight= new int[grossSal.length];

        updateSizeInfo();
	}
	public class bsAdapter extends BaseAdapter
	{
	    Activity cntx;
	    String[] array;
	    public bsAdapter(Activity context,String[] arr)
	    {
	        // TODO Auto-generated constructor stub
	        this.cntx=context;
	        this.array = arr;

	    }

	    public int getCount()
	    {
	        // TODO Auto-generated method stub
	        return array.length;
	    }

	    public Object getItem(int position)
	    {
	        // TODO Auto-generated method stub
	        return array[position];
	    }

	    public long getItemId(int position)
	    {
	        // TODO Auto-generated method stub
	        return array.length;
	    }

	    public View getView(final int position, View convertView, ViewGroup parent)
	    {
	        View row=null;
	        LayoutInflater inflater=cntx.getLayoutInflater();
	        row=inflater.inflate(R.layout.simplerow, null);
	        
	        DecimalFormat df = new DecimalFormat("#.##");
	        final TextView title	=	(TextView)row.findViewById(R.id.title);
	        TextView tvcol1	=	(TextView)row.findViewById(R.id.colortext01);
	        TextView tvcol2	=	(TextView)row.findViewById(R.id.colortext02);
	        
	        TextView gt		=	(TextView)row.findViewById(R.id.text01);
	        TextView nt		=	(TextView)row.findViewById(R.id.text02);
	        
	        tvcol1.setHeight(grossheight[position]);
	        tvcol2.setHeight(netheight[position]);
	        title.setText(datelabel[position]);
	        
	        gt.setText(df.format(grossSal[position]/1000)+" k");
	        nt.setText(df.format(netSal[position]/1000)+" k");
	        
	        title.setOnClickListener(new OnClickListener() {
				
				public void onClick(View v) {
					Toast.makeText(state_production_compare.this, "STATE: "+title.getText().toString()+"\nQuantity: "+str_quan[position]+" "+unit[position]+"\nValue: Rs."+str_val[position], Toast.LENGTH_SHORT).show();
				}
			});
	        
			gt.setOnClickListener(new OnClickListener() {
							
				public void onClick(View v) {
					Toast.makeText(state_production_compare.this, "STATE: "+title.getText().toString()+"\nQuantity: "+str_quan[position]+" "+unit[position]+"\nValue: Rs."+str_val[position], Toast.LENGTH_SHORT).show();
				
				}
			});
			nt.setOnClickListener(new OnClickListener() {
				
				public void onClick(View v) {
					Toast.makeText(state_production_compare.this, "STATE: "+title.getText().toString()+"\nQuantity: "+str_quan[position]+" "+unit[position]+"\nValue: Rs."+str_val[position], Toast.LENGTH_SHORT).show();
				
				}
			});
	        tvcol1.setOnClickListener(new OnClickListener() {
	        	
				public void onClick(View v) {
					Toast.makeText(state_production_compare.this, "STATE: "+title.getText().toString()+"\nQuantity: "+str_quan[position]+" "+unit[position]+"\nValue: Rs."+str_val[position], Toast.LENGTH_SHORT).show();
				}
			});
	        
	        tvcol2.setOnClickListener(new OnClickListener() {
				
				public void onClick(View v) {
					Toast.makeText(state_production_compare.this, "STATE: "+title.getText().toString()+"\nQuantity: "+str_quan[position]+" "+unit[position]+"\nValue: Rs."+str_val[position], Toast.LENGTH_SHORT).show();
				}
			});
	        
	    return row;
	    }
	}
	private void updateSizeInfo() {
		
		/** This is onWindowFocusChanged method is used to allow the chart to
		 * update the chart according to the orientation.
		 * Here h is the integer value which can be updated with the orientation
		 */
		int h;
		if(getResources().getConfiguration().orientation == 1)
		{
			h = (int) (lay.getHeight()*0.5);
			if(h == 0)
			{
				h = 200;
			}
		}
		else
		{
			h = (int) (lay.getHeight()*0.3);
			if(h == 0)
			{
				h = 130;
			}
		}
		for(int i=0;i<netSal.length;i++) 
		{
			netheight[i] = (int)((h*netSal[i])/highest);
			grossheight[i] = (int)((h*grossSal[i])/highest);
			System.out.println("net width[i] "+netheight[i]+"gross width[i] "+grossheight[i]);
		}
		listview.setAdapter(new bsAdapter(this,datelabel));
	}
	

}


