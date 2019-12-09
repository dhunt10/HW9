package edu.cs3500.spreadsheets.view;

import edu.cs3500.spreadsheets.model.Cell;
import edu.cs3500.spreadsheets.model.Coord;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.Map;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;

/**
 * graphical frame to be put on a window.
 */
public class GraphicsFrame extends JFrame {

  private Map<Coord, Cell> curr;
  private GridPanel gridPanel;
  private JFrame frame = new JFrame(); //creates frame
  private JLabel[][] grid; //names the grid of buttons

  /**
   * Graphical static view constructor.
   * @param curr current map of cells.
   * @param width width of the spreadsheet.
   * @param height height of the spreadsheet.
   */
  public GraphicsFrame(Map<Coord, Cell> curr,
      int width, int height) {

    super();
    this.setPreferredSize(new Dimension(width,  height));
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    this.setLayout(new BorderLayout());
    gridPanel = new GridPanel(width, height, curr);

    JScrollPane scrollBar = new JScrollPane(
        gridPanel,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
    this.add(scrollBar, BorderLayout.CENTER);

    this.pack();
    this.setSize(800, 500);
  }

  /**
   * Allows for the display to be visible to user.
   */
  public void display() {
    this.setVisible(true);
  }

  /**
   * Returns current grid.
   * @return current grid.
   */
  public GridPanel getGridPanel() {
    return this.gridPanel;
  }
}
