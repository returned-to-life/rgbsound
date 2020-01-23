package silence.rgbsound.wavefile;

import silence.rgbsound.chunk.Chunk;
import silence.rgbsound.chunk.IChunkRan;
import silence.rgbsound.chunk.IChunkSeq;

import java.io.FileOutputStream;
import java.io.IOException;


public class WaveFileWriter
{
    final String enc = new String("ASCII");
    String fileDirectory = null;

    int depth;
    int rate;
    int channum;

    IChunkRan chunk;
    public void setChunk(IChunkRan chunk) {
        this.chunk = chunk;
    }
    public void setFileDirectory(String fileDirectory) { this.fileDirectory = fileDirectory; }

    public WaveFileWriter(int pDepth, int pRate, int pChannum)
    {
        depth = pDepth;
        rate = pRate;
        channum = pChannum;
    }

    public int length() {
        return (int) chunk.getCount();
    }
    private void writenumber(FileOutputStream out, short bytecount, long number)
            throws IOException {
        for (short j = 0; j < bytecount; j++)
            out.write( (byte) (number >> (8 * j)) & 0xFF );
    }
    public void finalise(String filename) {
        try {
            FileOutputStream out = new FileOutputStream(fileDirectory +  filename + ".wav");

            out.write(new String("RIFF").getBytes(enc));
            int chunkSize = length() * channum * (depth / 8) + 44 - 8;
            writenumber(out, (short) 4, chunkSize);
            out.write(new String("WAVE").getBytes(enc));
            out.write(new String("fmt ").getBytes(enc));
            writenumber(out, (short) 4, 16);
            writenumber(out, (short) 2, 1);
            writenumber(out, (short) 2, channum);
            writenumber(out, (short) 4, rate);
            writenumber(out, (short) 4, rate * channum * (depth / 8));
            writenumber(out, (short) 2, channum * (depth / 8));
            writenumber(out, (short) 2, depth);
            out.write(new String("data").getBytes(enc));
            writenumber(out, (short) 4, length() * channum * (depth / 8));

            for (int i = 0; i < length(); i++)
                for(int j = 0; j < channum; j++)
                {
                    writenumber(out, (short) (depth / 8), chunk.getValue(i));
                }

            out.close();
        }
        catch(IOException ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Chunk ch = new Chunk();
        for (int i = 0; i < 500; i++)
            ch.append(i);

        WaveFileWriter wfw = new WaveFileWriter(16, 44100, 2);
        wfw.setChunk(ch);
        wfw.finalise("test");
    }
}

