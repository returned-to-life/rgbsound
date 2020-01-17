package silence.rgbsound.sample;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import silence.rgbsound.client.forms.PickTestsetForm;
import silence.rgbsound.link.CommunicatorMock;
import silence.rgbsound.link.messages.TestsetMapResponce;

@Configuration
public class SampleApplicationConfig {

    @Bean
    @Scope(value="prototype")
    public PickTestsetForm pickTestsetDialog() {
        PickTestsetForm dialog = new PickTestsetForm();
        dialog.setCommunicator(communicator());
        return dialog;
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
}
