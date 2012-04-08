/**
 * Will read data from Derby DB
 * Will store Generic Data in 
 * the Order Array List
 */

package code;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.concurrent.Callable;

public class OrdersRequest implements Callable<ArrayList<Order>> {
    
		@Override
		public ArrayList<Order> call() throws Exception {
			
			/**
			 * Three DB Objectes
			 */
			Connection conn = null;
			Statement stmt = null;
			ResultSet rs = null;
			ArrayList<Order> orders = new ArrayList<Order>();
			
			try {
				
				/**
				 * 2 Obtain connection
				 */
				conn = DriverManager.getConnection( "jdbc:derby://localhost:1527/MyDB");
			    
				/**
				 * 3.Create an instance of the Java class Statement
				 */
				stmt = conn.createStatement();
				
				 // Build an SQL String 
			    String sqlQuery = "SELECT * FROM MyPortfolio";
				
				/**
				 * 4. For SQL Select execute the following command
				 */
				rs = stmt.executeQuery(sqlQuery);
				
				// fill orders result with data from the database
				while (rs.next()) {
			    	Integer id = rs.getInt("ID");
			       	String symbol = rs.getString("Symbol");
			       	Integer quantity = rs.getInt("Quantity");
			       	Float price = rs.getFloat("Price");
			       	Float totalValue = quantity * price;
				 	
			       	orders.add((new Order (id, symbol, quantity, price, totalValue )));
			    }
				
			} catch (SQLException se) {
				System.out.println("SQLError: " + se.getMessage() + " code: " + se.getErrorCode()); 
			    
			} catch (Exception e) {
				e.printStackTrace();
				
			} finally{
			       // clean up the system resources
			       try{
				   rs.close();     
				   stmt.close(); 
				   conn.close();  
			       } catch(Exception e){
			           e.printStackTrace();
			       } 
			}
			
			// TODO Auto-generated method stub
			
			/**
			 * The point to write Orders
			 * to Order Class so
			 * Table GUI can read it
			 */
			return orders;
		}
    	
}