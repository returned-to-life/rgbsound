package silence.rgbsound.audio;

import java.io.File;

public class PlaySound
{
    String fileDir;

    public PlaySound(String FileDirectory)
    {
        fileDir = FileDirectory;
    }

    public void play(String filename) {
        try {
            ProcessBuilder pb = new ProcessBuilder("aplay", filename + ".wav");
            pb.directory(new File(fileDir));
            pb.redirectErrorStream(true);
            Process p = pb.start();
        } catch ( Exception ex ) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        PlaySound ps = new PlaySound("/home/rtl/rgbsound/Tmp/");
        ps.play("test_read");
    }
}

