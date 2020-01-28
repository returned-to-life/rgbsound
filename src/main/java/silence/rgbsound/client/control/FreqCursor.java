package silence.rgbsound.client.control;

public class FreqCursor {

    double startFreqA;
    double startFreqB;
    double stepSize;

    int stepCount;
    int currentStepA;
    int currentStepB;
    boolean sideA_first;

    public FreqCursor(double startFreqA, double startFreqB, int stepCount, double stepSize) {
        this.startFreqA = startFreqA;
        this.startFreqB = startFreqB;
        this.stepCount = stepCount;
        this.stepSize = stepSize;

        this.sideA_first = true;
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

    public int getCurrentStepA() { return currentStepA; }
    public int getCurrentStepB() { return currentStepB; }

    public void start() {
        currentStepA = 0;
        currentStepB = 0;
    }
    public boolean isNotEnd() {
        return (currentStepA < stepCount) && (currentStepB < stepCount);
    }
    public void next() {
        if (sideA_first) {
            currentStepA += 1;
            if (currentStepA >= stepCount) {
                currentStepB += 1;
                if (currentStepB < stepCount) {
                    currentStepA = 0;
                }
            }
        }
        else {
            currentStepB += 1;
            if (currentStepB >= stepCount) {
                currentStepA += 1;
                if (currentStepA < stepCount) {
                    currentStepB = 0;
                }
            }
        }
    }

}
