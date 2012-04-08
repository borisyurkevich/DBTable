/**
 * This is a blank table with seven columns and twelve rows
 * Will store data in local user memory
 * Another Class can calculate data and update using data with API
 */

package code;

public class Table {
	
	//API's
	public float readElement(int row, int col) {
		return table[row][col];
	}
	
	public void writeElement(float newNumber, int row, int col) {
		table[row][col] = newNumber;
	}
	
	Float [][] table = new Float [7][12];
}