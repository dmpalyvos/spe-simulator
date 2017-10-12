package se.chalmers.dcs.spe.simulation;

import se.chalmers.dcs.spe.simulation.impl.Operator;

/**
 * The entity that decides which thread runs on which operator.
 *
 */
public interface Scheduler {
	/**
	 * Get the appropriate operator to be executed by the thread with the given id.
	 * 
	 * @param threadId
	 *            The id of the thread that requests the operator.
	 * @return An operator to be executed.
	 */
	Operator getNextOperator(long threadId);
}
