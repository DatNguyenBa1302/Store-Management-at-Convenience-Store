/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package data_object;

import java.util.List;
import product_object.Product;
import product_object.Warehouse;

/**
 *
 * @author User
 */
public interface IDaoProduct {
    public void addProduct();
    public void updateProduct(DaoWarehouse Warehouse,DaoReport Report);
    public void deleteProduct(DaoWarehouse Warehouse);
    public void showAllProduct();
    public void loadFileProduct(String str);
}
