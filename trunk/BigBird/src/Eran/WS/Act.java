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
	
	rawData.add(sample);
	
	realfft = new Vector<float[]>();
	for(int i=0;i<BBUtils.SAMPLE_SIZE;i++){
		Complex c[]=new Complex[BBUtils.SAMPLE_NUM];
		for (int j=0;j<BBUtils.SAMPLE_NUM;j++){
			c[j]=new Complex(sample[i+j*BBUtils.SAMPLE_SIZE],0);
		}
		Complex[] fftComplex = FFT.fft(c);
		
		float[] realTemp=new float[BBUtils.SAMPLE_NUM];
		for (int j=0;j<BBUtils.SAMPLE_NUM;j++){
			realTemp[j] = (float) fftComplex[j].re();
		}
		
		realfft.add(realTemp);
		
	}
	
	
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
	
	public Vector<float[]> getProcessedData(){
		;
		return realfft;
	}
	
	
	
}
