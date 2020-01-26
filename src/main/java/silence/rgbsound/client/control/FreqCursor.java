package silence.rgbsound.client.control;

public class FreqCursor {

    double startFreqA;
    double startFreqB;
    double stepSize;

    int stepCount;
    int currentStepA;
    int currentStepB;

    public FreqCursor(double startFreqA, double startFreqB, int stepCount, double stepSize) {
        this.startFreqA = startFreqA;
        this.startFreqB = startFreqB;
        this.stepCount = stepCount;
        this.stepSize = stepSize;
    }

    public int getStepCount() { return stepCount; }
    public double getMaxFreqA() { return startFreqA + (stepSize * stepCount); }
    public double getMinFreqA() { return startFreqA; }
    public double getMaxFreqB() { return startFreqB + (stepSize * stepCount); }
    public double getMinFreqB() { return startFreqB; }

    public double getFreqA() {
        return startFreqA + (currentStepA * stepSize);
    }
    public double getFreqB() {
        return startFreqB + (currentStepB * stepSize);
    }
}
