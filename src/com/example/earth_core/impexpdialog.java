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


public class impexpdialog extends Activity{
	private Intent intent;
	ProgressDialog pd;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.impexpdialog);
		
		pd=new ProgressDialog(this);
		pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		pd.setMessage("Loading");
		pd.setCancelable(true);
		pd.setIndeterminate(true);
		
		Button b= (Button) findViewById(R.id.button1);
		
		b.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				pd.show();
				
				RadioGroup select=(RadioGroup) findViewById(R.id.radioGroup1);
				RadioButton radiobutton=(RadioButton) findViewById(select.getCheckedRadioButtonId());
				String s=""+radiobutton.getText();
				
				RadioGroup category=(RadioGroup) findViewById(R.id.radioGroup2);
				RadioButton radiobutton1=(RadioButton) findViewById(category.getCheckedRadioButtonId());
				String temp=""+radiobutton1.getText();
				
				
				intent= new Intent(impexpdialog.this,importexport.class);
				intent.putExtra("select",s);
				intent.putExtra("category",temp);
				
				startActivity(intent);
								
				}
		});
	
	}

	public void onResume() {
	    super.onResume();  
	    if (pd.isShowing()) {
	        pd.cancel();
	    }
	}
}
