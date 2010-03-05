package Eran.WS;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;


public class HelloAccelerometer extends Activity{

	private TextView mTxtView;

	private SensorManager mSensorManager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
		mSensorManager.registerListener(mSensorListener, 
				mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
				SensorManager.SENSOR_DELAY_FASTEST);
		mTxtView = new TextView(this);
		setContentView(mTxtView);
	}

	public void updateTV(float p_x, float p_y, float p_z)
	{
		mTxtView.setText("x: "+p_x+"\ny: "+p_y+"\nz: "+p_z);
	}

	private final SensorEventListener mSensorListener = new SensorEventListener() {

		public void onSensorChanged(SensorEvent se)
		{
			float x = se.values[0];
			float y = se.values[1];
			float z = se.values[2];
			updateTV(x, y , z);
		}

		public void onAccuracyChanged(Sensor sensor, int accuracy) {}
	};

	@Override
	protected void onResume()
	{
		super.onResume();
		register();
	}
	
	

	private void register() {
		mSensorManager.registerListener(mSensorListener, 
				mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
				SensorManager.SENSOR_DELAY_FASTEST);
		
	}

	@Override
	protected void onStop()
	{
		mSensorManager.unregisterListener(mSensorListener);
		super.onStop();
	}
	
	
	/*
    private InputStream doPost(String urlString, String content) throws IOException {
        URL url = new URL(urlString);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        InputStream in = null;
        OutputStream out;
        byte[] buff;
        con.setRequestMethod("POST");
        con.setDoOutput(true);
        con.setDoInput(true);
        con.connect();
        out = con.getOutputStream();
        buff = content.getBytes("UTF8");
        out.write(buff);
        out.flush();
        out.close();
        in = con.getInputStream();

        return in;
    } 
    */
}