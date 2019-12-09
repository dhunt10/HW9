package edu.cs3500.spreadsheets.model.values;

import edu.cs3500.spreadsheets.model.Cell;
import edu.cs3500.spreadsheets.model.Coord;
import edu.cs3500.spreadsheets.model.Formula;
import java.util.Map;

/**
 * Formula.
 */
public interface Value extends Formula {

  /**
   * Setter to set the value of a value object.
   * @param value value to be set.
   */
  void setValue(Object value);

  /**
   * Evaluates a cell.
   * @return the value of the cell.
   */
  Value evaluate(Map<Coord, Cell> mapOfCells, String useless);
}
