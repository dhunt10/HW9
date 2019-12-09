package edu.cs3500.spreadsheets.view;

import edu.cs3500.spreadsheets.controller.CompositeSpreadsheetController;
import edu.cs3500.spreadsheets.model.Cell;
import edu.cs3500.spreadsheets.model.Coord;
import edu.cs3500.spreadsheets.model.Spreadsheet;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridLayout;
import java.util.Map;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

/**
 * Defines a frame in a window.
 */
public class CompositeFrame extends JFrame {
  private Map<Coord, Cell> curr;
  private GridPanel gridPanel;
  private JPanel editOptions;
  private JFrame frame = new JFrame(); //creates frame
  private JLabel[][] grid; //names the grid of buttons
  private JTextField rawContents;
  private JButton confirm;
  private JButton cancel;
  private Spreadsheet model;
  private IView view;

  /**
   * Constructor to have a composite, dynamic frame.
   * @param curr current spreadsheet in map form.
   * @param width width of the spreadsheet.
   * @param height height of the spreadhseet.
   * @param model spreadsheet.
   * @param view the current view.
   */
  public CompositeFrame(Map<Coord, Cell> curr,
      int width, int height, Spreadsheet model, IView view) {

    super();
    this.model = model;
    this.setPreferredSize(new Dimension(width,  height));
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.view = view;

    this.setLayout(new BorderLayout());

    //add options panel
    editOptions = new JPanel(new GridLayout());
    GridBagConstraints c = new GridBagConstraints();
    c.fill = GridBagConstraints.HORIZONTAL;


    //add buttons to the frame
    cancel = new JButton("Cancel");
    cancel.setSize(50,50);
    c.gridx = 0;
    c.gridy = 0;
    c.ipadx = 10;
    c.ipady = 30;

    editOptions.add(cancel, c);

    confirm = new JButton("Confirm");
    confirm.setPreferredSize(new Dimension(10, 30));
    c.gridx = 0;
    c.gridy = 0;
    c.ipadx = 10;
    c.ipady = 30;
    editOptions.add(confirm,c);


    //textfield
    rawContents = new JTextField();
    rawContents.setPreferredSize(new Dimension(10, 30));
    c.gridx = 0;
    c.gridy = 0;
    c.ipadx = 10;
    c.ipady = 30;
    editOptions.add(rawContents, c);

    this.add(editOptions, BorderLayout.NORTH);

    //add the grid of cells
    gridPanel = new GridPanel(width, height, curr);
    JScrollPane scrollBar = new JScrollPane(
        gridPanel,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
    this.add(scrollBar, BorderLayout.CENTER);

    this.gridPanel.addMouseListener(new CompositeSpreadsheetController(
        model, width, height, rawContents, confirm, this.view, cancel));

    this.pack();
    this.setSize(800, 500);
  }

  /**
   * Updates the state of the view.
   * @param curr spreadsheet in map form.
   */
  public void newState(Map<Coord, Cell> curr) {
    gridPanel.setState(curr);
  }


  /**
   * Sets the view to be visible.
   */
  public void display() {
    this.setVisible(true);
  }

}
