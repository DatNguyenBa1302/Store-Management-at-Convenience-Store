/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package data_object;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import product_object.InStore;
import product_object.Product;
import product_object.Warehouse;

/**
 *
 * @author User
 */
public class DaoProduct implements IDaoProduct {

    ArrayList<Product> productList;
    Scanner sc = new Scanner(System.in);
    InputClass inputClass = new InputClass();

    public DaoProduct() {
        this.productList = new ArrayList<>();
    }

    public ArrayList<Product> getProductList() {
        return productList;
    }

    public void setProductList(ArrayList<Product> productList) {
        this.productList = productList;
    }

    @Override
    public void addProduct() {
        String code, name, manufacturingDate, expirationDate, type;
        int quality;
        System.out.println("Input product: ");
        System.out.println("1. Product for daily use");
        System.out.println("2. Product with long shelf life");
        int choice = inputClass.inputChoice("Input 1 or 2, please", 0, 3);
        if (choice == 1) {
            type = "daily product";
        } else {
            type = "lasting product";
        }
        while (true) {
            code = inputClass.inputString("Input code product").trim();
            if (findIndex(code) < 0) {
                break;
            } else {
                System.out.println("The product is exits in list");
            }
        }
        name = inputClass.inputString("Input name product");

        productList.add(new Product(code, name, type, "", ""));
        System.out.println("|----------Add successfully!----------|");
    }

    @Override
    public void updateProduct(DaoWarehouse Warehouse, DaoReport Report) {
        String oldCode, newName, newCode = "";
        boolean cond = true;
        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        //date1 cho newManufacturingDate, date2 cho newExpirationDate
        LocalDate date1, date2;
        if (productList.size() != 0) {
            showAllProduct();
            oldCode = inputClass.inputString("Input code of product you want to update").trim();
            if (findIndex(oldCode) < 0) {
                System.out.println("Product does not exist");
            } else {
                do {
                    System.out.println("|------------------------------|");
                    System.out.println("|          UPDATE OPTION       |");
                    System.out.println("|------------------------------|");
                    System.out.println("What imformation do you want to update: \n");
                    System.out.println("1.Code ");
                    System.out.println("2.Product name ");
                    System.out.println("3.Exit ");
                    int choice = inputClass.inputInt("Input your choice");
                    switch (choice) {
                        case 1:
                            do {
                                newCode = inputClass.inputString("Input new code of product").trim();
                                if (findIndex(newCode) < 0) {
                                    break;
                                }
                            } while (true);

                            for (Product product : productList) {
                                if (product.getCode().matches(oldCode)) {
                                    product.setCode(newCode);
                                }
                            }
                            for (Warehouse importProduct : Warehouse.getImportReceiptList()) {
                                if (importProduct.getProduct().getCode().matches(oldCode)) {
                                    importProduct.getProduct().setCode(newCode);
                                }
                            }
                            for (Warehouse exportProduct : Warehouse.getExortReceiptList()) {
                                if (exportProduct.getProduct().getCode().matches(oldCode)) {
                                    exportProduct.getProduct().setCode(newCode);
                                }
                            }
                            for (InStore inStore : Report.getStoreList()) {
                                if(inStore.getProduct().getCode().matches(oldCode)){
                                    inStore.getProduct().setCode(newCode);
                                }
                            }
                            break;
                        case 2:
                            newName = inputClass.inputString("Input new name of product");
                            for (Product product : productList) {
                                if (product.getCode().matches(newCode)) {
                                    product.setName(newName);
                                }
                            }
                            for (Warehouse importProduct : Warehouse.getImportReceiptList()) {
                                if (importProduct.getProduct().getCode().matches(newCode)) {
                                    importProduct.getProduct().setName(newName);
                                }
                            }
                            for (Warehouse exportProduct : Warehouse.getExortReceiptList()) {
                                if (exportProduct.getProduct().getCode().matches(newCode)) {
                                    exportProduct.getProduct().setName(newName);
                                }
                            }
                            for (InStore inStore : Report.getStoreList()) {
                                if(inStore.getProduct().getCode().matches(newCode)){
                                    inStore.getProduct().setName(newName);
                                }
                            }
                            break;
                        default:
                            System.out.println();
                            showAllProduct();
                            System.out.println("|----------Update successfully!----------|");
                            cond = false;
                            break;
                    }
                } while (cond);
            }
        } else {
            System.err.println("List does not have any product\n");
        }
    }

