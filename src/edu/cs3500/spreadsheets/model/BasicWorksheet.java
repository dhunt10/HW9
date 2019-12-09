package edu.cs3500.spreadsheets.model;

import edu.cs3500.spreadsheets.model.WorksheetReader.WorksheetBuilder;
import edu.cs3500.spreadsheets.model.values.StringValue;
import edu.cs3500.spreadsheets.model.values.Value;
import edu.cs3500.spreadsheets.sexp.Parser;
import edu.cs3500.spreadsheets.sexp.Sexp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This is a worksheet representation that has the basic needs that were
 * in the specifications of the assignment. We made it basic just in case
 * that we are given different types of worksheets later on.
 */
public class BasicWorksheet implements Spreadsheet {

  private final Map<Coord, Cell> currSpreadSheet;
  private List<Coord> coordList;
  public Spreadsheet spreadsheet;

  /**
   * This build the worksheet with given list of cells.
   *
   * @param currSpreadSheet array list of array list holding the cells.
   * @param coordList list of all the cells filled.
   */
  public BasicWorksheet(Map<Coord, Cell> currSpreadSheet, List<Coord> coordList) {
    this.currSpreadSheet = currSpreadSheet;
    this.coordList = coordList;
    fillBlank();
    getEvaluatedCells();
  }

  /**
   * This is a Builder that returns a worksheet with the default parameters.
   *
   * @return a basic build Worksheet.
   */
  public static Builder defaultBuilder() {
    return new Builder();
  }

  /**
   * Function to run through the map of cells and evaluate each individual cell.
   * This function is called directly after a spreadsheet has been created and will subsequently
   * be called after every new cell addition.
   */
  @Override
  public void getEvaluatedCells() {

    Sexp sexp;
    for (Coord item : coordList) {
      if (currSpreadSheet.get(item).getRawString().contains("=")) {
        sexp = Parser.parse(currSpreadSheet.get(item)
            .getRawString().replaceAll("=", ""));
      }
      else {
        if (currSpreadSheet.get(item).getRawString().equals("")) {
          continue;
        }
        sexp = Parser.parse(currSpreadSheet.get(item).getContents().toString());
      }

      try {
        Formula deliverable = sexp.accept(new SexpToFormula());
        currSpreadSheet.get(item).setEvaluatedData(deliverable.evaluate(currSpreadSheet,
            sexp.toString().split(" ")[0]));
      }
      catch (ArrayIndexOutOfBoundsException e) {
        currSpreadSheet.get(item).setEvaluatedData(
            new StringValue(currSpreadSheet.get(item).getRawString()));
      }
    }
  }

  /**
   * Evaluates a single cell, to be used upon receiving a newly updated cell.
   * @param s spreadsheet.
   * @param value value to be placed in cell.
   * @return the evaluated value of the in value.
   */
  public static Value getEvaluatedSingleCell(Spreadsheet s, String value) {
    Sexp sexp = null;
    if (value.contains("=")) {
      sexp = Parser.parse(value.replaceAll("=", ""));
    }

    else {
      if (value.equals("")) {
        return new StringValue("");
      }
      sexp = Parser.parse(value);
    }
    try {
      Formula deliverable = sexp.accept(new SexpToFormula());

      return deliverable.evaluate(s.getCurrSpreadSheet(),
          sexp.toString().split(" ")[0]);

    }
    catch (ArrayIndexOutOfBoundsException e) {
      return new StringValue("NaN");
    }
    catch (IllegalArgumentException e) {
      return new StringValue("NaN");
    }
  }


  /**
   * This creates a builder of a blank cell as a redundancy of the blank cell constructor.
   * @param coord coordinate for new blank cell.
   */
  public void blankCell(Coord coord) {
    Cell cell = new Cell(coord);
    currSpreadSheet.put(coord, cell);
    coordList.add(coord);
  }

  /**
   * This function is called to ensure that there are no null spots in our spreadsheet.
   * What this does is take the largest row and largest column and fill every
   * 'spot' in our spreadsheet (represented as a map) with a cell with no contents.
   */
  public void fillBlank() {

    int highRow = getMaxRow();
    int highCol = getMaxCol();

    for (int i = highCol; i > 0; i--) {
      for (int j = highRow; j > 0; j--) {
        try {
          getCellAt(new Coord(i, j)).getContents();
        }
        catch (NullPointerException e) {

          blankCell(new Coord(i,j));
        }
      }

    }

  }

  /**
   * This function will be used to fill the 'empty' cells with cells that are blank and
   * have no values.
   * @return int, the highest value row.
   */
  public int getMaxRow() {
    int highRow = 0;

    for (Coord coord : coordList) {
      if (coord.row > highRow) {
        highRow = coord.row;
      }
    }
    if (highRow < 50) {
      return 100;
    }
    else {
      return highRow;
    }
  }

  /**
   * This function will be used to fill the 'empty' cells with cells that are blank and
   * have no values.
   * @return int, the highest value column.
   */
  public int getMaxCol() {
    int highCol = 0;

    for (Coord coord : coordList) {
      if (coord.col > highCol) {
        highCol = coord.col;
      }

    }

    if (highCol < 50) {
      return 100;
    }
    else {
      return highCol;
    }
  }

  /**
   * This helps us locate the cells in the Arraylist of Arraylist of cells so we can make changes to
   * specific cells.
   *
   * @param coord is the location of the cell.
   * @return the cell at the given coordinates.
   */
  @Override
  public Cell getCellAt(Coord coord) {
    return currSpreadSheet.get(coord);
  }

  @Override
  public Map<Coord, Cell> getCurrSpreadSheet() {
    return currSpreadSheet;
  }


  /**
   * This is a static class that allows us to build the worksheet.
   */
  public static final class Builder implements WorksheetBuilder<Spreadsheet> {

    private Map<Coord, Cell> currSpreadSheet = new HashMap<Coord, Cell>();
    private List<Coord> coordList = new ArrayList<>();


    /**
     * This is a function that creates a cell as part of the builder to create a worksheet.
     *
     * @param col      the column of the new cell (1-indexed)
     * @param row      the row of the new cell (1-indexed)
     * @param contents the raw contents of the new cell: may be {@code null}, or any string. Strings
     *                 beginning with an {@code =} character should be treated as formulas; all
     *                 other strings should be treated as number or boolean values if possible, and
     *                 string values otherwise.
     * @return a Builder
     */
    @Override
    public Builder createCell(int col, int row, String contents) {
      Coord coord = new Coord(col, row);
      Sexp sexp;
      if (contents.contains("=")) {
        sexp = Parser.parse(contents.replaceAll("=", ""));
      }
      else {
        sexp = Parser.parse(contents);
      }
      Formula formula = sexp.accept(new SexpToFormula());

      Cell cell = new Cell(coord, formula, contents);
      currSpreadSheet.put(coord, cell);
      coordList.add(coord);
      return this;

    }

    /**
     * Getter to return the value of a given key in the spreadsheet map.
     * @param coord coordinate of cell you wish to access.
     * @return the cell of the given coordinate.
     */
    public Cell getCellAt(Coord coord) {
      return currSpreadSheet.get(coord);
    }

    /**
     *This creates the worksheet from the builder.
     * @return BasicWorksheet
     */
    @Override
    public BasicWorksheet createWorksheet() {
      return new BasicWorksheet(currSpreadSheet, coordList);
    }

  }


}