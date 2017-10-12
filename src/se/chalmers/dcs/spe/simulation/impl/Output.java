package se.chalmers.dcs.spe.simulation.impl;

import se.chalmers.dcs.spe.simulation.StreamNode;

/**
 * A {@link StreamNode} that represents a query output.
 */
public class Output extends StreamNode {
	private long outputTuples;

	/**
	 * Construct.
	 * 
	 * @param id
	 *            The unique id of this output.
	 */
	public Output(long id) {
		super(id);
	}

	@Override
	public StreamNode setNext(StreamNode... next) {
		throw new UnsupportedOperationException();
	}

	@Override
	protected void doExecute(long duration) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void addToQueue(long tuplesNumber) {
		if (tuplesNumber < 0) {
			throw new IllegalArgumentException();
		}
		outputTuples += tuplesNumber;
	}

	/**
	 * Report the results of the execution so far.
	 */
	public void report() {
		System.out.println(String.format("---- OUTPUT [%d] ----", getId()));
		System.out.println(String.format("Total Output = %d tuples", outputTuples));
	}
}
