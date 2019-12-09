package edu.cs3500.spreadsheets.provider.view;

import edu.cs3500.spreadsheets.provider.controller.ControllerAdapter;
import edu.cs3500.spreadsheets.model.Cell;
import edu.cs3500.spreadsheets.model.Coord;
import edu.cs3500.spreadsheets.view.GridPanel;
import edu.cs3500.spreadsheets.view.IView;
import java.util.Map;

/**
 * Adapts the provider's view to our IView.
 */
public class ViewAdapter implements IView {

  public EditableView adaptee;
  public ControllerAdapter adapter;

  /**
   * Constructor.
   * @param adaptee their view.
   * @param adapter controller.
   */
  public ViewAdapter(EditableView adaptee, ControllerAdapter adapter) {
    this.adaptee = adaptee;
    this.adapter = adapter;
  }

  @Override
  public void saveTo(String filePath) {
    throw new UnsupportedOperationException("Not a textual view!");
  }

  @Override
  public void display() {
    adaptee.makeView();
  }

  @Override
  public String buildTextView() {
    throw new UnsupportedOperationException("Not a textual view!");
  }

  @Override
  public GridPanel getCells() {
    throw new UnsupportedOperationException("Client uses Table not GridPanel!");
  }

  @Override
  public void newState(Map<Coord, Cell> newSheet) {
    //not needed
  }
}
