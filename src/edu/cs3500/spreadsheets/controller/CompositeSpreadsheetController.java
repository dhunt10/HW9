package edu.cs3500.spreadsheets.controller;

import edu.cs3500.spreadsheets.BeyondGood;
import edu.cs3500.spreadsheets.model.BasicWorksheet.Builder;
import edu.cs3500.spreadsheets.model.Cell;
import edu.cs3500.spreadsheets.model.Coord;
import edu.cs3500.spreadsheets.model.Spreadsheet;
import edu.cs3500.spreadsheets.view.CompositeViewButtonActions;
import edu.cs3500.spreadsheets.view.CompositeViewMouseActions;
import edu.cs3500.spreadsheets.view.GridPanel;
import edu.cs3500.spreadsheets.view.IView;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.plaf.synth.SynthUI;

/**
 * Controller for communicating between view and model.
 */
public class CompositeSpreadsheetController implements SpreadsheetController,
    CompositeViewMouseActions, CompositeViewButtonActions {

  private Spreadsheet model;
  private IView view;
  private int maxCols;
  private int maxRows;
  private JTextField textField;
  private int x;
  private int y;
  private JButton accept;
  private JButton cancel;
  private boolean high = false;
  private Coord highLight = null;
  private JButton newSpreadsheet;
  private int numberSpreadsheet = 1;
  private final Map<Integer, Spreadsheet> currSpreadSheetMap = new HashMap<>();
  private int viewShowSpreadSheet = 1;

  /**
   * Constructor.
   * @param model data to retrieve.
   * @param maxCols the number of columns to make.
   * @param maxRows the number of rows to make.
   * @param textfield the text box in the view so that data can be taken from it and to it.
   * @param accept the accept button so we can have action listeners for it.
   * @param view the view.
   * @param cancel the cancel button so we can have action listeners for it.
   */
  public CompositeSpreadsheetController(Spreadsheet model, int maxCols, int maxRows,
      JTextField textfield, JButton accept, IView view, JButton cancel, JButton newSpreadsheet,
      JButton left, JButton right) {
    this.model = model;
    this.view = view;
    this.maxCols = maxCols;
    this.maxRows = maxRows;
    this.textField = textfield;
    this.accept = accept;
    this.newSpreadsheet = newSpreadsheet;
    this.currSpreadSheetMap.put(this.numberSpreadsheet, model);
    accept.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        actionDone(e);
      }
    });

    this.cancel = cancel;
    cancel.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        cancelActionPerformed(e);
      }
    });

    newSpreadsheet.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        newSpreadsheetPerformed(e);
      }
    });

    left.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        moveLeft(e);
      }
    });

    right.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        moveRight(e);
      }
    });
  }
  
  @Override
  public void mouseClicked(MouseEvent e) {
    //not necessary
  }


  @Override
  public void mousePressed(MouseEvent e) {
    Component c = e.getComponent();

    ArrayList<Component> headerCells = new ArrayList<>();
    if (c instanceof GridPanel) {


      Cell highlightCell = (Cell) ((GridPanel) c).getcellScreenLocation()
          .get(e.getPoint());
      //highlightCell.highlightSelf();
      JPanel test = (JPanel) c.getComponentAt(e.getPoint());


      for (Component cell : ((GridPanel) c).getComponents()) {
        cell.setBackground(new Color(196, 198, 255));
      }

      for (int width = 1; width <= 1 + maxCols * 80; width += 80) {
        c.getComponentAt(width, 1).setBackground(new Color(74, 77, 145));
        headerCells.add(c.getComponentAt(width, 1));

      }

      for (int height = 1; height <= 1 + maxRows * 30; height += 30) {
        c.getComponentAt(1, height).setBackground(new Color(74, 77, 145));
        headerCells.add(c.getComponentAt(1, height));
      }

      if (!headerCells.contains(test)) {
        if (highLight == null || highLight.row == -1
            || !highLight.equals(new Coord(e.getX() / 80, e.getY() / 30))) {
          this.x = e.getX() / 80;
          this.y = e.getY() / 30;
          highLight = new Coord(this.x, this.y);
          test.setBackground(Color.PINK);
          StringBuilder sb = new StringBuilder();
          sb.append(Coord.colIndexToName(x)).append(y);
          this.textField.setText(currSpreadSheetMap.get(this.viewShowSpreadSheet).getCellAt(new Coord(x, y)).getRawString());
        }
        else {
          this.x = -1;
          this.y = -1;
          test.setBackground(new Color(196,198,255));
          this.high = false;
          highLight = null;
          this.textField.setText("");
        }
      }
    }
  }

  @Override
  public void mouseReleased(MouseEvent e) {
    Component c = e.getComponent();
    Border whiteBorder = BorderFactory.createLineBorder(Color.WHITE);
    Border redBorder = BorderFactory.createLineBorder(Color.magenta,5);
    JPanel test = (JPanel) c.getComponentAt(e.getPoint());

    test.setBorder(whiteBorder);
  }

  @Override
  public void mouseEntered(MouseEvent e) {
    //not necessary
  }

  @Override
  public void mouseExited(MouseEvent e) {
    //not necessary

  }

  /**
   * Perform action related to accept button.
   * @param e button click for accept button.
   */
  public void actionDone(ActionEvent e) {
    StringBuilder sb = new StringBuilder();
    sb.append(Coord.colIndexToName(x)).append(y);
    try {
      this.updateProgram(sb.toString(), textField.getText(), currSpreadSheetMap.get(this.viewShowSpreadSheet));
      view.newState(this.currSpreadSheetMap.get(this.viewShowSpreadSheet).getCurrSpreadSheet());
      new Coord(this.x, this.y);
    }
    catch (ArrayIndexOutOfBoundsException r) {
      System.out.println("No Cell Selected COMB");
    }
    catch (IllegalArgumentException f) {
      System.out.println("No Cell Selected");
    }

    this.x = -1;
    this.y = -1;
    textField.setText("");

  }

  /**
   * Perform action when cancel button is clicked.
   * @param e button click for cancel button.
   */
  public void cancelActionPerformed(ActionEvent e) {
    try {
      String contents = currSpreadSheetMap.get(this.viewShowSpreadSheet).getCellAt(new Coord(x,y)).getRawString();
      textField.setText(contents);
    }
    catch (IllegalArgumentException e1) {
      System.out.println("Cancel can only be used when a cell is selected");
    }

  }

  /**
   * Responsible for updating the cells.
   * @param coordinate the coordinate in form : A1.
   * @param inString the raw string value of a new cell.
   * @param s the spreadsheet.
   */
  public void updateProgram(String coordinate, String inString, Spreadsheet s) {
    BeyondGood.updateCurrentView(coordinate, inString, s);
  }


  public void newSpreadsheetPerformed(ActionEvent e) {
    this.numberSpreadsheet = this.numberSpreadsheet + 1;
    Builder b = new Builder();
    Spreadsheet s = b.createWorksheet(true);
    this.currSpreadSheetMap.put(this.numberSpreadsheet, s);
    this.view.getFrame().setTitle(this.viewShowSpreadSheet + " of " + this.numberSpreadsheet);
    //IView v = BeyondGood.createView("composite", null, b.createWorksheet(true), 50);
    //v.display();
  }

  @Override
  public final void setX(int x) {
    this.x = x;
  }

  @Override
  public final void setY(int y) {
    this.y = y;
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    //not necessary
  }

  public void moveLeft(ActionEvent e) {
    try {

      view.newState(this.currSpreadSheetMap.get(this.viewShowSpreadSheet - 1).getCurrSpreadSheet());
      this.viewShowSpreadSheet = this.viewShowSpreadSheet - 1;
      this.view.getFrame().setTitle(this.viewShowSpreadSheet + " of " + this.numberSpreadsheet);
      textField.setText("");
      this.x = -1;
      this.y = -1;

    }
    catch (NullPointerException e1) {

    }
  }

  public void moveRight(ActionEvent e) {
    try {
      view.newState(this.currSpreadSheetMap.get(this.viewShowSpreadSheet + 1).getCurrSpreadSheet());
      this.viewShowSpreadSheet = this.viewShowSpreadSheet + 1;
      this.view.getFrame().setTitle(this.viewShowSpreadSheet + " of " + this.numberSpreadsheet);
      textField.setText("");
      this.x = -1;
      this.y = -1;
    }
    catch (NullPointerException e1) {

    }

  }
}
