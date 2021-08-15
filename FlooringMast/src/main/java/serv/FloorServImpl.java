/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serv;

import dao.FloorDao;
import dto.Order;
import dto.Product;
import dto.Tax;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.*;

/**
 *
 * @author Samma
 */
public class FloorServImpl implements FloorServ {

    FloorDao dao;
    
    public FloorServImpl(FloorDao dao) throws FileNotFoundException{
        this.dao = dao;
        this.dao.load();
    }
    
    @Override
    public List<String> fetchAllOrders(LocalDate date) {
        List<String> allOrders = new ArrayList<String>();
        List<Order> orderOrders = dao.allOrders();
        for(Order order : orderOrders){
            if(order.date.isEqual(date)){
                allOrders.add("Order " + order.order + ". " + order.product + "s to "
                        + order.state +" for " + order.customer +". It will cost " + order.total);
            }
        }
        return allOrders;
    }

    @Override
    public Order processOrder(String custName, String state, String productType, String sArea, String date){
        // Calculate a LOT
        
        BigDecimal area = new BigDecimal(sArea).setScale(2, RoundingMode.HALF_UP);
        Tax tax = dao.fetchTax(state);
        BigDecimal taxRate = tax.tax;
        
        Product product = dao.fetchProduct(productType);
        BigDecimal costPer = product.costPer;
        BigDecimal laborPer = product.laborPer;
        // Fetch product type, and through that, cost per square foot and labor cost per square foot
        
        // Fetch state, and through that, get tax rate.
        
        // Material Cost here: Area * Cost Per Square
        BigDecimal matCost = area.multiply(costPer);
        // Labor cost here: Area * Labor Cost Per Square
        BigDecimal laborCost = area.multiply(laborPer);
        // Tax here: (Mat + lab cost) * tax/100
        BigDecimal fullTax = matCost.add(laborCost);
        fullTax.multiply(taxRate.divide(new BigDecimal("100").setScale(2, RoundingMode.HALF_UP)));
        BigDecimal fullCost = matCost.add(laborCost).add(fullTax);
        fullCost=fullCost.setScale(2, RoundingMode.HALF_UP);
        // Total here Mat Cost + Lab Cost + Tax
        System.out.println(date);
        Order newOrder = new Order(date, 0, custName, state, taxRate, productType, area, costPer, laborPer, matCost, laborCost, fullTax, fullCost);
        return newOrder;
    }

    @Override
    public String fetchOneOrder(int orderNum) {
        if(dao.orders.containsKey(orderNum)){
        Order order = dao.orders.get(orderNum);
        return "Order " + order.order + ". " + order.product + "s to "
                    + order.state +" for " + order.customer +".";
        }
        return "ORDER NOT FOUND ERROR.";
    }
    
    @Override
    public String fetchOneOrder(Order order){
        return "Order " + order.order + ". " + order.product + "s to "
                    + order.state +" for " + order.customer +".";
    }
    
    @Override
    public boolean hasOrderNum(int orderNum){
        if(dao.orders.containsKey(orderNum)){
            return true;
        }
        return false;
    }
    
    @Override
    public void addOrder(Order order) {
        dao.newOrder(order);
    }

    @Override
    public boolean editName(int orderNum, String newVal) {
        if(dao.orders.containsKey(orderNum)){
            dao.orders.get(orderNum).customer=newVal;
            return true;
        }
        return false;
    }

    @Override
    public boolean editState(int orderNum, String newVal) {
        // Change the value, then recalculate all.
        // Gotta check to make sure both the order and new State exists.
        if(dao.orders.containsKey(orderNum) && dao.taxes.containsKey(newVal)){
            Order order = dao.orders.get(orderNum);
            order.state=newVal;
            return this.recalc(orderNum);
        }
        return false;
    }

    @Override
    public boolean editProduct(int orderNum, String newVal) {
        if(dao.orders.containsKey(orderNum) && dao.products.containsKey(newVal)){
            Order order = dao.orders.get(orderNum);
            order.product = newVal;
            return this.recalc(orderNum);
        }
        return false;
    }

    @Override
    public boolean editArea(int orderNum, String newVal) {
        if(dao.orders.containsKey(orderNum)){
            Order order = dao.orders.get(orderNum);
            order.area = new BigDecimal(newVal).setScale(2, RoundingMode.HALF_UP);
            return this.recalc(orderNum);
        }
        return false;
    }
    
    public boolean recalc(int orderNum){
        // Redo all, given the idea that state, product type, and area have been changed.
        // In every use case, this if statement should be a useless check. 
        // Still, better to err on the side of caution.
        if(dao.orders.containsKey(orderNum)){
            Order order = dao.orders.get(orderNum);
            // Grab from products
            order.costPerSquare =dao.products.get(order.product).costPer;
            order.labPerSquare = dao.products.get(order.product).laborPer;
            // Get the total costs
            order.matCost = order.costPerSquare.multiply(order.area);
            order.labCost = order.labPerSquare.multiply(order.area);
            // Calculate taxRate and tax total
            order.taxRate=dao.taxes.get(order.state).tax;
            order.tax =order.matCost.add(order.labCost);
            order.tax = order.tax.multiply(order.taxRate.divide(new BigDecimal("100").setScale(2, RoundingMode.HALF_UP)));
            // Then redo total.
            order.total=order.matCost.add(order.labCost).add(order.tax);
            order.total = order.total.setScale(2, RoundingMode.HALF_UP);
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteOrder(int orderNum) {
        if(dao.orders.containsKey(orderNum)){
            dao.orders.remove(orderNum);
            return true;
        }
        return false;
    }

    @Override
    public void endStep() {
        dao.exportAll();
    }

    
}
