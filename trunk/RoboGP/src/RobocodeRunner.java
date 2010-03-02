import ga.RoboGen;
import robocode.control.*;
import robocode.control.events.*;

public class RobocodeRunner {

	public static void main(String[] args) {

		RoboGen rg = new RoboGen();


		// Create the RobocodeEngine
//		RobocodeEngine engine = new RobocodeEngine(); // Run from current working directory
//		RobocodeEngine engine = new RobocodeEngine(new java.io.File("C:/Robocode")); // Run from C:/Robocode
		RobocodeEngine engine = new RobocodeEngine(new java.io.File("robocode")); // Run from C:/Robocode

		// Add our own battle listener to the RobocodeEngine 
		engine.addBattleListener(new BattleObserver());

		// Show the Robocode battle view
		engine.setVisible(false);

		// Setup the battle specification

		int numberOfRounds = 5;
		BattlefieldSpecification battlefield = new BattlefieldSpecification(800, 600);
		RobotSpecification[] selectedRobots = engine.getLocalRepository("sample.Fire,ga.RoboTemp");

		BattleSpecification battleSpec = new BattleSpecification(numberOfRounds, battlefield, selectedRobots);

		// Run our specified battle and let it run till it is over
		engine.runBattle(battleSpec, true/* wait till the battle is over */);

		// Cleanup our RobocodeEngine
		engine.close();

		// Make sure that the Java VM is shut down properly
		System.exit(0);
	}
}


/**
 * Our private battle listener for handling the battle event we are interested in.
 */
class BattleObserver extends BattleAdaptor {

	// Called when the battle is completed successfully with battle results
	public void onBattleCompleted(BattleCompletedEvent e) {
		System.out.println("-- Battle has completed --");

		// Print out the sorted results with the robot names
		System.out.println("Battle results:");
		for (robocode.BattleResults result : e.getSortedResults()) {

			System.out.println("  " + result.getTeamLeaderName() + ": " + result.getScore());
		}
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


