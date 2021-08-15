/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core;

import dao.FloorDao;
import dto.Order;
import serv.FloorServ;
import user.io.FloorIo;

/**
 *
 * @author Samma
 */
public class Controller {
    FloorIo io;
    FloorServ serv;
    
    public Controller(FloorIo io, FloorServ serv){
        this.io = io;
        this.serv = serv;
    }
    
    public void run(){
        int input = 0;
        String textInput = "";
        while(true){
            io.displayOptions();
            input = io.readInt("Which would you like to select?");
            if(input == 1){
                io.displayAll(serv.fetchAllOrders());
            }
            if(input == 2){
                String cust = io.readIn("What is the customers name?");
                String state =  io.readIn("What state?");
                String product = io.readIn("Product type?");
                String area = io.readIn("Area?");
                Order newOrder = serv.processOrder(cust, state, product, area);
                io.print("Would you like to add the following order:");
                String strInput = io.readIn(serv.fetchOneOrder(newOrder));
                if(strInput.equalsIgnoreCase("yes")){
                    serv.addOrder(newOrder);
                    io.print("Added!");
                }
            }
            if(input==3){
                input = io.displayEditOptions();
                if(input == 1){
                    textInput = io.readIn("What would you like the new name to be?");
                    io.print("I have no idea why this is happening");
                    input = Integer.valueOf(io.readIn("What's the order ID?"));
                    if(!serv.editName(input, textInput)){
                        io.print("Please check to make sure you have the correct order ID.");
                    }
                }
                else if(input == 2){
                    textInput = io.readIn("What would you like the new State to be? (In abbreviation, please.)");
                    input = io.readInt("What's the order ID?");
                    if(!serv.editState(input, textInput)){
                        io.print("Please check to make sure you have the correct order ID and state abbreviation.");
                    }
                }
                else if(input == 3){
                    textInput = io.readIn("What would you like the new product to be?");
                    input = io.readInt("What's the order ID?");
                    if(!serv.editProduct(input, textInput)){
                        io.print("Please check to make sure you have the correct order ID and product type.");
                    }
                }
                else if(input == 4){
                    textInput = io.readIn("What would you like the new area to be?");
                    textInput = io.readIn("What's the order ID?");
                    if(!serv.editArea(input, textInput)){
                        io.print("Please check to make sure you have the correct order ID and product type.");
                    }
                }
                else{
                    io.print("Not recognized, sorry.");
                }
            }
            if(input == 4){
                input=io.readInt("What order number would you like to get rid of?");
                if(serv.hasOrderNum(input)){
                    if(io.promptDelete(serv.fetchOneOrder(input)))
                        serv.deleteOrder(input);
                }
                else{
                    io.print("Order not found.");
                }
            }
            if(input == 6){
                return;
            }
        }
    }
    
}
