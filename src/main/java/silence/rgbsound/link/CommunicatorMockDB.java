package silence.rgbsound.link;

import org.springframework.stereotype.Service;
import silence.rgbsound.client.control.CoverageCounter;
import silence.rgbsound.db.CoverageDone;
import silence.rgbsound.db.CoverageMap;
import silence.rgbsound.db.Found;
import silence.rgbsound.db.dao.CoverageDoneDao;
import silence.rgbsound.db.dao.CoverageMapDao;
import silence.rgbsound.db.dao.FoundDao;
import silence.rgbsound.link.messages.TestsetMapResponce;

import java.util.List;

@Service
public class CommunicatorMockDB implements Communicator {

    CoverageCounter counter;
    public void setCoverageCounter(CoverageCounter counter) {
        this.counter = counter;
    }

    CoverageMapDao coverageMapDao;
    CoverageDoneDao coverageDoneDao;
    FoundDao foundDao;

    public void setCoverageMapDao(CoverageMapDao coverageMapDao) {
        this.coverageMapDao = coverageMapDao;
    }
    public void setCoverageDoneDao(CoverageDoneDao coverageDoneDao) {
        this.coverageDoneDao = coverageDoneDao;
    }
    public void setFoundDao(FoundDao foundDao) {
        this.foundDao = foundDao;
    }

    public TestsetMapResponce GetTestsetMap(int mapIndex) {
        CoverageMap map = coverageMapDao.getCoverageMap(mapIndex);

        counter.init(map.getFreqStart(), map.getFreqEnd(), map.getStepWidth(), map.getStepFactor());
        int sizeAB = counter.countLength();

        TestsetMapResponce tr =  new TestsetMapResponce(mapIndex, sizeAB, map.getStepWidth(), map.getStepFactor(),
                coverageDoneDao.getCoverageMax(mapIndex), foundDao.getFoundMax(mapIndex) );

        counter.start();
        while (counter.notEnd()) {
            tr.setCell(counter.getStepIndexA(), counter.getStepIndexB(),
                    counter.getStepFreqA(), counter.getStepFreqB(),
                    coverageDoneDao.getCellCoverageCount(mapIndex, counter.getStepIndexA(), counter.getStepIndexB()),
                    foundDao.getCellFoundCount(mapIndex, counter.getStepIndexA(), counter.getStepIndexB()) );
            counter.nextStep();
        }

        return tr;
    }
    public void saveCoverage(CoverageDone coverage) {
        coverageDoneDao.insertCoverageDoneWithFounds(coverage);
    }
}
