package upm.userinterfaces;

import upm.domotics.LivingLab;
import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.Switch;

public class DoorsActivity extends Activity {
	
	//para DEBUG
	private static final String TAG = "Doors";
	
	private Switch sFront;
	
	private OnCheckedChangeListener occl_Front= new OnCheckedChangeListener(){
		@Override
		public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
	        if(isChecked){
	        	speak.speak("Puerta principal abierta", false);
	        	LivingLab.openFrontDoor();
	        	Log.d(TAG,"abrir front");
	        } else{
	        	speak.speak("Puerta principal cerrada", false);
	        	LivingLab.closeFrontDoor();
	        	Log.d(TAG,"cerrar front");
	        }
	    }
	};
	
	private Speak speak;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_doors);
		ActionBar actionBar = getActionBar();
	    actionBar.setDisplayHomeAsUpEnabled(true);
	    actionBar.setTitle(R.string.doors);
		
		sFront= (Switch) findViewById(R.id.sDoorsFront);
		sFront.setOnCheckedChangeListener(occl_Front);
		
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
