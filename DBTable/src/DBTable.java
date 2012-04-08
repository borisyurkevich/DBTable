/**
 * TRY IT from LESSON 23
 * 
 * Special thanks to Yakov Fain and 
 * his wonderful book Java Programming 24-Hour Trainer
 *
 */


import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;

import code.Order;
import code.OrdersRequest;

public class DBTable  extends JFrame implements TableModelListener{

	MyTableModel myTableModel; 
	JTable myTable;

 DBTable (String winTitle){
  super(winTitle);
  
  myTableModel = new MyTableModel();
  myTable = new JTable(myTableModel );

  //Add the JTable to frame and enable scrolling 
  add(new JScrollPane( myTable));

  // Register an event listener
   myTableModel.addTableModelListener(this);   
   
 }

 public void tableChanged(TableModelEvent e) {
  // Code to process data changes goes here
	 System.out.println("Someone changed the data in JTable!");
 }

 /**
  * Main
  * @param args
  */
 public static void main(String args[]){
  
	 DBTable dBTable = new DBTable( "DBTable" );
	 dBTable.pack();   
	 dBTable.setVisible( true );

}  

 class MyTableModel extends AbstractTableModel {
	
		ArrayList<Order> myData = new ArrayList<Order>();
		String[] orderColNames = { "Order ID",   "Symbol", 
                "Quantity", "Price", "Total"};

	   MyTableModel(){
		   
		   ExecutorService pool = Executors.newFixedThreadPool(1);
		   Future<ArrayList<Order>> futureOrders = pool.submit(new OrdersRequest());
			
		   try {
			   myData = futureOrders.get(); 
		   } catch (InterruptedException ie) {
			   System.out.println(ie.getMessage());
			   ie.printStackTrace();
			   
		   } catch (ExecutionException ee) {
			   System.out.println(ee.getMessage());
			   ee.printStackTrace();
		   } finally {
			   pool.shutdown();
		   }
		}
		
	    public int getColumnCount() {
	      return 5;
	    }

	    public int getRowCount() {
	      return myData.size();
	    	
	    }
	    
	    /**
	     * Will Update data 
	     */

	    public Object getValueAt(int row, int col) {
	       
	    	switch (col) {
	    	  case 0:   // col 1
	    	    return myData.get(row).orderID;
	    	  case 1:   // col 2
	    		  return myData.get(row).stockSymbol;
	    	  case 2:   // col 3
	    		  return myData.get(row).quantity;
	    	  case 3:   // col 4 
	    		  return myData.get(row).price;
	    	  case 4:  // col 5 
	    		  return myData.get(row).totalValue;
	    	  default:
	    	    return "";
	    	}
	    	
	    }
	    
	    public String getColumnName(int col){
	    	return orderColNames[col];
	    }

	    /**
	     * This table is not editable.
	     */
	    public boolean isCellEditable(int row, int col) {
	    	  
	    	  if (col ==2){ 
	    	      return false;
	    	  } else {
	    	    return false;
	    	  }
	    	}
    
	    // Update the model when the use changes the quantity
	    public void setValueAt(Object value, int row, int col){
	    
	     if (col== 2){
	       myData.get(row).quantity=(Integer.valueOf(value.toString()));
         }
	    
	     //Notify the world about the change
	     //fireTableDataChanged();
	      
	     TableModelEvent event = new TableModelEvent(this, row, row, col);
	     fireTableChanged(event);
	  }
 }
}