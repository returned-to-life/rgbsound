package silence.rgbsound.sample;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import silence.rgbsound.client.forms.PickTestsetForm;

public class SampleApplication {

    public static void main(String[] args) {
        ApplicationContext ctx = new AnnotationConfigApplicationContext(SampleApplicationConfig.class);

        PickTestsetForm dialog = ctx.getBean("pickTestsetDialog", PickTestsetForm.class);
        dialog.pack();
        dialog.setVisible(true);

        System.exit(0);
    }

}
