package space.davidboles.lib.neuralnetwork.old.processors;

public class PassthroughProcessor extends NetworkProcessor {

	@Override
	public float[] process(float[] input, float[][] coefficients) {
		return input;
	}

}
