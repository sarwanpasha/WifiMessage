package upm.userinterfaces;

import android.content.Context;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;

public class Speak implements OnInitListener
{

	private TextToSpeech tts = null;
	private static Speak sp = null;
	private static boolean enabled = false;
	
	
	public static Speak getInstance(Context ctx)
	{
		if (sp==null) sp = new Speak(ctx);
		return sp;
	}
	
	private Speak(Context ctx)
	{
		tts = new TextToSpeech(ctx.getApplicationContext(), this);
	}
	
	public void shutdown()
	{
		if (tts!=null)
		{
			tts.stop();
			tts.shutdown();
		}
		sp = null;
		enabled = false;
	}
	
	public void speak(String s, boolean queue)
	{
		if (enabled && tts!=null && s!=null)
		{
			if (queue) tts.speak(s, TextToSpeech.QUEUE_ADD, null);
			else tts.speak(s, TextToSpeech.QUEUE_FLUSH, null);
		}
	}

	@Override
	public void onInit(int status)
	{
		enabled = true;
	}
}
