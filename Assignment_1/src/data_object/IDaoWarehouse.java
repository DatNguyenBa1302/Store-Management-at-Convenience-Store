/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package data_object;

/**
 *
 * @author User
 */
public interface IDaoWarehouse {
    public void importReceipt(DaoReport storeInWarehouseClass);
    public void exportReceipt(DaoReport storeInWarehouseClass);
    public void loadFileWarehouse(String str);
}
