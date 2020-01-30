package silence.rgbsound.client.forms;

import silence.rgbsound.client.control.FreqCursor;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;

public class WatchFreqCursorComponent extends JComponent {

    static final int CELL_SIZE = 20;

    static final Color borderColor = new Color(20, 20,20);
    static final Color cellSplitColor = new Color(160,160,160);
    static final Color cellDefaultColor = new Color(200,200,200);
    static final Color cellSelectedColor = new Color(30, 70, 100);
    static final Color cellVisitedColor = new Color(80,160,200);
    static final Color cellCheckedColor = new Color(30, 160, 50);
    static final Color cellCheckedSelectedColor = new Color(30, 100, 70);

    private int cellcount = 8;

    private FreqCursor freqCursor;
    public void setFreqCursor(FreqCursor freqCursor) { this.freqCursor = freqCursor; }

    public WatchFreqCursorComponent() {
        super();
        cellcount = 8;
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
    public int dispatchCellA(int y) {
        return y / CELL_SIZE;
    }
    public int dispatchCellB(int x) {
        return x / CELL_SIZE;
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

    private void paintCells(Graphics2D g2) {
        for (int a = 0; a < cellcount; a++) {
            for (int b = 0; b < cellcount; b++) {
                FreqCursor.CellStatus st = freqCursor.getCellStatus(a, b);
                switch (st) {
                    case CHECKED:
                        g2.setPaint(cellCheckedColor);
                        break;
                    case VISITED:
                        g2.setPaint(cellVisitedColor);
                        break;
                    case DEFAULT:
                    default:
                        g2.setPaint(cellDefaultColor);
                }
                g2.fill(new Rectangle2D.Double(b * CELL_SIZE, a * CELL_SIZE, CELL_SIZE, CELL_SIZE));
            }
        }
    }

    private void paintCurrentCell(Graphics2D g2) {
        int a = freqCursor.getCurrentStepA();
        int b = freqCursor.getCurrentStepB();
        if (freqCursor.getCellStatus(a, b) == FreqCursor.CellStatus.CHECKED)
            g2.setPaint(cellCheckedSelectedColor);
        else
            g2.setPaint(cellSelectedColor);
        g2.fill(new Rectangle2D.Double(b * CELL_SIZE, a * CELL_SIZE, CELL_SIZE, CELL_SIZE));
        g2.setPaint(borderColor);
        g2.draw(new Rectangle2D.Double(b * CELL_SIZE, a * CELL_SIZE, CELL_SIZE, CELL_SIZE));
    }

    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;

        if (freqCursor == null) {
            paintCellBorders(g2);
            return;
        }
        else {
            cellcount = freqCursor.getStepCount();
            paintCells(g2);
            paintCellBorders(g2);
            paintCurrentCell(g2);
        }
    }
}
