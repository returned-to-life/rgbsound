package silence.rgbsound.link;

import silence.rgbsound.db.CoverageDone;
import silence.rgbsound.db.Found;
import silence.rgbsound.link.messages.TestsetMapResponce;

import java.util.List;

public interface Communicator {
    TestsetMapResponce GetTestsetMap(int mapIndex);
    void saveCoverage(CoverageDone coverage);
}