    @Override
    public void deleteProduct(DaoWarehouse Warehouse) {
        String newCode;
        newCode = inputClass.inputString("Input code of product you want to delete");
        int index = findIndex(newCode);
        int indexInWarehouse = -1;
        ArrayList<Warehouse> warehouseList = Warehouse.getWarehousetList();
        for (int i = 0; i < warehouseList.size(); i++) {
            if (warehouseList.get(i).getProduct().getCode().equals(newCode)) {
                indexInWarehouse = i;
            }
        }
        if (indexInWarehouse >= 0) {
            System.err.println("This Product Had In Import (Or Export) Receipt. Can Not DELETE!!!");
        } else if (index > 0) {
            System.out.println("Do you want to delete: ");
            System.out.println("1.Yes ");
            System.out.println("2.No ");
            int choice = inputClass.inputChoice("Your choice", 0, 3);
            if (choice == 1) {
                productList.remove(index);
                System.out.println("Delete Successfully!!!");
            } else {
                System.out.println("Delete Failed!!!!");
            }
        } else {
            System.out.println("The product is not exited");
        }
        System.out.println("|-------------Completed!---------------|");

    }

    @Override
    public void showAllProduct() {
        if (productList.size() != 0) {
            Comparator<Product> c = new Comparator<Product>() {
                @Override
                public int compare(Product o1, Product o2) {
                    return o1.getCode().compareTo(o2.getCode());
                }
            };

            Collections.sort(productList, c);
            System.out.printf("%-16s%-20s%-20s", "Product code", "Type", "Product name");
            System.out.println();
            for (Product product1 : productList) {
                System.out.println(product1.screenProduct());
            }
            System.out.println("|--------------------------------------|");
        } else {
            System.err.println("List does not have any product");
        }
    }

    @Override
    public void loadFileProduct(String str) {
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
                text = string.split("-");
                
                String code = text[0].trim();
                String type = text[1].trim();
                String name = text[2].trim();
                productList.add(new Product(code, name, type, "", ""));

                string = br.readLine();
            }
            fr.close();
            br.close();
        } catch (IOException e) {
        }
    }

    public void saveToFile(String fileName) {
        try {
            PrintWriter pw = new PrintWriter(fileName);
            for (Product product : productList) {
                pw.println(product);
                pw.flush();
            }
            pw.flush();
            pw.close();
        } catch (FileNotFoundException ex) {
        }
    }

    public boolean checkValidDate(String stringDate) {
        boolean valid = true;
        int count = 0;
        String[] arrayString = stringDate.split("-");
        for (int i = 0; i < arrayString.length; i++) {
            for (int j = 0; j < arrayString[i].length(); j++) {
                if (!Character.isDigit(arrayString[i].charAt(j))) {
                    valid = false;
                    break;
                }
            }
        }
        if (arrayString[0].length() == 2 && arrayString[1].length() == 2 && arrayString[2].length() == 4 && valid) {
            return false;
        } else {
            return true;
        }
    }

    public int findIndex(String code) {
        int indexCode = -1;
        for (int i = 0; i < productList.size(); i++) {
            if (productList.get(i).getCode().equals(code)) {
                indexCode = i;
                break;
            }
        }
        return indexCode;
    }

    public Product getProduct(String code) {
        int index = findIndex(code);
        if (index < 0) {
            return null;
        } else {
            return productList.get(index);
        }
    }
}
