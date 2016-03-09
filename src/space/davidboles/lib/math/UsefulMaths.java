package space.davidboles.lib.math;

import java.util.Random;

public class UsefulMaths {
	
	/**
	 * Raises a base to a power as if it were positive but reintroducing the negative. This allows you to maintain odd exponent esque curves without worrying about whether your base is negative or your exponent even.
	 * @param base The base to be raised.
	 * @param exponent The exponent the base is raised to.
	 * @return The result, with negative reintroduced.
	 */
	public static float powish(float base, float exponent) {
		boolean negative = false;
		if(base < 0) {
			negative = true;
			base *= -1;
		}
		float out = (float) Math.pow(base, exponent);
		if(negative) out *= -1;
		return out;
	}
	
	static Random random = new Random();
	public static float unitRandom() {
		float value = random.nextFloat();
		if(random.nextBoolean()) value *= -1;
		return value;
	}
	
	public static float boundInclusive(float in, float lower, float upper) {
		if(in < lower) in = lower;
		if(in > upper) in = upper;
		return in;
	}
}
