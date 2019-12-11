package edu.cs3500.spreadsheets;

import edu.cs3500.spreadsheets.model.BasicWorksheet;
import edu.cs3500.spreadsheets.model.BasicWorksheet.Builder;
import edu.cs3500.spreadsheets.model.Coord;
import edu.cs3500.spreadsheets.model.Spreadsheet;
import edu.cs3500.spreadsheets.model.WorksheetReader;
import edu.cs3500.spreadsheets.provider.controller.ControllerAdapter;
import edu.cs3500.spreadsheets.provider.model.ViewModelImpl;
import edu.cs3500.spreadsheets.provider.model.Worksheet;
import edu.cs3500.spreadsheets.provider.model.WorksheetImpl;
import edu.cs3500.spreadsheets.provider.view.EditableView;
import edu.cs3500.spreadsheets.provider.view.ViewAdapter;
import edu.cs3500.spreadsheets.view.CompositeView;
import edu.cs3500.spreadsheets.view.GraphicsView;
import edu.cs3500.spreadsheets.view.IView;
import edu.cs3500.spreadsheets.view.TextView;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * The main class for our program.
 */
public class BeyondGood {

  /**
   * Static void main.
   * @param args any command-line arguments.
   */
  public static void main(String[] args) throws FileNotFoundException {
    File infile = new File("/Users/darinhunt/Desktop/OOD/HW7/test/testTRI.txt");
    //File infile = null;
    //File outfile = new File("/Users/darinhunt/Desktop/OOD/HW7/test/testTRI_results.txt");
    //File infile = null;
    File outfile = null;
    String incell = null;
    int size = 51;
    String view = null;
    for (int i = 0; i < args.length; i++) {
      switch (args[i]) {
        case ("-size"):
          if (i == args.length - 1) {
            throw new IllegalArgumentException("You need to give a size");
          }
          try {
            size = Integer.parseInt(args[i + 1]);
          }
          catch (ArrayIndexOutOfBoundsException e) {
            throw new IllegalArgumentException("That is not a valid integer");
          }
          break;
        case("-in"):
          if (i == args.length - 1) {
            throw new IllegalArgumentException("You need to give me an input file you dumb-dumb");
          }
          infile = new File(args[i + 1]);
          i++;
          break;
        case("-eval"):
          if (i == args.length - 1) {
            throw new IllegalArgumentException("You need to give me a cell you silly nugget");
          }
          incell = args[i + 1];
          i++;
          break;
        case("-save"):
          if (i == args.length - 1) {
            throw new IllegalArgumentException("Need a file name to save to");
          }
          outfile = new File(args[i + 1]);
          view = "text";
          i++;
          break;
        case("-gui"):
          view = "graphic";
          break;
        case("-edit"):
          view = "composite";
          break;
        case("-provider"):
          view = "provider";
          break;
        default:
          throw new IllegalArgumentException("This is not how you use our application tough guy");
      }
    }

    createSpreadSheet(infile, incell, view, outfile, size);
  }

  /**
   * Function that creates a spreadsheet by taking in a file.
   * @param file the name of the file.
   * @param cell cell.
   * @param type the type of view to be rendered.
   * @param saveTo the path of where to save the outfile to.
   * @param size the size of the spreadsheet to be made.
   * @throws FileNotFoundException if file not found.
   */
  private static void createSpreadSheet(
      File file, String cell, String type, File saveTo, int size) throws FileNotFoundException {
    Builder b = new Builder();
    BufferedReader reader;
    Spreadsheet spreadsheet;

    if (file == null && type.equals("text")) {
      throw new IllegalArgumentException("text needs a file");
    }

    if (file == null && type.equals("graphic") || file == null && type.equals("composite")
        || file == null && type.equals("provider")) {
      IView v = createView(type, saveTo, b.createWorksheet(false), size);
      v.display();
    }

    else {
      try {
        reader = new BufferedReader(new FileReader(file));
        String line = reader.readLine();
        while (line != null) {
          String[] phrase = line.split(" ", 2);
          String coordinate = phrase[0];
          String formula = phrase[1];
          String[] coord1 = coordinate.split("(?<=\\D)(?=\\d)", 2);

          int col = Coord.colNameToIndex(coord1[0]);
          int row = Integer.parseInt(coord1[1]);
          b.createCell(col, row, formula);
          line = reader.readLine();
        }
        reader.close();
      } catch (IOException e) {
        throw new IllegalArgumentException(e);
      }
      FileReader fileReader = new FileReader(file);
      Spreadsheet s = WorksheetReader.read(BasicWorksheet.defaultBuilder(), fileReader);

      b.createWorksheet(false);

      IView v = createView(type, saveTo, s, size);
      v.getFrame().setTitle(1 + " of " + 1);
      v.display();
    }

  }

  /**
   * Updates the view when new cells are added.
   * @param coord the coordinate of the newly updated cell. In form : A1.
   * @param value the raw string value being placed in the new cell.
   * @param s the spreadsheet.
   */
  public static void updateCurrentView(String coord, String value, Spreadsheet s) {
    String[] coord1 = coord.split("(?<=\\D)(?=\\d)", 2);
    int col;
    int row;
    try {
      col = Coord.colNameToIndex(String.valueOf(coord1[0]));
      row = Integer.parseInt(coord1[1]);
      try {
        s.getCurrSpreadSheet().get(new Coord(col, row))
            .setEvaluatedData(BasicWorksheet.getEvaluatedSingleCell(s, value));
        s.getCurrSpreadSheet().get(new Coord(col, row)).setContents(value);
        s.getCurrSpreadSheet().get(new Coord(col, row)).setRawString(value);
      }
      catch (IllegalArgumentException e) {
        s.getCurrSpreadSheet().get(new Coord(col, row)).setRawString("\"" + value + "\"");
        s.getCurrSpreadSheet().get(new Coord(col, row))
            .setEvaluatedData(BasicWorksheet.getEvaluatedSingleCell(s, "NaN"));
        s.getCurrSpreadSheet().get(new Coord(col, row)).setContents("NaN");
      }

      s.getEvaluatedCells();
    }
    catch (NumberFormatException e) {
      System.out.println("Invalid value");
    }
  }

  /**
   * Responsible for rendering the view.
   * @param type the type of view.
   * @param saveTo where the spreadsheet is being saved to.
   * @param s the spreadsheet.
   * @param size the size of the spreadsheet.
   * @return the view.
   */
  public static IView createView(String type, File saveTo, Spreadsheet s, int size) {
    switch (type) {
      case("text"):
        TextView createView = new TextView(s.getCurrSpreadSheet(), 5, 5);
        createView.saveTo(saveTo.getPath());
        return createView;
      case("graphic"): return new GraphicsView(s,  size, size);
      case("composite"):
        return new CompositeView(s.getCurrSpreadSheet(), size, size, s);
      case("provider"):
        Worksheet w = new WorksheetImpl(s);
        ViewModelImpl vmi = new ViewModelImpl(w);
        EditableView editableView = new EditableView(vmi);
        ViewAdapter viewAdapter = new ViewAdapter(
            editableView, new ControllerAdapter(w, editableView));
        return viewAdapter;
      default: throw new IllegalArgumentException("This type of view is not supported");
    }
  }
}