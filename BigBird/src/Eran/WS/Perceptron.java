package Eran.WS;

import java.util.Collections;
import java.util.Vector;

public class Perceptron {

	Vector<float[]> weights;

	Perceptron(){
		weights = new Vector<float[]>();
	}


	float[] getWeights(	Vector<Act> acts,int index){

		//		assert (acts!=null);

		Vector<float[]> x = new Vector<float[]>();
		Vector<Integer> y = new Vector<Integer>();

		for (Act a : acts){
			for (float[] rd : a.getProcessedData()){
				x.add(rd);

				int res = (acts.indexOf(a)==index)?1:-1;
				y.add(res);
			}
		}


		int numSamples =x.size();
		int sizeX=x.get(0).length;

		float[] w = new float[sizeX+1];//Weights, +1 for Bias XXX

		float alpha =1;

		//% While (Ei s.t. "y_i*<w, x_i>" < 1)
		//% w = w + yixi
		Boolean exists=true;

		while(exists){
			exists = false; // Assume this will be the last iteration over the
			// example set.


			Vector <Integer> rnd = new Vector<Integer>(numSamples);
			for (int i=0;i<numSamples;i++){
				rnd.add(i);
			}
			Collections.shuffle(rnd);
			for (int r=0;r<numSamples;r++){
				int i = rnd.get(r);

				float[] X = x.get(i);
				float a=0;
				for (int j=0;j<sizeX;j++)
					a+=X[j]*w[j];
				float f = a*y.get(i);
				//        a=(X(i,:)*w');
				//        f=Y(i)*a;

				if (f<1){

					for (int j=0;j<sizeX;j++){
						//      w = w + Y(i)*X(i,:)*alpha;
						w[j] += y.get(i)*X[j]*alpha;
					}
					exists = true;
				}

			}
		}
		
		//Normalize
		double sumSqr=0;
		for (int h=0;h<w.length;h++){
			sumSqr+=Math.pow(w[h],2);
		}
		for (int h=0;h<w.length;h++){
			w[h]/=sumSqr;
		}
		
		
		return w;
	}



	public int vote(float[] sample) {
		int wSize=weights.size();
		float[] scores = new float[wSize];
		int i=0;
		for(float[] X:weights){
			float temp=0;
			for (int j=0;j<sample.length;j++){
				temp+=X[j]*sample[j];
			}
			scores[i]=temp;
			i++;
		}
		float max=Float.NEGATIVE_INFINITY;
		int index=0;
		for (int j=0;j<wSize;j++){
			if (scores[j]>max){
				max=scores[j];
				index=j;
			}
		}
		
		return index;
	}



	public void train(Vector<Act> acts) {
		weights.clear();
		for(int i=0;i<acts.size();i++)
			weights.add(getWeights(acts,i));

	}
}