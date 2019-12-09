package edu.cs3500.spreadsheets.provider.model;

import edu.cs3500.spreadsheets.BeyondGood;
import edu.cs3500.spreadsheets.model.Coord;
import edu.cs3500.spreadsheets.model.Spreadsheet;
import java.util.Map;

/**
 * R/W version of our spreadsheet.
 */
public class WorksheetImpl implements Worksheet {

  Spreadsheet spreadsheet;

  /**
   * Constructor for R/W spreadsheet.
   * @param spreadsheet our version of spreadsheet.
   */
  public WorksheetImpl(Spreadsheet spreadsheet) {
    this.spreadsheet = spreadsheet;
  }

  @Override
  public void evaluateAll() throws IllegalArgumentException {
    spreadsheet.getEvaluatedCells();
  }

  @Override
  public String getCellAtRaw(int i, int i1) throws IllegalArgumentException {
    return spreadsheet.getCurrSpreadSheet().get(new Coord(i, i1)).getRawString();
  }

  @Override
  public String getCellAtEvaluated(int i, int i1) throws IllegalArgumentException {
    return spreadsheet.getCurrSpreadSheet().get(new Coord(i, i1)).getEvaluatedData().toString();
  }

  @Override
  public void changeCellContentsOrReplaceCell(Coord c, String contents) {
    spreadsheet.getCellAt(c).setRawString(contents);
    spreadsheet.getCellAt(c).setContents(contents);
    BeyondGood.updateCurrentView(c.toString(), contents, spreadsheet);
  }

  public Spreadsheet getBasicWorksheet() {
    return spreadsheet;
  }


  @Override
  public Map<Coord, BasicCell> getRawSpreadsheet() {
    return ViewModelImpl.mapConverter(spreadsheet.getCurrSpreadSheet());
  }
}
