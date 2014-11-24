package com.example.earth_core;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

public class dialogore extends Activity {
	ProgressDialog pd;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dialogore);
		final Spinner spinner1 = (Spinner) findViewById(R.id.spinner1);
		Button b= (Button) findViewById(R.id.button1);
		
		pd=new ProgressDialog(this);
		pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		pd.setMessage("Loading");
		pd.setCancelable(true);
		pd.setIndeterminate(true);
		
		b.setOnClickListener(new OnClickListener(){
		
		RadioGroup choose=(RadioGroup) findViewById(R.id.viewgroup);
		RadioButton radiobutton;

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				pd.show();
				Intent intent;
				radiobutton=(RadioButton) findViewById(choose.getCheckedRadioButtonId());
				String s=""+radiobutton.getText();
				if(s.equalsIgnoreCase("list view"))
				{
					intent= new Intent(dialogore.this,ore_list_map1.class);
				}
				else
				{
					intent= new Intent(dialogore.this,viewmaps.class);
				}
				
				intent.putExtra("mineral",String.valueOf(spinner1.getSelectedItem()));
				startActivity(intent);
		
			}
			
		});
		
	}
	
	@Override
	public void onResume() {
	    super.onResume();  
	    if (pd.isShowing()) {
	        pd.cancel();
	    }
	}
}
