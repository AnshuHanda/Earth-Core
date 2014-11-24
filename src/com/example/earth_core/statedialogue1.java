package com.example.earth_core;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class statedialogue1 extends Activity{
	RadioGroup choose;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.statedialogue1);
		Button b=(Button) findViewById(R.id.button1);
		choose=(RadioGroup) findViewById(R.id.viewgroup);
		b.setOnClickListener(new OnClickListener(){
			
			
			RadioButton radiobutton;

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent;
				radiobutton=(RadioButton) findViewById(choose.getCheckedRadioButtonId());
				String s=""+radiobutton.getText();
				if(s.equalsIgnoreCase("Mineral wise"))
				{
					intent= new Intent(statedialogue1.this,stategraph.class);
				}
				else
				{
					intent= new Intent(statedialogue1.this,state_production.class);
					
				}
				
				
				startActivity(intent);
			}
			
		});
	}
}
