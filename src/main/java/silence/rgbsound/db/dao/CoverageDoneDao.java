package silence.rgbsound.db.dao;

import silence.rgbsound.client.control.MapCellCounter;
import silence.rgbsound.db.CoverageDone;

import java.util.List;

public interface CoverageDoneDao {
    public int getCoverageMax(int mapIndex);
    public int getCellCoverageCount(int mapIndex, int stepIndexA, int stepIndexB);
    public List<MapCellCounter> getAllCellsCoverageCount(int mapIndex);
    public void insertCoverageDoneWithFounds(CoverageDone coverageDone);
}
