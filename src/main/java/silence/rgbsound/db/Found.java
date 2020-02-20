package silence.rgbsound.db;

import java.io.Serializable;

public class Found implements Serializable {
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getCoverageDoneId() {
        return coverageDoneId;
    }

    public void setCoverageDoneId(long coverageDoneId) {
        this.coverageDoneId = coverageDoneId;
    }

    public double getFreqA() {
        return freqA;
    }

    public void setFreqA(double freqA) {
        this.freqA = freqA;
    }

    public double getFreqB() {
        return freqB;
    }

    public void setFreqB(double freqB) {
        this.freqB = freqB;
    }

    public boolean isPhaseMatters() {
        return phaseMatters;
    }

    public void setPhaseMatters(boolean phaseMatters) {
        this.phaseMatters = phaseMatters;
    }

    long id;
    long coverageDoneId;
    double freqA;
    double freqB;
    boolean phaseMatters;
}
