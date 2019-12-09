/*import static junit.framework.TestCase.assertEquals;

import edu.cs3500.spreadsheets.model.Cell;
import edu.cs3500.spreadsheets.model.Coord;
import edu.cs3500.spreadsheets.model.Formula;
import edu.cs3500.spreadsheets.model.Function;
import edu.cs3500.spreadsheets.model.reference.Reference;
import edu.cs3500.spreadsheets.model.values.BooleanValue;
import edu.cs3500.spreadsheets.model.values.NumValue;
import edu.cs3500.spreadsheets.model.values.StringValue;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;

/**
 * Testing for functionality of cell.
 */
/*public class CellTests {

  @Test
  public void testBoolean() {
    Cell test = new Cell(new Coord(1, 1), new BooleanValue(false));
    assertEquals("false", test.getContents().toString());
  }

  @Test
  public void testString() {
    Cell test = new Cell(new Coord(1, 1), new StringValue("helloworld"));
    assertEquals("helloworld", test.getContents().toString());
  }

  @Test
  public void testDouble() {
    Cell test = new Cell(new Coord(1, 1), new NumValue(46.9));
    assertEquals("46.9", test.getContents().toString());
  }

  @Test
  public void testBlank() {
    Cell test = new Cell(new Coord(1, 1));
    assertEquals("", test.getContents().toString());
  }

  @Test
  public void testFormula() {
    List<Formula> lists = new ArrayList<>();
    lists.add(new NumValue(10));
    lists.add(new NumValue(2));
    Cell test = new Cell(new Coord(1,1), new Function("=(SUM 10 + 2)", lists, "SUM"));
    assertEquals(12, test.getContents());

  }

  @Test
  public void testReference() {

    Cell test = new Cell(new Coord(1,1), new NumValue(4));
    Cell test2 = new Cell(new Coord(1,2), new NumValue(8));
    Cell test3 = new Cell(new Coord(1,3), new Function("=(SUM A1 + A2)"));
    assertEquals(12, test3.getContents());

  }

  @Test (expected = IllegalArgumentException.class)
  public void testSameReference() {
    Cell test1 = new Cell(new Coord(1,1), new Reference("A1"));
  }

  @Test (expected = IllegalArgumentException.class)
  public void testSameReferenceIndirect() {
    Cell test1 = new Cell(new Coord(1,1), new NumValue(3));
    Cell test2 = new Cell(new Coord(1,2), new Reference("A1 + A3"));
    Cell test3 = new Cell(new Coord(1,3), new Reference("A2 + A2"));
  }

  @Test
  public void testRange() {
    Cell test1 = new Cell(new Coord(1,1), new NumValue(3));
    Cell test2 = new Cell(new Coord(1,2), new NumValue(5));
    Cell test3 = new Cell(new Coord(1,3), new Function("=(SUM A1:A2)"));
    assertEquals(8, test3.getContents());
  }

  @Test
  public void testAllCells() {

    Cell test = new Cell(new Coord(1,1), new NumValue(4));
    Cell test2 = new Cell(new Coord(1,2), new BooleanValue(true));
    Cell test3 = new Cell(new Coord(1,2), new StringValue("Hello"));
    Cell test4 = new Cell(new Coord(1,2), new Reference("A1"));
    Cell test5 = new Cell(new Coord(1,3), new Function("=(A1 + A2)"));
    assertEquals(12, test3.getContents());

  }

  @Test (expected = IllegalArgumentException.class)
  public void blankCell() {
    Cell test1 = new Cell(new Coord(1,1));
  }

  @Test (expected = IllegalArgumentException.class)
  public void testFormulaFalse()  {
    Cell test = new Cell(new Coord(1,1), new Function("=(SUM 10 TRUE)"));
    assertEquals(12, test.getContents());

  }
}*/
