package silence.rgbsound.instrument;

public class FrameComposer {
    /*
     * tool for making sound with predefined fade-in and fade-out
     */
    static final int	frameSize = 576;

    long			frameCountIn;
    long			frameCountPeak;
    long			frameCountOut;

    public FrameComposer(int Rate, double Length) {

        long frameCount = Math.round( Rate * Length / frameSize );

        if ( Length > 1.0 ) {
            frameCountPeak = Math.round( Rate * (Length - 0.6) / frameSize );
            frameCountIn = (frameCount - frameCountPeak) / 2;
        }
        else {
            frameCountPeak = Math.round( (Rate * Length) / (3 * frameSize) );
            frameCountIn = frameCountPeak;
        }
        frameCountOut = frameCount - frameCountPeak - frameCountIn;
    }

    public long	getFrameCount() {
        return		frameCountIn + frameCountPeak + frameCountOut;
    }
    public int	getFrameSize(long frameIndex) {
        return		frameSize;
    }
    public double getAmp(long frameIndex) {
        double	res = 0;
        if (frameIndex < frameCountIn) {
            res = (Double.valueOf(frameIndex) / Double.valueOf(frameCountIn)) ;
        }
        else if (frameIndex < frameCountIn + frameCountPeak) {
            res = 1.0;
        }
        else if (frameIndex < frameCountIn + frameCountPeak + frameCountOut) {
            double fromPartStart = Double.valueOf(frameIndex - frameCountIn - frameCountPeak);
            double length = Double.valueOf(frameCountOut);
            res = ((length - fromPartStart) / length);
        }
        return res;
    }

    public static void main(String[] args) {
        FrameComposer fc = new FrameComposer(44100, 0.3);

        System.out.println("FrameComposer [frameIndex; frameAmp]");
        for (int i = 0; i < fc.getFrameCount(); i++) {
            System.out.println("[" + i + "; " + fc.getAmp(i) + "]");
        }
        System.out.println();

        FrameComposer fc2 = new FrameComposer(44100, 2.0);
        for (int i = 0; i < fc2.getFrameCount(); i++) {
            System.out.println("[" + i + "; " + fc2.getAmp(i) + "]");
        }
        System.out.println();
    }
}
