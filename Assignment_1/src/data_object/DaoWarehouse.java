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
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;
import javax.swing.text.DateFormatter;
import product_object.InStore;
import product_object.Product;
import product_object.Warehouse;

/**
 *
 * @author Admin
 */
public class DaoWarehouse implements IDaoWarehouse {

    Scanner sc = new Scanner(System.in);
    private DaoProduct productInWarehouse;
    ArrayList<Warehouse> importReceiptList;
    ArrayList<Warehouse> exortReceiptList;
    InputClass input = new InputClass();

    public DaoWarehouse(DaoProduct list) {
        this.importReceiptList = new ArrayList<>();
        this.exortReceiptList = new ArrayList<>();
        this.productInWarehouse = list;
    }

    public ArrayList<Warehouse> getImportReceiptList() {
        return importReceiptList;
    }

    public ArrayList<Warehouse> getExortReceiptList() {
        return exortReceiptList;
    }

    public void setImportReceiptList(ArrayList<Warehouse> importReceiptList) {
        this.importReceiptList = importReceiptList;
    }

    public void setExortReceiptList(ArrayList<Warehouse> exortReceiptList) {
        this.exortReceiptList = exortReceiptList;
    }

    public ArrayList<Warehouse> getWarehousetList() {
        ArrayList<Warehouse> warehouseList = new ArrayList<>();
        for (Warehouse warehouse : this.importReceiptList) {
            warehouseList.add(warehouse);
        }

        for (Warehouse warehouse : this.exortReceiptList) {
            warehouseList.add(warehouse);
        }
        return warehouseList;
    }

    @Override
    public void importReceipt(DaoReport storeInWarehouseClass) {
        boolean cond = true;
        String import_exportCode = "I0000000";
        String code, time, choice, manufacturingDate, expirationDate = null;
        int quantity;
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        int number = 1, index;
        String format;
        while (cond) {
            ArrayList<Product> productList = new ArrayList<>();
            Product product;
            format = import_exportCode.format("I" + "%07d", number);
            if (findImportExportCode(format) >= 0) {
                number++;
            } else {
                while (cond) {
                    productInWarehouse.showAllProduct();
                    code = input.inputString("Input code of product want to import");
                    index = productInWarehouse.findIndex(code);
                    product = productInWarehouse.getProduct(code);
                    if (index < 0) {
                        System.out.println("nThe product is not in Product list");
                    } else {
                        LocalDate today = LocalDate.now();
                        time = today.format(dateFormat);
                        quantity = input.inputInt("Input the quantity to import");
                        manufacturingDate = input.inputDate("Input manufactuing date");
                        if (productInWarehouse.getProduct(code).getTypeProduct().equals("lasting product")) {
                            LocalDate date1 = LocalDate.parse(manufacturingDate, dateFormat);
                            while (true) {
                                expirationDate = input.inputDate("Input expiration date");
                                LocalDate date2 = LocalDate.parse(expirationDate, dateFormat);
                                if (date1.isBefore(date2)) {
                                    break;
                                } else {
                                    System.out.println("The expiration date must be before expiration manufactuing date");
                                }
                            }
                        } else if (productInWarehouse.getProduct(code).getTypeProduct().equals("daily product")) {
                            //Ngày của hxd tự cộng thêm 1 ngày
                            LocalDate date1 = LocalDate.parse(manufacturingDate, dateFormat).plusDays(3);
                            expirationDate = date1.format(dateFormat);
                        }
                        Product p = new Product(product.getCode(), product.getName(), product.getTypeProduct(), manufacturingDate, expirationDate);
                        storeInWarehouseClass.addToStoreList(p, quantity);
                        Warehouse warehouse = new Warehouse(format, time, p, quantity);
                        importReceiptList.add(warehouse);
                    }
                    if(importReceiptList.size()>=2) deletWarehouseHasInList(importReceiptList);
                    do {
                        System.out.println("Do you want to continue: ");
                        System.out.printf("[Y] for Yes" + "\n[N] for No" + "\nYour choice: ");
                        choice = sc.nextLine();
                        if (choice.equalsIgnoreCase("y") || choice.equalsIgnoreCase("n")) {
                            break;
                        } else {
                            System.out.println("Please input Yes or No");
                        }
                    } while (true);
                    System.out.println("|--------------------------------------|");
                    if (choice.equalsIgnoreCase("n")) {
                        cond = false;
                    }
                }
            }
        }
        
    }

