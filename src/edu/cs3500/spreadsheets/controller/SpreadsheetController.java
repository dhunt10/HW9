package edu.cs3500.spreadsheets.controller;

import edu.cs3500.spreadsheets.model.Spreadsheet;

/**
 * Controller responsible for communicating between view and model.
 */
public interface SpreadsheetController {

  /**
   * updates the program with new information.
   * @param coordinate the cell coordinate.
   * @param inString the raw string to be placed in a cell.
   * @param s the spreadsheet.
   */
  void updateProgram(String coordinate, String inString, Spreadsheet s);

  /**
   * Used for testing purposes only.
   * @param x the manually set x.
   */
  void setX(int x);

  /**
   * used for testing purposes only.
   * @param y the manually set y.
   */
  void setY(int y);
}