package silence.rgbsound.link;

import org.springframework.stereotype.Service;
import silence.rgbsound.client.control.CoverageCounter;
import silence.rgbsound.db.CoverageDone;
import silence.rgbsound.db.Found;
import silence.rgbsound.link.messages.TestsetMapResponce;

import java.util.List;

@Service
public class CommunicatorMockRandom implements Communicator {

    CoverageCounter counter;
    public void setCoverageCounter(CoverageCounter counter) {
        this.counter = counter;
    }

    public TestsetMapResponce GetTestsetMap(int mapIndex) {
        final double StartFreq = 20.0;
        final double EndFreq = 40000.0;
        final int stepWidth = 8;
        final double stepFactor = 0.005;

        final int coverageMax = 10;
        final int foundMax = 5;

        counter.init(StartFreq, EndFreq, stepWidth, stepFactor);
        int sizeAB = counter.countLength();

        TestsetMapResponce tr =  new TestsetMapResponce(mapIndex, sizeAB, stepWidth, stepFactor, coverageMax, foundMax );

        counter.start();
        while (counter.notEnd()) {
            tr.setCell(counter.getStepIndexA(), counter.getStepIndexB(),
                    counter.getStepFreqA(), counter.getStepFreqB(),
                    (int) Math.round(Math.random() * coverageMax),
                    (int) Math.round(Math.random() * foundMax) );
            counter.nextStep();
        }

        return tr;
    }

    @Override
    public void saveCoverage(CoverageDone coverage) {

    }
}
