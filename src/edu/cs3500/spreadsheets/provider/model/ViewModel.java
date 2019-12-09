package edu.cs3500.spreadsheets.provider.model;

import edu.cs3500.spreadsheets.model.Coord;
import java.util.Map;

/**
 * An interface which contains all the getter methods from the Worksheet interface, and none of the
 * setters.  Instances of this interface are safe to use in the view, since there are no methods
 * which modify the model.
 */

public interface ViewModel {

  /**
   * Gets the raw spreadsheet of an instance of ViewModel.  The raw spreadsheet is represented as a
   * HashMap of Coordinates connected to unevaluated BasicCells.  This method is used to get the
   * input for a worksheet as it was given.
   *
   * @return a Hashmap representing the original, unevaluated worksheet of a model
   */

  Map<Coord, BasicCell> getRawSpreadsheet();

  /**
   * Returns the raw contents of a cell at a given position in the hashmap of the rawSpreadsheet.
   * This string represents the exact contents that a user used when creating this cell.
   *
   * @param i  the col 1-indexed of the desired cell
   * @param i1 teh row s-indexed of the desired cell
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

}
