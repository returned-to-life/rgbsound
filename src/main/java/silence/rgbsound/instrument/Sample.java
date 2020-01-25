package silence.rgbsound.instrument;

import silence.rgbsound.chunk.Chunk;
import silence.rgbsound.chunk.IChunkSeq;

public class Sample extends Chunk {
    /* technically just a wrap for the main use-case of frameComposer
     *  */
    long rate;

    public Sample(long Rate) {
        super();
        rate = Rate;
    }

    public Sample(long Rate, double Length, IChunkSeq wm) {
        super();
        rate = Rate;

        FrameComposer fc = new FrameComposer((int) Rate, Length);
        wm.start();

        for (int i = 0; i < fc.getFrameCount(); i++) {
            double ampFactor = fc.getAmp(i);

            for (int j = 0; j < fc.getFrameSize(i); j++) {
                append( Math.round(ampFactor * wm.getValue()) );
                wm.next();
            }
        }
    }

    public void append(IChunkSeq wm, double Length) {
        FrameComposer fc = new FrameComposer((int)rate, Length);
        wm.start();

        for (int i = 0; i < fc.getFrameCount(); i++) {
            double ampFactor = fc.getAmp(i);

            for (int j = 0; j < fc.getFrameSize(i); j++) {
                append( Math.round(ampFactor * wm.getValue()) );
                wm.next();
            }
        }
    }
}
