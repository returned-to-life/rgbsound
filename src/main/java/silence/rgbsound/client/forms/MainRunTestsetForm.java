package silence.rgbsound.client.forms;

import silence.rgbsound.client.control.RunTestsetController;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainRunTestsetForm extends JFrame {
    private JTextField textField1;
    private JCheckBox ONCheckBox;
    private JTextField textField2;
    private JTextField textField3;
    private JTextField textField4;
    private JTextField textField5;
    private JButton checkUncheckButton;
    private JButton showWaveButton;
    private JButton playReferenceButton;
    private JButton playSoundButton;
    private JTextField textField6;
    private JTextField textField7;
    private JButton startStopButton;
    private JButton quitButton;
    private JButton makeDesisionButton;
    private JTextField textField8;
    private JTextField textField9;
    private JTextField textField10;
    private WatchFreqCursorComponent watchFreqCursorComponent1;
    private JButton nextAButton;
    private JButton flipABButton;
    private JButton nextBButton;
    private JButton ONPhaseButton;
    private JButton playPauseButton;
    private JPanel panelOne;

    public void setTestsetController(RunTestsetController testsetController) {
        this.testsetController = testsetController;
    }

    RunTestsetController testsetController;

    public MainRunTestsetForm() {
        setContentPane(panelOne);
        playSoundButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                onPlaySound();
            }
        });
        playReferenceButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                onPlayReference();
            }
        });
    }

    private void onPlaySound() {
        if (testsetController != null) testsetController.PlayCurrentStepSound();
    }

    private void onPlayReference() {
        if (testsetController != null) testsetController.PlayReferenceSound();
    }
}
