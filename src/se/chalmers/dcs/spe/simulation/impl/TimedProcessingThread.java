package se.chalmers.dcs.spe.simulation.impl;

import se.chalmers.dcs.spe.simulation.ProcessingThread;
import se.chalmers.dcs.spe.simulation.Scheduler;

/**
 * A {@link ProcessingThread} implementation that runs on an operator for a
 * certain amount of time and then requests a new operator from the
 * {@link Scheduler}.
 *
 */
public class TimedProcessingThread implements ProcessingThread {
	private final long duration;
	private final Scheduler scheduler;
	private final long id;
	private Operator currentOperator = new Operator(-1, EmptyMetric.INSTANCE, 0, 0);

	/**
	 * Construct.
	 * 
	 * @param id
	 *            The unique id of the thread.
	 * @param duration
	 *            The time interval, in milliseconds, during which the thread will
	 *            execute. After that it will call the scheduler and request a new
	 *            operator to run on.
	 * @param scheduler
	 *            The current active {@link Scheduler}.
	 */
	public TimedProcessingThread(long id, long duration, Scheduler scheduler) {
		this.duration = duration;
		this.scheduler = scheduler;
		this.id = id;
	}

	@Override
	public void update(long time) {
		if (!currentOperator.isExecuting()) {
			currentOperator = scheduler.getNextOperator(getId());
			currentOperator.execute(duration);
		}
	}

	@Override
	public long getId() {
		return id;
	}

}
