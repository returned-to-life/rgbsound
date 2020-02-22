package silence.rgbsound.db.dao;

import silence.rgbsound.client.control.MapCellCounter;

import java.util.List;

public interface FoundDao {
    public int getFoundMax(int mapIndex);
    public int getCellFoundCount(int mapIndex, int stepIndexA, int stepIndexB);
    public List<MapCellCounter> getAllCellsFoundCount(int mapIndex);
}
