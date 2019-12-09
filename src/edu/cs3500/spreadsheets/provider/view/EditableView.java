package edu.cs3500.spreadsheets.provider.view;

import edu.cs3500.spreadsheets.provider.model.ViewModel;
import edu.cs3500.spreadsheets.provider.controller.KeyboardListener;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;


import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JScrollPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import edu.cs3500.spreadsheets.model.Coord;

/**
 * A view for editing worksheets.  An EditableView contains the same table as VisualView.  In
 * addition, an EditableView contains a JToolBar which contains a JTextField that shows the raw
 * contents of a mouse-clicked cell.  This textField is editable.  The JToolBar also features an
 * okay button, and a cancel button.  If the okay button is pressed, the text within the JTextField
 * replaces the contents of the currently selected cell.  If the cancel button is pressed, the
 * contents of the JTextField revert to the original raw contents of the selected cell.  All these
 * listeners are dealt with in the controller.  Editable View takes in a ViewModel.  It has no
 * ability to mutate the model.  In addition to the button listeners, there is a mouse listener used
 * to determine the selected cell based on where the user presses.
 */

public class EditableView extends JFrame implements EditableViewInterface {

  private ViewModel model;
  private JTable table;
  private JToolBar jtb;
  private int numRows = 26;

  private JButton okayButton;
  private JButton cancelButton;
  private JTextField input;
  // represents the initial text that appears in the JTextField upon selecting a cell
  private String origText;

  private int cellHeight = 20;
  private JScrollPane pane1;
  private Coord selectedCell;

  /**
   * Creates an EditableView for the given model.
   * @param model the model for this view
   */

