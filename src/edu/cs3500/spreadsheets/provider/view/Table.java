package edu.cs3500.spreadsheets.provider.view;

import edu.cs3500.spreadsheets.provider.model.BasicCell;
import edu.cs3500.spreadsheets.provider.model.ViewModel;
import java.awt.Color;
import java.awt.Dimension;
import java.util.Map;

import javax.swing.JScrollPane;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.JFrame;
import javax.swing.table.TableColumn;
import edu.cs3500.spreadsheets.model.Coord;

/**
 * Table is an implementation of WorksheetView that renders a given worksheet model inside a window,
 * drawing a grid of cells adn showing their evaluated values. The model is the model to be made
 * into a visual spreadsheet. NumCols is the minimum number of columns in the visual spreadsheet.
 * NumRows is the minimum number of rows in the visual spreadsheet. Height is the desired height of
 * the window that contains the spreadsheet. Width is the desired width of the window that contains
 * the spreadsheet. CellHeight is the desired height of each cell. CellWidth is the desired width of
 * each cell. ColNames is an array of the column names of the spreadsheet.  These will be A, B,
 * C.... Contents is the data of the spreadsheet.
 */

public class Table extends JPanel implements WorksheetView {

  int numCols = 26;
  int numRows = 30;
  private ViewModel model;
  private int height = 500;
  private int width = 500;

  private int cellHeight = 20;
  private int cellWidth = 100;
  private String[] colNames;
  private String[][] contents;


  /**
   * Creates a Table of the given model.  The table is a JTable held in a scrollPane.
   *
   * @param model the model to be made into a visual spreadsheet
   */
  public Table(ViewModel model) {
    this.model = model;
    JTable table = makeTable(false);
    JScrollPane scrollPane = new JScrollPane(table);
    // creates a column header, made up of numbers 1 - numRows.
    JTable fixedColOne = new JTable(getRowHeading(), getEmptyColumnName());
    // Sets teh size of the column header to match the size of other columns.
    fixedColOne.setPreferredScrollableViewportSize(new Dimension(cellWidth, height));
    fixedColOne.setSize(cellWidth, cellHeight * numRows);
    fixedColOne.setRowHeight(cellHeight);
    // sets the background color of the column header to be light gray
    fixedColOne.setBackground(Color.lightGray);
    // sets the column header to the scrollpane
    scrollPane.setRowHeaderView(fixedColOne);
    add(scrollPane);


    JButton addColumn;
    JButton addRow;

    // These buttons will be implemented in future assignemtns when we make a controller.  For now,
    // these buttons do nothing.
    addColumn = new JButton("Add Column");
    addColumn.setActionCommand("Add Column");
    add(addColumn);

    addRow = new JButton("Add Row");
    addRow.setActionCommand("Add Row");
    add(addRow);
  }

  public int getNumCols() {
    return numCols;
  }

  public int getNumRows() {
    return numRows;
  }

  @Override
  public int getHeight() {
    return height;
  }

  @Override
  public int getWidth() {
    return width;
  }

  public int getCellHeight() {
    return cellHeight;
  }

  public int getCellWidth() {
    return cellWidth;
  }

  private JTable makeTable(boolean isEditable) {
    // Gets the maximum number of columns needed to contain the model.  This is the biggest column
    // coordinate in the model, or the default number of columns.
    numCols = getMaxColumn(model.getRawSpreadsheet());
    // Gets the maximum number of rows needed to contain the model.  This is the biggest row
    // coordinate in the model, or the default number of rows.
    numRows = getMaxRow(model.getRawSpreadsheet());
    // initializes the colNames array to be of size numCols.
    colNames = new String[numCols];
    // initializes the contents array to be of size numCols by the size of numRows.
    contents = new String[numRows][numCols];
    // creates the columns and rows of the contents by filling the contents and colNames arrays.
    makeCol();
    makeRow();
    JTable table;
    // creates a JTable with the contents and colNames.
    table = new JTable(contents, colNames) {
      @Override
      public boolean isCellEditable(int row, int column) {
        //all cells false
        return false;
      }
    };
    // sets the background color of the colomn header to lightGray.
    table.getTableHeader().setBackground(Color.lightGray);
    //Sets the row height of each row to be the desired cell height.
    table.setRowHeight(cellHeight);
    // sets the column width to be the desired cell width
    for (int i = 0; i < table.getColumnModel().getColumnCount(); i++) {
      TableColumn column = table.getColumnModel().getColumn(i);
      column.setWidth(cellWidth);
    }
    // Does not resize the cells to fit into the frame.
    table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
    // sets the lines between cells to be visible.
    table.setShowGrid(true);
    // sets the color of the line grid to be black.
    table.setGridColor(Color.BLACK);
    return table;
  }

  public JTable getTable(boolean editable) {
    return makeTable(editable);
  }

  /**
   * Gets an empty column name, to be used as teh top left cell of the spreadsheet.
   *
   * @return an array of size one, with an empty string
   */
  private String[] getEmptyColumnName() {
    String[] ans = new String[1];
    ans[0] = "";
    return ans;
  }

  /**
   * Creates the row heading array.
   *
   * @return a 2D array of numRows arrays filled with one number each, representing which row.
   */
  private String[][] getRowHeading() {
    String[][] ans = new String[numRows][1];
    for (int i = 0; i < numRows; i++) {
      ans[i][0] = Integer.toString(i + 1);
    }
    return ans;
  }

  /**
   * Gets the number of columns necessary to encapsulate the entire model.
   *
   * @param in a map of cells to be represented
   * @return the highest column that corresponds to a cell in the model, or the default numCol,
   *         whichever is bigger.
   */
  private int getMaxColumn(Map<Coord, BasicCell> in) {
    int i = 0;
    for (Map.Entry entry : in.entrySet()) {
      Coord c = (Coord) entry.getKey();
      if (c.col > i) {
        i = c.col;
      }
    }
    if (i > numCols) {
      return i;
    } else {
      return numCols;
    }
  }

  /**
   * Gets the number of rows necessary to encapsulate the entire model.
   *
   * @param in a map of cells to be represented
   * @return the highest row that corresponds to a cell in the model, or the default numRow,
   *         whichever is bigger.
   */
  private int getMaxRow(Map<Coord, BasicCell> in) {
    int i = 0;
    for (Map.Entry entry : in.entrySet()) {
      Coord c = (Coord) entry.getKey();
      if (c.row > i) {
        i = c.row;
      }
    }
    if (i > numCols) {
      return i;
    } else {
      return numCols;
    }
  }

  /**
   * Fills the columnNames array to be A, B, C, ... until the numCols by converting the number
   * column to its corresponding column name.
   */
  private void makeCol() {
    for (int c = 0; c < numCols; c++) {
      colNames[c] = (Coord.colIndexToName(c + 1));
    }
  }

  /**
   * Fills the contents array.  Each array represents a row, with each element in each array
   * representing a cell in the model, or an empty string if there is no cell at that position.
   */
  private void makeRow() {
    for (int row = 0; row < numRows; row++) {
      for (int col = 0; col < numCols; col++) {
        if (model.getRawSpreadsheet().containsKey(new Coord(col + 1, row + 1))) {
          contents[row][col] = model.getCellAtEvaluated(
              col + 1, row + 1).replace("\"", "");
        }
      }
    }
  }

  @Override
  public void makeView() {
    JFrame frame = new JFrame("Worksheet");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    Table pane = new Table(model);
    pane.setOpaque(true);
    frame.setContentPane(pane);
    frame.pack();
    frame.setVisible(true);

  }
}

