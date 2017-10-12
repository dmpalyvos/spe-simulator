package se.chalmers.dcs.spe.simulation.impl;

import se.chalmers.dcs.spe.simulation.OperatorMetric;
import se.chalmers.dcs.spe.simulation.StreamNode;

/**
 * An operator is the base processing entity in the stream processing system. It
 * retrieves tuples from its input queue, does some processing and then outputs
 * some tuples to the input queues of the next StreamNodes in the query graph.
 */
public class Operator extends StreamNode {
	private final double selectivity;
	private final double throughput;
	private final OperatorMetric metric;
	private long inputQueueSize;
	private long totalOutputTuples;
	private long totalProcessedTuples;

	/**
	 * Construct.
	 * 
	 * @param id
	 *            The unique id of the operator.
	 * @param selectivity
	 *            The selectivity of the operator, meaning the fraction of
	 *            input/output tuples.
	 * @param throughput
	 *            The throughput of the operator, namely the rate of processing per
	 *            second.
	 */
	public Operator(long id, double selectivity, double throughput) {
		this(id, EmptyMetric.INSTANCE, selectivity, throughput);
	}

	/**
	 * Construct.
	 * 
	 * @param id
	 *            The unique id of the operator.
	 * @param metric
	 *            The scheduling metric
	 * @param selectivity
	 *            The selectivity of the operator, meaning the fraction of
	 *            input/output tuples.
	 * @param throughput
	 *            The throughput of the operator, namely the rate of processing per
	 *            second.
	 */
	public Operator(long id, OperatorMetric metric, double selectivity, double throughput) {
		super(id);
		this.selectivity = selectivity;
		// Divide by 1000 because times are given in milliseconds
		// but throughput in tuples/second
		this.throughput = throughput / 1000.0;
		this.metric = metric;
	}

	@Override
	protected void doExecute(long duration) {
		long processedTuples = Math.min(Math.round(duration * throughput), inputQueueSize);
		this.inputQueueSize -= processedTuples;
		long outputTuples = Math.round(processedTuples * selectivity);
		totalOutputTuples += outputTuples;
		for (StreamNode next : nextNodes) {
			next.addToQueue(outputTuples);
		}
		totalProcessedTuples += processedTuples;
		metric.update(currentTime, inputQueueSize, processedTuples, outputTuples);

	}

	@Override
	public void addToQueue(long tuplesNumber) {
		if (tuplesNumber < 0) {
			throw new IllegalArgumentException();
		}
		this.inputQueueSize += tuplesNumber;
	}

	/**
	 * @return The value of the scheduling metric.
	 */
	public double getMetricValue() {
		return metric.getValue();
	}

	/**
	 * @return The size of the input queue.
	 */
	public long getInputQueueSize() {
		return this.inputQueueSize;
	}

	/**
	 * @return The total number of tuples processed by the operator.
	 */
	public long getTotalProcessedTuples() {
		return this.totalProcessedTuples;
	}

	/**
	 * @return The total number of tuples outputed by the operator.
	 */
	public long getTotalOutputTuples() {
		return this.totalOutputTuples;
	}

	/**
	 * Print execution results.
	 */
	public void report() {
		System.out.println(String.format("---- OPERATOR [%d] ----", getId()));
		System.out.println(String.format("- Selectivity = %3.2f %%", 100 * selectivity));
		System.out.println(String.format("- Throughput = %3.2f tuples/second", 1000 * throughput));
		System.out.println(String.format("* Total Processed = %d tuples", getTotalProcessedTuples()));
		System.out.println(String.format("* Total Output = %d tuples", getTotalOutputTuples()));
		System.out.println(String.format("+ Curent Input Queue =  %d tuples", getInputQueueSize()));
	}
}
