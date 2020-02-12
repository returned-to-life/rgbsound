package silence.rgbsound.client.control;

import silence.rgbsound.link.Communicator;
import silence.rgbsound.link.CommunicatorMockRandom;
import silence.rgbsound.link.messages.TestsetMapResponce;

public class PickTestsetController {

    Communicator communicator;
    public void setCommunicator(Communicator communicator) {
        this.communicator = communicator;
        this.mapResponse = communicator.GetTestsetMap(1);
    }

    TestsetMapResponce mapResponse;
    public void setMapResponse(TestsetMapResponce mapResponse) { this.mapResponse = mapResponse; }

    public void reloadTestsetMap() {
        this.mapResponse = communicator.GetTestsetMap(1);
    }

    private int firstShownCellIndexB = 0;
    private int firstShownCellIndexA = 0;
    private int currentCellIndexA = 0;
    private int currentCellIndexB = 0;

    public int getFirstShownCellIndexB() {
        return firstShownCellIndexB;
    }

    public void setFirstShownCellIndexB(int firstShownCellIndexB) {
        this.firstShownCellIndexB = firstShownCellIndexB;
    }

    public int getFirstShownCellIndexA() {
        return firstShownCellIndexA;
    }

    public void setFirstShownCellIndexA(int firstShownCellIndexA) {
        this.firstShownCellIndexA = firstShownCellIndexA;
    }

    public int getCurrentCellIndexA() {
        return currentCellIndexA;
    }

    public void setCurrentCellIndexA(int currentCellIndexA) {
        this.currentCellIndexA = currentCellIndexA;
    }

    public int getCurrentCellIndexB() {
        return currentCellIndexB;
    }

    public void setCurrentCellIndexB(int currentCellIndexB) {
        this.currentCellIndexB = currentCellIndexB;
    }

    public int getCoverageMax() {
        if (mapResponse == null) return 1;
        return mapResponse.getCoverageMax();
    }
    public int getFoundMax() {
        if (mapResponse == null) return 1;
        return mapResponse.getFoundMax();
    }
    public TestsetMapResponce.TestsetMapCell getCell(int a, int b) {
        if (mapResponse == null)
            return TestsetMapResponce.getEmptyCell();
        return mapResponse.getCell(firstShownCellIndexA + a, firstShownCellIndexB + b);
    }
    public double getMinFreq() {
        if (mapResponse == null) return 0;
        return mapResponse.getCell(0,0).getStartFreqA();
    }
    public double getMaxFreq() {
        if (mapResponse == null) return 0;
        return mapResponse.getCell(mapResponse.getSizeAB() - 1, mapResponse.getSizeAB() - 1).getStartFreqA();
    }
    public void adjustA(int adjustValue, int maxAdjustValue, int cellCountY) {
        if (adjustValue < 0 )
            adjustValue = 0;
        this.firstShownCellIndexA = (int) Math.round((Double.valueOf(adjustValue) / maxAdjustValue) * (mapResponse.getSizeAB() - cellCountY));
    }
    public void adjustB(int adjustValue, int cellCountX) {
        final int maxAdjustValue = 100;
        if (adjustValue > maxAdjustValue )
            adjustValue = maxAdjustValue;
        if (adjustValue < 0 )
            adjustValue = 0;
        this.firstShownCellIndexB = (int) Math.round((Double.valueOf(adjustValue) / maxAdjustValue) * (mapResponse.getSizeAB() - cellCountX));
    }
    public Testset getCurrentAsTestset() {
        TestsetMapResponce.TestsetMapCell cell = getCell(currentCellIndexA, currentCellIndexB);
        return new Testset(mapResponse.getStepWidth(), mapResponse.getStepFactor(), cell.getStartFreqA(), cell.getStartFreqB(), cell.getCoverageCount(), cell.getFoundCount());
    }
}
