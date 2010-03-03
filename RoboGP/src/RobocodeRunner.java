import ga.RoboGen;
import robocode.BattleResults;
import robocode.control.*;
import robocode.control.events.*;

public class RobocodeRunner extends BattleAdaptor {

	static int population=1;
	private BattleResults[] sortedResults;
	
	public static void main(String[] args) {

		String robotString="sample.Fire";
		RoboGen[] rg = new RoboGen[population];
		for (int i = 0;i<population;i++){
			rg[i]= new RoboGen();
			robotString+=",ga."+rg[i].getName();
		}

		RobocodeEngine engine = new RobocodeEngine(new java.io.File("robocode")); // Run from .../RoboGP/robocode

		// Add our own battle listener to the RobocodeEngine 
		engine.addBattleListener(new RobocodeRunner());
		

		// Show the Robocode battle view
		engine.setVisible(true);

		// Setup the battle specification
		int numberOfRounds = 10;
		BattlefieldSpecification battlefield = new BattlefieldSpecification(800, 600);
		RobotSpecification[] selectedRobots = engine.getLocalRepository(robotString);

		BattleSpecification battleSpec = new BattleSpecification(numberOfRounds, battlefield, selectedRobots);

		// Run our specified battle and let it run till it is over
		engine.runBattle(battleSpec, true/* wait till the battle is over */);

		
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

			System.out.println("  " + result.getTeamLeaderName() + ": " + result.getScore());
		}
		sortedResults =  e.getSortedResults();
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


