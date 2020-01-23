package silence.rgbsound.client.control;

import silence.rgbsound.audio.PlaySound;
import silence.rgbsound.instrument.Sample;
import silence.rgbsound.instrument.Wave;
import silence.rgbsound.wavefile.WaveFileWriter;

public class RunTestsetController {
    /* all MainRunTestsetForm actions */
    enum TestsetRunState {RUNNING, STOPPED, PAUSED};

    FreqCursor freqCursor;

    long projectRate;
    long projectMaxAmp;
    PlaySound playSound;
    WaveFileWriter waveWriter;
    TestsetRunState state;

    public void setFreqCursor(FreqCursor freqCursor) { this.freqCursor = freqCursor;    }
    public void setProjectRate(long projectRate) { this.projectRate = projectRate; }
    public void setProjectMaxAmp(long projectMaxAmp) { this.projectMaxAmp = projectMaxAmp; }
    public void setPlaySound(PlaySound playSound) {
        this.playSound = playSound;
    }
    public void setWaveWriter(WaveFileWriter waveWriter) { this.waveWriter = waveWriter; }

    //----------------------------- actions -------------------------------------------
    public void StartStop() {
        if (state == TestsetRunState.RUNNING) state = TestsetRunState.STOPPED;
        if (state == TestsetRunState.STOPPED) state = TestsetRunState.RUNNING;
        if (state == TestsetRunState.PAUSED) {
            state = TestsetRunState.RUNNING;
        }
    }

    public void PlayCurrentStepSound() {}
    public void PlayReferenceSound() {
        Wave reference = new Wave((int) projectRate, 440.0, 8000, 0.0);
        Sample s = new Sample(projectRate, 1.0, projectMaxAmp, reference);
        String filename = "tmpsound";
        waveWriter.setChunk(s);
        waveWriter.finalise(filename);
        playSound.play(filename);
    }
}
