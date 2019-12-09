package edu.cs3500.spreadsheets.provider.model;

import edu.cs3500.spreadsheets.model.Cell;
import edu.cs3500.spreadsheets.model.Coord;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Read only version of the spreadsheet.
 */
public class ViewModelImpl implements ViewModel {

  Worksheet basicWorksheet;

  /**
   * Constructor of which is a read only spreadsheet.
   * @param basicWorksheet our version of spreadsheet.
   */
  public ViewModelImpl(Worksheet basicWorksheet) {
    this.basicWorksheet = basicWorksheet;
  }

  @Override
  public Map<Coord, BasicCell> getRawSpreadsheet() {
    return basicWorksheet.getRawSpreadsheet();
    //Map<Coord, BasicCell> adaptee = new HashMap<>();
    //adaptee = mapConverter(basicWorksheet.getCurrSpreadSheet());
    //return adaptee;
  }

  /**
   * Converts our map to their map of cells.
   * @param map map of coords: cells.
   * @return the provider's map of coords: basicCells.
   */
  public static Map<Coord, BasicCell> mapConverter(
      Map<Coord, edu.cs3500.spreadsheets.model.Cell> map) {
    Map<Coord, BasicCell> adaptee = new HashMap<>();
    Iterator mapIterator = map.entrySet().iterator();
    while (mapIterator.hasNext()) {
      Map.Entry mapElement = (Map.Entry)mapIterator.next();
      BasicCell bc = new BasicCell((Cell) mapElement.getValue());
      adaptee.put((Coord) mapElement.getKey(), bc);
    }
    return adaptee;
  }

  /**
   * Converts provider's map to map of our cells.
   * @param map map of coords: basicCells.
   * @return our map of coords: Cells.
   */
  public static Map<Coord, edu.cs3500.spreadsheets.model.Cell> reverseMapConverter(
      Map<Coord, BasicCell> map) {
    Map<Coord, edu.cs3500.spreadsheets.model.Cell> adaptee = new HashMap<>();
    Iterator mapIterator = map.entrySet().iterator();
    while (mapIterator.hasNext()) {
      Map.Entry mapElement = (Map.Entry)mapIterator.next();
      Cell bc = new edu.cs3500.spreadsheets.model.Cell((Coord) mapElement.getValue());
      adaptee.put((Coord) mapElement.getKey(), bc);
    }

    return adaptee;
  }

  @Override
  public String getCellAtRaw(int i, int i1) throws IllegalArgumentException {
    //Coord coord = new Coord(i, i1);
    //return basicWorksheet.getCellAt(coord).getRawString();
    return basicWorksheet.getCellAtRaw(i, i1);
  }

  @Override
  public String getCellAtEvaluated(int i, int i1) throws IllegalArgumentException {
    //Coord coord = new Coord(i, i1);
    //return basicWorksheet.getCellAt(coord).getEvaluatedData().toString();
    return basicWorksheet.getCellAtEvaluated(i, i1);
  }
}
