/*import static junit.framework.TestCase.assertEquals;

import com.sun.jdi.Value;
import edu.cs3500.spreadsheets.model.Cell;
import edu.cs3500.spreadsheets.model.Coord;
import edu.cs3500.spreadsheets.model.Formula;
import edu.cs3500.spreadsheets.model.Function;
import edu.cs3500.spreadsheets.model.values.NumValue;
import edu.cs3500.spreadsheets.model.values.StringValue;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;


/**
 * Testing functionality of functions SUM, PROD, < and COMB.
 *//*
public class ValueTests {

  @Test
  public void testSUM() {
    List<Formula> lists = new ArrayList<>();
    lists.add(new NumValue(10));
    lists.add(new NumValue(2));

    Cell test = new Cell(new Coord(1,1), new Function("=(< 10 2)", lists, "SUM"));
    assertEquals(12, test.getContents());
  }

  @Test
  public void testPRODUCT() {
    List<Formula> lists = new ArrayList<>();
    lists.add(new NumValue(10));
    lists.add(new NumValue(2));
    Cell test = new Cell(new Coord(1,1), new Function("=(PROD 10 2)", lists, "PROD"));
    assertEquals(20, test.getContents());
  }

  @Test
  public void testLessThan() {
    List<Formula> lists = new ArrayList<>();
    lists.add(new NumValue(10));
    lists.add(new NumValue(2));
    Cell test = new Cell(new Coord(1,1), new Function("=(< 2 10)", lists, "<"));
    assertEquals(true, test.getContents());
  }

  @Test
  public void testCombine() {
    List<Formula> lists = new ArrayList<>();
    lists.add(new StringValue("HELLO"));
    lists.add(new StringValue("DARIN"));
    lists.add(new StringValue("AND"));
    lists.add(new StringValue("SATWIK"));
    Cell test = new Cell(new Coord(1,1),
        new Function("=(COMB HELLO DARIN AND SATWIK", lists, "COMB"));
    assertEquals("HELLODARINANDSATWIK", test.getContents());
  }


}*/
