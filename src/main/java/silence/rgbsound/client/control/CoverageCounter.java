package silence.rgbsound.client.control;

import org.springframework.stereotype.Component;

@Component
public class CoverageCounter {
    /**
     * calculation of quantity and parameters of testsets
     * within given range of Frequencies and necessary precision
     **/
    public static int countLength(double StartFreq, double EndFreq, int stepWidth, double stepFactor) {
        int result = 0;
        double curFreq = StartFreq;
        while (curFreq < EndFreq)
        {
            curFreq += curFreq * stepFactor * stepWidth;
            result++;
        }
        return result;
    }

    public static double countStepEndFreq(double StartFreq, int stepWidth, double stepFactor) {
        return StartFreq + (StartFreq * stepFactor * stepWidth);
    }

    // general properties
    double StartFreq = 20.0;
    double EndFreq = 40000.0;
    int stepWidth = 8;
    double stepFactor = 0.005;

    public void init(double StartFreq, double EndFreq, int stepWidth, double stepFactor)
    {
        this.StartFreq = StartFreq;
        this.EndFreq = EndFreq;
        this.stepWidth = stepWidth;
        this.stepFactor = stepFactor;
    }

    public int countLength() {
        int result = 0;
        double curFreq = StartFreq;
        while (curFreq < EndFreq) {
            curFreq += curFreq * stepFactor * stepWidth;
            result++;
        }
        return result;
    }

    // cursor and it's methods
    int curIndexA;
    int curIndexB;
    double curFreqA;
    double curFreqB;

    public void start() {
        curIndexA = 0;
        curIndexB = 0;
        curFreqA = StartFreq;
        curFreqB = StartFreq;
    }

    public boolean notEnd() {
        return ((curFreqA < EndFreq) && (curFreqB < EndFreq));
    }

    public void nextStep() {
        curFreqB += curFreqB * stepFactor * stepWidth;
        curIndexB += 1;

        if (curFreqB >= EndFreq) {
            curFreqA += curFreqA * stepFactor * stepWidth;
            curIndexA += 1;

            // if both cursors at the end we need to keep them for correct work of notEnd()
            if (curFreqA >= EndFreq) return;

            // if notEnd() supposed to be true clear the second cursor
            curFreqB = StartFreq;
            curIndexB = 0;
        }
    }

    public int getStepIndexA() {
        return curIndexA;
    }
    public int getStepIndexB() {
        return curIndexB;
    }
    public double getStepFreqA() {
        return curFreqA;
    }
    public double getStepFreqB() {
        return curFreqB;
    }
}
