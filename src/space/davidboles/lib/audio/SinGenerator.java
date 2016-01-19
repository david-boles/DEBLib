package space.davidboles.lib.audio;

public class SinGenerator {
	
	boolean rising = true;
	double currentAngle = 0;
	double tau = (2d * Math.PI);
	
	public void setGeneratorRadian(double radians) {
		this.currentAngle = radians;
		if (this.currentAngle >= tau) this.currentAngle = 0;
		this.currentAngle = Math.sin(this.currentAngle);
	}
	
	public void setGeneratorDegrees(double degrees) {
		this.currentAngle = Math.toRadians(degrees);
		if (this.currentAngle >= tau) this.currentAngle = 0;
		this.currentAngle = Math.sin(this.currentAngle);
	}
	
	public double[] generateDToneSecs(int samplesPS, double frequency, double seconds) {
		double[] out = new double[(int) Math.floor(seconds*samplesPS)];
		double tau = (2d * Math.PI);
		double change = (tau * frequency)/samplesPS;
		//NOTE: Incomplete...
		
		for(int i = 0; i < out.length; i++) {
			this.currentAngle = this.currentAngle + change;
			if (this.currentAngle >= tau) this.currentAngle = 0;
			
			out[i] = Math.sin(this.currentAngle);
		}
		
		return out;
	}
	
	
	
	public double[] generateDToneSamples(int samplesPS, double frequency, int samples) {
		double[] out = new double[samples];
		double change = (tau * frequency)/samplesPS;
		
		for(int i = 0; i < out.length; i++) {
			this.currentAngle = this.currentAngle + change;
			if (this.currentAngle >= tau) this.currentAngle = 0;
			
			out[i] = Math.sin(this.currentAngle);
		}
		
		return out;
	}
	
}
