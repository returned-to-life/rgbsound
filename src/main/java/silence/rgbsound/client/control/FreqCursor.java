package silence.rgbsound.client.control;

import java.util.ArrayList;

public class FreqCursor {

    public enum CellStatus { DEFAULT, CHECKED, VISITED }

    ArrayList<ArrayList<CellStatus>> cells;

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

        cells = new ArrayList<>();
        for (int a = 0; a < stepCount; a++) {
            ArrayList<CellStatus> row = new ArrayList<>();
            for (int b = 0; b < stepCount; b++)
                row.add(CellStatus.DEFAULT);
            cells.add(row);
        }
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
    public CellStatus getCellStatus(int a, int b) { return cells.get(a).get(b); }

    public void start() {
        currentStepA = 0;
        currentStepB = 0;
    }
    public boolean isNotEnd() {
        return (currentStepA < stepCount) && (currentStepB < stepCount);
    }
    public void next() {
        VisitCell();
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

    public void CheckCell() {
        cells.get(currentStepA).set(currentStepB, CellStatus.CHECKED);
    }
    public void VisitCell() {
        if (cells.get(currentStepA).get(currentStepB) == CellStatus.DEFAULT)
            cells.get(currentStepA).set(currentStepB, CellStatus.VISITED);
    }
    public void ClearCells() {
        for (int a = 0; a < stepCount; a++)
            for (int b = 0; b < stepCount; b++)
                cells.get(currentStepA).set(currentStepB, CellStatus.DEFAULT);
    }
    public void switchPrimaryAxis() {
        sideA_first = !sideA_first;
    }
}
