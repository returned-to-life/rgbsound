package silence.rgbsound.chunk;

import java.util.ArrayList;

public class Chunk implements IChunkRan, IChunkSeq {
    ArrayList<Long> values;

    private long		cur_index;

    public Chunk() {
        values = new ArrayList<>();
    }
    public void			append(long value) {
        values.add(value);
    }

    public	long		getCount() {
        return values.size();
    }
    public	long	    getValue(long index) {
        return values.get((int) index);
    }

    public	void		start() {
        cur_index = 0;
    }
    public	boolean		isEnd() {
        if ( cur_index >= values.size() ) return true;

        return false;
    }
    public	boolean		isNotEnd() {
        if ( cur_index < values.size() ) return true;

        return false;
    }
    public	void		next() {
        cur_index += 1;
    }

    public	long		getValue() {
        return getValue(cur_index);
    }

    public static void main(String[] args) {
        System.out.println();
    }
}
