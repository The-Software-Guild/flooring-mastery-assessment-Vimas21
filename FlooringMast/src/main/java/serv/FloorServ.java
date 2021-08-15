package serv;

import dto.Order;
import java.math.BigDecimal;
import java.util.*;

/**
 * The bridge between dao and core.
 */
public interface FloorServ {
    
    // Gotta bridge information!
    
    // Fetch and return an order
    
    // Fetch and return ALL orders.
    public List<String> fetchAllOrders();
    
    public String fetchOneOrder(int orderNum);
    
    public String fetchOneOrder(Order order);
    
    public boolean hasOrderNum(int orderNum);
    
    public boolean deleteOrder(int orderNum);
    
    // Add an order.
    
    public void addOrder(Order order);
    
    public Order processOrder(String custName, String state, String productType, String area);
    
    public boolean editName(int orderNum, String newVal); 
    
    public boolean editState(int orderNum, String newVal);
    
    public boolean editProduct(int orderNum, String newVal);
    
    public boolean editArea(int orderNum, String newVal);
    // Edit an order.
    
    
    
}
