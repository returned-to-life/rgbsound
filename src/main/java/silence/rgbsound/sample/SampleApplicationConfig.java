package silence.rgbsound.sample;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Scope;
import silence.rgbsound.audio.PlaySound;
import silence.rgbsound.client.control.AmpCursor;
import silence.rgbsound.client.control.FreqCursor;
import silence.rgbsound.client.control.PhaseCursor;
import silence.rgbsound.client.control.RunTestsetController;
import silence.rgbsound.client.forms.MainRunTestsetForm;
import silence.rgbsound.client.forms.PickTestsetForm;
import silence.rgbsound.link.CommunicatorMock;
import silence.rgbsound.wavefile.WaveFileWriter;

@Configuration
public class SampleApplicationConfig {

    @Bean
    public MainRunTestsetForm testsetForm() {
        MainRunTestsetForm mtf = new MainRunTestsetForm();
        mtf.setTestsetController(controller());
        return mtf;
    }

    @Bean
    @Scope(value="prototype")
    public PickTestsetForm pickTestsetDialog() {
        PickTestsetForm dialog = new PickTestsetForm();
        dialog.setCommunicator(communicator());
        return dialog;
        /*
        PickTestsetForm dialog = ctx.getBean("pickTestsetDialog", PickTestsetForm.class);
        dialog.pack();
        dialog.setVisible(true);*/
    }

    @Bean
    public CommunicatorMock communicator() {
        CommunicatorMock comm = new CommunicatorMock();
        comm.setCoverageCounter(counter());
        return comm;
    }

    @Bean
    public CoverageCounter counter() {
        return new CoverageCounter();
    }

    @Bean
    public RunTestsetController controller() {
        RunTestsetController control = new RunTestsetController();
        control.setFreqCursor(freqs());
        control.setAmpCursor(new AmpCursor(8000));
        control.setPhaseCursor(new PhaseCursor());
        control.setProjectRate(44100);
        control.setProjectMaxAmp(8000);
        control.setPlaySound(player());
        control.setWaveWriter(waveWriter());
        return control;
    }

    @Bean
    public FreqCursor freqs() {
        return new FreqCursor(440.0, 630.0, 8, 15.0);
    }

    @Bean
    public String tmpdirPath() { //return "#{systemProperties'java.io.tmpdir'}";
        return "/home/rtl/tmp/"; };

    @Bean
    public PlaySound player() {
        return new PlaySound(tmpdirPath());
    }

    @Bean
    @Scope(value="prototype")
    public WaveFileWriter waveWriter() {
        WaveFileWriter wf = new WaveFileWriter(16, 44100, 2);
        wf.setFileDirectory(tmpdirPath());
        return wf;
    }
}
