package silence.rgbsound.localtest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import silence.rgbsound.client.forms.MainRunTestsetForm;

import javax.swing.*;
import java.awt.*;

@SpringBootApplication
public class LocaltestApplication {

    public static void main(String[] args) {
        ApplicationContext ctx = new AnnotationConfigApplicationContext(LocaltestConfig.class);

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
