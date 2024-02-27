/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package aplication;

import action_service.service;
import data_object.DaoProduct;
import data_object.DaoWarehouse;
import java.security.Provider;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author User
 */
public class Main {

    public static void main(String[] args) {
        service service = new service();
        int choiceOption, choiceProduct, choiceWarehouse, choiceReport;
        Scanner sc = new Scanner(System.in);

        String productPath = "C:\\Users\\User\\Documents\\NetBeansProjects\\Assignment 1\\product.dat";
        String warehousePath = "C:\\Users\\User\\Documents\\NetBeansProjects\\Assignment 1\\warehouse.dat";
        service.getProductManage().loadFileProduct(productPath);
        service.getWarehouseManage().loadFileWarehouse(warehousePath);
        service.getReportManage().loadFileToGetWarehouseInReport(warehousePath);
//        service.getProductManage().loadFileToGetWarehouseInProduct(warehousePath);

        ArrayList<String> option = new ArrayList<>();
        ArrayList<String> product = new ArrayList<>();
        ArrayList<String> warehouse = new ArrayList<>();
        ArrayList<String> report = new ArrayList<>();

        option.add("Manage products");
        option.add("Manage Warehouse");
        option.add("Report");
        option.add("Store data to files");
        option.add("Close the application");

        product.add("Add a product");
        product.add("Update product information");
        product.add("Delete product");
        product.add("Show all product");

        warehouse.add("Create an import receipt");
        warehouse.add("Create an export receipt");
        warehouse.add("Print all receipt");
        warehouse.add("Quit");

        report.add("Products that have expired");
        report.add("The products that the store is selling");
        report.add("Products that are running out of stock");
        report.add("Import/export receipt of a product");

        do {
            System.out.println("|--------------------------------------|");
            System.out.println("|           CONVENIENT STORE           |");
            System.out.println("|--------------------------------------|");
            choiceOption = service.get_choice(option);
            switch (choiceOption) {
                case 1:
                    System.out.println("|--------------------------------------|");
                    System.out.println("|            PRODUCT MANAGEMENT        |");
                    System.out.println("|--------------------------------------|");
                    choiceProduct = service.get_choice(product);
                    
                    switch (choiceProduct) {
                        case 1:
                            while (true) { 
                                boolean cond = true;
                                service.getProductManage().addProduct();
                                System.out.println("Do you want to add other product: ");
                                System.out.printf("[Y] for Yes" + "\n[N] for No" + "\nYour choice: ");
                                String choice = sc.nextLine();
                                System.out.println("|--------------------------------------|");
                                    if (choice.equalsIgnoreCase("n")) {                                       
                                        break;
                                    } else if (!choice.equalsIgnoreCase("y") || !choice.equalsIgnoreCase("n")) {
                                        cond = false;
                                        System.out.println("Must choice yes or no");
                                    
                                }
                            }
                            break;
                        case 2:
                            service.getProductManage().updateProduct(service.getWarehouseManage(),service.getReportManage());
                            break;
                        case 3:
                            service.getProductManage().deleteProduct(service.getWarehouseManage());
                            break;
                        default:
                            service.getProductManage().showAllProduct();
                            break;
                    }
                    break;
                case 2:
                    System.out.println("|--------------------------------------|");
                    System.out.println("|           WAREHOUSE MANAGEMENT       |");
                    System.out.println("|--------------------------------------|");
                    choiceWarehouse = service.get_choice(warehouse);
                    switch (choiceWarehouse) {
                        case 1:
                            service.getWarehouseManage().importReceipt(service.getReportManage());
                            break;
                        case 2:
                            service.getWarehouseManage().exportReceipt(service.getReportManage());
                            break;
                        case 3:
                            service.getWarehouseManage().printImportReceipt();
                            System.out.println();
                            service.getWarehouseManage().printExportReceipt();
                            break;
                        default:
                            break;
                    }
                    break;
                case 3:
                    System.out.println("|--------------------------------------|");
                    System.out.println("|                 REPORT               |");
                    System.out.println("|--------------------------------------|");
                    choiceReport = service.get_choice(report);
                    switch (choiceReport) {
                        case 1:
                            service.getReportManage().expiredProduct();
                            break;
                        case 2:
                            service.getReportManage().sellingProduct();
                            break;
                        case 3:
                            service.getReportManage().runningOutProduct();
                            break;
                        default:
                            service.getReportManage().importExportReceipt();

                    }
                    break;
                case 4:
                    service.getStoreFile().storeProduct(productPath);
                    service.getStoreFile().storeWarehouse(warehousePath);
                    break;
                default:
            }
        } while (choiceOption > 0 && choiceOption < option.size());
    }
}
