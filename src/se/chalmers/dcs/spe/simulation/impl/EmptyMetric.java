package se.chalmers.dcs.spe.simulation.impl;

import se.chalmers.dcs.spe.simulation.OperatorMetric;

/**
 * Metric that does nothing, for simulations that a metric is not needed.
 */
enum EmptyMetric implements OperatorMetric {
	INSTANCE;

	@Override
	public void update(long time, long inputQueueSize, long processedTuples, long outputTuples) {
	}

	@Override
	public double getValue() {
		return 0;
	}

}
