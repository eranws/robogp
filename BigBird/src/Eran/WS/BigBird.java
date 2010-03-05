package Eran.WS;


import winterwell.jtwitter.Twitter;
import winterwell.jtwitter.TwitterException;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class BigBird extends Activity {
	Button start;

	private OnClickListener startListener = new OnClickListener() {
		public void onClick(View v) {
			startService(new Intent(BigBird.this, BigBirdService.class));
			start.setText("Stop");
			start.setOnClickListener(stopListener);


		}
	}; 

	private OnClickListener stopListener = new OnClickListener() {
		public void onClick(View v) {
			stopService(new Intent(BigBird.this, BigBirdService.class));
			start.setText("Start");
			start.setOnClickListener(startListener);
		}
	};

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);


		start = (Button) findViewById(R.id.Button01);
		start.setText("Start");
		start.setOnClickListener(startListener);
		startActivity(new Intent(BigBird.this, HelloAccelerometer.class));

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
}
