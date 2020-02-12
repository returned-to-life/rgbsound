package silence.rgbsound.db.dao;

public interface FoundDao {
    public int getFoundMax(int mapIndex);
    public int getCellFoundCount(int mapIndex, int stepIndexA, int stepIndexB);
}
