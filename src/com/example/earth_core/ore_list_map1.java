package com.example.earth_core;


import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.TableRow.LayoutParams;

public class ore_list_map1 extends ListActivity {
	
	EditText et;
    TextView tv;
	String s1,state_cut;
	ArrayList<String> count;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
			setContentView(R.layout.or_list_map);
		
	        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.window_title);
	        ImageButton   icon  = (ImageButton) findViewById(R.id.button);
	        icon.setOnClickListener(new OnClickListener(){

	   				@Override
	   				public void onClick(View v) {
	   					SharedPreferences settings = getSharedPreferences("MYPREFS", 0);
		     	        String mainMenuView = settings.getString("MAIN_MENU_VIEW", "");
		     	        Intent mainIntent = new Intent(ore_list_map1.this,MainActivity.class);
		               	if(mainMenuView.equalsIgnoreCase("grid view"))
		               		mainIntent = new Intent(ore_list_map1.this,MainActivity.class);
		            	else if(mainMenuView.equalsIgnoreCase("list view"))
		            		mainIntent = new Intent(ore_list_map1.this,MainActivityList.class);
		            	
		                startActivity(mainIntent);
	   					
	   				}
	   	        	
	   	        });

		
		s1= getIntent().getExtras().getString("mineral");
		tv = (TextView) findViewById(R.id.tv1);
		tv.setText(s1);
		if(s1.equalsIgnoreCase("Lead-Zinc"))
			s1="Lead_Zinc";
		
		 if(!haveNetworkConnection(this))
			{
				Toast.makeText(this,"Check Internet Settings",Toast.LENGTH_LONG).show();
			}
		 
		et=(EditText) findViewById(R.id.editText1);
		       
		
		getdata("");
		
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
					ArrayList<String> count1=new ArrayList<String>();
					count1.clear();
					setListAdapter(new ArrayAdapter<String>(ore_list_map1.this, android.R.layout.simple_list_item_1,android.R.id.text1, count1));
					int n=getdata(""+s);
					if(n==0)
					{
						tv.setText("No Records Found!!!");
					}
					else
					{
						tv.setText(getIntent().getExtras().getString("mineral").toUpperCase());
					}
				}
				
			
			
		});
		
	}
	public boolean haveNetworkConnection(Context context)
	{
	    boolean haveConnectedWifi = false;
	    boolean haveConnectedMobile = false;

	    ConnectivityManager cm = (ConnectivityManager) ore_list_map1.this.getSystemService(Context.CONNECTIVITY_SERVICE);
	    // or if function is out side of your Activity then you need context of your Activity
	    // and code will be as following
	    // (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

	    NetworkInfo[] netInfo = cm.getAllNetworkInfo();
	    for (NetworkInfo ni : netInfo)
	    {
	        if (ni.getTypeName().equalsIgnoreCase("WIFI"))
	        {
	            if (ni.isConnected())
	            {
	                haveConnectedWifi = true;
	                System.out.println("WIFI CONNECTION AVAILABLE");
	            } else
	            {
	                System.out.println("WIFI CONNECTION NOT AVAILABLE");
	            }
	        }
	        if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
	        {
	            if (ni.isConnected())
	            {
	                haveConnectedMobile = true;
	                System.out.println("MOBILE INTERNET CONNECTION AVAILABLE");
	            } else
	            {
	                System.out.println("MOBILE INTERNET CONNECTION NOT AVAILABLE");
	            }
	        }
	    }
	    return haveConnectedWifi || haveConnectedMobile;
	}
	int getdata(String state)
	{
		Cursor testdata;
		TestAdapter mDbHelper=new TestAdapter(this); ;
		mDbHelper.open();
		if(state=="")
		{
			
			testdata = mDbHelper.getData("select rowid from " +s1+" order by state");
				
		}
		else{
			
		state_cut=state;
		
		testdata = mDbHelper.getData("select rowid from " +s1+" where state like '"+state+"%' order by state");
		}
		if(testdata.getCount()==0)
		{	
			mDbHelper.close();
			return 0;
		}
		


		count=new ArrayList<String>();
		count.clear();

		for(testdata.moveToFirst();!testdata.isAfterLast();testdata.moveToNext())
	
		count.add(testdata.getString(0));


		setListAdapter(new MyAdapter(this, android.R.layout.simple_list_item_1,android.R.id.text1, count));
		
		mDbHelper.close();
        return 1;
	}
	
	private class MyAdapter extends ArrayAdapter<String>{

		ImageButton b;
		String urlstring;
		
		public MyAdapter(Context context, int resource,
				int textViewResourceId, List<String> objects) {
			super(context, resource, textViewResourceId, objects);
			// TODO Auto-generated constructor stub
		}
		
		@Override
		public View getView(int position,View convertView, ViewGroup parent){
			
			LayoutInflater inflater=(LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View row=inflater.inflate(R.layout.list_map1,parent,false);
			if(position%2==0)
				row.setBackgroundColor(Color.BLACK);
			else
				row.setBackgroundColor(Color.WHITE);
		
			
			 b=(ImageButton)row.findViewById(R.id.map12);
			
			TableLayout tl = (TableLayout) row.findViewById(R.id.maintable);
			 
			TableRow tr;
		    TextView colname,value; 
			 
			TestAdapter mDbHelper=new TestAdapter(ore_list_map1.this); 
			mDbHelper.open();
			final String rowid=count.get(position);
		
			Cursor testdata = mDbHelper.getData("select * from " +s1+" where rowid="+rowid+" order by state");
			
			for(int j=0;j<testdata.getColumnCount();j++)
			{
				if(j==3||j==4||j==5||j==6)
					continue;
				
				tr = new TableRow(ore_list_map1.this);
	            tr.setLayoutParams(new LayoutParams(
	                    LayoutParams.MATCH_PARENT,
	                    LayoutParams.MATCH_PARENT));
	 
	            /** Creating a TextView to add to the row **/
	            colname = new TextView(ore_list_map1.this);
	            colname.setText(""+testdata.getColumnName(j)+": ");
	            colname.setTextColor(Color.RED);
	            //  colname.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
	            colname.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT));
	            colname.setPadding(5, 5, 5, 5);
	            tr.addView(colname);  // Adding textView to tablerow.
	            
	            value = new TextView(ore_list_map1.this);
	            value.setText(testdata.getString(j));
	            value.setTextColor(Color.MAGENTA);
	            value.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT));
	            value.setPadding(5, 5, 5, 5);
	            //value.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
	            tr.addView(value); // Adding textView to tablerow.
	
	            // Add the TableRow to the TableLayout
	    
	            tl.addView(tr, new TableLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
			}
			  urlstring="http://maps.googleapis.com/maps/api/staticmap?zoom=8&size=150x190&markers=size:mid|color:red|"+testdata.getString(4)+","+testdata.getString(5)+"&sensor=false";
			  if(haveNetworkConnection(ore_list_map1.this))
				{
					
				
			 GetXMLTask task = new GetXMLTask();
		        // Execute the task
		        task.execute(new String[] { urlstring });
				}
		        
		        b.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent intent=new Intent(ore_list_map1.this,view_single_location.class);
					intent.putExtra("mineral", s1);
					intent.putExtra("rowid", rowid);
					
					startActivity(intent);
				}
			});
			
			mDbHelper.close();
			return row;
		}
		
		
		 private class GetXMLTask extends AsyncTask<String, Void, Bitmap> {
		        @Override
		        protected Bitmap doInBackground(String... urls) {
		            Bitmap map = null;
		            for (String url : urls) {
		                map = downloadImage(url);
		            }
		            return map;
		        }
		 
		        // Sets the Bitmap returned by doInBackground
		        @Override
		        protected void onPostExecute(Bitmap result) {
		            b.setImageBitmap(result);
		        }
		 
		        // Creates Bitmap from InputStream and returns it
		        private Bitmap downloadImage(String url) {
		            Bitmap bitmap = null;
		            InputStream stream = null;
		            BitmapFactory.Options bmOptions = new BitmapFactory.Options();
		            bmOptions.inSampleSize = 1;
		 
		            try {
		                stream = getHttpConnection(url);
		                bitmap = BitmapFactory.
		                        decodeStream(stream, null, bmOptions);
		                stream.close();
		            } catch (IOException e1) {
		                e1.printStackTrace();
		            }
		            return bitmap;
		        }
		 
		        // Makes HttpURLConnection and returns InputStream
		        private InputStream getHttpConnection(String urlString)
		                throws IOException {
		            InputStream stream = null;
		            URL url = new URL(urlString);
		            URLConnection connection = url.openConnection();
		 
		            try {
		                HttpURLConnection httpConnection = (HttpURLConnection) connection;
		                httpConnection.setRequestMethod("GET");
		                httpConnection.connect();
		 
		                if (httpConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
		                    stream = httpConnection.getInputStream();
		                }
		            } catch (Exception ex) {
		                Log.d("getting map error",""+ex);
		            }
		            return stream;
		        }
		    }
		
	}
}
