package silence.rgbsound.sample;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import silence.rgbsound.client.forms.MainRunTestsetForm;
import silence.rgbsound.client.forms.PickTestsetForm;

import javax.swing.*;
import java.awt.*;

public class SampleApplication {

    public static void main(String[] args) {
        ApplicationContext ctx = new AnnotationConfigApplicationContext(SampleApplicationConfig.class);

        EventQueue.invokeLater(new Runnable() {
            public void run() {
                MainRunTestsetForm mainForm = ctx.getBean("testsetForm", MainRunTestsetForm.class);
                mainForm.pack();
                mainForm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                mainForm.setVisible(true);
            }
        } );
    }

}
