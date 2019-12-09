package edu.cs3500.spreadsheets.model;

import edu.cs3500.spreadsheets.model.reference.Reference;
import edu.cs3500.spreadsheets.model.values.BooleanValue;
import edu.cs3500.spreadsheets.model.values.NumValue;
import edu.cs3500.spreadsheets.model.values.StringValue;
import edu.cs3500.spreadsheets.sexp.Sexp;
import edu.cs3500.spreadsheets.sexp.SexpVisitor;
import java.util.ArrayList;
import java.util.List;

/**
 * This class should visit each Sexp and convert it into a formula that will be added
 * to cell contents, and it implements SexpVisior.
 */
public class SexpToFormula implements SexpVisitor<Formula> {

  /**
   * This visits a SBoolean and creates a BooleanValue.
   * @param b the boolean value of SBoolean.
   * @return a new BooleanValue for our cell contents.
   */
  @Override
  public Formula visitBoolean(boolean b) {
    return new BooleanValue(b);
  }

  /**
   * This visits SNumber and converts it to a NumValue.
   * @param d the value SNumber.
   * @return a new NumValue for our cell contents.
   */
  @Override
  public Formula visitNumber(double d) {
    return new NumValue(d);
  }

  /**
   * This visits a list and should convert it to a function.
   * @param l the contents of the list (not yet visited)
   * @return a new function based on the list of Sexps.
   */
  @Override
  public Formula visitSList(List<Sexp> l) {
    List<Formula> args = new ArrayList<>();
    for (int i = 0; i < l.size(); i++) {
      args.add(l.get(i).accept(this));
    }

    return new Function(args, l.get(0).toString());

  }

  /**
   * This visits a SSymbol and makes it a Reference for our cells.
   * @param s the string value of the symbol.
   * @return a cell reference based on the string name.
   */
  @Override
  public Formula visitSymbol(String s) {
    switch (s) {
      case "SUM":
        return new StringValue("SUM");

      case "PROD":
        return new StringValue("PROD");

      case "<":
        return new StringValue("<");

      case "COMB":
        return new StringValue("COMB");

      default:
        return new Reference(s);
    }
  }

  /**
   * This visits a SString and converts it to a StringValue.
   * @param s the contents of the string
   * @return a new StringValue for our cell contents.
   */
  @Override
  public Formula visitString(String s) {
    return new StringValue(s);
  }
}
