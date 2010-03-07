package Eran.WS;


import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Timer;
import java.util.TimerTask;

import winterwell.jtwitter.Twitter;
import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class BigBird extends Activity {

	private static final long SAMPLE_INTERVAL	= 500; //Milliseconds
	private static final int SAMPLE_SIZE		= 10; //seconds
	private static int trainCounter= 0; //seconds
	private static int startCounter1= 0; //seconds
	private static int startCounter2= 0; //seconds
	private static int startCounter3= 0; //seconds

	// UI Buttons

	TextView tv;
	Button start;
	Button train;
	Button load;
	Button save;

	Button record;
	Button cancel;
	Button select;
	Button create;
	Button back;

	private Timer timer;
	private TimerTask startTimerTask;
	private TimerTask recordTimerTask;


	private float tempx,tempy,tempz;

	private float[][] trainData;

	private Vibrator vib;
	private OnClickListener startListener = new OnClickListener() {

		public void onClick(View v) {
			log("Start");
			start.setText("Stop");
			start.setOnClickListener(stopListener);

			timer.scheduleAtFixedRate(startTimerTask,0,SAMPLE_INTERVAL);


		}
	}; 

	private OnClickListener stopListener = new OnClickListener() {
		public void onClick(View v) {
			//		startActivity(new Intent(BigBird.this, HelloAccelerometer.class));
			//		stopService(new Intent(BigBird.this, BigBirdService.class));
			start.setText("Start");
			start.setOnClickListener(startListener);
			log("Stop");
		}
	};





	private OnClickListener trainListener = new OnClickListener() {
		public void onClick(View v) {
			log("train");
			initTrain();

		}
	};

	private OnClickListener loadListener = new OnClickListener() {
		public void onClick(View v) {
			log("load");
		}
	}; 
	private OnClickListener saveListener = new OnClickListener() {
		public void onClick(View v) {
			log("save");
		}
	}; 

	private OnClickListener recordListener = new OnClickListener() {
		private boolean flag=false;

		public void onClick(View v) {
			log("record");
			initTrain();
			recordTimerTask=new TimerTask() {
				public void run() {
					log("TTask" +String.valueOf(SAMPLE_SIZE)+" "+String.valueOf(trainCounter));

					if(trainCounter<SAMPLE_SIZE){
						//					tv.setText(String.valueOf(SAMPLE_SIZE-trainCounter));
						log(getDate()+" "+tempx+" "+tempy+" "+tempz +" "+trainCounter);
						trainCounter++;
					}
					else{
						recordTimerTask.cancel();
						trainCounter=0;
						flag=true;

						timer.purge();

					}
				}};
				timer.scheduleAtFixedRate(recordTimerTask, 0, SAMPLE_INTERVAL);
				while(!flag);
				flag=false;

				record.setEnabled(false);
				cancel.setEnabled(true);
				select.setEnabled(true);
				create.setEnabled(true);
				tv.setText("Choose an action");
		}
	}; 
	private OnClickListener cancelListener = new OnClickListener() {
		public void onClick(View v) {
			log("cancel");
		}
	}; 
	private OnClickListener selectListener = new OnClickListener() {
		public void onClick(View v) {
			log("select");
		}
	}; 
	private OnClickListener createListener = new OnClickListener() {
		public void onClick(View v) {
			log("create");
		}
	}; 
	private OnClickListener backListener = new OnClickListener() {
		public void onClick(View v) {
			log("back");
			init();
		}
	}; 


	// Accelerometer sensor
	private final SensorEventListener startSensorListener = new SensorEventListener() {

		public void onAccuracyChanged(Sensor arg0, int arg1) {
		}

		public void onSensorChanged(SensorEvent arg0) {
		}
	};


	private final SensorEventListener trainSensorListener = new SensorEventListener() {


		public void onSensorChanged(SensorEvent se)
		{
			tempx = se.values[0];
			tempy = se.values[1];
			tempz = se.values[2];


		}

		public void onAccuracyChanged(Sensor sensor, int accuracy) {}
	};

	private void register() {
		mSensorManager.registerListener(trainSensorListener, 
				mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
				SensorManager.SENSOR_DELAY_UI);
	}





	private void updateTV(float p_x, float p_y, float p_z)
	{
		tv.setText("x: "+p_x+"\ny: "+p_y+"\nz: "+p_z);
	}

	private SensorManager mSensorManager;

	@Override
	public void onCreate(Bundle savedInstanceState) { 
		super.onCreate(savedInstanceState);
		init();

		trainData = new float[3][SAMPLE_SIZE];

		vib = (Vibrator) getSystemService(VIBRATOR_SERVICE);
		timer = new Timer();


		startTimerTask =	new TimerTask() {
			public void run() {
				//log(getDate()+" "+tempx+" "+tempy+" "+tempz);
			}
		};


		recordTimerTask =	new TimerTask() {
			public void run() {
				log("TTask" +String.valueOf(SAMPLE_SIZE)+" "+String.valueOf(trainCounter));

				if(trainCounter<SAMPLE_SIZE){
					//				tv.setText(String.valueOf(SAMPLE_SIZE-trainCounter));
					log(getDate()+" "+tempx+" "+tempy+" "+tempz +" "+trainCounter);
					trainCounter++;
				}
				else{
					trainCounter=0;
					recordTimerTask.cancel();
					timer.purge();

					tv.setText("Choose an action");
					record.setEnabled(false);
					cancel.setEnabled(true);
					select.setEnabled(true);
					create.setEnabled(true);


				}
			}
		};

		mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
		register();

		Twitter twitter = new Twitter("eranws","051240051");

		// Print Alon Sagi Status
		//		System.out.println(twitter.getStatus("alonsagi"));

		// Set my status
		//		try{
		//			twitter.updateStatus("from the Small Box!!!");
		//		}
		//		catch (TwitterException e){
		//			BBUtils.log(e.getMessage());
		//		}

	}

	private void init() {
		setContentView(R.layout.main);

		tv = (TextView) findViewById(R.string.hello);

		start = (Button) findViewById(R.id.Button01);
		start.setText("Start");
		start.setOnClickListener(startListener);

		train = (Button) findViewById(R.id.Button02);
		train.setOnClickListener(trainListener);

		load = (Button) findViewById(R.id.Button03);
		load.setOnClickListener(loadListener);

		save = (Button) findViewById(R.id.Button04);
		save.setOnClickListener(saveListener);

	}

	private void log(String string) {
		BBUtils.log(string);	
	}

	protected void initTrain() {
		setContentView(R.layout.train);

		tv = (TextView) findViewById(R.string.timer);

		record = (Button) findViewById(R.id.Button05);
		record.setOnClickListener(recordListener);

		cancel = (Button) findViewById(R.id.Button06);
		cancel.setOnClickListener(cancelListener);

		select = (Button) findViewById(R.id.Button07);
		select.setOnClickListener(selectListener);

		create = (Button) findViewById(R.id.Button08);
		create.setOnClickListener(createListener);

		back = (Button) findViewById(R.id.Button09);
		back.setOnClickListener(backListener);

		cancel.setEnabled(false);
		select.setEnabled(false);
		create.setEnabled(false);

	}

	private String getDate(){
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

