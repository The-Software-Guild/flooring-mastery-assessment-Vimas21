/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core;

import dao.FloorDao;
import dao.FloorDaoImpl;
import java.io.FileNotFoundException;
import serv.FloorServ;
import serv.FloorServImpl;
import user.io.FloorIo;
import user.io.FloorIoImpl;

/**
 *
 * @author Samma
 */
public class Master {
    
    public static void main(String[] args) throws FileNotFoundException{
        FloorDao dao = new FloorDaoImpl();
        FloorServ serv = new FloorServImpl(dao);
        FloorIo io = new FloorIoImpl();
        Controller cont = new Controller(io, serv);
        cont.run();
        System.out.println("It worked.");
    }
}
