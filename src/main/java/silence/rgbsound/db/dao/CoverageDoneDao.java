package silence.rgbsound.db.dao;

public interface CoverageDoneDao {
    public int getCoverageMax(int mapIndex);
    public int getCellCoverageCount(int mapIndex, int stepIndexA, int stepIndexB);
}
