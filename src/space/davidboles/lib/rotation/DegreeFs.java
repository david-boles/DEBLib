package space.davidboles.lib.rotation;

public class DegreeFs {
	
	public static int boundDegrees(int degrees) {
		int out = degrees;
		
		while (out < 0 || out > 359) {
			if (out < 0) out += 360;
			if (out > 359) out -= 360;
		}
		
		return out;
	}
	
}
