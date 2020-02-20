package silence.rgbsound.client.forms;

import silence.rgbsound.db.CoverageDone;
import silence.rgbsound.db.Found;
import silence.rgbsound.link.Communicator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

public class MakeDecisionForm extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JEditorPane editorPane1;
    private JPanel foundPointsPanel;
    private JCheckBox phaseCheckBox;
    private boolean isCanceled;

    public void setCommunicator(Communicator communicator) {
        this.communicator = communicator;
    }
    private Communicator communicator;
    private CoverageDone testsetInfo;
    private Map<Found, Checkbox> testsetFounds;

    public MakeDecisionForm() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(e -> onOK());

        buttonCancel.addActionListener(e -> onCancel());

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(e -> onCancel(), KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
        isCanceled = false;
    }

    private void onOK() {
        // add your code here
        isCanceled = false;
        saveDecision();
        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        isCanceled = true;
        dispose();
    }

    public boolean getDialogWasCanceled() {
        return isCanceled;
    }

    public void setTestsetInfo(CoverageDone testsetInfo, List<Found> foundList) {
        this.testsetInfo = testsetInfo;
        this.testsetFounds = new HashMap<>();

        foundPointsPanel.setLayout(new BoxLayout(foundPointsPanel, BoxLayout.Y_AXIS));
        if (foundList.size() == 0) {
            foundPointsPanel.add(new Label("Nothing found"));
            return;
        }
        for (Found f : foundList) {
            String descr = "A: " + String.format("%.4f", f.getFreqA()) +
                    " B: " + String.format("%.4f", f.getFreqB());
            Checkbox foundCheckBox = new Checkbox(descr, true);
            foundPointsPanel.add(foundCheckBox);
            testsetFounds.put(f, foundCheckBox);
        }
        repaint();
    }
    private void saveDecision() {
        if (communicator == null) return;
        if (testsetInfo == null) return;

        CoverageDone coverage = new CoverageDone();
        coverage.setStepIndexA(testsetInfo.getStepIndexA());
        coverage.setStepIndexB(testsetInfo.getStepIndexB());
        coverage.setUserId(testsetInfo.getUserId());
        coverage.setCoverageMapId(testsetInfo.getCoverageMapId());

        coverage.setComment(editorPane1.getText().trim());
        coverage.setTimestamp(new java.sql.Date(new Date().getTime()));

        List<Found> founds = new ArrayList<>();
        for (Found f: testsetFounds.keySet()) {
            if (testsetFounds.get(f).getState())
                f.setPhaseMatters(phaseCheckBox.isSelected());
                founds.add(f);
        }
        coverage.setFounds(founds);

        communicator.saveCoverage(coverage);
    }

    public static void main(String[] args) {
        MakeDecisionForm dialog = new MakeDecisionForm();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }
}
