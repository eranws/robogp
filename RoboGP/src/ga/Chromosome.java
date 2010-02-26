package ga;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;

public class Chromosome {

	private static int statserial=0;		//update each robot creation
	private static int statgeneration=0;	//update when creating new generations
	private static StringBuilder sb;

	private static void createTempFile(){

	}


	private int serial;		//update each robot creation
	private int generation;	//update when creating new generations
	private boolean isCompiled =false;	//XXX can compare generation to current gen instead...

	private File file;
	private String data;

	private FileWriter fw;
	public Chromosome(){
		//Randomize	randStr
		//Chromosome (randStr)
	}

	public Chromosome(String str){

		//convert string to commands
//		sb.insert(offset, b);
		
		//Write to file
		file = new File(Constants.path+String.valueOf(generation)+String.valueOf(serial));
		try {
			file.createNewFile();
			fw = new FileWriter(file);
			fw.write(sb.toString());
			fw.flush();
			fw.close();
		} catch (IOException e) {e.printStackTrace();}
		serial=statserial++;
		generation=statgeneration;


	}



	private void compile()
	{
		String fileToCompile = file.getAbsolutePath();	//Constants.path+"RoboTemp.java";//XXX
		JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
		int compilationResult =	compiler.run(null, null, null, fileToCompile);
		if(compilationResult == 0){
			System.out.println("Compilation is successful");
		}else{
			System.err.println("Compilation Failed");
		}

	}

	public static int getStatgeneration() {
		return statgeneration;
	}

	public static void setStatgeneration(int statgen) {
		Chromosome.statgeneration = statgen;
	}

	public int getGeneration() {
		return generation;
	}

	public void setGeneration(int gen) {//XXX
		this.generation = gen;
	}	


}
