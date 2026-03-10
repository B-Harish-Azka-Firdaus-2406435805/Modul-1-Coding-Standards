package id.ac.ui.cs.advprog.eshop.model;

import lombok.Getter;

import java.util.List;

@Getter
public class Order {
    String id;
    List<Product> products;
    Long orderTime;
    String author;
    String status;

    public Order(String id, List<Product> products, Long orderTime, String author) {
        this.id = id;
        this.orderTime = orderTime;
        this.author = author;
        this.status = "WAITING_PAYMENT";

        if (products.isEmpty()) {
            throw new IllegalArgumentException();
        } else {
            this.products = products;
        }
    }

    public Order(String id, List<Product> products, Long orderTime, String author, String status) {
        this(id, products, orderTime, author);

        String[] statusList = {"WAITING_PAYMENT", "FAILED", "SUCCESS", "CANCELLED"};
        boolean valid = false;
        for (String s : statusList) {
            if (s.equals(status)) {
                valid = true;
                break;
            }
        }
        if (!valid) {
            throw new IllegalArgumentException();
        } else {
            this.status = status;
        }
    }

    public void setStatus(String status) {
        String[] statusList = {"WAITING_PAYMENT", "FAILED", "SUCCESS", "CANCELLED"};
        boolean valid = false;
        for (String s : statusList) {
            if (s.equals(status)) {
                valid = true;
                break;
            }
        }
        if (!valid) {
            throw new IllegalArgumentException();
        } else {
            this.status = status;
        }
    }
}
