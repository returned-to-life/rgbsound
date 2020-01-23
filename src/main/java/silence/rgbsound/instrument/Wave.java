package silence.rgbsound.instrument;

import silence.rgbsound.chunk.IChunkRan;
import silence.rgbsound.chunk.IChunkSeq;

public class Wave implements IChunkRan, IChunkSeq {
    private int 		rate;
    private double 		freq;
    private long 		amp;
    private double 		phase;

    private long			cur_index;

    private		double		Period() {
        return	Double.valueOf(rate) / freq;
    }

    public Wave(int Rate, double Freq, long Amp, double StartPhase) {
        rate = 	Rate;
        freq = 	Freq;
        amp = 	Amp;
        phase = StartPhase;

        cur_index = 0;
    }

    public Wave(double Freq, long Amp) {
        this(44100, Freq, Amp, 0.0);
    }

    public Wave(int Rate, double Freq, long Amp) {
        this(Rate, Freq, Amp, 0.0);
    }

    public	long		    getCount() {
        // returns	count of one period of wave
        return Math.round( Period() );
    }
    public	double		getPhase(long index) {
        long 	startPhase = 	Math.round( phase * Period() );
        return	Double.valueOf((index + startPhase) % Math.round( Period() )) / Period();
    }
    public	long	    getValue(long index) {
        return Math.round( amp * Math.sin( 2 * Math.PI * getPhase(index) ) );
    }

    public	void		start() {
        cur_index = 0;
    }
    public	boolean		isEnd() {
        if ( cur_index >= Period() ) return true;

        return false;
    }
    public	boolean		isNotEnd() {
        if ( cur_index >= Period() ) return false;

        return true;
    }
    public	void		next() {
        cur_index += 1;
    }

    public	long		getValue() {
        return getValue(cur_index);
    }

    public	double		getPhase() {
        return getPhase(cur_index);
    }
    public	void		tuneAmp(long Amp) {
        amp = Amp;
    }
    public	void		tunePhase(double Phase) {
        phase = Phase;
    }
    public	void		tuneFreq(double Freq) {
        freq = Freq;
    }

    public static void main(String[] args) {
        IChunkRan cr = new Wave(8800, 440.0, 40000, 0.0);
        System.out.println(cr.getCount());
        for (int i = 0; i < cr.getCount(); i++)
        {
            System.out.print("[" + cr.getValue(i) + "]");
        }
        System.out.println();

        IChunkSeq cs = new Wave(8800, 440.0, 40000, 0.0);
        for (cs.start(); cs.isNotEnd(); cs.next())
        {
            System.out.print("[" + cs.getValue() + "]");
        }
        System.out.println();
    }
}
