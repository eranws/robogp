package Eran.WS;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Vector;

import winterwell.jtwitter.Twitter;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class BigBird extends Activity implements OnClickListener,OnLongClickListener {

	private static final int TRAIN_REQUEST = 0;
	private static final int START_REQUEST = 1;

	//UI
	TextView tv;
	// UI Buttons
	Button	start,train,load,save,record,cancel,select,create,back,add,clear;


	//Data
	float[] sample;
	Vector<Act> acts;
	Perceptron perceptron;

	File f;
	protected int statItem;


	@Override
	public void onCreate(Bundle savedInstanceState) { 
		super.onCreate(savedInstanceState);
		init();
		
		//		vib = (Vibrator) getSystemService(VIBRATOR_SERVICE);
		acts = new Vector<Act>();
		perceptron =new	Perceptron();
		loadActs();

		Twitter twitter = new Twitter("eranws","051240051");
		//TODO NTH Sign in window

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

		//Prompt for SD Card; TODO
		File dir = new File("/sdcard/BigBird");
		if (!dir.isDirectory())
			dir.mkdir();
		//		File v = new File("/sdcard/BigBird/versions");


		load = (Button) findViewById(R.id.Button03);
		load.setOnClickListener(this);

		save = (Button) findViewById(R.id.Button04);
		save.setOnClickListener(this);

		clear = (Button) findViewById(R.id.Button11);
		clear.setOnLongClickListener(this);


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

		add = (Button) findViewById(R.id.Button10);
		add.setOnClickListener(this);

		cancel.setEnabled(false);
		select.setEnabled(false);
		create.setEnabled(false);

	}




	public void onClick(View v) {
		switch (v.getId()){

		//start
		case R.id.Button01:
			log("1");
			Intent j = new Intent(BigBird.this,MeasureActivityStart.class);
			startActivityForResult(j,START_REQUEST);

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

			loadActs();


			break;
			//save
		case R.id.Button04:
			log("4");
			log("save 0");


			if (acts!=null && acts.size()>0){


				try {
					f = new File("/sdcard/BigBird/2");

					if (!f.exists())
						f.createNewFile();
					FileOutputStream fos = new FileOutputStream(f);
					ObjectOutputStream oos = new ObjectOutputStream(fos);

					oos.writeObject(acts);

					oos.close();
					log("save success");
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

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
					perceptron.train(acts);
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
					perceptron.train(acts);

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

		case R.id.Button10:
			log("10");
			log("add");


			final AlertDialog.Builder crAlert2 = new AlertDialog.Builder(this);
			crAlert2.setTitle("Add");
			crAlert2.setMessage("Add description");

			final EditText input2 = new EditText(this);
			crAlert2.setView(input2);

			crAlert2.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int whichButton) {
					String str = input2.getText().toString();

					if (str!="" && str!=null){
						acts.get(statItem).addToStringPool(str);
					}
				}
			});
			crAlert2.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int whichButton) {
					initTrain();
				}
			});


			AlertDialog.Builder builder2 = new AlertDialog.Builder(this);
			builder2.setTitle("Pick an Act");
			String[] items2 = getActNames();
			builder2.setItems(items2, new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int item) {


					statItem=item;
					crAlert2.show();


					initTrain();
				}
			});
			AlertDialog alert2 = builder2.create();

			alert2.show();


			break;

		}//END Switch

	}

	@SuppressWarnings("unchecked")
	private void loadActs() {
		try {
			f = new File("/sdcard/BigBird/2");

			if (f.exists()){
				FileInputStream fis = new FileInputStream(f);
				ObjectInputStream ois = new ObjectInputStream(fis);

				Vector<Act> readObject = (Vector<Act>) ois.readObject();
				acts = readObject;

				ois.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		log("load success");
		
		perceptron.train(acts);

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
				log("Got it!");
				sample=extras.getFloatArray("");
				int chosenAct = perceptron.vote(sample);
				String chosenActStr = acts.get(chosenAct).getFromStringPool();
				
				log(chosenAct+chosenActStr);
				//TODO tweet
				tv.setText(chosenAct+" "+chosenActStr);
				

//				Intent j = new Intent(BigBird.this,MeasureActivityStart.class);
//				startActivityForResult(j,START_REQUEST);
			}


		}

	}



	public boolean onLongClick(View v) {
		log("Clear");
		AlertDialog.Builder crAlert = new AlertDialog.Builder(this);
		crAlert.setTitle("Achtung!");
		crAlert.setMessage("Are you sure?");

		// You can set an EditText view to get user input besides
		// which button was pressed.
		//		final EditText input = new EditText(this);
		//		crAlert.setView(input);

		crAlert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {

				acts.clear();
			}
		});
		crAlert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
			}
		});

		crAlert.show();

		return false;
	}	

}
