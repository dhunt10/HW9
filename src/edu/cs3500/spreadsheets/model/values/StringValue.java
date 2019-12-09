package edu.cs3500.spreadsheets.model.values;

import edu.cs3500.spreadsheets.model.Cell;
import edu.cs3500.spreadsheets.model.Coord;
import java.util.Map;
import java.util.Objects;

/**
 * Class defines what it is to be a string within a cell.
 */
public class StringValue implements Value {
  private String string;

  /**
   * Constructor. Takes in a string value and saves it.
   * @param string string to be saved as this.string.
   */
  public StringValue(String string) {
    this.string = string;
  }

  @Override
  public String toString() {
    return String.valueOf(string);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof StringValue)) {
      return false;
    }
    StringValue value = (StringValue) o;
    return value.string.equals(string);
  }

  /**
   * Standard get function.
   * @return the value of the String.
   */
  public String getValue() {
    return this.string;
  }

  @Override
  public void setValue(Object string) {
    this.string = string.toString();
  }

  @Override
  public Value evaluate(Map<Coord, Cell> mapOfCells, String useless) {
    return this;
  }

  @Override
  public int hashCode()  {
    return Objects.hash(string);
  }

}

