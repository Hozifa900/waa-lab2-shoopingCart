package mvc;

import jakarta.validation.constraints.*;

public class Product {
    @NotEmpty(message = "Product number cannot be empty")
    @Size(min = 2, max = 5, message = "Product number should be between 2 and 5 characters")
    private String productNumber;
    @NotEmpty(message = "Product name cannot be empty")
    @Size(min = 2, max = 20, message = "Product name should be between 2 and 20 characters")
    private String name;
    private double price;

    public Product() {
    }

    public Product(String productNumber, String name, double price) {

        this.productNumber = productNumber;
        this.name = name;
        this.price = price;
    }

    public String getProductNumber() {
        return productNumber;
    }

    public void setProductNumber(String productNumber) {
        this.productNumber = productNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

}
