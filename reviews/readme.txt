README


Successfully Working Features:
	-Confirm Button to Update evaluated cells in view: 
		When pressed it will take in the textfield input and update the selected cell to the new input.

	-Textfield as an input: 
		When a text is typed in the textfield it becomes the possible input string and can be used when confirm button is pressed.

	-Display of their spreadsheet: 
		All the components of their GUI are present including scrollbar, buttons, textfields, headers, grid of cells, and a instructions textfield on the bottom. 

	-Highlighting and Selecting Cells: 
		Built into JTable, cell is highlighted when clicked, we are able to store the Cell for editing purposes on mouseclick.


Unsuccessful Features:
	- Cancel button to revert to original text:
		We could not implement this because we could not figure out how to have one actionlistener from the view for two buttons with different purposes. They seem to use runnables in their model/controller but they did not have a controller interface to work with and we did not want to ask for their implemented controller class.

	- infinite scrolling (was never implemented by them as part of their view code):
		This was not a feature that was part of their view nor ours so we could not decorate their view with our implementation.

