/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package product_object;


import java.text.SimpleDateFormat;
import java.util.Date;


/**
 *
 * @author User
 */
public class Warehouse  {
    private String import_ExportCode;
    private String import_ExportTime;
    private Product product;
    private int quantity;

    public Warehouse(String import_ExportCode, String import_ExportTime, Product product, int quantity) {
        this.import_ExportCode = import_ExportCode;
        this.import_ExportTime = import_ExportTime;
        this.product = product;
        this.quantity = quantity;
    }
    
    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
    
    public String getImport_ExportCode() {
        return import_ExportCode;
    }

    public String getImport_ExportTime() {
        return import_ExportTime;
    }

    public void setImport_ExportCode(String import_ExportCode) {
        this.import_ExportCode = import_ExportCode;
    }

    public void setImport_ExportTime(String import_ExportTime) {
        this.import_ExportTime = import_ExportTime;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return  import_ExportCode +" - "+ import_ExportTime +" - "+ product.getCode()+" - "+product.getName() +" - "+ quantity;
    }
    
    public String screenReceipt() {
        return String.format("%-16s%-20s%-20s%-20s%-25s%-20s%-5d", import_ExportCode, import_ExportTime, product.getCode(), product.getName(), product.getManufacturingDate(), product.getExpirationDate(), quantity);
    }
}
