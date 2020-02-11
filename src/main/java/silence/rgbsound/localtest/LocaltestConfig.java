package silence.rgbsound.localtest;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import silence.rgbsound.audio.PlaySound;
import silence.rgbsound.client.control.*;
import silence.rgbsound.client.forms.MainRunTestsetForm;
import silence.rgbsound.client.forms.PickTestsetForm;
import silence.rgbsound.link.CommunicatorMockRandom;
import silence.rgbsound.wavefile.WaveFileWriter;

@Configuration
public class LocaltestConfig {

    @Bean
    public MainRunTestsetForm testsetForm() {
        MainRunTestsetForm mtf = new MainRunTestsetForm();
        mtf.setTestsetController(controller());
        mtf.onLoad();
        return mtf;
    }

    @Bean
    @Scope(value = "prototype")
    public Testset testset() {
        return pickTestsetDialog().getResult();
    }

    @Bean
    @Scope(value="prototype")
    public PickTestsetForm pickTestsetDialog() {
        PickTestsetForm dialog = new PickTestsetForm();
        dialog.setController(pickController());
        dialog.onLoad();
        dialog.pack();
        dialog.setVisible(true);
        return dialog;
    }

    @Bean
    public PickTestsetController pickController() {
        PickTestsetController ctrl = new PickTestsetController();
        ctrl.setCommunicator(communicator());
        return ctrl;
    }

    @Bean
    public CommunicatorMockRandom communicator() {
        CommunicatorMockRandom comm = new CommunicatorMockRandom();
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
        control.LoadTestset(testset());
        control.setAmpCursor(new AmpCursor(8000));
        control.setProjectMaxAmp(8000);
        control.setProjectRate(44100);
        control.setPlaySound(player());
        control.setWaveWriter(waveWriter());
        return control;
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
