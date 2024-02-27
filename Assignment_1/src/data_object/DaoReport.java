/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package data_object;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;
import javafx.util.converter.LocalDateTimeStringConverter;
import product_object.InStore;
import product_object.Product;
import product_object.Warehouse;

/**
 *
 * @author Admin
 */
public class DaoReport implements IDaoReport {
    
    private DaoProduct productInReport;
    private DaoWarehouse warehouseInReport;
    ArrayList<InStore> storeList;
    Scanner sc = new Scanner(System.in);
    
    public DaoReport(DaoProduct pList, DaoWarehouse wList) {
        this.productInReport = pList;
        this.warehouseInReport = wList;
        this.storeList = new ArrayList<>();
    }
    
    public ArrayList<InStore> getStoreList() {
        return storeList;
    }
    
    public void setStoreList(ArrayList<InStore> storeList) {
        this.storeList = storeList;
    }
    
    @Override
    public void expiredProduct() {
        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate today = LocalDate.now();
        System.out.printf("%-16s%-20s%-20s%-25s%-20s%-10s", "Code product", "Type", "Name product", "Manufacturing date" ,"Expirtation date", "Quantity");
        System.out.println();
        for (InStore inStore : storeList) {
            LocalDate inStoreDay = LocalDate.parse(inStore.getProduct().getExpirationDate(), format);
            if (inStoreDay.compareTo(today) < 0) {
                System.out.println(inStore.toString());
            }
        }
    }
    
    @Override
    public void sellingProduct() {
        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate today = LocalDate.now();
        System.out.printf("%-16s%-20s%-20s%-25s%-20s%-10s", "Code product", "Type", "Name product", "Manufacturing date" ,"Expirtation date", "Quantity");
        System.out.println();
        for (InStore inStore : storeList) {
            LocalDate inStoreDay = LocalDate.parse(inStore.getProduct().getExpirationDate(), format);
            if (inStoreDay.compareTo(today) > 0) {
                System.out.println(inStore.toString());
            }
        }
    }
    
    @Override
    public void runningOutProduct() {
        Comparator<InStore> c = new Comparator<InStore>() {
            @Override
            public int compare(InStore o1, InStore o2) {
                return o1.getQuantity() - o2.getQuantity();
            }
        };
        
        Collections.sort(storeList, c);
        System.out.printf("%-16s%-20s%-20s%-25s%-20s%-10s", "Code product", "Type", "Name product", "Manufacturing date" ,"Expirtation date", "Quantity");
        System.out.println();
        for (InStore inStore : storeList) {
            if (inStore.getQuantity() <= 3) {
                System.out.println(inStore.toString());
            }
        }
    }
    
    @Override
    public void importExportReceipt() {
        System.out.printf("Input product code: ");
        String newCode = sc.nextLine();
        ArrayList<Warehouse> warehouseList = warehouseInReport.getWarehousetList();
        System.out.printf("%-16s%-20s%-20s%-20s%-25s%-20s%-5s", "Receipt code", "Time", "Code product", "Name product", "Manufacturing date", "Expirtation date", "Quantity");
        System.out.println();
        for (Warehouse warehouse : warehouseList) {
            if (warehouse.getProduct().getCode().equals(newCode)) {
                System.out.println( warehouse.screenReceipt());
            }
        }
    }
    
    public int quantityInStore(Product product) {
        int quantity = -1;
        for (InStore inStore : storeList) {
            if (inStore.getProduct().equals(product)) {
                quantity = inStore.getQuantity();
            }
        }
        return quantity;
    }
    
    public void updateQuantityFromExport(int newQuantity, String code, String manufacturingDate) {
        for (InStore inStore : storeList) {
            if (inStore.getProduct().getCode().matches(code) && inStore.getProduct().getManufacturingDate().matches(manufacturingDate)) {
                inStore.setQuantity(inStore.getQuantity() - newQuantity);
            }
        }
    }
    
    public void addToStoreList(Product product, int quantity) {
        this.storeList.add(new InStore(product, quantity));
        if (storeList.size() >= 2) {
            deletProductHasInList();
        }
    }
    
    public void deletProductHasInList() {
        int sum = 0;
        for (int i = 0; i < storeList.size(); i++) {
            for (int j = i + 1; j < storeList.size(); j++) {
                if (storeList.get(i).getProduct().getCode().equals(storeList.get(j).getProduct().getCode())
                        && storeList.get(i).getProduct().getManufacturingDate().equals(storeList.get(j).getProduct().getManufacturingDate())) {
                    sum += storeList.get(j).getQuantity();
                    storeList.remove(j);
                    storeList.get(i).setQuantity(storeList.get(i).getQuantity() + sum);
                }
            }       
        }
    }
    
    public int indexOfCodeProductInStore(String code) {
        int index = -1;
        for (int i = 0; i < storeList.size(); i++) {
            if (storeList.get(i).getProduct().getCode().equals(code)) {
                index = i;
                break;
            }
        }
        return index;
    }
    
    public void showAllProductInStore() {
        if (storeList.size() != 0) {
            System.out.printf("%-16s%-20s%-20s%-25s%-20s%-10s", "Code product", "Type", "Name product", "Manufacturing date" ,"Expirtation date", "Quantity");
            System.out.println();
            for (InStore inStore : storeList) {
                System.out.println(inStore.toString());
            }
            System.out.println("|------------------------------|");
        } else {
            System.err.println("List does not have any product");
        }
    }
    
    public void loadFileToGetWarehouseInReport(String str) {
        File f = new File(str);
        try {
            FileReader fr = new FileReader(f);
            BufferedReader br = new BufferedReader(fr);
            String[] text;
            String string = br.readLine();;
            while (true) {
                if (string == null) {
                    break;
                }
                text = string.split(" - ");
                String Import_ExportCode = text[0].trim();
                String Import_ExportTime = text[1].trim();
                String codeProduct = text[2].trim();
                String nameProduct = text[3].trim();
                int quantity = Integer.parseInt(text[4].trim());
                String manufacturingDate = "", expirationDate = "";
                manufacturingDate = text[5].trim();
                expirationDate = text[6].trim();
                
                if (Import_ExportCode.startsWith("I")) {
                    Product product = productInReport.getProduct(codeProduct);
                    Product p = new Product(product.getCode(), product.getName(), product.getTypeProduct(), manufacturingDate, expirationDate);
                    addToStoreList(p, quantity);
                }
                if (Import_ExportCode.startsWith("E")) {
                    for (InStore inStore : storeList) {
                        if (inStore.getProduct().getCode().matches(codeProduct) && inStore.getProduct().getManufacturingDate().matches(manufacturingDate)) {
                            inStore.setQuantity(inStore.getQuantity() - quantity);
                        }
                    }
                }
                string = br.readLine();
            }
            fr.close();
            br.close();
        } catch (IOException e) {
        }
    }
}
