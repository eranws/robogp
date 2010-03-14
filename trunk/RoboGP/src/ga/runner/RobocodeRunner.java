package ga.runner;
import ga.RoboGen;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;

import robocode.BattleResults;
import robocode.control.BattleSpecification;
import robocode.control.BattlefieldSpecification;
import robocode.control.RobocodeEngine;
import robocode.control.RobotSpecification;
import robocode.control.events.BattleAdaptor;
import robocode.control.events.BattleCompletedEvent;
import robocode.control.events.BattleErrorEvent;
import robocode.control.events.BattleMessageEvent;

public class RobocodeRunner extends BattleAdaptor {

	static PrintStream ps;
	static int population = 100;
	private static String referenceRobot = "sample.Fire";
	private BattleResults[] sortedResults;

	public static void main(String[] args) throws IOException {


		File f = new File ("scores");
		if (!f.exists())
			f.createNewFile();
		ps = new PrintStream(f);

		RobocodeEngine engine = new RobocodeEngine(new java.io.File("robocode")); 
		// Add our own battle listener to the RobocodeEngine
		engine.addBattleListener(new RobocodeRunner());
		// Show the Robocode battle view
		engine.setVisible(false);

		// Setup the battle specification
		int numberOfRounds = 1;
		BattlefieldSpecification battlefield = new BattlefieldSpecification(
				800, 600);


		//loop generations

		//String robotString = referenceRobot;

		RoboGen[] rg = new RoboGen[population];
		for (int i = 0; i < population; i++) {
			rg[i] = new RoboGen();
		}
		for (int i = 0; i < population; i++) {

			String robotString =referenceRobot + ",ga." + rg[i].getName();

			RobotSpecification[] selectedRobots = engine.getLocalRepository(robotString);
			BattleSpecification battleSpec = new BattleSpecification(numberOfRounds, battlefield, selectedRobots);
			// Run our specified battle and let it run till it is over
			engine.runBattle(battleSpec, true/* wait till the battle is over */);
		}





		// Cleanup our RobocodeEngine
		engine.close();
		// Make sure that the Java VM is shut down properly
		System.exit(0);
	}

	// Called when the battle is completed successfully with battle results
	public void onBattleCompleted(BattleCompletedEvent e) {
		System.out.println("-- Battle has completed --");

		// Print out the sorted results with the robot names
		System.out.println("Battle results:");
		for (robocode.BattleResults result : e.getSortedResults()) {

			if (!result.getTeamLeaderName().equals(referenceRobot))
				ps.println(result.getTeamLeaderName()+  " " +result.getScore());
		}
		sortedResults = e.getSortedResults();
	}

	// Called when the game sends out an information message during the battle
	public void onBattleMessage(BattleMessageEvent e) {
		System.out.println("Msg> " + e.getMessage());
	}

	// Called when the game sends out an error message during the battle
	public void onBattleError(BattleErrorEvent e) {
		System.err.println("Err> " + e.getError());
	}
}
