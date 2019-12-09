package edu.cs3500.spreadsheets.provider.model;


import edu.cs3500.spreadsheets.sexp.Sexp;

/**
 * Provider's version of our cell.
 */
public class BasicCell implements Cell {
  edu.cs3500.spreadsheets.model.Cell c;

  /**
   * Constructor which adapts our providers cell to be used as our cell.
   * @param c instance of our cell.
   */
  public BasicCell(edu.cs3500.spreadsheets.model.Cell c) {
    this.c = c;
  }

  @Override
  public String getRawInput() {
    return c.getRawString();
  }

  @Override
  public String getEvaluatedInput() throws IllegalArgumentException {
    return c.getEvaluatedData().toString();
  }

  @Override
  public void setEvaluatedValue(Sexp val) {
    throw new UnsupportedOperationException("Not supported method");
  }
}
