package se.chalmers.dcs.spe.simulation;

import java.util.List;

/**
 * Parameters related to the execution of a simulation, namely scheduling
 * decisions.
 *
 */
public interface ExecutionParameters {
	/**
	 * Get the processing threads for this simulation.
	 * 
	 * @param query
	 *            The {@link Query} that the given threads will run on.
	 * @param nThreads
	 *            The number of threads
	 * @return A list of {@link ProcessingThread}s.
	 */
	List<ProcessingThread> getThreads(Query query, int nThreads);

	/**
	 * Get the appropriate scheduler for this simulation.
	 * 
	 * @param query
	 *            The query that this scheduler will run on.
	 * @return A {@link Scheduler} instance.
	 */
	Scheduler getScheduler(Query query);
}
