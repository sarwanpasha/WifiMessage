package upm.userinterfaces;

import upm.domotics.LivingLab;
import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

public class LightsActivity extends Activity {
	
	//para DEBUG
	private static final String TAG = "Lights";
	private Speak speak;
	private Button submit;
    private EditText name,email,ContactNumber,Designation,TravllingDestination,city,country,IDCard;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_lights);
		ActionBar actionBar = getActionBar();
	    actionBar.setDisplayHomeAsUpEnabled(true);
	    actionBar.setTitle(R.string.lights);

        name=(EditText)findViewById(R.id.etname);
        email=(EditText)findViewById(R.id.etemail);
        ContactNumber=(EditText)findViewById(R.id.etcontactnumber);
        Designation=(EditText)findViewById(R.id.etdesignation);
        TravllingDestination=(EditText)findViewById(R.id.ettravellingdesignation);
        city=(EditText)findViewById(R.id.etcity);
        country=(EditText)findViewById(R.id.etcountry);
        IDCard=(EditText)findViewById(R.id.etidcard);

        submit=(Button) findViewById(R.id.btnsubmit);
		submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(name.getText().toString().equals("")||email.getText().toString().equals("")||ContactNumber.getText().toString().equals("")||
                        Designation.getText().toString().equals("")||
                        TravllingDestination.getText().toString().equals("")||city.getText().toString().equals("")||
                        country.getText().toString().equals("")||IDCard.getText().toString().equals(""))
                {

                    if (TextUtils.isEmpty(name.getText().toString())) {
                        name.setError("Please Fill Name Field");
                        return;
                    }else if (TextUtils.isEmpty(email.getText().toString())) {
                        email.setError("Please Fill Email Field");
                        return;
                    }else if (TextUtils.isEmpty(ContactNumber.getText().toString())) {
                        ContactNumber.setError("Please Fill Number Field");
                        return;
                    }else if (TextUtils.isEmpty(Designation.getText().toString())) {
                        Designation.setError("Please Fill Designation Field");
                        return;
                    }else if (TextUtils.isEmpty(TravllingDestination.getText().toString())) {
                        TravllingDestination.setError("Please Fill Designation Field");
                        return;
                    }else if (TextUtils.isEmpty(city.getText().toString())) {
                        city.setError("Please Fill City Field");
                        return;
                    }else if (TextUtils.isEmpty(country.getText().toString())) {
                        country.setError("Please Fill country Field");
                        return;
                    }else if (TextUtils.isEmpty(IDCard.getText().toString())) {
                        IDCard.setError("Please Fill IDCard Field");
                        return;
                    }
                  //  Toast.makeText(getApplicationContext(),"Please fill all fields",Toast.LENGTH_LONG).show();
                }

                else{

                }
            }
        });
		speak = Speak.getInstance(this);
	}
	
	public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId()) {
	        case android.R.id.home:
	        	finish(); 
		        return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}
	

}
