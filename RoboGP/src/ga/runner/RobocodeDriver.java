package ga.runner;

import ga.RoboGen;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Collections;
import java.util.ArrayList;
import java.util.ArrayList;

import robocode.control.BattlefieldSpecification;
import robocode.control.RobocodeEngine;

public class RobocodeDriver {

	static PrintStream ps;
	static int populationSize = 100;
	private static String referenceRobot = "sample.Fire";

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {

		File f = new File ("scores.txt");
		if (!f.exists())
			f.createNewFile();
		ps = new PrintStream(f);


		RobocodeEngine engine = new RobocodeEngine(new java.io.File("robocode")); 
		// Setup the battle specification
		BattlefieldSpecification battlefield = new BattlefieldSpecification(800, 600);

		// Show/hide the Robocode battle view
		engine.setVisible(true);

		//number of rounds
		int numberOfRounds = 1;

		RobocodeRunner runner = new RobocodeRunner(engine,populationSize,numberOfRounds,battlefield,ps);

		// Add our own battle listener to the RobocodeEngine
		engine.addBattleListener(runner);

		//initialize random population
		ArrayList<RoboGen> population = runner.initRandomSeedArray();
		
		//loop here: XXX
		
		
		for (int eras=0;eras<100;eras++){
		// battle against reference robot, updates values inside RoboGen object
		runner.benchMarkArray(population, referenceRobot);
		Collections.sort(population);
		Collections.reverse(population);
		
		//NTH: watch best robot's match
		population= runner.createNextGeneration(population);
		}

		// Cleanup our RobocodeEngine
		engine.close();
		// Make sure that the Java VM is shut down properly
		System.exit(0);



	}

}
