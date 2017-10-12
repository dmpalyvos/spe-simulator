package se.chalmers.dcs.spe.simulation;

/**
 * Interface for stream processing execution simulations.
 */
public interface Simulation {

	/**
	 * Run the simulation for the given amount of (simulaton) time, in milliseconds.
	 * 
	 * @param duration
	 */
	void run(long duration);
}
