package silence.rgbsound.instrument;

import silence.rgbsound.chunk.IChunkRan;
import silence.rgbsound.chunk.IChunkSeq;

import java.util.ArrayList;

public class WaveMix implements IChunkSeq, IChunkRan {
    static class WeightedWave {
        public Wave		wave;
        public double	weight;
        public WeightedWave(Wave w, double p_weight) {
            wave = w;
            weight = p_weight;
        }

    }
    ArrayList<WeightedWave> wavelist;

    public WaveMix(Wave w) {
        this(w, 1.0);
    }

    public WaveMix(Wave w, double weight) {
        wavelist = new ArrayList<>();
        wavelist.add( new WeightedWave(w, weight) );
    }

    private void	clear() {
        wavelist = new ArrayList<>();
    }

    public void		mix(Wave addwave) {
        wavelist.add( new WeightedWave(addwave, 1.0) );
    }
    public void		mix(Wave addwave, double weight) {
        wavelist.add( new WeightedWave(addwave, weight) );
    }
    public void		mix(WaveMix addMix) {
        for (WeightedWave c : addMix.wavelist) {
            wavelist.add( c );
        }
    }
    public void		tuneAmp(long newAmp) {
        for (WeightedWave ww : wavelist) {
            ww.wave.tuneAmp( Math.round(ww.weight * newAmp) );
        }
    }

    public	void		start(){
        for (WeightedWave ww : wavelist) {
            ww.wave.start();
        }
    }
    public	boolean		isEnd() {
        return !( isNotEnd() );
    }
    public	boolean		isNotEnd() {
        for (WeightedWave ww : wavelist) {
            if ( ww.wave.isNotEnd() ) return true;
        }
        return false;
    }
    public	void		next() {
        for (WeightedWave ww : wavelist) {
            ww.wave.next();
        }
    }

    public	long		getValue() {
        long sumValue = 0L;
        for (WeightedWave ww : wavelist) {
            sumValue += Math.round(ww.wave.getValue() * ww.weight);
        }
        return sumValue;
    }

    public	long		getCount() {
        long maxCount = 0;
        for (WeightedWave ww : wavelist) {
            if ( ww.wave.getCount() > maxCount )
                maxCount = ww.wave.getCount();
        }
        return maxCount;
    }
    public	long	getValue(long index) {
        long sumValue = 0L;
        for (WeightedWave ww : wavelist) {
            sumValue += Math.round(ww.wave.getValue(index) * ww.weight);
        }
        return sumValue;
    }

    public static void main(String[] args) {
        WaveMix wmAdd = new WaveMix( new Wave(8800, 440.0, 20000, 0.0) );
        wmAdd.mix( new Wave(8800, 565.0, 20000, 0.0) );
        for (int i = 0; i < wmAdd.getCount(); i++)
        {
            System.out.print(" " + wmAdd.getValue(i));
        }
        System.out.println();

        WaveMix wm = new WaveMix( new Wave(8800, 705.0, 20000, 0.0) );
        wm.mix( wmAdd );

        for (int i = 0; i < wm.getCount(); i++)
        {
            System.out.print(" " + wm.getValue(i));
        }
        System.out.println();
    }
}
