package silence.rgbsound.client.forms;

import silence.rgbsound.client.control.AmpCursor;
import silence.rgbsound.client.control.FreqCursor;
import silence.rgbsound.client.control.PhaseCursor;
import silence.rgbsound.client.control.RunTestsetController;
import silence.rgbsound.instrument.Wave;
import silence.rgbsound.instrument.WaveMix;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.util.List;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainRunTestsetForm extends JFrame {
    private JTextField phaseStepCountTextField;
    private JCheckBox phaseONCheckBox;
    private JTextField phaseStepTextField;
    private JTextField phaseValueTextField;
    private JTextField ampStepTextField;
    private JTextField ampValueStepTextField;
    private JButton checkUncheckButton;
    private JButton showWaveButton;
    private JButton playReferenceButton;
    private JButton playSoundButton;
    private JTextField freqATextField;
    private JTextField freqBTextField;
    private JButton startStopButton;
    private JButton quitButton;
    private JButton makeDecisionButton;
    private JTextField ampMinTextField;
    private JTextField ampMaxTextField;
    private JTextField ampStepCountTextField;
    private WatchFreqCursorComponent watchFreqCursorComponent;
    private JButton nextAButton;
    private JButton flipABButton;
    private JButton nextBButton;
    private JButton ONPhaseButton;
    private JButton playPauseButton;
    private JPanel panelOne;
    private JLabel freqBminLabel;
    private JLabel freqBmaxLabel;
    private JLabel freqAminLabel;
    private JLabel freqAmaxLabel;

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
        if (testsetController == null) return;
        SwingWorker<Void, Integer> playTask = new SwingWorker<Void, Integer>() {
            @Override
            protected Void doInBackground() throws Exception {
                AmpCursor ampCursor = testsetController.getAmpCursor();
                ampCursor.start();
                while (ampCursor.isNotEnd()) {
                    publish(1);
                    testsetController.PlayCurrentStepSound();
                    ampCursor.next();
                }
                return null;
            }
            @Override
            protected void process(List<Integer> arg) {
                UpdateAmpFields(testsetController.getAmpCursor());
            }
        };
        playTask.execute();
    }

    private void onPlayReference() {
        if (testsetController != null) testsetController.PlayReferenceSound();
    }

    private void UpdateAmpFields(AmpCursor ampCursor) {
        ampMaxTextField.setText(String.valueOf(ampCursor.getMinAmp()));
        ampMaxTextField.setText(String.valueOf(ampCursor.getMaxAmp()));
        ampStepCountTextField.setText(String.valueOf(ampCursor.getStepCount()));
        ampStepTextField.setText(String.valueOf(ampCursor.getCurrentStep() + 1));
        ampValueStepTextField.setText(String.valueOf(ampCursor.getAmp()));
    }
    private void UpdatePhaseFields(PhaseCursor phaseCursor) {
        phaseONCheckBox.setEnabled(phaseCursor.getEnabled());
        phaseStepCountTextField.setText(String.valueOf(phaseCursor.getStepCount()));
        phaseStepTextField.setText(String.valueOf(phaseCursor.getStep() + 1));
        phaseValueTextField.setText(String.valueOf(phaseCursor.getPhase()));
    }
    private void UpdateFreqFields(FreqCursor freqCursor) {
        freqATextField.setText(String.valueOf(freqCursor.getFreqA()));
        freqBTextField.setText(String.valueOf(freqCursor.getFreqB()));
        freqAmaxLabel.setText(String.valueOf(freqCursor.getMaxFreqA()));
        freqAminLabel.setText(String.valueOf(freqCursor.getMinFreqA()));
        freqBmaxLabel.setText(String.valueOf(freqCursor.getMaxFreqB()));
        freqBminLabel.setText(String.valueOf(freqCursor.getMinFreqB()));
        //WatchFreqCursorComponent.setCurrentCell();
    }
}
