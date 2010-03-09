package Eran.WS;

import java.util.Calendar;
import java.util.GregorianCalendar;

import android.util.Log;

public class BBUtils {

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
