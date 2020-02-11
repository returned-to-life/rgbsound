package silence.rgbsound.client.forms;

import silence.rgbsound.client.control.PickTestsetController;
import silence.rgbsound.link.messages.TestsetMapResponce;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;

public class PickTestsetComponent extends JComponent {

    final int USUAL_WIDTH = 640;
    final int USUAL_HEIGHT = 640;

    final int CELL_SIZE = 16;
    final Color borderColor = new Color(20, 20,20);
    final Color cellSplitColor = new Color(160,160,160);
    final Color cellSelectedColor = new Color(245, 255, 150);

    final int cellColorR = 80;
    final int cellColorGMin = 100;
    final int cellColorGMax = 200;
    final int cellColorBMin = 100;
    final int cellColorBMax = 200;

    final Color cellColor1 = new Color(cellColorR,cellColorGMin,cellColorBMin);
    final Color cellColor2 = new Color(cellColorR,cellColorGMin,cellColorBMax);
    final Color cellColor3 = new Color(cellColorR,cellColorGMax,cellColorBMin);
    final Color cellColor4 = new Color(cellColorR,cellColorGMax,cellColorBMax);

    public Color getCellColor(int coverageMax, int foundMax, TestsetMapResponce.TestsetMapCell cell) {
        int cellColorG = cellColorGMin;
        try {
            cellColorG += (cellColorGMax - cellColorGMin) * (Double.valueOf(cell.getFoundCount()) / Double.valueOf(foundMax));
        }
        catch (IllegalArgumentException ex) {}
        int cellColorB = cellColorBMin;
        try {
            cellColorB += (cellColorBMax - cellColorBMin) * (Double.valueOf(cell.getCoverageCount()) / Double.valueOf(coverageMax));
        }
        catch (IllegalArgumentException ex) {}
        return new Color(cellColorR, cellColorG, cellColorB);
    }

    PickTestsetController controller;
    public void setController(PickTestsetController controller) { this.controller = controller; }

    public PickTestsetComponent() {
        super();
    }

    public int getCellCountX() {
        return USUAL_WIDTH / CELL_SIZE;
    }
    public int getCellCountY() {
        return USUAL_HEIGHT / CELL_SIZE;
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
        for (int i = 1; i < getCellCountY(); i++)
            g2.draw(new Line2D.Double(0.0, i*CELL_SIZE, USUAL_WIDTH - 1, i*CELL_SIZE));
        // vertical lines
        for (int i = 1; i < getCellCountX(); i++)
            g2.draw(new Line2D.Double(i*CELL_SIZE, 0.0, i*CELL_SIZE, USUAL_HEIGHT - 1));

        g2.setPaint(borderColor);
        g2.draw(new Rectangle2D.Double(0.0, 0.0, USUAL_WIDTH - 1, USUAL_HEIGHT - 1));
    }

    private void paintCells(Graphics2D g2) {
        for (int b = 0; b < getCellCountX(); b++)
            for (int a = 0; a < getCellCountY(); a++)
            {
                g2.setColor(getCellColor(controller.getCoverageMax(), controller.getFoundMax(), controller.getCell(a, b)));
                g2.fill(new Rectangle2D.Double(b * CELL_SIZE, a * CELL_SIZE, CELL_SIZE, CELL_SIZE));
            }
    }

    private void paintCurrentCell(Graphics2D g2) {
        g2.setPaint(cellSelectedColor);
        g2.fill(new Rectangle2D.Double(controller.getCurrentCellIndexB() * CELL_SIZE, controller.getCurrentCellIndexA() * CELL_SIZE, CELL_SIZE, CELL_SIZE));
        g2.setPaint(borderColor);
        g2.draw(new Rectangle2D.Double(controller.getCurrentCellIndexB() * CELL_SIZE, controller.getCurrentCellIndexA() * CELL_SIZE, CELL_SIZE, CELL_SIZE));
    }

    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;

        if (controller == null) {
            paintCellBorders(g2);
            return;
        }
        else {
            paintCells(g2);
            paintCellBorders(g2);
            paintCurrentCell(g2);
        }
    }

    public Dimension getPreferredSize() {
        return new Dimension(USUAL_WIDTH, USUAL_HEIGHT);
    }


}
