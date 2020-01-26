package silence.rgbsound.client.control;

import silence.rgbsound.audio.PlaySound;
import silence.rgbsound.instrument.Sample;
import silence.rgbsound.instrument.Wave;
import silence.rgbsound.instrument.WaveMix;
import silence.rgbsound.wavefile.WaveFileWriter;

import java.util.ArrayList;

public class RunTestsetController {
    /* all MainRunTestsetForm actions */
    enum TestsetRunState {RUNNING, STOPPED, PAUSED};

    FreqCursor freqCursor;
    AmpCursor ampCursor;
    PhaseCursor phaseCursor;

    long projectRate;
    long projectMaxAmp;
    PlaySound playSound;
    WaveFileWriter waveWriter;
    TestsetRunState state;

    public AmpCursor getAmpCursor() { return ampCursor; }

    public void setFreqCursor(FreqCursor freqCursor) { this.freqCursor = freqCursor;    }
    public void setAmpCursor(AmpCursor ampCursor) { this.ampCursor = ampCursor; }
    public void setPhaseCursor(PhaseCursor phaseCursor) {this.phaseCursor = phaseCursor; }
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

    public void PlayCurrentStepSound() throws InterruptedException {
        Sample sample = new Sample(projectRate);

        Wave waveA = new Wave((int) projectRate, freqCursor.getFreqA(), ampCursor.getMaxAmp(), phaseCursor.getPhase());
        Wave waveB = new Wave((int) projectRate, freqCursor.getFreqB(), ampCursor.getMaxAmp(), phaseCursor.getPhase());

        WaveMix WM = new WaveMix(waveA, ampCursor.getAmpFactor());
        WM.mix(waveB, 1.0 - ampCursor.getAmpFactor());

        sample.append(WM, 1.0);

        String filename = "lastsamplesound";
        waveWriter.setChunk(sample);
        waveWriter.finalise(filename);
        playSound.play(filename);
        Thread.sleep(1000);
    }

    public void PlayReferenceSound() {
        Wave reference = new Wave((int) projectRate, 440.0, 8000, 0.0);
        Sample s = new Sample(projectRate, 1.0, reference);
        String filename = "tmpsound";
        waveWriter.setChunk(s);
        waveWriter.finalise(filename);
        playSound.play(filename);
    }
}
