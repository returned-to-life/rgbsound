package silence.rgbsound.localuse;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import silence.rgbsound.client.forms.MainRunTestsetForm;
import silence.rgbsound.localtest.LocaltestConfig;

import javax.swing.*;
import java.awt.*;

@SpringBootApplication
public class LocaluseApplication {

    public static void main(String[] args) {
        ApplicationContext ctx = new AnnotationConfigApplicationContext(LocaluseConfig.class);

        EventQueue.invokeLater(() -> {
            MainRunTestsetForm mainForm = ctx.getBean("testsetForm", MainRunTestsetForm.class);
            mainForm.pack();
            mainForm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            mainForm.setVisible(true);
        });
    }

}
