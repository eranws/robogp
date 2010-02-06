package Eran.WS;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class BigBird extends Activity {
   
	private OnClickListener startListener = new OnClickListener() {
	    public void onClick(View v) {
	    	startService(new Intent(BigBird.this, BigBirdService.class));
	    }
	};
	
	private OnClickListener stopListener = new OnClickListener() {
	    public void onClick(View v) {
	    	stopService(new Intent(BigBird.this, BigBirdService.class));
	    }
	};
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        
        final Button start = (Button) findViewById(R.id.Button01);


        start.setText("Start");
        start.setOnClickListener(startListener);
    }
}