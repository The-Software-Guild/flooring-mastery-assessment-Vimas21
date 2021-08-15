package dao;

import dto.Order;
import dto.Product;
import dto.Tax;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;


/**
 * Our DAO. Low level stuff, fetching items and giving them back.
 */

public interface FloorDao {
    
    // Create an empty hashM for orders, taxes, and products.
    HashMap<Integer, Order> orders = new HashMap<Integer, Order>();
    HashMap<String, Tax> taxes = new HashMap<String, Tax>();
    HashMap<String, Product> products = new HashMap<String, Product>();
    
    // Load the input into the empty hashMs.
    public void load() throws FileNotFoundException;
    
    // Break Down Order String
    public void orderBreakdown(String line);
    
    // Break Down Tax String
    public void taxBreakdown(String line);
    
    public void productBreakdown(String line);
    
    // Fetch a specific order, tax, and product, via key.
    public Order fetchOrd(String key);
    
    public Tax fetchTax(String key);
    
    public Product fetchProduct(String key);
    
    // Assign new value in order.
    
    public void newOrder(Order newOrder);
    
    // Return a contains for tax and product as a bool.
    public boolean isValid(String state, String product);
    
    public List<Order> allOrders();
    
    // Save the info to files.
    
}
