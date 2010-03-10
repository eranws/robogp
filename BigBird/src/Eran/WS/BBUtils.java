package Eran.WS;

import java.util.Calendar;
import java.util.GregorianCalendar;

import android.util.Log;

public class BBUtils {

//	public static final long OVERLAP_INTERVAL = 11000;
	
	protected static final long SAMPLE_INTERVAL	= 100; //Period in Milliseconds
	protected static final int SAMPLE_NUM		= 128; //number of samples
	protected static final long SECONDS =SAMPLE_NUM*SAMPLE_INTERVAL;
	
	protected static final int SAMPLE_SIZE = 9; //size of each sample (acc'xyz=3)
	protected static final long TRAIN_DELAY = 3000; //milliseconds delay to start

	public static void log(String string) {
		Log.d("BB", string);
		
	}
	public static String getDate(){
		GregorianCalendar g=(GregorianCalendar) GregorianCalendar.getInstance();

		int ms = g.get(Calendar.MILLISECOND);
		String time = 
			g.get(Calendar.HOUR)+":"+
			g.get(Calendar.MINUTE)+":"+
			g.get(Calendar.SECOND)+":"+
			ms;
		return time;
		//log(TAG, time+" "+s+"\n");
	}

}
