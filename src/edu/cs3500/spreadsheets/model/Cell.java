package edu.cs3500.spreadsheets.model;

import edu.cs3500.spreadsheets.model.values.StringValue;
import edu.cs3500.spreadsheets.model.values.Value;
import edu.cs3500.spreadsheets.sexp.Parser;
import edu.cs3500.spreadsheets.sexp.Sexp;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;


/**
 * Defines what a cell is and how it is defined.
 */
public class Cell {

  private Formula contents;
  private Coord coord;
  private Value evaluatedData;
  private String cellValueString;

  /**
   * Construtor for a cell that will have contents.
   * @param coords the coordinates of the cell in currSpreadSheet.
   * @param contents content of the cell, not yet evaluated.
   * @param cellValueString raw string value to be placed in cell.
   */
  public Cell(Coord coords, Formula contents, String cellValueString) {
    this.coord = coords;
    this.contents = contents;
    this.cellValueString = cellValueString;
  }

  /**
   * Construtor for a cell that will be blank.
   * @param coord the coordinates of the cell in currSpreadSheet.
   */
  public Cell(Coord coord) {
    this.coord = coord;
    this.contents = new StringValue("");
    this.evaluatedData = new StringValue("");
    this.cellValueString = "";
  }



  /**
   * Returns the raw contents of the cell.
   * @return raw contents of the cell.
   */
  public Formula getContents() {
    return this.contents;
  }

  /**
   * Gets data that has been evaluated.
   * @return data that has been evaluated.
   */
  public Value getEvaluatedData() {
    return this.evaluatedData;
  }

  /**
   * Setter to set the final evaluated data to appear to user.
   * @param value value to be set as the evaluated value.
   */
  public void setEvaluatedData(Value value) {
    this.evaluatedData = value;
  }

  /**
   * So the cell knows how ot draw itself in the view.
   * @return JPanel of the cell.
   */
  public JPanel drawSelf() {
    JLabel field = new JLabel();
    if (this.toString().equals("")) {
      field = new JLabel(" ");
    }
    else {
      field = new JLabel(this.toString());
    }

    JPanel cell = new JPanel();
    cell.add(field);
    cell.setBackground(new Color(196, 198, 255));
    cell.setPreferredSize(new Dimension(50, 20));
    cell.setBorder(BorderFactory.createLineBorder(Color.white));
    return cell;
  }


  /**
   * This creates a String representing our cell.
   * @return String value of our evaluated data.
   */
  @Override
  public String toString() {
    try {
      return evaluatedData.toString();
    } catch (NullPointerException e) {
      return "";
    }
  }

  /**
   * This function is used to get back the raw string of the cell.
   * @return raw string of the cell.
   */
  public String getRawString() {
    return this.cellValueString;
  }

  /**
   * Sets the raw string value of a cell.
   * @param raw string value to be placed in cell.
   */
  public void setRawString(String raw) {
    this.cellValueString = raw;
  }

  /**
   * Changes what will in the cell.
   * @param contents the content that will be placed in the cell.
   */
  public void setContents(String contents) {

    Sexp sexp;
    if (contents.contains("=")) {
      sexp = Parser.parse(contents.replaceAll("=", ""));
      Formula formula = sexp.accept(new SexpToFormula());
      this.contents = formula;
    }
    else if (contents.equals("")) {
      this.contents = new StringValue("");
    }
    else {
      try {
        sexp = Parser.parse(contents);
        Formula formula = sexp.accept(new SexpToFormula());
        this.contents = formula;
      }
      catch (ArrayIndexOutOfBoundsException e) {
        StringBuilder sb = new StringBuilder();
        sb.append("\"").append(contents).append("\"");
        sexp = Parser.parse(sb.toString());
        Formula formula = sexp.accept(new SexpToFormula());
        this.contents = formula;
      }
      catch (IllegalArgumentException e) {
        sexp = Parser.parse("NaN");
        Formula formula = sexp.accept(new SexpToFormula());
        this.contents = formula;
      }
    }
  }

}








