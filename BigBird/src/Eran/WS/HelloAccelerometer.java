////package Eran.WS;
////
////import android.app.Activity;
////import android.hardware.Sensor;
////import android.hardware.SensorEvent;
////import android.hardware.SensorEventListener;
////import android.hardware.SensorManager;
////import android.os.Bundle;
////import android.widget.TextView;
////
////
//public class HelloAccelerometer extends Activity{
//
//	private TextView mTxtView;
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		// TODO Auto-generated method stub
//		super.onCreate(savedInstanceState);
////		mTxtView = new TextView(this);
//		mTxtView = (TextView) findViewById(R.string.hello);
////		setContentView(mTxtView);
//		BBUtils.log("Create");
//	}
//
//
//
//	@Override
//	protected void onResume()
//	{
//		super.onResume();
//		register();
//	}
//
//
//
//	
//
//	@Override
//	protected void onStop()
//	{
//		mSensorManager.unregisterListener(mSensorListener);
//		super.onStop();
//	}
//}
//
////mSensorManager.unregisterListener(mSensorListener);
