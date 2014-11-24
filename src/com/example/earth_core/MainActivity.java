package com.example.earth_core;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageButton;

import android.widget.TextView;

public class MainActivity extends Activity{
	ProgressDialog pd;
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.main1);
	    pd=new ProgressDialog(this);
		pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		pd.setMessage("Loading");
		pd.setCancelable(true);
		pd.setIndeterminate(true);
	    GridView gridView = (GridView)findViewById(R.id.gridview);
	    gridView.setAdapter(new MyAdapter(this));
	    
	}
	

private class MyAdapter extends BaseAdapter {
    private List<Item> items = new ArrayList<Item>();
    private LayoutInflater inflater;

    public MyAdapter(Context context) {
        inflater = LayoutInflater.from(context);

        items.add(new Item("ORES AND MINERALS",       R.drawable.icon11));
        items.add(new Item("IMPORT AND EXPORT",   R.drawable.icon12));
        items.add(new Item("MINERAL PRODUCTION", R.drawable.icon13));
        items.add(new Item("LEASE",      R.drawable.icon14));
        items.add(new Item("RESERVES",     R.drawable.icon15));
        items.add(new Item("ACTIVITY REGISTRATION",      R.drawable.icon16));
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int i) {
        return items.get(i);
    }

    @Override
    public long getItemId(int i) {
        return items.get(i).drawableId;
    }

    
    private class Item {
        final String name;
        final int drawableId;

        Item(String name, int drawableId) {
            this.name = name;
            this.drawableId = drawableId;
        }
    }

	@Override
	public View getView(int i, View view, ViewGroup viewGroup) {
		// TODO Auto-generated method stub
		 View v = view;
	        ImageButton picture;
	        TextView name;
	        if(v == null) {
	            v = inflater.inflate(R.layout.grid_item, viewGroup, false);
	            v.setTag(R.id.picture, v.findViewById(R.id.picture));
	            v.setTag(R.id.text, v.findViewById(R.id.text));
	        }

	        picture = (ImageButton)v.getTag(R.id.picture);
	        name = (TextView)v.getTag(R.id.text);
	       
	        Item item = (Item)getItem(i);

	        picture.setImageResource(item.drawableId);
	        name.setText(item.name);
	        final String temp=item.name;
	        picture.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent intent=new Intent(MainActivity.this,dialogore.class);
			        if(temp.equalsIgnoreCase("ORES AND MINERALS"))
			        {
			        	intent=new Intent(MainActivity.this,dialogore.class); 
			        }
			        else
			        	if(temp.equalsIgnoreCase("IMPORT AND EXPORT"))
			        	{
			        		intent=new Intent(MainActivity.this,impexpdialog.class); 
			        	}
				     else
				       	if(temp.equalsIgnoreCase("MINERAL PRODUCTION"))
				       	{
				       		intent=new Intent(MainActivity.this,statedialogue1.class); 
				       	}
					 else
					   	if(temp.equalsIgnoreCase("LEASE"))
					   	{
					   		intent=new Intent(MainActivity.this,lease_dialog.class); 
					   	}
					 else
					   	if(temp.equalsIgnoreCase("RESERVES"))
					   	{
					   		pd.show();
					   		intent=new Intent(MainActivity.this,state_reserves.class); 	
			        	}
				    else
				    	if(temp.equalsIgnoreCase("ACTIVITY REGISTRATION"))
						{
				    		intent=new Intent(MainActivity.this,state_registration.class); 	
						}
			        startActivity(intent);
					
				}
	        	
	        });
	       
	     /*
	        
				*/
	        return v;
	}
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
			Intent intent= new Intent(MainActivity.this,aboutus.class);
			startActivity(intent);
		
		}
		if(item.getItemId()==R.id.settings)
		{
			Intent intent= new Intent(MainActivity.this,Settings.class);
			startActivity(intent);
		
		}
		return super.onOptionsItemSelected(item);
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
	public void onResume() {
	    super.onResume();  
	    if (pd.isShowing()) {
	        pd.cancel();
	    }
	
	}
}
