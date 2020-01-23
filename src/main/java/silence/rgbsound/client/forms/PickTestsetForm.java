package silence.rgbsound.client.forms;

import silence.rgbsound.link.CommunicatorMock;

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
    }

    CommunicatorMock communicator;

    public void setCommunicator(CommunicatorMock communicator) {
        this.communicator = communicator;
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
        pickTestsetComponent1.setMapResponse(communicator.GetTestsetMap(1));
        repaint();
    }

    public static void main(String[] args) {
        PickTestsetForm dialog = new PickTestsetForm();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }

    public void createUIComponents() {
        // TODO: place custom component creation code here
    }

}