    @Override
    public void exportReceipt(DaoReport storeInWarehouseClass) {
        boolean cond = true;
        String import_exportCode = "E0000000";
        String code, time, choice, manufacturingDate, expirationDate = null;
        int quantity;
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        int number = 1, index = -1;
        String format;
        while (cond) {
            format = import_exportCode.format("E" + "%07d", number);
            if (findImportExportCode(format) >= 0) {
                number++;
            } else {
                while (cond) {
                    storeInWarehouseClass.showAllProductInStore();
                    code = input.inputString("Input code of product want to export").trim();
                    for (int i = 0; i < storeInWarehouseClass.getStoreList().size(); i++) {
                        if (storeInWarehouseClass.getStoreList().get(i).getProduct().getCode().matches(code)) {
                            index = i;
                        }
                    }
                    if (index < 0) {
                        System.out.println("The product is not in Store");
                    } else {
                        Product product = chooseProduct(code, storeInWarehouseClass);
                        if (product != null) {
                            int quantityInStore = storeInWarehouseClass.quantityInStore(product);
                            if (storeInWarehouseClass.indexOfCodeProductInStore(code) < 0) {
                                System.out.println("The product is not in Store");
                            } else {
                                LocalDate today = LocalDate.now();
                                time = today.format(dateFormat);

                                while (true) {
                                    quantity = input.inputInt("Input the quantity to export");
                                    if (quantity <= quantityInStore) {
                                        break;
                                    } else {
                                        System.out.println("The quantity in Export receipt must be less than in Store");
                                    }
                                }
                                storeInWarehouseClass.updateQuantityFromExport(quantity, code, product.getManufacturingDate());
                                Product p = new Product(product.getCode(), product.getName(), product.getTypeProduct(), product.getManufacturingDate(), product.getExpirationDate());
                                Warehouse warehouse = new Warehouse(format, time, p, quantity);
                                exortReceiptList.add(warehouse);
                            }
                            if(exortReceiptList.size()>=2) deletWarehouseHasInList(exortReceiptList);
                            do {
                                System.out.println("Do you want to continue: ");
                                System.out.printf("[Y] for Yes" + "\n[N] for No" + "\nYour choice: ");
                                choice = sc.nextLine();
                                if (choice.equalsIgnoreCase("y") || choice.equalsIgnoreCase("n")) {
                                    break;
                                } else {
                                    System.out.println("Please input Yes or No");
                                }
                            } while (true);
                            System.out.println("|--------------------------------------|");
                            if (choice.equalsIgnoreCase("n")) {
                                cond = false;
                            }
                        }else{
                            System.err.println("The product is out of stock");
                            cond = false;
                        }
                    }
                }
            }
        }
    }

