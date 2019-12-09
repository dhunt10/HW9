package edu.cs3500.spreadsheets.view;

import edu.cs3500.spreadsheets.model.Cell;
import edu.cs3500.spreadsheets.model.Coord;
import edu.cs3500.spreadsheets.model.Spreadsheet;
import java.util.Map;

/**
 * The view.
 */
public class GraphicsView implements IView {
  GraphicsFrame frame;
  Map<Coord, Cell> sheet;
  int width;
  int height;
  GridPanel cells;
  int x;
  int y;
  Spreadsheet spreadsheet;

  /**
   * Static view constructor.
   * @param spreadsheet spreadsheet.
   * @param width width of spreadsheet.
   * @param height height of spreadsheet.
   */
  public GraphicsView(Spreadsheet spreadsheet, int width, int height) {
    this.sheet = spreadsheet.getCurrSpreadSheet();
    this.width = width;
    this.height = height;
    this.frame = new GraphicsFrame(sheet, width, height);
    this.cells = frame.getGridPanel();
    this.spreadsheet = spreadsheet;
  }


  @Override
  public void saveTo(String filePath) {
    throw new UnsupportedOperationException("Can't save a visual view");
  }

  @Override
  public void display() {
    frame.display();
  }

  @Override
  public String buildTextView() {
    throw new UnsupportedOperationException(
        "Can't display textual view of visual view");
  }

  /**
   * Returns the current grid.
   * @return current grid.
   */
  public GridPanel getCells() {
    return this.cells;
  }

  @Override
  public void newState(Map<Coord, Cell> newSheet) {
    throw new UnsupportedOperationException(
        "No new view");
  }


}
