package se.chalmers.dcs.spe.simulation.impl;

import java.util.List;

import se.chalmers.dcs.spe.simulation.ExecutionParameters;
import se.chalmers.dcs.spe.simulation.ProcessingThread;
import se.chalmers.dcs.spe.simulation.Query;
import se.chalmers.dcs.spe.simulation.Simulation;

/**
 * A simple simulation that runs threads in synchronous rounds.
 *
 */
public class SynchronousSimulation implements Simulation {

	protected final List<Operator> operators;
	protected final List<Source> sources;
	protected final List<Output> outputs;
	private final List<ProcessingThread> threads;
	private final long roundDuration;

	/**
	 * Construct.
	 * 
	 * @param roundDuration
	 *            The duration of the synchronous rounds, in milliseconds.
	 * @param executionParameters
	 *            The {@link ExecutionParameters} for this simulation instance,
	 *            which will define scheduling decisions etc.
	 * @param query
	 *            The {@link Query} that will be simulated.
	 */
	public SynchronousSimulation(long roundDuration, ExecutionParameters executionParameters, Query query) {
		this.roundDuration = roundDuration;
		this.operators = query.getOperators();
		this.sources = query.getSources();
		this.outputs = query.getOutputs();
		this.threads = executionParameters.getThreads(query, operators.size());
	}

	@Override
	public void run(long duration) {
		for (long t = 0; t < duration; t++) {
			generateSourceTuples(t);
			updateOperators(t);
			updateThreads(t);
		}
		report(duration);
	}

	private void generateSourceTuples(long t) {
		for (Source source : sources) {
			source.update(t);
			if (!source.isExecuting()) {
				source.execute(roundDuration);
			}
		}
	}

	private void updateOperators(long t) {
		for (Operator operator : operators) {
			operator.update(t);
		}
	}

	private void updateThreads(long t) {
		for (ProcessingThread thread : threads) {
			thread.update(t);
		}
	}

	/**
	 * Report the results of the simulation.
	 * 
	 * @param duration
	 *            The duration of the simulation in milliseconds.
	 */
	public void report(long duration) {
		System.out.println("************ SIMULATION RESULTS ************");
		System.out.println(String.format("************ RUNTIME = %3.2f sec ************", (duration / 1000.0)));
		System.out.println(String.format("************ ROUND = %3.2f sec ************", (roundDuration / 1000.0)));
		for (Source source : sources) {
			source.report();
		}
		for (int i = 0; i < operators.size(); i++) {
			operators.get(i).report();
		}
		for (Output output : outputs) {
			output.report();
		}
	}

}
