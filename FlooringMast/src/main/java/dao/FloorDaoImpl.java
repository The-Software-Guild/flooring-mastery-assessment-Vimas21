/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import dto.Order;
import dto.Product;
import dto.Tax;
import java.io.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;


public class FloorDaoImpl implements FloorDao {
    int maxOrder;
            
    public FloorDaoImpl(){
        int maxOrder=0;
    }
    
    @Override
    public void load() throws FileNotFoundException{
        File folder = new File("Orders");
        File[] fileList = folder.listFiles();
        Scanner sc;
        for(File file : fileList){
            sc = new Scanner(new BufferedReader(new FileReader(file)));
            sc.nextLine();
            while(sc.hasNextLine()){
                orderBreakdown(sc.nextLine());
            }
        }
        sc = new Scanner(new BufferedReader(new FileReader("Data/Taxes.txt")));
        sc.nextLine();
        while(sc.hasNextLine()){
            taxBreakdown(sc.nextLine());
        }
        sc = new Scanner(new BufferedReader(new FileReader("Data/Products.txt")));
        sc.nextLine();
        while(sc.hasNextLine()){
            productBreakdown(sc.nextLine());
        }
        
    }

    @Override
    public Order fetchOrd(String key) {
        return orders.get(key);
    }
    
    public List<Order> allOrders(){
        ArrayList<Order> allOrds = new ArrayList<Order>();
        for(int key : orders.keySet()){
            allOrds.add(orders.get(key));
            maxOrder++;
        }
        return allOrds;
    }

    @Override
    public Tax fetchTax(String key) {
        return taxes.get(key);
    }

    @Override
    public Product fetchProduct(String key) {
        return products.get(key);
    }

    @Override
    public void orderBreakdown(String line) {
        List<String> brokenDown = Arrays.asList(line.split(","));
        orders.put(Integer.valueOf(brokenDown.get(0)), new Order(Integer.valueOf(brokenDown.get(0)), brokenDown.get(1), 
                brokenDown.get(2), new BigDecimal(brokenDown.get(3)).setScale(2, RoundingMode.HALF_UP), brokenDown.get(4), 
                new BigDecimal(brokenDown.get(5)).setScale(2, RoundingMode.HALF_UP), 
                new BigDecimal(brokenDown.get(6)).setScale(2, RoundingMode.HALF_UP), 
                new BigDecimal(brokenDown.get(7)).setScale(2, RoundingMode.HALF_UP), 
                new BigDecimal(brokenDown.get(8)).setScale(2, RoundingMode.HALF_UP), 
                new BigDecimal(brokenDown.get(9)).setScale(2, RoundingMode.HALF_UP), 
                new BigDecimal(brokenDown.get(10)).setScale(2, RoundingMode.HALF_UP), 
                new BigDecimal(brokenDown.get(11)).setScale(2, RoundingMode.HALF_UP)));
        
    }
    
    public void taxBreakdown(String line){
        List<String> brokenDown = Arrays.asList(line.split(","));
        taxes.put(brokenDown.get(0), new Tax(brokenDown.get(0), brokenDown.get(1), 
            new BigDecimal(brokenDown.get(2)).setScale(2, RoundingMode.HALF_UP)));
    }
    
    public void productBreakdown(String line){
        List<String> brokenDown = Arrays.asList(line.split(","));
        products.put(brokenDown.get(0), new Product(brokenDown.get(0),
            new BigDecimal(brokenDown.get(1)).setScale(2, RoundingMode.HALF_UP),
            new BigDecimal(brokenDown.get(2)).setScale(2, RoundingMode.HALF_UP)));
    }

    @Override
    public void newOrder(Order newOrder) {
        newOrder.order=maxOrder;
        maxOrder++;
        orders.put(newOrder.order, newOrder);
    }

    @Override
    public boolean isValid(String state, String product) {
        return taxes.containsKey(state) && products.containsKey(product);
    }
    
}
