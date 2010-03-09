package Eran.WS;


import java.util.Vector;

import winterwell.jtwitter.Twitter;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class BigBird extends Activity implements OnClickListener {

	private static final int TRAIN_REQUEST = 0;
	private static final int START_REQUEST = 1;

	//UI
	TextView tv;
	// UI Buttons
	Button	start,train,load,save,record,cancel,select,create,back;


	private Vibrator vib;
	//Data
	float[] sample;
	Vector<Act> acts;

	@Override
	public void onCreate(Bundle savedInstanceState) { 
		super.onCreate(savedInstanceState);
		init();

		vib = (Vibrator) getSystemService(VIBRATOR_SERVICE);
		acts = new Vector<Act>();

		Twitter twitter = new Twitter("eranws","051240051");
		/*		 Print Alon Sagi Status
				System.out.println(twitter.getStatus("alonsagi"));

		 Set my status
				try{
					twitter.updateStatus("from the Small Box!!!");
				}
				catch (TwitterException e){
					BBUtils.log(e.getMessage());
				}
		 */
	}

	private void init() {
		setContentView(R.layout.main);

		tv = (TextView) findViewById(R.string.hello);

		start = (Button) findViewById(R.id.Button01);
		start.setText("Start");
		start.setOnClickListener(this);

		train = (Button) findViewById(R.id.Button02);
		train.setOnClickListener(this);

		load = (Button) findViewById(R.id.Button03);
		load.setOnClickListener(this);

		save = (Button) findViewById(R.id.Button04);
		save.setOnClickListener(this);

	}

	private void log(String string) {
		BBUtils.log(string);	
	}

	protected void initTrain() {
		setContentView(R.layout.train);

		tv = (TextView) findViewById(R.string.timer);

		record = (Button) findViewById(R.id.Button05);
		record.setOnClickListener(this);

		cancel = (Button) findViewById(R.id.Button06);
		cancel.setOnClickListener(this);

		select = (Button) findViewById(R.id.Button07);
		select.setOnClickListener(this);

		create = (Button) findViewById(R.id.Button08);
		create.setOnClickListener(this);

		back = (Button) findViewById(R.id.Button09);
		back.setOnClickListener(this);

		cancel.setEnabled(false);
		select.setEnabled(false);
		create.setEnabled(false);

	}


	public void onClick(View v) {
		switch (v.getId()){

		//start
		case R.id.Button01:
			log("1");
			//TODO
			break;

			//train
		case R.id.Button02:
			log("2");
			log("train");
			initTrain();
			break;

			//load
		case R.id.Button03:
			log("3");
			log("load");
			//TODO load acts

			break;
			//save
		case R.id.Button04:
			log("4");
			log("save");
			//TODO save acts

			break;


			//record
		case R.id.Button05:
			log("5");
			log("record");
			Intent i = new Intent(this, MeasureActivityTrain.class);
			startActivityForResult(i, TRAIN_REQUEST);
			break;

			//cancel
		case R.id.Button06:
			log("6");
			log("cancel");
			sample=null;
			initTrain();
			break;
			
			//select
		case R.id.Button07:
			log("7");
			log("select");

			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle("Pick an Act");
			String[] items = getActNames();
			builder.setItems(items, new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int item) {
					acts.get(item).addSample(sample);
					init();
				}
			});
			AlertDialog alert = builder.create();

			alert.show();


			break;


			//create - creates new act
		case R.id.Button08:
			log("8");
			log("create");

			AlertDialog.Builder crAlert = new AlertDialog.Builder(this);
			crAlert.setTitle("New Act");
			crAlert.setMessage("insert act name");

			// You can set an EditText view to get user input besides
			// which button was pressed.
			final EditText input = new EditText(this);
			crAlert.setView(input);

			crAlert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int whichButton) {
					String value = input.getText().toString();

					if (value!="" && value!=null){
						acts.add(new Act(sample,value));
					}

					init();
				}
			});
			crAlert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int whichButton) {
					init();
				}
			});

			crAlert.show();

			break;

			//back
		case R.id.Button09:
			log("9");
			log("back");
			sample=null;
			init();
			break;
			
			//TODO acts.get(1).addToStringPool("Going upstairs");//is Bored
			
			
		}//END Switch

	}

	private String[] getActNames() {
		int actsSize = acts.size();
		String[] strArray = new String[actsSize];
		for (int i=0;i<actsSize;i++)
			strArray[i]=acts.get(i).name;
		return strArray;
	}

	protected void onActivityResult (int requestCode, int resultCode, Intent intent){
		super.onActivityResult(requestCode, resultCode, intent);
		if (intent!=null){
			Bundle extras = intent.getExtras();
			sample=extras.getFloatArray("");

			if (requestCode==TRAIN_REQUEST){
				record.setEnabled(false);

				cancel.setEnabled(true);

				if (acts.size()>0){ //none available
					select.setEnabled(true);
				}
				create.setEnabled(true);
				tv.setText("Choose an action");
			}

			if (requestCode==START_REQUEST){
				//TODO
			}

		}

	}	

}
