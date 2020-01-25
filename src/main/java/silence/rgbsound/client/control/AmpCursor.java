package silence.rgbsound.client.control;

import silence.rgbsound.instrument.Wave;
import silence.rgbsound.instrument.WaveMix;

public class AmpCursor {
    long maxAmp;
    long minAmp;

    int stepCount;

    int currentStep;

    public AmpCursor(long maxAmp) {
        this.maxAmp = maxAmp;
        this.minAmp = 0;

        this.stepCount = 7;
        this.currentStep = 0;
    }

    public void start() { currentStep = 0; }
    public boolean isNotEnd() { return currentStep < stepCount; }
    public void next() { currentStep += 1; }

    public long getAmp() { return Math.round(getMaxAmp() * getAmpFactor()); }
    public int getStepCount() { return stepCount; }
    public int getCurrentStep() { return currentStep; }

    public long getMaxAmp() { return maxAmp; }
    public long getMinAmp() { return minAmp; }
    public double getAmpFactor() { return Double.valueOf(currentStep) / Double.valueOf(stepCount - 1); }
}
