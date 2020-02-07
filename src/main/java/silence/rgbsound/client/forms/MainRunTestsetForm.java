package silence.rgbsound.client.forms;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import silence.rgbsound.client.control.*;
import silence.rgbsound.instrument.Wave;
import silence.rgbsound.instrument.WaveMix;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainRunTestsetForm extends JFrame implements ApplicationContextAware {
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
    private JButton selectAnotherButton;

    public void setTestsetController(RunTestsetController testsetController) {
        this.testsetController = testsetController;
        watchFreqCursorComponent.setFreqCursor(testsetController.getFreqCursor());
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
        startStopButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) { onStartStop(); }
        });
        checkUncheckButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                onCheckUncheck();
            }
        });
        flipABButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                onFlipAB();
            }
        });
        playPauseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                onPlayPause();
            }
        });
        watchFreqCursorComponent.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                super.mouseClicked(mouseEvent);
                onJumpToCell(mouseEvent.getX(), mouseEvent.getY());
            }
        });
        nextAButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                onNextA();
            }
        });
        nextBButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                onNextB();
            }
        });
        selectAnotherButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                onGetNewTestset();
            }
        });
    }

    public void onLoad() {
        if (testsetController == null) return;

        UpdateAmpFields(testsetController.getAmpCursor());
        UpdatePhaseFields(testsetController.getPhaseCursor());
        UpdateFreqFields(testsetController.getFreqCursor());
        EnableDisablePauseOnlyButtons(false);
        repaint();
    }

    private void onGetNewTestset() {
        Testset testset = ctx.getBean("testset", Testset.class);
        testsetController.LoadTestset(testset);
        onLoad();
    }

    private void onCheckUncheck() {
        testsetController.getFreqCursor().ToogleCheckCell();
        watchFreqCursorComponent.repaint();
    }

    private void onFlipAB() {
        testsetController.getFreqCursor().switchPrimaryAxis();
    }
    private void onNextA() { testsetController.getFreqCursor().NextA(); }
    private void onNextB() { testsetController.getFreqCursor().NextB(); }

    private void onJumpToCell(int x, int y) {
        if (testsetController == null) return;

        int a = watchFreqCursorComponent.dispatchCellA(y);
        int b = watchFreqCursorComponent.dispatchCellB(x);
        testsetController.getFreqCursor().goToCell(a, b);

        watchFreqCursorComponent.repaint();
    }

    private void onStartStop() {
        if (testsetController == null) return;
        if (testsetController.isActive()) {
            testsetController.Stop();
            startStopButton.setText("restart");
            return;
        }

        SwingWorker<Void, Integer> playTask = new SwingWorker<Void, Integer>() {
            @Override
            protected Void doInBackground() throws Exception {
                testsetController.Start();
                while (testsetController.isActive()) {
                    publish(1);
                    testsetController.PlayCurrentStepSound();
                    testsetController.NextStep();
                }
                return null;
            }
            @Override
            protected void process(List<Integer> arg) {
                UpdateFreqFields(testsetController.getFreqCursor());
                UpdatePhaseFields(testsetController.getPhaseCursor());
                UpdateAmpFields(testsetController.getAmpCursor());
            }
        };
        EnableDisablePauseOnlyButtons(false);
        startStopButton.setText("stop");
        playTask.execute();
    }

    private void onPlayPause() {
        if (testsetController == null) return;
        if (testsetController.isActive()) {
            testsetController.Stop();
            EnableDisablePauseOnlyButtons(true);
            playPauseButton.setText("play");
            return;
        }

        SwingWorker<Void, Integer> playTask = new SwingWorker<Void, Integer>() {
            @Override
            protected Void doInBackground() throws Exception {
                testsetController.Play();
                while (testsetController.isActive()) {
                    publish(1);
                    testsetController.PlayCurrentStepSound();
                    testsetController.NextStep();
                }
                return null;
            }
            @Override
            protected void process(List<Integer> arg) {
                UpdateFreqFields(testsetController.getFreqCursor());
                UpdatePhaseFields(testsetController.getPhaseCursor());
                UpdateAmpFields(testsetController.getAmpCursor());
            }
        };
        EnableDisablePauseOnlyButtons(false);
        playPauseButton.setText("pause");
        playTask.execute();
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
        freqATextField.setText(String.format("%.4f",freqCursor.getFreqA()));
        freqBTextField.setText(String.format("%.4f",freqCursor.getFreqB()));
        freqAmaxLabel.setText(String.format("%.2f", freqCursor.getMaxFreqA()));
        freqAminLabel.setText(String.format("%.2f", freqCursor.getMinFreqA()));
        freqBmaxLabel.setText(String.format("%.2f",freqCursor.getMaxFreqB()));
        freqBminLabel.setText(String.format("%.2f",freqCursor.getMinFreqB()));
        watchFreqCursorComponent.repaint();
    }
    private void EnableDisablePauseOnlyButtons(boolean enable) {
        checkUncheckButton.setEnabled(enable);
        playSoundButton.setEnabled(enable);
        playReferenceButton.setEnabled(enable);
    }

    private ApplicationContext ctx;
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        ctx = applicationContext;
    }
}
