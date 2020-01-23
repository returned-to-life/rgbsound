package silence.rgbsound.chunk;

public interface IChunkSeq {
    void		start();
    boolean		isEnd();
    boolean		isNotEnd();
    void		next();

    long		getValue();
}