  public EditableView(ViewModel model) {
    this.model = model;
    this.table = new Table(model).getTable(true);
    pane1 = new JScrollPane(table);

    // Creates the toolBar
    JToolBar tb = new JToolBar();
    input = new JTextField(10);
    tb.add(input);
    okayButton = new JButton("Okay");
    okayButton.setActionCommand("Okay Button");
    tb.add(okayButton);
    cancelButton = new JButton("Cancel");
    cancelButton.setActionCommand("Cancel Button");
    tb.add(cancelButton);
    this.jtb = tb;
    table.setCellSelectionEnabled(true);

    // creates a column header, made up of numbers 1 - numRows.
    JTable fixedColOne = new JTable(getRowHeading(), getEmptyColumnName());
    // Sets teh size of the column header to match the size of other columns.
    int cellWidth = 100;
    int height = 500;
    fixedColOne.setPreferredScrollableViewportSize(new Dimension(cellWidth, height));
    fixedColOne.setSize(cellWidth, cellHeight * numRows);
    fixedColOne.setRowHeight(cellHeight);
    // sets the background color of the column header to be light gray
    fixedColOne.setBackground(Color.lightGray);
    // sets the column header to the scrollpane
    pane1.setRowHeaderView(fixedColOne);
    MouseListener ml = new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        int clickedRow = table.getSelectedRow();
        int clickedCol = table.getSelectedColumn();
        if (clickedCol >= 0 && clickedRow >= 0) {
          selectedCell = new Coord(clickedCol + 1, clickedRow + 1);
        }
      }
    };
    table.addMouseListener(ml);
    this.setSelectedCell(new Coord(1, 1));
  }

  @Override
  public void setMouseListener(MouseListener ml) {
    table.addMouseListener(ml);
  }


  /**
   * Determines if a string is a valid coordinate.
   *
   * @param s the string in question
   * @return true if the string contains only letters, then only numbers
   */
  private boolean isCoord(String s) {
    if (s.startsWith("\"")) {
      s = s.substring(1, s.length() - 1);
    }
    if (s == null) {
      return false;
    }
    if (s.equals("")) {
      return false;
    }
    boolean hasNumber = false;
    int firstNumber = -1;
    for (int i = 0; i < s.length(); i++) {
      if (Character.isDigit(s.charAt(i))) {
        hasNumber = true;
        if (firstNumber == -1) {
          firstNumber = i;
        }
      }
    }
    if (!hasNumber) {
      return false;
    }
    if (firstNumber == 0) {
      return false;
    }
    boolean hasOnlyLettersThanOnlyNumbers = true;
    for (int i = 0; i < firstNumber; i++) {
      if (!(s.charAt(i) >= 'A' && s.charAt(i) <= 'Z')) {
        hasOnlyLettersThanOnlyNumbers = false;
      }
    }
    for (int i = firstNumber; i < s.length(); i++) {
      if (!Character.isDigit(s.charAt(i))) {
        hasOnlyLettersThanOnlyNumbers = false;
      }
    }
    return hasOnlyLettersThanOnlyNumbers;
  }

  /**
   * Determines if a string is a valid range.
   *
   * @param s the string in question
   * @return true if the string contains two valid coords separated by ":"
   */
  private boolean isRange(String s) {
    if (s == null) {
      return false;
    }
    if (!s.contains(":")) {
      return false;
    }
    String before = s.substring(0, s.indexOf(":"));
    String after = s.substring(s.indexOf(":") + 1);
    return isCoord(before) && isCoord(after);
  }

  @Override
  public void setInput(String text) {
    input.setText(text);
  }

  /**
   * Sets the original input of a cell.  If the input is an equation, or reference, the
   * corresponding equal sign and parentheses are added.
   * @param text the text that should be in the input
   */
  public void setOrigInput(String text) {
    if (isCoord(text)) {
      if (text.startsWith("\"")) {
        text = text.substring(1, text.length() - 1);
      }
      text = "=" + text;
    }
    if (text.startsWith("SUM")) {
      text = "=(" + text + ")";
    }
    else if (text.startsWith("PRODUCT")) {
      text = "=(" + text + ")";
    }
    else if (text.startsWith("<")) {
      text = "=(" + text + ")";
    }
    else if (text.startsWith("(")) {
      text = "=" + text;
    }
    else if (text.startsWith("SUBSTRING")) {
      text = "=(" + text + ")";
    }
    if (isRange(text)) {
      text = "=(" + text;
    }
    this.origText = text;
    input.setText(text);
  }

  @Override
  public void revert() {
    input.setText(origText);
  }

  @Override
  public String getInputString() {
    return input.getText();
  }


  @Override
  public void setKeyboardListener(KeyboardListener kl) {
    table.addKeyListener(kl);
  }

  @Override
  public void clearInputString() {
    input.setText("");
  }


  /**
   * Gets an empty column name, to be used as teh top left cell of the spreadsheet.
   *
   * @return an array of size one, with an empty string*/

  private String[] getEmptyColumnName() {
    String[] ans = new String[1];
    ans[0] = "";
    return ans;
  }

  /**
   * Creates the row heading array.
   * @return a 2D array of numRows arrays filled with one number each, representing which row.
   */

  private String[][] getRowHeading() {
    String[][] ans = new String[numRows][1];
    for (int i = 0; i < numRows; i++) {
      ans[i][0] = Integer.toString(i + 1);
    }
    return ans;
  }

  @Override
  public Coord getSelectedCell() {
    return new Coord(this.selectedCell.col, this.selectedCell.row);
  }

  @Override
  public void changeCell() {
    int numCols = 26;
    for (int col = 0; col < numCols; col++) {
      for (int row = 0; row < numRows; row++) {
        try {
          if (!model.getCellAtRaw(col + 1, row + 1).equals(table.getValueAt(row, col))) {
            table.setValueAt(model.getCellAtEvaluated(col + 1, row + 1), row, col);
          }
        }
        catch (Exception e) {
          table.setValueAt("", row, col);
        }
      }
    }
  }

  /**
   * Creates the direction label to be put on the view frame to give users some tips on how to use
   * an editable worksheet.
   * @return a JTextArea with directions.
   */
  private JTextArea makeDirectionLabel() {
    String text = "Click on a cell to edit." + "\n" + "Possible functions: \n" +
        "\"=(SUM double double...)\", " +
        "\"=(PRODUCT double double...)\", \"=(< double double)\", \n" +
        "\"=(SUBSTRING string int int)\".  \n Reference another cell by inputting " +
        "\"=coordinate\", or a range of cells, \n \"STARTRANGE:ENDRANGE\"";
    return new JTextArea(text);
  }

  @Override
  public void resetFocus() {
    this.setFocusable(true);
    this.requestFocus();
  }

  @Override
  public void addActionListener(ActionListener actionListener) {
    okayButton.addActionListener(actionListener);
    cancelButton.addActionListener(actionListener);
  }

  @Override
  public void setSelectedCell(Coord c) {
    this.selectedCell = c;
    this.origText = model.getCellAtRaw(c.col, c.row);
  }

  @Override
  public void makeView() {
    JFrame frame = new JFrame("Worksheet");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    JPanel contentPane = new JPanel(new BorderLayout());
    contentPane.add(pane1, BorderLayout.CENTER);
    contentPane.add(jtb, BorderLayout.PAGE_START);
    frame.setContentPane(contentPane);
    frame.add(makeDirectionLabel(), BorderLayout.SOUTH);
    frame.pack();
    frame.setVisible(true);
  }
}
