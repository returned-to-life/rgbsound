package silence.rgbsound.instrument;

import silence.rgbsound.chunk.Chunk;

public class Sample extends Chunk {
    /* technically just a wrap for the main use-case of frameComposer
     *  */
    long rate;
    long max_amp;

    public Sample(long Rate, double Length, long MaxAmp, Wave wm) {
        super();
        rate = Rate;
        max_amp = MaxAmp;

        FrameComposer fc = new FrameComposer((int) Rate, Length, MaxAmp);
        wm.start();

        for (int i = 0; i < fc.getFrameCount(); i++) {
            wm.tuneAmp( fc.getAmp(i) );

            for (int j = 0; j < fc.getFrameSize(i); j++) {
                append( wm.getValue() );
                wm.next();
            }
        }
    }

    public void append(Wave wm, double Length) {
        FrameComposer fc = new FrameComposer((int)rate, Length, max_amp);
        wm.start();

        for (int i = 0; i < fc.getFrameCount(); i++) {
            wm.tuneAmp( fc.getAmp(i) );

            for (int j = 0; j < fc.getFrameSize(i); j++) {
                append( wm.getValue() );
                wm.next();
            }
        }
    }
}
