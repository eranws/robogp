package Eran.WS;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.Window;

public class MeasureActivityTrain extends Activity {

	private float temp[];
	private float[] trainData;

	protected static final long SAMPLE_INTERVAL	= 500; //Period in Milliseconds
	protected static final int SAMPLE_NUM		= 10; //number of samples
	protected static final int SAMPLE_SIZE = 9; //size of each sample (acc'xyz=3)
	private static final long DELAY = 3000; //milliseconds delay to start

	private SensorManager mSensorManager;

	private Timer timer;
	private Vibrator v;

	
	/**
	 * Ctor
	 */
	public void onCreate(Bundle savedInstanceState) { 
		super.onCreate(savedInstanceState);
		mSensorManager = (SensorManager) getSystemService(Activity.SENSOR_SERVICE);
		registerSensor();
		
		v = (Vibrator) getSystemService(VIBRATOR_SERVICE);

		requestWindowFeature(Window.FEATURE_PROGRESS);
		getWindow().setFeatureInt(Window.FEATURE_PROGRESS, 4200);

		trainData = new float[SAMPLE_NUM*SAMPLE_SIZE];
		temp= new float[SAMPLE_SIZE];
		timer = new Timer();

		timer.scheduleAtFixedRate(countDownTimerTask, 0, 100);
		timer.scheduleAtFixedRate(recordTimerTask, DELAY, SAMPLE_INTERVAL);
		
		
		@SuppressWarnings("unused")
		ProgressDialog dialog=ProgressDialog.show(MeasureActivityTrain.this, "Training", 
                "Starting in 5 seconds. Please wait...", true,true);
		 
			
		
	}

	private void registerSensor() {
		// Start listening to all sensors.
//		mSensorManager.registerListener(mSensorListener, mSensorManager.getSensors());
		
		mSensorManager.registerListener(sensorListener, 
				mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
				SensorManager.SENSOR_DELAY_UI);
		
		mSensorManager.registerListener(sensorListener, 
				mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD),
				SensorManager.SENSOR_DELAY_UI);
		
		mSensorManager.registerListener(sensorListener, 
				mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION),
				SensorManager.SENSOR_DELAY_UI);
		
	}

	SensorEventListener sensorListener = new SensorEventListener(){
		public void onAccuracyChanged(Sensor sensor, int accuracy) {
		}

		public void onSensorChanged(SensorEvent se) {


			switch(se.sensor.getType()){
			
			case Sensor.TYPE_ACCELEROMETER:
				temp[0] = se.values[0];		
				temp[1] = se.values[1];
				temp[2] = se.values[2];
					
			case Sensor.TYPE_MAGNETIC_FIELD:
				temp[3] = se.values[0];		
				temp[4] = se.values[1];
				temp[5] = se.values[2];

			case Sensor.TYPE_ORIENTATION:
				temp[6] = se.values[0];		
				temp[7] = se.values[1];
				temp[8] = se.values[2];

			}
		}
	};



	//Timer Task - Sample
	private static int trainCounter=0;
	TimerTask recordTimerTask=new TimerTask() {

		
		public void run() {

			if(trainCounter<SAMPLE_NUM){

				String str="";
				for (int i=0;i<SAMPLE_SIZE;i++)
					str+=temp[i]+"\t\t ";
				
				BBUtils.log(trainCounter+"/"+SAMPLE_NUM+": "+str);

				for (int j=0;j<SAMPLE_SIZE;j++){
					trainData[SAMPLE_SIZE*trainCounter+j]=temp[j];
				}
				trainCounter++;
				//				tv.setText(""+(SAMPLE_NUM-trainCounter));

			}
			else{
				recordTimerTask.cancel();
				trainCounter=0;
				timer.purge();


				v.vibrate(500);

				Intent mIntent = new Intent();
				mIntent.putExtra("",trainData);
				setResult(RESULT_OK, mIntent);
				finish();

			}
		}};
		//End Timer Task
		
		
		private static int CDCounter=0;
		TimerTask countDownTimerTask = new TimerTask() {
			@Override
			public void run() {
				if (CDCounter<50){
					if((CDCounter%10)==0){//every second
						BBUtils.log("COUNTDOWN "+CDCounter);
						if((CDCounter/10)==2){//on the 5th time
							v.vibrate(800);
						}
						else
							v.vibrate(100);
					}
					CDCounter++;

				}
				else{
					countDownTimerTask.cancel();
					CDCounter=0;
					timer.purge();
				}
			}
		};	
}

