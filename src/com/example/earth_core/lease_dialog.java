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

public class lease_dialog extends Activity {
	ProgressDialog pd;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.leases_dialog);
		pd=new ProgressDialog(this);
		pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		pd.setMessage("Loading");
		pd.setCancelable(true);
		pd.setIndeterminate(true);
		Button b3= (Button) findViewById(R.id.button1);
		b3.setOnClickListener(new OnClickListener(){
			
			

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				pd.show();
				Intent intent= new Intent(lease_dialog.this,lease.class);
				
				RadioGroup select=(RadioGroup) findViewById(R.id.radioGroup1);
				RadioButton radiobutton=(RadioButton) findViewById(select.getCheckedRadioButtonId());
				String s=""+radiobutton.getText();
				if(s.equalsIgnoreCase("Mineral Wise"))
				{
					intent.putExtra("tablename", "mineralwise_leases");
				}
				else
				{
					intent.putExtra("tablename", "statewise_leases");
				}
				
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
