package edu.cs3500.spreadsheets.model.reference;

import edu.cs3500.spreadsheets.model.Cell;
import edu.cs3500.spreadsheets.model.Coord;
import edu.cs3500.spreadsheets.model.Formula;
import edu.cs3500.spreadsheets.model.values.NumValue;
import edu.cs3500.spreadsheets.model.values.StringValue;
import edu.cs3500.spreadsheets.model.values.Value;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Reference is a type that references any cell.
 * A reference can be a reference to single cell or a block of cells.
 * For example: A1 would be a single reference and A1:B7 would be a block of cells.
 */
public class Reference implements Formula {

  List<String> refs;
  String references;
  List<Coord> evaluatedRefs;
  String function = "default";

  /**
   * Constructor. takes in a string that should be formatted as ["Cell1:Cell2"] or ["Cell1]. The
   * constructor takes in a string, calls a function to make a list of the cells. being references
   * and then calls an additional function to return a list of coordinates.
   *
   * @param references a string formatted as such: ["Cell1:Cell2"] or ["Cell1].
   */
  public Reference(String references) {
    this.references = references;
    String[] splitter = references.split(":");

    if (splitter.length > 1) {
      refs = referenceListMaker(splitter[0], splitter[1]);
    } else {
      refs = referenceListMaker(splitter[0]);
    }

    this.evaluatedRefs = getRefs();
  }

  /**
   * Constructor, takes ina  single reference.
   * @param references single reference to another cell.
   * @param function the type of function action to be acted on the reference.
   */
  public Reference(String references, String function) {
    this.function = function;
    this.references = references;
    String[] splitter = references.split(":");

    if (splitter.length > 1) {
      refs = referenceListMaker(splitter[0], splitter[1]);
    } else {
      refs = referenceListMaker(splitter[0]);
    }

    this.evaluatedRefs = getRefs();
  }

  /**
   * Makes a list of single reference cell. Polymorphic design allows for just one cell.
   *
   * @param firstBound the single cell to be parsed through.
   * @return returns a list of 1 single reference cell.
   */
  private List<String> referenceListMaker(String firstBound) {

    String[] coord1 = firstBound.split("(?<=\\D)(?=\\d)", 2);

    List<String> bounds = new ArrayList<>();
    StringBuilder sb = new StringBuilder();
    sb.append(sb.append((((coord1[0].charAt(coord1[0].length() - 1))))));
    sb.append(Integer.parseInt(coord1[1]));

    bounds.add(sb.toString());

    return bounds;
  }

  /**
   * Makes a list of all the cells within a given range.
   *
   * @param firstBound  range is defined with firstBound as the start.
   * @param secondBound range is defined with second bound as the start.
   * @return a list of strings that hold all the points being referenced.
   */
  private List<String> referenceListMaker(String firstBound, String secondBound) {
    List<String> bounds = new ArrayList<>();
    String[] coord1 = firstBound.split("(?<=\\D)(?=\\d)", 2);
    String[] coord2 = secondBound.split("(?<=\\D)(?=\\d)", 2);

    int zeroDiff = Math.abs((char) (((coord1[0].charAt(coord1[0].length() - 1)) + 1))
        - (char) (((coord2[0].charAt(coord2[0].length() - 1)) + 1)));
    zeroDiff = zeroDiff + 1;
    int oneDiff = Math.abs(Integer.parseInt(coord1[1]) - Integer.parseInt(coord2[1])) + 1;

    char coordOneValue = (char) (((coord1[0].charAt(coord1[0].length() - 1)) + 1));
    char coordTwoValue = (char) (((coord2[0].charAt(coord2[0].length() - 1)) + 1));


    if (coordOneValue == coordTwoValue) {
      for (int i = 0; i < oneDiff; i++) {
        StringBuilder sb = new StringBuilder();
        sb.append((char) (((coord1[0].charAt(coord1[0].length() - 1)) + i - i)));
        sb.append(Integer.parseInt(coord1[1]) + i);
        bounds.add(sb.toString());
      }

    } else if (coord1[1].equals(coord2[1])) {
      for (int i = 0; i < zeroDiff; i++) {
        StringBuilder sb = new StringBuilder();
        sb.append((char) (((coord1[0].charAt(coord1[0].length() - 1)) + i)));
        sb.append(Integer.parseInt(coord1[1]));
        bounds.add(sb.toString());
      }
    } else {
      for (int i = 0; i < zeroDiff; i++) {
        for (int j = 0; j < oneDiff; j++) {
          StringBuilder sb = new StringBuilder();
          sb.append((char) (((coord1[0].charAt(coord1[0].length() - 1)) + i)));
          sb.append(Integer.parseInt(coord1[1]) + j);
          bounds.add(sb.toString());
        }
      }

    }

    return bounds;
  }

  /**
   * converts the strings made in referenceListMaker to coordinates.
   *
   * @return a list of coordinates being referenced.
   */
  private List<Coord> getRefs() {
    List<Coord> references = new ArrayList<>();
    for (int i = 0; i < this.refs.size(); i++) {
      String[] coord1 = refs.get(i).split("(?<=\\D)(?=\\d)", 2);
      int col = Coord.colNameToIndex(String.valueOf(this.refs.get(i).charAt(0)));
      int row = Integer.parseInt(String.valueOf(coord1[1]));

      Coord coord = new Coord(col, row);
      references.add(coord);
    }
    return references;
  }

  @Override
  public Value evaluate(Map<Coord, Cell> mapOfCells, String useless) {

    double sum = 1;

    if (useless.equals("(SUM")) {
      for (int i = 0; i < evaluatedRefs.size(); i++) {
        try {
          sum = sum + Double.parseDouble(String.valueOf(
              mapOfCells.get(evaluatedRefs.get(i)).getEvaluatedData()));
        }
        catch (NullPointerException e) {
          continue;
        }
        catch (NumberFormatException e) {
          continue;
        }

      }
      sum = sum - 1;
    }
    else if (useless.equals("(PROD")) {
      for (int i = 0; i < evaluatedRefs.size(); i++) {


        try {
          sum = sum * Double.parseDouble(String.valueOf(mapOfCells.get(
              evaluatedRefs.get(i)).getEvaluatedData()));
        }
        catch (NullPointerException e) {
          continue;
        }
        catch (NumberFormatException e) {
          continue;
        }
      }
    }
    else if (useless.equals("(COMB")) {
      StringBuilder sb = new StringBuilder();

      for (int i = 0; i < evaluatedRefs.size(); i++) {
        try {
          sb.append(mapOfCells.get(evaluatedRefs.get(i)).getEvaluatedData());
          return new StringValue(sb.toString());
        }
        catch (NullPointerException e) {
          continue;
        }
        catch (NumberFormatException e) {
          continue;
        }
      }
    }
    else {
      try {
        return mapOfCells.get(evaluatedRefs.get(0)).getEvaluatedData();
      }
      catch (NullPointerException e) {
        return new StringValue("");
      }
      catch (NumberFormatException e) {
        return new StringValue("");
      }
    }
    return new NumValue(sum);
  }
}