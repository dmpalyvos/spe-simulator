package se.chalmers.dcs.spe.simulation;

/**
 * Basic processing entity. An operator can do work only if it has a thread
 * assigned to it.
 *
 */
public interface ProcessingThread {
	/**
	 * Update the state of the thread. Must be called at each timer tick.
	 * 
	 * @param time
	 *            The current (simulation) time, in milliseconds.
	 */
	void update(long time);

	/**
	 * Get the unique identifier of this thread.
	 * 
	 * @return The id of the thread.
	 */
	long getId();
}
