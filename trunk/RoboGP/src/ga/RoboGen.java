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

		serial=statserial++; //set and update serial
		generation=statgeneration;
		
		makeRobot();




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

	private String filename;

	public void makeRobot(){
		//Create file
		name = "Robo"+String.valueOf(generation)+"_"+String.valueOf(serial);
		filename = Constants.path+name+".java";
		file = new File(filename);
		try {				
			file.createNewFile();
			fw = new FileWriter(file);

			String[] parts= new String[]{
					"package ga;\nimport robocode.*;\nimport java.awt.Color;\npublic class "+name+" extends Robot{\nprivate static final boolean GUN_ROBOT = ",
					";\n private static final boolean RADAR_ROBOT = ",
					";\npublic void run(){\nsetAdjustGunForRobotTurn(GUN_ROBOT);\nsetAdjustRadarForRobotTurn(RADAR_ROBOT);\nwhile(true){\n",
					"\n}\n}\npublic void onScannedRobot(ScannedRobotEvent e) {\n",
					"\n}public void onHitByBullet(HitByBulletEvent e) {\n",
					"\n}public void onBulletHit(BulletHitEvent event){\n",
					"\n}public void	onHitRobot(HitRobotEvent event){\n",
					"\n}public void onHitWall(HitWallEvent event){\n",
					"\n}}"
			};
			
			
			fw.write(parts[0]);
			fw.write(String.valueOf(Boolean.valueOf(""+chromosome.charAt(0))));
			fw.write(parts[1]);
			fw.write(String.valueOf(Boolean.valueOf(""+chromosome.charAt(1))));
			fw.write(parts[2]);

			int command, param;

			for (int i=0;i<EVENTS;i++){
				for (int j=0;j<COMMANDS_PER_EVENT;j++){
					command = Integer.parseInt(chromosome.substring(FLAGS+j*(COMMANDS+ACCURACY)+(COMMANDS+ACCURACY)*i, FLAGS+COMMANDS+j*(COMMANDS+ACCURACY)+(COMMANDS+ACCURACY)*i),2);
					param = Integer.parseInt(chromosome.substring(FLAGS+COMMANDS+j*(COMMANDS+ACCURACY)+(COMMANDS+ACCURACY)*i, FLAGS+(j+1)*(COMMANDS+ACCURACY)+(COMMANDS+ACCURACY)*i),2);
					fw.write(getCommandLine(command,param));
					
				}
				fw.write(parts[i+3]);
			}
			
			fw.close();
			compile();
		} catch (IOException e) {e.printStackTrace();}
	}


	private void compile()
	{
		JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
		int compilationResult =	compiler.run(null, null, null, filename);
		if(compilationResult == 0){
			System.out.println("Compilation is successful");//XXX BREAK?
		}else{
			System.err.println("Compilation Failed");
		}

	}


	private String getCommandLine(int command, int param) {

		String s=actions[(command)]+getParam(command,param)+");";
		return s;
		

	}
	private double getParam(int action, int value){
		return rangeMin[action]+(rangeMax[action]-rangeMin[action])*(value/Math.pow(2,ACCURACY));
	}

	final double[] rangeMin = {-100,0.1,0,0,0,0,0,0};
	final double[] rangeMax = {100,3,180,180,180,180,180,180};

	final String[] actions= new String[]{
			"ahead(", //distance
			"fire(", //power
			"turnGunLeft(", //degree
			"turnLeft(",
			"turnRadarLeft(",
			"turnGunRight(", 
			"turnRight(",
			"turnRadarRight("
	};


	
}


