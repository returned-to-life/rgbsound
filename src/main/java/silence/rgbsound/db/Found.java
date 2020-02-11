package silence.rgbsound.db;

import java.io.Serializable;

public class Found implements Serializable {
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getCoverageMapId() {
        return coverageMapId;
    }

    public void setCoverageMapId(long coverageMapId) {
        this.coverageMapId = coverageMapId;
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
    long coverageMapId;
    double freqA;
    double freqB;
    boolean phaseMatters;
}
