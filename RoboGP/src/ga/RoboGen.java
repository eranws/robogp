package ga;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;

/**
 * @author eran weissenstern
 * 
 * RoboGen - a class represents a robot that created by a given string
 * 	with N bits length.  
 *
 */
public class RoboGen {

	private static final int FLAGS = 2;

	private static final int COMMANDS = 3;	//3bits = 8 Commands.
	private static final int EVENTS = 6;	//run, onHit, onScanned, etc.
	private static final int ACCURACY = 4;	//4bits = 16 => angle accuracy = 180/16 =~12deg

	private static final int COMMANDS_PER_EVENT = 4;

	private static final int CHROMOSOME_SIZE = FLAGS+EVENTS*(COMMANDS+ACCURACY)*COMMANDS_PER_EVENT;


	private static int statserial=0;		//update each robot creation
	private static int statgeneration=0;	//update when creating new generations

	private int serial;		//update each robot creation
	private int generation;	//update when creating new generations

	private String chromosome; 	//the string that creates the java file

	private String name;


	/**
	 * Default Ctor. generates random seed.
	 */
	public RoboGen(){
		this(null);
	}

	/**
	 * @return a N length of 1's and 0's
	 */
	private String getSeed(){
		String seed="";
		for (int j=0;j<CHROMOSOME_SIZE;j++)
			seed+= Math.round(Math.random());	
		return seed;

	}

	public RoboGen(String str){
		if (str==null)
			chromosome = getSeed();
		else
			chromosome = (str);

		makeRobot();


		serial=statserial++; //set and update serial
		generation=statgeneration;


	}

	public static int getStatgeneration() {
		return statgeneration;
	}
	public static void setStatgeneration(int statgen) {
		RoboGen.statgeneration = statgen;
	}
	public int getGeneration() {
		return generation;
	}
	/**
	 * @return the chromosome
	 */
	public String getChromosome() {
		return chromosome;
	}
	/**
	 * @return the serial
	 */
	public int getSerial() {
		return serial;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}


	private  StringBuilder sb;
	private  File file;
	private  FileWriter fw;

	public void makeRobot(){

		System.out.println(Boolean.valueOf(""+chromosome.charAt(0))); //XXX
		System.out.println(Boolean.valueOf(""+chromosome.charAt(1)));
		
		Integer command, param;
		
		for (int i=0;i<EVENTS;i++){
			for (int j=0;j<COMMANDS_PER_EVENT;j++){
			
			 command = Integer.valueOf(chromosome.substring(2+j*(COMMANDS+ACCURACY), 5+j*(COMMANDS+ACCURACY)));
			 param = Integer.valueOf(chromosome.substring(5+j*(COMMANDS+ACCURACY), 12+j*(COMMANDS+ACCURACY)));
			
			}
		}
		
		System.out.println(chromosome.charAt(1));
		System.out.println(chromosome.charAt(2));
		System.out.println(chromosome.charAt(3));
		

		//Create file
		name = String.valueOf(generation)+String.valueOf(serial);
		file = new File(Constants.path+name);
		try {				
		file.createNewFile();
		fw = new FileWriter(file);



			sb = new StringBuilder();
			//convert string to commands
			//		sb.insert(offset, b);
			fw.write(sb.toString());




			fw.flush();
			fw.close();
			compile();
		} catch (IOException e) {e.printStackTrace();}
	}

	private void compile()
	{
		//		String fileToCompile = file.getAbsolutePath();	//Constants.path+"RoboTemp.java";//XXX
		String fileToCompile = Constants.path+name;
		JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
		int compilationResult =	compiler.run(null, null, null, fileToCompile);
		if(compilationResult == 0){
			System.out.println("Compilation is successful");//XXX BREAK?
		}else{
			System.err.println("Compilation Failed");
		}

	}

}


