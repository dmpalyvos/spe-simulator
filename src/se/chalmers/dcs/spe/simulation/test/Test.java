package se.chalmers.dcs.spe.simulation.test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import se.chalmers.dcs.spe.simulation.ExecutionParameters;
import se.chalmers.dcs.spe.simulation.ProcessingThread;
import se.chalmers.dcs.spe.simulation.Query;
import se.chalmers.dcs.spe.simulation.Scheduler;
import se.chalmers.dcs.spe.simulation.Simulation;
import se.chalmers.dcs.spe.simulation.impl.OneToOneScheduler;
import se.chalmers.dcs.spe.simulation.impl.Operator;
import se.chalmers.dcs.spe.simulation.impl.Output;
import se.chalmers.dcs.spe.simulation.impl.Source;
import se.chalmers.dcs.spe.simulation.impl.SynchronousSimulation;
import se.chalmers.dcs.spe.simulation.impl.TimedProcessingThread;

/**
 * Sample test for {@link SynchronousSimulation} with a small query.
 *
 */
public class Test {

	/**
	 * Main.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		final long ROUND_DURATION = 100;
		Simulation sim = new SynchronousSimulation(ROUND_DURATION, oneToOneExecutionParams(ROUND_DURATION), getQuery());
		sim.run(1000);
	}

	/**
	 * Factory method for {@link ExecutionParameters}.
	 * 
	 * @param roundDuration
	 *            The round duration
	 * @return An instance of {@link ExecutionParameters}
	 */
	public static ExecutionParameters oneToOneExecutionParams(long roundDuration) {
		return new ExecutionParameters() {

			@Override
			public List<ProcessingThread> getThreads(Query query, int nThreads) {
				Scheduler scheduler = getScheduler(query);
				List<ProcessingThread> threads = new ArrayList<>();
				for (int i = 0; i < nThreads; i++) {
					threads.add(new TimedProcessingThread(i, roundDuration, scheduler));
				}
				return threads;
			}

			@Override
			public Scheduler getScheduler(Query query) {
				return new OneToOneScheduler(query.getOperators());
			}
		};
	}

	/**
	 * @return A sample query.
	 */
	public static Query getQuery() {
		Source s1 = new Source(1, 10);
		Source s2 = new Source(2, 20);
		Output o1 = new Output(1);
		Operator op1 = new Operator(1, 1.0, 10);
		Operator op2 = new Operator(2, 1.0, 20);
		s1.setNext(op1);
		s2.setNext(op1, op2);
		op1.setNext(op2);
		op2.setNext(o1);
		// Return query factory
		return new Query() {

			@Override
			public List<Source> getSources() {
				return Arrays.asList(s1, s2);
			}

			@Override
			public List<Output> getOutputs() {
				return Arrays.asList(o1);
			}

			@Override
			public List<Operator> getOperators() {
				return Arrays.asList(op1, op2);
			}
		};
	}
}
