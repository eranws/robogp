package Eran.WS;

import java.io.Serializable;
import java.util.Vector;


public class Act implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	String name;
	private Vector<String> stringPool;
	private Vector<float[]> rawData;
	private Vector<float[]> realfft;


	public Act(float[] sample, String value){
		BBUtils.log("act Ctor: "+value);
		name = value;
		stringPool 	= new Vector<String>();
		rawData 	= new Vector<float[]>();
		realfft 	= new Vector<float[]>();

		addSample(sample);


	}

	public void addSample(float[] sample) {
		rawData.add(sample);
		realfft.add(FFT.getRealFFT(sample));
	}

	public void addToStringPool(String str) {
		stringPool.add(str);
	}

	public String getFromStringPool() {
		int size = stringPool.size();
		if (size>0){
		int rnd = (int) Math.floor(size*Math.random());
		return stringPool.get(rnd);
		}
		else return name;
	}

	public Vector<float[]> getProcessedData(){
		return realfft;
	}



}
