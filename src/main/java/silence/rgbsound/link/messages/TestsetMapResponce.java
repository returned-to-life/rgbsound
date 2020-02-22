package silence.rgbsound.link.messages;

import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class TestsetMapResponce {

    public static class TestsetMapCell {
        double startFreqA = 0.0;
        double startFreqB = 0.0;
        int coverageCount = 0;
        int foundCount = 0;

        public void init(double startFreqA, double startFreqB, int coverageCount, int foundCount) {
            this.startFreqA = startFreqA;
            this.startFreqB = startFreqB;
            this.coverageCount = coverageCount;
            this.foundCount = foundCount;
        }

        public double getStartFreqA() {
            return startFreqA;
        }

        public double getStartFreqB() {
            return startFreqB;
        }

        public int getCoverageCount() {
            return coverageCount;
        }
        public void setCoverageCount(int count) { this.coverageCount = count; }

        public int getFoundCount() { return foundCount; }
        public void setFoundCount(int count) { this.foundCount = count; }

        public boolean isEmpty() { return (startFreqA == 0.0 && startFreqB == 0.0); }
    }

    int mapId;
    int stepWidth;
    double stepFactor;
    int coverageMax;
    int foundMax;
    int sizeAB;
    ArrayList<ArrayList<TestsetMapCell>> mapSquare;
    static TestsetMapCell emptyCell;
    public static TestsetMapCell getEmptyCell() {
        if (emptyCell == null) emptyCell = new TestsetMapCell();
        return emptyCell;
    }

    public TestsetMapResponce(int mapId, int sizeAB, int stepWidth, double stepFactor, int coverageMax, int foundMax) {
        this.mapId = mapId;
        this.sizeAB = sizeAB;
        mapSquare = new ArrayList<>(sizeAB);
        for (int a = 0; a < sizeAB; a++) {
            ArrayList<TestsetMapCell> newlist = new ArrayList<>(sizeAB - a);
            for (int b = a; b < sizeAB; b++) {
                newlist.add(new TestsetMapCell());
            }
            mapSquare.add(newlist);
        }
        this.stepWidth = stepWidth;
        this.stepFactor = stepFactor;
        this.coverageMax = coverageMax;
        this.foundMax = foundMax;
    }

    public TestsetMapCell getCell(int indexA, int indexB) {
        if (indexA > indexB || indexA >= sizeAB || indexB >= sizeAB) return getEmptyCell();
        return mapSquare.get(indexA).get(sizeAB - indexB - 1);
    }

    public void setCell(int indexA, int indexB, double startFreqA, double startFreqB, int coverageCount, int foundCount) {
        if (indexA > indexB || indexA >= sizeAB || indexB >= sizeAB) return;
        getCell(indexA, indexB).init(startFreqA, startFreqB, coverageCount, foundCount);
    }

    public void setCellCoverageCount(int indexA, int indexB, int count) {
        if (indexA > indexB || indexA >= sizeAB || indexB >= sizeAB) return;
        getCell(indexA, indexB).setCoverageCount(count);
    }
    public void setCellFoundCount(int indexA, int indexB, int count) {
        if (indexA > indexB || indexA >= sizeAB || indexB >= sizeAB) return;
        getCell(indexA, indexB).setFoundCount(count);
    }

    public int getCoverageMax() {
        return coverageMax;
    }

    public int getFoundMax() {
        return foundMax;
    }
    public int getSizeAB() {
        return sizeAB;
    }
    public int getStepWidth() { return stepWidth; }
    public double getStepFactor() { return stepFactor; }
    public int getMapId() { return mapId; }
}
