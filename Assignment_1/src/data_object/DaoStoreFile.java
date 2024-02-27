/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package data_object;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import product_object.Product;
import product_object.Warehouse;

/**
 *
 * @author User
 */
public class DaoStoreFile implements IDaoStoreFile{
    private DaoProduct productInFile;
    private DaoWarehouse warehouseInFile;
    public DaoStoreFile(DaoProduct pList, DaoWarehouse wList) {
        this.productInFile = pList;
        this.warehouseInFile = wList;
    }
    
    @Override
    public void storeProduct(String fileName) {
        productInFile.saveToFile(fileName);
        System.out.println("\nStoring warehouse to warehouse.dat file is complete\n ");
    }
    

    @Override
    public void storeWarehouse(String fileName) {
        warehouseInFile.saveToFile(fileName);
        System.out.println("\nStoring product to product.dat file is complete\n ");
}
}
