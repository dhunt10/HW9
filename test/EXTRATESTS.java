import static junit.framework.TestCase.assertEquals;

import edu.cs3500.spreadsheets.controller.CompositeSpreadsheetController;
import edu.cs3500.spreadsheets.model.BasicWorksheet.Builder;
import edu.cs3500.spreadsheets.model.Cell;
import edu.cs3500.spreadsheets.model.Coord;
import edu.cs3500.spreadsheets.model.Spreadsheet;
import edu.cs3500.spreadsheets.view.CompositeView;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JButton;
import javax.swing.JTextField;
import org.junit.Test;

/**
 * Tests for both multiple worksheets and full column references.
 */
public class EXTRATESTS {

  @Test
  public void testColumnReference() {
    Builder b = new Builder();
    b.createCell(1,1, String.valueOf(10));
    b.createCell(1,2, String.valueOf(20));
    b.createCell(2,1,"=(SUM A)");
    Spreadsheet s = b.createWorksheet(false);
    assertEquals(String.valueOf(30.0), s.getCellAt(new Coord(2,1)).getEvaluatedData().toString());
    b.createCell(3,1, "=(SUM A B)");
    b.createCell(4,1, "=(SUM A1 B)");
    b.createCell(5,1, "=(SUM 10 B)");
    b.createWorksheet(false);
    assertEquals(String.valueOf(60.0), s.getCellAt(new Coord(3,1)).getEvaluatedData().toString());
    assertEquals(String.valueOf(40.0), s.getCellAt(new Coord(4,1)).getEvaluatedData().toString());
    assertEquals(String.valueOf(40.0), s.getCellAt(new Coord(5,1)).getEvaluatedData().toString());
  }



  @Test
  public void testWorksheetAdd() {
    Builder b = new Builder();
    JButton accept = new JButton();
    JButton cancel = new JButton();
    JTextField text = new JTextField();
    JButton left = new JButton();
    JButton right = new JButton();
    JButton add = new JButton();
    Map<Coord, Cell> cellMap = new HashMap<>();

    Spreadsheet s = b.createWorksheet(false);
    CompositeView view = new CompositeView(cellMap, 50, 50, s);

    CompositeSpreadsheetController compositeSpreadsheetController = new
        CompositeSpreadsheetController(s, 50, 50, text,
        accept, view, cancel, add, left, right);

    b.createCell(1,1,"10");
    b.createWorksheet(false);
    assertEquals(String.valueOf(10.0), s.getCellAt(new Coord(1,1)).getEvaluatedData().toString());

    add.doClick();
    right.doClick();

    Spreadsheet s1 = compositeSpreadsheetController.getMap().get(1);
    Spreadsheet s2 = compositeSpreadsheetController.getMap().get(2);
    assertEquals(String.valueOf(10.0), s1.getCellAt(new Coord(1,1)).getEvaluatedData().toString());
    assertEquals("", s2.getCurrSpreadSheet().get(new Coord(1,1)).toString());

  }
}
