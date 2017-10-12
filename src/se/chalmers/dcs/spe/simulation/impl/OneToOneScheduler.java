package se.chalmers.dcs.spe.simulation.impl;

import java.util.List;

import se.chalmers.dcs.spe.simulation.Scheduler;

/**
 * Simple scheduler implementation that assigns each thread to one operator.
 *
 */
public class OneToOneScheduler implements Scheduler {

	private final List<Operator> operators;

	/**
	 * Construct.
	 * 
	 * @param operators
	 *            The operators that are to be scheduled.
	 */
	public OneToOneScheduler(List<Operator> operators) {
		this.operators = operators;
	}

	@Override
	public Operator getNextOperator(long threadId) {
		if (threadId > operators.size()) {
			throw new IllegalArgumentException("This scheduler cannot work with more threads than Operators!");
		}
		// FIXME: Ugly and dangerous
		return operators.get((int) threadId);
	}

}
