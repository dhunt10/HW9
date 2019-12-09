package edu.cs3500.spreadsheets.model;

import edu.cs3500.spreadsheets.model.values.Value;
import java.util.Map;

/**
 * This represents any function, reference, or value that goes into a cell.
 */
public interface Formula {

  /**
   * This lets us see what the formula is as a string.
   * @return a String representation of a formula.
   */
  @Override
  String toString();

  /**
   * This evaluates a given cell's formula and returns the final value.
   * @param mapOfCells this map will be used in the event that a cell contains a reference.
   * @param formula string to be placed in cell, to be evaluated.
   * @return a final value to be added as part of the cell's evaluated content.
   */
  Value evaluate(Map<Coord, Cell> mapOfCells, String formula);
}
