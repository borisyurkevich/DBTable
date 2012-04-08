package code;

/**
 * Order class is holding all values
 *
 */
public class Order {
    public int orderID;
    public String stockSymbol;
    public int quantity;
    public float price;
    public float totalValue;
    
    public Order(int id, String stockSymbol, int quantity, float price, float totalValue){
    	orderID=id;
    	this.stockSymbol=stockSymbol;
    	this.quantity=quantity;
    	this.price=price;
    	this.totalValue = totalValue;
    }
}
