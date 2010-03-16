package ga.runner;
import ga.RoboGen;

import java.io.PrintStream;
import java.util.List;
import java.util.ArrayList;

import robocode.control.BattleSpecification;
import robocode.control.BattlefieldSpecification;
import robocode.control.RobocodeEngine;
import robocode.control.RobotSpecification;
import robocode.control.events.BattleAdaptor;
import robocode.control.events.BattleCompletedEvent;
import robocode.control.events.BattleErrorEvent;
import robocode.control.events.BattleMessageEvent;

/**
 * @author קרשמ
 *
 */
public class RobocodeRunner extends BattleAdaptor {



	RobocodeEngine engine;
	private PrintStream ps;
	String referenceRobot;
	private int populationSize;
	private BattlefieldSpecification battlefield;
	private int numberOfRounds;

	private Integer tempValue;//score of robot written by onBattleCompleted(), read by benchMark();
	private static int Statgeneration=0;

	public RobocodeRunner(
			RobocodeEngine engine,
			int population,
			//String referenceRobot,
			int numberOfRounds,
			BattlefieldSpecification battlefield, PrintStream ps){


		this.ps=ps;

		this.engine=engine;
		//this.referenceRobot =  referenceRobot;
		this.populationSize = population;
		this.numberOfRounds=numberOfRounds;
		this.battlefield=battlefield;
	}

	public ArrayList<RoboGen> initRandomSeedArray(){
		ArrayList<RoboGen> pop = new ArrayList<RoboGen>();
		//RoboGen[] rg = new RoboGen[populationSize];
		for (int i = 0; i < populationSize; i++) {
			pop.add(new RoboGen());//seed random robots
		}
		return pop;
	}

	/**
	 * battles each robot from the input robot list
	 * returns list of robots, sorted by their score against the reference robot
	 * @param pop - robot list
	 * @param referenceRobot - reference robot
	 */
	public ArrayList<RoboGen> benchMarkArray(ArrayList<RoboGen> pop,String referenceRobot){
		this.referenceRobot =  referenceRobot;

		ArrayList<RoboGen> popSet = new ArrayList<RoboGen>(); 
		ps.println("Generation "+Statgeneration);
		Statgeneration++;
		for (RoboGen r:pop){ 

			String robotString =referenceRobot + ",ga." + r.getName();

			RobotSpecification[] selectedRobots = engine.getLocalRepository(robotString);
			BattleSpecification battleSpec = new BattleSpecification(numberOfRounds, battlefield, selectedRobots);
			// Run our specified battle and let it run till it is over
			engine.runBattle(battleSpec, true/* wait till the battle is over */);
			r.setScore(tempValue);
		}
		ps.println ();

		return popSet;

	}


	//seed random robots
	@Deprecated
	public ArrayList<RoboGen> initRandomSeed(){
		ArrayList<RoboGen> pop = new ArrayList<RoboGen>();
		for (int i = 0; i < populationSize; i++) {
			pop.add(new RoboGen()); //insert value later on
		}
		return pop;
	}


	/**
	 * battles each robot from the input robot list
	 * returns list of robots, sorted by their score against the reference robot
	 * @param rg - robot list
	 * @param referenceRobot - reference robot
	 */
	@Deprecated
	public void benchMark(ArrayList<RoboGen> pop,String referenceRobot){
		for (RoboGen r:pop){ 

			String robotString =referenceRobot + ",ga." + r.getName();

			RobotSpecification[] selectedRobots = engine.getLocalRepository(robotString);
			BattleSpecification battleSpec = new BattleSpecification(numberOfRounds, battlefield, selectedRobots);
			// Run our specified battle and let it run till it is over
			engine.runBattle(battleSpec, true/* wait till the battle is over */);

		}

	}


	// Called when the battle is completed successfully with battle results
	public void onBattleCompleted(BattleCompletedEvent e) {
		//System.out.println("-- Battle has completed --");

		// Print out the sorted results with the robot names
		//System.out.println("Battle results:");
		for (robocode.BattleResults result : e.getIndexedResults()) {

			//				ps.print(result.getTeamLeaderName()+  " " +tempValue);
			//				ps.print(tempValue+" ");

			if (!result.getTeamLeaderName().equals(referenceRobot)){
				tempValue=result.getScore();
				//			}
			}
			System.out.print(result.getScore()+"\t");
			ps.print(result.getScore()+"\t");

			//		sortedResults = e.getSortedResults();
		}
		System.out.println();
		ps.println();
	}
	// Called when the game sends out an information message during the battle
	public void onBattleMessage(BattleMessageEvent e) {
		//System.out.println("Msg> " + e.getMessage());
	}

	// Called when the game sends out an error message during the battle
	public void onBattleError(BattleErrorEvent e) {
		System.err.println("Err> " + e.getError());
	}

	/**
	 * picks two robots at random (weighted by their score: P(<Robot,rank>)=(1/2)^rank
	 * 
	 * @param scoredPop
	 * @return
	 */
	public ArrayList<RoboGen> createNextGeneration(ArrayList<RoboGen> scoredPop) {


		int NUM_OF_PARENTS=2;

		double rnd;
		int popSize=scoredPop.size();
		RoboGen[] parents=new RoboGen[NUM_OF_PARENTS];
		ArrayList<RoboGen> newGenome = new ArrayList<RoboGen>();
		//RoboGen.incStatgeneration(); XXX BUG, not recognizing new classes
		RoboGen.resetSerial();


		String newChrom;//= new char[RoboGen.CHROMOSOME_SIZE];

		//generate popSize robots
		for (int i=0;i<popSize;i++){

			// pick 2 parents, for example. (could be more...)
			for (int n=0;n<NUM_OF_PARENTS;n++){
				rnd = Math.random();

				//weighted random pick
				parents[n]=scoredPop.get(popSize-1);//if rnd is very small...
				for (int j=0;j<popSize;j++){
					if (rnd>Math.pow(0.5, j+1)){
						parents[n]=scoredPop.get(j);
						break;
					}

				}
			}
			//splice and dice

			int bitCut = (int)Math.floor(Math.random()*RoboGen.CHROMOSOME_SIZE);
			if (bitCut==0) bitCut++;
			newChrom= parents[0].getChromosome().substring(0, bitCut-1)+
			String.valueOf(Math.round(Math.random()))+
			parents[1].getChromosome().substring(bitCut, RoboGen.CHROMOSOME_SIZE);

			/*BAD BAD BAD
			for (int s=0;s<RoboGen.CHROMOSOME_SIZE;s++){
				whichParent=(int)Math.round(Math.random());// 0 or 1
				newChrom+=parents[whichParent].getChromosome().charAt(s);
			}
			 */

			//mutate - flip one bit chosen randomly TODO
			//			int mut = (int)Math.floor(Math.random()*RoboGen.CHROMOSOME_SIZE);
			//			int bit = Integer.valueOf(newChrom[mut]);
			//			newChrom[mut]=




			//add
			newGenome.add(new RoboGen(newChrom));
		}
		return newGenome;
	}
}