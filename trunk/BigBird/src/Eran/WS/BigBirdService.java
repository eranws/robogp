package Eran.WS;

import java.util.logging.Logger;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.widget.Toast;

public class BigBirdService extends Service{

	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public void onCreate(){
//		Toast.makeText(BigBirdService.this, "Service started!", Toast.LENGTH_LONG).show();
		BBUtils.log("Service Started");
	}
	
	public void onDestroy(){
//		Toast.makeText(BigBirdService.this, "Service started!", Toast.LENGTH_LONG).show();
		BBUtils.log("Service Stopped");
	}
	
	
	
}		
		
		// File	-	-	-	-	-	-
//		try {
//			File f = new File("/sys/class/power_supply/battery/batt_vol");
//			raf = new RandomAccessFile(f, "r");
//			File out = new File("/sdcard/eran2");
//			out.createNewFile();
//			fw = new FileWriter(out,true);
//
//
//		} catch (Exception e1) {
//			e1.printStackTrace();
//		}

		
		//Timer ----------------
//		timer.scheduleAtFixedRate(new TimerTask() {
//			public void run() {
//				if (isCharging)
//					flipPmIcon();
//			}
//		},0,BLINK_INTERVAL);
		
		
				
// Date & Time -------	-	-	-	-	-
//					GregorianCalendar g=(GregorianCalendar) GregorianCalendar.getInstance();
//
//					int ms = g.get(Calendar.MILLISECOND);
//					String time = 
//					g.get(Calendar.HOUR)+":"+
//					g.get(Calendar.MINUTE)+":"+
//					g.get(Calendar.SECOND)+":"+
//					ms;
//					log(TAG, time+" "+s+"\n");
//					fw.append(time+" "+s+"\n");
//					fw.flush();
					//					fw.write(time+" "+s+"\n");

// Handler -	-	-	-	-	-
//mHandler = new Handler() {
//
//
//	public void handleMessage(Message msg) {
//		// process incoming messages here
//
//		//Log.d("LOOP",String.valueOf(msg.what));
//		if (msg.what == 1){