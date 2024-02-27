/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package action_service;

import data_object.DaoProduct;
import data_object.DaoReport;
import data_object.DaoStoreFile;
import data_object.DaoWarehouse;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author Admin
 */
public class service {
    private DaoProduct productManage;
    private DaoReport  reportManage;
    private DaoWarehouse warehouseManage;
    private DaoStoreFile storeFile;
    private int choice;
    Scanner sc = new Scanner(System.in);
    public service() {
        this.productManage = new DaoProduct();   
        this.warehouseManage = new DaoWarehouse(this.productManage);
        this.reportManage = new DaoReport(this.productManage,this.warehouseManage);
        this.storeFile = new DaoStoreFile(this.productManage,this.warehouseManage);
    }

    public DaoProduct getProductManage() {
        return productManage;
    }

    public DaoReport getReportManage() {
        return reportManage;
    }

    public DaoWarehouse getWarehouseManage() {
        return warehouseManage;
    }

    public DaoStoreFile getStoreFile() {
        return storeFile;
    }
    
   public int get_choice(ArrayList<String> list){
        for(int i =0;i<list.size();i++){
           System.out.println(+(i+1)+". "+list.get(i));
        }
        System.out.print("Plese choose from 1 to "+list.size() + ": ");
        choice = sc.nextInt();
        System.out.println("|--------------------------------------|");
        return choice;
   }
}
