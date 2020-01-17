package silence.rgbsound.client.forms;

import silence.rgbsound.client.control.FreqCursor;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;

public class WatchFreqCursorComponent extends JComponent {

    final int CELL_SIZE = 20;

    final Color borderColor = new Color(20, 20,20);
    final Color cellSplitColor = new Color(160,160,160);
    final Color cellDefaultColor = new Color(200,200,200);
    final Color cellSelectedColor = new Color(30, 50, 100);
    final Color cellVisitedColor = new Color(80,160,200);
    final Color cellCheckedColor = new Color(30, 160, 50);

    private int cellcount = 8;
    private FreqCursor freqCursor;

    public WatchFreqCursorComponent() {
        super();
    }

    public int getCellCount() {
        return cellcount;
    }
    public int getUsualSize() {
        return cellcount * CELL_SIZE;
    }
    public Dimension getPreferredSize() {
        return new Dimension(getUsualSize(), getUsualSize());
    }

    private void paintCellBorders(Graphics2D g2) {
        g2.setPaint(cellSplitColor);
        // horizontal lines
        for (int i = 1; i < getCellCount(); i++)
            g2.draw(new Line2D.Double(0.0, i*CELL_SIZE, getUsualSize() - 1, i*CELL_SIZE));
        // vertical lines
        for (int i = 1; i < getCellCount(); i++)
            g2.draw(new Line2D.Double(i*CELL_SIZE, 0.0, i*CELL_SIZE, getUsualSize() - 1));

        g2.setPaint(borderColor);
        g2.draw(new Rectangle2D.Double(0.0, 0.0, getUsualSize() - 1, getUsualSize() - 1));
    }

    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;

        if (freqCursor == null) {
            paintCellBorders(g2);
            return;
        }
        else {
            paintCellBorders(g2);
        }
    }
}
