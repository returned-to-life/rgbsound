package silence.rgbsound.client.control;

import silence.rgbsound.db.Found;

import java.util.ArrayList;
import java.util.List;

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
    public double getFreqA(int a) {
        return startFreqA + (a * stepSize);
    }
    public double getFreqB(int b) {
        return startFreqB + (b * stepSize);
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
    public void NextA() {
        currentStepA += 1;
        if (currentStepA >= stepCount) currentStepA = 0;
    }
    public void NextB() {
        currentStepB += 1;
        if (currentStepB >= stepCount) currentStepB = 0;
    }
    public void goToCell(int a, int b) {
        if (a >= 0 && a < stepCount) currentStepA = a;
        if (b >= 0 && b < stepCount) currentStepB = b;
    }

    public void ToogleCheckCell() {
        if (cells.get(currentStepA).get(currentStepB) == CellStatus.CHECKED) {
            cells.get(currentStepA).set(currentStepB, CellStatus.VISITED);
        }
        else {
            cells.get(currentStepA).set(currentStepB, CellStatus.CHECKED);
        }
    }
    public List<Found> getAllChecked() {
        ArrayList<Found> result = new ArrayList<>();
        for (int a = 0; a < stepCount; a++)
            for (int b = 0; b < stepCount; b++)
            {
                if (cells.get(a).get(b) == CellStatus.CHECKED) {
                    Found f = new Found();
                    f.setFreqA(getFreqA(a));
                    f.setFreqB(getFreqB(b));
                    result.add(f);
                }
            }
        return result;
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
