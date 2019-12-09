package edu.cs3500.spreadsheets.provider.view;

import edu.cs3500.spreadsheets.provider.controller.KeyboardListener;
import edu.cs3500.spreadsheets.model.Coord;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;

/**
 * An interface that allows for editing.  EditableViewInterface contains the table from VisualView,
 * but in addition contains a toolbar that shows the raw contents of the selected cell, as well as
 * buttons for cancel and okay.  These buttons are further explained in EditableView.
 */

public interface EditableViewInterface extends WorksheetView {

  /**
   * Reset the focus on the appropriate part of the view that has the keyboard listener attached to
   * it, so that keyboard events will still flow through.
   */
  void resetFocus();

  /**
   * Sets the original text to the textField.  This method does the same thing as setInput, except
   * it also defines the original text for the current cell.  This is used in case the cancel button
   * is pressed, and the text needs to be reverted.
   *
   * @param text the raw contents of the selected cell
   */
  void setOrigInput(String text);

  /**
   * Adds action listeners to the view.
   *
   * @param listener the listener to be added.
   */
  void addActionListener(ActionListener listener);

  /**
   * Sets a mouse listner to listen for mouse clicks in the view.
   *
   * @param ml the mouse listener
   */
  void setMouseListener(MouseListener ml);

  /**
   * Clears the text bar's text, used when a new cell is selected, or a cell is saved.
   */
  void clearInputString();

  /**
   * Gets the coordinate, 1-indexed of the cell that was clicked on by a mouse event.  This
   * coordinate is used to know which cell in the model to edit.
   *
   * @return the coord of the selected cell.
   */
  Coord getSelectedCell();

  /**
   * Updates the cells in a visual view to reflect their values in the updated model.  This should
   * only update the cells that have been effected by a change.
   */
  void changeCell();

  /**
   * Sets the selected cell to the corresponding coord.  This method is only used for manual
   * manipulation, as the clicking of a cell with accomplish the same thing.
   *
   * @param c the coord that should be the selected cell
   */
  void setSelectedCell(Coord c);

  /**
   * Changes the contents of the JTextField back to what they were before the user started typing.
   * This is called when a user presses the cancel button, changing the JTextField back to the
   * contents of the selected cell, or blank if no cell is selected.
   */
  void revert();

  /**
   * Gets the current text in the JTextField.
   *
   * @return a string of the contents of the current JTextField.
   */
  String getInputString();

  /**
   * Sets the keyboard listener for an editable view.
   *
   * @param kl the keyboard listener
   */
  void setKeyboardListener(KeyboardListener kl);

  /**
   * Sets the input for the text field of an EditableView.
   *
   * @param str the string that should be the input.
   */
  void setInput(String str);
}
