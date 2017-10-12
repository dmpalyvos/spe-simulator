package se.chalmers.dcs.spe.simulation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Abstract class representing every node in the query graph, including sources,
 * operators and outputs.
 *
 */
public abstract class StreamNode {
	private final long id;
	protected List<StreamNode> nextNodes = new ArrayList<>();
	protected long currentTime = 0;
	private long executionEndTime;
	private boolean executing = false;

	protected StreamNode(long id) {
		this.id = id;
	}

	/**
	 * @return The Id of the node. Nodes of different types can have equal IDs, but
	 *         it is better to have different IDs for nodes of the same type (e.g.
	 *         operators).
	 */
	public long getId() {
		return this.id;
	}

	/**
	 * Set the next {@link StreamNode}s of this node in the query graph.
	 * 
	 * @param next
	 *            The next nodes.
	 * @return {@code this} for chaining.
	 */
	public StreamNode setNext(StreamNode... next) {
		if (next == null) {
			throw new NullPointerException();
		}
		this.nextNodes = Arrays.asList(next);
		return this;
	}

	/**
	 * Update the execution state of this node.
	 * 
	 * @param time
	 *            The current (simulation) time.
	 */
	public void update(long time) {
		currentTime = time;
		if (time > executionEndTime) {
			executing = false;
		}
	}

	/**
	 * Simulate an execution of this {@link StreamNode} for the given duration of
	 * (simulation) time.
	 * 
	 * @param duration
	 *            The (simulation) time that this {@link StreamNode} will be
	 *            executed for.
	 * @throws IllegalArgumentException
	 *             if the node is executing some other task
	 */
	public void execute(long duration) {
		if (duration <= 0) {
			throw new IllegalArgumentException();
		}
		if (executing) {
			throw new IllegalStateException("Entity is busy.");
		}
		executing = true;
		executionEndTime = currentTime + duration;
		doExecute(duration);
	}

	/**
	 * Template method for implementing the actual execution logic for various
	 * subclasses such as operators and sources.
	 * 
	 * @param duration
	 *            The simulation time that this node will be executed for.
	 */
	protected abstract void doExecute(long duration);

	/**
	 * @return {@code true} if the node is currently executing some task.
	 */
	public boolean isExecuting() {
		return executing;
	}

	/**
	 * Add the given number of tuples to the input queue of this {@link StreamNode}.
	 * 
	 * @param tuplesNumber
	 *            The number of tuples to add to the input queue.
	 */
	public abstract void addToQueue(long tuplesNumber);
}
