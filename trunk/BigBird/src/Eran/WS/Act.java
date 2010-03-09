package Eran.WS;

import java.util.Vector;

public class Act {
	String name;
	Vector<String> stringPool;
	Vector<float[]> rawData;

	public Act(float[] sample, String value){
	BBUtils.log("act Ctor: "+value);
	name = value;
	stringPool 	= new Vector<String>();
	rawData 	= new Vector<float[]>();
	
	rawData.add(sample);
	}

	public void addSample(float[] sample) {
		rawData.add(sample);
	}
	
	public void addToStringPool(String str) {
		stringPool.add(str);
	}
	
	public String getFromStringPool() {
		int size = stringPool.size();
		int rnd = (int) Math.floor(size*Math.random());
		return stringPool.get(rnd);
	}
	
	
	
	
}
