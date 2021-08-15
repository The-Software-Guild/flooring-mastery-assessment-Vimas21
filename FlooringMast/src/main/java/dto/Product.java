package dto;

import java.math.BigDecimal;


public class Product {
    public String type;
    public BigDecimal costPer;
    public BigDecimal laborPer;
    
    public Product(String type, BigDecimal costPer, BigDecimal laborPer){
        this.type = type;
        this.costPer = costPer;
        this.laborPer = laborPer;
    }
    
}