    @Override
    public void loadFileWarehouse(String str) {
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
                String nameProdut = text[3].trim();
                int quantity = Integer.parseInt(text[4].trim());
                String manufacturingDate = null, expirationDate = null;
                Product product = productInWarehouse.getProduct(codeProduct);
                if (Import_ExportCode.startsWith("I")) {
                    manufacturingDate = text[5].trim();
                    expirationDate = text[6].trim();
                }
                Product p = new Product(product.getCode(), product.getName(), product.getTypeProduct(), manufacturingDate, expirationDate);
                if (Import_ExportCode.startsWith("I")) {
                    importReceiptList.add(new Warehouse(Import_ExportCode, Import_ExportTime, p, quantity));
                    productInWarehouse.getProduct(codeProduct).setManufacturingDate(manufacturingDate);
                    productInWarehouse.getProduct(codeProduct).setExpirationDate(expirationDate);
                } else {
                    exortReceiptList.add(new Warehouse(Import_ExportCode, Import_ExportTime, product, quantity));
                }
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
            for (Warehouse importObject : importReceiptList) {
                String manufacturingDate = importObject.getProduct().getManufacturingDate();
                String expirationDate = importObject.getProduct().getExpirationDate();
                pw.println(importObject + " - " + manufacturingDate + " - " + expirationDate);
                pw.flush();
            }
            for (Warehouse exportObject : exortReceiptList) {
                String manufacturingDate = exportObject.getProduct().getManufacturingDate();
                String expirationDate = exportObject.getProduct().getExpirationDate();
                pw.println(exportObject+ " - " + manufacturingDate + " - " + expirationDate);
                pw.flush();
            }
            pw.flush();
            pw.close();
        } catch (FileNotFoundException ex) {
        }

    }

    public int findImportExportCode(String str) {
        int index = -1;
        for (int i = 0; i < importReceiptList.size(); i++) {
            if (importReceiptList.get(i).getImport_ExportCode().equals(str)) {
                index = i;
                break;
            }
        }
        for (int i = 0; i < exortReceiptList.size(); i++) {
            if (exortReceiptList.get(i).getImport_ExportCode().equals(str)) {
                index = i;
                break;
            }
        }
        return index;
    }

    public void printImportReceipt() {
        Comparator<Warehouse> c = new Comparator<Warehouse>() {
            @Override
            public int compare(Warehouse o1, Warehouse o2) {
                if(o1.getImport_ExportCode().matches(o2.getImport_ExportCode())){
                   return o1.getProduct().getCode().compareTo(o2.getProduct().getCode()); 
                }else{
                    return o1.getImport_ExportCode().compareTo(o2.getImport_ExportCode());
                }            
            }
        };

        Collections.sort(importReceiptList, c);
        System.out.printf("%-16s%-20s%-20s%-20s%-25s%-20s%-5s", "Import code", "Import time", "Code product", "Name product", "Manufacturing date", "Expirtation date", "Quantity");
        System.out.println();
        for (Warehouse importObject : importReceiptList) {
            System.out.println(importObject.screenReceipt());
        }
    }

    public void printExportReceipt() {
        Comparator<Warehouse> c = new Comparator<Warehouse>() {
            @Override
            public int compare(Warehouse o1, Warehouse o2) {
                if(o1.getImport_ExportCode().matches(o2.getImport_ExportCode())){
                   return o1.getProduct().getCode().compareTo(o2.getProduct().getCode()); 
                }else{
                    return o1.getImport_ExportCode().compareTo(o2.getImport_ExportCode());
                }     
            }
        };

        Collections.sort(exortReceiptList, c);
        System.out.printf("%-16s%-20s%-20s%-20s%-25s%-20s%-5s", "Export code", "Export time", "Code product", "Name product", "Manufacturing date", "Expirtation date", "Quantity");
        System.out.println();
        for (Warehouse exportObject : exortReceiptList) {
            System.out.println(exportObject.screenReceipt());
        }
    }
    
    public void deletWarehouseHasInList(ArrayList<Warehouse> ReceiptList) {
        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        int sum = 0;
        for (int i = 0; i < ReceiptList.size(); i++) {
            LocalDate dateManufacturing = LocalDate.parse(ReceiptList.get(i).getProduct().getManufacturingDate(), format);
            for (int j = i + 1; j < ReceiptList.size(); j++) {
                LocalDate dateManufacturing1 = LocalDate.parse(ReceiptList.get(j).getProduct().getManufacturingDate(), format);
                if (ReceiptList.get(i).getProduct().getCode().matches(ReceiptList.get(j).getProduct().getCode())
                        && dateManufacturing.equals(dateManufacturing1)) {
                    sum += ReceiptList.get(j).getQuantity();
                    ReceiptList.remove(j);
                    ReceiptList.get(i).setQuantity(ReceiptList.get(i).getQuantity() + sum);
                }
            }        
        }
    }
    
    public Product chooseProduct(String code, DaoReport daoReport) {
        ArrayList<InStore> productListSameCode = new ArrayList<>();
        for (InStore inStore : daoReport.getStoreList()) {
            if (inStore.getProduct().getCode().matches(code)) {
                productListSameCode.add(inStore);
            }
        }
        Comparator<InStore> c = new Comparator<InStore>() {
            @Override
            public int compare(InStore o1, InStore o2) {
                return o1.getProduct().getManufacturingDate().compareTo(o2.getProduct().getManufacturingDate());
            }
        };
        Collections.sort(productListSameCode, c);
        System.out.printf("%-9s%-16s%-16s%-20s%-25s%-20s%-15s","Number", "Code product", "Type", "Name product", "Manufacturing date" ,"Expirtation date", "Quantity");
        System.out.println();
        for (int i = 0; i < productListSameCode.size(); i++) {
            System.out.println((i+1)+"        "+productListSameCode.get(i).toString());
        }

        int choice = input.inputChoice("Your choice", 0, productListSameCode.size() + 1);
        if( productListSameCode.get(choice - 1).getQuantity()>0){
            return productListSameCode.get(choice - 1).getProduct();
        }else return null;        
    }
}
