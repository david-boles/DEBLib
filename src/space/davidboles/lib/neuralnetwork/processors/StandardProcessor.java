package space.davidboles.lib.neuralnetwork.processors;

public class StandardProcessor extends NetworkProcessor {
	
	@Override
	public float[] process(float[] input, float[][] coefficients) {
		float[] out = new float[coefficients.length];
		
		for(int i=0; i < out.length; i++) {
			out[i] = this.process(input, coefficients[i]);
		}
		
		return out;
	}
	
	private float process(float[] input, float[] coefficients) {
		float out = 0;
		for(int i=0; i < input.length; i++) {
			out+= (input[i]*coefficients[i])/input.length;
		}
		return sigmoid(out);
	}
	
	private float sigmoid(float input) {
		float out = input;
		out*=7.5;
		out/=Float.MAX_VALUE;
		out= (float) Math.pow(Math.E, out);
		out+=1;
		out=2/out;
		out-=1;
		return out;
	}

}
