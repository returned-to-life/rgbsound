package silence.rgbsound.client.control;

public class Testset {
    int stepCount;
    double stepSize;
    double startFreqA;
    double startFreqB;
    int coverageCount;
    int foundCount;

    public int getStepCount() {
        return stepCount;
    }

    public void setStepCount(int stepCount) {
        this.stepCount = stepCount;
    }

    public double getStepSize() {
        return stepSize;
    }

    public void setStepSize(double stepSize) {
        this.stepSize = stepSize;
    }

    public double getStartFreqA() {
        return startFreqA;
    }

    public void setStartFreqA(double startFreqA) {
        this.startFreqA = startFreqA;
    }

    public double getStartFreqB() {
        return startFreqB;
    }

    public void setStartFreqB(double startFreqB) {
        this.startFreqB = startFreqB;
    }

    public int getCoverageCount() {
        return coverageCount;
    }

    public void setCoverageCount(int coverageCount) {
        this.coverageCount = coverageCount;
    }

    public int getFoundCount() {
        return foundCount;
    }

    public void setFoundCount(int foundCount) {
        this.foundCount = foundCount;
    }

    public Testset(int stepCount, double stepSize, double startFreqA, double startFreqB, int coverageCount, int foundCount) {
        this.stepCount = stepCount;
        this.stepSize = stepSize;
        this.startFreqA = startFreqA;
        this.startFreqB = startFreqB;
        this.coverageCount = coverageCount;
        this.foundCount = foundCount;
    }
}
