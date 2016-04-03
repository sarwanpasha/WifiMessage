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

public class BlindsActivity extends Activity {
	//para DEBUG
	private static final String TAG = "Blinds";
	
	private Switch sAll,sRoom,sLivingRoom,sKitchen;
	
	private OnCheckedChangeListener occl_All = new OnCheckedChangeListener(){
		@Override
		public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
			setAllSwitches(isChecked);
	        if(isChecked){
	        	speak.speak("Todas las persianas subidas", false);
	        	LivingLab.raiseAllBlinds();
	        	Log.d(TAG,"subir todas");
	        	
	        } else{
	        	speak.speak("Todas las persianas bajadas", false);
	        	LivingLab.lowerAllBlinds();
	        	Log.d(TAG,"bajar todas");
	        }
	    }
	},
	occl_Room = new OnCheckedChangeListener(){
		@Override
		public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
			checkAllSwitches();
	        if(isChecked){
	        	speak.speak("Persiana de la habitación subida", false);
	        	LivingLab.RoomBlindRaise();
	        	Log.d(TAG,"subir room");
	        } else{
	        	speak.speak("Persiana de la habitación bajada", false);
	        	LivingLab.RoomBlindLower();
	        	Log.d(TAG,"bajar room");
	        }
	    }
	},
	occl_LivingRoom = new OnCheckedChangeListener(){
		@Override
		public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
			checkAllSwitches();
	        if(isChecked){
	        	speak.speak("Persiana del salón subida", false);
	        	LivingLab.livingRoomBlindRaise();
	        	Log.d(TAG,"subir livingroom");
	        } else{
	        	speak.speak("Persiana del salón bajada", false);
	        	LivingLab.livingRoomBlindLower();
	        	Log.d(TAG,"bajar livingroom");
	        }
	    }
	},
	occl_Kitchen = new OnCheckedChangeListener(){
		@Override
		public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
			checkAllSwitches();
	        if(isChecked){
	        	speak.speak("Persiana de la cocina subida", false);
	        	LivingLab.kitchenBlindRaise();
	        	Log.d(TAG,"subir cocina");
	        } else{
	        	speak.speak("Persiana de la cocina bajada", false);
	        	LivingLab.kitchenBlindLower();
	        	Log.d(TAG,"bajar cocina");
	        }
	    }
	};
	
	private Speak speak;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_blinds);
		ActionBar actionBar = getActionBar();
	    actionBar.setDisplayHomeAsUpEnabled(true);
	    actionBar.setTitle(R.string.blinds);
		
		sAll=(Switch) findViewById(R.id.sBlindsAll);
		sAll.setOnCheckedChangeListener(occl_All);
		sRoom=(Switch) findViewById(R.id.sBlindsRoom);
		sRoom.setOnCheckedChangeListener(occl_Room);
		sLivingRoom=(Switch) findViewById(R.id.sBlindsLivingRoom);
		sLivingRoom.setOnCheckedChangeListener(occl_LivingRoom);
		sKitchen=(Switch) findViewById(R.id.sBlindsKitchen);
		sKitchen.setOnCheckedChangeListener(occl_Kitchen);
		
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

	private void setAllSwitches(boolean checked){
		sRoom.setChecked(checked);
		sLivingRoom.setChecked(checked);
		sKitchen.setChecked(checked);
	}
	
	private void checkAllSwitches(){
		boolean[] switches={sRoom.isChecked(), sLivingRoom.isChecked(), sKitchen.isChecked()};
		
		boolean all_on=switches[0];
		for(int i=1; i<switches.length;i++){
			all_on = all_on && switches[i];
		}
		
		if(all_on){
			sAll.setChecked(true);
		} else {
			sAll.setChecked(false);
			sRoom.setChecked(switches[0]);
			sLivingRoom.setChecked(switches[1]);
			sKitchen.setChecked(switches[2]);
		}
	}
}
