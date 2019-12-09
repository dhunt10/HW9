package edu.cs3500.spreadsheets.view;

import edu.cs3500.spreadsheets.model.Cell;
import edu.cs3500.spreadsheets.model.Coord;
import edu.cs3500.spreadsheets.model.Spreadsheet;
import java.util.Map;

/**
 * The view.
 */
public class CompositeView implements IView {

  CompositeFrame frame;
  Map<Coord, Cell> sheet;
  int width;
  int height;
  GridPanel cells;
  Spreadsheet model;
  IView view;

  /**
   * constructor to have a composite dynamic view.
   * @param sheet spreadsheet in map form.
   * @param width width of the spreadsheet.
   * @param height height of teh spreadsheet.
   * @param model spreadsheet.
   */
  public CompositeView(Map<Coord, Cell> sheet, int width, int height, Spreadsheet model) {
    this.view = view;
    this.model = model;
    this.sheet = sheet;
    this.width = width;
    this.height = height;
    this.frame = new CompositeFrame(sheet, width, height, model, this);
    //this.cells = frame.getGridPanel();
  }

  @Override
  public void saveTo(String filePath) {
    throw new UnsupportedOperationException("Can't save a visual view");
  }

  @Override
  public void display() {
    frame.display();
  }

  @Override
  public String buildTextView() {
    throw new UnsupportedOperationException(
        "Can't display textual view of visual view");
  }

 
  @Override
  public GridPanel getCells() {
    return this.cells;
  }



  @Override
  public void newState(Map<Coord, Cell> state) {
    this.sheet = state;
    frame.newState(state);
  }


}
