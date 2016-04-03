package upm.userinterfaces;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import upm.luggageReciever.Luggage_Main_Activity;

public class MainActivity extends Activity
{
	private Button bLights,bDoors;
	private OnClickListener ocl_Lights = new OnClickListener()
	{
		@Override
		public void onClick(View v)
		{
			//speak.speak("Luces", false);
			Intent intent = new Intent(getApplicationContext(), LightsActivity.class);
			startActivity(intent);
		}
	},
	
	ocl_Doors = new OnClickListener()
	{
		@Override
		public void onClick(View v)
		{
			//speak.speak("Puertas", false);
//			Intent intent = new Intent(getApplicationContext(), DoorsActivity.class);
			Intent intent = new Intent(getApplicationContext(), Luggage_Main_Activity.class);
			startActivity(intent);
		}
	};
	
//	private Speak speak;
	
	@Override
	protected void onCreate(Bundle bn)
	{
		super.onCreate(bn);
		setContentView(R.layout.activity_main);
		setTitle(R.string.myhome);
		
		bLights = (Button)findViewById(R.id.bMainLights);
		bLights.setOnClickListener(ocl_Lights);
		bDoors = (Button)findViewById(R.id.bMainDoors);
		bDoors.setOnClickListener(ocl_Doors);
		
	//	speak = Speak.getInstance(this);
	}
}
