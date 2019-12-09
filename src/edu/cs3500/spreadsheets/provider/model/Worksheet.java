package edu.cs3500.spreadsheets.provider.model;

import edu.cs3500.spreadsheets.model.Coord;
import java.util.Map;

/**
 * A worksheet is a combination of cells, represented as a single data structure.
 */

public interface Worksheet {

  /**
   * Evaluates all the cells in a rawSpreadsheet to create a corresponding evaluatedSpreadsheet.
   * This is done cell by cell in order of creation.  If a cell references a cell that is not yet
   * created, it treats that cell as a blank cell, with all default values.  For example, this means
   * that if a cell depends on the doubleValue of another cell, it accepts 0.0 as that number.
   *
   * @throws IllegalArgumentException if the cell is self-referencing, or is badly formatted.
   */
  public void evaluateAll() throws IllegalArgumentException;

  /**
   * Returns the raw contents of a cell at a given position in the hashmap of the rawSpreadsheet.
   * This string represents the exact contents that a user used when creating this cell.
   *
   * @param i  the col 1-indexed of the desired cell
   * @param i1 teh row 1-indexed of the desired cell
   * @return the raw contents of a cell at the desired position
   * @throws IllegalArgumentException if the cell called does not exist in this spreadsheet.
   */
  public String getCellAtRaw(int i, int i1) throws IllegalArgumentException;

  /**
   * Returns a string representation of the evaluated cell.
   *
   * @param i  the col 1-indexed of the desired cell
   * @param i1 teh row 1-indexed of the desired cell
   * @return the cell at the desired position
   * @throws IllegalArgumentException if the cell called does not exist in this spreadsheet, or the
   *                                  evaluation of that cell throws an error, because it is
   *                                  self-referencing or malformed
   */
  public String getCellAtEvaluated(int i, int i1) throws IllegalArgumentException;

  /**
   * Changes the cell's contents if the cell at the given coordinate  already exists, otherwise
   * creates a new cell with the given contents at the given coordinate.
   *
   * @param c        the col and row 1-indexed of the desired cell
   * @param contents the contents of the cell in question
   * @throws IllegalArgumentException if the cell called does not exist in this spreadsheet, or the
   *                                  evaluation of that cell throws an error, because it is
   *                                  self-referencing or malformed
   */
  public void changeCellContentsOrReplaceCell(Coord c, String contents) throws
      IllegalArgumentException;

  /**
   * Gets the raw spreadsheet of an instance of ViewModel.  The raw spreadsheet is represented as a
   * HashMap of Coordinates connected to unevaluated BasicCells.  This method is used to get the
   * input for a worksheet as it was given.
   *
   * @return a Hashmap representing the original, unevaluated worksheet of a model
   */

  Map<Coord, BasicCell> getRawSpreadsheet();
}
