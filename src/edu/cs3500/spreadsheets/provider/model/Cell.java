package edu.cs3500.spreadsheets.provider.model;

import edu.cs3500.spreadsheets.sexp.Sexp;

/**
 * A cell is a data type that can be blank, contain a value, or contain a formula.  A value is a one
 * of a boolean, a double, or a string.  A formula is one of a value, a reference to a rectangular
 * region of cells in its spreadsheet, or a function applied to one or more formulas as its
 * arguments.  A function is represented as a list of values, beginning with a string representing
 * the function's name (for example, SUM).  A cells raw contents can be evaluated. When evaluated, a
 * reference value becomes the value of the referenced cell.  A function is evaluated to a single
 * value, either a string, a number, or a boolean.  A single value evaluates to itself.
 */

public interface Cell {

  /**
   * Returns the unevaluated contents of this cell as a string before being parsed by the parser.
   * This string is exactly the contents that the user inputted into the cell.
   *
   * @return the string representing the raw input of a cell.
   */
  public String getRawInput();

  /**
   * Returns the evaluated contents of this cell as a string.  If this cell holds a string value,
   * that value is wrapped in quotes.  If there are quotes within that string, they are escaped with
   * a backslash and any backslashes should are escaped with another backslash.  If this cell holds
   * a double value, its value is converted to a string, and padded to six places. If this cell
   * holds a boolean value, the boolean is converted to a string.  The evaluated value of a cell
   * will never be a list, because a list will always evaluate to a string, number, or boolean, or
   * otherwise throw an error.  If the cell is blank, an empty string will be returned.
   *
   * @return a string representing the evaluated contents of this cell.
   * @throws IllegalArgumentException if this cell is self-referential, is wrongly formatted, tries
   *                                  to evaluate a function which is not yet created, or passes in
   *                                  the wrong inputs to an already created function.
   */
  public String getEvaluatedInput() throws IllegalArgumentException;

  /**
   * Changes the evaluated value of a cell after it is evaluated in the scope of a larger
   * worksheet.  This should happen immediately after each cell is evaluated so that there is not
   * a long period of time in which the cell's evaluated contents are not blank, but do not
   * represent the wrong raw inputs.
   */
  public void setEvaluatedValue(Sexp val);

}
