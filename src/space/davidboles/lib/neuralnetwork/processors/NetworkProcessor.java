package space.davidboles.lib.neuralnetwork.processors;

public abstract class NetworkProcessor {
	public abstract float[] process(float[] input, float[][] coefficients);
}
