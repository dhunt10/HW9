package edu.cs3500.spreadsheets.provider.controller;

import edu.cs3500.spreadsheets.model.Coord;
import edu.cs3500.spreadsheets.provider.model.Worksheet;
import edu.cs3500.spreadsheets.provider.view.EditableViewInterface;
import edu.cs3500.spreadsheets.view.CompositeViewButtonActions;
import edu.cs3500.spreadsheets.view.CompositeViewMouseActions;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;

/**
 * Adapter of controller to communicate between provider view and our model.
 */
public class ControllerAdapter implements
    CompositeViewMouseActions, CompositeViewButtonActions {

  private Worksheet model;
  private EditableViewInterface evi;
  //private IView adapter;

  /**
   * Constructor which sets controls for provider view.
   * @param model model to create spreadsheet.
   * @param evi an instance of the editable view implementation.
   */
  public ControllerAdapter(Worksheet model, EditableViewInterface evi) {
    this.evi = evi;
    this.model = model;
    evi.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        confirmActionPerformed(e);
      }
    });

    /*evi.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        cancelActionPerformed(e);
      }
    });*/
    evi.setMouseListener(this);
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    // not needed
  }

  @Override
  public void mouseClicked(MouseEvent e) {
    evi.setSelectedCell(new Coord(e.getX() / 75 + 1, e.getY() / 20 + 1));
    evi.setInput(model.getCellAtRaw(evi.getSelectedCell().col, evi.getSelectedCell().row));
  }

  @Override
  public void mousePressed(MouseEvent e) {
    // not needed
  }

  @Override
  public void mouseReleased(MouseEvent e) {
    // not needed
  }

  @Override
  public void mouseEntered(MouseEvent e) {
    // not needed
  }

  @Override
  public void mouseExited(MouseEvent e) {
    // not needed
  }

  /**
   * Perform action when cancel button is clicked.
   * @param e button click for cancel button.
   */
  public void cancelActionPerformed(ActionEvent e) {
    evi.setInput(model.getCellAtRaw(evi.getSelectedCell().col, evi.getSelectedCell().row));
  }

  /**
   * Commands confirm button.
   * @param e action of clicking button.
   */
  public void confirmActionPerformed(ActionEvent e) {
    model.changeCellContentsOrReplaceCell(evi.getSelectedCell(), evi.getInputString());
    evi.changeCell();
  }


}
