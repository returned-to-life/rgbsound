package silence.rgbsound.db;

import java.io.Serializable;

public class CoverageMap implements Serializable {

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getFreqStart() {
        return freqStart;
    }

    public void setFreqStart(double freqStart) {
        this.freqStart = freqStart;
    }

    public double getFreqEnd() {
        return freqEnd;
    }

    public void setFreqEnd(double freqEnd) {
        this.freqEnd = freqEnd;
    }

    public double getStepFactor() {
        return stepFactor;
    }

    public void setStepFactor(double stepFactor) {
        this.stepFactor = stepFactor;
    }

    public int getStepWidth() {
        return stepWidth;
    }

    public void setStepWidth(int stepWidth) {
        this.stepWidth = stepWidth;
    }

    long id;
    double freqStart;
    double freqEnd;
    double stepFactor;
    int stepWidth;
}
