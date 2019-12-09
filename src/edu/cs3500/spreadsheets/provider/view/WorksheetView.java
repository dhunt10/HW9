package edu.cs3500.spreadsheets.provider.view;

/**
 * A worksheet view is a way to represent a worksheet model.  A visual worksheet is made, or the
 * contents of a model are saved to a file.
 */

public interface WorksheetView {

  /**
   * Makes a visual representation of a model, or saves a model to a file.
   */
  public void makeView();

}
