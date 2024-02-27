/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package product_object;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


/**
 *
 * @author User
 */
public class Product {
    private String code;
    private String name;
    private String manufacturingDate;
    private String expirationDate;
    private String typeProduct;

    public Product() {
    }
    
    public Product(String code, String name,String typeProduct, String manufacturingDate, String expirationDate) {
        this.code = code;
        this.name = name;
        this.manufacturingDate = manufacturingDate;
        this.expirationDate = expirationDate;
        this.typeProduct = typeProduct;
    }   
    
    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public String getManufacturingDate() {      
        return manufacturingDate;
    }

    public String getExpirationDate() {
        return expirationDate;
    }

    public String getTypeProduct() {
        return typeProduct;
    }
    
    public void setManufacturingDate(String  manufacturingDate) {
        this.manufacturingDate = manufacturingDate;
    }

    public void setExpirationDate(String expirationDate) {
        this.expirationDate = expirationDate;
    }
    
    public void setCode(String code) {
        this.code = code;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTypeProduct(String typeProduct) {
        this.typeProduct = typeProduct;
    }
    
    @Override
    public String toString() {
        return code + " - " + typeProduct + " - "+ name  ;
    }
    
    public String screenProduct() {
        return String.format("%-16s%-20s%-20s", code, typeProduct, name);
    }
}
