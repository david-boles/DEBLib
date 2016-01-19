package space.davidboles.lib.probability;

public class ProbabilityFs {
	
	public static float boundProbability(float probability) {
		float out = probability;
		
		while (out < 0 || out > 1) {
			if (out < 0) out += 1;
			if (out > 1) out -= 1;
		}
		
		return out;
	}
	
}
