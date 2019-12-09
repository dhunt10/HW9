package edu.cs3500.spreadsheets.model.values;

import edu.cs3500.spreadsheets.model.Cell;
import edu.cs3500.spreadsheets.model.Coord;
import java.util.Map;
import java.util.Objects;

/**
 * Class defines what it is to be of number value within a cell.
 */
public class NumValue implements Value {

  private double number;

  /**
   * Constructor. Takes in a number value and saves it.
   * @param number number to be saved as this.number.
   */
  public NumValue(double number) {
    this.number = number;
  }

  @Override
  public String toString() {
    return String.valueOf(number);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof NumValue)) {
      return false;
    }
    NumValue value = (NumValue) o;
    return Double.compare(value.number, number) == 0;
  }

  /**
   * Standard get function.
   * @return the value of the number.
   */
  public double getValue() {
    return this.number;
  }

  @Override
  public Value evaluate(Map<Coord, Cell> mapOfCells, String useless) {
    return this;
  }

  /**
   * Setter to set the value.
   * @param number number to be set.
   */
  @Override
  public void setValue(Object number) {
    this.number = Double.parseDouble(number.toString());
  }

  @Override
  public int hashCode() {
    return Objects.hash(number);
  }
}

