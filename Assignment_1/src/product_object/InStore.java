/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package product_object;

/**
 *
 * @author User
 */
public class InStore {
    private Product product;
    private int quantity;

    public InStore(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public Product getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return String.format("%-16s%-20s%-20s%-25s%-20s%-10d", product.getCode(), product.getTypeProduct(), product.getName(), product.getManufacturingDate(), product.getExpirationDate(), quantity);
    }
    
}
