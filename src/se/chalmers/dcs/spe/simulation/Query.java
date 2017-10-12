package se.chalmers.dcs.spe.simulation;

import java.util.List;

import se.chalmers.dcs.spe.simulation.impl.Operator;
import se.chalmers.dcs.spe.simulation.impl.Output;
import se.chalmers.dcs.spe.simulation.impl.Source;

/**
 * Stream Processing Query Abstraction. Sources feed the operators which in turn
 * feed the outputs of the query.
 * 
 */
public interface Query {
	/**
	 * @return The sources of the query.
	 */
	List<Source> getSources();

	/**
	 * @return The operators of the query. Does not include outputs or soures.
	 */
	List<Operator> getOperators();

	/**
	 * @return The outputs of the query.
	 */
	List<Output> getOutputs();
}
