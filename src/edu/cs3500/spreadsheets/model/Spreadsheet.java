package edu.cs3500.spreadsheets.model;

import java.util.Map;

/**
 * This interface is for all types of worksheets, which are made up of cells.
 */
public interface Spreadsheet {

  /**
   * This helps us get each cell in the worksheet based on their coordinate.
   * @param coord is the location of the cell.
   * @return the cell at the specified coordinate.
   */
  Cell getCellAt(Coord coord);

  /**
   * Getter to return the current spreadsheet.
   * @return the current spreadsheet in map form.
   */
  Map<Coord, Cell> getCurrSpreadSheet();

  /**
   * Get the highest column needed so that we can create blank cells if
   * necessary that are missing in the read in file.
   * @return int that represents the highest column # in the spreadsheet.
   */
  int getMaxCol();

  /**
   * Get the highest row needed so that we can create blank cells if
   * necessary that are missing in the read in file.
   * @return int that represents the highest row # in the spreadsheet.
   */
  int getMaxRow();

  /**
   * Updates the spreadsheet to evaluate all of the cells.
   */
  void getEvaluatedCells();
}
