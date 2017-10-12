package se.chalmers.dcs.spe.simulation.impl;

import se.chalmers.dcs.spe.simulation.StreamNode;

/**
 * A source of data (e.g. sensor) in the stream processing pipeline. Generates a
 * constant stream of tuples with the given rate.
 *
 */
public class Source extends StreamNode {

	private final double rate;
	private long totalGeneratedTuples = 0;

	/**
	 * Construct.
	 * 
	 * @param id
	 *            The unique id of the source.
	 * @param rate
	 *            The tuple generation rate.
	 */
	public Source(long id, double rate) {
		super(id);
		this.rate = rate / 1000.0;
	}

	@Override
	protected void doExecute(long duration) {
		long generatedTuples = Math.round(duration * rate);
		for (StreamNode next : nextNodes) {
			next.addToQueue(generatedTuples);
		}
		totalGeneratedTuples += generatedTuples;
	}

	@Override
	public void addToQueue(long tuplesNumber) {
		throw new UnsupportedOperationException();
	}

	/**
	 * Report execution results.
	 */
	public void report() {
		System.out.println(String.format("---- SOURCE [%d] ----", getId()));
		System.out.println(String.format("- Source Rate = %3.2f tuples/second", 1000 * rate));
		System.out.println(String.format("+ Total Generated = %d tuples", totalGeneratedTuples));
	}

}
