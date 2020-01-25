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

    public void PlayCurrentStepSound() {
        Wave waveA = new Wave((int)projectRate, freqCursor.getFreqA(), projectMaxAmp , phaseCursor.getPhase());
        Wave waveB = new Wave((int)projectRate, freqCursor.getFreqB(), projectMaxAmp, phaseCursor.getPhase());

        double[] ps = {1.0, 0.7, 0.5, 0.3, 0.0};
        ArrayList<WaveMix> wms = new ArrayList<>();

        for (double p : ps) {
            WaveMix WM = new WaveMix(waveA, p);
            WM.mix(waveB, 1.0 - p);
            wms.add(WM);
        }

        Sample s = new Sample(projectRate, 1.0, projectMaxAmp, wms.get(0));
        for (WaveMix WM : wms) {
            s.append(WM, 1.0);
        }

        String filename = "tmpsound";
        waveWriter.setChunk(s);
        waveWriter.finalise(filename);
        playSound.play(filename);
    }

    public void PlayReferenceSound() {
        Wave reference = new Wave((int) projectRate, 440.0, 8000, 0.0);
        Sample s = new Sample(projectRate, 1.0, projectMaxAmp, reference);
        String filename = "tmpsound";
        waveWriter.setChunk(s);
        waveWriter.finalise(filename);
        playSound.play(filename);
    }
}
