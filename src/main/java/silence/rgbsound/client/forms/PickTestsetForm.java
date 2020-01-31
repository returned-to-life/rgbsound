package silence.rgbsound.client.forms;

import silence.rgbsound.client.control.PickTestsetController;
import silence.rgbsound.link.CommunicatorMock;
import silence.rgbsound.link.messages.TestsetMapResponce;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class PickTestsetForm extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JButton buttonReload;
    private JLabel labelFreqA;
    private JLabel labelFreqB;
    private JButton buttonLoadFounds;
    private JTable table1;
    private JLabel labelCoverage;
    private JPanel panelTestsetPicker;
    private PickTestsetComponent pickTestsetComponent1;
    private JScrollBar scrollBar1;
    private JScrollBar scrollBar2;
    private JLabel minFreqLabel;
    private JLabel maxFreqLabel1;
    private JLabel maxFreqLabel2;
    private JLabel labelMinShownBFreq;
    private JLabel labelMaxShownBFreq;
    private JLabel labelFounds;

    public PickTestsetForm() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        buttonReload.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                onReload();
            }
        });

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
        pickTestsetComponent1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                super.mouseClicked(mouseEvent);
                onSelectTestset(mouseEvent.getX(), mouseEvent.getY());
            }
        });
    }

    PickTestsetController controller;

    public void setController(PickTestsetController controller) {
        this.controller = controller;
        pickTestsetComponent1.setController(controller);
    }

    private void onOK() {
        // add your code here
        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    private void onReload() {
        controller.reloadTestsetMap();

        minFreqLabel.setText(String.valueOf(Math.round(controller.getMinFreq())));
        maxFreqLabel1.setText(String.valueOf(Math.round(controller.getMaxFreq())));
        maxFreqLabel2.setText(String.valueOf(Math.round(controller.getMaxFreq())));
        repaint();
    }

    private void onSelectTestset(int x, int y) {
        int a = pickTestsetComponent1.dispatchCellA(y);
        int b = pickTestsetComponent1.dispatchCellB(x);
        controller.setCurrentCellIndexA(a);
        controller.setCurrentCellIndexB(b);

        TestsetMapResponce.TestsetMapCell cell = controller.getCell(a, b);
        labelFreqA.setText("Freq A: " + String.format("%.2f", cell.getStartFreqA()));
        labelFreqB.setText("Freq B: " + String.format("%.2f", cell.getStartFreqB()));
        labelCoverage.setText("Tested " + String.valueOf(cell.getCoverageCount()) + " times");
        labelFounds.setText("Founds: " + String.valueOf(cell.getFoundCount()));

        repaint();
    }

    public void createUIComponents() {
        // TODO: place custom component creation code here
    }

}
