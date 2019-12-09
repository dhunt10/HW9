package edu.cs3500.spreadsheets.view;

import edu.cs3500.spreadsheets.model.Cell;
import edu.cs3500.spreadsheets.model.Coord;
import java.util.Map;

/**
 * View.
 */
public interface IView {


  /**
   * Where to save the file.
   * @param filePath the path which the file will be saved.
   */
  void saveTo(String filePath);

  /**
   * Sets the view to be visible.
   */
  void display();

  /**
   * creator of the tex view.
   * @return text view is a string.
   */
  String buildTextView();

  /**
   * Returns the cells.
   * @return cells.
   */
  GridPanel getCells();

  /**
   * Creates a new spreadsheet.
   * @param newSheet the sheet to go off of in map form.
   */
  void newState(Map<Coord, Cell> newSheet);
}
