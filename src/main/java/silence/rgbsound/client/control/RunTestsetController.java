package silence.rgbsound.client.control;

import silence.rgbsound.audio.PlaySound;
import silence.rgbsound.db.CoverageDone;
import silence.rgbsound.db.Found;
import silence.rgbsound.instrument.Sample;
import silence.rgbsound.instrument.Wave;
import silence.rgbsound.instrument.WaveMix;
import silence.rgbsound.link.Communicator;
import silence.rgbsound.link.messages.TestsetMapResponce;
import silence.rgbsound.wavefile.WaveFileWriter;

import java.util.*;

public class RunTestsetController {
    /* all MainRunTestsetForm actions */
    public enum TestsetRunState {RUNNING, STOPPED};

    FreqCursor freqCursor;
    AmpCursor ampCursor;
    PhaseCursor phaseCursor;

    long projectRate;
    long projectMaxAmp;
    PlaySound playSound;
    WaveFileWriter waveWriter;
    TestsetRunState state;
    Testset testset;

    public AmpCursor getAmpCursor() { return ampCursor; }
    public FreqCursor getFreqCursor() { return freqCursor; }
    public PhaseCursor getPhaseCursor() { return phaseCursor; }

    public void setFreqCursor(FreqCursor freqCursor) { this.freqCursor = freqCursor;    }
    public void setAmpCursor(AmpCursor ampCursor) { this.ampCursor = ampCursor; }
    public void setPhaseCursor(PhaseCursor phaseCursor) {this.phaseCursor = phaseCursor; }
    public void setProjectRate(long projectRate) { this.projectRate = projectRate; }
    public void setProjectMaxAmp(long projectMaxAmp) { this.projectMaxAmp = projectMaxAmp; }
    public void setPlaySound(PlaySound playSound) {
        this.playSound = playSound;
    }
    public void setWaveWriter(WaveFileWriter waveWriter) { this.waveWriter = waveWriter; }

    public RunTestsetController() {
        state = TestsetRunState.STOPPED;
    }

    public void LoadTestset(Testset testset) {
        this.testset = testset;
        setFreqCursor(new FreqCursor(testset.getStartFreqA(), testset.getStartFreqB(), testset.getStepCount(), testset.getStepSize()));
        setPhaseCursor(new PhaseCursor());
        Stop();
    }
    public CoverageDone getTestsetInfo() {
        CoverageDone result = new CoverageDone();
        if (testset != null) {
            result.setCoverageMapId(testset.getMapId());
            result.setUserId(testset.getUserId());
            result.setStepIndexA(testset.getStepIndexA());
            result.setStepIndexB(testset.getStepIndexB());
        }
        return result;
    }
    //----------------------------- actions -------------------------------------------
    public void Start() {
        freqCursor.ClearCells();
        freqCursor.start();
        //phaseCursor.start();
        ampCursor.start();
        state = TestsetRunState.RUNNING;
    }

    public void Play() {
        state = TestsetRunState.RUNNING;
    }

    public void Stop() {
        state = TestsetRunState.STOPPED;
    }

    public boolean isActive() {
        if (state == TestsetRunState.STOPPED) return false;
        else return true;
    }

    public void NextStep() {
        ampCursor.next();
        if (ampCursor.isNotEnd()) return;
        else {
            freqCursor.next();
            if (freqCursor.isNotEnd())
                ampCursor.start();
            else
                state = TestsetRunState.STOPPED;
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
