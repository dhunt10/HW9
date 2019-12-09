# HW7
### By Darin and Satwik.

### Spreadsheets
#####
Data can be placed into the spreadsheets by loading in a static text file as well as dynamically
when the composite graphical view is running. If the graphical view is running data can only be
inputted via a static file before running the program. Finally, a simpler text view is also available
in which you can view the data but cannot edit it as the data has been read in via a static text file
before compile time.

### ***Changes Made***
#####
We have added in a single change function called updateCurrentView
we have added in a evaluate single cell function, which in turn evaluates one single cell and
checks to see if any other cells rely on it. The way our design currently works is we
have a builder which makes a spread sheet with or without an infile. Then once the file is
built we can then add to it with the newly made updateCurrentView function. We have obviously
added in a new dynamic view which we can manipulate after the creation of the spreadsheet.
Every time we make changes to the spreadsheet we then update the dynamic view.
Instead of throwing errors when illicit data is placed in one of the cells we have now
implemented certain handlers to warn or tell the user they have done so. These specific behaviors
are explained below.

We have also added listeners. The listeners we have added in the controller consist of two
designated to call a function when one of the two buttons is clicked. When the confirm
button is clicked the data placed into the text box is evaluated and placed into the currently
selected cell. When the cancel button is pressed the current raw data in the cell is placed into
the text box.

We have an additional listener which waits for button clicks anywhere on the grid. The click's
coordinates are then translated to a cell number and a cell is then chosen and depending on
if the cell is currently selected it will either highlight or un-highlight. This is how you must
a cell.

## To edit a cell:
> - First: click a cell and wait for it to be highlighter (it wont be long).
> - Second: enter the data in the text box in the proper format (see below for details).
> - Third: Press the confirm button.
> - Watch your data update the chart.

## To clear a cell:
> - Select the cell
> - Clear the data from the text box
> - Press the confirm button

### Behavior
#####
If something is put into a cell that is believed to be a typo or an incomplete expression such
as
> SUM(1:)
#####
Then the string-ified version of that expression will be placed into the cell, while the text box
will be filled with the string-ified version.

If something throws a bad entry such as
> 10 + 3
#####
Then the cell will be filled with
````$xslt
NaN
````

while the text box will then be filled with what was originally filled in the cell,
in this example:
> 10 + 3

### Clicking and Choosing Textbox
#####
You just have to click a cell once to select it and a second time to deselect it.
You can also choose a different cell to deselect the first one and select a second
cell all in the same move. You can then enter information into the text box and press
the confirm button to add the data into the selected cell.

### Cancel Button
#####
The cancel button will delete what you had in the text box. If you wish to delete data inside
of a cell you must press the confirm button to then clear stuff in the cell itself.

### Confirm Button
#####
Any information you wish to place in a cell must be written in the text box and then
confirmed with the confirm button, this includes deleting information.

##### Status

- [x] Model
- [x] Model Test
- [x] Text View
- [x] Text View Test
- [x] Graphical View
- [x] Graphical View Test
- [ ] Controller
- [ ] Controller Test
- [ ] Additional View
- [ ] Additional View Test
------------------------------------------------------------------

##### 3 inputs are possible for cells
**Values**
 - Values can be:
  ````
  numbers
  booleans
  strings
  ````
**Formulas**
 - Formulas can include:
  ````
  SUM
  PROD
  COMB
  <
  ````
**Reference**
 - A reference to an additional cell or block of cells
 > - A1:B7 (Block Reference)
 > - C2 (Single Reference)

------------------------------------------------------------------
##### The following is an example input file
````
A1 3
A2 7
A3 =(SUM 10 2)
A4 =(SUM A1 A2)
A5 =(PROD (SUM 10 20) (PROD 10 A4))
A6 =(SUM A2:B2)
A7 =(< A7 3)
````
