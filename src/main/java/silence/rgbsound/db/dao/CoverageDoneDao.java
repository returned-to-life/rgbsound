package silence.rgbsound.db.dao;

import silence.rgbsound.db.CoverageDone;

public interface CoverageDoneDao {
    public int getCoverageMax(int mapIndex);
    public int getCellCoverageCount(int mapIndex, int stepIndexA, int stepIndexB);
    public void insertCoverageDoneWithFounds(CoverageDone coverageDone);
}
